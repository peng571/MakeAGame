package com.makeagame.magerevenge;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.makeagame.core.Controler;
import com.makeagame.core.action.Action;
import com.makeagame.core.action.EventListener;
import com.makeagame.core.view.SignalEvent;
import com.makeagame.core.view.SignalEvent.Signal;
import com.makeagame.tools.Bar;
import com.makeagame.tools.Button;
import com.makeagame.tools.KeyTable;
import com.makeagame.tools.KeyTable.Frame;
import com.makeagame.tools.KeyTable.Key;
import com.makeagame.tools.SimpleLayout;
import com.makeagame.tools.Sprite;

public class ViewCardTable extends SimpleLayout {
    
    class ViewSendButton extends SimpleLayout {
        //SimpleLayout back;
        //SimpleLayout front;
        
        SimpleLayout icon;
        SimpleLayout icon_bar;
        
        Bar bar;
        Button button;
        final int selfIndex;
        String type;
        
        public ViewSendButton(int idx, String type) {
            super();
            this.selfIndex = idx;
            this.type = type;
            
            icon = new SimpleLayout();
            addChild(icon);
            
            icon_bar = new SimpleLayout(new Sprite(MegaRevenge.ROLE_1 + "btn_inactive2"));
            addChild(icon_bar);
            
            bar = new Bar();
            bar.setBar(Bar.Direction.COLUMN_REVERSE, 144);
            bar.percent = 1.0f;
            
            button = new Button(new Sprite(MegaRevenge.ROLE_1 + "btn_inactive2"));
            button.onClickAction = new Action(){
                @Override
                public void execute() { 
                    ViewCardTable.this.sendSoldiers(selfIndex); 
                }
            };
            
            KeyTable ktPushed = new KeyTable(new Frame[] {
                    new Frame(  0    , new Key[] { new Key(".sound", "") }),
                    new Frame(  50    , new Key[] { new Key(".sound", "button1.snd") }),
                    new Frame(  100    , new Key[] { new Key(".sound", "") }),
                    
                    new Frame(  0    , new Key[] { new Key("0.y", new Double(7), KeyTable.INT_EXP) }),
                    new Frame(  120    , new Key[] { new Key("0.y", new Double(0), KeyTable.INT_EXP) }),
                    new Frame(  0    , new Key[] { new Key("1.y", new Double(7), KeyTable.INT_EXP) }),
                    new Frame(  120    , new Key[] { new Key("1.y", new Double(0), KeyTable.INT_EXP) }),
                    /*
                    new Frame(  0    , new Key[] { new Key("1.alpha", new Double(1.0), KeyTable.INT_EXP) }),
                    new Frame(  300 , new Key[] { new Key("1.alpha", new Double(0.0), KeyTable.INT_EXP) }),
                    
                    new Frame(  0    , new Key[] { new Key("1.dstFunc", new Integer(GL30.GL_ONE)) }),
                    new Frame(  300 , new Key[] { new Key("1.dstFunc", new Integer(GL30.GL_ONE_MINUS_SRC_ALPHA)) }),
                    */
            });
            
            KeyTable ktStatic = new KeyTable(new Frame[] {
                    //new Frame(  0    , new Key[] { new Key(".sound", "") }),
                    //new Frame(  50    , new Key[] { new Key(".sound", "button1.snd") }),
                    //new Frame(  100    , new Key[] { new Key(".sound", "") }),

                    new Frame(  0    , new Key[] { new Key("1.alpha", new Double(1.0), KeyTable.INT_LOG) }),
                    new Frame(  200 , new Key[] { new Key("1.alpha", new Double(0.0), KeyTable.INT_LOG) }),
            });
            
            button.setInactiveSprite( new SimpleLayout()
                    .addChild(new SimpleLayout(new Sprite(MegaRevenge.ROLE_1 + "btn_inactive")))
                    .addChild(new SimpleLayout(new Sprite(MegaRevenge.ROLE_1 + "btn_inactive")))
            );
            
            button.setActiveSprite( new SimpleLayout()
                    .addChild(new SimpleLayout(new Sprite(MegaRevenge.ROLE_1 + "btn")))
                    .addChild(new SimpleLayout(new Sprite(MegaRevenge.ROLE_1 + "btn_inactive")))
                    );
            
            /*
            button.setHoveredSprite( new SimpleLayout()
                    .addChild(new SimpleLayout(new Sprite(MegaRevenge.ROLE_1 + "btn_inactive")))
                    .addChild(new SimpleLayout(hovered))
            );
            */
            
            //button.setPushedAnimation(ktPushed);
            button.setStaticAnimation(ktStatic);
            button.setInactiveAnimation(ktPushed);
            

            
        }
        
        @Override
        public void beforeReslove() {
            super.beforeReslove();
            button.apply(this.icon);
        }
        
        @Override
        public void beforeRender() {
            button.RectArea(realX-10, realY-10, 117, 144);
            
            bar.percent -= 0.005;
            bar.apply(icon_bar.sprite);
            
            if (bar.percent <= 0.0) {
                button.listener.enable_state.enter(EventListener.Active);
            } else {
                button.listener.enable_state.enter(EventListener.Inactive);
            }
        }
        
    }
    
    
    public ViewSendButton[] send_buttons;
    public String[] typeList;
    
    public ViewCardTable() {
        super();
        XY(349, -13);
        
        typeList = new String[] {
                MegaRevenge.CASTLE,
                MegaRevenge.ROLE_1,
                MegaRevenge.ROLE_2,
                MegaRevenge.ROLE_3,
                MegaRevenge.ROLE_4,
        };
        send_buttons = new ViewSendButton[5];
        
        for (int i=0; i<5; i++) {
            send_buttons[i] = new ViewSendButton(i, typeList[i]);
            send_buttons[i].XY(117*i, 0);
            this.addChild(send_buttons[i]);
        }
    }
    
    
    @Override
    public void beforeReslove() {
        super.beforeReslove();
    }
    
    public void sendSoldiers(int index) {
        try {
            Controler.get().call(
                Sign.BATTLE_SendSoldier, new JSONObject()
                        .put("player", 0)
                        .put("soldierType", typeList[index]));
        } catch(JSONException e) {
            e.printStackTrace();
        }
    }
    
    public void signal(ArrayList<SignalEvent> signalList) {
        for (int i=0; i<5; i++) {
            send_buttons[i].button.signal(signalList);
        }
    }
    
    public void model(Hold data) {
        for (int i=0; i < data.sendcard.length; i++) {
            Hold.SendCard c = data.sendcard[i];
            
            typeList[i] = c.type;
            send_buttons[i].type = c.type;
            send_buttons[i].bar.percent = 1.0f - c.sendCD;
            
        }
    }
}
