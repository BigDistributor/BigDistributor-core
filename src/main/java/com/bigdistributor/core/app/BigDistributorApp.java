package com.bigdistributor.core.app;


import com.bigdistributor.core.generic.IndexableClass;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@IndexableClass
public @interface BigDistributorApp {

    ApplicationMode mode();

}
