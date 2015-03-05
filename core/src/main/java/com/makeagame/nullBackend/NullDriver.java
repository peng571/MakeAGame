package com.makeagame.nullBackend;
import java.util.ArrayList;

/*
import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
*/

// TODO: 不要引用Bootstrap
import com.makeagame.core.Bootstrap;

import com.makeagame.core.Driver;
import com.makeagame.core.Engine;

import com.makeagame.core.view.RenderEvent;
import com.makeagame.core.view.SignalEvent;
import com.makeagame.core.view.SignalEvent.MouseEvent;

/*
import com.makeagame.core.resource.InternalResource;
import com.makeagame.core.resource.ResourceManager;
import com.makeagame.core.resource.type.Type;


import com.makeagame.tools.MathTools;
*/


public class NullDriver  implements Driver{

    //SpriteBatch batch;
    //BitmapFont gameLable;
    Engine engine;
    
    
    
    /*  
     *  測用的功能
     *
     */
    
    public String renderResult = "";
    
    final ArrayList<SignalEvent> _signalList = new ArrayList<SignalEvent>();
    int mouseX, mouseY;
    
    public void simMouse(int screenX, int screenY) {
        mouseX = screenX;
        mouseY = screenY;
        this._signalList.add(new SignalEvent(SignalEvent.MOUSE_EVENT, SignalEvent.ACTION_MOVE, new int[] { 0, mouseX, mouseY }));
    }
    
    public void simMouseClick() {
        this._signalList.add(new SignalEvent(SignalEvent.MOUSE_EVENT, SignalEvent.ACTION_DOWN, new int[] { 0, mouseX, mouseY }));
        this._signalList.add(new SignalEvent(SignalEvent.MOUSE_EVENT, SignalEvent.ACTION_UP, new int[] { 0, mouseX, mouseY }));
    }
    
    @Override
    public void init() {
        //System.out.println("game start");
        
        //batch = new SpriteBatch();
        //gameLable = new BitmapFont();
        //gameLable.setColor(new Color(1, 0, 0, 1));    
    }
    

    @Override
    public ArrayList<SignalEvent> signal(final ArrayList<SignalEvent> signalList) {
        /*
        Gdx.input.setInputProcessor(new InputAdapter() {

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                signalList.add(new SignalEvent(SignalEvent.MOUSE_EVENT, SignalEvent.ACTION_UP, new int[] { button, screenX, screenY }));
                return super.touchUp(screenX, screenY, pointer, button);
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                signalList.add(new SignalEvent(SignalEvent.MOUSE_EVENT, SignalEvent.ACTION_DRAG, new int[] { SignalEvent.MouseEvent.ANY_KEY, screenX, screenY }));
                return super.touchDragged(screenX, screenY, pointer);
            }

            @Override
            public boolean mouseMoved(int screenX, int screenY) {
                signalList.add(new SignalEvent(SignalEvent.MOUSE_EVENT, SignalEvent.ACTION_MOVE, new int[] { MouseEvent.ANY_KEY, screenX, screenY }));
                return super.mouseMoved(screenX, screenY);
            }

            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                Engine.logD("touch " + screenX + " " + screenY);
                signalList.add(new SignalEvent(SignalEvent.MOUSE_EVENT, SignalEvent.ACTION_DOWN, new int[] { button, screenX, screenY }));
                return super.touchDown(screenX, screenY, pointer, button);
            }

            @Override
            public boolean keyDown(int keycode) {
                signalList.add(new SignalEvent(SignalEvent.KEY_EVENT, SignalEvent.ACTION_DOWN, new int[] { keycode }));
                return super.keyDown(keycode);
            }

            @Override
            public boolean keyUp(int keycode) {
                signalList.add(new SignalEvent(SignalEvent.KEY_EVENT, SignalEvent.ACTION_UP, new int[] { keycode }));
                return super.keyUp(keycode);
            }
        });
        */
        int button = 0;
        int screenX = 0;
        int screenY = 1;
        
        
        return this._signalList;
    }

    
    
    @Override
    public ArrayList<RenderEvent> render(ArrayList<RenderEvent> renderList) {

        //Engine.logD("batch begine time " + System.currentTimeMillis());

        // clear background
        renderResult = "";
        
        for (RenderEvent e : renderList) {
            switch (e.type) {
            case RenderEvent.IMAGE:
                renderResult += "IMAGE(";
                renderResult += String.valueOf(e.x);
                renderResult += ",";
                renderResult += String.valueOf(e.y);
                renderResult += ");";
                
                /*
                Texture texture = null;
                try {
                    texture = (Texture) e.res.getPayload();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                if(texture == null){
                    return renderList;
                }
            
                // Engine.logI("x: " + new Float(e.x).toString());
                // Engine.logI("y: " + new Float(e.y).toString());

                int dim[] = e.res.getSrcDim();
                int dim2[] = new int[] { e.srcX, e.srcY, e.srcW, e.srcH };
                dim2[0] = dim2[0] + dim[0];
                dim2[1] = dim2[1] + dim[1];
                dim2[2] = dim2[2] == -1 ? dim[2] : dim2[2];
                dim2[3] = dim2[3] == -1 ? dim[3] : dim2[3];

                dim = MathTools.inner(dim, dim2);
                int srcX = dim[0];
                int srcY = dim[1];
                int srcW = dim[2];
                int srcH = dim[3];

                float x = e.x;
                float y = Bootstrap.screamHeight() - e.y - srcH;
                batch.draw(texture, x, y, (float) srcW, (float) srcH, srcX, srcY, srcW, srcH, e.flipX, e.flipY);
                */
                break;
            case RenderEvent.LABEL:
                //gameLable.draw(batch, e.s, e.x, Bootstrap.screamHeight() - e.y);
                renderResult += "LABEL();";
                break;
            case RenderEvent.SOUND:
                renderResult += "SOUND();";
                break;
                
//                InternalResource payload = e.res.getPayload();
//                if(payload instanceof LibgdxResSound){
//                    Sound sound = payload.get();
//                    sound.play(e.vol);
//                } 
//                break;
            }
        }
        
        
        // 威: 之後要決定把等待迴圈移動到特定的地方
        //try {
        //    Thread.sleep((long) (1000 / Bootstrap.FPS - Gdx.graphics.getDeltaTime()));
        //} catch (InterruptedException e1) {
        //    e1.printStackTrace();
        //}
       
        
        return renderList;        
    }
    
    
    // 注意: 這裡是 Override libgdx
    //@Override
    //public void create() {
    //    super.create();
    //    init();
    //}
    
    
    // 注意: 這裡是 Override libgdx
    //@Override
    //public void render() {
    //    engine.mainLoop();
    //}
    
    

    @Override
    public Driver setEngine(Engine engine){
        this.engine = engine;
        return this;
    }
    
    // 注意: 這裡是給 android 專用?
    //public NullDriver getApplication(){
    //    return this;
    //}

}

