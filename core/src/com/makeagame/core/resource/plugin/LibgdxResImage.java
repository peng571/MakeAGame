package com.makeagame.core.resource.plugin;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.makeagame.core.resource.InternalResource;
import com.makeagame.core.resource.type.Type;

@Type(type = "img")
public class LibgdxResImage extends Texture implements InternalResource{

    public LibgdxResImage(){
        super("");
    }
    
    public LibgdxResImage(FileHandle file) {
        super(file);
    }
    
    public LibgdxResImage(String path){
        super(path);
    }
}

