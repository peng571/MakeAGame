package com.makeagame.magerevenge;

import java.util.ArrayList;

import org.json.JSONException;

import com.google.gson.Gson;
import com.makeagame.core.Controler;
import com.makeagame.core.view.BaseViewComponent;
import com.makeagame.core.view.BaseViewLayout;
import com.makeagame.core.view.NumberView;
import com.makeagame.core.view.RenderEvent;
import com.makeagame.core.view.SignalEvent;
import com.makeagame.core.view.SignalEvent.KeyEvent;
import com.makeagame.core.view.View;
import com.makeagame.tools.Bar;
import com.makeagame.tools.Bar.Direction;
import com.makeagame.tools.ObjectAdapter;
import com.makeagame.tools.SimpleLayout;
import com.makeagame.tools.Sprite;

public class GameView implements View {

    class ViewResTable extends BaseViewLayout {
        BaseViewLayout fund;
        BaseViewLayout res1;
        BaseViewLayout res2;
        BaseViewLayout res3;
        NumberView fund_number;
        NumberView res1_number;
        NumberView res2_number;
        NumberView res3_number;

        public ViewResTable() {
            withXY(206, 0);
            fund_number = (NumberView) new NumberView(new Sprite("font_number_withe"), 12).withXY(110, 11);
            fund_number.setNumber(789456);

            res1_number = (NumberView) new NumberView(new Sprite("font_number_withe"), 12).withXY(63, 11);
            res1_number.setNumber(12);

            res2_number = (NumberView) new NumberView(new Sprite("font_number_withe"), 12).withXY(63, 11);
            res2_number.setNumber(0);

            res3_number = (NumberView) new NumberView(new Sprite("font_number_withe"), 12).withXY(63, 11);
            res3_number.setNumber(1);

            fund = new BaseViewLayout().withSprite(new Sprite("fund_bg"))
                                       .withXY(0, -22)
                    .addChild(new BaseViewComponent().withSprite(new Sprite("fund_icon").XY(4, 0)))
                    .addChild(fund_number);
            
            res1 = new BaseViewLayout().withSprite(new Sprite("res_bg")).withXY(0, 15)
                    .addChild(new BaseViewComponent().withSprite(new Sprite("res1_icon").XY(4, 0)))
                    .addChild(res1_number);
            
            res2 = new BaseViewLayout().withSprite(new Sprite("res_bg"))
                                       .withXY(0, 51)
                   .addChild(new BaseViewComponent().withSprite(new Sprite("res2_icon").XY(3, 0)))
                   .addChild(res2_number);
            
            res3 = new BaseViewLayout().withSprite(new Sprite("res_bg"))
                                       .withXY(0, 87)
                    .addChild(new BaseViewComponent().withSprite(new Sprite("res3_icon").XY(4, 0)))
                    .addChild(res3_number);

            addChild(fund);
            addChild(res1);
            addChild(res2);
            addChild(res3);
        }

        @Override
        public void beforeRender() {
            super.beforeRender();

        }

        public void model(Hold data) {
            this.fund_number.setNumber(data.money);
            this.res1_number.setNumber(data.resource[0]);
            this.res2_number.setNumber(data.resource[1]);
            this.res3_number.setNumber(data.resource[2]);
        }
    }

    class ViewTopBoard extends BaseViewLayout {

        BaseViewLayout top_board;
        BaseViewComponent pause;
        BaseViewComponent hp0, hp1;
        Bar bar0, bar1;

        public ViewTopBoard() {
            withXY(0, 0);
            top_board = new BaseViewLayout().withSprite(new Sprite("top_board").center(480, 0)).withXY(480, 0);
            pause = new BaseViewComponent().withSprite(new Sprite("pause").center(24, 0)).withXY(0, 40);
            hp0 = new BaseViewComponent().withSprite(new Sprite("base_hp")).withXY(-230, 28);
            hp1 = new BaseViewComponent().withSprite(new Sprite("base_hp")).withXY(75, 28);

            addChild(top_board
                    .addChild(hp0)
                    .addChild(hp1));
                    //.addChild(pause));

            bar0 = new Bar();
            bar0.setBar(Direction.ROW_RESVERSE, 155);
            bar1 = new Bar();
            bar1.setBar(Direction.ROW, 155);
        }

