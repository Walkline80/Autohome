package com.walkline.util;

import net.rim.device.api.ui.Screen;
import net.rim.device.api.ui.TransitionContext;
import net.rim.device.api.ui.Ui;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.UiEngineInstance;
import net.rim.device.api.ui.component.Dialog;

public class Function
{
    public static void errorDialog(final String message)
    {
        UiApplication.getUiApplication().invokeAndWait(new Runnable()
        {
            public void run() {Dialog.alert(message);} 
        });
    }

    public static void attachTransition(Screen screen, int transitionType)
	{
		UiEngineInstance engine = Ui.getUiEngineInstance();
		TransitionContext pushAction = null;
		TransitionContext popAction = null;

		switch (transitionType)
		{
			case TransitionContext.TRANSITION_FADE:
				pushAction = new TransitionContext(TransitionContext.TRANSITION_FADE);
				popAction = new TransitionContext(TransitionContext.TRANSITION_FADE);
				pushAction.setIntAttribute(TransitionContext.ATTR_DURATION, 300);
				popAction.setIntAttribute(TransitionContext.ATTR_DURATION, 300);
				break;
			case TransitionContext.TRANSITION_SLIDE:
				pushAction = new TransitionContext(TransitionContext.TRANSITION_SLIDE);
				popAction = new TransitionContext(TransitionContext.TRANSITION_SLIDE);
				pushAction.setIntAttribute(TransitionContext.ATTR_DURATION, 300);
				pushAction.setIntAttribute(TransitionContext.ATTR_DIRECTION, TransitionContext.DIRECTION_LEFT);
				popAction.setIntAttribute(TransitionContext.ATTR_DURATION, 300);
				popAction.setIntAttribute(TransitionContext.ATTR_DIRECTION, TransitionContext.DIRECTION_RIGHT);
				break;
			default:
				pushAction = new TransitionContext(TransitionContext.TRANSITION_NONE);
				popAction = new TransitionContext(TransitionContext.TRANSITION_NONE);
		}

		engine.setTransition(null, screen, UiEngineInstance.TRIGGER_PUSH, pushAction);
		engine.setTransition(screen, null, UiEngineInstance.TRIGGER_POP, popAction);
	}
}