package com.makeagame.magerevenge;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;


import com.makeagame.core.Controler;
import com.makeagame.core.Engine;
import com.makeagame.core.resource.ResourceManager;
import com.makeagame.core.view.RenderEvent;
import com.makeagame.core.view.SignalEvent;
import com.makeagame.core.view.SignalEvent.KeyEvent;
import com.makeagame.core.view.View;
import com.makeagame.tools.KeyTable;
import com.makeagame.tools.KeyTable.ApplyList;
import com.makeagame.tools.KeyTable.Frame;
import com.makeagame.tools.KeyTable.Key;
import com.makeagame.tools.SimpleLayout;
import com.makeagame.tools.Sprite;

public class GameView implements View {
	Button[] btnCallHeros;

	SimpleLayout sprite;
	
	
	class PowerRing extends SimpleLayout {
		KeyTable keyTable;
		double count = -10.0;
		
		public PowerRing() {
			super(new Sprite("power_ring"));
			xy(34, -35);
			keyTable = new KeyTable(new Frame[]{
					new Frame(0, new Key[]{
							new Key("x", new Double(20), KeyTable.INT_LINEAR)}),
					new Frame(10, new Key[]{
							new Key("x", new Double(100), KeyTable.INT_LINEAR),
							new Key("red", new Double(0.0f), KeyTable.INT_LINEAR)}),
					new Frame(30, new Key[]{
							new Key("x", new Double(100), KeyTable.INT_LINEAR),
							new Key("red", new Double(1.0f), KeyTable.INT_LINEAR)}),
					
			});
		}
		
		@Override
		public void beforeRender() {
			count += 0.2;
			this.sprite.apply(keyTable.get(count));
		}
	}
	
	SimpleLayout background;
	SimpleLayout hline_field_ground;
	SimpleLayout castle_L;
	SimpleLayout castle_R;
	SimpleLayout top_board;
	SimpleLayout base_hp;
	SimpleLayout pause;
	SimpleLayout bottom_board;
	//SimpleLayout power_ring;
	PowerRing power_ring;
	SimpleLayout vline_res_icon;
	SimpleLayout res_icon_money;
	SimpleLayout res_icon_res1;
	SimpleLayout res_icon_res2;
	SimpleLayout hline_send_icon;
	SimpleLayout send_icon_soldier1;
	SimpleLayout send_icon_soldier2;
	SimpleLayout send_icon_soldier3;
	SimpleLayout send_icon_soldier4;
	SimpleLayout send_icon_soldier5;
	
	public GameView() {
		
		
		
		btnCallHeros = new Button[5];
		btnCallHeros[0] = new Button(MakeAGame.CASTLE, 0, 450, 64, 64);
		btnCallHeros[1] = new Button(MakeAGame.ROLE_1, 80, 450, 64, 64);
		btnCallHeros[2] = new Button(MakeAGame.ROLE_2, 180, 450, 64, 64);
		btnCallHeros[3] = new Button(MakeAGame.ROLE_3, 280, 450, 64, 64);
		btnCallHeros[4] = new Button(MakeAGame.ROLE_3, 280, 450, 64, 64);
		
		background = new SimpleLayout(new Sprite("background"));
		hline_field_ground = new SimpleLayout().xy(0, 340);
		castle_L = new SimpleLayout(new Sprite(MakeAGame.CASTLE + "L").center(160, 240)).xy(80, 0);
		castle_R = new SimpleLayout(new Sprite(MakeAGame.CASTLE + "R").center(96, 240)).xy(880, 0);
		top_board = new SimpleLayout(new Sprite("top_board").center(480, 0)).xy(480,0);
		base_hp = new SimpleLayout(new Sprite("base_hp")).xy(-230, 28);
		pause = new SimpleLayout(new Sprite("pause").center(24, 0)).xy(0, 40);
		bottom_board = new SimpleLayout(new Sprite("bottom_board").center(0, 60)).xy(0, 408);
		//power_ring = new SimpleLayout(new Sprite("power_ring")).xy(34, -35);
		power_ring = new PowerRing();
		vline_res_icon = new SimpleLayout().xy(217, 0);
		res_icon_money = new SimpleLayout(new Sprite("res_icon_money")).xy(0, 10);
		res_icon_res1 = new SimpleLayout(new Sprite("res_icon_res1")).xy(0, 48);
		res_icon_res2 = new SimpleLayout(new Sprite("res_icon_res2")).xy(0, 86);
		hline_send_icon = new SimpleLayout().xy(0, 10);
		send_icon_soldier1 = new SimpleLayout(new Sprite(MakeAGame.CASTLE + "btn")).xy(367, 0);
		send_icon_soldier2 = new SimpleLayout(new Sprite(MakeAGame.ROLE_1 + "btn")).xy(470, 0);
		send_icon_soldier3 = new SimpleLayout(new Sprite(MakeAGame.ROLE_1 + "btn")).xy(573, 0);
		send_icon_soldier4 = new SimpleLayout(new Sprite(MakeAGame.ROLE_1 + "btn")).xy(676, 0);
		send_icon_soldier5 = new SimpleLayout(new Sprite(MakeAGame.ROLE_1 + "btn")).xy(779, 0);
		
		background.addChild(hline_field_ground
				.addChild(castle_L)
				.addChild(castle_R)
		).addChild(top_board
				.addChild(base_hp)
				.addChild(pause)
		).addChild(bottom_board
				.addChild(power_ring)
				.addChild(vline_res_icon
						.addChild(res_icon_money)
						.addChild(res_icon_res1)
						.addChild(res_icon_res2)
				).addChild(hline_send_icon
						.addChild(send_icon_soldier1)
						.addChild(send_icon_soldier2)
						.addChild(send_icon_soldier3)
						.addChild(send_icon_soldier4)
						.addChild(send_icon_soldier5)
				)
		);
		
		sprite = background;
		
	}

	@Override
	public void signal(ArrayList<SignalEvent> signalList) throws JSONException {
		String clickBtn = "";
		for (SignalEvent s : signalList) {
			if (s.type == SignalEvent.MOUSE_EVENT || s.type == SignalEvent.TOUCH_EVENT) {
				if (s.signal.press(KeyEvent.ANY_KEY) && s.action == SignalEvent.ACTION_DOWN) {
				}
				if (s.action == SignalEvent.ACTION_UP) {
				}

				for (Button b : btnCallHeros) {
					if (b.isClick(s)) {
						clickBtn = b.id;
					}
				}
			}
		}
			Controler.get().call(Sign.BATTLE_SendSoldier, new JSONObject().put("player", 0).put("soldierType", clickBtn));
	}

	@Override
	public ArrayList<RenderEvent> render(ArrayList<String> build) {

		ArrayList<RenderEvent> list = new ArrayList<RenderEvent>();
		list.addAll(sprite.render(0, 0));
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
