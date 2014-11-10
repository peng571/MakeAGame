package com.makeagame.core.model;

import java.util.ArrayList;

import com.makeagame.core.Controler;

// ´xºÞÅÞ¿è
public class ModelManager {

	public static ModelManager instance;

	ArrayList<Model> modelList;

	public static ModelManager get() {
		if (instance == null) {
			instance = new ModelManager();
		}
		return instance;

	}

	public ModelManager() {
		modelList = new ArrayList<Model>();
	}

	public void addModel(Model model) {
		modelList.add(model);
	}

	// Override to allocation Call
	public void process(Object[] signs) {
		for (Model m : modelList) {
			m.process();
		}
	}

	public ArrayList<Object> hold() {
		ArrayList<Object> holds = new ArrayList<Object>();
		for (Model m : modelList) {
			holds.add(m.hold());
		}
		return holds;
	}

}
