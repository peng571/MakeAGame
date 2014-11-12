package com.makeagame.core.model;

import java.util.ArrayList;

import com.makeagame.core.Controler;

// ´xºÞÅÞ¿è
public class ModelManager {

	private static ModelManager instance;

	private	ArrayList<TopModel> modelList;

	public static ModelManager get() {
		if (instance == null) {
			instance = new ModelManager();
		}
		return instance;

	}

	private ModelManager() {
		modelList = new ArrayList<TopModel>();
	}

	public void addModel(TopModel model) {
		modelList.add(model);
	}

	// Override to allocation Call
	public void process(Object[] signs) {
		for (TopModel m : modelList) {
			m.process(signs);
		}
	}

	public ArrayList<Object> hold() {
		ArrayList<Object> holds = new ArrayList<Object>();
		for (TopModel m : modelList) {
			holds.add(m.hold());
		}
		return holds;
	}

}
