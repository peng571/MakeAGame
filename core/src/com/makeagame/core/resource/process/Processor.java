package com.makeagame.core.resource.process;

public @interface Processor {

    boolean isFinder() default false;

//    boolean isReader() default false;

//    boolean isDecoder() default false;
    
    boolean isLoader() default false;
    
}
