package com.makeagame.first;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.JsonValue;
import com.makeagame.core.Controler;
import com.makeagame.core.model.TopModel;
import com.makeagame.core.model.ModelManager;
import com.makeagame.core.view.RenderEvent;
import com.makeagame.core.view.SignalEvent;
import com.makeagame.core.view.TopView;
import com.makeagame.core.view.ViewManager;

public class MakeAGame extends ApplicationAdapter {

	// long countTime = 0, startTime = 0;
	// boolean reseting, running;
	SpriteBatch batch;
	// GameRole cat;
	// ArrayList<GameRole> humans;
	// BitmapFont timmer, gameLable;
	// String lable;
	ViewManager vManager;
	ModelManager mManager;
	Controler controler;

	@Override
	public void create() {

		batch = new SpriteBatch();
		vManager = ViewManager.get();
		mManager = ModelManager.get();
		controler=  Controler.get();
		vManager.add("cat", new TopView() {

			@Override
			public void signal(ArrayList<SignalEvent> s) {
				if (s.equals(new SignalEvent(SignalEvent.KEY_EVENT, SignalEvent.ACTION_DOWN, new Object[] { "left" }))) {
					controler.call("cat", new Object[] { "left" });
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
			}

			@Override
			public ArrayList<RenderEvent> render() {
				return null;
			}

		});
		// cat = new CatRole(new Texture("pussy.png"));
		// // human = new HumanRole(new Texture("group9.png"));
		// humans = new ArrayList<GameRole>();
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

//		Controler.get().loop();

		batch.begin();
		ArrayList<RenderEvent> renderList = vManager.render();
		for (RenderEvent e : renderList) {
			// batch.draw();
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

	class HumanRole extends GameRole {
		HumanRole(Texture image) {
			super(image);
			x = 0;
			y = 0;
			Random rand = new Random();
			maxSpeedX = 3f + ((rand.nextInt(20) - 10) * 0.1f);
			maxSpeedY = 3f + ((rand.nextInt(20) - 10) * 0.1f);
			a = 0.3f + ((rand.nextInt(10) - 5) * 0.02f);
			;
			w = 32;
			h = 32;

		}

		void process() {

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
	}

	class CatRole extends GameRole {

		CatRole(Texture image) {
			super(image);
			x = Config.screamWidth() / 2f;
			y = Config.screamHeight() / 2f;
			maxSpeedX = 6f;
			maxSpeedY = 6f;
			a = 0.6f;
			w = 32;
			h = 32;
		}

		void reset() {
			x = Config.screamWidth() / 2f;
			y = Config.screamHeight() / 2f;
			speedX = 0;
			speedY = 0;
		}

		void process() {

			if (keyLeft) {
				speedX -= a;
			} else if (speedX < 0) {
				speedX += a;
			}
			if (keyRight) {
				speedX += a;
			} else if (speedX > 0) {
				speedX -= a;
			}
			if (keyUp) {
				speedY += a;
			} else if (speedY > 0) {
				speedY -= a;
			}
			if (keyDown) {
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
	}

	abstract class GameRole implements TopModel {
		float x, y;
		float speedX, speedY;
		float maxSpeedX, maxSpeedY;
		float a;
		int w, h;

		// GameRole setFromJSON(JsonValue json) {
		// return this;
		// }

		// 改變內部行為
		abstract void process(Object[] signs);

		// 完整交出內部
		abstract Object hold();

		void reset() {
			x = 0;
			y = 0;
			speedX = 0;
			speedY = 0;
		}
	}

}

// public class MakeAGame extends ApplicationAdapter {
//
// long countTime = 0, startTime = 0;
// boolean reseting, running;
// SpriteBatch batch;
// GameRole cat;
// ArrayList<GameRole> humans;
// BitmapFont timmer, gameLable;
// String lable;
// public static boolean keyLeft, keyDown, keyRight, keyUp;

// @Override
// public void create() {
// batch = new SpriteBatch();
// cat = new CatRole(new Texture("pussy.png"));
// // human = new HumanRole(new Texture("group9.png"));
// humans = new ArrayList<GameRole>();
// timmer = new BitmapFont();
// gameLable = new BitmapFont();
// gameLable.setColor(new Color(1, 0, 0, 1));

// }

// public void start() {
// reseting = true;
// running = true;
// startTime = System.currentTimeMillis();
// cat.reset();
// humans.clear();
// humans.add(new HumanRole(new Texture("person91.png")));
//
// }

// @Override
// public void render() {
// if (running) {
// Gdx.gl.glClearColor(1, 1, 1, 1);
// Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
// if ((cat.x > human.x - human.w / 2 && cat.x < human.x + human.w / 2) &&
// (cat.y > human.y - human.h / 2 && cat.y < human.y + human.h / 2)) {
// lable = "Game Over...";
// running = false;
// }
// }
//
// // keybroad
// if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
// keyLeft = true;
// } else {
// keyLeft = false;
// }
// if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
// keyRight = true;
// } else {
// keyRight = false;
// }
// if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
// keyUp = true;
// } else {
// keyUp = false;
// }
// if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
// keyDown = true;
// } else {
// keyDown = false;
// }
//
// cat.move();
// for (GameRole human : humans) {
// human.move();
// }
// }
//
// } else {
// lable = "press Enter to start.";
// if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
// start();
// }
// }
// batch.begin();
//
// timmer.draw(batch, String.valueOf(countTime), 10, 10);
// gameLable.draw(batch, lable, Config.screamWidth() / 2f, Config.screamHeight()
// / 2f);
//
// batch.draw(cat.image, cat.x, cat.y);
// for (GameRole human : humans) {
// batch.draw(human.image, human.x, human.y);
// }
// batch.end();
// }

// class HumanRole extends GameRole {
// HumanRole(Texture image) {
// super(image);
// x = 0;
// y = 0;
// Random rand = new Random();
// maxSpeedX = 3f + (( rand.nextInt(20) -10 ) * 0.1f);
// maxSpeedY = 3f + (( rand.nextInt(20) -10 ) * 0.1f);
// a = 0.3f + (( rand.nextInt(10) -5 ) * 0.02f);;
// w = 32;
// h = 32;
//
// }
//
// void move() {
//
// if (x > cat.x) {
// speedX -= a;
// } else if (x < cat.x) {
// speedX += a;
// }
// if (y > cat.y) {
// speedY -= a;
// } else if (y < cat.y) {
// speedY += a;
// }
//
// if (speedX > maxSpeedX) {
// speedX = maxSpeedX;
// } else if (speedX < -maxSpeedX) {
// speedX = -maxSpeedX;
// }
// if (speedY > maxSpeedY) {
// speedY = maxSpeedY;
// } else if (speedY < -maxSpeedY) {
// speedY = -maxSpeedY;
// }
//
// x += speedX;
// y += speedY;
// if (x < 0) {
// x = 0f;
// } else if (x + w > Config.screamWidth()) {
// x = Config.screamWidth() - w;
// }
// if (y < 0) {
// y = 0f;
// } else if (y + h > Config.screamWidth()) {
// y = Config.screamWidth() - h;
// }
// }
// }
//
// class CatRole extends GameRole {
//
// CatRole(Texture image) {
// super(image);
// x = Config.screamWidth() / 2f;
// y = Config.screamHeight() / 2f;
// maxSpeedX = 6f;
// maxSpeedY = 6f;
// a = 0.6f;
// w = 32;
// h = 32;
// }
//
// void reset() {
// x = Config.screamWidth() / 2f;
// y = Config.screamHeight() / 2f;
// speedX = 0;
// speedY = 0;
// }
//
// void move() {
//
// if (keyLeft) {
// speedX -= a;
// } else if (speedX < 0) {
// speedX += a;
// }
// if (keyRight) {
// speedX += a;
// } else if (speedX > 0) {
// speedX -= a;
// }
// if (keyUp) {
// speedY += a;
// } else if (speedY > 0) {
// speedY -= a;
// }
// if (keyDown) {
// speedY -= a;
// } else if (speedY < 0) {
// speedY += a;
// }
//
// if (speedX > maxSpeedX) {
// speedX = maxSpeedX;
// } else if (speedX < -maxSpeedX) {
// speedX = -maxSpeedX;
// }
// if (speedY > maxSpeedY) {
// speedY = maxSpeedY;
// } else if (speedY < -maxSpeedY) {
// speedY = -maxSpeedY;
// }
//
// x += speedX;
// y += speedY;
// if (x < 0) {
// x = 0f;
// } else if (x + w > Config.screamWidth()) {
// x = Config.screamWidth() - w;
// }
// if (y < 0) {
// y = 0f;
// } else if (y + h > Config.screamHeight()) {
// y = Config.screamWidth() - h;
// }
// }
// }
//
// abstract class GameRole {
// Texture image;
// float x, y;
// float speedX, speedY;
// float maxSpeedX, maxSpeedY;
// float a;
// int w, h;
//
// GameRole(Texture image) {
// this.image = image;
// }
//
// abstract void move();
//
// void reset() {
// x = 0;
// y = 0;
// speedX = 0;
// speedY = 0;
// }
//
// }
// }

