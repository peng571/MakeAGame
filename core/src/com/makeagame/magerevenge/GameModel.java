package com.makeagame.magerevenge;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.makeagame.core.component.Position;
import com.makeagame.core.model.Model;
import com.makeagame.core.resource.ResourceManager;
import com.makeagame.tools.State;

public class GameModel implements Model {

	public final static String SCREEN_MAIN = "main";
	public final static String SCREEN_BATTLE = "battle";
	public final static String SCREEN_MENU = "levelmenu";

	String screen; // "main", "battle", "levelmenu"
	boolean isStoreOpen;
	int currentTime;

	boolean start;
	Random rand = new Random();
	LinkedList<Role> roles;

	State moneyGetState;
	long moneyGetTime = 600;
	Player[] player; // You & computer(before change to online mode)
	int moneyGet = 15;

	// int maxCastleLevel = 3;

	public GameModel() {
		startLevel(1, 1);
	}

	private void startLevel(int level, int difficulty) {
		screen = "battle";
		player = new Player[] { new Player(0), new Player(1, level) };
		roles = new LinkedList<Role>();
		roles.add(new Role(ResourceManager.get().read(MakeAGame.CASTLE + "L"), 0));
		roles.add(new Role(ResourceManager.get().read(MakeAGame.CASTLE + "R"), 1));
		moneyGetState = new State(new long[][] { { State.BLOCK, moneyGetTime }, { State.ALLOW, State.BLOCK } });
		start = true;
	}

	private void pause() {
		start = false;
		// TODO
	}

	private void resumeGame() {
		start = true;
		// TODO
	}

	private void gameOver() {
		start = false;
		// TODO
	}

	@Override
	public void process(int command, JSONObject params) throws JSONException {
		State.setNowTime(System.currentTimeMillis());
		if (start) {
			switch (command)
			{
			case Sign.MAIN_NewGame:
				startLevel(params.getInt("level"), params.getInt("difficulty"));
				break;
			case Sign.MAIN_EnterLevelMenu:
				// TODO
				break;
			case Sign.MAIN_StartMenu:
				// TODO
				break;
			case Sign.BATTLE_SendSoldier:
				String soldierType = params.getString("soldierType");
				int soldierId = 0;
				if (soldierType.equals(MakeAGame.ROLE_1)) {
					soldierId = 1;
				} else if (soldierType.equals(MakeAGame.ROLE_2)) {
					soldierId = 2;
				} else if (soldierType.equals(MakeAGame.ROLE_3)) {
					soldierId = 3;
				} else if (soldierType.equals(MakeAGame.ROLE_4)) {
					soldierId = 4;
				}
				player[params.getInt("player")].click(soldierId);
				break;
			case Sign.BATTLE_UsePower:
				player[params.getInt("player")].power(params.getInt("powerType"));
				break;
			case Sign.BATTLE_Upgrade:
				player[params.getInt("player")].click(0);
				break;
			case Sign.BATTLE_UseItem:
				// TODO
				break;
			case Sign.BATTLE_Pause:
				if (params.getBoolean("toggle")) {
					pause();
				} else {
					resumeGame();
				}
				break;
			case Sign.BATTLE_Surrender:
				gameOver();
				break;
			case Sign.STORE_OpenStore:
				// TODO
				break;
			case Sign.STORE_Checkout:
				// TODO
				break;
			case Sign.STORE_Deal:
				// TODO
				break;
			case Sign.DEBUG_AddMoney:
				player[0].totalMoney += params.getInt("amonut");
				break;
			case Sign.DEBUG_ResetColddown:

				// TODO
				break;
			case Sign.DEBUG_PrintData:
				System.out.println(hold());
				break;
			default:
				// Engine.logE("get unknow command " + command);
			}

			// earn money
			if (moneyGetState.enter(1)) {
				player[0].totalMoney += moneyGet * player[0].castleLevel;
				player[1].totalMoney += moneyGet * player[1].castleLevel;
				moneyGetState.enter(0);
			}
			player[1].ai();

			// run role
			for (ListIterator<Role> it = roles.listIterator(); it.hasNext();) {
				Role r = it.next();
				if (r.state.currentStat() != Role.STATE_RECYCLE) {
					r.run();
				} else {
					it.remove();
				}
			}
		}
	}

