package com.makeagame.tools;

import java.util.ArrayList;

public class State {
	long[][] cond_table;
	public static long ALLOW = 0; // Allow
	public static long BLOCK = Long.MAX_VALUE; // Block
	public static long global_current = 0;

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
	 * @cond_table: ���A�����, �p�G�n�q[���AA]�ഫ��[���AB],
	 * �� cond_table[A][B] == ALLOW �h�ഫ���\
	 * �� cond_table[A][B] == BLOCK �h�ഫ����
	 * �_�h cond_table[A][B] �N��p�ƾ�, �p�G���e[���AA]��(����ɶ�!!)�w�g�W�L���p�ƾ�
	 * �h �ഫ���\, �_�h�ഫ����
	 */
	public State(long[][] cond_table) {
		records = new ArrayList<State.StateRecord>();
		this.cond_table = cond_table;
		this.reset();
	}

	public void setTable(long[][] cond_table)	{
		// ���o�˧a
		if (cond_table.length == this.cond_table.length) {
			this.cond_table = cond_table;
		}
	}

	public void setTableValue(long value, int r, int c)	{
		if (r < cond_table.length && c < cond_table[r].length) {
			cond_table[r][c] = value;
		}
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

	// ���o�{�b�����A
	public int currentStat() {
		StateRecord last = this.records.get(this.records.size() - 1);
		return last.stat;
	}

	public int previousStat() {
		// TODO: �[�J�˴��{��
		StateRecord prev = this.records.get(this.records.size() - 2);
		return prev.stat;
	}

	// ���o�{�b���A�������ɶ�
	public long elapsed() {
		return elapsed(global_current);
	}

	public long elapsed(long now) {
		StateRecord last = this.records.get(this.records.size() - 1);
		//Engine.logI("now: " + Long.toString(now));
		//Engine.logI("last: " + Long.toString(last.time));
		return now - last.time;
	}
	
	public long elapsed(int stat)  {
		return elapsed(stat, global_current);
	}
	
	public long elapsed(int stat, long now) {
		StateRecord finded = null;
		for (int i=0; i<records.size(); i++) {
			int revIdx = records.size() - i - 1;
			if (records.get(revIdx).stat == stat) {
				finded = records.get(revIdx);
				break;
			}
		}
		if (finded != null) {
			return now - finded.time;
		} else {
			// TODO: ��@�ӧ�n���^�ǭ�
			return now;
		}
	}

	public static void setNowTime(long now) {
		global_current = now;
	}

	// �ഫ���A, now �N��{�b���ɶ�
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
		 * StateRecord last = this.records.get( this.records.size() - 1);
		 * long timecond = this.cond_table[last.stat][stat];
		 * if (pass && timecond != 0) {
		 * pass = false;
		 * //if (now > last.time + timecond) {
		 * if (this.elapsed(now) > timecond) {
		 * pass = true;
		 * }
		 * }
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
		StateRecord last = this.records.get(this.records.size() - 1);
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