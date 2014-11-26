package com.makeagame.core.model;

import java.util.ArrayList;

import com.google.gson.Gson;

public class AutoMoveModel extends MovableModel {

	ArrayList<Mession> messionList;
	Mession currentMession;

	int deviation = 5;

	public AutoMoveModel(String gson) {
		super(gson);
		messionList = new ArrayList<AutoMoveModel.Mession>();
	}

	@Override
	public void process(String gsonString) {
		if (!messionList.isEmpty()) {
			if (currentMession == null) {
				currentMession = messionList.get(0);
				currentMession.start(model.x, model.y);
			}
			if (Math.abs(currentMession.x - model.x) < deviation && Math.abs(currentMession.y - model.y) < deviation) {
				model.x = currentMession.x;
				model.y = currentMession.y;
				stop(currentMession.callback);
				messionList.remove(currentMession);
				currentMession = null;
			} else {
				model.sX += Math.abs(currentMession.x - model.x) > deviation ? model.aX * (currentMession.x > model.x ? 1 : -1)
						* (Math.abs(currentMession.x - model.x) > currentMession.slowDownLimitX ? 1 : -1) : 0;
				model.sY += Math.abs(currentMession.y - model.y) > deviation ? model.aY * (currentMession.y > model.y ? 1 : -1)
						* (Math.abs(currentMession.y - model.y) > currentMession.slowDownLimitY ? 1 : -1) : 0;
				model.sX = Math.abs(model.sX) > model.maxSX ? model.maxSX * (model.sX > 0 ? 1 : -1) : model.sX;
				model.sY = Math.abs(model.sY) > model.maxSY ? model.maxSY * (model.sY > 0 ? 1 : -1) : model.sY;
				model.x += model.sX;
				model.y += model.sY;
			}
		}
	}

	public void moveTo(int x, int y, Action callback) {
		// System.out.println("from " + model.x + ", " + model.y + " move to " + x + ", " + y);
		Mession mission = new Mession(x, y, callback);
		messionList.add(mission);
	}

	public void stop(Action callback) {
		model.sX = 0;
		model.sY = 0;
		if (callback != null) {
			callback.execute();
		}
	}

	class Mession {
		public int x;
		public int y;
		public float slowDownLimitX, slowDownLimitY;
		public Action callback;

		public Mession(int dstX, int dstY, Action callback) {
			this.x = dstX;
			this.y = dstY;
			this.callback = callback;
		}

		public Mession(int dstX, int dstY) {
			this(dstX, dstY, null);
		}

		public void start(int srcX, int srcY) {
			int degreeX = Math.abs(srcX - x);
			int degreeY = Math.abs(srcY - y);
			slowDownLimitX = (model.maxSX * model.maxSX) / (2 * model.aX);
			slowDownLimitY = (model.maxSY * model.maxSY) / (2 * model.aY);
			if (slowDownLimitX > degreeX) {
				slowDownLimitX = degreeX / 2;
			}
			if (slowDownLimitY > degreeY) {
				slowDownLimitY = degreeY / 2;
			}
		}
	}

	@Override
	public String hold() {
		return new Gson().toJson(this);
	}

	@Override
	public String info() {
		return "auto move model " + new Gson().toJson(this);
	}

}
