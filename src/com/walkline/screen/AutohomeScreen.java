package com.walkline.screen;

import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

import javax.microedition.io.HttpConnection;

import me.regexp.CharacterIterator;
import me.regexp.RE;
import me.regexp.RECompiler;
import me.regexp.REProgram;
import me.regexp.StreamCharacterIterator;
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
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.container.MainScreen;

import com.walkline.autohome.AutohomeSDK;
import com.walkline.autohome.inf.Topic;
import com.walkline.autohome.inf.TopicList;
import com.walkline.util.Function;
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

    	setTitle("汽车之家 - 媳妇当车模");
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
				if (topicList != null) {refreshTopicList(topicList);}
			}
		});
    }

    private void refreshTopicList(TopicList topicList)
    {
    	if (_listSet.getManager() == null) {_foreground.add(_listSet);}
		//if (_listSet.getFieldCount() > 0) {_listSet.deleteAll();}

		Vector topicVector = topicList.getTopicList();
		Topic topic;

		for (int i=0; i<topicVector.size(); i++)
		{
			topic = (Topic) topicVector.elementAt(i);
			if (topic != null)
			{
				_item = new ListStyleButtonField(topic);
				_item.setChangeListener(new FieldChangeListener()
				{
					public void fieldChanged(Field field, int context)
					{
						if (context != FieldChangeListener.PROGRAMMATIC) {showTopicDetailScreen();}
					}
				});

				_listSet.add(_item);
			}
		}

		final int currentPage = topicList.getPageIndex() + 1;
		if (topicList.getPageIndex() < topicList.getPageCount())
		{
			_item = new ListStyleButtonField("下一页");
			_item.setChangeListener(new FieldChangeListener()
			{
				public void fieldChanged(Field field, int context)
				{
					if (context != FieldChangeListener.PROGRAMMATIC)
					{
						int currentPos = _listSet.getFieldWithFocusIndex();
						_listSet.delete(field);
						
						getTopicList(String.valueOf(currentPage));
					}
				}
			});
			_listSet.add(_item);
		}

		if (_listSet.getFieldCount() > 0)
		{
			UiApplication.getUiApplication().invokeLater(new Runnable()
			{
				public void run() {refreshTopicListIcons();}
			});
		}
    }

    private void refreshTopicListIcons()
    {
		ListStyleButtonField item = null;

		_queue.removeAll();

		for (int i=0; i<_listSet.getFieldCount(); i++)
		{
			Field object = _listSet.getField(i);

			if (object instanceof ListStyleButtonField)
			{
				item = (ListStyleButtonField) object;
				if (!item.hasThumbnail())
				{
					_queue.execute(new DownloadImages(item));
				}
			}
		}
    }

    private void showTopicDetailScreen()
    {
    	ListStyleButtonField item = (ListStyleButtonField) _listSet.getFieldWithFocus();

    	UiApplication.getUiApplication().pushScreen(new TopicDetailsScreen(_autohome, item.getUrl()));
    }

    class DownloadImages implements Runnable
    {
    	ListStyleButtonField item;
		MyConnectionFactory cf = new MyConnectionFactory();
    	HttpConnection conn = null;
		StringBuffer buffer = new StringBuffer();
		String url;

    	public DownloadImages(ListStyleButtonField item)
    	{
    		this.item = item;
    		this.url = item.getThumbnailUrl();
    	}

		public void run()
		{
			try {
				if ((url == null) || url.equalsIgnoreCase("") || (cf == null)) {return;}

				ConnectionDescriptor connd = cf.getConnection(url);
				conn = (HttpConnection) connd.getConnection();

				conn.setRequestProperty(HttpProtocolConstants.HEADER_CONNECTION, HttpProtocolConstants.HEADER_KEEP_ALIVE);
				conn.setRequestProperty(HttpProtocolConstants.HEADER_REFERER, "http://www.autohome.com.cn");
				//conn.setRequestProperty(HttpProtocolConstants.HEADER_USER_AGENT, System.getProperty("browser.useragent"));//"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1985.143 Safari/537.36");

				int resCode = conn.getResponseCode();

				switch (resCode)
				{
					case HttpConnection.HTTP_OK: 
					{
						InputStream inputStream = conn.openInputStream();
						int c;

						while ((c = inputStream.read()) != -1) {buffer.append((char) c);}

						item.setThumbnail(buffer.toString().getBytes());
						inputStream.close();
						break;
					}
				}
			} catch (Exception e) {}
			  finally {if (conn != null) {try {conn.close(); conn = null;} catch (IOException e) {Function.errorDialog(e.toString());}}}
		}
	}
}