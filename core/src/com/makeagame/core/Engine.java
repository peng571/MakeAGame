package com.makeagame.core;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.makeagame.core.model.ModelManager;
import com.makeagame.core.resource.Resource;
import com.makeagame.core.resource.ResourceManager;
import com.makeagame.core.view.RenderEvent;
import com.makeagame.core.view.SignalEvent;
import com.makeagame.core.view.SignalEvent.MouseEvent;
import com.makeagame.core.view.ViewManager;

public class Engine extends ApplicationAdapter {

	public static boolean LOG = true;
	public static boolean DEBUG = true;

	SpriteBatch batch;
	BitmapFont gameLable;
	Bootstrap bootstrap;

	ArrayList<SignalEvent> signalList = new ArrayList<SignalEvent>();
	ArrayList<RenderEvent> renderList;

	public Engine(Bootstrap bootstrap) {
		this.bootstrap = bootstrap;
	}

	@Override
	public void create() {
		System.out.println("game start");
		batch = new SpriteBatch();

		bootstrap.resourceFactory(ResourceManager.get());
		bootstrap.viewFactory(ViewManager.get());
		bootstrap.modelFactory(ModelManager.get());

		gameLable = new BitmapFont();
		gameLable.setColor(new Color(1, 0, 0, 1));
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(Bootstrap.BACKGROUND_COLOR.r, Bootstrap.BACKGROUND_COLOR.g, Bootstrap.BACKGROUND_COLOR.b, Bootstrap.BACKGROUND_COLOR.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
				logI("touch " + screenX + " " + screenY);

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

		if (signalList != null) {
			ViewManager.get().signal(signalList);
			signalList.clear();
		} else {
			signalList = new ArrayList<SignalEvent>();
		}

		batch.begin();
		batch.enableBlending();
		renderList = ViewManager.get().render();
		for (RenderEvent e : renderList) {
			// if (e.useBlend) {
			// batch.enableBlending();
			// batch.setBlendFunction(e.srcFunc, e.dstFunc);
			// } else {
			// batch.disableBlending();
			// }
			if (e.useBlend) {
				batch.setBlendFunction(e.srcFunc, e.dstFunc);
			} else {
				batch.setBlendFunction(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
			}
			batch.setColor(e.color);
			switch (e.type) {
			case RenderEvent.IMAGE:
				//if (e.texture != null) {
					//batch.draw(e.texture, e.x, Bootstrap.screamHeight() - e.y - e.dstH, 0, 0, e.srcW, e.srcH, e.ratioX, e.ratioY, e.angle);
					//draw(Texture texture, float x, float y, float width, float height, int srcX, int srcY, int srcWidth, int srcHeight, boolean flipX, boolean flipY)
					//Texture texture = ResourceManager.get().textureMap.get(e.s);
					Texture texture = ResourceManager.get().textureMap.get(e.res.path);
					//Engine.logI("x: " + new Float(e.x).toString());
					//Engine.logI("y: " + new Float(e.y).toString());
					
					
					int dim[] = e.res.getSrcDim();
					int srcX = dim[0] + e.srcX;
					int srcY = dim[1] + e.srcY;
					int srcW = e.srcW == -1 ? dim[2] : Math.min(dim[2], e.srcW);
					int srcH = e.srcH == -1 ? dim[3] : Math.min(dim[3], e.srcH);
					//Engine.logI("srcW: " + new Integer(srcW).toString());
					//Engine.logI("srcH: " + new Integer(srcH).toString());
					float x = e.x;
					float y = Bootstrap.screamHeight() - e.y - srcH;
					batch.draw(texture, x, y, (float)srcW, (float)srcH, srcX, srcY, srcW, srcH, e.flipX, e.flipY);
				//}
				break;
			case RenderEvent.LABEL:
				gameLable.draw(batch, e.s, e.x, Bootstrap.screamHeight() - e.y);
				break;
			case RenderEvent.SOUND:
				Sound sound = ResourceManager.get().soundMap.get(e.res.path);
				sound.play(e.vol);
				break;
			}
		}
		batch.end();
		try {
			Thread.sleep((long) (1000 / Bootstrap.FPS - Gdx.graphics.getDeltaTime()));
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}

	public static void logI(String s) {
		if (LOG) {
			System.out.println(s);
		}
	}

	public static void logD(String d) {
		if (LOG) {
			System.out.println(d);
		}
	}

	public static void logE(String e) {
		System.out.println(e);
	}

	public static void logE(Exception e) {
		System.out.println(e);
	}

}
