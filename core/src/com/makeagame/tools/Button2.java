package com.makeagame.tools;

import java.util.ArrayList;

import com.makeagame.core.view.RenderEvent;
import com.makeagame.core.view.SignalEvent;
import com.makeagame.core.view.View;


public class Button2 implements View {
	/* �i�઺���A:
	 *   �w�ʪ��A:
	 *     Disable		disable �ɫ��s����Ĳ�o, �B���s��ܦǦ�
	 *     Activate		
	 *     Inactivate	InActivate �ɫ��s����Ĳ�o, �ӥB��ܬ��ǳƤ�
	 *     Invisible	InVisible �ɫ��s��Ĳ�o, �B�����
	 *     Hovered		�N��ƹ�(����)�b�P���Ϥ���(��ʥ��x�W�L��)
	 *     Pushed		�N��Q���ۮ�

	 *     
	 */
	public Button2()
	{
		action_state = new State(new long[][] {
		//		Static			Hovered		Pushed
				{ State.BLOCK, State.ALLOW, State.BLOCK },
				{ State.ALLOW, State.BLOCK, State.ALLOW },
				{ State.BLOCK, State.ALLOW, State.BLOCK } 
		});
		
		visible_state = new State(new long[][] {
		//		Invisible		Visible
				{ State.BLOCK, State.ALLOW },
				{ State.ALLOW, State.BLOCK }
		});
		
		// �u���Inactive->Active
		// Enable �O�L�窬�A
		enable_state = new State(new long[][] {
		//		Disable		 Enable			Active 		Inactive
				{ State.BLOCK, State.ALLOW,	State.BLOCK, State.BLOCK },
				{ State.ALLOW, State.BLOCK,	State.ALLOW, State.ALLOW },
				{ State.ALLOW, State.BLOCK,	State.BLOCK, State.ALLOW },
				{ State.ALLOW, State.BLOCK,	State.ALLOW, State.BLOCK }
		});
	}
	
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
	static double progress = 0.0;
	
	
	State visible_state;
	State enable_state;
	State action_state;
	
	int x, y, w, h;
	
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
	
	public void OnMouseIn() {}
	public void OnMouseOut() {}
	public void OnMouseDown() {}
	public void OnMouseUp() {}
	
	@Override
	public void signal(ArrayList<SignalEvent> signalList) {
		if (enable_state.currentStat() != Active) {
			action_state.reset(Static);
			return;
		}
		for (SignalEvent s : signalList) {
			if (s.type == SignalEvent.MOUSE_EVENT || s.type == SignalEvent.TOUCH_EVENT) {
				if (isInArea( s.signal.x,  s.signal.y)) {
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
	
	@Override
	public String info() {
		return "button view";
	}
	
	@Override
	public ArrayList<RenderEvent> render(ArrayList<String> build) {
		
		if (progress >= 1.0) {
			enable_state.enter(Active);
		} else {
			enable_state.enter(Inactive);
		}
		
		// �p�G invisible�h�����
		ArrayList<RenderEvent> renderlist = new ArrayList<RenderEvent>();
		if (visible_state.currentStat() == Invisible) {
			return renderlist;
		}
		
		switch (enable_state.currentStat())
		{
		case Disable:
			break;
		case Inactive:
			break;
		case Active:
			switch (action_state.currentStat())
			{
			case Static:
				break;
			case Hovered:
				break;
			case Pushed:
				break;
			}
			break;
		}
		return renderlist;
	}
}