	long powerApplyTime;

	@Override
	public String hold() {
		Hold hold = new Hold();
		hold.screen = screen;
		if (screen.equals(SCREEN_BATTLE)) {
			hold.money = player[0].totalMoney;
			hold.resource = new int[] { 0, 0, 0 };
			hold.sendcard = new Hold.SendCard[player[0].sendCards.length];
			for (int i = 0; i < player[0].sendCards.length; i++) {
				hold.sendcard[i] = player[0].sendCards[i].hold();
			}
			hold.soldier = new ArrayList<Hold.Unit>();
			hold.castle = new Hold.Unit[2];

//			Collections.sort(roles, new Comparator<Role>() {
//				@Override
//				public int compare(Role r1, Role r2) {
//					if (r1.m.y < r2.m.y) {
//						return -1;
//					}
//					if (r1.m.y > r2.m.y) {
//						return 1;
//					}
//					return 0;
//				}
//			});

			for (Role r : roles) {
				if (!r.m.id.equals(MakeAGame.CASTLE)) {
					hold.soldier.add(r.hold());
				} else {
					int id = r.m.group;
					hold.castle[id] = r.hold();
				}
			}
			hold.powerApplyTime = powerApplyTime;
			hold.powerCD = (float) player[0].skillCDState.elapsed() / (float) player[0].skillCDTime;
		}
		hold.isStoreOpen = false;
		hold.currentTime = State.global_current;
		return new Gson().toJson(hold);
	}

	@Override
	public String info() {
		return "main model";
	}

	class Player {
		int group;
		int totalMoney;
		int castleLevel;
		SendCard[] sendCards;
		State skillCDState;
		long skillCDTime = 20000;
		boolean ai;

		public Player(int group) {
			this.group = group;
			totalMoney = 0;
			castleLevel = 1;
			ai = false;
			sendCards = new SendCard[] {
					new SendCard(MakeAGame.CASTLE, 150, 1000),
					new SendCard(MakeAGame.ROLE_1, 100, 3000),
					new SendCard(MakeAGame.ROLE_2, 250, 6000),
					new SendCard(MakeAGame.ROLE_3, 300, 12000),
					new SendCard(MakeAGame.ROLE_4),
			};
			skillCDState = new State(new long[][] { { State.BLOCK, skillCDTime }, { State.ALLOW, State.BLOCK } });
		}

		public Player(int group, int level) {
			this(group);
			ai = true;
			switch (level)
			{
			case 1:
				sendCards = new SendCard[] {
						new SendCard(MakeAGame.CASTLE, 250, 1000),
						new SendCard(MakeAGame.ROLE_1, 160, 3500),
						new SendCard(MakeAGame.ROLE_2),
						new SendCard(MakeAGame.ROLE_3),
						new SendCard(MakeAGame.ROLE_4),
				};
				break;
			case 2:
				sendCards = new SendCard[] {
						new SendCard(MakeAGame.CASTLE, 200, 1000),
						new SendCard(MakeAGame.ROLE_1, 140, 3500),
						new SendCard(MakeAGame.ROLE_2, 300, 7000),
						new SendCard(MakeAGame.ROLE_3),
						new SendCard(MakeAGame.ROLE_4),
				};
				break;
			case 3:
				sendCards = new SendCard[] {
						new SendCard(MakeAGame.CASTLE, 200, 1000),
						new SendCard(MakeAGame.ROLE_1, 130, 3500),
						new SendCard(MakeAGame.ROLE_2, 280, 7000),
						new SendCard(MakeAGame.ROLE_3),
						new SendCard(MakeAGame.ROLE_4),
				};
				break;
			case 4:
				sendCards = new SendCard[] {
						new SendCard(MakeAGame.CASTLE, 200, 1000),
						new SendCard(MakeAGame.ROLE_1, 130, 3500),
						new SendCard(MakeAGame.ROLE_2, 280, 7000),
						new SendCard(MakeAGame.ROLE_3, 350, 13000),
						new SendCard(MakeAGame.ROLE_4),
				};
				break;
			case 5:
				sendCards = new SendCard[] {
						new SendCard(MakeAGame.CASTLE, 200, 1000),
						new SendCard(MakeAGame.ROLE_1, 120, 3500),
						new SendCard(MakeAGame.ROLE_2, 270, 7000),
						new SendCard(MakeAGame.ROLE_3, 330, 13000),
						new SendCard(MakeAGame.ROLE_4),
				};
				break;
			}

		}

