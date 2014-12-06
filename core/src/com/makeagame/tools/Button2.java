package com.makeagame.tools;

import java.util.ArrayList;
import com.makeagame.core.Engine;
import com.makeagame.core.view.RenderEvent;
import com.makeagame.core.view.SignalEvent;
import com.makeagame.core.view.View;

public class Button2 {// implements View {
/*
 * �i�઺���A:
 * �w�ʪ��A:
 * Disable disable �ɫ��s����Ĳ�o, �B���s��ܦǦ�
 * Activate
 * Inactivate InActivate �ɫ��s����Ĳ�o, �ӥB��ܬ��ǳƤ�
 * Invisible InVisible �ɫ��s��Ĳ�o, �B�����
 * Hovered �N��ƹ�(����)�b�P���Ϥ���(��ʥ��x�W�L��)
 * Pushed �N��Q���ۮ�
 */
	static final int Invisible = 0;
	static final int Visible = 1;
	static final int Disable = 0;
	static final int Enable = 1;
	static final int Active = 2;
	static final int Inactive = 3;
	static final int Static = 0;
	static final int Hovered = 1;
	static final int Pushed = 2;
	// �i�ױ�, ��F�� 1.0 �ɶi�JActive, ����ɰh�^Inactive
	static double progress = 1.0;
	public State visible_state;
	public State enable_state;
	public State action_state;

	public Button2()
	{
		action_state = new State(new long[][] {
				// Static Hovered Pushed
				{ State.BLOCK, State.ALLOW, State.BLOCK },
				{ State.ALLOW, State.BLOCK, State.ALLOW },
				{ State.BLOCK, State.ALLOW, State.BLOCK }
		});
		action_state.reset(Static);
		visible_state = new State(new long[][] {
				// Invisible Visible
				{ State.BLOCK, State.ALLOW },
				{ State.ALLOW, State.BLOCK }
		});
		visible_state.reset(Visible);
		// �u���Inactive->Active
		// Enable �O�L�窬�A
		enable_state = new State(new long[][] {
				// Disable Enable Active Inactive
				{ State.BLOCK, State.ALLOW, State.BLOCK, State.BLOCK },
				{ State.ALLOW, State.BLOCK, State.ALLOW, State.ALLOW },
				{ State.ALLOW, State.BLOCK, State.BLOCK, State.ALLOW },
				{ State.ALLOW, State.BLOCK, State.ALLOW, State.BLOCK }
		});
		enable_state.reset(Active);
	}

	int x, y, w, h;

	public void setRectArea(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	public void setEnable(boolean bool) {
		if (bool) {
			enable_state.enter(Enable);
		} else {
			enable_state.enter(Disable);
		}
	}

	public void setVisible(boolean bool) {
		if (bool) {
			visible_state.enter(Visible);
		} else {
			visible_state.enter(Invisible);
		}
	}

	public boolean isInArea(int _x, int _y) {
		if (_x > x && _x < x + w && _y > y && _y < y + h) {
			return true;
		}
		return false;
	}

	public void OnMouseIn() {
	}

	public void OnMouseOut() {
	}

	public void OnMouseDown() {
	}

	public void OnMouseUp() {
	}

	// @Override
	public void signal(ArrayList<SignalEvent> signalList) {
		if (enable_state.currentStat() != Active) {
			// Engine.logI("es: " + Integer.toString(enable_state.currentStat()));
			action_state.reset(Static);
			return;
		}
		for (SignalEvent s : signalList) {
			if (s.type == SignalEvent.MOUSE_EVENT || s.type == SignalEvent.TOUCH_EVENT) {
				// Engine.logI(Integer.toString(s.signal.x));
				if (isInArea(s.signal.x, s.signal.y)) {
					if (s.action == SignalEvent.ACTION_MOVE) {
						if (action_state.enter(Hovered)) {
							OnMouseIn();
						}
					} else if (s.action == SignalEvent.ACTION_DOWN) {
						if (action_state.enter(Pushed)) {
							OnMouseDown();
						}
					} else if (s.action == SignalEvent.ACTION_UP) {
						if (action_state.enter(Hovered)) {
							OnMouseUp();
						}
					}
				} else {
					if (action_state.enter(Static)) {
						OnMouseOut();
					}
				}
			}
		}
	}

	// @Override
	public String info() {
		return "button view";
	}

	Sprite spDisable;
	Sprite spInactive;
	Sprite spStatic;
	Sprite spHovered;
	Sprite spPushed;
	KeyTable ktDisable;
	KeyTable ktInactive;
	KeyTable ktStatic;
	KeyTable ktHovered;
	KeyTable ktPushed;

	public void setDisableSprite(Sprite sprite) {
		spDisable = sprite;
	}

	public void setDisableAnimation(KeyTable keytable) {
		ktDisable = keytable;
	}

	public void setInactiveSprite(Sprite sprite) {
		spInactive = sprite;
	}

	public void setInactiveAnimation(KeyTable keytable) {
		ktInactive = keytable;
	}

	public void setStaticSprite(Sprite sprite) {
		spStatic = sprite;
	}

	public void setStaticAnimation(KeyTable keytable) {
		ktStatic = keytable;
	}

	public void setHoveredSprite(Sprite sprite) {
		spHovered = sprite;
	}

	public void setHoveredAnimation(KeyTable keytable) {
		ktHovered = keytable;
	}

	public void setPushedSprite(Sprite sprite) {
		spPushed = sprite;
	}

	public void setPushedAnimation(KeyTable keytable) {
		ktPushed = keytable;
	}

	public void setActiveSprite(Sprite sprite) {
		spStatic = sprite;
		spHovered = sprite;
		spPushed = sprite;
	}

	public void setActiveAnimation(KeyTable keytable) {
		ktStatic = keytable;
		ktHovered = keytable;
		ktPushed = keytable;
	}

	// @Override
	// public ArrayList<RenderEvent> render(ArrayList<String> build) {
	public void apply(Sprite sprite) {
		/*
		 * if (progress >= 1.0) {
		 * enable_state.enter(Active);
		 * } else {
		 * enable_state.enter(Inactive);
		 * }
		 */
		// �p�G invisible�h�����
		// TODO: ����b��
		// ArrayList<RenderEvent> renderlist = new ArrayList<RenderEvent>();
		if (visible_state.currentStat() == Invisible) {
			return;
		}
		switch (enable_state.currentStat())
		{
		case Disable:
			if (spDisable != null) {
				sprite.copyFrom(spDisable);
			}
			if (ktDisable != null) {
				sprite.apply(ktDisable.get(enable_state.elapsed()));
			}
			break;
		case Inactive:
			if (spInactive != null) {
				sprite.copyFrom(spInactive);
			}
			if (ktInactive != null) {
				sprite.apply(ktInactive.get(enable_state.elapsed()));
			}
			break;
		case Active:
			switch (action_state.currentStat())
			{
			case Static:
				if (spStatic != null) {
					sprite.copyFrom(spStatic);
				}
				if (ktStatic != null) {
					sprite.apply(ktStatic.get(action_state.elapsed()));
				}
				break;
			case Hovered:
				if (spHovered != null) {
					sprite.copyFrom(spHovered);
				}
				if (ktHovered != null) {
					sprite.apply(ktHovered.get(action_state.elapsed()));
				}
				break;
			case Pushed:
				if (spPushed != null) {
					sprite.copyFrom(spPushed);
				}
				if (ktPushed != null) {
					sprite.apply(ktPushed.get(action_state.elapsed()));
				}
				break;
			}
			break;
		}
	}
}