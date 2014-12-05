package com.makeagame.core;

import java.util.ArrayList;
import java.util.Map;

import org.json.JSONObject;

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

	public void call(String id, int command ,JSONObject params) {
		model.process(id , command, params);
	}

	public void call( int command ,JSONObject params) {
		call("ALL" , command, params);
	}
	
	public ArrayList<String> build() {
		return 	model.hold();
	}

}
