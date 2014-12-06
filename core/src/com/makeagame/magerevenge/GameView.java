package com.makeagame.magerevenge;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.makeagame.core.Controler;
import com.makeagame.core.resource.ResourceManager;
import com.makeagame.core.view.RenderEvent;
import com.makeagame.core.view.SignalEvent;
import com.makeagame.core.view.SignalEvent.KeyEvent;
import com.makeagame.core.view.View;
import com.makeagame.tools.Bar;
import com.makeagame.tools.Button2;
import com.makeagame.tools.KeyTable;
import com.makeagame.tools.KeyTable.Frame;
import com.makeagame.tools.KeyTable.Key;
import com.makeagame.tools.SimpleLayout;
import com.makeagame.tools.Sprite;

public class GameView implements View {
	Button[] btnCallHeros;

	class PowerRing extends SimpleLayout {
		KeyTable keyTable;
		double count = 0.0;
		Bar bar;
		Button2 button;
		Button2 btn_prev;
		Button2 btn_next;
		
		SimpleLayout layout_icon;
		SimpleLayout layout_prev;
		SimpleLayout layout_next;
		
		String selectPower;
		
		public PowerRing() {
			super(new Sprite("power_ring"));
			selectPower = "a";
			xy(34, -35);
			keyTable = new KeyTable(new Frame[] {
					new Frame(0, new Key[] {
							new Key("x", new Double(20), KeyTable.INT_LINEAR) }),
					new Frame(10, new Key[] {
							new Key("x", new Double(100), KeyTable.INT_LINEAR),
							new Key("red", new Double(0.0f), KeyTable.INT_LINEAR) }),
					new Frame(30, new Key[] {
							new Key("x", new Double(100), KeyTable.INT_LINEAR),
							new Key("red", new Double(1.0f), KeyTable.INT_LINEAR) }),

			});
			bar = new Bar();
			bar.setBar(Bar.Direction.COLUMN_REVERSE, 160);

			button = new Button2();
			{
				Sprite active = new Sprite("power_ring");
				active.alpha = 1.0f;
				Sprite hovered = new Sprite("power_ring");
				hovered.alpha = 0.5f;
				
				button.setActiveSprite(active);
				button.setHoveredSprite(hovered);
			}
			
			btn_prev = new Button2() {
				@Override
				public void OnMouseDown() {
					PowerRing.this.prevPower();
				}
			};
			layout_prev = new SimpleLayout(new Sprite("power_prev"));
			{
				Sprite active = new Sprite("power_prev");
				active.alpha = 0.5f;
				Sprite hovered = new Sprite("power_prev");
				hovered.red = 2.0f;
				
				btn_prev.setActiveSprite(active);
				btn_prev.setHoveredSprite(hovered);
			}
			addChild(layout_prev);
			
			btn_next = new Button2() {
				@Override
				public void OnMouseDown() {
					PowerRing.this.nextPower();
				}
			};
			layout_next = new SimpleLayout(new Sprite("power_next"));
			{
				Sprite active = new Sprite("power_next");
				active.alpha = 0.5f;
				Sprite hovered = new Sprite("power_next");
				hovered.red = 2.0f;
				
				btn_next.setActiveSprite(active);
				btn_next.setHoveredSprite(hovered);
			}
			addChild(layout_next);
			
			layout_icon = new SimpleLayout(new Sprite("power_a"));
			addChild(layout_icon);
		}

		@Override
		public void beforeRender() {
			super.beforeRender();
			
			button.setRectArea(realX, realY, 160, 160);
			button.apply(this.sprite);
			
			btn_prev.setRectArea(realX-30, realY-30, 60, 60);
			btn_prev.apply(layout_prev.sprite);
			
			btn_next.setRectArea(realX+100, realY+100, 60, 60);
			btn_next.apply(layout_next.sprite);
			
			bar.percent += 0.01;
			bar.apply(this.sprite);
			
			count += 0.2;
			//this.sprite.apply(keyTable.get(count));
		}
		
		
		public void nextPower() {
			if (selectPower == "a") {
				selectPower = "b";
			} else if (selectPower == "b") {
				selectPower = "c";
			}
			layout_icon.sprite.image = "power_" + selectPower;
		}
		
		public void prevPower() {
			if (selectPower == "b") {
				selectPower = "a";
			} else if (selectPower == "c") {
				selectPower = "b";
			}
			layout_icon.sprite.image = "power_" + selectPower;
		}
	}
	
	class CardTable extends SimpleLayout {
		public SimpleLayout[] send_icon_soldiers;
		public Button2[] btn_send_soldiers;
		
		public CardTable() {
			xy(367, 10);
			send_icon_soldiers = new SimpleLayout[] {
					new SimpleLayout(new Sprite(MakeAGame.CASTLE + "btn")).xy(0, 0),
					new SimpleLayout(new Sprite(MakeAGame.ROLE_1 + "btn")).xy(103, 0),
					new SimpleLayout(new Sprite(MakeAGame.ROLE_1 + "btn")).xy(206, 0),
					new SimpleLayout(new Sprite(MakeAGame.ROLE_1 + "btn")).xy(309, 0),
					new SimpleLayout(new Sprite(MakeAGame.ROLE_1 + "btn")).xy(412, 0),
			};
			for (int i=0; i<send_icon_soldiers.length; i++) {
				addChild(send_icon_soldiers[i]);
			}
			
			btn_send_soldiers = new Button2[] { 
					new Button2() { @Override public void OnMouseDown() { 
						CardTable.this.sendSoldiers(0); } }, 
					new Button2() { @Override public void OnMouseDown() { 
						CardTable.this.sendSoldiers(1); } }, 
					new Button2() { @Override public void OnMouseDown() { 
						CardTable.this.sendSoldiers(2); } }, 
					new Button2() { @Override public void OnMouseDown() { 
						CardTable.this.sendSoldiers(3); } }, 
					new Button2() { @Override public void OnMouseDown() { 
						CardTable.this.sendSoldiers(4); } }, 
			};
			
			for (int i=0; i<send_icon_soldiers.length; i++) {
				Sprite active = new Sprite(MakeAGame.CASTLE + "btn");
				active.alpha = 0.5f;
				// TODO: 按鈕動畫和CD表現
				btn_send_soldiers[i].setActiveSprite(active);
				//btn_next.setHoveredSprite(hovered);

			}
		}
		
