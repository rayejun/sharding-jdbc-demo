package io.github.rayejun.shardingjdbc.demo.dao.mysql;

import io.github.rayejun.shardingjdbc.demo.model.mysql.User;

public interface UserMapper {
    int deleteByPrimaryKey(String id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}