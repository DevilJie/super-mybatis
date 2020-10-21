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
package com.cjxch.supermybatis.cache.redis.autoconfigure;

import com.cjxch.supermybatis.cache.base.autoconfigure.SuperMybatisCacheAutoConfiguration;
import com.cjxch.supermybatis.cache.base.autoconfigure.SuperMybatisCacheProperties;
import com.cjxch.supermybatis.cache.base.core.SuperMybatisCacheConstants;
import com.cjxch.supermybatis.cache.redis.init.SuperMybatisRedisConstants;
import com.cjxch.supermybatis.core.setting.GlobalSetting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;


@Configuration
@AutoConfigureAfter({SuperMybatisCacheAutoConfiguration.class})
@ImportResource("classpath*:applicationContext-supermybatis-cache-redis.xml")
public class SuperMybatisRedisCacheAutoConfiguration {

    public SuperMybatisRedisCacheAutoConfiguration(ApplicationContext applicationContext) {
        SuperMybatisRedisConstants.dbCacheSetting = SuperMybatisCacheConstants.dbCacheSetting;
        SuperMybatisRedisConstants.applicationContext = applicationContext;
    }
}
