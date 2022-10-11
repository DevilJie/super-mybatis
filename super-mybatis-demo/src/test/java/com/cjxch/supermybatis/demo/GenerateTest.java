package com.cjxch.supermybatis.demo;

import com.cjxch.supermybatis.generate.GenerateBuilder;
import org.junit.Test;

/**
 * @Author Czy
 * @Description 生成工具类测试
 **/
public class GenerateTest {

    @Test
    public void generateTest(){
        GenerateBuilder.builder().entityPath("com.cjxch.supermybatis.demo.entity")
                .savePath("D:\\generate\\aaa").generate();

    }
}
