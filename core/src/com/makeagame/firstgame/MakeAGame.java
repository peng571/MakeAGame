package com.makeagame.firstgame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.google.gson.Gson;
import com.makeagame.core.Bootstrap;
import com.makeagame.core.Controler;
import com.makeagame.core.Engine;
import com.makeagame.core.model.Model;
import com.makeagame.core.model.ModelManager;
import com.makeagame.core.model.MovableModel;
import com.makeagame.core.resource.Resource;
import com.makeagame.core.resource.ResourceManager;
import com.makeagame.core.view.RenderEvent;
import com.makeagame.core.view.SignalEvent;
import com.makeagame.core.view.View;
import com.makeagame.core.view.ViewManager;

public class MakeAGame {

	private Engine engine;

	public Engine getEngine() {
		return engine;
	}

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
				resource.bind("pear", new Resource().image("image/pear4.png"));
				resource.bind("pear2", new Resource().image("image/avocado.png"));
				resource.bind("banana", new Resource().image("image/banana4.png"));
				resource.bind("banana2", new Resource().image("image/banana7.png"));
				resource.bind("boom", new Resource().image("image/boom3.png"));
				resource.bind("bird", new Resource().image("image/bird.png").attribute("data/bird.txt"));
				resource.bind("fruit", new Resource().attribute("data/fruit.txt"));
			}
		});
	}

	class GameView implements View {

		int mouseX, mouseY;
		int touchStartX, touchStartY;

		@Override
		public void signal(ArrayList<SignalEvent> signalList) {
			Sign sign = new Sign();
			for (SignalEvent s : signalList) {
				if (s.type == SignalEvent.MOUSE_EVENT || s.type == SignalEvent.TOUCH_EVENT) {
					if (s.action == SignalEvent.ACTION_DRAG) {
						sign.x = s.signal.x;
						sign.y = s.signal.y;
						// System.out.println("touch x " + sign.x + ", y" + sign.y);
					}
				}
			}
			Controler.get().call("main", new Gson().toJson(sign));
		}

		@Override
		public ArrayList<RenderEvent> render(ArrayList<String> build) {

			ArrayList<RenderEvent> list = new ArrayList<RenderEvent>();
			for (String s : build) {
				Hold hold = new Gson().fromJson(s, Hold.class);
				list.add(new RenderEvent(ResourceManager.get().fetch("bird")).XY(hold.x, hold.y).srcWH(128, 128).Ratio(0.6f).Rotation(hold.angle));
				for (Fruit f : hold.fruits) {
					if (f.flying) {
						list.add(new RenderEvent(ResourceManager.get().fetch(f.type)).XY(f.x, f.y).srcWH(128, 128).Ratio(0.8f));
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

	class GameModel implements Model {

		BirdModel bird;
		HashMap<String, FruitModel> fruits;
		long restTime = 500;
		long gameTime;

		public GameModel() {
			// bird = create(ResourceManager.get().read("bird"));
			bird = new BirdModel(ResourceManager.get().read("bird"));
			fruits = new HashMap<String, FruitModel>();
			fruits.put("pear", new FruitModel(ResourceManager.get().read("fruit")));
			fruits.put("banana", new FruitModel(ResourceManager.get().read("fruit")));
		}

		class FruitModel extends MovableModel {
			int level;
			int score;
			long shootTime;
			Random rand = new Random();
			boolean shooting = false;

			public FruitModel(String gson) {
				super(gson);
				model.sX += rand.nextInt(10) * 0.1f - 0.5f;
				level = 1;
				score = 5 * level;
			}

			@Override
			public void process(String gsonString) {
				if (shooting) {
					model.sY += model.aY;
					if (model.sY > model.maxSY) {
						model.sY = model.maxSY;
					} else if (model.sY < -model.maxSY) {
						model.sY = -model.maxSY;
					}

					model.x += model.sX;
					model.y += model.sY;
					// System.out.println(new Gson().toJson(model));

					if (model.y < 0) {
						model.y = 0;
					} else if (model.y - model.h > Bootstrap.screamHeight()) {
						model.y = Bootstrap.screamHeight() - model.h;
					}

					if (model.y > Bootstrap.screamHeight()) {
						shooting = false;
						model.x = model.initX;
						model.y = model.initY;
						model.sX = rand.nextInt(10) - 5;
						model.sY = model.initSY;
						shootTime = System.currentTimeMillis();
						System.out.println("fruit failed");
					}
				}
			}
		}

		class BirdModel extends MovableModel {
			boolean flying = false;
			boolean LtoR = true;
			long flyTime;
			float angle;

			public BirdModel(String gson) {
				super(gson);
				angle = 0;
			}

			@Override
			public void process(String gsonString) {
				Sign signs = new Gson().fromJson(gsonString, Sign.class);
				if (!flying) {
					if (System.currentTimeMillis() - flyTime > restTime) {
						if (signs.x != 0 && signs.y != 0) {
							flying = true;
							System.out.println("start flying");
						}
					}
				} else {
					if (signs.x != 0 && signs.y != 0) {
						if (signs.x < model.x) {
							model.sX -= model.aX;
						} else if (signs.x > model.x) {
							model.sX += model.aX;
						}
						if (signs.y < model.y) {
							model.sY -= model.aY;
						} else if (signs.y > model.y) {
							model.sY += model.aY;
						}
					}
					if (LtoR) {
						if (model.sX > model.maxSX) {
							model.sX = model.maxSX;
						} else if (model.sX < model.minSX) {
							model.sX = model.minSX;
						}
					} else {
						if (model.sX < -model.maxSX) {
							model.sX = -model.maxSX;
						} else if (model.sX > -model.minSX) {
							model.sX = -model.minSX;
						}
					}
					if (model.sY > model.maxSY) {
						model.sY = model.maxSY;
					} else if (model.sY < -model.maxSY) {
						model.sY = -model.maxSY;
					}

					model.x += model.sX;
					model.y += model.sY;
					angle = (float) (-1 * Math.toDegrees(Math.sin(model.sY / model.sX)));
//					System.out.println("sin " + (model.sY / model.sX) + " = " + angle);
					// System.out.println(new Gson().toJson(m));

					if (model.y < 0) {
						model.y = 0;
					} else if (model.y > Bootstrap.screamHeight()) {
						model.y = Bootstrap.screamHeight() - model.h;
					}

					if (LtoR && model.x > Bootstrap.screamWidth() || (!LtoR && model.x < -model.w)) {
						flying = false;
						model.sX = ((LtoR) ? 1 : -1) * model.initSX;
						LtoR = !LtoR;
						model.y = model.initY;
						flyTime = System.currentTimeMillis();
						System.out.println("stop flying");
						ResourceManager.get().fetch("bird").flip(true, false);
					}
				}
			}
		}

		@Override
		public void process(String gsonString) {
			// Sign signs = new Gson().fromJson(gsonString, Sign.class);
			bird.process(gsonString);
			for (FruitModel f : fruits.values()) {
				if (System.currentTimeMillis() - f.shootTime > restTime) {
					f.shooting = true;
					System.out.println("shoot again");
				}
				f.process(null);
			}
		}

		@Override
		public String hold() {
			Hold hold = new Hold();
			hold.angle = bird.angle;
			hold.x = bird.model.x;
			hold.y = bird.model.y;
			hold.fruits = new ArrayList<Fruit>();
			for (String type : fruits.keySet()) {
				FruitModel f = fruits.get(type);
				hold.fruits.add(new Fruit(type, f.model.x, f.model.y, f.shooting));
			}
			return new Gson().toJson(hold);
		}

		@Override
		public String info() {
			return "main model";
		}

	}

	class Sign {
		int x;
		int y;
	}

	class Hold {
		int score;
		float angle;
		int x;
		int y;
		ArrayList<Fruit> fruits;
	}

	class Fruit {
		boolean flying;
		String type;
		int x;
		int y;

		public Fruit(String type, int x, int y, boolean f) {
			this.type = type;
			this.x = x;
			this.y = y;
			this.flying = f;
		}
	}
}
