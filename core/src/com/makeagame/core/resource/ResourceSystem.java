package com.makeagame.core.resource;

import java.util.ArrayList;

import com.makeagame.core.resource.Resource.ResourceState;
import com.makeagame.core.resource.plugin.LibgdxImage;
import com.makeagame.core.resource.process.Finder;
import com.makeagame.core.resource.process.LoadProcessor;
import com.makeagame.core.resource.process.Loader;
import com.makeagame.core.resource.process.MemoryManager;

public class ResourceSystem{

    Provider provider;
    
    MemoryManager manager;
//    HashMap<String, Resource> map = new HashMap<String, Resource>();

    // TODO 找找看有沒有更好的做法
    public ArrayList<LoadProcessor> processorList; 
   
    public static ResourceSystem instance;

    private ResourceSystem() {
//        provider = new Provider();
        
        processorList = new ArrayList<LoadProcessor>();
    }

    public static ResourceSystem get() {
        if (instance == null) {
            instance = new ResourceSystem();
        }
        return instance;
    }

    
    public void addProcessor(LoadProcessor process) {
        processorList.add(process);
    }

    public void removeProcessor(LoadProcessor process) {
        processorList.remove(process);
    }

    public void removeAllProcessor() {
        processorList.clear();
    }

    
    
    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public void setMemoryManager(MemoryManager manager) {
        this.manager = manager;
    }

    public void resetDefault() {
        removeAllProcessor();
    }

    
    public void startup() {
        // TODO 設定好系統之後, 要呼叫void startup()用來啟動固定資源系統, 一但啟動之後所有的外掛方法都不能再被使用
    }

    
    // TODO 用provider作為和使用者的溝通介面用意是????
    public Provider getProvider() {
        return provider;
    }
    
    
    // Note:
    // 完成後不用特別通知
    // 把讀取完成的資源存好之後
    // 等待下一次的rander來拿取即可
    public Resource fetch(String id){
        
        Resource res = ResourceManager.get().get(id);
       
        
        // TODO: 應該不是用 NAMED
        // 還未擁有 Payload 的 Resource
        if(res.getState() == ResourceState.NAMED){
            String path = find(id);
            load(res, path, new LibgdxImage());
        }
      
        
       
        
        // 多序的部分先跳過
//        Thread fecthThread = new Thread(){
//
//            @Override
//            public synchronized void run(){
//                String path = find(id);
//                
//                // Object ram = read(path);
//                
//                // Resource res = decode(ram);
//                returnRes = decode(path);
//            }
//            
//        };
        
        
        
        
        
        return res;
    }
    
   
    
    // TODO 在另一個 thread 進行
    private synchronized String find(String id){
        for(LoadProcessor process : processorList) {
            if(process instanceof Finder){
                String path = ((Finder) process).findPath(id);
                if("".equals(path)){
                    return path;
                }
            }
        }
        
        return "";
    }
    
    
    private synchronized Resource load(Resource res, String path, InternalResource type){
        for(LoadProcessor process : processorList) {
            if(process instanceof Loader){
                InternalResource payload = ((Loader) process).load(path, type);
                if(payload != null){
                    res.setPayload(payload);
                    res.setState(ResourceState.USABLE);
                    return res;
                }
            }
        }
        return res;
        
    }
    
    
    

}
