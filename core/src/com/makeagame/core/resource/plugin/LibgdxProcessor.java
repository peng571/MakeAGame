package com.makeagame.core.resource.plugin;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.makeagame.core.resource.InternalResource;
import com.makeagame.core.resource.process.Loader;
import com.makeagame.core.resource.process.Processor;

@Processor(isLoader = true)
public class LibgdxProcessor implements Loader {

    public HashMap<String, String> pathMap;
    
    public HashMap<String, Texture> textureMap;
    public HashMap<String, Sound> soundMap;
    public HashMap<String, String> attributeMap;

    public LibgdxProcessor(){
        
        pathMap = new HashMap<String, String>();
        
        textureMap = new HashMap<String, Texture>();
        soundMap = new HashMap<String, Sound>();
        attributeMap = new HashMap<String, String>();
    }
    



    @Override
    public InternalResource load(String path, InternalResource type) {
        if(type instanceof LibgdxImage){
            return new LibgdxImage(Gdx.files.internal(path));
        }
        return null;
    }



    @Override
    public boolean match(String path, InternalResource type) {
        return false;
    }
    


}

