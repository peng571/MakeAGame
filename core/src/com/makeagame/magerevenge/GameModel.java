package com.makeagame.magerevenge;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.makeagame.core.Bootstrap;
import com.makeagame.core.Engine;
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
	ArrayList<Role> roles;

	State enemyCreateState;
	State moneyGetState;
	State skillCDState;

	long enemyCreateTime = 16000;
	long moneyGetTime = 1000;
	long skillCDTime = 3000;

	int moneyGet = 10;
	int totalMoney;
	int castleLevel;

	SendCard[] sendCards;
	static int[] COST = { 150, 100, 250, 300, 300 };

	public GameModel() {
		roles = new ArrayList<Role>();
		sendCards = new SendCard[] {
				new SendCard(MakeAGame.ROLE_1, 100, 3000),
				new SendCard(MakeAGame.ROLE_2, 250, 6000),
				new SendCard(MakeAGame.ROLE_3, 300, 12000),
				new SendCard(MakeAGame.ROLE_4),
				new SendCard(MakeAGame.ROLE_4)
		};

		roles.add(new Role(ResourceManager.get().read(MakeAGame.CASTLE + "L"), 0));
		roles.add(new Role(ResourceManager.get().read(MakeAGame.CASTLE + "R"), 1));
		startLevel(1, 1);
		enemyCreateState = new State(new long[][] { { State.BLOCK, enemyCreateTime }, { enemyCreateTime, State.BLOCK } });
		moneyGetState = new State(new long[][] { { State.BLOCK, moneyGetTime }, { State.ALLOW, State.BLOCK } });
		skillCDState = new State(new long[][] { { State.BLOCK, skillCDTime }, { State.ALLOW, State.BLOCK } });

	}

	private void startLevel(int level, int difficulty) {
		screen = "battle";
		resumeGame();
		// TODO
	}

	private void pause() {
		start = false;
		// TODO
	}

	private void resumeGame() {

		start = true;
		totalMoney = 0;
		castleLevel = 1;
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
			// button click
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
				int player = params.getInt("player");
				int cost = 0;
				String soldierType = params.getString("soldierType");
				if (soldierType.equals(MakeAGame.ROLE_1)) {
					cost = COST[1];
				} else if (soldierType.equals(MakeAGame.ROLE_2)) {
					cost = COST[2];
				} else if (soldierType.equals(MakeAGame.ROLE_3)) {
					cost = COST[3];
				}
				if (cost != 0 && totalMoney >= cost) {
					roles.add(new Role(ResourceManager.get().read(soldierType), 0));
					totalMoney -= cost;
				}
				break;
			case Sign.BATTLE_UsePower:
				if (skillCDState.enter(1)) {
					// TODO power
					powerApplyTime = State.global_current;
					skillCDState.enter(0);
				}
				break;
			case Sign.BATTLE_Upgrade:
				player = params.getInt("player");
				cost = COST[0];
				if (totalMoney >= cost) {
					totalMoney -= cost;
					COST[0] *= 2;
					castleLevel++;
				}
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
				totalMoney = params.getInt("amonut");
				break;
			case Sign.DEBUG_ResetColddown:
				// TODO
				break;
			case Sign.DEBUG_PrintData:
				System.out.println(hold());
				break;
			default:
				Engine.logE("get unknow command " + command);
			}

			// create enemy
			if (enemyCreateState.enter(1)) {
				roles.add(new Role(ResourceManager.get().read(MakeAGame.ROLE_1), 1));
				enemyCreateState.enter(0);
			}

			// earn money
			if (moneyGetState.enter(1)) {
				totalMoney += moneyGet * castleLevel;
				moneyGetState.enter(0);
			}

			// run role
			for (ListIterator<Role> it = roles.listIterator(); it.hasNext();) {
				Role r = it.next();
				if (r.state.currentStat() != Role.STATE_DEATH) {
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
			hold.money = totalMoney;
			hold.resource = new int[] { 0, 0, 0 };
			hold.sendcard = new Hold.SendCard[sendCards.length];
			for (int i = 0; i < sendCards.length; i++) {
				hold.sendcard[i] = sendCards[i].hold();
			}
			hold.soldier = new ArrayList<Hold.Unit>();
			hold.castle = new Hold.Unit[2];
			for (Role r : roles) {
				if (!r.m.id.equals(MakeAGame.CASTLE)) {
					hold.soldier.add(r.hold());
				} else {
					int id = r.m.group;
					hold.castle[id] = r.hold();
				}
			}
			hold.powerApplyTime = powerApplyTime;
			hold.powerCD = (float) skillCDState.elapsed() / (float) skillCDTime;
		}
		hold.isStoreOpen = false;
		hold.currentTime = State.global_current;
		return new Gson().toJson(hold);
	}

	@Override
	public String info() {
		return "main model";
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
			state = new State(new long[][] { { State.BLOCK, State.ALLOW, cdTime, State.BLOCK } });
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
	}

	class Role {

		// 1 Moving, 2 Preparing, 3 Attacking, 4 Backing, 5 Death
		public final static int STATE_MOVING = 0;
		public final static int STATE_PERPARING = 1;
		public final static int STATE_ATTACKING = 2;
		public final static int STATE_BACKING = 3;
		public final static int STATE_DEATH = 4;

		State state;
		Role meet;
		Attribute m;
		long lastAttackTime;
		long backingTime = 50;
		ArrayList<Hold.Hurt> hurtRecord;

		public Role(String gson, int group) {
			m = init(gson);
			m.group = group;
			m.x = group == 0 ? 110 : 848;
			m.y = 340 + (20 - rand.nextInt(40));
			m.maxHp = m.hp;
			m.baseAtkTime = m.atkTime;
			m.level = 1;
			hurtRecord = new ArrayList<Hold.Hurt>();

			state = new State(new long[][] {
					{ State.ALLOW, State.ALLOW, State.BLOCK, State.ALLOW, State.ALLOW },
					{ State.ALLOW, State.BLOCK, m.atkTime, State.ALLOW, State.ALLOW },
					{ State.ALLOW, State.ALLOW, State.BLOCK, State.ALLOW, State.ALLOW },
					{ backingTime, backingTime, State.BLOCK, State.BLOCK, State.ALLOW },
					{ State.BLOCK, State.BLOCK, State.BLOCK, State.BLOCK, State.BLOCK } });
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
					if (m.group == 1) {
						totalMoney += m.money;
					}
					if (m.id.equals("castle")) {
						gameOver();
					}
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
			// not a good function
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
			h.pos = new Position<Integer>(m.x, m.y);
			h.stateRecord = state.currentStat();
			h.strongLevel = m.level;
			h.type = m.id;
			return h;
		}
	}
}