package io.github.rayejun.shardingjdbc.demo.dao.mysql;

import io.github.rayejun.shardingjdbc.demo.model.mysql.Sharding;
import org.apache.ibatis.annotations.Param;

public interface ShardingMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Sharding record);

    int insertSelective(Sharding record);

    Sharding selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Sharding record);

    int updateByPrimaryKey(Sharding record);

    Sharding selectByKeyLogicTable(@Param("shardingKey") String shardingKey, @Param("logicTable") String logicTable);
}