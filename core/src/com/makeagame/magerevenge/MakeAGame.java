package com.makeagame.magerevenge;

import com.makeagame.core.Bootstrap;
import com.makeagame.core.Engine;
import com.makeagame.core.model.ModelManager;
import com.makeagame.core.resource.Resource;
import com.makeagame.core.resource.ResourceManager;
import com.makeagame.core.view.ViewManager;

public class MakeAGame {

	private Engine engine;

	public static String CASTLE = "castle";
	public static String ROLE_1 = "Swordman"; // 劍士(刺客)
	public static String ROLE_2 = "Warrior"; // 戰士(十字軍) 
	public static String ROLE_3 = "Mage"; // 法師
	public static String ROLE_4 = "Shielder"; // (A) 重裝武士

	public MakeAGame() { 

		engine = new Engine(new Bootstrap() {

			@Override
			public void viewFactory(ViewManager manager) {
				manager.add("main", new GameView());
			}

			@Override
			public void modelFactory(ModelManager manager) {
				manager.add("main", new GameModel());
			}

			@Override
			public void resourceFactory(ResourceManager rm) {
				rm.bindImage(ROLE_1 + "btn", "mr2/send_icons.png").src(0, 0, 117, 144);
				rm.bindImage("send_icon_soldier1", "mr2/send_icons.png").src(117, 0, 117, 144);
				rm.bindImage("send_icon_soldier1", "mr2/send_icons.png").src(234, 0, 117, 144);
				rm.bindImage("send_icon_soldier1", "mr2/send_icons.png").src(351, 0, 117, 144);
				rm.bindImage("send_icon_soldier1", "mr2/send_icons.png").src(468, 0, 117, 144);
				rm.bindImage(ROLE_1 + "btn_inactive", "mr2/send_icons_inactive.png").src(0, 0, 117, 144);
				rm.bindImage("send_icon_soldier1_inactive", "mr2/send_icons_inactive.png").src(117, 0, 117, 144);
				rm.bindImage("send_icon_soldier1_inactive", "mr2/send_icons_inactive.png").src(234, 0, 117, 144);
				rm.bindImage("send_icon_soldier1_inactive", "mr2/send_icons_inactive.png").src(351, 0, 117, 144);
				rm.bindImage("send_icon_soldier1_inactive", "mr2/send_icons_inactive.png").src(468, 0, 117, 144);
				rm.bindImage(ROLE_1 + "btn_inactive2", "mr2/send_icons_inactive2.png").src(0, 0, 117, 144);
				rm.bindImage("send_icon_soldier1_inactive2", "mr2/send_icons_inactive2.png").src(117, 0, 117, 144);
				rm.bindImage("send_icon_soldier1_inactive2", "mr2/send_icons_inactive2.png").src(234, 0, 117, 144);
				rm.bindImage("send_icon_soldier1_inactive2", "mr2/send_icons_inactive2.png").src(351, 0, 117, 144);
				rm.bindImage("send_icon_soldier1_inactive2", "mr2/send_icons_inactive2.png").src(468, 0, 117, 144);
				rm.bindImage("role_walk1", "mr2/role.png").src(0, 0, 124, 100);
				rm.bindImage("role_walk2", "mr2/role.png").src(124, 0, 124, 100);
				rm.bindImage("role_walk3", "mr2/role.png").src(248, 0, 124, 100);
				rm.bindImage("role_walk4", "mr2/role.png").src(372, 0, 124, 100);
				rm.bindImage("role_attack1", "mr2/role.png").src(496, 0, 124, 100);
				rm.bindImage("role_attack2", "mr2/role.png").src(620, 0, 124, 100);
				rm.bindImage("role_attack3", "mr2/role.png").src(744, 0, 124, 100);
				rm.bindImage("role_hurt", "mr2/role.png").src(868, 0, 124, 100);
				rm.bindImage("role_fail", "mr2/role.png").src(992, 0, 124, 100);
				rm.bindImage("role_dead", "mr2/role.png").src(1116, 0, 124, 100);
				rm.bindImage("fund_icon", "mr2/res.png").src(0, 0, 40, 40);
				rm.bindImage("res1_icon", "mr2/res.png").src(40, 0, 41, 40);
				rm.bindImage("res2_icon", "mr2/res.png").src(81, 0, 41, 40);
				rm.bindImage("res3_icon", "mr2/res.png").src(122, 0, 40, 40);
				rm.bindImage("fund_bg", "mr2/res.png").src(162, 0, 142, 40);
				rm.bindImage("res_bg", "mr2/res.png").src(304, 0, 100, 40);
				rm.bindImage("pause", "mr2/res.png").src(404, 0, 48, 48);
				rm.bindImage("background", "mr2/background1.jpg").src(0, 0, 960, 540);
				rm.bindImage("top_board", "mr2/top_board.png").src(0, 0, 960, 100);
				rm.bindImage("top_title", "mr2/top_title.png").src(0, 0, 140, 80);
				rm.bindImage("castle_al", "mr2/castle.png").src(0, 0, 256, 256);
				rm.bindImage("castle_op", "mr2/castle.png").src(256, 0, 256, 256);
				rm.bindImage("bottom_board", "mr2/bottom_board.png").src(0, 0, 960, 192);
				rm.bindImage("base_hp", "mr2/base_hp.png").src(0, 0, 155, 14);
				rm.bindImage("role_hp", "mr2/role_hp.png").src(0, 0, 33, 3);
				rm.bindImage("font_number_withe", "mr2/font_number_withe.png").src(0, 0, 120, 24);
				rm.bindImage("power_next", "mr2/power_btn.png").src(0, 0, 48, 48);
				rm.bindImage("power_prev", "mr2/power_btn.png").src(48, 0, 48, 48);
				rm.bindImage("power_a", "mr2/power.png").src(0, 0, 180, 180);
				rm.bindImage("power_b", "mr2/power.png").src(180, 0, 160, 160);
				rm.bindImage("power_c", "mr2/power.png").src(340, 0, 160, 160);
				rm.bindImage("power_ring", "mr2/power.png").src(500, 0, 180, 180);
				rm.bindImage("power_ring_inactive", "mr2/power.png").src(680, 0, 180, 180);

				
				rm.bindImage(ROLE_1, "mr2/font_number_withe.png").attribute("data/role1.txt");
				rm.bindImage(ROLE_2, "mr2/font_number_withe.png").attribute("data/role2.txt");
				rm.bindImage(ROLE_3, "mr2/font_number_withe.png").attribute("data/role3.txt");
				rm.bindImage(CASTLE + "L", "mr2/castle.png").attribute("data/castle.txt");
				rm.bindImage(CASTLE + "R", "mr2/castle.png").attribute("data/castle.txt");
				
				rm.bindSound("button1.snd", "sound/button-50.mp3");
			}
			
//			//@Override
//			public void resourceFactory2(ResourceManager rm) {
//				rm.bindImage("background", "mr/background1.png");
//				rm.bindImage("top_board", "mr/top_board.png");
//				rm.bindImage("pause", "mr/pause.png");
//				rm.bindImage("base_hp", "mr/base_hp.png");
//				rm.bindImage("bottom_board", "mr/bottom_board.png");
//				rm.bindImage("power_ring", "mr/power_ring.png");
//				rm.bindImage("power_ring_inactive", "mr/power_ring_inactive.png");
//				
////				resource.bind("background", new Resource().image("mr/background1.png"));
////				resource.bind("top_board", new Resource().image("mr/top_board.png"));
////				resource.bind("pause", new Resource().image("mr/pause.png"));
////				resource.bind("base_hp", new Resource().image("mr/base_hp.png"));
////				resource.bind("bottom_board", new Resource().image("mr/bottom_board.png"));
////				resource.bind("power_ring", new Resource().image("mr/power_ring.png"));
////				resource.bind("power_ring_inactive", new Resource().image("mr/power_ring_inactive.png"));
//				
//				rm.bindImage("power_a", "mr/power_a.png");
//				rm.bindImage("power_b", "mr/power_b.png");
//				rm.bindImage("power_c", "mr/power_c.png");
//				rm.bindImage("power_next", "mr/bottom_next.png");
//				rm.bindImage("power_prev", "mr/bottom_prev.png");
//				
////				resource.bind("power_a", new Resource().image("mr/power_a.png"));
////				resource.bind("power_b", new Resource().image("mr/power_b.png"));
////				resource.bind("power_c", new Resource().image("mr/power_c.png"));
////				resource.bind("power_next", new Resource().image("mr/bottom_next.png"));
////				resource.bind("power_prev", new Resource().image("mr/bottom_prev.png"));
//				
//				rm.bindImage("font_number_withe", "mr/font_number_withe.png");
//				rm.bindImage("fund_bg", "mr/fund_bg.png");
//				rm.bindImage("res_bg", "mr/res_bg.png");
//				rm.bindImage("fund_icon", "mr/fund_icon.png");
//				rm.bindImage("res1_icon", "mr/res1_icon.png");
//				rm.bindImage("res2_icon", "mr/res2_icon.png");
//				rm.bindImage("res3_icon", "mr/res3_icon.png");
//				
////				resource.bind("font_number_withe", new Resource().image("mr/font_number_withe.png"));
////				resource.bind("fund_bg", new Resource().image("mr/fund_bg.png"));
////				resource.bind("res_bg", new Resource().image("mr/res_bg.png"));
////				resource.bind("fund_icon", new Resource().image("mr/fund_icon.png"));
////				resource.bind("res1_icon", new Resource().image("mr/res1_icon.png"));
////				resource.bind("res2_icon", new Resource().image("mr/res2_icon.png"));
////				resource.bind("res3_icon", new Resource().image("mr/res3_icon.png"));
//				
//				rm.bindImage(ROLE_1, "mr/font_number_withe.png").attribute("data/role1.txt");
//				rm.bindImage(ROLE_2, "mr/font_number_withe.png").attribute("data/role2.txt");
//				rm.bindImage(ROLE_3, "mr/font_number_withe.png").attribute("data/role3.txt");
//				rm.bindImage(CASTLE + "L", "mr/castle_al.png").attribute("data/castle.txt");
//				rm.bindImage(CASTLE + "R", "mr/castle_op.png").attribute("data/castle.txt");
//				
////				rm.bind(ROLE_1, new Resource().image("image/pussy.png").attribute("data/role1.txt"));
////				rm.bind(ROLE_2, new Resource().image("image/person91.png").attribute("data/role2.txt"));
////				rm.bind(ROLE_3, new Resource().image("image/group9.png").attribute("data/role3.txt"));
////				rm.bind(CASTLE + "L", new Resource().image("mr/castle_al.png").attribute("data/castle.txt"));
////				rm.bind(CASTLE + "R", new Resource().image("mr/castle_op.png").attribute("data/castle.txt"));
////				
//				//resource.bind(CASTLE + "btn", new Resource().image("mr/send_icon_soldier1.png"));
//				//resource.bind(ROLE_1 + "btn", new Resource().image("mr/send_icon_soldier2.png"));
//				//resource.bind(ROLE_2 + "btn", new Resource().image("mr/send_icon_soldier3.png"));
//				//resource.bind(ROLE_3 + "btn", new Resource().image("mr/send_icon_soldier4.png"));
//				//resource.bind(ROLE_4 + "btn", new Resource().image("mr/send_icon_soldier5.png"));
//				
//				rm.bindImage(ROLE_1 + "btn", "mr/send_icon_soldier1.png");
//				rm.bindImage(ROLE_1 + "btn_inactive", "mr/send_icon_soldier1_inactive.png");
//				rm.bindImage(ROLE_1 + "btn_inactive2", "mr/send_icon_soldier1_inactive2.png");
//				
////				rm.bind(ROLE_1 + "btn", new Resource().image("mr/send_icon_soldier1.png"));
////				rm.bind(ROLE_1 + "btn_inactive", new Resource().image("mr/send_icon_soldier1_inactive.png"));
////				rm.bind(ROLE_1 + "btn_inactive2", new Resource().image("mr/send_icon_soldier1_inactive2.png"));
//				
//				
//				rm.bindImage("role_walk1", "mr/role_walk1.png");
//				rm.bindImage("role_walk2", "mr/role_walk2.png");
//				rm.bindImage("role_walk3", "mr/role_walk3.png");
//				rm.bindImage("role_walk4", "mr/role_walk4.png");
//				rm.bindImage("role_attack1", "mr/role_attack1.png");
//				rm.bindImage("role_attack2", "mr/role_attack2.png");
//				rm.bindImage("role_attack3", "mr/role_attack3.png");
//				rm.bindImage("role_hurt", "mr/role_hurt.png");
//				rm.bindImage("role_fail", "mr/role_fail.png");
//				rm.bindImage("role_dead", "mr/role_dead.png");
//				rm.bindImage("role_hp", "mr/role_hp.png");
////				rm.bind("role_walk1", new Resource().image("mr/role_walk1.png"));
////				rm.bind("role_walk2", new Resource().image("mr/role_walk2.png"));
////				rm.bind("role_walk3", new Resource().image("mr/role_walk3.png"));
////				rm.bind("role_walk4", new Resource().image("mr/role_walk4.png"));
////				rm.bind("role_attack1", new Resource().image("mr/role_attack1.png"));
////				rm.bind("role_attack2", new Resource().image("mr/role_attack2.png"));
////				rm.bind("role_attack3", new Resource().image("mr/role_attack3.png"));
////				rm.bind("role_hurt", new Resource().image("mr/role_hurt.png"));
////				rm.bind("role_fail", new Resource().image("mr/role_fail.png"));
////				rm.bind("role_dead", new Resource().image("mr/role_dead.png"));
//				
//				rm.bindSound("button1.snd", "sound/button-50.mp3");
//				//rm.bind("button1.snd", new Resource().sound("sound/button-50.mp3"));
//			}
		});
	}

	public Engine getEngine() {
		return engine;
	}
}
