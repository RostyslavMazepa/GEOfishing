package com.geofishing.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;

import java.lang.annotation.Annotation;

public class IgnoreIncludeAnnotationIntrospector extends JacksonAnnotationIntrospector {

    protected <A extends Annotation> A _findAnnotation(Annotated annotated,
                                                       Class<A> annoClass) {
        if (annoClass.equals(JsonInclude.class)) {
            return null;
        }
        return annotated.getAnnotation(annoClass);
    }

    protected boolean _hasAnnotation(Annotated annotated, Class<? extends Annotation> annoClass) {
        if (annoClass.equals(JsonInclude.class)) {
            return false;
        }
        return annotated.hasAnnotation(annoClass);
    }
}
