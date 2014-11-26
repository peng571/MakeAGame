package com.makeagame.firstgame;

import java.util.ArrayList;
import java.util.Random;

import com.google.gson.Gson;
import com.makeagame.core.Bootstrap;
import com.makeagame.core.Controler;
import com.makeagame.core.Engine;
import com.makeagame.core.model.Action;
import com.makeagame.core.model.AutoMoveModel;
import com.makeagame.core.model.Model;
import com.makeagame.core.model.ModelManager;
import com.makeagame.core.resource.Resource;
import com.makeagame.core.resource.ResourceManager;
import com.makeagame.core.view.RenderEvent;
import com.makeagame.core.view.SignalEvent;
import com.makeagame.core.view.SignalEvent.KeyEvent;
import com.makeagame.core.view.View;
import com.makeagame.core.view.ViewManager;

/**
 * not finish yet
 * 
 * @author Peng
 * 
 */
public class PuzzleFrog {

	// private int gameState = -1; // 1 user move, 2 ball remove, 3 new ball drop
	private Engine engine;

	public PuzzleFrog() {

		engine = new Engine(new Bootstrap() {

			@Override
			public void viewFactory(ViewManager manager) {
				manager.add("main", new GameView());
			}

			@Override
			public void modelFactory(ModelManager manager) {
				manager.add("main", new GameModel());
			}

			@Override
			public void resourceFactory(ResourceManager resource) {
				resource.bind("ball", new Resource().attribute("data/automove.txt"));
				resource.bind("ball1", new Resource().image("image/black.png"));
				resource.bind("ball2", new Resource().image("image/blue.png"));
				resource.bind("ball3", new Resource().image("image/grey.png"));
				resource.bind("ball4", new Resource().image("image/green.png"));
				resource.bind("ball5", new Resource().image("image/orange.png"));
				resource.bind("ball6", new Resource().image("image/pink.png"));
				resource.bind("ball7", new Resource().image("image/red.png"));
				resource.bind("boom", new Resource().image("image/boom3.png"));
			}
		});
	}

	public Engine getEngine() {
		return engine;
	}

	class GameView implements View {

		int startX = 100, startY = 100;
		int ballW = 48;
		int ballH = 48;
		Sign sign;

		@Override
		public void signal(ArrayList<SignalEvent> signalList) {
			for (SignalEvent s : signalList) {
				if (s.type == SignalEvent.MOUSE_EVENT || s.type == SignalEvent.TOUCH_EVENT) {
					if (s.signal.press(KeyEvent.ANY_KEY) && s.action == SignalEvent.ACTION_DOWN) {
						sign = new Sign();
						sign.downX = s.signal.x - startX;
						sign.downY = s.signal.y - startY;
					}
					if (s.action == SignalEvent.ACTION_UP) {
						sign.upX = s.signal.x - startX;
						sign.upY = s.signal.y - startY;
						Controler.get().call("main", new Gson().toJson(sign));
						break;
					}
				}
			}
			Controler.get().call("main", "");
		}

		@Override
		public ArrayList<RenderEvent> render(ArrayList<String> build) {

			ArrayList<RenderEvent> list = new ArrayList<RenderEvent>();
			for (String s : build) {
				Hold hold = new Gson().fromJson(s, Hold.class);
				for (Position p : hold.ballMap) {
					list.add(new RenderEvent(ResourceManager.get().fetch("ball" + p.type)).XY(startX + p.x, startY + p.y).srcWH(ballW, ballH));
				}

				if (!hold.remove.isEmpty()) {
					for (Position p : hold.remove) {
						list.add(new RenderEvent(ResourceManager.get().fetch("boom")).XY(startX + p.x, startY + p.y).srcWH(ballW, ballH));
					}
				}
			}

			return list;
		}

		@Override
		public String info() {
			return "main view";
		}

	}

	class GameModel implements Model {

		final static int BALL_W = 60;
		final static int BALL_H = 60;
		final static int RAW = 7;
		final static int CAL = 7;
		Ball[][] ballMap = new Ball[RAW][CAL];
		ArrayList<Position> remove;
		Random rand = new Random();
		boolean moved;
		int move;// 0 left, 1 up , 2 right, 3 down

		class Ball extends AutoMoveModel {

			public int type;

			public Ball(String gson, int raw, int col) {
				super(gson);
				model.x = BALL_W * raw;
				model.y = BALL_H * col;
				System.out.println("add new ball " + model.x + ", " + model.y);
				model.h = BALL_H;
				model.w = BALL_W;
				type = rand.nextInt(7) + 1;
			}
		}

