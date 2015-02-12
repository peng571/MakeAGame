package com.makeagame.core.newengine;

import java.util.ArrayList;

import com.makeagame.core.resource.Resource;
import com.makeagame.core.resource.process.Finder;
import com.makeagame.core.resource.process.MemoryManager;

public class ResourceSystem2{

    Provider2 provider;
    
    MemoryManager manager;
//    HashMap<String, Resource> map = new HashMap<String, Resource>();

    // TODO 找找看有沒有更好的做法
    public ArrayList<Process2> processorList; 
   
    public static ResourceSystem2 instance;

    private ResourceSystem2() {
//        provider = new Provider();
        
        processorList = new ArrayList<Process2>();
    }

    public static ResourceSystem2 get() {
        if (instance == null) {
            instance = new ResourceSystem2();
        }
        return instance;
    }

    
    public void addProcessor(Process2 process) {
        processorList.add(process);
    }

    public void removeProcessor(Process2 process) {
        processorList.remove(process);
    }

    public void removeAllProcessor() {
        processorList.clear();
    }

    
    
    public void setProvider(Provider2 provider) {
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
    public Provider2 getProvider() {
        return provider;
    }
    
    
    Resource retval;
    // Note:
    // 完成後不用特別通知
    // 把讀取完成的資源存好之後
    // 等待下一次的rander來拿取即可
    public Resource fetch(final String id){
        retval = new Resource(id);
        Thread fecthThread = new Thread(){

            @Override
            public synchronized void run(){
                String path = find(id);
                
                // Object ram = read(path);
                
                // Resource res = decode(ram);
//                retval = decode(path);
            }
            
        };
        return retval;
    }
    
   
    
    // TODO 在另一個 thread 進行
    private synchronized String find(String id){
        for(Process2 process : processorList) {
            if(process instanceof Finder){
                String path = ((Finder) process).findPath(id);
                if("".equals(path)){
                    return path;
                }
            }
        }
        
        //  或者應該回傳null?
        return "";
    }
    
    
    
    

}
