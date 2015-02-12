package com.makeagame.core.resource.plugin;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.makeagame.core.resource.InternalResource;
import com.makeagame.core.resource.type.Type;

@Type(type = "img")
public class LibgdxImage extends Texture implements InternalResource{

    public LibgdxImage(){
        super("");
    }
    
    public LibgdxImage(FileHandle file) {
        super(file);
    }
}
