package com.walkline.app;

import net.rim.device.api.ui.UiApplication;
import com.walkline.screen.AutohomeScreen;

public class AutohomeApp extends UiApplication
{
    public static void main(String[] args)
    {
        AutohomeApp theApp = new AutohomeApp();       
        theApp.enterEventDispatcher();
    }
    
    public AutohomeApp() {pushScreen(new AutohomeScreen());}    
}