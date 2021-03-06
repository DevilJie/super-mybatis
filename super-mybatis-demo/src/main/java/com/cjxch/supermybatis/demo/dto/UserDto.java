package com.cjxch.supermybatis.demo.dto;

import com.cjxch.supermybatis.base.annotation.Column;
import com.cjxch.supermybatis.base.annotation.Table;
import com.cjxch.supermybatis.base.enu.MatchMode;
import com.cjxch.supermybatis.demo.entity.User;

import java.util.List;

/**
 * @Author: 菜鸡小彩虹
 * @Date: 2020/09/30/16:26
 */
@Table("user_info")
public class UserDto extends User {

    @Column(matchMode = MatchMode.GE, ignored = true, matchBase = "age")
    private Integer ageStart;
    @Column(matchMode = MatchMode.LE, ignored = true, matchBase = "age")
    private Integer ageEnd;
    @Column(matchMode = MatchMode.NOT_IN, ignored = true, matchBase = "age")
    private List<Integer> ages;

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

    public List<Integer> getAges() {
        return ages;
    }

    public void setAges(List<Integer> ages) {
        this.ages = ages;
    }
}
