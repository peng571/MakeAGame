package com.makeagame.magerevenge;

import java.util.ArrayList;
import java.util.HashMap;
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
	
	// 翻轉, 暫時不管
	public int flipx = 0;
	public int flipy = 0;
	
	// 請參照 opneGL的 setColor
	public double red = 1.0f;
	public double green = 1.0f;
	public double blue = 1.0f;
	public double alpha = 1.0f;
	
	// 請參照 opneGL的 blendfunction
	public int blendmethod = 0;
	
	public String image;
	// 該 image 的中心點
	public int image_cntx = 0;
	public int image_cnty = 0;
	
	public boolean visible = true;
	
	// 階層系統的子物件
	public ArrayList<Sprite> children;

	
	// TODO: 自動抓取中心點(從設定檔?)
	public void reset_image(String image) {
		this.image = image;
		this.image_cntx = 0; // TODO
		this.image_cnty = 0; // TODO
	}
	
	// TODO: 有空可以順便做, 或是也可以不做用applylist.apply(sprite)就好
	public void apply(ApplyList applylist) {
		HashMap<String, Object> map = applylist.map;
		if (map.containsKey("image")) { reset_image((String) map.get("image")); }
		if (map.containsKey("x")) { x = (Integer) map.get("x"); }
		if (map.containsKey("y")) { y = (Integer) map.get("y"); }
		// TODO: .......
		
	}
	
	public ArrayList<RenderEvent> render(int offx, int offy) {
		// 先暫時這樣
		reset_image(this.image);
		
		// 先算出真正的位置
		int x = this.x + offx;
		int y = this.y + offy;
		
		ArrayList<RenderEvent> list = new ArrayList<RenderEvent>();
		for (Sprite c : children) {
			if (c.visible) {
				list.addAll(c.render(x, y));
			}
		}
		
		
		// TODO:	等你增加新的RenderEvent後在自己改動這邊
		// 			優先加入 setColor 和 blendfunction 的支援, 其他的以後再說
		list.add(new RenderEvent(
					ResourceManager.get().fetch(image)).XY(
						x- image_cntx,
						y- image_cnty)
				);
		
		return list;
	}

}
