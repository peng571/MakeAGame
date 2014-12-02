package com.makeagame.magerevenge;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Random;

import com.google.gson.Gson;
import com.makeagame.core.Bootstrap;
import com.makeagame.core.model.Model;
import com.makeagame.core.resource.ResourceManager;

public class GameModel implements Model {

	boolean start;
	Random rand = new Random();
	ArrayList<Role> roles;

	int enemyCreateTime = 16000;
	long lastEnemyCreateTime;
	int moneyGetTime = 1000;
	long lastMoneyGetTime;
	int moneyGet = 10;
	int totalMoney;
	int castleLevel;

	static int[] COST = { 150, 100, 250, 300 };

	public GameModel() {
		roles = new ArrayList<Role>();
		roles.add(new Role(ResourceManager.get().read("castle"), 0));
		roles.add(new Role(ResourceManager.get().read("castle"), 1));
		start = true;
		totalMoney = 0;
		castleLevel = 1;
		lastEnemyCreateTime = System.currentTimeMillis();
	}

	@Override
	public void process(String gsonString) {
		Sign signs = new Gson().fromJson(gsonString, Sign.class);
		if (start) {
			// button click
			if (signs.clickBtn != null) {
				int cost = 0;
				if (signs.clickBtn.equals(MakeAGame.CASTLE)) {
					cost = COST[0];
					if (totalMoney >= cost) {
						totalMoney -= cost;
						COST[0] *= 2;
						castleLevel++;
					}
				} else {
					if (signs.clickBtn.equals(MakeAGame.ROLE_1)) {
						cost = COST[1];
					} else if (signs.clickBtn.equals(MakeAGame.ROLE_2)) {
						cost = COST[2];
					} else if (signs.clickBtn.equals(MakeAGame.ROLE_3)) {
						cost = COST[3];
					}
					if (cost != 0 && totalMoney >= cost) {
						roles.add(new Role(ResourceManager.get().read(signs.clickBtn), 0));
						totalMoney -= cost;
					}
				}
			}

			// create enemy
			if (System.currentTimeMillis() - lastEnemyCreateTime > enemyCreateTime) {
				roles.add(new Role(ResourceManager.get().read(MakeAGame.ROLE_1), 1));
				lastEnemyCreateTime = System.currentTimeMillis();
			}

			// earn money
			if (System.currentTimeMillis() - lastMoneyGetTime > moneyGetTime) {
				totalMoney += moneyGet * castleLevel;
				lastMoneyGetTime = System.currentTimeMillis();
			}

			// run role
			for (ListIterator<Role> it = roles.listIterator(); it.hasNext();) {
				Role r = it.next();
				if (r.m.state < Role.STATE_DIE) {
					r.run();
				}
				else {
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

		final static int STATE_WALK = 1;
		final static int STATE_ATTACK = 2;
		final static int STATE_DIE = 3;

		Role meet;

		Attribute m;

		long lastAttackTime;

		public Role(String gson, int group) {
			m = init(gson);
			m.group = group;
			m.x = group == 0 ? 32 : (Bootstrap.screamWidth() - 32);
			m.maxHp = m.hp;
			m.state = 0;
		}

		public class Attribute {
			public String id;
			int group; // 0 mine, 1 others
			int hp;
			int maxHp;
			int atk;
			int state;
			int x;
			float sX;
			int money;
			long atkTime;
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
				if (m.group == 0 && r.m.group == 1 && m.x >= r.m.x
						|| (m.group == 1 && r.m.group == 0 && m.x <= r.m.x)) {
					m.x = r.m.x;
					meet = r;
					break;
				}
			}

			// move if not meet others
			if (meet == null) {
				m.state = Role.STATE_WALK;
				m.x += (m.group == 0 ? 1 : -1) * m.sX;
			} else {
				// attack while stop
				m.state = Role.STATE_ATTACK;
				if (System.currentTimeMillis() - lastAttackTime > m.atkTime) {
					meet.m.hp -= m.atk;
					lastAttackTime = System.currentTimeMillis();
				}
			}

			// die
			if (m.hp <= 0) {
				if (m.group == 1) {
					totalMoney += m.money;
				}
				if (m.id.equals("castle")) {
					start = false;
				}
				m.state = Role.STATE_DIE;
			}
		}
	}
}