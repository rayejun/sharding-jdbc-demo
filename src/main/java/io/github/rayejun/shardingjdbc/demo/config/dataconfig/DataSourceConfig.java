package io.github.rayejun.shardingjdbc.demo.config.dataconfig;

import com.alibaba.druid.pool.DruidDataSource;
import io.github.rayejun.shardingjdbc.demo.config.algorithm.CustomPreciseShardingAlgorithm;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.StandardShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

@Configuration
@MapperScan(basePackages = "io.github.rayejun.shardingjdbc.demo.dao.mysql", sqlSessionTemplateRef = "sqlSessionTemplate")
public class DataSourceConfig {

    final Environment environment;

    public DataSourceConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public CustomPreciseShardingAlgorithm dbPreciseShardingAlgorithm() {
        return new CustomPreciseShardingAlgorithm("db");
    }

    @Bean
    public CustomPreciseShardingAlgorithm tablePreciseShardingAlgorithm() {
        return new CustomPreciseShardingAlgorithm("table");
    }

    @Bean
    public DataSource dataSource() throws SQLException {
        String dataSourcePrefix = "spring.shardingsphere.datasource", defaultDataSourceName = environment.getProperty("spring.shardingsphere.sharding.default-data-source-name");
        Map<String, DataSource> dataSources = new HashMap<>();
        String[] dataSourceNames = Objects.requireNonNull(environment.getProperty(dataSourcePrefix + ".names")).split(",");
        for (String key : dataSourceNames) {
            String prefix = dataSourcePrefix + "." + key;
            DruidDataSource dataSource = new DruidDataSource();
            dataSource.setDbType(environment.getProperty(prefix + ".type"));
            dataSource.setDriver(new com.mysql.cj.jdbc.Driver());
            dataSource.setUrl(environment.getProperty(prefix + ".url"));
            dataSource.setUsername(environment.getProperty(prefix + ".username"));
            dataSource.setPassword(environment.getProperty(prefix + ".password"));
            dataSource.setTestWhileIdle(false);
            dataSources.put(key, dataSource);
        }

        // 配置base_user表规则
        TableRuleConfiguration userDataTableRuleConfiguration = new TableRuleConfiguration("base_user", "ds$->{0..1}.base_user,ds$->{0..1}.base_user$->{1..2}");
        // 配置分库
        userDataTableRuleConfiguration.setDatabaseShardingStrategyConfig(new StandardShardingStrategyConfiguration("school_id", dbPreciseShardingAlgorithm()));
        // 配置分表
        userDataTableRuleConfiguration.setTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("school_id", tablePreciseShardingAlgorithm()));

        // 配置分片规则
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        // 配置默认数据库
        shardingRuleConfig.setDefaultDataSourceName(defaultDataSourceName);
        shardingRuleConfig.getTableRuleConfigs().add(userDataTableRuleConfiguration);

        Properties props = new Properties();
        props.put("sql.show", "true");
        return ShardingDataSourceFactory.createDataSource(dataSources, shardingRuleConfig, props);
    }

    @Bean
    public DataSourceTransactionManager transactionManager(@Qualifier("dataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setCallSettersOnNulls(true);
        configuration.setLogImpl(org.apache.ibatis.logging.stdout.StdOutImpl.class);
        bean.setConfiguration(configuration);
        return bean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
