package com.makeagame.core.view;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;

import com.makeagame.core.Controler;

// �x����ܡAframe ���i�X�I
public class ViewManager {

	private HashMap<String, View> viewMap;
	private static ViewManager instance;

	private ViewManager() {
		viewMap = new HashMap<String, View>();
	}

	public static ViewManager get() {
		if (instance == null) {
			instance = new ViewManager();
		}
		return instance;
	}

	public void add(String id, View v) {
		viewMap.put(id, v);
	}


	// �����~�����O
	public void signal(ArrayList<SignalEvent> s) {
		for (View v : getArray()) {
			try {
				v.signal(s);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	public ArrayList<View> getArray() {
		ArrayList<View> vs = new ArrayList<View>();
		vs.addAll(viewMap.values());
		return vs;
	}

	// �e�Xø�ϫ��O
	public ArrayList<RenderEvent> render() {
		ArrayList<String> build = Controler.get().build();
		ArrayList<RenderEvent> renderList = new ArrayList<RenderEvent>();
		for (View v : getArray()) {
			renderList.addAll(v.render(build));
		}
		return renderList;
	}

}
