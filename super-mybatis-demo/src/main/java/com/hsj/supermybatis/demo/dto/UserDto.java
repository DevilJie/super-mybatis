package com.hsj.supermybatis.demo.dto;

import com.hsj.supermybatis.base.annotation.Column;
import com.hsj.supermybatis.base.annotation.Table;
import com.hsj.supermybatis.base.enu.MatchMode;
import com.hsj.supermybatis.demo.entity.User;

/**
 * @Author: 菜鸡小彩虹
 * @Date: 2020/09/30/16:26
 */
@Table("test_user")
public class UserDto extends User {

    @Column(matchMode = MatchMode.GE, ignored = true, matchBase = "age")
    private Integer ageStart;
    @Column(matchMode = MatchMode.LE, ignored = true, matchBase = "age")
    private Integer ageEnd;

    public Integer getAgeStart() {
        return ageStart;
    }

    public void setAgeStart(Integer ageStart) {
        this.ageStart = ageStart;
    }

    public Integer getAgeEnd() {
        return ageEnd;
    }

    public void setAgeEnd(Integer ageEnd) {
        this.ageEnd = ageEnd;
    }
}
