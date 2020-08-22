package io.github.rayejun.shardingjdbc.demo.config.algorithm;

import io.github.rayejun.shardingjdbc.demo.utils.ShardingUtil;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.Collection;

/**
 * 精确分片算法
 */
public class CustomPreciseShardingAlgorithm implements PreciseShardingAlgorithm<String> {

    private final String type;

    public CustomPreciseShardingAlgorithm(String type) {
        this.type = type;
    }

    @Lazy
    @Autowired
    ShardingUtil shardingUtil;

    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<String> shardingValue) {
        return shardingUtil.getTarget(type, shardingValue);
    }
}