		public void power(int type) {
			if (skillCDState.enter(1)) {
				for (Role r : roles) {
					if (r.m.group != group) {
						r.m.hp -= 300;
					}
				}
				powerApplyTime = State.global_current;
				skillCDState.enter(0);
			}
		}

		public void ai() {
			// TODO 加入電腦的大決 (大量兵團)
			for (SendCard card : sendCards) {
				if (card.canClick(this)) {
					card.send(this);
				}
			}
		}

		public void click(int id) {
			sendCards[id].send(this);
		}
	}

	class SendCard {
		long cdTime;
		State state;
		boolean locked;
		int costMoney;
		int[] costResource;
		String type;
		int strongLevel;

		public SendCard(String type) {
			this.type = type;
			this.locked = true;
		}

		public SendCard(String type, int costMoney, long cdTime) {
			this.type = type;
			this.costMoney = costMoney;
			this.cdTime = cdTime;
			state = new State(new long[][] { { State.BLOCK, cdTime }, { State.ALLOW, State.BLOCK } });
			costResource = new int[] { 0, 0, 0 };
			locked = false;
			strongLevel = 1;
		}

		public Hold.SendCard hold() {
			Hold.SendCard h = new Hold.SendCard();
			h.type = type;
			h.locked = locked;
			if (!locked) {
				h.costMoney = costMoney;
				h.costResource = costResource;
				h.sendCD = (float) state.elapsed() / (float) cdTime;
				h.strongLevel = strongLevel;
			}
			return h;
		}

		public boolean canClick(Player player) {
			if (locked) {
				return false;
			}
			if (player.totalMoney < costMoney) {
				return false;
			}
			return true;
		}

		public void send(Player player) {
			if (canClick(player)) {
				if (state.enter(1)) {
					player.totalMoney -= costMoney;
					if (type.equals(MakeAGame.CASTLE)) {
						costMoney *= 2;
						player.castleLevel++;
					} else {
						roles.add(new Role(ResourceManager.get().read(type), player.group));
					}
					state.enter(0);
				}
			}
		}
	}

	class Role {

		// 1 Moving, 2 Preparing, 3 Attacking, 4 Backing, 5 Death
		public final static int STATE_MOVING = 0;
		public final static int STATE_PERPARING = 1;
		public final static int STATE_ATTACKING = 2;
		public final static int STATE_BACKING = 3;
		public final static int STATE_DEATH = 4;
		public final static int STATE_RECYCLE = 5;

		State state;
		Role meet;
		Attribute m;
		long lastAttackTime;
		long backingTime = 50;
		long recycleTime = 2000;

		ArrayList<Hold.Hurt> hurtRecord;

		public Role(String gson, int group) {
			m = init(gson);
			m.group = group;
			m.x = group == 0 ? 110 : 848;
			m.y = 10 + (10 - rand.nextInt(20));
			m.maxHp = m.hp;
			m.baseAtkTime = m.atkTime;
			m.level = 1;
			hurtRecord = new ArrayList<Hold.Hurt>();

			state = new State(new long[][] {
					{ State.BLOCK, State.ALLOW, State.BLOCK, State.ALLOW, State.ALLOW, State.BLOCK },
					{ State.ALLOW, State.BLOCK, m.atkTime, State.ALLOW, State.ALLOW, State.BLOCK },
					{ State.ALLOW, State.ALLOW, State.BLOCK, State.ALLOW, State.ALLOW, State.BLOCK },
					{ backingTime, backingTime, State.BLOCK, State.BLOCK, State.ALLOW, State.BLOCK },
					{ State.BLOCK, State.BLOCK, State.BLOCK, State.BLOCK, State.BLOCK, recycleTime },
					{ State.BLOCK, State.BLOCK, State.BLOCK, State.BLOCK, State.BLOCK, State.BLOCK } });
		}

