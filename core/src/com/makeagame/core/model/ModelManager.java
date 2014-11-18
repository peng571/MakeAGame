package com.makeagame.core.model;

import java.util.ArrayList;
import java.util.HashMap;

// ´xºÞÅÞ¿è
public class ModelManager {

	private static ModelManager instance;
	private HashMap<String, Model> modelMap;

	public static ModelManager get() {
		if (instance == null) {
			instance = new ModelManager();
		}
		return instance;
	}

	private ModelManager() {
		modelMap = new HashMap<String, Model>();
	}

	public void process(String id, String gsonString) {
		// process singo model
		Model model = modelMap.get(id);
		if (model != null) {
			model.process(gsonString);
		} else {
			System.out.println("get error model at " + id);
		}
	}

	public ArrayList<Model> getArray() {
		ArrayList<Model> ms = new ArrayList<Model>();
		ms.addAll(modelMap.values());
		return ms;
	}

	public ArrayList<String> hold() {
		ArrayList<String> holds = new ArrayList<String>();
		for (Model m : getArray()) {
			holds.add(m.hold());
		}
		return holds;
	}

	public void add(String id, Model topMpdel) {
		modelMap.put(id, topMpdel);
	}

	public Model get(String id) {
		return modelMap.get(id);
	}

	public void clear() {
		modelMap.clear();
	}

	public void remove(String id) {
		modelMap.remove(id);
	}

}
