package com.makeagame.core.view;

import java.util.ArrayList;

import com.makeagame.core.Controler;

public interface TopView {

	// 接收外部指令
	public abstract void signal(ArrayList<SignalEvent> s);

	// 送出繪圖指令
	public abstract ArrayList<RenderEvent> render();
}
