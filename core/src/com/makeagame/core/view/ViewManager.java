package com.makeagame.core.view;

import java.util.ArrayList;

import com.makeagame.core.Controler;

// �x����ܡAframe ���i�X�I
public class ViewManager {

	// �����~�����O
	public void signal(ArrayList<SignalEvent> s)
	{
		
	}
	
	
	// �e�Xø�ϫ��O
	public ArrayList<RenderEvent>  render()
	{
		 ArrayList<RenderEvent> renderList = new  ArrayList<RenderEvent>();
		 renderList.addAll(Controler.get().build());
		return renderList;
		
	}
	
}
