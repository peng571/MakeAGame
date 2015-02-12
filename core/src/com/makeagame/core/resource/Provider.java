package com.makeagame.core.resource;


public class Provider<T extends InternalResource> {

    
    // 下面兩個方法會丟出轉型錯誤異常
    Resource<T> fetch(String id) {
        
        return null;
    }

    Resource<T> use(String id) {
        return null;
    }

    void release(String id) {
    }

    int getLoadingCount() {
        return 0;
    }

    int getUsableCount() {
        return 0;
    }

}
