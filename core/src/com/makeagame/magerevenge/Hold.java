package com.makeagame.magerevenge;

import java.util.ArrayList;

import com.makeagame.core.component.Position;

public class Hold {
	// int money;
	boolean gameStart;
	int[] cost = new int[4];
	ArrayList<RoleHold> roles = new ArrayList<RoleHold>();

	String Screen;
	boolean isStoreOpen;
	int currentTime;

	// if (Screen = "battle")
	int money;
	int[] resourc;
	SendCard[] sendcard;
	Unit[] soldier;
	Unit[] castle;
	int powerApplyTime;
	float powerCD;
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

class SendCard {
	float sendCD;
	boolean locked;
	int costMoney;
	int[] costResource = new int[3];
	String type;
	int strongLevel;
}

class Unit {
	int group;
	Position pos;
	// StateRecord? stateRecord;
	float hpp;
	// ? hurtRecord;
	String type;
	int strongLevel;
}
