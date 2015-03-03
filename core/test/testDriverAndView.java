package testunit;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.makeagame.nullBackend.NullDriver;
import com.makeagame.core.Engine;
import com.makeagame.core.Bootstrap;

// interface
import com.makeagame.core.Driver;
import com.makeagame.core.view.View;
import com.makeagame.core.model.Model;

import com.makeagame.core.view.RenderEvent;
import com.makeagame.core.view.SignalEvent;

import org.json.JSONException;
import org.json.JSONObject;

// TODO:
// getMainModel 之類的要可以接受 null
// Engine 再取得物件後記得做 null 檢查


public class testDriverAndView {
    
    class MyTopView implements View {
        public int frameCount =  0;
        public ArrayList<SignalEvent> lastSignals = new ArrayList<SignalEvent>();
        public renderEvent = null;
        
        public void signal(ArrayList<SignalEvent> s) {
            lastSignals = new ArrayList<SignalEvent>(s);
        };

        public ArrayList<RenderEvent> render(ArrayList<RenderEvent> list, String s) {
            frameCount += 1;
            if (renderEvent != null) {
                ArrayList<RenderEvent> result = new ArrayList<RenderEvent>()
                result.add(renderEvent);
                return result;
            } else {
                return new ArrayList<RenderEvent>();
            }
        };
    }
    
    class MyTopModel implements Model {
        public void process(int command, JSONObject json) {
            
        }

        public String hold() {
            return "";
        }
    }
    
    NullDriver nullDriver;
    Engine engine;
    MyTopView topView;
    MyTopModel topModel;
    
    void setupEngine() {
        nullDriver = new NullDriver();
        topView = new MyTopView();
        topModel = new MyTopModel();
        
        engine = new Engine(new Bootstrap() {
            
            @Override
            public View getMainView() {
                
                return topView;
            }

            @Override
            public Model getMainModel() {
                return topModel;
            }
            
            @Override
            public Driver getDriver() {
                return nullDriver;
            }
        });
        nullDriver.setEngine(engine);
    }
    
    
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {}

	@Before
	public void setUp() throws Exception {
        setupEngine();
    }

	@After
    public void tearDown() throws Exception {}

    @Test
    public void testTickOne() {
        assertEquals(0, topView.frameCount);
        engine.mainLoop();
        assertEquals(1, topView.frameCount);
        engine.mainLoop();
        assertEquals(2, topView.frameCount);
        
    }
    
    
	@Test
    public void testSignal() {
        // 在還沒有信號輸入之前
        engine.mainLoop();
        assertEquals(0, topView.lastSignals.size());
        
        
        // 模擬一個滑鼠移動
        nullDriver.simMouse(2, 3);
        engine.mainLoop();
        assertEquals(1, topView.lastSignals.size());
        
        
        // 再模擬另一個滑鼠移動
        nullDriver.simMouse(20, 30);
        engine.mainLoop();
        assertEquals(1, topView.lastSignals.size());
        assertEquals(20, topView.lastSignals.get(0).signal.x);
        assertEquals(30, topView.lastSignals.get(0).signal.y);
        
        // 一次兩個訊號
        nullDriver.simMouse(100, 101);
        nullDriver.simMouse(200, 201);
        engine.mainLoop();
        assertEquals(2, topView.lastSignals.size());
        assertEquals(100, topView.lastSignals.get(0).signal.x);
        assertEquals(200, topView.lastSignals.get(1).signal.x);
        
        // 訊號應該不被保留
        engine.mainLoop();
        assertEquals(0, topView.lastSignals.size());
        
	}
    
    // TODO:
    public void testRender() {
        //topView.renderEvent = new RenderEvent()
        //engine.mainLoop();
        
    }
