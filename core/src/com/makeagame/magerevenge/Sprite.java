package com.makeagame.magerevenge;

import java.util.ArrayList;
import java.util.HashMap;
import com.makeagame.core.resource.ResourceManager;
import com.makeagame.core.view.RenderEvent;
import com.makeagame.magerevenge.KeyTable.ApplyList;

public class Sprite {
	// �첾
	public int x = 0;
	public int y = 0;
	
	// �Y��, �Ȯɤ���
	public double scalex = 1.0f;
	public double scaley = 1.0f;
	
	// ����, �Ȯɤ���
	public double rotateAngle = 0.0f;
	
	// ½��, �Ȯɤ���
	public int flipx = 0;
	public int flipy = 0;
	
	// �аѷ� opneGL�� setColor
	public double red = 1.0f;
	public double green = 1.0f;
	public double blue = 1.0f;
	public double alpha = 1.0f;
	
	// �аѷ� opneGL�� blendfunction
	public int blendmethod = 0;
	
	public String image;
	// �� image �������I
	public int image_cntx = 0;
	public int image_cnty = 0;
	
	public boolean visible = true;
	
	// ���h�t�Ϊ��l����
	public ArrayList<Sprite> children;

	
	// TODO: �۰ʧ�������I(�q�]�w��?)
	public void reset_image(String image) {
		this.image = image;
		this.image_cntx = 0; // TODO
		this.image_cnty = 0; // TODO
	}
	
	// TODO: ���ťi�H���K��, �άO�]�i�H������applylist.apply(sprite)�N�n
	public void apply(ApplyList applylist) {
		HashMap<String, Object> map = applylist.map;
		if (map.containsKey("image")) { reset_image((String) map.get("image")); }
		if (map.containsKey("x")) { x = (Integer) map.get("x"); }
		if (map.containsKey("y")) { y = (Integer) map.get("y"); }
		// TODO: .......
		
	}
	
	public ArrayList<RenderEvent> render(int offx, int offy) {
		// ���Ȯɳo��
		reset_image(this.image);
		
		// ����X�u������m
		int x = this.x + offx;
		int y = this.y + offy;
		
		ArrayList<RenderEvent> list = new ArrayList<RenderEvent>();
		for (Sprite c : children) {
			if (c.visible) {
				list.addAll(c.render(x, y));
			}
		}
		
		
		// TODO:	���A�W�[�s��RenderEvent��b�ۤv��ʳo��
		// 			�u���[�J setColor �M blendfunction ���䴩, ��L���H��A��
		list.add(new RenderEvent(
					ResourceManager.get().fetch(image)).XY(
						x- image_cntx,
						y- image_cnty)
				);
		
		return list;
	}

}
