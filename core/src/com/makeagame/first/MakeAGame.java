package com.makeagame.first;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.google.gson.Gson;
import com.makeagame.core.Controler;
import com.makeagame.core.ResourceManager;
import com.makeagame.core.model.ModelManager;
import com.makeagame.core.model.TopModel;
import com.makeagame.core.view.RenderEvent;
import com.makeagame.core.view.SignalEvent;
import com.makeagame.core.view.TopView;
import com.makeagame.core.view.ViewManager;

public class MakeAGame extends ApplicationAdapter {

	SpriteBatch batch;
	ViewManager vManager;
	ModelManager mManager;
	ResourceManager resource;
	Controler controler;
	BitmapFont gameLable, timmer;

	@Override
	public void create() {

		System.out.println("game start");

		batch = new SpriteBatch();
		vManager = ViewManager.get();
		mManager = ModelManager.get();
		controler = Controler.get();
		resource = ResourceManager.get();

		resource.bind("cat", "image/pussy.png", "data/cat.txt");
		resource.bind("human", "image/person91.png", "data/human.txt");
		vManager.add("main", new GameView());
		mManager.add("timmer", new TimmerModel());

		timmer = new BitmapFont();
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
		vManager.signal(signalList);

		batch.begin();
		ArrayList<RenderEvent> renderList = vManager.render();
		for (RenderEvent e : renderList) {
			switch (e.type) {
			case RenderEvent.IMAGE:
				batch.draw(resource.fetch(e.s), e.x, e.y);
				break;
			case RenderEvent.LABEL:
				gameLable.draw(batch, e.s, Config.screamWidth() / 2f, Config.screamHeight() / 2f);
				break;
			}
		}
		// timmer.draw(batch, String.valueOf(countTime), 10, 10);
		batch.end();
	}

	class GameView implements TopView {

		@Override
		public void signal(ArrayList<SignalEvent> signalList) {
			Sign sign = new Sign();
			sign.left = false;
			sign.up = false;
			sign.down = false;
			sign.right = false;
			sign.enter = false;
			for (SignalEvent s : signalList) {
				if (s.equals(new SignalEvent(SignalEvent.KEY_EVENT, SignalEvent.ACTION_DOWN, new Object[] { "enter" }))) {
					sign.enter = true;
				}
				if (s.equals(new SignalEvent(SignalEvent.KEY_EVENT, SignalEvent.ACTION_DOWN, new Object[] { "left" }))) {
					sign.left = true;
				}
				if (s.equals(new SignalEvent(SignalEvent.KEY_EVENT, SignalEvent.ACTION_DOWN, new Object[] { "up" }))) {
					sign.up = true;
				}
				if (s.equals(new SignalEvent(SignalEvent.KEY_EVENT, SignalEvent.ACTION_DOWN, new Object[] { "down" }))) {
					sign.down = true;
				}
				if (s.equals(new SignalEvent(SignalEvent.KEY_EVENT, SignalEvent.ACTION_DOWN, new Object[] { "right" }))) {
					sign.right = true;
				}
			}
			controler.call("timmer", new Gson().toJson(sign));
		}

		@Override
		public ArrayList<RenderEvent> render(ArrayList<String> build) {
			ArrayList<RenderEvent> list = new ArrayList<RenderEvent>();
			for (String s : build) {
				Hold hold = new Gson().fromJson(s, Hold.class);
				if (hold.type.equals("image")) {
					// 畫圖片
					list.add(new RenderEvent(RenderEvent.IMAGE, hold.id, hold.x, hold.y));
				} else {
					// 畫文字
					if (hold.id.equals("timmer")) {
						list.add(new RenderEvent(RenderEvent.LABEL, hold.text, hold.x, hold.y));
					}
				}
			}
			return list;
		}

		@Override
		public String info() {
			return "main view";
		}

	}

	class TimmerModel implements TopModel {

		long startTime = 0;
		long countTime = 0;
		boolean reseting = false, running = false;
		int countDown = 0;

		@Override
		public void process(String gsonString) {

			if (running) {
				if (reseting) {
					if (System.currentTimeMillis() - startTime < 1000) {
						countDown = 3;
					} else if (System.currentTimeMillis() - startTime < 2000) {
						countDown = 2;
					} else if (System.currentTimeMillis() - startTime < 3000) {
						countDown = 1;
					} else if (System.currentTimeMillis() - startTime < 4000) {
						countDown = 0;
					} else {
						countDown = -1;
						startTime = System.currentTimeMillis();
						reseting = false;
					}
				} else {
					Hold cat = new Gson().fromJson(mManager.get("cat").hold(), Hold.class);
					if (!cat.alive) {
						running = false;
					}
					controler.call("cat", gsonString);
					controler.call("human", "");

					countTime = System.currentTimeMillis() - startTime;
					if (countTime > 3000 * mManager.getGroup("human").size()) {
						mManager.add("human", new HumanModel().init(resource.reed("human")), true);
					}

				}
			} else {
				Sign signs = new Gson().fromJson(gsonString, Sign.class);
				if (signs.enter) {
					start();
				}
			}
		}

		private void start() {
			reseting = true;
			running = true;
			startTime = System.currentTimeMillis();
			mManager.remove("cat");
			mManager.add("cat", new CatModel().init(resource.reed("cat")));
			mManager.remove("human");
			mManager.add("human", new HumanModel().init(resource.reed("human")), true);

		}

