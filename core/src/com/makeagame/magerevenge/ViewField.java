package com.makeagame.magerevenge;

import java.util.LinkedList;

import com.makeagame.tools.Bar;
import com.makeagame.tools.Bar.Direction;
import com.makeagame.tools.KeyTable;
import com.makeagame.tools.KeyTable.Frame;
import com.makeagame.tools.KeyTable.Key;
import com.makeagame.tools.SimpleLayout;
import com.makeagame.tools.Sprite;

public class ViewField extends SimpleLayout {
	SimpleLayout castle_L;
	SimpleLayout castle_R;
	SimpleLayout roleLayer;
	LinkedList<RoleView> roles;
	KeyTable[] roleKeyTable;

	int count = 0;

	public ViewField() {
		super();
		xy(0, 340);
		castle_L = new SimpleLayout(new Sprite("castle_al").center(160, 240)).xy(80, 0);
		castle_R = new SimpleLayout(new Sprite("castle_op").center(96, 240)).xy(880, 0);
		roleLayer = new SimpleLayout();
		addChild(castle_L);
		addChild(castle_R);
		addChild(roleLayer);

		roleKeyTable = new KeyTable[5];

		
		// walking
		roleKeyTable[0] = new KeyTable(new Frame[] {
				//new Frame(  0	, new Key[] { new Key("sound", "") }),
				//new Frame(  1	, new Key[] { new Key("sound", "hit1.snd") }),
				//new Frame(  2	, new Key[] { new Key("sound", "") }),
				new Frame( 0, new Key[] { new Key("image", "role1_walk1") }),
				new Frame( 100, new Key[] { new Key("image", "role1_walk2") }),
				new Frame( 200, new Key[] { new Key("image", "role1_walk3") }),
				new Frame( 300, new Key[] { new Key("image", "role1_walk4") }),
				new Frame( 400, new Key[] { new Key("image", "role1_walk5") }),
				new Frame( 500, new Key[] { new Key("image", "role1_walk6") }),
				new Frame( 600, new Key[] { new Key("image", "role1_walk7") }),
				new Frame( 700, new Key[] { new Key("image", "role1_walk8") }),
				new Frame( 800, new Key[] { new Key("image", "role1_walk1") }),
				
		}).setLoop(true);
		
		// preparing
		roleKeyTable[1] = new KeyTable(new Frame[] {
				new Frame(  300	, new Key[] { new Key("sound", "") }),
				new Frame(  400	, new Key[] { new Key("sound", "hit1.snd") }),
				new Frame(  500	, new Key[] { new Key("sound", "") }),
				
				
//				new Frame( 0, new Key[] { new Key("image", "role_walk3") }),
				new Frame( 0, new Key[] { new Key("image", "role1_stand1") }),
				new Frame( 200, new Key[] { new Key("image", "role1_attack1") }),
				new Frame( 400, new Key[] { new Key("image", "role1_attack2") }),
				new Frame( 550, new Key[] { new Key("image", "role1_attack3") }),
				new Frame( 700, new Key[] { new Key("image", "role1_stand1") }),
//				new Frame( 1000, new Key[] { new Key("image", "role_attack1") }),
//				new Frame( 800, new Key[] { new Key("image", "role_walk3") }),
		});	
		
//		// STATE_BACKING:
//		roleKeyTable[3] = new KeyTable(new Frame[] {
//				new Frame( 0, new Key[] { new Key("image", "role_walk4") }),
//				new Frame( 100, new Key[] { new Key("image", "role_hurt") }),
//				new Frame( 200, new Key[] { new Key("image", "role_hurt") }),
//				new Frame( 300, new Key[] { new Key("image", "role_hurt") }),
//				new Frame( 400, new Key[] { new Key("image", "role_walk4") }),
//		});
		
		// STATE_DEATH:
		roleKeyTable[3] = new KeyTable(new Frame[] {
				new Frame(  0	, new Key[] { new Key("sound", "") }),
				new Frame(  100	, new Key[] { new Key("sound", "dead1.snd") }),
				new Frame(  200	, new Key[] { new Key("sound", "") }),
				
				new Frame( 0, new Key[] { new Key("image", "role1_stand1") }),
				new Frame( 100, new Key[] { new Key("image", "role1_fail1") }),
				new Frame( 300, new Key[] { new Key("image", "role1_dead1") }),
				new Frame( 600, new Key[] { new Key("image", "role1_dead2") }),
				new Frame( 900, new Key[] { new Key("image", "role1_dead3") }),
		});	

		roles = new LinkedList<ViewField.RoleView>();

	}

	@Override
	public void beforeRender() {
		super.beforeRender();
		for (RoleView role : roles) {
			role.bar.apply(role.children.get(0).sprite);
		}
	}

	public void model(Hold data) {

	
		while (data.soldier.size() > roles.size()) {
			RoleView role = new RoleView();
			roles.add(role); 	// add only
			roleLayer.addChild(role);
		}

		count = 0;
		RoleView role ;
		for (Hold.Unit r : data.soldier) {
			// Engine.logI(new Long(r.lastWalkTime).toString());
			role = roles.get(count);
			role.model(r);
			role.visible = true;
			if (r.stateRecord == 4) {
				role.visible = false;
			}
			role.xy(r.pos.x, r.pos.y);
			role.bar.percent = r.hpp;
			role = null;
			count++;
		}
		for (int i = count; i < roles.size(); i++) {
			roles.get(count).visible = false;
		}

		// TODO(3): castle
//		for (Hold.Unit r : data.castle) {
//			
//		}
	}

	class RoleView extends SimpleLayout {

		Bar bar;

		public RoleView() {
			super();
			addChild(new SimpleLayout(new Sprite("role_hp")).xy(0, -100));
			bar = new Bar();
			bar.setBar(Direction.ROW, 32);
			sprite = new Sprite("role_walk1").center(60, 125);
		}

		public void model(Hold.Unit r) {

			if ( r.group == 1) {
				sprite.flip(true, false);
			}else{
				sprite.flip(false, false);
			}
				
			
			// solider animation
			switch (r.stateRecord) {
			case 0:
				sprite.apply(roleKeyTable[0].get(r.lastWalkTime));
				break;
			case 1:
				sprite.apply(roleKeyTable[1].get(r.lastPreparingTime));
				break;
//			case 3:
//				sprite.apply(roleKeyTable[3].get(r.lastBackingTime));
//				break;
			case 3:
				sprite.apply(roleKeyTable[3].get(r.lastDeathTime));
				break;
			}
		}
	}
}
