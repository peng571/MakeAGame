package com.makeagame.core.model;

import com.google.gson.Gson;
import com.makeagame.core.Bootstrap;

public abstract class MovableModel implements Model {

	public Attribute model;
	public boolean controllable;
	public boolean outable;

	public class Attribute {
		public String id;
		public int x;
		public int y;
		public int initY;
		public int initX;
		public float initSY;
		public float initSX;
		public float sX;
		public float sY;
		public float aX;
		public float aY;
		public float minSX;
		public float minSY;
		public float maxSX;
		public float maxSY;
		public int w;
		public int h;
	}

	public MovableModel(String gson) {
		model = init(gson);
	}

	public Attribute init(String gson) {
		Attribute model = new Gson().fromJson(gson, Attribute.class);
		return model;
	}

	@Override
	public abstract void process(String gsonString);


	@Override
	public String hold() {
		return new Gson().toJson(model);
	}

	@Override
	public String info() {
		return "MovableModel :" + new Gson().toJson(model);
	}

}
