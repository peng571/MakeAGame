package com.makeagame.tools;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;

public class KeyTable {
	public interface IntMethod {
		public Object interpolation(Key k1, Key k2, double current);

	}

	public final static IntMethod INT_NORMAL = new IntMethod() {
		public Object interpolation(Key k1, Key k2, double current) {
			return k1.value;
		}
	};

	public final static IntMethod INT_LINEAR = new IntMethod() {
		public Object interpolation(Key k1, Key k2, double current) {
			double f1 = ((Double) k1.value).doubleValue();
			double f2 = ((Double) k2.value).doubleValue();
			double r = f1 + (f2 - f1) * ((current - k1.pos) / (k2.pos - k1.pos));
			return Double.valueOf(r);
		}
	};

	public static class Key {
		public double pos;
		public String attr;
		public Object value;
		public IntMethod interp;

		public Key(String attr, Object value) {
			this.attr = attr;
			this.value = value;
			this.interp = INT_NORMAL;
		}

		public Key(String attr, Object value, IntMethod interp) {
			this.attr = attr;
			this.value = value;
			this.interp = interp;
		}

		public Key(String attr, Object value, IntMethod interp, double pos) {
			this.attr = attr;
			this.value = value;
			this.interp = interp;
			this.pos = pos;
		}
	}

	public static class Frame {
		Key[] keys;
		public double pos;

		public Frame(double pos, Key[] keys) {
			this.pos = pos;
			this.keys = keys;
		}
	}

	public class ApplyList {
		public HashMap<String, Object> map;

		public ApplyList() {
			map = new HashMap<String, Object>();
		}

		public <T> void apply(T object) {
			Field field;
			Iterator<String> it = this.map.keySet().iterator();

			while (it.hasNext()) {
				try {
					String name = it.next();
					field = object.getClass().getField(name);
					field.set(object, map.get(name));
				} catch (NoSuchFieldException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static Comparator<Frame> FramePosComparator = new Comparator<Frame>() {
		public int compare(Frame f1, Frame f2) {
			if (f1.pos == f2.pos)
				return 0;
			return f1.pos < f2.pos ? -1 : 1;
		}
	};

	Frame[] frames;

	// double current;
	public KeyTable(Frame[] frames) {
		this.frames = frames;
		Arrays.sort(this.frames, FramePosComparator);
	}

	private void _insert_two_map(
			HashMap<String, Key> before,
			HashMap<String, Key> after,
			Key key,
			double pos)
	{
		before.put(key.attr, new Key(
				key.attr,
				key.value,
				key.interp,
				pos
				));
		after.put(key.attr, new Key(
				key.attr,
				key.value,
				key.interp,
				pos
				));
	}

	private void _change_key(
			Key target,
			Key key,
			double pos)
	{
		target.value = key.value;
		target.interp = key.interp;
		target.pos = pos;
	}

	public ApplyList get(double current) {
		// current += step;

		HashMap<String, Key> before = new HashMap<String, Key>();
		HashMap<String, Key> after = new HashMap<String, Key>();

		// assert(before.keySet() == after.keySet())
		// ��X�b�ɶ��������@��key pair
		for (int i = 0; i < frames.length; i++) {
			Frame frame = frames[i];
			for (int j = 0; j < frame.keys.length; j++) {
				Key key = frame.keys[j];
				if (frame.pos < current) {

					if (!before.containsKey(key.attr)) {
						_insert_two_map(before, after, key, frame.pos);
					} else {
						_change_key(before.get(key.attr), key, frame.pos);
						_change_key(after.get(key.attr), key, frame.pos);
					}
				} else {
					if (!before.containsKey(key.attr)) {
						_insert_two_map(before, after, key, frame.pos);
					} else {
						// �p�G���e�� key.pos �p�� current �åB�w�g�s�b�C��
						// �ӥB���᪺ key.pos �j�� current
						// �h after �N�O��n*�U�@��* keypoint
						if (after.get(key.attr).pos < current) {
							_change_key(after.get(key.attr), key, frame.pos);
						}
					}
				}
			}
		}

		// assert(before.keySet() == after.keySet())
		// �}�l�i��̭��n�������ʧ@
		ApplyList result = new ApplyList();

		Iterator<String> it = before.keySet().iterator();
		while (it.hasNext()) {
			String attr = it.next();
			Key key1 = before.get(attr);
			Key key2 = after.get(attr);
			// �p�G key1 �M key2����m�ۦP ��ܤ��e�άO���᳣�S����Lkeypoint
			// �۵M�]�N�L�k�i�椺��
			if (key1.pos == key2.pos) {
				result.map.put(attr, key1.value);
			} else {
				// �� key1, key2 �i�椺��
				// �w key2.interp �ҫ��w��������k����
				result.map.put(attr,
						key2.interp.interpolation(key1, key2, current));
			}
		}
		return result;
	}

}
