package com.makeagame.core.view;

import java.util.ArrayList;

import com.makeagame.core.Controler;

// 掌管顯示，frame 的進出點
public class ViewManager {

	// 接收外部指令
	public void signal(ArrayList<SignalEvent> s)
	{
		
	}
	
	
	// 送出繪圖指令
	public ArrayList<RenderEvent>  render()
	{
		 ArrayList<RenderEvent> renderList = new  ArrayList<RenderEvent>();
		 renderList.addAll(Controler.get().build());
		return renderList;
		
	}
	
}
