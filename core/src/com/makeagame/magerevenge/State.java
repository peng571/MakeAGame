package com.makeagame.magerevenge;

import java.util.ArrayList;

public class State {
	double[][] cond_table;
	public static double ALLOW = 0.0;				// Allow
	public static double BLOCK = 99999999999999.0;	// Block
	
	public class StateRecord {
		public int stat;
		public double time;
		public StateRecord(int stat, double time) {
			this.stat = stat;
			this.time = time;
		}
	}
	
	public ArrayList<StateRecord> records;
	
	/*
	 * @cond_table: 狀態條件表, 如果要從[狀態A]轉換成[狀態B], 
	 * 				當 cond_table[A][B] == ALLOW 則轉換成功
	 * 				當 cond_table[A][B] == BLOCK 則轉換失敗
	 * 				否則 cond_table[A][B] 代表計數器, 如果之前[狀態A]的(持續時間!!)已經超過此計數器
	 * 				則 轉換成功, 否則轉換失敗
	 * 
	 */
	public State(double[][] cond_table) {
		this.cond_table = cond_table;
		this.reset();
	}
	
	public void reset() {
		this.records.clear();
		this.records.add(new StateRecord(0, 0.0));
	}
	
	public void reset(int stat, double time) {
		this.records.clear();
		this.records.add(new StateRecord(stat, time));
	}
	
	public State copy() {
		State New = new State(this.cond_table);
		return New;
	}
	
	// 取得現在的狀態
	public int currentStat() {
		StateRecord last = this.records.get( this.records.size() - 1);
		return last.stat;
	}
	
	// 取得現在狀態的維持時間
	public double elapsed(double now) {
		StateRecord last = this.records.get( this.records.size() - 1);
		return now - last.time;
	}
	
	// 轉換狀態, now 代表現在的時間
	public boolean enter(int stat, double now) {
		boolean pass = true;
		StateRecord last = this.records.get( this.records.size() - 1);
		
		double timecond = this.cond_table[last.stat][stat];
		if (pass && timecond != 0.0) {
			pass = false;
			//if (now > last.time + timecond) {
			if (this.elapsed(now) > timecond) {
				pass = true;
			}
		}
		
		if (pass) {
			this.records.add(new StateRecord(stat, now));
		}
		return pass;
	}
}
