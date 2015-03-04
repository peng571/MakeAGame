package com.makeagame.core;

import java.util.ArrayList;

import org.json.JSONException;

import com.makeagame.core.view.RenderEvent;
import com.makeagame.core.view.SignalEvent;

/**
 * 處理訊號的輸入輸出.
 * 提供整個遊戲的迴圈幫浦.
 * 提供除錯和紀錄功能.
 */
public class Engine {

    public static boolean LOG = true;
    public static boolean DEBUG = true;
    public static final String TAG = "MakeAGame";

//    private Bootstrap bootstrap;
    private Driver driver;

    static private ArrayList<SignalEvent> signalList = new ArrayList<SignalEvent>();
    static private ArrayList<RenderEvent> renderList = new ArrayList<RenderEvent>();
    
    public Engine(Bootstrap bootstrap) {
//        this.bootstrap = bootstrap;
        
        driver = bootstrap.getDriver();
        driver.setEngine(this);
        
        Controler.get().register( bootstrap.getMainModel(), bootstrap.getMainView());
//        bootstrap.resourceFactory(Resource2Manager.get());
    }

    

    // 主要迴圈: 所有的使用者操作指令，和繪圖指令都在這邊整合
    public void mainLoop() {
        
        signalList = driver.signal(signalList);
        try {
            Controler.get().mainView.signal(signalList);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        signalList.clear();        
        
        
        renderList =  Controler.get().mainView.render(renderList, Controler.get().build());
        // Engine.logI("renderList size: " + new Integer(renderList.size()).toString());
        renderList = driver.render(renderList);
        renderList.clear();
    }

    
    public static void logI(String s) {
        if (LOG) {
            // Gdx.app.log(TAG, s);
            System.out.println(s);
        }
    }

    public static void logD(String d) {
        if (LOG) {
            // Gdx.app.debug(TAG, d);
            System.out.println(d);
        }
    }

    public static void logE(String e) {
        // Gdx.app.error(TAG, e);
        System.out.println(e);
    }

    public static void logE(Exception e) {
        // Gdx.app.error(TAG, null, e);
        System.out.println(e);
    }

    
}
