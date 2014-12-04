package com.makeagame.magerevenge;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.makeagame.core.Controler;
import com.makeagame.core.component.Position;
import com.makeagame.core.resource.ResourceManager;
import com.makeagame.core.view.RenderEvent;
import com.makeagame.core.view.SignalEvent;
import com.makeagame.core.view.SignalEvent.KeyEvent;
import com.makeagame.core.view.View;

public class GameView implements View {
	Button[] btnCallHeros;

	/**
	 * [define of res]
	 */
	int hline_field_ground = 340;
	Position<Integer> pos_top_board = new Position<Integer>(0, 480);
	int hline_base_hp = 27;
	int x_base_hp_op = 76;
	Position<Integer> pos_pause = new Position<Integer>(40, 0);
	Position<Integer> pos_bottom_board = new Position<Integer>(0, 408);
	Position<Integer> pos_power_ring = new Position<Integer>(34, 25);
	int vline_res_icon = 217;
	int hline_send_icon;

	Sprite sprite;

	public GameView() {
		btnCallHeros = new Button[5];
		btnCallHeros[0] = new Button(MakeAGame.CASTLE, 0, 450, 64, 64);
		btnCallHeros[1] = new Button(MakeAGame.ROLE_1, 80, 450, 64, 64);
		btnCallHeros[2] = new Button(MakeAGame.ROLE_2, 180, 450, 64, 64);
		btnCallHeros[3] = new Button(MakeAGame.ROLE_3, 280, 450, 64, 64);
		btnCallHeros[4] = new Button(MakeAGame.ROLE_3, 280, 450, 64, 64);

		sprite = new Sprite("background");
		sprite.addChild(new Sprite().xy(0, 340)
				.addChild(new Sprite(MakeAGame.CASTLE + "L").center(-50, 340))
				.addChild(new Sprite(MakeAGame.CASTLE + "R").center(754, 340)));
		sprite.addChild(new Sprite("top_board").center(480, 0)
				.addChild(new Sprite("base_hp").xy(0, 27))
				.addChild(new Sprite("pause").xy(40, 0)));
		sprite.addChild(new Sprite("bottom_board").xy(0, 408).center(0, 60)
				.addChild(new Sprite("power_ring").xy(34, 25))
				.addChild(new Sprite().xy(217, 0).addChild(new Sprite("res_icon_money").xy(70, 0)))
				.addChild(new Sprite("res_icon_res1").xy(108, 0))
				.addChild(new Sprite("res_icon_res2").xy(146, 0))
				.addChild(new Sprite().xy(0, 70)
						.addChild(new Sprite(MakeAGame.CASTLE + "btn").xy(367, 0))
						.addChild(new Sprite(MakeAGame.ROLE_1 + "btn").xy(470, 0))
						.addChild(new Sprite(MakeAGame.ROLE_1 + "btn").xy(573, 0))
						.addChild(new Sprite(MakeAGame.ROLE_1 + "btn").xy(696, 0))
						.addChild(new Sprite(MakeAGame.ROLE_1 + "btn").xy(779, 0))));

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
		list.addAll(sprite.render(0, 0));
		for (String s : build) {
			final Hold hold = new Gson().fromJson(s, Hold.class);
			// Engine.logI("get hold " + s);
			for (RoleHold r : hold.roles) {
				if (!r.id.equals(MakeAGame.CASTLE)) {
					list.add(new RenderEvent(ResourceManager.get().fetch(r.id)).XY(r.x - (r.group == 0 ? 32 : 0), 300).srcWH(32, 32)); // .filp(r.group == 1, false)
				}
				list.add(new RenderEvent(String.valueOf(r.hp)).XY(r.x - (r.group == 0 ? 32 : 0), 260));
			}
			list.add(new RenderEvent(String.valueOf(hold.money)).XY(50, 50));
			// for (int i = 0; i < btnCallHeros.length; i++) {
			// list.addAll(btnCallHeros[i].draw(hold.cost[i]));
			// }
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

		public ArrayList<RenderEvent> draw(int cost) {
			ArrayList<RenderEvent> list = new ArrayList<RenderEvent>();
			list.add(new RenderEvent(ResourceManager.get().fetch(id + "btn")).XY(x, y).srcWH(w, h));
			list.add(new RenderEvent(String.valueOf(cost)).XY(x, y));
			return list;
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