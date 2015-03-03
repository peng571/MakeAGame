package com.makeagame.core.resource;

import com.makeagame.core.view.RenderEvent;



public class SoundResource extends Resource{

    // TODO
    
    SoundResource(String id) {
        super(id);
    }

    @Override
    public int getType() {
        return RenderEvent.SOUND;
    }

}
