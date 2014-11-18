package com.makeagame.firstgame;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import com.google.gson.Gson;
import com.makeagame.core.Bootstrap;
import com.makeagame.core.Controler;
import com.makeagame.core.Engine;
import com.makeagame.core.ResourceManager;
import com.makeagame.core.model.Model;
import com.makeagame.core.model.ModelManager;
import com.makeagame.core.view.RenderEvent;
import com.makeagame.core.view.SignalEvent;
import com.makeagame.core.view.View;
import com.makeagame.core.view.ViewManager;

public class MakeAGame {

	private Engine engine;

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
				resource.bind("cat", "image/pussy.png", "data/cat.txt");
				resource.bind("human", "image/person91.png", "data/human.txt");
			}

		});
	}

	public Engine getEngine() {
		return engine;
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
			Controler.get().call("main", new Gson().toJson(sign));
		}

		@Override
		public ArrayList<RenderEvent> render(ArrayList<String> build) {
			ArrayList<RenderEvent> list = new ArrayList<RenderEvent>();
			for (String s : build) {
				Hold hold = new Gson().fromJson(s, Hold.class);

				list.add(new RenderEvent(ResourceManager.get().fetch("cat"), hold.cat.x, hold.cat.y));
				for (Poistion human : hold.humans) {
					list.add(new RenderEvent(ResourceManager.get().fetch("human"), human.x, human.y));
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
				} else if (y + h > Bootstrap.screamHeight()) {
					y = Bootstrap.screamHeight() - h;
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
					y = Bootstrap.screamHeight() - h;
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
						humans.add(new HumanModel().init(ResourceManager.get().reed("human")));
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
			cat.init(ResourceManager.get().reed("cat"));
			humans.clear();
			humans.add(new HumanModel().init(ResourceManager.get().reed("human")));
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
			for (int i = 0; i < humans.size(); i++) {
				Poistion p = new Poistion();
				p.x = humans.get(i).x;
				p.y = humans.get(i).y;
				hold.humans.add(p);
			}
			hold.text = text;
			return new Gson().toJson(hold);
		}

		@Override
		public String info() {
			return "main model";
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
		Poistion cat = new Poistion();
		ArrayList<Poistion> humans = new ArrayList<Poistion>();
	}

	class Poistion {
		float x;
		float y;
	}

}
