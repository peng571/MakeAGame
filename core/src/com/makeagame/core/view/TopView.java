package com.makeagame.core.view;

import java.util.ArrayList;

public interface TopView {

	// �����~�����O
	public abstract void signal(ArrayList<SignalEvent> s);

	// �e�Xø�ϫ��O
	public abstract ArrayList<RenderEvent> render(ArrayList<String> list);
	
	public abstract String info();
}
