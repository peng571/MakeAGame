package com.makeagame.core;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.makeagame.core.model.ModelManager;
import com.makeagame.core.view.RenderEvent;
import com.makeagame.core.view.SignalEvent;
import com.makeagame.core.view.ViewManager;

public class Engine extends ApplicationAdapter {

	SpriteBatch batch;
	BitmapFont gameLable;
	Bootstrap bootstrap;

	public Engine(Bootstrap bootstrap) {
		this.bootstrap = bootstrap;
		bootstrap.resourceFactory(ResourceManager.get());
		bootstrap.viewFactory(ViewManager.get());
		bootstrap.modelFactory(ModelManager.get());

	}

	public int getWidth() {
		return bootstrap.screamWidth();
	}

	public int getHeight() {
		return bootstrap.screamHeight();
	}

	@Override
	public void create() {
		System.out.println("game start");

		gameLable = new BitmapFont();
		gameLable.setColor(new Color(1, 0, 0, 1));

	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// keybroad
		ArrayList<SignalEvent> signalList = new ArrayList<SignalEvent>();
		if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
			signalList.add(new SignalEvent(SignalEvent.KEY_EVENT, SignalEvent.ACTION_DOWN, new Object[] { "enter" }));
		}
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			signalList.add(new SignalEvent(SignalEvent.KEY_EVENT, SignalEvent.ACTION_DOWN, new Object[] { "left" }));
		} else {
			signalList.add(new SignalEvent(SignalEvent.KEY_EVENT, SignalEvent.ACTION_UP, new Object[] { "left" }));
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			signalList.add(new SignalEvent(SignalEvent.KEY_EVENT, SignalEvent.ACTION_DOWN, new Object[] { "right" }));
		} else {
			signalList.add(new SignalEvent(SignalEvent.KEY_EVENT, SignalEvent.ACTION_UP, new Object[] { "right" }));
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			signalList.add(new SignalEvent(SignalEvent.KEY_EVENT, SignalEvent.ACTION_DOWN, new Object[] { "up" }));
		} else {
			signalList.add(new SignalEvent(SignalEvent.KEY_EVENT, SignalEvent.ACTION_UP, new Object[] { "up" }));
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			signalList.add(new SignalEvent(SignalEvent.KEY_EVENT, SignalEvent.ACTION_DOWN, new Object[] { "down" }));
		} else {
			signalList.add(new SignalEvent(SignalEvent.KEY_EVENT, SignalEvent.ACTION_UP, new Object[] { "down" }));
		}
		ViewManager.get().signal(signalList);

		batch.begin();
		ArrayList<RenderEvent> renderList = ViewManager.get().render();
		for (RenderEvent e : renderList) {
			switch (e.type) {
			case RenderEvent.IMAGE:
				batch.draw(e.texture, e.x, e.y);
				break;
			case RenderEvent.LABEL:
				gameLable.draw(batch, e.s, getWidth() / 2f, getHeight() / 2f);
				break;
			}
		}
		batch.end();
	}

}
