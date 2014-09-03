package com.walkline.screen;

import net.rim.device.api.system.Application;
import net.rim.device.api.system.Characters;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.container.PopupScreen;
import net.rim.device.api.ui.container.VerticalFieldManager;
import com.walkline.autohome.AutohomeException;
import com.walkline.autohome.AutohomeSDK;
import com.walkline.autohome.inf.TopicList;
import com.walkline.util.Enumerations.RefreshActions;
import com.walkline.util.Function;

public class RefreshContentsScreen extends PopupScreen
{
	Thread thread = null;
	AutohomeSDK _autohome;
	String _param;
	TopicList _topicList;

	public RefreshContentsScreen(AutohomeSDK autohome, String param, int action)
	{
		super(new VerticalFieldManager());

		_autohome = autohome;
		_param = param;

		add(new LabelField("Please wait....", Field.FIELD_HCENTER));

		switch (action)
		{
			case RefreshActions.TOPICLIST:
				thread = new Thread(new TopicListRunnable());
				break;
		}

		thread.start();
	}

	class TopicListRunnable implements Runnable
	{
		public void run()
		{
			try {
				_topicList = _autohome.getTopicList(_param);

				Application.getApplication().invokeLater(new Runnable()
				{
					public void run() {onClose();}
				});
			} catch (AutohomeException e) {Function.errorDialog(e.toString());}
		}
	}

	public TopicList getTopicList() {return _topicList;}

	public boolean onClose()
	{
		if (thread != null)
		{
			try {
				thread.interrupt();
				thread = null;
			} catch (Exception e) {}
		}

		try {
			UiApplication.getUiApplication().popScreen(this);	
		} catch (Exception e) {}

		return true;
	}

	protected boolean keyChar(char key, int status, int time)
	{
		if (key == Characters.ESCAPE)
		{
			onClose();

			return true;
		}

		return super.keyChar(key, status, time);
	}
} 