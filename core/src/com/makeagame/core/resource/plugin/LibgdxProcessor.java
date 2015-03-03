package com.makeagame.core.resource.plugin;

import java.util.HashMap;

import org.json.JSONObject;

import com.makeagame.core.resource.InternalResource;
import com.makeagame.core.resource.Resource;
import com.makeagame.core.resource.Resource.ResourceState;
import com.makeagame.core.resource.process.Finder;
import com.makeagame.core.resource.process.Processor;

public class LibgdxProcessor implements Processor {

    private Finder finder;
    
    public HashMap<String, String> pathMap;
    
    public LibgdxProcessor(Finder finder){
        this.finder = finder;
        pathMap = new HashMap<String, String>();
    }
    
    
    private JSONObject find(String id){
        return finder.find(id);
    }

    @Override
    public boolean canHandle() {
        return false;
    }



    @Override
    public void handleResource(Resource res) {
        
        String id = res.getID();
        JSONObject attr = null;
        switch(res.getState()){
        case NAMED:
            res.setState(ResourceState.FINDING);
        case FINDING:
            attr = find(id);
            InternalResource payload = null;
            if(attr == null){
                res.setState(ResourceState.NOTFOUND);
                break;
            }
            res.setState(ResourceState.DECODING);
        case DECODING:
            String type = attr.optString("type");
            String path = attr.optString("path");
            System.out.println("path " + path);
            if("img".equals(type)){
               payload = new LibgdxResImage(path);
            }else if("snd".equals(type)){
                payload = new LibgdxResSound(path);
            }else if("atr".equals(type)){
                payload = new LibgdxResText(path);
            }else{
                res.setState(ResourceState.DECODEERROR);
                return;
            }
            res.setPayload(payload);
            res.setState(ResourceState.USABLE);
        }   
    }

}

