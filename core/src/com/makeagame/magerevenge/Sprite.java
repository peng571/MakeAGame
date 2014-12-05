package com.makeagame.magerevenge;

import java.util.ArrayList;
import java.util.HashMap;

import com.makeagame.core.Engine;
import com.makeagame.core.resource.ResourceManager;
import com.makeagame.core.view.RenderEvent;
import com.makeagame.magerevenge.KeyTable.ApplyList;

public class Sprite {
	// 位移
	public int x = 0;
	public int y = 0;

	// 縮放, 暫時不管
	public double scalex = 1.0f;
	public double scaley = 1.0f;

	// 旋轉, 暫時不管
	public double rotateAngle = 0.0f;

	// 翻轉
	public boolean flipx = false;
	public boolean flipy = false;

	// 請參照 opneGL的 setColor
	public float red = 1.0f;
	public float green = 1.0f;
	public float blue = 1.0f;
	public float alpha = 1.0f;

	// 請參照 opneGL的 blendfunction
	public int blendmethod = 0;

	// 圖片
	public String image;

	// 該 image 的中心點
	public int image_cntx = 0;
	public int image_cnty = 0;

	public boolean visible = true;

	// 階層系統的子物件
	public ArrayList<Sprite> children;

	public Sprite(String image) {
		this.image = image;
	}

	// 基準線
	public Sprite() {
	}

	public Sprite xy(int x, int y) {
		this.x = x;
		this.y = y;
		return this;
	}

	public Sprite center(int x, int y) {
		this.image_cntx = x;
		this.image_cnty = y;
		return this;
	}

	public Sprite color(float r, float g, float b, float a) {
		this.red = r;
		this.green = g;
		this.blue = b;
		this.alpha = a;
		return this;
	}

	public Sprite flip(boolean x, boolean y) {
		this.flipx = x;
		this.flipy = y;
		return this;
	}

	public Sprite addChild(Sprite sprite) {
		if (children == null) {
			children = new ArrayList<Sprite>();
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

	// TODO: 自動抓取中心點(從設定檔?)
	public void reset_image(String image) {
		this.image = image;
		this.image_cntx = 0; // TODO
		this.image_cnty = 0; // TODO
	}

	// TODO: 有空可以順便做, 或是也可以不做用applylist.apply(sprite)就好
	public void apply(ApplyList applylist) {
		HashMap<String, Object> map = applylist.map;
		if (map.containsKey("image")) {
			reset_image((String) map.get("image"));
		}
		if (map.containsKey("x")) {
			x = (Integer) map.get("x");
		}
		if (map.containsKey("y")) {
			y = (Integer) map.get("y");
		}
		// TODO: .......

	}

	public ArrayList<RenderEvent> render(int offx, int offy) {
		// 先暫時這樣
		//reset_image(this.image);

		// 先算出真正的位置
		int x = this.x + offx;
		int y = this.y + offy;

		ArrayList<RenderEvent> list = new ArrayList<RenderEvent>();

		// TODO: 等你增加新的RenderEvent後在自己改動這邊
		// 優先加入 setColor 和 blendfunction 的支援, 其他的以後再說
		if (image != null) {
			Engine.logI("render " + image + " to " + (x - image_cntx)  + " , " + (y - image_cnty));
			list.add(new RenderEvent(ResourceManager.get().fetch(image))
					.XY(x - image_cntx, y - image_cnty)
					.color(red, green, blue, alpha)
					// .blend(srcFunc, dstFunc)
					);
		}

		if (children != null) {
			for (Sprite c : children) {
				if (c.visible) {
					list.addAll(c.render(x, y));
				}
			}
		}

		return list;
	}

}
