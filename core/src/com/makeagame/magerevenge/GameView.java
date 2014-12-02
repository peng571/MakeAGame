package com.makeagame.magerevenge;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.makeagame.core.Controler;
import com.makeagame.core.resource.ResourceManager;
import com.makeagame.core.view.RenderEvent;
import com.makeagame.core.view.SignalEvent;
import com.makeagame.core.view.SignalEvent.KeyEvent;
import com.makeagame.core.view.View;

public class GameView implements View {
	int testint = 2;
	
	Button[] btnCallHeros;

	public GameView() {
		btnCallHeros = new Button[3];
		btnCallHeros[0] = new Button(MakeAGame.ROLE_1, 80, 450, 64, 64);
		btnCallHeros[1] = new Button(MakeAGame.ROLE_2, 180, 450, 64, 64);
		btnCallHeros[2] = new Button(MakeAGame.ROLE_3, 280, 450, 64, 64);
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
				list.add(new RenderEvent(String.valueOf(r.hp)).XY(r.x - (r.group == 0 ? 32 : 0), 260));
			}
			list.add(new RenderEvent(String.valueOf(hold.money)).XY(50, 460));
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