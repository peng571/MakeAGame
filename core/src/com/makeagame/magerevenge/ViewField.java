package com.makeagame.magerevenge;

import com.makeagame.tools.KeyTable;
import com.makeagame.tools.SimpleLayout;
import com.makeagame.tools.Sprite;
import com.makeagame.tools.KeyTable.Frame;
import com.makeagame.tools.KeyTable.Key;

public class ViewField extends SimpleLayout {
	SimpleLayout castle_L;
	SimpleLayout castle_R;
	SimpleLayout role_layout;
	//ArrayList<SimpleLayout> roles;
	KeyTable[] roleKeyTable;
	int count = 0;
	
	public ViewField() {
		xy(0, 340);
		castle_L = new SimpleLayout(new Sprite(MakeAGame.CASTLE + "L").center(160, 240)).xy(80, 0);
		castle_R = new SimpleLayout(new Sprite(MakeAGame.CASTLE + "R").center(96, 240)).xy(880, 0);
		role_layout = new SimpleLayout();
		addChild(castle_L);
		addChild(castle_R);
		addChild(role_layout);
		
		roleKeyTable = new KeyTable[5];
		
		// 走路動畫
		roleKeyTable[0] = new KeyTable(new Frame[] {
				new Frame(0, new Key[] {
						new Key("image", "role_walk1") }),
				new Frame(3, new Key[] {
						new Key("image", "role_walk2") }),
				new Frame(6, new Key[] {
						new Key("image", "role_walk3") }),
				new Frame(9, new Key[] {
						new Key("image", "role_walk4") }),
		});
		
	}
	
	@Override
	public void beforeRender() {
		super.beforeRender();
	
	}
	
	public void model(Hold hold) {
		count += 1;
		if (count > 12) { count = 0; }
		
		role_layout.removeChildren();
		for (RoleHold r : hold.roles) {
			if (!r.id.equals(MakeAGame.CASTLE)) {
				Sprite sp = new Sprite().center(60, 90)
							.flip(r.group==1 ? true:false, false);//new Sprite("role_walk1")
				
				sp.apply(roleKeyTable[0].get(count));
				role_layout.addChild(
						new SimpleLayout(sp)
						.xy(r.x, 0));
			}
			// TODO: 加入血條
			//list.add(new RenderEvent(String.valueOf(r.hp)).XY(r.x - (r.group == 0 ? 32 : 0), 260));
		}
	}
}