package com.makeagame.firstgame;

import java.util.ArrayList;
import java.util.Random;

import com.google.gson.Gson;
import com.makeagame.core.Bootstrap;
import com.makeagame.core.Controler;
import com.makeagame.core.Engine;
import com.makeagame.core.model.AnimationObject;
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

	final static int BALL_W = 60;
	final static int BALL_H = 60;
	final static int ROW = 7;
	final static int COL = 7;

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

		Random rand = new Random();
		final static int BALL_W = 60;
		final static int BALL_H = 60;

		Ball[][] balls = new Ball[ROW][COL];
		int downX, downY;
		int upX, upY;
		int move;

		public GameView()
		{
			String ballInitJson = ResourceManager.get().read("ball");
			for (int i = 0; i < ROW; i++) {
				for (int j = 0; j < COL; j++) {
					balls[i][j] = new Ball(ballInitJson, i, j);
				}
			}
		}

		@Override
		public void signal(ArrayList<SignalEvent> signalList) {
			for (SignalEvent s : signalList) {
				if (s.type == SignalEvent.MOUSE_EVENT || s.type == SignalEvent.TOUCH_EVENT) {
					if (s.signal.press(KeyEvent.ANY_KEY) && s.action == SignalEvent.ACTION_DOWN) {
						sign = new Sign();
						downX = s.signal.x - startX;
						downY = s.signal.y - startY;
					}
					if (s.action == SignalEvent.ACTION_UP) {
						upX = s.signal.x - startX;
						upY = s.signal.y - startY;
						sign.row = downX / BALL_W;
						sign.col = downY / BALL_H;
						move = -1;
						if (Math.abs(downX - upX) > BALL_W / 2) {
							move = downX > upX ? 0 : 2;
						}
						if (Math.abs(downY - upY) > BALL_H / 2) {
							move = downY > upY ? 1 : 3;
						}
						sign.move = move;
						Controler.get().call("main", new Gson().toJson(sign));
					}
				}
			}
		}

		class Ball extends AnimationObject {

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

		@Override
		public ArrayList<RenderEvent> render(ArrayList<String> build) {

			ArrayList<RenderEvent> list = new ArrayList<RenderEvent>();
			for (String s : build) {
				Hold hold = new Gson().fromJson(s, Hold.class);
				if (hold.moved) {
					balls[hold.srcR][hold.srcC].moveTo(balls[hold.dstR][hold.dstC].model.x, balls[hold.dstR][hold.dstC].model.y, null);
					balls[hold.dstR][hold.dstC].moveTo(balls[hold.srcR][hold.srcC].model.x, balls[hold.srcR][hold.srcC].model.y, null);
				}
				for (int i = 0; i < ROW; i++) {
					for (int j = 0; j < COL; j++) {
						balls[i][j].run();
						if (hold.ballMap[i][j] > 0) {
							list.add(new RenderEvent(ResourceManager.get().fetch("ball" + String.valueOf(hold.ballMap[i][j])))
									.XY(startX + balls[i][j].model.x, startY + balls[i][j].model.y).srcWH(ballW, ballH));
						}
					}
				}

				// if (!hold.remove.isEmpty()) {
				// for (Position p : hold.remove) {
				// list.add(new RenderEvent(ResourceManager.get().fetch("boom")).XY(startX + p.r, startY + p.c).srcWH(ballW, ballH));
				// }
				// }
			}

			return list;
		}

		@Override
		public String info() {
			return "main view";
		}

	}

	class GameModel implements Model {

		Random rand = new Random();

		Ball[][] ballMap = new Ball[ROW][COL];
		ArrayList<Ball> remove;
		boolean moved;
		int move;// 0 left, 1 up , 2 right, 3 down

		public GameModel() {
			//
			for (int i = 0; i < ROW; i++) {
				for (int j = 0; j < COL; j++) {
					ballMap[i][j] = new Ball(i, j, rand.nextInt(7) + 1);
				}
			}
			remove = countBall();
			do {
				if (!remove.isEmpty()) {
					for (Ball p : remove) {
						ballMap[p.r][p.c] = new Ball(p.r, p.c, rand.nextInt(7) + 1);
					}
					remove.clear();
				}
				remove = countBall();

			} while (!remove.isEmpty());
		}

		class Ball {
			int type;
			int r;
			int c;

			public Ball(int r, int c, int type) {
				this.type = type;
				this.r = r;
				this.c = c;
			}

			public Ball(int r, int c) {
				this(r, c, -1);
			}
		}

		private ArrayList<Ball> countBall() {
			int countColor = -1;
			ArrayList<Ball> temp = new ArrayList<Ball>();
			ArrayList<Ball> remove = new ArrayList<Ball>();
			for (int i = 0; i < ROW; i++) {
				for (int j = 0; j < COL; j++) {
					Ball p = new Ball(i, j);
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
			for (int j = 0; j < COL; j++) {
				for (int i = 0; i < ROW; i++) {
					Ball p = new Ball(i, j);
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
			for (Ball p : remove) {
				System.out.println("remove " + p.r + ", " + p.c);
			}
			return remove;
		}

		int row, col;
		int nextRaw, nextCal;

		@Override
		public void process(String gsonString) {
			Sign signs = new Gson().fromJson(gsonString, Sign.class);
			if (signs.move != -1) {
				row = signs.row;
				col = signs.col;
				moved = false;
				System.out.println("raw " + row + " , cal " + col);
				if (row >= 0 && row < ROW && col >= 0 && col < COL) {
					nextRaw = row;
					nextCal = col;
					switch (move) {
					case 0: // up
						if (row > 0) {
							nextRaw--;
						}
						break;
					case 1: // left
						if (col > 0) {
							nextCal--;
						}
						break;
					case 2: // down
						if (row < ROW - 1) {
							nextRaw++;
						}
						break;
					case 3: // right
						if (col < COL - 1) {
							nextCal++;
						}
						break;
					}
					moved = true;
					final Ball temp = ballMap[nextRaw][nextCal];
					ballMap[nextRaw][nextCal] = ballMap[row][col];
					ballMap[row][col] = temp;

					remove = countBall();
					for (Ball ball : remove) {
						ballMap[ball.r][ball.c].type = -1;
					}
					for (int i = ROW - 1; i >= 0; i--) {
						for (int j = 0; j < COL; j++) {
							if (ballMap[i][j].type == -1) {
								for (int k = i; k >= 0; k--) {
									if (ballMap[k][j].type != -1) {
										ballMap[i][j].type = ballMap[k][j].type;
										ballMap[k][j].type = -1;
										break;
									}
								}
								if (ballMap[i][j].type == -1) {
									ballMap[i][j].type = rand.nextInt(7) + 1;
								}
							}
						}
					}
				}
			}
		}

		@Override
		public String hold() {
			Hold hold = new Hold();
			for (int i = 0; i < ROW; i++) {
				for (int j = 0; j < COL; j++) {
					hold.ballMap[i][j] = ballMap[i][j].type;
				}
			}
			hold.srcR = row;
			hold.srcC = col;
			hold.dstR = nextRaw;
			hold.dstC = nextCal;
			hold.moved = moved;
			moved = false;
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
		int row;
		int col;
		int move;
	}

	class Hold {
		boolean moved;
		int srcR, srcC;
		int dstR, dstC;
		int[][] ballMap = new int[7][7];
		ArrayList<GameModel.Ball> remove;
	}

}
