package com.makeagame.core;

// this version is base on Libgdx
public class Driver {

	public static Driver instance;

	private Driver() {
	}

	public static Driver get() {
		if (instance == null) {
			instance = new Driver();
		}
		return instance;

	}

	public void input(String[] ins) {
	}

	public String[] output() {
		String[] s = new String[] { "1", "2" };
		return s;
	}

}