		@Override
		public String hold() {
			Hold hold = new Hold();
			hold.id = "timmer";
			hold.type = "text";
			String text = "";
			if (reseting) {
				if (countDown == -1) {
					text = "";
				} else if (countDown == 0) {
					text = "start!!!";
				} else {
					text = String.valueOf(countDown);
				}
			} else if (!running) {
				text = "press Enter to start.";
			}
			hold.text = text;
			return new Gson().toJson(hold);
		}

		@Override
		public String info() {
			return "timmer model";
		}

		@Override
		public TimmerModel init(String gsonString) {
			return this;
		}

	}

	class HumanModel implements TopModel {

		float x = 0, y = 0;
		float speedX = 0, speedY = 0;
		float maxSpeedX = 4f, maxSpeedY = 4f;
		float a = 0.4f;
		int w = 32, h = 32;

		@Override
		public HumanModel init(String gsonString) {
			HumanModel model = new Gson().fromJson(gsonString, HumanModel.class);
			speedX = 0;
			speedY = 0;
			this.x = model.x;
			this.y = model.y;
			Random rand = new Random();
			this.maxSpeedX = model.maxSpeedX + ((rand.nextInt(20) - 10) * 0.1f);
			this.maxSpeedY = model.maxSpeedY + ((rand.nextInt(20) - 10) * 0.1f);
			this.a = model.a + ((rand.nextInt(10) - 5) * 0.02f);
			this.w = model.w;
			this.h = model.h;
			return this;
		}

		@Override
		public void process(String gsonString) {
			Hold cat = new Gson().fromJson(mManager.get("cat").hold(), Hold.class);

			if (x > cat.x) {
				speedX -= a;
			} else if (x < cat.x) {
				speedX += a;
			}
			if (y > cat.y) {
				speedY -= a;
			} else if (y < cat.y) {
				speedY += a;
			}

			if (speedX > maxSpeedX) {
				speedX = maxSpeedX;
			} else if (speedX < -maxSpeedX) {
				speedX = -maxSpeedX;
			}
			if (speedY > maxSpeedY) {
				speedY = maxSpeedY;
			} else if (speedY < -maxSpeedY) {
				speedY = -maxSpeedY;
			}

			x += speedX;
			y += speedY;
			if (x < 0) {
				x = 0f;
			} else if (x + w > Config.screamWidth()) {
				x = Config.screamWidth() - w;
			}
			if (y < 0) {
				y = 0f;
			} else if (y + h > Config.screamWidth()) {
				y = Config.screamWidth() - h;
			}
		}

		@Override
		public String hold() {
			Hold hold = new Hold();
			hold.type = "image";
			hold.id = "human";
			hold.x = x;
			hold.y = y;
			hold.w = w;
			hold.h = h;
			return new Gson().toJson(hold);
		}

		@Override
		public String info() {
			return "human model";
		}

	}

	class CatModel implements TopModel {
		boolean alive = true;
		float x, y;
		float speedX, speedY;
		float maxSpeedX, maxSpeedY;
		float a;
		int w, h;

		@Override
		public CatModel init(String gsonString) {
			CatModel model = new Gson().fromJson(gsonString, CatModel.class);
			alive = true;
			speedX = 0;
			speedY = 0;
			this.x = model.x;
			this.y = model.y;
			this.maxSpeedX = model.maxSpeedX;
			this.maxSpeedY = model.maxSpeedY;
			this.a = model.a;
			this.w = model.w;
			this.h = model.h;
			return this;
		}

		@Override
		public void process(String gsonString) {
			Sign signs = new Gson().fromJson(gsonString, Sign.class);
			if (signs.left) {
				speedX -= a;
			} else if (speedX < 0) {
				speedX += a;
			}
			if (signs.right) {
				speedX += a;
			} else if (speedX > 0) {
				speedX -= a;
			}
			if (signs.up) {
				speedY += a;
			} else if (speedY > 0) {
				speedY -= a;
			}
			if (signs.down) {
				speedY -= a;
			} else if (speedY < 0) {
				speedY += a;
			}

			if (speedX > maxSpeedX) {
				speedX = maxSpeedX;
			} else if (speedX < -maxSpeedX) {
				speedX = -maxSpeedX;
			}
			if (speedY > maxSpeedY) {
				speedY = maxSpeedY;
			} else if (speedY < -maxSpeedY) {
				speedY = -maxSpeedY;
			}

			x += speedX;
			y += speedY;
			if (x < 0) {
				x = 0f;
			} else if (x + w > Config.screamWidth()) {
				x = Config.screamWidth() - w;
			}
			if (y < 0) {
				y = 0f;
			} else if (y + h > Config.screamHeight()) {
				y = Config.screamWidth() - h;
			}
			Hold human;
			for (int i = 0; i < mManager.getGroupNum("human"); i++) {
				if (mManager.get("human" + i) != null) {
					human = new Gson().fromJson(mManager.get("human" + i).hold(), Hold.class);
					if ((x > human.x - human.w / 2 && x < human.x + human.w / 2) && (y > human.y - human.h / 2 && y < human.y + human.h / 2)) {
						System.out.println("game over");
						alive = false;
					}
				}
			}
		}

		@Override
		public String hold() {
			Hold hold = new Hold();
			hold.id = "cat";
			hold.type = "image";
			hold.x = x;
			hold.y = y;
			hold.alive = alive;
			return new Gson().toJson(hold);
		}

		@Override
		public String info() {
			return "cat model";
		}
	}

	class Sign {
		boolean up;
		boolean down;
		boolean left;
		boolean right;
		boolean enter;

	}

	class Hold {
		String id;
		String type;
		String text;
		float x;
		float y;
		int w;
		int h;
		boolean alive;
	}

}
