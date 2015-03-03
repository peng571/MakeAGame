package com.makeagame.core.resource.plugin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.makeagame.core.resource.InternalResource;

public class LibgdxResText implements InternalResource {

    private String text;
    
    public LibgdxResText(String path) {
        FileHandle handle = Gdx.files.internal(path);
        if (handle != null && handle.exists()) {
            text = handle.readString();
        }
    }
    
    public String getText(){
        return text;
    }
}
