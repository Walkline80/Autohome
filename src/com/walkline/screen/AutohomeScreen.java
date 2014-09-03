package com.walkline.screen;

import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.FontFamily;
import net.rim.device.api.ui.FontManager;
import net.rim.device.api.ui.Ui;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.container.MainScreen;

import com.walkline.autohome.AutohomeException;
import com.walkline.autohome.AutohomeSDK;
import com.walkline.util.Function;
import com.walkline.util.network.WorkQueue;
import com.walkline.util.ui.ForegroundManager;

public final class AutohomeScreen extends MainScreen
{
	private AutohomeSDK _autohome = AutohomeSDK.getInstance();
	private WorkQueue _queue = new WorkQueue(3);
	//private ListStyleButtonSet _smzdmSet = new ListStyleButtonSet();
	//private ListStyleButtonField _item;
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

        //UiApplication.getUiApplication().invokeLater(new Runnable()
        //{
		//	public void run()
		//	{
				try {
					_autohome.getTopicList("1");
				} catch (AutohomeException e) {}
		//		//getTopicList(String.valueOf(1));
		//	}
		//});
    }

    private void getTopicList(final String page)
    {
    	UiApplication.getUiApplication().invokeLater(new Runnable()
    	{
			public void run()
			{
				
		    	//ExperienceList experienceList;
				//RefreshContentsScreen popupScreen = new RefreshContentsScreen(_smzdm, page, RefreshActions.EXPERIENCELIST);
				//UiApplication.getUiApplication().pushModalScreen(popupScreen);

				//experienceList = popupScreen.getExperienceList();

				//if (popupScreen != null) {popupScreen = null;}
				//if (experienceList != null)
				//{
				//	refreshExperienceList(experienceList);
				//}
			}
		});
    }
}