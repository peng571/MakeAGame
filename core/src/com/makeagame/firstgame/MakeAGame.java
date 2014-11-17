package com.makeagame.firstgame;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.google.gson.Gson;
import com.makeagame.core.Bootstrap;
import com.makeagame.core.Controler;
import com.makeagame.core.ResourceManager;
import com.makeagame.core.model.ModelManager;
import com.makeagame.core.model.Model;
import com.makeagame.core.view.RenderEvent;
import com.makeagame.core.view.SignalEvent;
import com.makeagame.core.view.View;
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
		mManager.add("main", new GameModel());

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
				batch.draw(e.texture, e.x, e.y);
				break;
			case RenderEvent.LABEL:
				gameLable.draw(batch, e.s, Bootstrap.screamWidth() / 2f, Bootstrap.screamHeight() / 2f);
				break;
			}
		}
		batch.end();
	}

	class GameView implements View {

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

				list.add(new RenderEvent(resource.fetch("cat"), hold.cat.x, hold.cat.y));
				for (Hold.Human human : hold.humans) {
					list.add(new RenderEvent(resource.fetch("human"), human.x, human.y));
				}
				list.add(new RenderEvent(hold.text, 200, 200));
			}
			return list;
		}

		@Override
		public String info() {
			return "main view";
		}

	}

	class GameModel implements Model {

		long startTime = 0;
		long countTime = 0;
		boolean reseting = false, running = false;
		int countDown = 0;

		CatModel cat;
		ArrayList<HumanModel> humans;

		public GameModel() {
			cat = new CatModel();
			humans = new ArrayList<HumanModel>();
		}

		class RoleModel {
			float x, y;
			float speedX = 0, speedY = 0;
			float maxSpeedX, maxSpeedY;
			float a;
			int w, h;

			public RoleModel init(String gsonString) {
				RoleModel model = new Gson().fromJson(gsonString, RoleModel.class);
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
		}

		class HumanModel extends RoleModel {

			public HumanModel init(String gsonString) {
				super.init(gsonString);
				return this;
			}

			public void move(CatModel cat) {
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
				} else if (x + w > Bootstrap.screamWidth()) {
					x = Bootstrap.screamWidth() - w;
				}
				if (y < 0) {
					y = 0f;
				} else if (y + h > Bootstrap.screamWidth()) {
					y = Bootstrap.screamWidth() - h;
				}
			}
		}

		class CatModel extends RoleModel {
			boolean alive;

			@Override
			public CatModel init(String gsonString) {
				super.init(gsonString);
				alive = true;
				return this;
			}

			public void move(Sign signs) {
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
				} else if (x + w > Bootstrap.screamWidth()) {
					x = Bootstrap.screamWidth() - w;
				}
				if (y < 0) {
					y = 0f;
				} else if (y + h > Bootstrap.screamHeight()) {
					y = Bootstrap.screamWidth() - h;
				}
			}
		}

		@Override
		public void process(String gsonString) {
			Sign signs = new Gson().fromJson(gsonString, Sign.class);
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
					if (!cat.alive) {
						running = false;
					}

					cat.move(signs);
					for (HumanModel human : humans) {
						human.move(cat);
						if ((cat.x > human.x - human.w / 2 && cat.x < human.x + human.w / 2) && (cat.y > human.y - human.h / 2 && cat.y < human.y + human.h / 2)) {
							System.out.println("game over");
							cat.alive = false;
						}
					}

					countTime = System.currentTimeMillis() - startTime;
					if (countTime > 3000 * humans.size()) {
						humans.add(new HumanModel().init(resource.reed("human")));
					}

				}
			} else {
				if (signs.enter) {
					start();
				}
			}
		}

		private void start() {
			reseting = true;
			running = true;
			startTime = System.currentTimeMillis();
			cat.init(resource.reed("cat"));
			humans.clear();
			humans.add(new HumanModel().init(resource.reed("human")));
		}

		@Override
		public String hold() {
			Hold hold = new Hold();
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
			hold.cat.x = cat.x;
			hold.cat.y = cat.y;
			hold.humans = new Hold.Human[humans.size()];
			for (int i = 0; i < humans.size(); i++) {
				hold.humans[i].x = humans.get(i).x;
				hold.humans[i].y = humans.get(i).y;
			}
			hold.text = text;
			return new Gson().toJson(hold);
		}

		@Override
		public String info() {
			return "timmer model";
		}

		@Override
		public GameModel init(String gsonString) {
			return this;
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
		String text;

		class Cat {
			float x;
			float y;
		}

		Cat cat = new Cat();

		public class Human {
			float x;
			float y;
		}

		Human[] humans;
	}

}
