package com.makeagame.magerevenge;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.google.gson.Gson;
import com.makeagame.core.Bootstrap;
import com.makeagame.core.Controler;
import com.makeagame.core.Engine;
import com.makeagame.core.model.Model;
import com.makeagame.core.model.ModelManager;
import com.makeagame.core.resource.Resource;
import com.makeagame.core.resource.ResourceManager;
import com.makeagame.core.view.RenderEvent;
import com.makeagame.core.view.SignalEvent;
import com.makeagame.core.view.SignalEvent.KeyEvent;
import com.makeagame.core.view.View;
import com.makeagame.core.view.ViewManager;

public class MakeAGame {

	private Engine engine;

	public static String ROLE_1 = "ball1";
	public static String ROLE_2 = "ball2";
	public static String ROLE_3 = "ball3";

	public MakeAGame() {

		engine = new Engine(new Bootstrap() {

			@Override
			public void viewFactory(ViewManager manager) {
				manager.add("main", new GameView());
			}

			@Override
			public void modelFactory(ModelManager manager) {
				manager.add("main", new GameModel());
			}

			@Override
			public void resourceFactory(ResourceManager resource) {
				resource.bind(ROLE_1, new Resource().image("image/pussy.png").attribute("data/role1.txt"));
				resource.bind(ROLE_2, new Resource().image("image/person91.png").attribute("data/role2.txt"));
				resource.bind(ROLE_3, new Resource().image("image/group9.png").attribute("data/role3.txt"));
				resource.bind("castle", new Resource().image("image/pear4.png").attribute("data/castle.txt"));
				resource.bind(ROLE_1 + "btn", new Resource().image("image/black.png"));
				resource.bind(ROLE_2 + "btn", new Resource().image("image/blue.png"));
				resource.bind(ROLE_3 + "btn", new Resource().image("image/grey.png"));
			}
		});
	}

	public Engine getEngine() {
		return engine;
	}
}