		@Override
		public void beforeRender() {
			super.beforeRender();
			for (int i=0; i<send_icon_soldiers.length; i++) {
				btn_send_soldiers[i].setRectArea(
						send_icon_soldiers[i].realX, 
						send_icon_soldiers[i].realY, 86, 108);
				btn_send_soldiers[i].apply(send_icon_soldiers[i].sprite);
			}
		}
		
		public void sendSoldiers(int index) {
			// TODO:
		}
		
		public void model() {
			// TODO: 接收model資料
		}
	}
	

	
	class ResTable extends SimpleLayout {
		SimpleLayout res_icon_money;
		SimpleLayout res_icon_res1;
		SimpleLayout res_icon_res2;
		// TODO: res3
		public ResTable() {
			xy(217, 0);
			res_icon_money = new SimpleLayout(new Sprite("res_icon_money")).xy(0, 10);
			res_icon_res1 = new SimpleLayout(new Sprite("res_icon_res1")).xy(0, 48);
			res_icon_res2 = new SimpleLayout(new Sprite("res_icon_res2")).xy(0, 86);
			addChild(res_icon_money);
			addChild(res_icon_res1);
			addChild(res_icon_res2);
		}
		
		@Override
		public void beforeRender() {
			super.beforeRender();
		
		}
	}
	
	class Field extends SimpleLayout {
		SimpleLayout castle_L;
		SimpleLayout castle_R;
		
		public Field() {
			xy(0, 340);
			castle_L = new SimpleLayout(new Sprite(MakeAGame.CASTLE + "L").center(160, 240)).xy(80, 0);
			castle_R = new SimpleLayout(new Sprite(MakeAGame.CASTLE + "R").center(96, 240)).xy(880, 0);
			addChild(castle_L);
			addChild(castle_R);
		}
		
		@Override
		public void beforeRender() {
			super.beforeRender();
		
		}
		
	}

	SimpleLayout sprite;

	SimpleLayout background;
	Field field;
	
	SimpleLayout top_board;
	SimpleLayout base_hp;
	SimpleLayout pause;
	
	SimpleLayout bottom_board;
	PowerRing power_ring;
	ResTable res_table;
	CardTable card_table;
	
	public GameView() {

		btnCallHeros = new Button[5];
		btnCallHeros[0] = new Button(MakeAGame.CASTLE, 0, 450, 64, 64);
		btnCallHeros[1] = new Button(MakeAGame.ROLE_1, 80, 450, 64, 64);
		btnCallHeros[2] = new Button(MakeAGame.ROLE_2, 180, 450, 64, 64);
		btnCallHeros[3] = new Button(MakeAGame.ROLE_3, 280, 450, 64, 64);
		btnCallHeros[4] = new Button(MakeAGame.ROLE_3, 380, 450, 64, 64);

		background = new SimpleLayout(new Sprite("background"));
		field = new Field();
		
		top_board = new SimpleLayout(new Sprite("top_board").center(480, 0)).xy(480, 0);
		base_hp = new SimpleLayout(new Sprite("base_hp")).xy(-230, 28);
		pause = new SimpleLayout(new Sprite("pause").center(24, 0)).xy(0, 40);
		
		bottom_board = new SimpleLayout(new Sprite("bottom_board").center(0, 60)).xy(0, 408);
		power_ring = new PowerRing();
		res_table = new ResTable();
		card_table = new CardTable();
		
		background.addChild(field)
				.addChild(top_board
						.addChild(base_hp)
						.addChild(pause))
				.addChild(bottom_board
						.addChild(power_ring)
						.addChild(res_table)
						.addChild(card_table)
				);

		sprite = background;

	}

	@Override
	public void signal(ArrayList<SignalEvent> signalList) throws JSONException {
		power_ring.button.signal(signalList);
		power_ring.btn_prev.signal(signalList);
		power_ring.btn_next.signal(signalList);
		for (int i=0; i<5; i++) {
			card_table.btn_send_soldiers[i].signal(signalList);
		}
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
		sprite.reslove(0, 0);
		list.addAll(sprite.render());
		
		// 這邊先不要刪 >< 我要測model用
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
			for (int i = 0; i < btnCallHeros.length; i++) {
				list.addAll(btnCallHeros[i].draw(hold.cost[i]));
			}
		}

		return list;
	}

	@Override
	public String info() {
		return "main view";
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
//			list.add(new RenderEvent(ResourceManager.get().fetch(id + "btn")).XY(x, y).srcWH(w, h));
			list.add(new RenderEvent(String.valueOf(cost)).XY(x, y));
			return list;
		}
	}
}
