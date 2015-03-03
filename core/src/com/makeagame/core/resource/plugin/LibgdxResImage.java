package com.makeagame.core.resource.plugin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.makeagame.core.resource.InternalResource;
import com.makeagame.core.resource.type.Type;

@Type(type = "img")
public class LibgdxResImage extends Texture implements InternalResource{

    public LibgdxResImage(FileHandle file) {
        super(file);
        
        System.out.println("LibgdxResImage(FileHandle file) " + file.toString());
    }
    
    public LibgdxResImage(String path){
        // XXX 在外部資源的存取架構弄好前，暫時先單純的存取內部資源
        super(Gdx.files.internal(path));
        
//        super(Gdx.files.classpath("../resource/" + path));
//        Gdx.files.internal(path);
//        System.out.println("LibgdxResImage(String path) " + path);
    }
}

