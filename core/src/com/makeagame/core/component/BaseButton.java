package com.makeagame.core.component;

import java.util.ArrayList;

import com.makeagame.core.model.Model;
import com.makeagame.core.view.RenderEvent;
import com.makeagame.core.view.SignalEvent;
import com.makeagame.core.view.View;

public abstract class BaseButton implements Model, View {

	float x, y;
	float w, h;

	@Override
	public void signal(ArrayList<SignalEvent> s) {

		// if()

		// TODO Auto-generated method stub

	}

	@Override
	public ArrayList<RenderEvent> render(ArrayList<String> list) {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	public void process(String gsonString) {
//		// TODO Auto-generated method stub
//
//	}

	public void onButtonClick(int key, Action action) {
		if (action != null) {
			action.execute();
		}
	}

	public void onMouceClick(Action action) {
	}

	@Override
	public abstract String hold();

	@Override
	public String info() {
		return "button";
	}

	abstract class Action {
		public abstract void execute();
	}

	
	abstract class onMouceListener {
		abstract boolean onMouceDown();

		abstract boolean onMouceHold();

		abstract boolean onMouceOver();

		abstract boolean onMouceUp();
	}

}
