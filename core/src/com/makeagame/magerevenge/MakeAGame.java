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
			public void resourceFactory(ResourceManager resource) {
				resource.bind("background", new Resource().image("mr/background1.png"));
				resource.bind("top_board", new Resource().image("mr/top_board.png"));
				resource.bind("pause", new Resource().image("mr/pause.png"));
				resource.bind("base_hp", new Resource().image("mr/base_hp.png"));
				resource.bind("bottom_board", new Resource().image("mr/bottom_board.png"));
				resource.bind("power_ring", new Resource().image("mr/power_ring.png"));
				resource.bind("power_a", new Resource().image("mr/power_a.png"));
				resource.bind("power_b", new Resource().image("mr/power_b.png"));
				resource.bind("power_c", new Resource().image("mr/power_c.png"));
				resource.bind("power_next", new Resource().image("mr/bottom_next.png"));
				resource.bind("power_prev", new Resource().image("mr/bottom_prev.png"));
				resource.bind("res_icon_money", new Resource().image("mr/res_icon_money.png"));
				resource.bind("res_icon_res1", new Resource().image("mr/res_icon_res1.png"));
				resource.bind("res_icon_res2", new Resource().image("mr/res_icon_res2.png"));
				resource.bind("res_icon_res3", new Resource().image("mr/res_icon_res3.png"));
				resource.bind(ROLE_1, new Resource().image("image/pussy.png").attribute("data/role1.txt"));
				resource.bind(ROLE_2, new Resource().image("image/person91.png").attribute("data/role2.txt"));
				resource.bind(ROLE_3, new Resource().image("image/group9.png").attribute("data/role3.txt"));
				resource.bind(CASTLE + "L", new Resource().image("mr/castle_al.png").attribute("data/castle.txt"));
				resource.bind(CASTLE + "R", new Resource().image("mr/castle_op.png").attribute("data/castle.txt"));
				resource.bind(CASTLE + "btn", new Resource().image("mr/send_icon_soldier1.png"));
				resource.bind(ROLE_1 + "btn", new Resource().image("mr/send_icon_soldier2.png"));
				resource.bind(ROLE_2 + "btn", new Resource().image("mr/send_icon_soldier3.png"));
				resource.bind(ROLE_3 + "btn", new Resource().image("mr/send_icon_soldier4.png"));
				resource.bind(ROLE_4 + "btn", new Resource().image("mr/send_icon_soldier5.png"));
				
				resource.bind("role_walk1", new Resource().image("mr/role_walk1.png"));
				resource.bind("role_walk2", new Resource().image("mr/role_walk2.png"));
				resource.bind("role_walk3", new Resource().image("mr/role_walk3.png"));
				resource.bind("role_walk4", new Resource().image("mr/role_walk4.png"));
				resource.bind("role_attack1", new Resource().image("mr/role_attack1.png"));
				resource.bind("role_attack2", new Resource().image("mr/role_attack2.png"));
				resource.bind("role_attack3", new Resource().image("mr/role_attack3.png"));
				resource.bind("role_hurt", new Resource().image("mr/role_hurt.png"));
				resource.bind("role_fail", new Resource().image("mr/role_fail.png"));
				resource.bind("role_dead", new Resource().image("mr/role_dead.png"));
				
				resource.bind("button1.snd", new Resource().sound("sound/button-50.mp3"));
			}
		});
	}

	public Engine getEngine() {
		return engine;
	}
}
