package com.makeagame.core.view;

import java.util.ArrayList;

public interface TopView {

	// 接收外部指令
	public abstract void signal(ArrayList<SignalEvent> s);

	// 送出繪圖指令
	public abstract ArrayList<RenderEvent> render(ArrayList<String> list);
	
	public abstract String info();
}
