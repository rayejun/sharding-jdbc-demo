package io.github.rayejun.shardingjdbc.demo.model.mysql;

public class Sharding {
    private Integer id;

    private String shardingKey;

    private String shardingKeyName;

    private String logicTable;

    private String targetTable;

    private String targetDb;

    private String dbName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShardingKey() {
        return shardingKey;
    }

    public void setShardingKey(String shardingKey) {
        this.shardingKey = shardingKey == null ? null : shardingKey.trim();
    }

    public String getShardingKeyName() {
        return shardingKeyName;
    }

    public void setShardingKeyName(String shardingKeyName) {
        this.shardingKeyName = shardingKeyName == null ? null : shardingKeyName.trim();
    }

    public String getLogicTable() {
        return logicTable;
    }

    public void setLogicTable(String logicTable) {
        this.logicTable = logicTable == null ? null : logicTable.trim();
    }

    public String getTargetTable() {
        return targetTable;
    }

    public void setTargetTable(String targetTable) {
        this.targetTable = targetTable == null ? null : targetTable.trim();
    }

    public String getTargetDb() {
        return targetDb;
    }

    public void setTargetDb(String targetDb) {
        this.targetDb = targetDb == null ? null : targetDb.trim();
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName == null ? null : dbName.trim();
    }
}