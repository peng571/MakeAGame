package com.makeagame.core;

import java.util.ArrayList;

import com.makeagame.core.model.ModelManager;

public  final  class Controler {

	public static Controler instance;
	public ModelManager model;

	private Controler() {
		model = ModelManager.get();
	}

	public static Controler get() {
		if (instance == null) {
			instance = new Controler();
		}
		return instance;

	}

	public void call(String id, String jsonString) {
		model.process(id , jsonString);
	}

	public ArrayList<String> build() {
		return 	model.hold();
	}

//	public void loop() {
//		call(new Object[]{});
//		build();
//	}

}
