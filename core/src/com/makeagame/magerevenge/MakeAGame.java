package com.makeagame.magerevenge;
//
//import java.util.ArrayList;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import com.google.gson.Gson;
//import com.makeagame.core.Bootstrap;
//import com.makeagame.core.Engine;
//import com.makeagame.core.model.Model;
//import com.makeagame.core.resource.ResourceManager;
//import com.makeagame.core.view.RenderEvent;
//import com.makeagame.core.view.SignalEvent;
//import com.makeagame.core.view.SignalEvent.KeyEvent;
//import com.makeagame.core.view.View;
//
//import javax.script.*;
//
///**
// * new game - Free Running 跑酷遊戲
// */
//public class MakeAGame {
//	private Engine engine;
//
//	public Engine getEngine() {
//		return engine;
//	}
//
//	public MakeAGame() {
//		ScriptEngineManager factory = new ScriptEngineManager();
//		ScriptEngine e = factory.getEngineByName("groovy");
//		try {
//			e.eval("println 'Hello'");
//		} catch (ScriptException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		engine = new Engine(new Bootstrap() {
//			@Override
//			public View setMainView() {
//				return new GameView();
//			}
//
//			@Override
//			public Model setMainModel() {
//				return new GameModel();
//			}
//
//			@Override
//			public void resourceFactory(ResourceManager resource) {
//				// TODO:
//				// resource.bind("xx", new Resource().image("image/xx.png"));
//			}
//		});
//	}
//
//	class GameView implements View {
//		int touchStartX, touchStartY;
//
//		@Override
//		public void signal(ArrayList<SignalEvent> signalList) {
//			for (SignalEvent s : signalList) {
//				if (s.type == SignalEvent.MOUSE_EVENT || s.type == SignalEvent.TOUCH_EVENT) {
//					if (s.signal.press(KeyEvent.ANY_KEY) && s.action == SignalEvent.ACTION_DOWN) {
//						touchStartX = s.signal.x;
//						touchStartY = s.signal.y;
//					}
//					if (s.action == SignalEvent.ACTION_UP) {}
//				}
//			}
//		}
//
//		@Override
//		public ArrayList<RenderEvent> render(String build) {
//			ArrayList<RenderEvent> list = new ArrayList<RenderEvent>();
//			Hold hold = new Gson().fromJson(build, Hold.class);
//			return list;
//		}
//
//		@Override
//		public String info() {
//			return "main view";
//		}
//	}
//
//	class GameModel implements Model {
//		public GameModel() {}
//
//		@Override
//		public String hold() {
//			Hold hold = new Hold();
//			return new Gson().toJson(hold);
//		}
//
//		@Override
//		public String info() {
//			return "main model";
//		}
//
//		@Override
//		public void process(int command, JSONObject json) throws JSONException {
//			switch (command) {
//			// TODO
//			}
//		}
//	}
//
//	class Sign {}
//
//	class Hold {}
//}

import com.makeagame.core.Bootstrap;
import com.makeagame.core.Driver;
import com.makeagame.core.Engine;
import com.makeagame.core.model.Model;
import com.makeagame.core.resource.ResourceManager;
import com.makeagame.core.resource.ResourceSystem;
import com.makeagame.core.resource.plugin.LibgdxDriver;
import com.makeagame.core.resource.plugin.LibgdxProcessor;
import com.makeagame.core.resource.process.RegisterFinder;
import com.makeagame.core.view.View;

/**
 * 遊戲開發者的進入點
 * 在這邊設定要組合的plugin
 *
 */
public class MakeAGame {

    private LibgdxDriver driver;
    public MakeAGame() { 

        driver = new LibgdxDriver();
        Engine engine = new Engine(new Bootstrap() {
            
            @Override
            public View getMainView() {
                return  new GameView();
            }

            @Override
            public Model getMainModel() {
                return new GameModel();
            }
            
            @Override
            public Driver getDriver() {
                return driver;
            }
        });
        driver.setEngine(engine);
        
        ResourceSystem rs = ResourceSystem.get();
        
        RegisterFinder finder = new RegisterFinder();
        registerResource(finder);
        rs.addProcessor(finder);

        rs.addProcessor(new LibgdxProcessor());
        
        
    }
    
