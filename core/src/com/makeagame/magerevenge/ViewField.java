package com.makeagame.magerevenge;

import com.makeagame.core.Engine;
import com.makeagame.tools.KeyTable;
import com.makeagame.tools.SimpleLayout;
import com.makeagame.tools.Sprite;
import com.makeagame.tools.KeyTable.Frame;
import com.makeagame.tools.KeyTable.Key;

public class ViewField extends SimpleLayout {
	SimpleLayout castle_L;
	SimpleLayout castle_R;
	SimpleLayout roleLayer;
	//ArrayList<SimpleLayout> roles;
	KeyTable[] roleKeyTable;
	//int count = 0;
	
	public ViewField() {
		super();
		xy(0, 340);
		castle_L = new SimpleLayout(new Sprite(MakeAGame.CASTLE + "L").center(160, 240)).xy(80, 0);
		castle_R = new SimpleLayout(new Sprite(MakeAGame.CASTLE + "R").center(96, 240)).xy(880, 0);
		roleLayer = new SimpleLayout();
		addChild(castle_L);
		addChild(castle_R);
		addChild(roleLayer);
		
		roleKeyTable = new KeyTable[5];
		
		// walking
		roleKeyTable[0] = new KeyTable(new Frame[] {
				//new Frame(  0	, new Key[] { new Key("sound", "") }),
				//new Frame(  1	, new Key[] { new Key("sound", "button1.snd") }),
				//new Frame(  2	, new Key[] { new Key("sound", "") }),
				new Frame( 0, new Key[] { new Key("image", "role_walk1") }),
				new Frame( 100, new Key[] { new Key("image", "role_walk2") }),
				new Frame( 200, new Key[] { new Key("image", "role_walk3") }),
				new Frame( 300, new Key[] { new Key("image", "role_walk4") }),
				new Frame( 400, new Key[] { new Key("image", "role_walk4") }),
				
		}).setLoop(true);
		
		// preparing
		roleKeyTable[1] = new KeyTable(new Frame[] {
				new Frame( 0, new Key[] { new Key("image", "role_walk3") }),
				new Frame( 100, new Key[] { new Key("image", "role_attack1") }),
				new Frame( 200, new Key[] { new Key("image", "role_attack2") }),
				new Frame( 300, new Key[] { new Key("image", "role_attack3") }),
				new Frame( 400, new Key[] { new Key("image", "role_walk3") }),
		});	
		
		// STATE_BACKING:
		roleKeyTable[3] = new KeyTable(new Frame[] {
				new Frame( 0, new Key[] { new Key("image", "role_walk4") }),
				new Frame( 100, new Key[] { new Key("image", "role_hurt") }),
				new Frame( 200, new Key[] { new Key("image", "role_hurt") }),
				new Frame( 300, new Key[] { new Key("image", "role_hurt") }),
				new Frame( 400, new Key[] { new Key("image", "role_walk4") }),
		});
		
		// STATE_DEATH:
		roleKeyTable[4] = new KeyTable(new Frame[] {
				new Frame( 0, new Key[] { new Key("image", "role_walk4") }),
				new Frame( 100, new Key[] { new Key("image", "role_hurt") }),
				new Frame( 200, new Key[] { new Key("image", "role_fail") }),
				new Frame( 300, new Key[] { new Key("image", "role_dead") }),
				new Frame( 400, new Key[] { new Key("image", "role_dead") }),
		});	
	}
	
	@Override
	public void beforeRender() {
		super.beforeRender();
	
	}
	
	public void model(Hold data) {
		
		// TODO(3): gc problem, need optimize
		roleLayer.removeChildren();
		
		for (Hold.Unit r : data.soldier) {
			
			if (!r.type.equals(MakeAGame.CASTLE)) {
				Sprite sp = new Sprite("role_walk1").center(60, 90)
							.flip(r.group==1 ? true:false, false);
				
				//Engine.logI(new Long(r.lastWalkTime).toString());
				
				// solider animation
				if (r.stateRecord == 0) {
					sp.apply(roleKeyTable[0].get(r.lastWalkTime));
				}
				if (r.stateRecord == 1) {
					sp.apply(roleKeyTable[1].get(r.lastPreparingTime));
				}
				if (r.stateRecord == 3) {
					sp.apply(roleKeyTable[3].get(r.lastBackingTime));
				}
				if (r.stateRecord == 4) {
					sp.apply(roleKeyTable[4].get(r.lastDeathTime));
				}
				
				// TODO(3): gc problem, need optimize
				roleLayer.addChild(new SimpleLayout(sp)
						.xy(r.pos.getX(), 0));
			}
			// TODO(2): hp bar
			
			
		}

		for (Hold.Unit r : data.castle) {
			// TODO(3): castle
		}
	}
}
