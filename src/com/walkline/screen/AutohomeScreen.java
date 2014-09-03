package com.walkline.screen;

import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;
import javax.microedition.io.HttpConnection;
import net.rim.device.api.io.IOUtilities;
import net.rim.device.api.io.http.HttpProtocolConstants;
import net.rim.device.api.io.transport.ConnectionDescriptor;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.FontFamily;
import net.rim.device.api.ui.FontManager;
import net.rim.device.api.ui.Ui;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.container.MainScreen;
import com.walkline.autohome.AutohomeException;
import com.walkline.autohome.AutohomeSDK;
import com.walkline.autohome.inf.Topic;
import com.walkline.autohome.inf.TopicList;
import com.walkline.util.Enumerations.RefreshActions;
import com.walkline.util.network.MyConnectionFactory;
import com.walkline.util.network.WorkQueue;
import com.walkline.util.ui.ForegroundManager;
import com.walkline.util.ui.ListStyleButtonField;
import com.walkline.util.ui.ListStyleButtonSet;

public final class AutohomeScreen extends MainScreen
{
	private AutohomeSDK _autohome = AutohomeSDK.getInstance();
	private WorkQueue _queue = new WorkQueue(3);
	private ListStyleButtonSet _listSet = new ListStyleButtonSet();
	private ListStyleButtonField _item;
	private ForegroundManager _foreground = new ForegroundManager(0);

    public AutohomeScreen()
    {        
    	super (NO_VERTICAL_SCROLL | USE_ALL_HEIGHT | NO_SYSTEM_MENU_ITEMS);

		try {
			FontFamily family = FontFamily.forName("BBGlobal Sans");
			Font appFont = family.getFont(Font.PLAIN, 8, Ui.UNITS_pt);
			FontManager.getInstance().setApplicationFont(appFont);
		} catch (ClassNotFoundException e) {}

        //setDefaultClose(false);

		add(_foreground);

        UiApplication.getUiApplication().invokeLater(new Runnable()
        {
			public void run() {getTopicList(String.valueOf(1));}
		});
    }

    private void getTopicList(final String page)
    {
    	UiApplication.getUiApplication().invokeLater(new Runnable()
    	{
			public void run()
			{
		    	TopicList topicList;
				RefreshContentsScreen popupScreen = new RefreshContentsScreen(_autohome, page, RefreshActions.TOPICLIST);
				UiApplication.getUiApplication().pushModalScreen(popupScreen);

				topicList = popupScreen.getTopicList();

				if (popupScreen != null) {popupScreen = null;}
				if (topicList != null) {refreshExperienceList(topicList);}
			}
		});
    }

    private void refreshExperienceList(TopicList topicList)
    {
    	if (_listSet.getManager() == null) {_foreground.add(_listSet);}
		if (_listSet.getFieldCount() > 0) {_listSet.deleteAll();}

		Vector experiencesVector = topicList.getTopicList();
		Topic topic;

		for (int i=0; i<experiencesVector.size(); i++)
		{
			topic = (Topic) experiencesVector.elementAt(i);
			if (topic != null)
			{
				_item = new ListStyleButtonField(topic);
				_item.setChangeListener(new FieldChangeListener()
				{
					public void fieldChanged(Field field, int context)
					{
						if (context != FieldChangeListener.PROGRAMMATIC) {}
					}
				});

				_listSet.add(_item);
			}
		}

		if (_listSet.getFieldCount() > 0)
		{
			UiApplication.getUiApplication().invokeLater(new Runnable()
			{
				public void run() {}//refreshExperienceListIcons();}
			});
		}
    }

    private void refreshExperienceListIcons()
    {
		ListStyleButtonField item;

		_queue.removeAll();

		for (int i=0; i<_listSet.getFieldCount(); i++)
		{
			Field object = _listSet.getField(i);

			if (object instanceof ListStyleButtonField)
			{
				item = (ListStyleButtonField) object;
				_queue.execute(new DownloadImages(item));
			}
		}
    }

    class DownloadImages implements Runnable
    {
    	ListStyleButtonField _item;
		MyConnectionFactory cf = new MyConnectionFactory();
    	MyConnectionFactory _factory = new MyConnectionFactory();
    	HttpConnection _conn = null;
    	InputStream inputStream = null;

    	public DownloadImages(ListStyleButtonField item) {_item = item;}

		public void run()
		{
			if (_item.getThumbnailUrl().equalsIgnoreCase("")) {return;}

			ConnectionDescriptor connd = _factory.getConnection(_item.getThumbnailUrl());

			if (connd == null) {return;}

			try {
				_conn = (HttpConnection) connd.getConnection();
				_conn.setRequestProperty(HttpProtocolConstants.HEADER_CONNECTION, HttpProtocolConstants.HEADER_KEEP_ALIVE);

				inputStream = _conn.openInputStream();

				if (inputStream.available() > 0)
				{
					byte[] data = IOUtilities.streamToBytes(inputStream);

					if (data.length == _conn.getLength()) {_item.setThumbnail(data);}
				}

				inputStream.close();
				_conn.close();
			} catch (IOException e) {}
		}
	}
}