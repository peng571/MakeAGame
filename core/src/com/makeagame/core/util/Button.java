package com.makeagame.core.util;

import java.util.ArrayList;

import com.makeagame.core.model.Model;
import com.makeagame.core.view.RenderEvent;
import com.makeagame.core.view.SignalEvent;
import com.makeagame.core.view.View;

public abstract class Button implements Model, View {

	float x,y;
	float w,h;
	
	@Override
	public void signal(ArrayList<SignalEvent> s) {
		
//		if()
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<RenderEvent> render(ArrayList<String> list) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public abstract Model init(String gsonString) ;

	@Override
	public void process(String gsonString) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public abstract String hold();

	@Override
	public String info() {
		return "button";
	}
	
	

}
