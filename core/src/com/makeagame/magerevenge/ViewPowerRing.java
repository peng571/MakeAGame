package com.makeagame.magerevenge;

import com.makeagame.tools.Bar;
import com.makeagame.tools.Button2;
import com.makeagame.tools.KeyTable;
import com.makeagame.tools.SimpleLayout;
import com.makeagame.tools.Sprite;
import com.makeagame.tools.KeyTable.Frame;
import com.makeagame.tools.KeyTable.Key;

public class ViewPowerRing extends SimpleLayout {
	KeyTable keyTable;
	double count = 0.0;

	Bar bar;
	Button2 button;
	Button2 btn_prev;
	Button2 btn_next;
	
	SimpleLayout layout_icon;
	SimpleLayout layout_prev;
	SimpleLayout layout_next;
	
	String selectPower;
	
	public ViewPowerRing() {
		super(new Sprite("power_ring"));

		selectPower = "a";
		xy(34, -35);
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
		bar = new Bar();
		bar.setBar(Bar.Direction.COLUMN_REVERSE, 160);

		button = new Button2();
		{
			Sprite active = new Sprite("power_ring");
			active.alpha = 1.0f;
			Sprite hovered = new Sprite("power_ring");
			hovered.alpha = 0.5f;
			
			button.setActiveSprite(active);
			button.setHoveredSprite(hovered);
		}
		
		btn_prev = new Button2() {
			@Override
			public void OnMouseDown() {
				ViewPowerRing.this.prevPower();
			}
		};
		layout_prev = new SimpleLayout(new Sprite("power_prev"));
		{
			Sprite active = new Sprite("power_prev");
			active.alpha = 0.5f;
			Sprite hovered = new Sprite("power_prev");
			hovered.red = 2.0f;
			
			btn_prev.setActiveSprite(active);
			btn_prev.setHoveredSprite(hovered);
		}
		addChild(layout_prev);
		
		btn_next = new Button2() {
			@Override
			public void OnMouseDown() {
				ViewPowerRing.this.nextPower();
			}
		};
		layout_next = new SimpleLayout(new Sprite("power_next"));
		{
			Sprite active = new Sprite("power_next");
			active.alpha = 0.5f;
			Sprite hovered = new Sprite("power_next");
			hovered.red = 2.0f;
			
			btn_next.setActiveSprite(active);
			btn_next.setHoveredSprite(hovered);
		}
		addChild(layout_next);
		
		layout_icon = new SimpleLayout(new Sprite("power_a"));
		addChild(layout_icon);
		
	}

	@Override
	public void beforeRender() {
		super.beforeRender();
		
		button.setRectArea(realX, realY, 160, 160);
		button.apply(this.sprite);
		
		btn_prev.setRectArea(realX-30, realY-30, 60, 60);
		btn_prev.apply(layout_prev.sprite);
		
		btn_next.setRectArea(realX+100, realY+100, 60, 60);
		btn_next.apply(layout_next.sprite);
		
		bar.percent += 0.01;
		bar.apply(this.sprite);
		count += 0.2;
		//this.sprite.apply(keyTable.get(count));
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