        @Override
        public void beforeRender() {
            super.beforeRender();
            bar0.apply(hp0.sprite);
            bar1.apply(hp1.sprite);
        }

        public void model(Hold data) {
            bar0.percent = data.castle[0].hpp;
            bar1.percent = data.castle[1].hpp;
        }

    }

    class ViewStoreScene extends SimpleLayout {

        ObjectAdapter<StoreItem> adapter;

        public ViewStoreScene() {
            this.adapter = new ObjectAdapter<StoreItem>() {
                
                @Override
                public BaseViewLayout createView() {
                    BaseViewLayout layout = new BaseViewLayout();
//                    layout.addChild(sprite)
                    return layout;
                    
                }
                
                @Override
                public void fillItem(StoreItem item) {
                    // TODO Auto-generated method stub
                    
                }
            };
        }
        
        class StoreItem {
            int price;
            int tag;
            
            
        }
    }

    

    class ViewBattleScene extends BaseViewLayout {
        BaseViewLayout background;
        ViewField field;
        ViewTopBoard top_board;

        BaseViewLayout bottom_board;
        ViewPower power_ring;
        ViewResTable res_table;
        ViewCardTable card_table;

        public ViewBattleScene() {
            withXY(0, 0);
            background = new BaseViewLayout().withSprite(new Sprite("background1"));
            field = new ViewField();
            top_board = new ViewTopBoard();

            bottom_board = new BaseViewLayout().withSprite(new Sprite("bottom_board").center(0, 60))
                                               .withXY(0, 408);
            power_ring = new ViewPower();
            res_table = new ViewResTable();
            card_table = new ViewCardTable();

            background.addChild(field)
                    .addChild(top_board)
                    .addChild(bottom_board
                            .addChild(power_ring)
                            .addChild(res_table)
                            .addChild(card_table)
                    );

            addChild(background)
                    .addChild(field)
                    .addChild(top_board)
                    .addChild(bottom_board
                            .addChild(power_ring)
                            .addChild(res_table)
                            .addChild(card_table)
                    );
        }

        public void beforeRender() {
            // background
        }

        public void model(Hold data) {
            res_table.model(data);
            power_ring.model(data);
            card_table.model(data);
            top_board.model(data);

            field.model(data);
        }

    }

    BaseViewLayout currentScreen;
    ViewBattleScene battle_scene;
    ViewStoreScene store_scene;
    
    public GameView() {
        battle_scene = new ViewBattleScene();
        store_scene = new ViewStoreScene();
        
        currentScreen = battle_scene;
    }

    @Override
    public void signal(ArrayList<SignalEvent> signalList) throws JSONException {

        battle_scene.power_ring.button.signal(signalList);
        battle_scene.power_ring.btn_prev.signal(signalList);
        battle_scene.power_ring.btn_next.signal(signalList);
        battle_scene.card_table.signal(signalList);
        // for (int i=0; i<5; i++) {
        // battle_scene.card_table.btn_send_soldiers[i].signal(signalList);
        // }

        for (SignalEvent s : signalList) {
            if (s.type == SignalEvent.MOUSE_EVENT || s.type == SignalEvent.TOUCH_EVENT) {
                if (s.signal.press(KeyEvent.ANY_KEY) && s.action == SignalEvent.ACTION_DOWN) {
                }
                if (s.action == SignalEvent.ACTION_UP) {
                }
            }
        }
        Controler.get().call(0, null);
    }

    ArrayList<RenderEvent> list = new ArrayList<RenderEvent>();

    @Override
    public ArrayList<RenderEvent> render(ArrayList<RenderEvent> list, String build) {

        Hold data = new Gson().fromJson(build, Hold.class);
        battle_scene.model(data);

        currentScreen.reslove(0, 0);
        currentScreen.render(list);
        return list;
    }

}
