package com.makeagame.magerevenge;

import org.json.JSONException;
import org.json.JSONObject;

import com.makeagame.core.Controler;
import com.makeagame.core.action.Action;
import com.makeagame.core.action.EventListener;
import com.makeagame.core.view.SignalEvent.Signal;
import com.makeagame.tools.Bar;
import com.makeagame.tools.Button;
import com.makeagame.tools.KeyTable;
import com.makeagame.tools.KeyTable.Frame;
import com.makeagame.tools.KeyTable.Key;
import com.makeagame.tools.SimpleLayout;
import com.makeagame.tools.Sprite;

public class ViewPower extends SimpleLayout {
//    KeyTable keyTable;

    Bar bar;
    Button button;
    Button btn_prev;
    Button btn_next;
    
    SimpleLayout layout_ring;
    SimpleLayout layout_icon;
    SimpleLayout layout_prev;
    SimpleLayout layout_next;
    
    String selectPower;
    
    private static final KeyTable ktReady = new KeyTable(new Frame[] {
            //new Frame(  0    , new Key[] { new Key(".sound", "") }),
            //new Frame(  50    , new Key[] { new Key(".sound", "button1.snd") }),
            //new Frame(  100    , new Key[] { new Key(".sound", "") }),
            new Frame(  0    , new Key[] { new Key("sound", "") }),
            new Frame(  100    , new Key[] { new Key("sound", "powerup1.snd") }),
            new Frame(  200    , new Key[] { new Key("sound", "") }),
            
            new Frame(  0    , new Key[] { new Key("1.alpha", new Double(1.0), KeyTable.INT_SIN) }),
            new Frame(  600    , new Key[] { new Key("1.alpha", new Double(0.3), KeyTable.INT_SIN) }), //80
            new Frame(  1200    , new Key[] { new Key("1.alpha", new Double(1.0), KeyTable.INT_SIN) }), // 160
    }).setLoop(true);
    
    private static final KeyTable ktReady2 = new KeyTable(new Frame[] {
            new Frame(  0    , new Key[] { new Key("sound", "") }),
            new Frame(  100    , new Key[] { new Key("sound", "powerup1.snd") }),
            new Frame(  200    , new Key[] { new Key("sound", "") }),
    });
    
    private static final KeyTable ktUse = new KeyTable(new Frame[] {
            new Frame(  0    , new Key[] { new Key("sound", "") }),
            new Frame(  100    , new Key[] { new Key("sound", "power.snd") }),
            new Frame(  200    , new Key[] { new Key("sound", "") }),
    });
    
    public ViewPower() {
        super();
        
        selectPower = "a";
        XY(24, -53);
        /*
        keyTable = new KeyTable(new Frame[] {
                new Frame(0, new Key[] {
                        new Key("x", new Double(20), KeyTable.INT_LINEAR) }),
                new Frame(10, new Key[] {
                        new Key("x", new Double(100), KeyTable.INT_LINEAR),
                        new Key("red", new Double(0.0f), KeyTable.INT_LINEAR) }),
                new Frame(30, new Key[] {
                        new Key("x", new Double(100), KeyTable.INT_LINEAR),
                        new Key("red", new Double(1.0f), KeyTable.INT_LINEAR) }),
        });
        */
        bar = new Bar();
        bar.setBar(Bar.Direction.COLUMN_REVERSE, 180);

        {
            button = new Button(new Sprite("power_ring"));
            button.onClickAction = new Action(){
                @Override
                public void execute() {
                    ViewPower.this.usePower();
                }
            };
            layout_ring = new SimpleLayout()
                    .addChild(new SimpleLayout(new Sprite("power_ring_inactive")))
                    .addChild(new SimpleLayout(new Sprite("power_ring")));
            
            addChild(layout_ring);
            button.setInactiveSprite(layout_ring.copy());
            button.setActiveAnimation(ktReady);
        }
        
        
        
        
        btn_prev = new Button(new Sprite("power_prev"));
        btn_prev.onClickAction = new Action(){
            @Override
            public void execute() {
                ViewPower.this.prevPower();
            }
        };
        layout_prev = new SimpleLayout(new Sprite("power_prev")).XY(-7, 11);
        {
            SimpleLayout pushed = layout_prev.copy();
            pushed.sprite.red = 0.5f;
            btn_prev.setActiveSprite(layout_prev.copy());
            btn_prev.setPushedSprite(pushed);
        }
        addChild(layout_prev);
        
        btn_next = new Button(new Sprite("power_next"));
        btn_next.onClickAction = new Action(){
            @Override
            public void execute() {
                ViewPower.this.nextPower();
            }
        };
        layout_next = new SimpleLayout(new Sprite("power_next")).XY(136, 126);
        {
            SimpleLayout pushed = layout_next.copy();
            pushed.sprite.red = 0.5f;
            btn_next.setActiveSprite(layout_next.copy());
            btn_next.setPushedSprite(pushed);
        }
        addChild(layout_next);
        
        layout_icon = new SimpleLayout(new Sprite("power_a"));
        addChild(layout_icon);
        
    }
    
    @Override
    public void beforeReslove() {
        super.beforeReslove();
        button.RectArea(realX, realY, 160, 160);
        button.apply(layout_ring);
        
        btn_prev.RectArea(layout_prev.realX, layout_prev.realY, 48, 48);
        btn_prev.apply(layout_prev);
        
        btn_next.RectArea(layout_next.realX, layout_next.realY, 48, 48);
        btn_next.apply(layout_next);
        
        if (bar.percent >= 1.0) {
            button.listener.enable_state.enter(EventListener.Active);
        } else {
            button.listener.enable_state.enter(EventListener.Inactive);
        }
    }
    
    @Override
    public void beforeRender() {
        super.beforeRender();

        //bar.percent += 0.001;
        bar.apply(layout_ring.children.get(1).sprite);
        layout_ring.children.get(1).sprite.apply(ktUse.get(powerApplyTime));
        layout_ring.children.get(0).sprite.apply(
                ktReady2.get(button.listener.action_state.elapsed(EventListener.Static)));
    }
    
    
    public void usePower() {
        //bar.percent = 0.0f;
        
        try {
            Controler.get().call(
                Sign.BATTLE_UsePower, new JSONObject()
                        .put("player", 0)
                        .put("powerType", 0));
        } catch(JSONException e) {
            e.printStackTrace();
        }
    }
    
    public void nextPower() {
        if (selectPower == "a") {
            selectPower = "b";
        } else if (selectPower == "b") {
            selectPower = "c";
        }
        layout_icon.sprite.image = "power_" + selectPower;
    }
    
    public void prevPower() {
        if (selectPower == "b") {
            selectPower = "a";
        } else if (selectPower == "c") {
            selectPower = "b";
        }
        layout_icon.sprite.image = "power_" + selectPower;
    }
    
    private long powerApplyTime;
    public void model(Hold data) {
        bar.percent = data.powerCD;
        powerApplyTime = data.currentTime - data.powerApplyTime;
        
    }
    
}