		public GameModel() {
			String ballInitJson = ResourceManager.get().read("ball");
			for (int i = 0; i < RAW; i++) {
				for (int j = 0; j < CAL; j++) {
					ballMap[i][j] = new Ball(ballInitJson, i, j);
				}
			}
			remove = countBall();
			do {
				if (!remove.isEmpty()) {
					for (Position p : remove) {
						ballMap[p.x / BALL_W][p.y / BALL_H].type = rand.nextInt(7) + 1;
					}
					remove.clear();
				}
				remove = countBall();

			} while (!remove.isEmpty());
		}

		private ArrayList<Position> countBall() {
			int countColor = -1;
			ArrayList<Position> temp = new ArrayList<Position>();
			ArrayList<Position> remove = new ArrayList<Position>();
			for (int i = 0; i < RAW; i++) {
				for (int j = 0; j < CAL; j++) {
					Position p = new Position(ballMap[i][j].model.x, ballMap[i][j].model.y);
					if (ballMap[i][j].type == countColor) {
						temp.add(p);
					} else {
						if (temp.size() >= 3) {
							remove.addAll(temp);
						}
						temp.clear();
						temp.add(p);
						countColor = ballMap[i][j].type;
					}
				}
			}
			countColor = -1;
			temp.clear();
			for (int j = 0; j < CAL; j++) {
				for (int i = 0; i < RAW; i++) {
					Position p = new Position(ballMap[i][j].model.x, ballMap[i][j].model.y);
					if (ballMap[i][j].type == countColor) {
						temp.add(p);
					} else {
						if (temp.size() >= 3) {
							remove.addAll(temp);
						}
						temp.clear();
						temp.add(p);
						countColor = ballMap[i][j].type;
					}
				}
			}
			for (Position p : remove) {
				System.out.println("remove " + p.x + ", " + p.y);
			}
			return remove;
		}

		int raw, cal;
		int nextRaw, nextCal;

		@Override
		public void process(String gsonString) {
			Sign signs = new Gson().fromJson(gsonString, Sign.class);

			if (signs != null) {
				raw = signs.downX / BALL_W;
				cal = signs.downY / BALL_H;
				move = -1;
				if (Math.abs(signs.downX - signs.upX) > BALL_W / 2) {
					move = signs.downX > signs.upX ? 0 : 2;
				}
				if (Math.abs(signs.downY - signs.upY) > BALL_H / 2) {
					move = signs.downY > signs.upY ? 1 : 3;
				}
				if (move != -1) {
					moved = false;
					System.out.println("raw " + raw + " , cal " + cal);
					if (raw >= 0 && raw < RAW && cal >= 0 && cal < CAL) {
						nextRaw = raw;
						nextCal = cal;
						switch (move) {
						case 0: // up
							if (raw > 0) {
								nextRaw--;
							}
							break;
						case 1: // left
							if (cal > 0) {
								nextCal--;
							}
							break;
						case 2: // down
							if (raw < RAW - 1) {
								nextRaw++;
							}
							break;
						case 3: // right
							if (cal < CAL - 1) {
								nextCal++;
							}
							break;
						}

						moved = true;
						final Ball temp = ballMap[nextRaw][nextCal];
						final Ball temp2 = ballMap[raw][cal];
						temp.moveTo(temp2.model.x, temp2.model.y, null);
						temp2.moveTo(temp.model.x, temp.model.y, new Action() {
							@Override
							public void execute() {
								ballMap[raw][cal] = temp;
								ballMap[nextRaw][nextCal] = temp2;
								remove = countBall();
							}
						});

					}
				}
			}
			for (int i = 0; i < RAW; i++) {
				for (int j = 0; j < CAL; j++) {
					ballMap[i][j].process("");
				}
			}
		}

		@Override
		public String hold() {
			Hold hold = new Hold();
			for (int i = 0; i < RAW; i++) {
				for (int j = 0; j < CAL; j++) {
					hold.ballMap.add(new Position(ballMap[i][j].model.x, ballMap[i][j].model.y, ballMap[i][j].type));
				}
			}
			hold.remove = remove;
			remove.clear();
			return new Gson().toJson(hold);
		}

		@Override
		public String info() {
			return "main model";
		}

	}

	class Sign {
		int downX;
		int downY;
		int upX;
		int upY;
	}

	class Hold {
		ArrayList<Position> ballMap = new ArrayList<Position>();
		ArrayList<Position> remove;
	}

	class Position {
		int type;
		int x;
		int y;

		public Position(int x, int y, int type) {
			this.x = x;
			this.y = y;
			this.type = type;
		}

		public Position(int x, int y) {
			this(x, y, -1);
		}
	}

}
