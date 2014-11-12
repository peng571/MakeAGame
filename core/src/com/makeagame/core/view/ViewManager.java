package com.makeagame.core.view;

import java.util.ArrayList;
import java.util.HashMap;

import com.makeagame.core.Controler;

// �x����ܡAframe ���i�X�I
public class ViewManager {

	private HashMap<String, TopView> viewMap;
	private HashMap<String, Integer> groupMap;
	private static ViewManager instance;

	private ViewManager() {
		viewMap = new HashMap<String, TopView>();
	}

	public static ViewManager get() {
		if (instance == null) {
			instance = new ViewManager();
		}
		return instance;
	}

	public void add(String id, TopView v) {
		add(id, v, false);
	}

	public void add(String id, TopView v, boolean group) {
		String key;
		if (!group) {
			key = id;
		} else {
			int groupCount = groupMap.get(id) == null ? 0 : groupMap.get(id);
			groupCount++;
			key = id + String.valueOf(groupCount);
			groupMap.put(id, groupCount);
		}
		viewMap.put(id, v);
	}

	// �����~�����O
	public void signal(ArrayList<SignalEvent> s) {
		for (TopView v : getArray()) {
			v.signal(s);
		}
	}

	public ArrayList<TopView> getArray() {
		ArrayList<TopView> vs = new ArrayList<TopView>();
		vs.addAll(viewMap.values());
		return vs;
	}

	// �e�Xø�ϫ��O
	public ArrayList<RenderEvent> render() {
		ArrayList<RenderEvent> renderList = new ArrayList<RenderEvent>();
		for (TopView v : getArray()) {
			renderList.addAll(v.render());
		}
		renderList.addAll(Controler.get().build());
		return renderList;
	}

}
