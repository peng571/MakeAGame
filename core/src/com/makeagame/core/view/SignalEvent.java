package com.makeagame.core.view;

public class SignalEvent {

	public final static int MOUSE_EVENT = 0x01;
	public final static int KEY_EVENT = 0x02;
	public final static int TOUCH_EVENT = 0x03;

	public final static int ACTION_DOWN = 0x11;
	public final static int ACTION_UP = 0x12;
	public final static int ACTIOM_HOLD = 0x13;

	Object[] values;
	int eventType;
	int actionType;

	public SignalEvent(int type, int action, Object[] values) {
		this.eventType = type;
		this.actionType = action;
		this.values = values;
	}

}
