package com.makeagame.magerevenge;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.makeagame.core.Bootstrap;
import com.makeagame.core.Engine;
import com.makeagame.core.model.Model;
import com.makeagame.core.resource.ResourceManager;
import com.makeagame.tools.State;

public class GameModel implements Model {

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

	static int[] COST = { 150, 100, 250, 300, 300 };

	public GameModel() {
		roles = new ArrayList<Role>();
		roles.add(new Role(ResourceManager.get().read(MakeAGame.CASTLE + "L"), 0));
		roles.add(new Role(ResourceManager.get().read(MakeAGame.CASTLE + "R"), 1));
		startLevel(1, 1);
		enemyCreateState = new State(new long[][] { { State.BLOCK, enemyCreateTime }, { enemyCreateTime, State.BLOCK } });
		moneyGetState = new State(new long[][] { { State.BLOCK, moneyGetTime }, { State.ALLOW, State.BLOCK } });
	}

	private void startLevel(int level, int difficulty) {
		resume();
		// TODO
	}

	private void pause() {
		start = false;
		// TODO
	}

	private void resume() {
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
				// TODO
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
					resume();
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

	@Override
	public String hold() {
		Hold hold = new Hold();
		for (Role r : roles) {
			hold.roles.add(new RoleHold(r.m.id, r.m.x, r.m.hp, r.m.group, 1));
		}
		hold.cost = COST;
		hold.money = totalMoney;
		hold.gameStart = start;
		return new Gson().toJson(hold);
	}

	@Override
	public String info() {
		return "main model";
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
		long backingTime = 100;

		public Role(String gson, int group) {
			m = init(gson);
			m.group = group;
			m.x = group == 0 ? 32 : (Bootstrap.screamWidth() - 32);
			m.maxHp = m.hp;
//			m.atkTime *= (20 - rand.nextInt(40) * 0.01f) + 0.9;
//			System.out.println(m.atkTime);
			// m.state = 0;
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
			float sX;
			int money;
			int beAtk;
			long atkTime;
			int range;
		}

		public Attribute init(String gson) {
			// Engine.logI("init with gson " + gson);
			Attribute model = new Gson().fromJson(gson, Attribute.class);
			return model;
		}

		public void run() {
			// stop while meet other groups role
			meet = null;
			for (Role r : roles) {
				if (m.group == 0 && r.m.group == 1 && m.x + m.range >= r.m.x
						|| (m.group == 1 && r.m.group == 0 && m.x - m.range <= r.m.x)) {
					meet = r;
					System.out.println(m.id + " meet to " + meet.m.id);
				}
			}

			if (meet == null) {
				state.enter(Role.STATE_MOVING);
			} else {
				state.enter(Role.STATE_PERPARING);
			}

			if (m.atk > 0) {
				if (state.enter(Role.STATE_ATTACKING)) {
					System.out.println(m.id + " attack to " + meet.m.id);
					meet.m.hp -= m.atk;
//					meet.m.beAtk = m.atk;
					meet.state.enter(Role.STATE_BACKING);
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

			System.out.println(m.id + " state is " + state.currentStat());
			switch (state.currentStat())
			{
			case Role.STATE_MOVING:
				m.x += (m.group == 0 ? 1 : -1) * m.sX;
				break;
			case Role.STATE_PERPARING:
				break;
			case Role.STATE_ATTACKING:
				break;
			case Role.STATE_BACKING:
				m.x += (m.group == 0 ? -1 : 1) * m.atk * 0.5f;
				break;
			case Role.STATE_DEATH:
				break;
			}

		}
	}
}