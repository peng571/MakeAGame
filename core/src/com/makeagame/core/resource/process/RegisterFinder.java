package com.makeagame.core.resource.process;

import java.util.HashMap;

/**
 * 註冊式搜尋器
 * 以程式碼批次註冊 Resource 的 ID 跟 Path
 * 適合小規模的遊戲使用
 */
@Processor(isFinder=true)
public class RegisterFinder implements Finder {

    HashMap<String, String> map = new HashMap</* ID= */ String, /* Path= */ String>();
    
    @Override
    public String findPath(String id) {
        return map.get(id);
    }

    @Override
    public String[] getIDList() {
        return map.keySet().toArray(new String[map.size()]);
    }
    
    public void register(String id, String path){
        map.put(id, path);
    }
    
    
    
     
    
    
    
    
    
    
    
    

}
