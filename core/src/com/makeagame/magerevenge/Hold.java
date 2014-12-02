package com.makeagame.magerevenge;

import java.util.ArrayList;

public 	class Hold {
	int money;
	boolean gameStart;
	int[] cost = new int[4];
	ArrayList<RoleHold> roles = new ArrayList<RoleHold>();
}

class RoleHold {
	String id;
	int hp;
	int x;
	int state; // 1 Moving, 2 Preparing, 3 Attacking, 4 Backing, 5 Death
	int group;

	public RoleHold(String id, int x, int hp, int group, int state) {
		this.id = id;
		this.x = x;
		this.hp = hp;
		this.group = group;
		this.state = state;
	}
}
