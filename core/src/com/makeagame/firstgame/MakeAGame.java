package com.makeagame.firstgame;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Random;

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

	private static String ROLE_1 = "ball1";
	private static String ROLE_2 = "ball2";
	private static String ROLE_3 = "ball3";

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

	class GameView implements View {

		Button[] btnCallHeros;

		public GameView() {
			btnCallHeros = new Button[3];
			btnCallHeros[0] = new Button(ROLE_1, 80, 450, 64, 64);
			btnCallHeros[1] = new Button(ROLE_2, 180, 450, 64, 64);
			btnCallHeros[2] = new Button(ROLE_3, 280, 450, 64, 64);
		}

		@Override
		public void signal(ArrayList<SignalEvent> signalList) {
			Sign sign = new Sign();
			for (SignalEvent s : signalList) {
				if (s.type == SignalEvent.MOUSE_EVENT || s.type == SignalEvent.TOUCH_EVENT) {
					if (s.signal.press(KeyEvent.ANY_KEY) && s.action == SignalEvent.ACTION_DOWN) {
					}
					if (s.action == SignalEvent.ACTION_UP) {
					}

					for (Button b : btnCallHeros) {
						if (b.isClick(s)) {
							sign.clickBtn = b.id;
						}
					}
				}
			}
			Controler.get().call("main", new Gson().toJson(sign));
		}

		@Override
		public ArrayList<RenderEvent> render(ArrayList<String> build) {

			ArrayList<RenderEvent> list = new ArrayList<RenderEvent>();
			for (String s : build) {
				final Hold hold = new Gson().fromJson(s, Hold.class);
				// Engine.logI("get hold " + s);
				for (RoleHold r : hold.roles) {
					list.add(new RenderEvent(ResourceManager.get().fetch(r.id)).XY(r.x - (r.group == 0 ? 32 : 0), 300).srcWH(32, 32)); // .filp(r.group == 1, false)
					list.add(new RenderEvent(String.valueOf(r.hp)).XY(r.x - (r.group == 0 ? 32 : 0), 260).srcWH(32, 32));
				}
			}
			for (Button b : btnCallHeros) {
				list.add(b.draw());
			}
			return list;
		}

		@Override
		public String info() {
			return "main view";
		}

		class Bar {
			String id;
			float percent; // 0~1
			int x, y, w, h;

			public Bar(int x, int y, int w, int h) {
				this.x = x;
				this.y = y;
				this.h = h;
				this.w = w;
			}

			public RenderEvent[] draw() {
				return new RenderEvent[] {
						new RenderEvent(ResourceManager.get().fetch(id)).XY(x, y).srcWH(w, h),
						new RenderEvent(ResourceManager.get().fetch(id)).XY(x, y).srcWH(w, h).dstWH((int) (w * percent), h) };
			}
		}

		class Button {
			String id;
			int x, y, w, h;
			int key = -1;

			public Button(String id, int x, int y, int w, int h) {
				this.id = id;
				listenTo(-1);
				listenTo(x, y, w, h);
			}

			public void listenTo(int x, int y, int w, int h) {
				this.x = x;
				this.y = y;
				this.h = h;
				this.w = w;
			}

			public void listenTo(int key) {
				this.key = key;
			}

			public boolean isClick(SignalEvent s) {
				if (key != -1) {
					if (s.type == SignalEvent.KEY_EVENT && s.signal.press(key)) {
						return true;
					}
				}
				if (h != -1 || w != -1) {
					if ((s.type == SignalEvent.MOUSE_EVENT || s.type == SignalEvent.TOUCH_EVENT) &&
							s.signal.press(KeyEvent.ANY_KEY) && s.action == SignalEvent.ACTION_UP) {
						if (s.signal.x > x && s.signal.x < x + w && s.signal.y > y && s.signal.y < y + h) {
							return true;
						}
					}
				}
				return false;
			}

			public RenderEvent draw() {
				return new RenderEvent(ResourceManager.get().fetch(id + "btn")).XY(x, y).srcWH(w, h);
			}

		}

		// class Role {
		// String id;
		// int x, y, w, h;
		//
		// public RenderEvent draw() {
		// return new RenderEvent(ResourceManager.get().fetch(id)).XY(x, y).srcWH(w, h);
		// }
		//
		// }

	}

	class GameModel implements Model {

		Random rand = new Random();
		ArrayList<Role> roles;

		public GameModel() {
			roles = new ArrayList<MakeAGame.GameModel.Role>();
			roles.add(new Role(ResourceManager.get().read("castle"), 0));
			roles.add(new Role(ResourceManager.get().read("castle"), 1));

		}

		@Override
		public void process(String gsonString) {
			Sign signs = new Gson().fromJson(gsonString, Sign.class);
			if (signs.clickBtn != null) {
				roles.add(new Role(ResourceManager.get().read(signs.clickBtn), 0));

				// test enemy
				// roles.add(new Role(ResourceManager.get().read(signs.clickBtn), 1));
			}

			for (ListIterator<Role> it = roles.listIterator(); it.hasNext();) {
				Role r = it.next();
				if (r.m.state < Role.STATE_DIE) {
					r.run();
				}
				else {
					it.remove();
				}
			}
		}

		@Override
		public String hold() {
			Hold hold = new Hold();
			for (Role r : roles) {
				hold.roles.add(new RoleHold(r.m.id, r.m.x, r.m.hp, r.m.group, 1));
			}
			return new Gson().toJson(hold);
		}

		@Override
		public String info() {
			return "main model";
		}

		class Role {

			final static int STATE_WALK = 1;
			final static int STATE_ATTACK = 2;
			final static int STATE_DIE = 3;

			Role meet;

			Attribute m;

			public Role(String gson, int group) {
				m = init(gson);
				m.group = group;
				m.x = group == 0 ? 32 : (Bootstrap.screamWidth() - 32);
				m.maxHp = m.hp;
				m.state = 0;
			}

			public class Attribute {
				public String id;
				int group; // 0 mine, 1 others
				int hp;
				int maxHp;
				int atk;
				int state;
				public int x;
				public float sX;
			}

			public Attribute init(String gson) {
				// Engine.logI("init with gson " + gson);
				Attribute model = new Gson().fromJson(gson, Attribute.class);
				return model;
			}

			public void run()
			{
				// stop while meet other groups role
				meet = null;
				for (Role r : roles) {
					if (m.group == 0 && r.m.group == 1) {
						if (m.x > r.m.x) {
							m.x = r.m.x;
							meet = r;
							break;
						}
					} else if (m.group == 1 && r.m.group == 0) {
						if (m.x < r.m.x) {
							m.x = r.m.x;
							meet = r;
							break;
						}
					}
				}

				// move if not meet others
				if (meet == null) {
					m.state = Role.STATE_WALK;
					m.x += (m.group == 0 ? 1 : -1) * m.sX;
				} else {
					// attack while stop
					m.state = Role.STATE_ATTACK;
					meet.m.hp -= m.atk;
				}

				// die
				if (m.hp <= 0) {
					m.state = Role.STATE_DIE;
				}
			}
		}
	}

	class Sign {
		String clickBtn;
	}

	class Hold {
		ArrayList<RoleHold> roles = new ArrayList<MakeAGame.RoleHold>();
	}

	class RoleHold {
		String id;
		int hp;
		int x;
		int state; // 1 walk, 2 attack, 3 been attacked, 4 die
		int group;

		public RoleHold(String id, int x, int hp, int group, int state) {
			this.id = id;
			this.x = x;
			this.hp = hp;
			this.group = group;
			this.state = state;
		}
	}

}
