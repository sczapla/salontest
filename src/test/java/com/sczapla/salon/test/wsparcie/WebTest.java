package com.sczapla.salon.test.wsparcie;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static org.springframework.test.context.TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS;

/**
 * Adnotacja do oznaczenia testu JUnitowego służącego jako test Web. Włącza dostępne ułatwienia.
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@TestExecutionListeners(
        listeners = SeleniumTestListener.class,
        mergeMode = MERGE_WITH_DEFAULTS)
@SpringBootTest
@ContextConfiguration(classes =  KonfiguracjaTestow.class)
public @interface WebTest {
}
