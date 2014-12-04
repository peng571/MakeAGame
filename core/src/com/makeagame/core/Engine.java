package com.makeagame.core;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.makeagame.core.model.ModelManager;
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
		renderList = ViewManager.get().render();
		for (RenderEvent e : renderList) {
			switch (e.type) {
			case RenderEvent.IMAGE:
				if (e.texture != null) {
					if (e.useBlend) {
						batch.enableBlending();
						batch.setBlendFunction(e.srcFunc, e.dstFunc);
					} else {
						batch.disableBlending();
					}
					batch.setColor(e.color);
					batch.draw(e.texture, e.x, Bootstrap.screamHeight() - e.y - e.dstH, 0, 0, e.srcW, e.srcH, e.ratioX, e.ratioY, e.angle);

				}
				break;
			case RenderEvent.LABEL:
				gameLable.setColor(e.color);
				gameLable.draw(batch, e.s, e.x, Bootstrap.screamHeight() - e.y);
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
