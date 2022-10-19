package com.woowacourse.moamoa.common;

import com.woowacourse.moamoa.common.config.JpaAuditingConfig;
import com.woowacourse.moamoa.study.service.AsyncService;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Repository;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@DataJpaTest(includeFilters = @Filter(type = FilterType.ANNOTATION, classes = Repository.class))
@Import({JpaAuditingConfig.class, CategoryAndTagsSaver.class, AsyncService.class})
public @interface RepositoryTest {
}
