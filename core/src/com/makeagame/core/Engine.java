package com.makeagame.core;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.makeagame.core.model.ModelManager;
import com.makeagame.core.resource.ResourceManager;
import com.makeagame.core.view.RenderEvent;
import com.makeagame.core.view.SignalEvent;
import com.makeagame.core.view.ViewManager;

public class Engine extends ApplicationAdapter {

	public static boolean LOG = true;
	public static boolean DEBUG = true;

	SpriteBatch batch;
	BitmapFont gameLable;
	Bootstrap bootstrap;

	ArrayList<SignalEvent> signalList;
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

		// keybroad
		signalList = new ArrayList<SignalEvent>();
		if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
			signalList.add(new SignalEvent(SignalEvent.KEY_EVENT, new int[] { SignalEvent.KeyEvent.ENTER }));
		}
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			signalList.add(new SignalEvent(SignalEvent.KEY_EVENT, new int[] { SignalEvent.KeyEvent.LEFT }));
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			signalList.add(new SignalEvent(SignalEvent.KEY_EVENT, new int[] { SignalEvent.KeyEvent.RIGHT }));
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			signalList.add(new SignalEvent(SignalEvent.KEY_EVENT, new int[] { SignalEvent.KeyEvent.UP }));
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			signalList.add(new SignalEvent(SignalEvent.KEY_EVENT, new int[] { SignalEvent.KeyEvent.DOWN }));
		}

//		Gdx.input.setInputProcessor(new InputAdapter() {
//			public boolean touchDown(int screenX, int screenY, int pointer, int button) {
//				switch (button) {
//				case Buttons.LEFT:
//					signalList.add(new SignalEvent(SignalEvent.MOUSE_EVENT, new int[] { SignalEvent.MouseEvent.LEFT, screenX, screenY }));
//					break;
//				case Buttons.RIGHT:
//					signalList.add(new SignalEvent(SignalEvent.MOUSE_EVENT, new int[] { SignalEvent.MouseEvent.RIGHT, screenX, screenY }));
//					break;
//				}
//				return true;
//			}
//		});

		ViewManager.get().signal(signalList);

		batch.begin();
		renderList = ViewManager.get().render();
		for (RenderEvent e : renderList) {
			switch (e.type) {
			case RenderEvent.IMAGE:
				batch.draw(e.texture, e.x, e.y);
				break;
			case RenderEvent.LABEL:
				gameLable.draw(batch, e.s, Bootstrap.screamWidth() / 2f, Bootstrap.screamHeight() / 2f);
				break;
			}
		}
		batch.end();
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
