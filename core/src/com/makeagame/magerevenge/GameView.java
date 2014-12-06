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

	
	class ViewResTable extends SimpleLayout {
		SimpleLayout res_icon_money;
		SimpleLayout res_icon_res1;
		SimpleLayout res_icon_res2;
		// TODO: res3
		public ViewResTable() {
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
	
	class ViewTopBoard extends SimpleLayout {
		
		SimpleLayout top_board;
		SimpleLayout base_hp;
		SimpleLayout pause;
		
		public ViewTopBoard() {
			xy(0, 0);
			top_board = new SimpleLayout(new Sprite("top_board").center(480, 0)).xy(480, 0);
			base_hp = new SimpleLayout(new Sprite("base_hp")).xy(-230, 28);
			pause = new SimpleLayout(new Sprite("pause").center(24, 0)).xy(0, 40);

			addChild(top_board
					.addChild(base_hp)
					.addChild(pause));
		}
		
		@Override
		public void beforeRender() {
			super.beforeRender();
		
		}
	}
	
	class ViewBattleScene extends SimpleLayout {
		SimpleLayout background;
		ViewField field;
		ViewTopBoard top_board;

		SimpleLayout bottom_board;
		ViewPowerRing power_ring;
		ViewResTable res_table;
		ViewCardTable card_table;
		
		public ViewBattleScene() {
			xy(0, 0);
			background = new SimpleLayout(new Sprite("background"));
			field = new ViewField();
			top_board = new ViewTopBoard();
			
			bottom_board = new SimpleLayout(new Sprite("bottom_board").center(0, 60)).xy(0, 408);
			power_ring = new ViewPowerRing();
			res_table = new ViewResTable();
			card_table = new ViewCardTable();
			
			background.addChild(field)
			.addChild(top_board)
			.addChild(bottom_board
					.addChild(power_ring)
					.addChild(res_table)
					.addChild(card_table)
			);
			
			addChild(background)
					.addChild(field)
					.addChild(top_board)
					.addChild(bottom_board
							.addChild(power_ring)
							.addChild(res_table)
							.addChild(card_table)
					);
		}
		
	}

	SimpleLayout screen;
	ViewBattleScene battle_scene;
	
	public GameView() {
		btnCallHeros = new Button[5];
		btnCallHeros[0] = new Button(MakeAGame.CASTLE, 0, 450, 64, 64);
		btnCallHeros[1] = new Button(MakeAGame.ROLE_1, 80, 450, 64, 64);
		btnCallHeros[2] = new Button(MakeAGame.ROLE_2, 180, 450, 64, 64);
		btnCallHeros[3] = new Button(MakeAGame.ROLE_3, 280, 450, 64, 64);
		btnCallHeros[4] = new Button(MakeAGame.ROLE_3, 380, 450, 64, 64);
		battle_scene = new ViewBattleScene();
		
		screen = battle_scene;
	}

	@Override
	public void signal(ArrayList<SignalEvent> signalList) throws JSONException {

		battle_scene.power_ring.button.signal(signalList);
		battle_scene.power_ring.btn_prev.signal(signalList);
		battle_scene.power_ring.btn_next.signal(signalList);
		for (int i=0; i<5; i++) {
			battle_scene.card_table.btn_send_soldiers[i].signal(signalList);
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
		screen.reslove(0, 0);
		list.addAll(screen.render());
		
		
		// 糧o�刈永蝓刈�簫n禮R >< 禮�要織繳model瞼��
		for (String s : build) {
			final Hold hold = new Gson().fromJson(s, Hold.class);
			battle_scene.field.model(hold);
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
			// list.add(new RenderEvent(ResourceManager.get().fetch(id + "btn")).XY(x, y).srcWH(w, h));
			list.add(new RenderEvent(String.valueOf(cost)).XY(x, y));
			return list;
		}
	}
	
}
