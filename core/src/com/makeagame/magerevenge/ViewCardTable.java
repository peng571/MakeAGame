package com.makeagame.magerevenge;

import org.json.JSONException;
import org.json.JSONObject;

import com.makeagame.core.Controler;
import com.makeagame.tools.Button2;
import com.makeagame.tools.SimpleLayout;
import com.makeagame.tools.Sprite;

public class ViewCardTable extends SimpleLayout {
	public SimpleLayout[] send_icon_soldiers;
	public Button2[] btn_send_soldiers;
	public String[] map;
	
	public ViewCardTable() {
		xy(367, 10);
		
		map = new String[] {
				MakeAGame.CASTLE,
				MakeAGame.ROLE_1,
				MakeAGame.ROLE_2,
				MakeAGame.ROLE_3,
				MakeAGame.ROLE_4,
		};
		
		send_icon_soldiers = new SimpleLayout[5];
		for (int i=0; i<5; i++) {
			send_icon_soldiers[i] = new SimpleLayout(
					new Sprite(map[i] + "btn")).xy(103*i, 0);
			addChild(send_icon_soldiers[i]);
		}
		
		btn_send_soldiers = new Button2[] { 
				new Button2() { @Override public void OnMouseDown() { 
					ViewCardTable.this.sendSoldiers(0); } }, 
				new Button2() { @Override public void OnMouseDown() { 
					ViewCardTable.this.sendSoldiers(1); } }, 
				new Button2() { @Override public void OnMouseDown() { 
					ViewCardTable.this.sendSoldiers(2); } }, 
				new Button2() { @Override public void OnMouseDown() { 
					ViewCardTable.this.sendSoldiers(3); } }, 
				new Button2() { @Override public void OnMouseDown() { 
					ViewCardTable.this.sendSoldiers(4); } }, 
		};
		
		for (int i=0; i<send_icon_soldiers.length; i++) {
			Sprite active = new Sprite(MakeAGame.CASTLE + "btn");
			active.red = 0.6f;
			active.green = 0.6f;
			active.blue = 0.8f;
			
			Sprite hovered = new Sprite(MakeAGame.CASTLE + "btn");
			hovered.red = 1.0f;
			hovered.green = 1.0f;
			hovered.blue = 1.0f;
			
			// TODO: 按鈕動畫和CD表現
			btn_send_soldiers[i].setActiveSprite(active);
			btn_send_soldiers[i].setHoveredSprite(hovered);
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
		try {
		Controler.get().call(
				Sign.BATTLE_SendSoldier, new JSONObject()
						.put("player", 0)
						.put("soldierType", map[index]));
		} catch(JSONException e) {
			e.printStackTrace();
		}
	}
	
	public void model() {
		// TODO: 接收model資料
	}
}
