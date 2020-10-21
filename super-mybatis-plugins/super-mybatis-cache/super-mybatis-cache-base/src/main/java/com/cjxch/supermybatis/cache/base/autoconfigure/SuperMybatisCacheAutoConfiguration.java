/**
 * Copyright 2015-2019 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cjxch.supermybatis.cache.base.autoconfigure;

import com.cjxch.supermybatis.base.enu.DbCacheType;
import com.cjxch.supermybatis.cache.base.core.SuperMybatisCache;
import com.cjxch.supermybatis.cache.base.core.SuperMybatisCacheConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;


@Configuration
@AutoConfigureAfter(name="com.cjxch.supermybatis.extend.spring.SuperMybatisSqlSessionFactoryBean")
@ImportResource({"classpath*:applicationContext-supermybatis-cache.xml"})
public class SuperMybatisCacheAutoConfiguration {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public SuperMybatisCacheAutoConfiguration(SuperMybatisCacheProperties properties) {
        if(SuperMybatisCacheConstants.superMybatisCache != null) return;
        logger.debug("【Super-Mybatis-Cache】Initializing database cache plug-in...");
        DbCacheType type = properties.getGlobalSetting().getDbCacheSetting().getDbCacheType();
        SuperMybatisCacheConstants.dbCacheSetting = properties.getGlobalSetting().getDbCacheSetting();
        if(type == null)
            logger.debug("【Super-Mybatis-Cache】Database cache plug-in initialization failed: Please check if the 'dbCacheType' is configured");
        else{
            SuperMybatisCache.init(properties.getGlobalSetting().getDbCacheSetting());
            logger.debug("【Super-Mybatis-Cache】Finished initializing database cache plug-in...");
        }
    }
}
