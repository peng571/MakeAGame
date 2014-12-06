package com.makeagame.tools;

import java.util.ArrayList;
import java.util.HashMap;


import com.makeagame.core.view.RenderEvent;
import com.makeagame.tools.KeyTable.ApplyList;

public class SimpleLayout {
	// 畫面中實際值
	public int realX;
	public int realY;
		
	// 定值
	public int fixedX;
	public int fixedY;
	
	// 位移值(給動畫用)
	public int x;
	public int y;
	
	public boolean visible = true;
	
	// 階層系統的子物件
	public ArrayList<SimpleLayout> children;
	public Sprite sprite;
	
	public SimpleLayout() {
		this.sprite = new Sprite();
	}
	
	public SimpleLayout(Sprite sprite) {
		this.sprite = sprite;
		if (this.sprite == null) {
			this.sprite = new Sprite();
		}
	}
	
	public SimpleLayout xy(int x, int y) {
		this.fixedX = x;
		this.fixedY = y;
		return this;
	}
	
	public SimpleLayout addChild(SimpleLayout sprite) {
		if (children == null) {
			children = new ArrayList<SimpleLayout>();
		}
		children.add(sprite);
		return this;

	}

	public void removeChildren() {
		if (children != null) {
			children.clear();
		}
		children = null;
	}
	
	public void apply(ApplyList applylist) {
		HashMap<String, Object> map = applylist.map;
		if (map.containsKey("x")) {
			x = ((Double) map.get("x")).intValue();
		}
		if (map.containsKey("y")) {
			y = ((Double) map.get("y")).intValue();
		}
	}
	
	public void beforeRender() {
		
	}
	
	public ArrayList<RenderEvent> renderSelf(int x, int y) {
		beforeRender();
		return sprite.render(x, y);
	}
	
	public void reslove(int offx, int offy) {
		realX = fixedX + this.x + offx;
		realY = fixedY + this.y + offy;
		if (children != null) {
			for (SimpleLayout c : children) {
				c.reslove(realX, realY);
			}
		}
	}
	
	public ArrayList<RenderEvent> render() {

		// 先算出真正的位置
		//int x = fixedX + this.x + offx;
		//int y = fixedY + this.y + offy;
		
		ArrayList<RenderEvent> list = new ArrayList<RenderEvent>();
		list.addAll(renderSelf(realX, realY));

		if (children != null) {
			for (SimpleLayout c : children) {
				if (c.visible) {
					list.addAll(c.render());
				}
			}
		}

		return list;
	}
	
}