    private void registerResource(RegisterFinder finder){
        
        finder.register("send_icon_soldier1", "mr3/send_icons.png");
        finder.register(MegaRevenge.ROLE_1 + "btn", "mr3/send_icons.png");
        finder.register("send_icon_soldier1_inactive", "mr3/send_icons_inactive.png");
        finder.register(MegaRevenge.ROLE_1 + "btn_inactive", "mr3/send_icons_inactive.png");
        finder.register("send_icon_soldier1_inactive2", "mr3/send_icons_inactive2.png");
        finder.register(MegaRevenge.ROLE_1 + "btn_inactive2", "mr3/send_icons_inactive2.png");
        finder.register("role_walk1", "mr3/role.png");
        finder.register("role_walk2", "mr3/role.png");
        finder.register("role_walk3", "mr3/role.png");
        finder.register("role_walk4", "mr3/role.png");
        finder.register("role_attack1", "mr3/role.png");
        finder.register("role_attack2", "mr3/role.png");
        finder.register("role_attack3", "mr3/role.png");
        finder.register("role_hurt", "mr3/role.png");
        finder.register("role_fail", "mr3/role.png");
        finder.register("role_dead", "mr3/role.png");
        finder.register("role1_walk1", "mr3/role1.png");
        finder.register("role1_walk2", "mr3/role1.png");
        finder.register("role1_walk3", "mr3/role1.png");
        finder.register("role1_walk4", "mr3/role1.png");
        finder.register("role1_walk5", "mr3/role1.png");
        finder.register("role1_walk6", "mr3/role1.png");
        finder.register("role1_walk7", "mr3/role1.png");
        finder.register("role1_walk8", "mr3/role1_2.png");
        finder.register("role1_attack1", "mr3/role1_2.png");
        finder.register("role1_attack2", "mr3/role1_2.png");
        finder.register("role1_attack3", "mr3/role1_2.png");
        finder.register("role1_stand1", "mr3/role1_2.png");
        finder.register("role1_fail1", "mr3/role1_2.png");
        finder.register("role1_dead1", "mr3/role1_2.png");
        finder.register("role1_dead2", "mr3/role1_2.png");
        finder.register("role1_dead3", "mr3/role1_2.png");
        finder.register("eff_damage_rock1", "mr3/eff_damage_rock.png");
        finder.register("eff_damage_rock2", "mr3/eff_damage_rock.png");
        finder.register("eff_damage_rock3", "mr3/eff_damage_rock.png");
        finder.register("eff_damage_rock4", "mr3/eff_damage_rock.png");
        finder.register("fund_icon", "mr3/res.png");
        finder.register("res1_icon", "mr3/res.png");
        finder.register("res2_icon", "mr3/res.png");
        finder.register("res3_icon", "mr3/res.png");
        finder.register("fund_bg", "mr3/res.png");
        finder.register("res_bg", "mr3/res.png");
        finder.register("pause", "mr3/res.png");
        finder.register("background1", "mr3/background1.png");
        finder.register("top_board", "mr3/top_board.png");
        finder.register("top_title", "mr3/top_title.png");
        finder.register("castle_al", "mr3/castle.png");
        finder.register("castle_op", "mr3/castle.png");
        finder.register("bottom_board", "mr3/bottom_board.png");
        finder.register("base_hp", "mr3/base_hp.png");
        finder.register("role_hp", "mr3/role_hp.png");
        finder.register("font_number_withe", "mr3/font_number_withe.png");
        finder.register("power_next", "mr3/power_btn.png");
        finder.register("power_prev", "mr3/power_btn.png");
        finder.register("power_a", "mr3/power.png");
        finder.register("power_b", "mr3/power.png");
        finder.register("power_c", "mr3/power.png");
        finder.register("power_ring", "mr3/power.png");
        finder.register("power_ring_inactive", "mr3/power.png");


        
        finder.register(MegaRevenge.ROLE_1, "mr3/font_number_withe.png");
        finder.register(MegaRevenge.ROLE_2, "mr3/font_number_withe.png");
        finder.register(MegaRevenge.ROLE_3, "mr3/font_number_withe.png");
        finder.register(MegaRevenge.CASTLE + "L", "mr3/castle.png");
        finder.register(MegaRevenge.CASTLE + "R", "mr3/castle.png");
        
//        finder.register("button1.snd", "sound/button-50.mp3");
//        finder.register("hit1.snd", "sound/hit1.wav");
//        finder.register("powerup1.snd", "sound/powerup1.wav");
//        finder.register("dead1.snd", "sound/knock1.wav");
//        finder.register("power.snd", "sound/buster1.wav");
        
    }
    
    
    
    
    
    
    // 提供跨平台用的程序，目前主要還是透過 Libgdx來實現跨平台
    public LibgdxDriver getApplication(){
        return driver;
    }
    
    
    
}