		public class Attribute {
			public String id;
			int group; // 0 mine, 1 others
			int hp;
			int maxHp;
			int atk;
			int x;
			int y;
			float sX;
			int money;
			int beAtk;
			long atkTime;
			long baseAtkTime;
			int range;
			int level;
		}

		public Attribute init(String gson) {
			// Engine.logI("init with gson " + gson);
			Attribute model = new Gson().fromJson(gson, Attribute.class);
			return model;
		}

		public long getAtkTime(boolean atked) {
			m.atkTime = (long) (m.baseAtkTime * (atked ? 1.4f : 0.6f));
			return m.atkTime;
		}

		public void run() {
			// stop while meet other groups role
			meet = null;
			for (Role r : roles) {
				if (m.group == 0 && r.m.group == 1 && m.x + m.range >= r.m.x
						|| (m.group == 1 && r.m.group == 0 && m.x - m.range <= r.m.x)) {
					meet = r;
					// System.out.println(m.id + " meet to " + meet.m.id);
				}
			}

			if (meet == null) {
				state.enter(Role.STATE_MOVING);
			} else {
				state.enter(Role.STATE_PERPARING);
			}

			if (m.atk > 0) {
				if (state.enter(Role.STATE_ATTACKING)) {
					// System.out.println(m.id + " attack to " + meet.m.id);
					meet.m.hp -= m.atk;
					if (!meet.m.id.equals("castle")) {
						meet.m.beAtk = m.atk;
					}
					meet.hurtRecord.add(new Hold.Hurt(State.global_current, m.atk));
					meet.state.enter(Role.STATE_BACKING);
					state.setTableValue(getAtkTime(true), 1, 2);
					meet.state.setTableValue(meet.getAtkTime(false), 1, 2);
				}
			}

			// die
			if (m.hp <= 0) {
				if (state.enter(Role.STATE_DEATH)) {
					player[m.group == 0 ? 1 : 0].totalMoney += m.money;
					if (m.id.equals("castle")) {
						gameOver();
					}
				} else {
					state.enter(STATE_RECYCLE);
				}
			}

			// System.out.println(m.id + " state is " + state.currentStat());
			switch (state.currentStat()) {
			case Role.STATE_MOVING:
				m.x += (m.group == 0 ? 1 : -1) * m.sX;
				break;
			case Role.STATE_PERPARING:
				break;
			case Role.STATE_ATTACKING:
				break;
			case Role.STATE_BACKING:
				m.x += (m.group == 0 ? -1 : 1) * m.beAtk * 0.5f;
				break;
			case Role.STATE_DEATH:
				break;
			}
		}

		public Hold.Unit hold() {
			Hold.Unit h = new Hold.Unit();
			h.group = m.group;
			h.hpp = (float) m.hp / (float) m.maxHp;
			h.hurtRecord = new ArrayList<Hold.Hurt>();
			// is not a good function
			for (int i = 0; i < hurtRecord.size(); i++) {
				if (hurtRecord.get(i).time > State.global_current - 10000) {
					h.hurtRecord.add(hurtRecord.get(i));
				}
			}
			h.lastAttackTime = state.elapsed(STATE_ATTACKING);
			h.lastBackingTime = state.elapsed(STATE_BACKING);
			h.lastDeathTime = state.elapsed(STATE_DEATH);
			h.lastPreparingTime = state.elapsed(STATE_PERPARING);
			h.lastWalkTime = state.elapsed(STATE_MOVING);
			h.pos = new Position(m.x, m.y);
			h.stateRecord = state.currentStat();
			h.strongLevel = m.level;
			h.type = m.id;
			return h;
		}
	}
}
