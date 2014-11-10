package com.makeagame.core;

public  final  class TopControler {

	public static TopControler instance;
	public TopModel model;

	private TopControler() {
	}

	public static TopControler get() {
		if (instance == null) {
			instance = new TopControler();
		}
		return instance;

	}

	public void call(Object[] signs) {
		model.process();
	}

	public void build() {
		model.hold();
	}

	public void loop() {
		call(new Object[]{});
		build();
	}

}
