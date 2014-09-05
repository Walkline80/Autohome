package com.walkline.screen;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import net.rim.blackberry.api.browser.Browser;
import net.rim.blackberry.api.browser.BrowserSession;
import net.rim.device.api.browser.field2.BrowserField;
import net.rim.device.api.browser.field2.BrowserFieldConfig;
import net.rim.device.api.browser.field2.BrowserFieldListener;
import net.rim.device.api.io.IOUtilities;
import net.rim.device.api.system.AccelerometerSensor;
import net.rim.device.api.system.Display;
import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.ScrollView;
import net.rim.device.api.ui.TransitionContext;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.Menu;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.container.VerticalFieldManager;
import net.rim.device.api.util.StringProvider;

import org.w3c.dom.Document;

import com.walkline.autohome.AutohomeSDK;
import com.walkline.util.Enumerations.RefreshActions;
import com.walkline.util.Function;

public class TopicDetailsScreen extends MainScreen// implements OnShakeListener
{
	AutohomeSDK _autohome;
	String _url;
	LabelField _title;
	BrowserField _browserField;
	MyBrowserFieldListener _listener = new MyBrowserFieldListener();
	VerticalFieldManager _vfm = new VerticalFieldManager(ScrollView.VERTICAL_SCROLL);
	int _targetWidth = Display.getWidth();
	int _targetHeight = _targetWidth /2;

	public TopicDetailsScreen(AutohomeSDK autohome, String url)
	{
		super(MainScreen.VERTICAL_SCROLL | MainScreen.VERTICAL_SCROLLBAR | NO_SYSTEM_MENU_ITEMS);

		_autohome = autohome;
		_url = url;

		BrowserFieldConfig config = new BrowserFieldConfig();
		config.setProperty(BrowserFieldConfig.NAVIGATION_MODE, BrowserFieldConfig.NAVIGATION_MODE_POINTER);
		_browserField = new BrowserField(config);
		_browserField.addListener(_listener);

		_vfm.add(_browserField);
		add(_vfm);

		Function.attachTransition(this, TransitionContext.TRANSITION_SLIDE);

		UiApplication.getUiApplication().invokeLater(new Runnable()
		{
			public void run()
			{
				refreshUI();
				//RefreshContentsScreen popupScreen = new RefreshContentsScreen(_smzdm, _url, RefreshActions.EXPERIENCEDETAILS);
				//UiApplication.getUiApplication().pushModalScreen(popupScreen);

				//_experienceDetails = popupScreen.getExperienceDetails();

				//if (popupScreen != null) {popupScreen = null;}
				//if (_experienceDetails != null) {refreshUI();}
			}
		});
	}

	private void refreshUI()
	{
		_browserField.requestContent(_url);
		//String html = _experienceDetails.getContent();

		//try {
			//InputStream input = getClass().getResourceAsStream("/css/main");
			//String css = new String(IOUtilities.streamToBytes(input));

			//_browserField.displayContent("<html><head><meta charset='utf-8'><meta name='viewport' content='width=device-width, minimum-scale=1.0, maximum-scale=1.0'>" + css + "</head><body>" + new String(html.getBytes("utf-8")) + "</div></body></html>", "localhost://");
		//} catch (UnsupportedEncodingException e) {}
		//  catch (IOException e) {}
	}

	private void browseInBrowser()
	{
		BrowserSession session = Browser.getDefaultSession();
		//session.displayPage(_experienceDetails.getBtnUrl());
	}

	MenuItem menuBrowseInBrowser = new MenuItem(new StringProvider("查看原文"), 100, 10)
	{
		public void run() {browseInBrowser();}
	};

	MenuItem menuNightMode = new MenuItem(new StringProvider("夜间模式"), 100, 20)
	{
		public void run()
		{
			String className;
			String jsCode;

			//_night_mode = !_night_mode;

			//if (_night_mode)
			//{
			//	menuNightMode.setText(new StringProvider("\u221A夜间模式"));
			//	className = "night";
			//} else {
			//	menuNightMode.setText(new StringProvider("夜间模式"));
			//	className = "";
			//}

			//jsCode = "var obj=document.getElementById('night');obj.className='" + className + "'";
			jsCode = "var imgs = document.querySelectorAll('img');for (var i=0; i<imgs.length; i++){var img = imgs[i];if (img.getAttribute('class') != 'face'){img.src = img.getAttribute('src1');img.className = 'center';}}var spans = document.querySelectorAll('span');for (var key=0; key<spans.length; key++){var span = spans[key];var str;str = span.outerHTML;str = str.replace(/<.*?>/ig,'');span.outerHTML = str;}";
			try {_browserField.executeScript(jsCode);} catch (Exception e) {}
		}
	};

	protected void makeMenu(Menu menu, int instance)
	{
		menu.add(menuBrowseInBrowser);
		menu.add(menuNightMode);
		menu.addSeparator();

		super.makeMenu(menu, instance);
	};

	public boolean onClose()
	{
		UiApplication.getUiApplication().popScreen(this);

		return true;
	}

	protected class MyBrowserFieldListener extends BrowserFieldListener
	{
		public void documentLoaded(BrowserField browserField, Document document) throws Exception
		{
			String jsCode;

			jsCode = "var imgs = document.querySelectorAll('img');for (var i=0; i<imgs.length; i++){var img = imgs[i];if (img.getAttribute('class') != 'face'){img.src = img.getAttribute('src1');img.className = 'center';}}var spans = document.querySelectorAll('span');for (var key=0; key<spans.length; key++){var span = spans[key];var str;str = span.outerHTML;str = str.replace(/<.*?>/ig,'');span.outerHTML = str;}";
			try {_browserField.executeScript(jsCode);} catch (Exception e) {}
		}

		public void documentAborted(BrowserField browserField, Document document) throws Exception {}
		public void documentCreated(BrowserField browserField, Document document) throws Exception {}
		public void documentError(BrowserField browserField, Document document) throws Exception {}
		public void documentUnloading(BrowserField browserField, Document document) throws Exception {}
		public void downloadProgress(BrowserField browserField, Document document) throws Exception {}
	}
}