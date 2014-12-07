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

	class ViewNumber extends SimpleLayout {
		int number;
		String output; 
		
		public ViewNumber(Sprite sprite) {
			super(sprite);
			output = new String();
		}
		
		public void setNumber(int num) {
			number = num;
			output = Integer.toString(number);
		}
		
		@Override
		public ArrayList<RenderEvent> renderSelf(int x, int y) {
			beforeRender();
			ArrayList<RenderEvent> list = new ArrayList<RenderEvent>();
			int offset = -output.length()*12;
			for (int i=0; i < output.length(); i++) {
				int idx = output.codePointAt(i) - 48;
				offset += 12;
				list.add(new RenderEvent(ResourceManager.get().fetch(this.sprite.image))
					.XY(x+offset, y)
					.src(idx*12, 0, 12, 24)
				);
			}
			return list;
		}
	}
	
	class ViewResTable extends SimpleLayout {
		SimpleLayout fund;
		SimpleLayout res1;
		SimpleLayout res2;
		SimpleLayout res3;
		ViewNumber fund_number;
		ViewNumber res1_number;
		ViewNumber res2_number;
		ViewNumber res3_number;
		
		public ViewResTable() {
			xy(206, 0);
			fund_number = (ViewNumber) new ViewNumber(new Sprite("font_number_withe")).xy(110, 11);
			fund_number.setNumber(789456);
			
			res1_number = (ViewNumber) new ViewNumber(new Sprite("font_number_withe")).xy(63, 11);
			res1_number.setNumber(12);
			
			res2_number = (ViewNumber) new ViewNumber(new Sprite("font_number_withe")).xy(63, 11);
			res2_number.setNumber(0);
			
			res3_number = (ViewNumber) new ViewNumber(new Sprite("font_number_withe")).xy(63, 11);
			res3_number.setNumber(1);
			
			fund = new SimpleLayout(new Sprite("fund_bg")).xy(0, -22)
					.addChild(new SimpleLayout(new Sprite("fund_icon").xy(4, 0)))
					.addChild(fund_number);
			res1 = new SimpleLayout(new Sprite("res_bg")).xy(0, 15)
					.addChild(new SimpleLayout(new Sprite("res1_icon").xy(4, 0)))
					.addChild(res1_number);
			res2 = new SimpleLayout(new Sprite("res_bg")).xy(0, 51)
					.addChild(new SimpleLayout(new Sprite("res2_icon").xy(3, 0)))
					.addChild(res2_number);
			res3 = new SimpleLayout(new Sprite("res_bg")).xy(0, 87)
					.addChild(new SimpleLayout(new Sprite("res3_icon").xy(4, 0)))
					.addChild(res3_number);
			
			addChild(fund);
			addChild(res1);
			addChild(res2);
			addChild(res3);
		}
		
		@Override
		public void beforeRender() {
			super.beforeRender();
		
		}
		public void model(Hold data) {
			this.fund_number.setNumber(data.money);
			this.res1_number.setNumber(data.resource[0]);
			this.res2_number.setNumber(data.resource[1]);
			this.res3_number.setNumber(data.resource[2]);
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
		ViewPower power_ring;
		ViewResTable res_table;
		ViewCardTable card_table;
		
		public ViewBattleScene() {
			xy(0, 0);
			background = new SimpleLayout(new Sprite("background"));
			field = new ViewField();
			top_board = new ViewTopBoard();
			
			bottom_board = new SimpleLayout(new Sprite("bottom_board").center(0, 60)).xy(0, 408);
			power_ring = new ViewPower();
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
		
		public void model(Hold data) {
			res_table.model(data);
			power_ring.model(data);
			card_table.model(data);
			field.model(data);
		}
		
	}

	SimpleLayout screen;
	ViewBattleScene battle_scene;
	
	public GameView() {
		battle_scene = new ViewBattleScene();
		
		screen = battle_scene;
	}

	@Override
	public void signal(ArrayList<SignalEvent> signalList) throws JSONException {

		battle_scene.power_ring.button.signal(signalList);
		battle_scene.power_ring.btn_prev.signal(signalList);
		battle_scene.power_ring.btn_next.signal(signalList);
		battle_scene.card_table.signal(signalList);
		//for (int i=0; i<5; i++) {
		//	battle_scene.card_table.btn_send_soldiers[i].signal(signalList);
		//}

		for (SignalEvent s : signalList) {
			if (s.type == SignalEvent.MOUSE_EVENT || s.type == SignalEvent.TOUCH_EVENT) {
				if (s.signal.press(KeyEvent.ANY_KEY) && s.action == SignalEvent.ACTION_DOWN) {
				}
				if (s.action == SignalEvent.ACTION_UP) {
				}
			}
		}
		Controler.get().call(0, null);
	}

	@Override
	public ArrayList<RenderEvent> render(ArrayList<String> build) {
		
		final Hold data = new Gson().fromJson(build.get(build.size()-1), Hold.class);
		battle_scene.model(data);
		
		screen.reslove(0, 0);
		ArrayList<RenderEvent> list = new ArrayList<RenderEvent>();
		list.addAll(screen.render());
		
		
//		// 糧o�刈永蝓刈�簫n禮R >< 禮�要織繳model瞼��
		for (String s : build) {
			final Hold hold = new Gson().fromJson(s, Hold.class);
			battle_scene.field.model(hold);
			// Engine.logI("get hold " + s);
			for (Hold.Unit r : hold.soldier) {
//				if (!r.id.equals(MakeAGame.CASTLE)) {
//					list.add(new RenderEvent(ResourceManager.get().fetch(r.id)).XY(r.x - (r.group == 0 ? 32 : 0), 300).srcWH(32, 32)); // .filp(r.group == 1, false)
//				}
				list.add(new RenderEvent(String.valueOf(r.hpp)).XY(r.pos.getX() - (r.group == 0 ? 32 : 0), r.pos.getY()));
			}
			list.add(new RenderEvent(String.valueOf(hold.money)).XY(50, 50));
//			for (int i = 0; i < hold.sendcard.length; i++) {
//				list.addAll(btnCallHeros[i].draw(hold.cost[i]));
//			}
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
