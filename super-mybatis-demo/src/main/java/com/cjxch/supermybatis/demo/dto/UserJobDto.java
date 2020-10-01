package com.cjxch.supermybatis.demo.dto;

import com.cjxch.supermybatis.base.annotation.Table;
import com.cjxch.supermybatis.demo.entity.User;

/**
 * @Author: 菜鸡小彩虹
 * @Date: 2020/09/30/17:30
 */
@Table("user_job")
public class UserJobDto extends User {
    private String jobName;

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }
}
