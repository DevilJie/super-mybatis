package com.cjxch.supermybatis.core.setting;

public class SnowflakeSetting {
    @Deprecated
    private Long workerId = 1l;
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