package #package#.impl;

import com.cjxch.supermybatis.core.Service.impl.BaseServiceImpl;
import #entity#;
import #package#.#I##entityName#Service;
import #dao-package#.#I##entityName#Dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @Author #author#
 * @Description #entityName#
 * @Date: #date#
 */
@Service
public class #entityName#ServiceImpl extends BaseServiceImpl<#entityName#> implements #I##entityName#Service {

    @Autowired
    public void setBaseDao(#I##entityName#Dao #entityName-2#Dao) {
        super.setBaseDao(#entityName-2#Dao);
    }

}
