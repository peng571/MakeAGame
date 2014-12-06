package com.makeagame.magerevenge;

import com.makeagame.tools.Bar;
import com.makeagame.tools.Button2;
import com.makeagame.tools.KeyTable;
import com.makeagame.tools.SimpleLayout;
import com.makeagame.tools.Sprite;
import com.makeagame.tools.KeyTable.Frame;
import com.makeagame.tools.KeyTable.Key;

public class ViewPower extends SimpleLayout {
	KeyTable keyTable;
	KeyTable ktReady;
	double count = 0.0;

	Bar bar;
	Button2 button;
	Button2 btn_prev;
	Button2 btn_next;
	
	SimpleLayout layout_ring;
	SimpleLayout layout_icon;
	SimpleLayout layout_prev;
	SimpleLayout layout_next;
	
	String selectPower;
	
	public ViewPower() {
		super();
		
		selectPower = "a";
		xy(24, -53);
		ktReady = new KeyTable(new Frame[] {
				//new Frame(  0	, new Key[] { new Key(".sound", "") }),
				//new Frame(  50	, new Key[] { new Key(".sound", "button1.snd") }),
				//new Frame(  100	, new Key[] { new Key(".sound", "") }),
				
				new Frame(  0	, new Key[] { new Key("1.alpha", new Double(1.0), KeyTable.INT_SIN) }),
				new Frame(  80	, new Key[] { new Key("1.alpha", new Double(0.3), KeyTable.INT_SIN) }),
				new Frame(  160	, new Key[] { new Key("1.alpha", new Double(1.0), KeyTable.INT_SIN) }),
		}).setLoop(true);
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
			button = new Button2() {
				@Override
				public void OnMouseDown() {
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
		
		
		
		
		btn_prev = new Button2() {
			@Override
			public void OnMouseDown() {
				ViewPower.this.prevPower();
			}
		};
		layout_prev = new SimpleLayout(new Sprite("power_prev")).xy(-7, 11);
		{
			SimpleLayout pushed = layout_prev.copy();
			pushed.sprite.red = 0.5f;
			btn_prev.setActiveSprite(layout_prev.copy());
			btn_prev.setPushedSprite(pushed);
		}
		addChild(layout_prev);
		
		btn_next = new Button2() {
			@Override
			public void OnMouseDown() {
				ViewPower.this.nextPower();
			}
		};
		layout_next = new SimpleLayout(new Sprite("power_next")).xy(136, 126);
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
		button.setRectArea(realX, realY, 160, 160);
		button.apply(layout_ring);
		
		btn_prev.setRectArea(layout_prev.realX, layout_prev.realY, 48, 48);
		btn_prev.apply(layout_prev);
		
		btn_next.setRectArea(layout_next.realX, layout_next.realY, 48, 48);
		btn_next.apply(layout_next);
		
		if (bar.percent >= 1.0) {
			button.enable_state.enter(Button2.Active);
		} else {
			button.enable_state.enter(Button2.Inactive);
		}
	}
	
	@Override
	public void beforeRender() {
		super.beforeRender();

		bar.percent += 0.001;
		bar.apply(layout_ring.children.get(1).sprite);
		count += 0.2;
	}
	
	
	public void usePower() {
		bar.percent = 0.0f;
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
}
