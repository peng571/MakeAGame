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
	 * @cond_table: ���A�����, �p�G�n�q[���AA]�ഫ��[���AB], 
	 * 				�� cond_table[A][B] == ALLOW �h�ഫ���\
	 * 				�� cond_table[A][B] == BLOCK �h�ഫ����
	 * 				�_�h cond_table[A][B] �N��p�ƾ�, �p�G���e[���AA]��(����ɶ�!!)�w�g�W�L���p�ƾ�
	 * 				�h �ഫ���\, �_�h�ഫ����
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
	
	// ���o�{�b�����A
	public int currentStat() {
		StateRecord last = this.records.get( this.records.size() - 1);
		return last.stat;
	}
	
	// ���o�{�b���A�������ɶ�
	public double elapsed(double now) {
		StateRecord last = this.records.get( this.records.size() - 1);
		return now - last.time;
	}
	
	// �ഫ���A, now �N��{�b���ɶ�
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
