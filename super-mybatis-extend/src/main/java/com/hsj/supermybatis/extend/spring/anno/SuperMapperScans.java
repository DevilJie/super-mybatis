/**
 * Copyright 2010-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hsj.supermybatis.extend.spring.anno;

import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The Container annotation that aggregates several {@link SuperMapperScan} annotations.
 *
 * <p>
 * Can be used natively, declaring several nested {@link SuperMapperScan} annotations. Can also be used in conjunction with
 * Java 8's support for repeatable annotations, where {@link SuperMapperScan} can simply be declared several times on the
 * same method, implicitly generating this container annotation.
 *
 * @author Kazuki Shimizu
 * @since 2.0.0
 * @see SuperMapperScan
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(SuperMapperScannerRegistrar.RepeatingRegistrar.class)
public @interface SuperMapperScans {
  SuperMapperScan[] value();
}
