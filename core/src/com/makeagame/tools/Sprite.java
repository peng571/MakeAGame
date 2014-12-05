package com.makeagame.tools;

import java.util.ArrayList;
import java.util.HashMap;

import com.makeagame.core.resource.ResourceManager;
import com.makeagame.core.view.RenderEvent;
import com.makeagame.tools.KeyTable.ApplyList;

public class Sprite {
	// �첾
	public int x = 0;
	public int y = 0;

	// ����
	public int srcX = 0;
	public int srcY = 0;
	public int srcW = -1;
	public int srcH = -1;
	
	// �Y��, �Ȯɤ���
	//public double scalex = 1.0f;
	//public double scaley = 1.0f;

	// ����, �Ȯɤ���
	//public double rotateAngle = 0.0f;
	
	// ½��
	public boolean flipx = false;
	public boolean flipy = false;

	// �аѷ� opneGL�� setColor
	public float red = 1.0f;
	public float green = 1.0f;
	public float blue = 1.0f;
	public float alpha = 1.0f;

	// �аѷ� opneGL�� blendfunction
	//public int blendmethod = 0;
	
	// �Ϥ�
	public String image;

	// �� image �������I
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
	
	// ���h�t�Ϊ��l����
	//public ArrayList<Sprite> children;

	public Sprite(String image) {
		this.image = image;
	}

	// ��ǽu
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

	// TODO: �۰ʧ�������I(�q�]�w��?)
	public void reset_image(String image) {
		this.image = image;
		this.centerX = 0; // TODO
		this.centerY = 0; // TODO
	}

	// TODO: ���ťi�H���K��, �άO�]�i�H������applylist.apply(sprite)�N�n
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
		// TODO: ���A�W�[�s��RenderEvent��b�ۤv��ʳo��
		// �u���[�J setColor �M blendfunction ���䴩, ��L���H��A��
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
