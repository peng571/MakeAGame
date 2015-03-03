package com.makeagame.core.resource;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.makeagame.core.resource.Resource.ResourceState;
import com.makeagame.core.resource.process.MemoryManager;
import com.makeagame.core.resource.process.Processor;

/**
 * 負責 Resource 的讀取
 * 處裡多序
 */
public class ResourceSystem{

    Provider provider;
    
    MemoryManager manager;
//    HashMap<String, Resource> map = new HashMap<String, Resource>();

//    // TODO 找找看有沒有更好的做法
    public ArrayList<Processor> processorList; 
   
    public static ResourceSystem instance;

    private ResourceSystem() {
//        provider = new Provider();
        
        processorList = new ArrayList<Processor>();
    }

    public static ResourceSystem get() {
        if (instance == null) {
            instance = new ResourceSystem();
        }
        return instance;
    }

    
    public void addProcessor(Processor process) {
        processorList.add(process);
    }

    public void removeProcessor(Processor process) {
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
    
    
    // TODO: 多序
    public Resource fetch(String id){
        
        Resource res = ResourceManager.get().get(id);
       
        for(Processor process : processorList) {
            res = process.handleResource(res);
      }
        
//        // TODO: 應該不是用 NAMED
//        // 還未擁有 Payload 的 Resource
//        if(res.getState() == ResourceState.NAMED){
//            JSONObject attr;
//            try {
//                attr = find(id);
//                load(res, attr);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
      
        
       
        
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
    
   
    
//    // TODO 在另一個 thread 進行
//    private synchronized JSONObject find(String id) throws JSONException{
//        for(LoadProcessor process : processorList) {
//            if(process instanceof Finder){
//                JSONObject attr = ((Finder) process).find(id);
//                if(attr != null){
//                    return attr;
//                }
//            }
//        }
//        return new JSONObject().put("path", "").put("type", "err");
//    }
//    
//    
//    // TODO 在另一個 thread 進行
//    private synchronized Resource load(Resource res, JSONObject attribute){
//        for(LoadProcessor process : processorList) {
//            if(process instanceof Loader){
//                InternalResource payload = null;
//                Loader loader = ((Loader) process);
//                String type = attribute.optString("type", "err");
//                String path = attribute.optString("path");
//                payload = loader.decode(path, attribute);
//                // 該在這邊判斷類型嗎? 或者交給Decoder處裡?
////                if("img".equals(type)){
////                    payload = loader.load(attribute.optString("path"), new LibgdxResImage());
////                } else if("snd".equals(type)){
////                    payload = loader.load(attribute.optString("path"), new LibgdxResImage()); // TODO add class LibgdxSound
////                }else if("atr".equals(type)){
////                    payload = loader.load(attribute.optString("path"), new LibgdxResImage()); // TODO add class LibgdxText
////                }
//                
//                if(payload != null){
//                    res.setPayload(payload);
//                    res.setState(ResourceState.USABLE);
//                    return res;
//                }
//            }
//        }
//        return res;
//        
//    }
    
    
    

}
