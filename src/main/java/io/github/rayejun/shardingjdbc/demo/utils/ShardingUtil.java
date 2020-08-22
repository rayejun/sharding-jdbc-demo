package io.github.rayejun.shardingjdbc.demo.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.rayejun.shardingjdbc.demo.dao.mysql.ShardingMapper;
import io.github.rayejun.shardingjdbc.demo.model.mysql.Sharding;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ShardingUtil {

    private static final Logger logger = LoggerFactory.getLogger(ShardingUtil.class);

    @Value("${spring.shardingsphere.sharding.default-data-source-name}")
    private String defaultDataSourceName;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    ShardingMapper shardingMapper;

    private String key(String shardingKey, String logicTable) {
        return String.format("sharding_key:%s:logic_table:%s", shardingKey, logicTable);
    }

    public String get(String shardingKey, String logicTable) {
        try {
            return redisTemplate.opsForValue().get(key(shardingKey, logicTable));
        } catch (Exception ex) {
            logger.error("get：", ex);
            return null;
        }
    }

    public void set(String shardingKey, String logicTable, String value) {
        try {
            redisTemplate.opsForValue().set(key(shardingKey, logicTable), value);
        } catch (Exception ex) {
            logger.error("set：", ex);
        }
    }

    public String getTarget(String type, PreciseShardingValue<String> shardingValue) {
        String value = get(shardingValue.getValue(), shardingValue.getLogicTableName());
        if (value == null) {
            Sharding sharding = shardingMapper.selectByKeyLogicTable(shardingValue.getValue(), shardingValue.getLogicTableName());
            if (sharding != null) {
                Map<String, String> map = new HashMap<>();
                map.put("targetDb", sharding.getTargetDb());
                map.put("targetTable", sharding.getTargetTable());
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    set(shardingValue.getValue(), shardingValue.getLogicTableName(), objectMapper.writeValueAsString(map));
                } catch (JsonProcessingException e) {
                    logger.error(e.getMessage());
                }
                return type.equals("db") ? sharding.getTargetDb() : sharding.getTargetTable();
            } else {
                return type.equals("db") ? defaultDataSourceName : shardingValue.getValue();
            }
        } else {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                return objectMapper.readValue(value, Map.class).get(type.equals("db") ? "targetDb" : "targetTable").toString();
            } catch (JsonProcessingException e) {
                logger.error(e.getMessage());
            }
            return null;
        }
    }
}
