package com.makeagame.magerevenge;

import org.json.JSONException;
import org.json.JSONObject;

import com.makeagame.core.Bootstrap;
import com.makeagame.core.Driver;
import com.makeagame.core.Engine;
import com.makeagame.core.model.Model;
import com.makeagame.core.resource.ResourceSystem;
import com.makeagame.core.resource.plugin.LibgdxDriver;
import com.makeagame.core.resource.plugin.LibgdxProcessor;
import com.makeagame.core.resource.process.RegisterFinder;
import com.makeagame.core.view.View;

/**
 * 遊戲開發者的進入點 在這邊設定要組合的plugin
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
//        
        RegisterFinder finder = new RegisterFinder();
        registerResource(finder);
        
        LibgdxProcessor processor = new LibgdxProcessor();
        processor.setFinder(finder);
//        rs.addProcessor(finder);

        rs.addProcessor(processor);
        
//        
        
    }

    private JSONObject packageData(String path) throws JSONException{
        return new JSONObject().put("path", path);
    }

    private JSONObject packageData(String path, int x, int y, int w, int h) throws JSONException{
        return packageData(path).put("type", "img").put("x", x).put("y", y).put("w", w).put("h", h);
    }

    private JSONObject packageData(String path, String type) throws JSONException{
        return packageData(path).put("type", type);
    }

    private void registerResource(RegisterFinder finder) {
        try {
            finder.register("send_icon_soldier1", packageData("mr3/send_icons.png", 0, 0, 117, 144));
            finder.register("swordman_btn", packageData("mr3/send_icons.png", 117, 0, 117, 144));
            finder.register("send_icon_soldier1_inactive", packageData("mr3/send_icons_inactive.png", 0, 0, 117, 144));
            finder.register("swordman_btn_inactive", packageData("mr3/send_icons_inactive.png", 117, 0, 117, 144));
            finder.register("send_icon_soldier1_inactive2", packageData("mr3/send_icons_inactive2.png", 0, 0, 117, 144));
            finder.register("swordman_btn_inactive2", packageData("mr3/send_icons_inactive2.png", 117, 0, 117, 144));
            finder.register("role_walk1", packageData("mr3/role.png", 0, 0, 124, 100));
            finder.register("role_walk2", packageData("mr3/role.png", 124, 0, 124, 100));
            finder.register("role_walk3", packageData("mr3/role.png", 248, 0, 124, 100));
            finder.register("role_walk4", packageData("mr3/role.png", 372, 0, 124, 100));
            finder.register("role_attack1", packageData("mr3/role.png", 496, 0, 124, 100));
            finder.register("role_attack2", packageData("mr3/role.png", 620, 0, 124, 100));
            finder.register("role_attack3", packageData("mr3/role.png", 744, 0, 124, 100));
            finder.register("role_hurt", packageData("mr3/role.png", 868, 0, 124, 100));
            finder.register("role_fail", packageData("mr3/role.png", 992, 0, 124, 100));
            finder.register("role_dead", packageData("mr3/role.png", 1116, 0, 124, 100));
            finder.register("role1_walk1", packageData("mr3/role1.png", 0, 0, 175, 146));
            finder.register("role1_walk2", packageData("mr3/role1.png", 175, 0, 175, 146));
            finder.register("role1_walk3", packageData("mr3/role1.png", 350, 0, 175, 146));
            finder.register("role1_walk4", packageData("mr3/role1.png", 525, 0, 175, 146));
            finder.register("role1_walk5", packageData("mr3/role1.png", 700, 0, 175, 146));
            finder.register("role1_walk6", packageData("mr3/role1.png", 875, 0, 175, 146));
            finder.register("role1_walk7", packageData("mr3/role1.png", 1050, 0, 175, 146));
            finder.register("role1_walk8", packageData("mr3/role1_2.png", 0, 0, 175, 146));
            finder.register("role1_attack1", packageData("mr3/role1_2.png", 175, 0, 175, 146));
            finder.register("role1_attack2", packageData("mr3/role1_2.png", 350, 0, 175, 146));
            finder.register("role1_attack3", packageData("mr3/role1_2.png", 525, 0, 175, 146));
            finder.register("role1_stand1", packageData("mr3/role1_2.png", 700, 0, 175, 146));
            finder.register("role1_fail1", packageData("mr3/role1_2.png", 875, 0, 175, 146));
            finder.register("role1_dead1", packageData("mr3/role1_2.png", 1050, 0, 175, 146));
            finder.register("role1_dead2", packageData("mr3/role1_2.png", 1225, 0, 175, 146));
            finder.register("role1_dead3", packageData("mr3/role1_2.png", 1400, 0, 175, 146));
            finder.register("eff_damage_rock1", packageData("mr3/eff_damage_rock.png", 0, 0, 128, 128));
            finder.register("eff_damage_rock2", packageData("mr3/eff_damage_rock.png", 128, 0, 128, 128));
            finder.register("eff_damage_rock3", packageData("mr3/eff_damage_rock.png", 256, 0, 128, 128));
            finder.register("eff_damage_rock4", packageData("mr3/eff_damage_rock.png", 384, 0, 128, 128));
            finder.register("fund_icon", packageData("mr3/res.png", 0, 0, 40, 40));
            finder.register("res1_icon", packageData("mr3/res.png", 40, 0, 41, 40));
            finder.register("res2_icon", packageData("mr3/res.png", 81, 0, 41, 40));
            finder.register("res3_icon", packageData("mr3/res.png", 122, 0, 40, 40));
            finder.register("fund_bg", packageData("mr3/res.png", 162, 0, 142, 40));
            finder.register("res_bg", packageData("mr3/res.png", 304, 0, 100, 40));
            finder.register("pause", packageData("mr3/res.png", 404, 0, 48, 48));
            finder.register("background1", packageData("mr3/background1.png", 0, 0, 960, 540));
            finder.register("top_board", packageData("mr3/top_board.png", 0, 0, 960, 100));
            finder.register("top_title", packageData("mr3/top_title.png", 0, 0, 140, 80));
            finder.register("castle_al", packageData("mr3/castle.png", 0, 0, 256, 256));
            finder.register("castle_op", packageData("mr3/castle.png", 256, 0, 256, 256));
            finder.register("bottom_board", packageData("mr3/bottom_board.png", 0, 0, 960, 192));
            finder.register("base_hp", packageData("mr3/base_hp.png", 0, 0, 155, 14));
            finder.register("role_hp", packageData("mr3/role_hp.png", 0, 0, 33, 3));
            finder.register("font_number_withe", packageData("mr3/font_number_withe.png", 0, 0, 120, 24));
            finder.register("power_next", packageData("mr3/power_btn.png", 0, 0, 48, 48));
            finder.register("power_prev", packageData("mr3/power_btn.png", 48, 0, 48, 48));
            finder.register("power_a", packageData("mr3/power.png", 0, 0, 180, 180));
            finder.register("power_b", packageData("mr3/power.png", 180, 0, 180, 180));
            finder.register("power_c", packageData("mr3/power.png", 360, 0, 180, 180));
            finder.register("power_ring", packageData("mr3/power.png", 540, 0, 180, 180));
            finder.register("power_ring_inactive", packageData("mr3/power.png", 720, 0, 180, 180));
            
            /* ATTRIBUTE */
            finder.register("swordman", packageData("data/role1.txt", "atr"));
            // finder.register(ROLE_2, "mr3/font_number_withe.png").attribute("data/role2.txt"));
            // finder.register(ROLE_3, "mr3/font_number_withe.png").attribute("data/role3.txt"));
            finder.register("castleL", packageData("data/castle.txt", "atr"));
            finder.register("castleR", packageData("data/castle.txt", "atr"));
            
            /* SOUND */
            finder.register("button1.snd", packageData("sound/button-50.mp3", "snd"));
            finder.register("hit1.snd", packageData("sound/hit1.wav", "snd"));
            finder.register("powerup1.snd", packageData("sound/powerup1.wav", "snd"));
            finder.register("dead1.snd", packageData("sound/knock1.wav", "snd"));
            finder.register("power.snd", packageData("sound/buster1.wav", "snd"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // 提供跨平台用的程序，目前主要還是透過 Libgdx來實現跨平台
    public LibgdxDriver getApplication() {
        return driver;
    }
}
