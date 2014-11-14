package com.makeagame.core.model;

import java.util.ArrayList;
import java.util.HashMap;

// ´xºÞÅÞ¿è
public class ModelManager {

	private static ModelManager instance;
	private HashMap<String, TopModel> modelMap;
	private HashMap<String, Integer> groupMap;

	public static ModelManager get() {
		if (instance == null) {
			instance = new ModelManager();
		}
		return instance;

	}

	private ModelManager() {
		modelMap = new HashMap<String, TopModel>();
		groupMap = new HashMap<String, Integer>();
	}

	public void process(String id, String gsonString) {
		// process group
		if (groupMap.containsKey(id)) {
			int groupNum = groupMap.get(id);
			for (int i = 0; i < groupNum; i++) {
				TopModel model = modelMap.get(id + i);
				if (model != null) {
					model.process(gsonString);
				} else {
					System.out.println("get error model at [" + id + i + "]");
				}
			}
		} else {
			// process singo model
			TopModel model = modelMap.get(id);
			if (model != null) {
				model.process(gsonString);
			} else {
				System.out.println("get error model at " + id);
			}
		}
	}

	public ArrayList<TopModel> getArray() {
		ArrayList<TopModel> ms = new ArrayList<TopModel>();
		ms.addAll(modelMap.values());
		return ms;
	}

	public ArrayList<String> hold() {
		ArrayList<String> holds = new ArrayList<String>();
		for (TopModel m : getArray()) {
			holds.add(m.hold());
		}
		return holds;
	}

	public void add(String id, TopModel v) {
		add(id, v, false);
	}

	public void add(String id, TopModel topMpdel, boolean group) {
		String key;
		if (!group) {
			key = id;
		} else {
			int groupCount = groupMap.get(id) == null ? 0 : groupMap.get(id);
			key = id + String.valueOf(groupCount);
			groupCount++;
			groupMap.put(id, groupCount);
		}
		modelMap.put(key, topMpdel);
	}

	public TopModel get(String id) {
		return modelMap.get(id);
	}

	public ArrayList<TopModel> getGroup(String id) {
		int c = groupMap.get(id);
		ArrayList<TopModel> list = new ArrayList<TopModel>();
		for (int i = 0; i < c; i++) {
			list.add(modelMap.get(id + i));
		}
		return list;
	}

	public int getGroupNum(String id) {
		int num = groupMap.get(id) == null ? 0 : groupMap.get(id);
		return num;
	}

	public void clear() {
		modelMap.clear();
		groupMap.clear();
	}

	public void remove(String id) {
		if (groupMap.containsKey(id)) {
			int groupNum = groupMap.get(id);
			for (int i = 0; i < groupNum; i++) {
				modelMap.remove(id + i);
			}
			groupMap.remove(id);
		} else {
			modelMap.remove(id);
		}
	}

}
