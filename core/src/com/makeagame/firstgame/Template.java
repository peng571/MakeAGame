package com.makeagame.firstgame;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.makeagame.core.Bootstrap;
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

/**
 * Empty Game Template
 * 
 * @author Peng
 * 
 */
public class Template {

	private Engine engine;

	public Engine getEngine() {
		return engine;
	}

	public Template() {

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
				// TODO:
//				resource.bind("xx", new Resource().image("image/xx.png"));
			}
		});
	}

	class GameView implements View {

		int touchStartX, touchStartY;

		@Override
		public void signal(ArrayList<SignalEvent> signalList) {
			for (SignalEvent s : signalList) {
				if (s.type == SignalEvent.MOUSE_EVENT || s.type == SignalEvent.TOUCH_EVENT) {
					if (s.signal.press(KeyEvent.ANY_KEY) && s.action == SignalEvent.ACTION_DOWN) {
						touchStartX = s.signal.x;
						touchStartY = s.signal.y;
					}
					if (s.action == SignalEvent.ACTION_UP) {

					}
				}
			}
		}

		@Override
		public ArrayList<RenderEvent> render(ArrayList<String> build) {

			ArrayList<RenderEvent> list = new ArrayList<RenderEvent>();
			for (String s : build) {
				Hold hold = new Gson().fromJson(s, Hold.class);
			}
			return list;
		}

		@Override
		public String info() {
			return "main view";
		}

	}

	class GameModel implements Model {

		public GameModel() {
		}

		@Override
		public String hold() {
			Hold hold = new Hold();
			return new Gson().toJson(hold);
		}

		@Override
		public String info() {
			return "main model";
		}

		@Override
		public void process(int command, JSONObject json) throws JSONException {
			switch (command) {
			// TODO
			}

		}

	}

	class Sign {
	}

	class Hold {
	}

}
