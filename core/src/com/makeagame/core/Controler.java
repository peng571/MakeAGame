package com.makeagame.core;

import java.util.ArrayList;

import com.makeagame.core.model.ModelManager;
import com.makeagame.core.view.RenderEvent;

public  final  class Controler {

	public static Controler instance;
	public ModelManager model;

	private Controler() {
	}

	public static Controler get() {
		if (instance == null) {
			instance = new Controler();
		}
		return instance;

	}

	public void call(String id, Object[] signs) {
		model.process(signs);
	}

	public ArrayList<RenderEvent> build() {
		model.hold();
		return new ArrayList<RenderEvent>();
	}

//	public void loop() {
//		call(new Object[]{});
//		build();
//	}

}
