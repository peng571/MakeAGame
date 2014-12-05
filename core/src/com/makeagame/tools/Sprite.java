package com.makeagame.tools;

import java.util.ArrayList;
import java.util.HashMap;

import com.makeagame.core.resource.ResourceManager;
import com.makeagame.core.view.RenderEvent;
import com.makeagame.tools.KeyTable.ApplyList;

public class Sprite {
	// 位移
	public int x = 0;
	public int y = 0;

	// 裁切
	public int srcX = 0;
	public int srcY = 0;
	public int srcW = -1;
	public int srcH = -1;
	
	// 縮放, 暫時不管
	//public double scalex = 1.0f;
	//public double scaley = 1.0f;

	// 旋轉, 暫時不管
	//public double rotateAngle = 0.0f;
	
	// 翻轉
	public boolean flipx = false;
	public boolean flipy = false;

	// 請參照 opneGL的 setColor
	public float red = 1.0f;
	public float green = 1.0f;
	public float blue = 1.0f;
	public float alpha = 1.0f;

	// 請參照 opneGL的 blendfunction
	//public int blendmethod = 0;
	
	// 圖片
	public String image;

	// 該 image 的中心點
	public int centerX;
	public int centerY;
	
	
	public void copyFrom(Sprite sprite) {
		this.x = sprite.x;
		this.y = sprite.y;

		this.srcX = sprite.srcX;
		this.srcY = sprite.srcY;
		this.srcW = sprite.srcW;
		this.srcH = sprite.srcH;
	
		this.flipx = sprite.flipx;
		this.flipy = sprite.flipy;

		this.red = sprite.red;
		this.green = sprite.green;
		this.blue = sprite.blue;
		this.alpha = sprite.alpha;

		this.image = sprite.image;
		
		this.centerX = sprite.centerX;
		this.centerY = sprite.centerY;
	}
	
	// 階層系統的子物件
	//public ArrayList<Sprite> children;

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
		this.centerX = x;
		this.centerY = y;
		return this;
	}
	
	public Sprite srcRect(int x, int y, int w, int h) {
		srcX = x;
		srcY = y;
		srcW = w;
		srcH = h;
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

	// TODO: 自動抓取中心點(從設定檔?)
	public void reset_image(String image) {
		this.image = image;
		this.centerX = 0; // TODO
		this.centerY = 0; // TODO
	}

	// TODO: 有空可以順便做, 或是也可以不做用applylist.apply(sprite)就好
	public void apply(ApplyList applylist) {
		HashMap<String, Object> map = applylist.map;
		if (map.containsKey("image")) {
			reset_image((String) map.get("image"));
		}
		if (map.containsKey("x")) {
			x = ((Double) map.get("x")).intValue();
		}
		if (map.containsKey("y")) {
			y = ((Double) map.get("y")).intValue();
		}
		
		if (map.containsKey("srcX")) {
			srcX = ((Double) map.get("srcX")).intValue();
		}
		if (map.containsKey("srcY")) {
			srcY = ((Double) map.get("srcY")).intValue();
		}
		if (map.containsKey("srcW")) {
			srcW = ((Double) map.get("srcW")).intValue();
		}
		if (map.containsKey("srcH")) {
			srcH = ((Double) map.get("srcH")).intValue();
		}
		
		if (map.containsKey("red")) {
			red = ((Double) map.get("red")).floatValue();
		}
		if (map.containsKey("green")) {
			green = ((Double) map.get("green")).floatValue();
		}
		if (map.containsKey("blue")) {
			blue = ((Double) map.get("blue")).floatValue();
		}
		if (map.containsKey("alpha")) {
			alpha = ((Double) map.get("alpha")).floatValue();
		}
		
		// TODO: .......
	}
	
	public ArrayList<RenderEvent> render(int offx, int offy) {
		ArrayList<RenderEvent> list = new ArrayList<RenderEvent>();
		// TODO: 等你增加新的RenderEvent後在自己改動這邊
		// 優先加入 setColor 和 blendfunction 的支援, 其他的以後再說
		if (image != null) {
			/*if (srcW == -1) {
			list.add(new RenderEvent(ResourceManager.get().fetch(image))
					.XY(offx + x - centerX, offy + y - centerY)
					.color(red, green, blue, alpha)
					// .blend(srcFunc, dstFunc)
					);
			} else {*/
				list.add(new RenderEvent(ResourceManager.get().fetch(image))
					.XY(offx + x - centerX, offy + y - centerY)
					.color(red, green, blue, alpha)
					.src(srcX, srcY, srcW, srcH)
					
					// .blend(srcFunc, dstFunc)
				);
			//}
		}
		return list;
	}
	

}
