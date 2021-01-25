package com.bigdistributor.biglogger.generic;


import com.bigdistributor.core.app.ApplicationMode;
import com.bigdistributor.core.generic.IndexableClass;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Every LoggerManager has {@code Logger} as a superclass. All objects,
 * including terminal information, Fiji logging, pipeline management and remote logging implement the methods of this class.
 * In order to be indexed add annotation @LogProcessor, with identifying the context (Fiji, Headless, Cluster, Cloud)
 *
 * @author Marwan Zouinkhi
 * @since V0.1
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@IndexableClass
public @interface LogHandler {

    String format() default "";

    LogMode type();

    ApplicationMode[] modes();

    double priority() default 0.0D;
}


