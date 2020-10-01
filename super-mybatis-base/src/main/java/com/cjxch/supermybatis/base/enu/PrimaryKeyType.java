package com.cjxch.supermybatis.base.enu;


/**
 * 主键ID的生成策略
 */
public enum PrimaryKeyType {

    /**
     * ID自增长
     * 字段类型：int
     */
    AUTO(1),
    /**
     * UUID
     * 字段类型：varchar(32)
     * UUID.replace("-", "");
     */
    UUID(2),
    /**
     * 雪花算法
     * 字段类型：char(20)
     */
    SNOWFLAKE(3),
    /**
     * 输入ID
     * 根据实体类存储值进行入库
     */
    INPUT(4);

    private int type;

    PrimaryKeyType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
