package com.makeagame.core.resource.plugin;

import java.util.ArrayList;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.makeagame.core.Bootstrap;
import com.makeagame.core.Driver;
import com.makeagame.core.Engine;
import com.makeagame.core.view.RenderEvent;
import com.makeagame.core.view.SignalEvent;
import com.makeagame.core.view.SignalEvent.MouseEvent;
import com.makeagame.tools.MathTools;


public class LibgdxDriver extends ApplicationAdapter  implements Driver{

    SpriteBatch batch;
    BitmapFont gameLable;
    Engine engine;
    
    @Override
    public void init() {
        System.out.println("game start");
        if (Engine.LOG) {
            Gdx.app.setLogLevel(Application.LOG_DEBUG);
        }
        batch = new SpriteBatch();
        gameLable = new BitmapFont();
        gameLable.setColor(new Color(1, 0, 0, 1));    
    }


    @Override
    public ArrayList<SignalEvent> signal(final ArrayList<SignalEvent> signalList) {
        
        // TODO 這應該是一次性的設定，不該在此呼叫
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
        return signalList;
    }

    
    
    @Override
    public ArrayList<RenderEvent> render(ArrayList<RenderEvent> renderList) {
        Engine.logD("batch begine time " + System.currentTimeMillis());
        batch.begin();
        // batch.enableBlending();

        for (RenderEvent e : renderList) {
            // if (e.useBlend) {
            // batch.enableBlending();
            // batch.setBlendFunction(e.srcFunc, e.dstFunc);
            // } else {
            // batch.disableBlending();
            // }

            // if (e.useBlend) {
            // batch.setBlendFunction(e.srcFunc, e.dstFunc);
            // } else {
            // batch.setBlendFunction(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
            // }
            batch.setColor(e.color);

            switch (e.type) {
            case RenderEvent.IMAGE:
            
                // if (e.texture != null) {
                // batch.draw(e.texture, e.x, Bootstrap.screamHeight() - e.y - e.dstH, 0, 0, e.srcW, e.srcH, e.ratioX, e.ratioY, e.angle);
                // draw(Texture texture, float x, float y, float width, float height, int srcX, int srcY, int srcWidth, int srcHeight, boolean flipX, boolean flipY)
                // Texture texture = ResourceManager.get().textureMap.get(e.s);
            
//                Texture texture = ResourceManager.get().textureMap.get(e.res.path);
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

                // Engine.logI("src: (" + new Integer(srcX).toString() + ","
                // + new Integer(srcY).toString() + ","
                // + new Integer(srcW).toString() + ","
                // + new Integer(srcH).toString());

                // Engine.logI("srcH: " + new Integer(srcH).toString());
                float x = e.x;
                float y = Bootstrap.screamHeight() - e.y - srcH;
                batch.draw(texture, x, y, (float) srcW, (float) srcH, srcX, srcY, srcW, srcH, e.flipX, e.flipY);
                // batch.draw(texture, x, y);
                // }
                break;
            case RenderEvent.LABEL:
                gameLable.draw(batch, e.s, e.x, Bootstrap.screamHeight() - e.y);
                break;
//            case RenderEvent.SOUND:
//                InternalResource payload = e.res.getPayload();
//                if(payload instanceof LibgdxResSound){
//                    Sound sound = payload.get();
//                    sound.play(e.vol);
//                } 
//                break;
            }
        }
        batch.end();
        Engine.logD("batch end time " + System.currentTimeMillis());
        try {
            Thread.sleep((long) (1000 / Bootstrap.FPS - Gdx.graphics.getDeltaTime()));
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        return renderList;        
    }
    
    
    @Override
    public void create() {
        super.create();
        init();
    }
    
    @Override
    public void render() {
        engine.mainLoop();
    }

    @Override
    public Driver setEngine(Engine engine){
        this.engine = engine;
        return this;
    }
    
    public LibgdxDriver getApplication(){
        return this;
    }

}

