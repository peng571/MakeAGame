package com.makeagame.tools;

import java.util.ArrayList;

public class State {
	long[][] cond_table;
	public static long ALLOW = 0;				// Allow
	public static long BLOCK = Long.MAX_VALUE;	// Block
	
	public static  long global_current = 0;
	
	public class StateRecord {
		public int stat;
		public long time;
		public StateRecord(int stat, long time) {
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
	public State(long[][] cond_table) {
		records = new ArrayList<State.StateRecord>();
		this.cond_table = cond_table;
		this.reset();
	}
	
	public void reset() {
		this.records.clear();
		this.records.add(new StateRecord(0, 0));
	}
	
	public void reset(int stat) {
		this.records.clear();
		this.records.add(new StateRecord(stat, global_current));
	}
	
	
	public void reset(int stat, long time) {
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
	
	public int previousStat() {
		// TODO: 加入檢測程式
		StateRecord prev = this.records.get( this.records.size() - 2);
		return prev.stat;
	}
	
	// 取得現在狀態的維持時間
	public long elapsed(long now) {
		StateRecord last = this.records.get( this.records.size() - 1);
		return now - last.time;
	}
	
	public static void setNowTime(long now) {
		global_current = now;
	}
	
	// 轉換狀態, now 代表現在的時間
	public boolean enter(int stat) {
		return this.enter(stat, global_current, true);
	}
	
	public boolean enter(int stat, boolean pass) {
		return this.enter(stat, global_current, pass);
	}
	
	public boolean enter(int stat, long now) {
		return this.enter(stat, now, true);
	}
	
	public boolean enter(int stat, long now, boolean pass) {
		/*
		StateRecord last = this.records.get( this.records.size() - 1);
		
		long timecond = this.cond_table[last.stat][stat];
		if (pass && timecond != 0) {
			pass = false;
			//if (now > last.time + timecond) {
			if (this.elapsed(now) > timecond) {
				pass = true;
			}
		}
		*/
		if (pass) {
			pass = canEnter(stat, now);
		}
		
		if (pass) {
			this.records.add(new StateRecord(stat, now));
		}
		return pass;
	}
	
	public boolean canEnter(int stat) {
		return this.enter(stat, global_current);
	}
	
	public boolean canEnter(int stat, long now) {
		StateRecord last = this.records.get( this.records.size() - 1);
		long timecond = this.cond_table[last.stat][stat];
		boolean pass = true;
		if (timecond != 0) {
			pass = false;
			if (this.elapsed(now) > timecond) {
				pass = true;
			}
		}
		return pass;
	}
}
