package com.makeagame.first;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.google.gson.Gson;
import com.makeagame.core.Controler;
import com.makeagame.core.model.ModelManager;
import com.makeagame.core.model.TopModel;
import com.makeagame.core.view.RenderEvent;
import com.makeagame.core.view.SignalEvent;
import com.makeagame.core.view.TopView;
import com.makeagame.core.view.ViewManager;

public class MakeAGame extends ApplicationAdapter {

	// long countTime = 0, startTime = 0;
	// boolean reseting, running;
	SpriteBatch batch;
	// BitmapFont timmer, gameLable;
	// String lable;
	ViewManager vManager;
	ModelManager mManager;
	Controler controler;

	@Override
	public void create() {

		System.out.println("game start");

		batch = new SpriteBatch();
		vManager = ViewManager.get();
		mManager = ModelManager.get();
		controler = Controler.get();
		mManager.add("human", new HumanModel(), true);
		mManager.add("cat", new CatModel());
		mManager.add("timmer", new TimmerModel());
		vManager.add("main", new GameView());

		// timmer = new BitmapFont();
		// gameLable = new BitmapFont();
		// gameLable.setColor(new Color(1, 0, 0, 1));

	}

	// public void start() {
	// reseting = true;
	// running = true;
	// startTime = System.currentTimeMillis();
	// cat.reset();
	// humans.clear();
	// humans.add(new HumanRole(new Texture("person91.png")));
	//
	// }

	@Override
	public void render() {
		// if (running) {
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

		

		// Controler.get().loop();

		batch.begin();
		ArrayList<RenderEvent> renderList = vManager.render();
		for (RenderEvent e : renderList) {
			if (e.type == RenderEvent.IMAGE) {
				batch.draw(new Texture(e.s), e.x, e.y);
			}
		}

		//
		// timmer.draw(batch, String.valueOf(countTime), 10, 10);
		// gameLable.draw(batch, lable, Config.screamWidth() / 2f,
		// Config.screamHeight() / 2f);
		//
		// batch.draw(cat.image, cat.x, cat.y);
		// for (GameRole human : humans) {
		// batch.draw(human.image, human.x, human.y);
		// }
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
			controler.call("cat", new Gson().toJson(sign));
			controler.call("human", "");
		}

		@Override
		public ArrayList<RenderEvent> render(ArrayList<String> build) {
			ArrayList<RenderEvent> list = new ArrayList<RenderEvent>();
			for (String s : build) {
				Hold hold = new Gson().fromJson(s, Hold.class);
				if (hold.type.equals("image")) {
					String image = "";
					if (hold.id.equals("cat")) {
						image = "pussy.png";
					} else if (hold.id.equals("human")) {
						image = "group9.png";
					}
					list.add(new RenderEvent(RenderEvent.IMAGE, image, hold.x, hold.y));
				} else {
					if (hold.id.equals("timmer")) {
						list.add(new RenderEvent(RenderEvent.LABEL, "", hold.x, hold.y));
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

		@Override
		public void process(String gsonString) {

			// // timmer
			// if (reseting) {
			// if (System.currentTimeMillis() - startTime < 1000) {
			// lable = "3";
			// } else if (System.currentTimeMillis() - startTime < 2000) {
			// lable = "2";
			// } else if (System.currentTimeMillis() - startTime < 3000) {
			// lable = "1";
			// } else if (System.currentTimeMillis() - startTime < 4000) {
			// lable = "Start!!";
			// } else {
			// lable = "";
			// startTime = System.currentTimeMillis();
			// reseting = false;
			// }
			// } else {
			// countTime = System.currentTimeMillis() - startTime;
			// if(countTime > 3000 * humans.size())
			// {
			// humans.add(new HumanRole(new Texture("group9.png")));
			// }
			//
			//
			// for (GameRole human : humans) {
			// if ((cat.x > human.x - human.w / 2 && cat.x < human.x + human.w / 2)
			// && (cat.y > human.y - human.h / 2 && cat.y < human.y + human.h / 2))
			// {
			// lable = "Game Over...";
			// running = false;
			// }
			// }
			//
			//
			// } else {
			// lable = "press Enter to start.";
			// if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
			// start();
			// }
			// }
			
			if (!running) {
				Sign signs = new Gson().fromJson(gsonString, Sign.class);
				if (signs.enter) {
					startTime = System.currentTimeMillis();
					running = true;
					reseting = true;
				}
			} else {
				

			}

		}

		@Override
		public String hold() {
			return null;
		}

		@Override
		public String info() {
			return "timmer model";
		}

	}

	class HumanModel implements TopModel {

		float x = 0, y = 0;
		float speedX = 0, speedY = 0;
		float maxSpeedX = 4f, maxSpeedY = 4f;
		float a = 0.4f;
		int w = 32, h = 32;

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
			return new Gson().toJson(hold);
		}

		@Override
		public String info() {
			return "human model";
		}

	}

	class CatModel implements TopModel {
		float x = 0, y = 0;
		float speedX = 0, speedY = 0;
		float maxSpeedX = 3f, maxSpeedY = 3f;
		float a = 0.3f;
		int w = 32, h = 32;

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

		}

		@Override
		public String hold() {
			Hold hold = new Hold();
			hold.id = "cat";
			hold.type = "image";
			hold.x = x;
			hold.y = y;
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
	}

}
