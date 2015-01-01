package com.makeagame.magerevenge;

import com.makeagame.core.Bootstrap;
import com.makeagame.core.Engine;
import com.makeagame.core.model.Model;
import com.makeagame.core.resource.ResourceManager;
import com.makeagame.core.view.View;

public class MegaRevenge {

	private Engine engine;

	public static String CASTLE = "castle";
	public static String ROLE_1 = "Swordman"; // 劍士(刺客)
	public static String ROLE_2 = "Warrior"; // 戰士(十字軍) 
	public static String ROLE_3 = "Mage"; // 法師
	public static String ROLE_4 = "Shielder"; // (A) 重裝武士

	public MegaRevenge() { 

		engine = new Engine(new Bootstrap() {
	
			@Override
			public View setMainView() {
				return  new GameView();
			}

			@Override
			public Model setMainModel() {
				return new GameModel();
			}

			@Override
			public void resourceFactory(ResourceManager rm) {
				rm.bindImage("send_icon_soldier1", "mr3/send_icons.png").src(0, 0, 117, 144);
				rm.bindImage(ROLE_1 + "btn", "mr3/send_icons.png").src(117, 0, 117, 144);
				rm.bindImage("send_icon_soldier1_inactive", "mr3/send_icons_inactive.png").src(0, 0, 117, 144);
				rm.bindImage(ROLE_1 + "btn_inactive", "mr3/send_icons_inactive.png").src(117, 0, 117, 144);
				rm.bindImage("send_icon_soldier1_inactive2", "mr3/send_icons_inactive2.png").src(0, 0, 117, 144);
				rm.bindImage(ROLE_1 + "btn_inactive2", "mr3/send_icons_inactive2.png").src(117, 0, 117, 144);
				rm.bindImage("role_walk1", "mr3/role.png").src(0, 0, 124, 100);
				rm.bindImage("role_walk2", "mr3/role.png").src(124, 0, 124, 100);
				rm.bindImage("role_walk3", "mr3/role.png").src(248, 0, 124, 100);
				rm.bindImage("role_walk4", "mr3/role.png").src(372, 0, 124, 100);
				rm.bindImage("role_attack1", "mr3/role.png").src(496, 0, 124, 100);
				rm.bindImage("role_attack2", "mr3/role.png").src(620, 0, 124, 100);
				rm.bindImage("role_attack3", "mr3/role.png").src(744, 0, 124, 100);
				rm.bindImage("role_hurt", "mr3/role.png").src(868, 0, 124, 100);
				rm.bindImage("role_fail", "mr3/role.png").src(992, 0, 124, 100);
				rm.bindImage("role_dead", "mr3/role.png").src(1116, 0, 124, 100);
				rm.bindImage("role1_walk1", "mr3/role1.png").src(0, 0, 175, 146);
				rm.bindImage("role1_walk2", "mr3/role1.png").src(175, 0, 175, 146);
				rm.bindImage("role1_walk3", "mr3/role1.png").src(350, 0, 175, 146);
				rm.bindImage("role1_walk4", "mr3/role1.png").src(525, 0, 175, 146);
				rm.bindImage("role1_walk5", "mr3/role1.png").src(700, 0, 175, 146);
				rm.bindImage("role1_walk6", "mr3/role1.png").src(875, 0, 175, 146);
				rm.bindImage("role1_walk7", "mr3/role1.png").src(1050, 0, 175, 146);
				rm.bindImage("role1_walk8", "mr3/role1_2.png").src(0, 0, 175, 146);
				rm.bindImage("role1_attack1", "mr3/role1_2.png").src(175, 0, 175, 146);
				rm.bindImage("role1_attack2", "mr3/role1_2.png").src(350, 0, 175, 146);
				rm.bindImage("role1_attack3", "mr3/role1_2.png").src(525, 0, 175, 146);
				rm.bindImage("role1_stand1", "mr3/role1_2.png").src(700, 0, 175, 146);
				rm.bindImage("role1_fail1", "mr3/role1_2.png").src(875, 0, 175, 146);
				rm.bindImage("role1_dead1", "mr3/role1_2.png").src(1050, 0, 175, 146);
				rm.bindImage("role1_dead2", "mr3/role1_2.png").src(1225, 0, 175, 146);
				rm.bindImage("role1_dead3", "mr3/role1_2.png").src(1400, 0, 175, 146);
				rm.bindImage("eff_damage_rock1", "mr3/eff_damage_rock.png").src(0, 0, 128, 128);
				rm.bindImage("eff_damage_rock2", "mr3/eff_damage_rock.png").src(128, 0, 128, 128);
				rm.bindImage("eff_damage_rock3", "mr3/eff_damage_rock.png").src(256, 0, 128, 128);
				rm.bindImage("eff_damage_rock4", "mr3/eff_damage_rock.png").src(384, 0, 128, 128);
				rm.bindImage("fund_icon", "mr3/res.png").src(0, 0, 40, 40);
				rm.bindImage("res1_icon", "mr3/res.png").src(40, 0, 41, 40);
				rm.bindImage("res2_icon", "mr3/res.png").src(81, 0, 41, 40);
				rm.bindImage("res3_icon", "mr3/res.png").src(122, 0, 40, 40);
				rm.bindImage("fund_bg", "mr3/res.png").src(162, 0, 142, 40);
				rm.bindImage("res_bg", "mr3/res.png").src(304, 0, 100, 40);
				rm.bindImage("pause", "mr3/res.png").src(404, 0, 48, 48);
				rm.bindImage("background1", "mr3/background1.png").src(0, 0, 960, 540);
				rm.bindImage("top_board", "mr3/top_board.png").src(0, 0, 960, 100);
				rm.bindImage("top_title", "mr3/top_title.png").src(0, 0, 140, 80);
				rm.bindImage("castle_al", "mr3/castle.png").src(0, 0, 256, 256);
				rm.bindImage("castle_op", "mr3/castle.png").src(256, 0, 256, 256);
				rm.bindImage("bottom_board", "mr3/bottom_board.png").src(0, 0, 960, 192);
				rm.bindImage("base_hp", "mr3/base_hp.png").src(0, 0, 155, 14);
				rm.bindImage("role_hp", "mr3/role_hp.png").src(0, 0, 33, 3);
				rm.bindImage("font_number_withe", "mr3/font_number_withe.png").src(0, 0, 120, 24);
				rm.bindImage("power_next", "mr3/power_btn.png").src(0, 0, 48, 48);
				rm.bindImage("power_prev", "mr3/power_btn.png").src(48, 0, 48, 48);
				rm.bindImage("power_a", "mr3/power.png").src(0, 0, 180, 180);
				rm.bindImage("power_b", "mr3/power.png").src(180, 0, 180, 180);
				rm.bindImage("power_c", "mr3/power.png").src(360, 0, 180, 180);
				rm.bindImage("power_ring", "mr3/power.png").src(540, 0, 180, 180);
				rm.bindImage("power_ring_inactive", "mr3/power.png").src(720, 0, 180, 180);


				
				rm.bindImage(ROLE_1, "mr3/font_number_withe.png").attribute("data/role1.txt");
				rm.bindImage(ROLE_2, "mr3/font_number_withe.png").attribute("data/role2.txt");
				rm.bindImage(ROLE_3, "mr3/font_number_withe.png").attribute("data/role3.txt");
				rm.bindImage(CASTLE + "L", "mr3/castle.png").attribute("data/castle.txt");
				rm.bindImage(CASTLE + "R", "mr3/castle.png").attribute("data/castle.txt");
				
				rm.bindSound("button1.snd", "sound/button-50.mp3");
				rm.bindSound("hit1.snd", "sound/hit1.wav");
				rm.bindSound("powerup1.snd", "sound/powerup1.wav");
				rm.bindSound("dead1.snd", "sound/knock1.wav");
				rm.bindSound("power.snd", "sound/buster1.wav");
				
			}
			
		});
	}

	public Engine getEngine() {
		return engine;
	}
}
