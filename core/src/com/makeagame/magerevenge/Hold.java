package com.makeagame.magerevenge;

import java.util.ArrayList;

public 	class Hold {
	int money;
	boolean gameStart;
	ArrayList<RoleHold> roles = new ArrayList<RoleHold>();
}

class RoleHold {
	String id;
	int hp;
	int x;
	int state; // 1 walk, 2 attack, 3 been attacked, 4 die
	int group;

	public RoleHold(String id, int x, int hp, int group, int state) {
		this.id = id;
		this.x = x;
		this.hp = hp;
		this.group = group;
		this.state = state;
	}
}
