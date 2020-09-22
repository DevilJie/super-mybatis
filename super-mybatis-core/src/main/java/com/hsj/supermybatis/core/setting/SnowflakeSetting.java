package com.hsj.supermybatis.core.setting;

public class SnowflakeSetting {
    /**
     * TODO 机器 ID 部分
     */
    @Deprecated
    private Long workerId = 1l;
    /**
     * TODO 数据标识 ID 部分
     */
    @Deprecated
    private Long datacenterId = 1l;

    public Long getWorkerId() {
        return workerId;
    }

    public void setWorkerId(Long workerId) {
        this.workerId = workerId;
    }

    public Long getDatacenterId() {
        return datacenterId;
    }

    public void setDatacenterId(Long datacenterId) {
        this.datacenterId = datacenterId;
    }
}