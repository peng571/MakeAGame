package com.makeagame.core.resource;

import java.util.HashMap;

/**
 * 管理所有的 Resource
 * 供系統操作用
 * 不建議使用者自行呼叫
 */
public class ResourceManager {

    public static ResourceManager instance;
    private HashMap</* ID= */String, Resource> resourceMap;

    private ResourceManager() {
        resourceMap = new HashMap<String, Resource>();
    }

    public static ResourceManager get() {
        if (instance == null) {
            instance = new ResourceManager();
        }
        return instance;
    }
    
    public Resource get(String id){
        Resource res = resourceMap.get(id);
        
        if(res == null){
            res = new Resource(id);
        }
        
        resourceMap.put(id, res);
        return res;
    }

}
