package com.walkline.util.ui;

import com.walkline.app.AutohomeAppConfig;
import com.walkline.autohome.inf.Topic;
import com.walkline.util.BitmapMask;
import com.walkline.util.GPATools;
import com.walkline.util.StringUtility;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.Characters;
import net.rim.device.api.system.Display;
import net.rim.device.api.system.KeypadListener;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.TouchEvent;
import net.rim.device.api.ui.XYEdges;

public class ListStyleButtonField extends Field
{
    public static final int DRAWPOSITION_TOP = 0;
    public static final int DRAWPOSITION_BOTTOM = 1;
    public static final int DRAWPOSITION_MIDDLE = 2;
    public static final int DRAWPOSITION_SINGLE = 3;

    private static final int CORNER_RADIUS = 16;

    private static final int HPADDING = Display.getWidth() <= 320 ? 4 : 6;

    private static int COLOR_BACKGROUND = 0xFFFFFF;
    private static int COLOR_BORDER = 0xBBBBBB;
    private static int COLOR_BACKGROUND_FOCUS = 0x186DEF;

    private static final boolean USING_LARGE_ICON = Display.getWidth()<640 ? false : true;
    private Bitmap _iconStoryTitle;
    private int _iconStoryTitleSize = 0;
    private MyLabelFieldWithNewline _labelStoryTitle;
    private MyLabelField _labelSectionTitle;
    private MyLabelField _labelSectionDescription;

    private String _thumbnailUrl;
    private byte[] _thumbnailData;
    private String _url;
    private String _name;
    private int _id = 0;

    private int _rightOffset;
    private int _leftOffset;
    private int _labelHeight;
    private int _drawPosition = -1;

	private static Font FONT_STORY_TITLE;
	private static Font FONT_SECTION_TITLE;
	private static Font FONT_SECTION_DESCRIPTION;

    public ListStyleButtonField(String title)
    {
        super(USE_ALL_WIDTH | Field.FOCUSABLE);

        _labelStoryTitle = new MyLabelFieldWithNewline(title);

        setFontSize();
    }

    public ListStyleButtonField(Topic topic)
    {
        super(USE_ALL_WIDTH | Field.FOCUSABLE);

        _labelStoryTitle = new MyLabelFieldWithNewline(topic.getTitle());
        _thumbnailUrl = topic.getSmallPic();
        _id = topic.getTopicId();
        _url = StringUtility.replace(AutohomeAppConfig.queryTopicRequestURL, "$TOPICID$", String.valueOf(_id));

        _iconStoryTitle = USING_LARGE_ICON ? Bitmap.getBitmapResource("listIcon_large.png") : Bitmap.getBitmapResource("listIcon_small.png");

        _iconStoryTitleSize = _iconStoryTitle.getWidth();

        setFontSize();
    }

    public void setDrawPosition(int drawPosition) {_drawPosition = drawPosition;}

    public void layout(int width, int height)
    {
    	if (_iconStoryTitle != null)
    	{
            _leftOffset = _iconStoryTitle.getWidth() + HPADDING * 2;    		
    	} else {
    		_leftOffset = HPADDING * 2;
    	}

        _rightOffset = HPADDING;

        if (_labelStoryTitle != null)
        {
        	_labelStoryTitle.layout(width - _leftOffset - _rightOffset, height);
            _labelHeight = _labelStoryTitle.getHeight();
        }

        if (_labelSectionTitle != null)
        {
        	_labelSectionTitle.layout(width - _leftOffset - _rightOffset, height);
        	_labelHeight = _labelSectionTitle.getHeight();

            if (_labelSectionDescription != null)
            {
            	_labelSectionDescription.layout(width - _leftOffset - _rightOffset, height);
            	_labelHeight += _labelSectionDescription.getHeight();
            }
        }

        if (_iconStoryTitle != null)
        {
        	if (_labelHeight < _iconStoryTitle.getHeight()) {_labelHeight = _iconStoryTitle.getHeight();}
        }

        setExtent(width, _labelHeight + 10);
    }

    public String getThumbnailUrl() {return _thumbnailUrl;}

    public void setThumbnail(byte[] data)
    {
		_thumbnailData = data;

		if (_thumbnailData != null)
		{
			Bitmap newIcon = Bitmap.createBitmapFromBytes(_thumbnailData, 0, -1, 1);
			newIcon = GPATools.ResizeTransparentBitmap(newIcon, _iconStoryTitleSize, _iconStoryTitleSize, Bitmap.FILTER_LANCZOS, Bitmap.SCALE_STRETCH);

			BitmapMask mask = new BitmapMask(new XYEdges(2, 2, 2, 2), Bitmap.getBitmapResource("mask.png"));
			mask.applyMask(newIcon);

			_iconStoryTitle = newIcon;

			Manager m = getManager();
			if (m != null) {m.invalidate();}
		}
    }

    public String getUrl() {return _url;}

    public int getSectionId() {return _id;}

    public String getSectionName() {return _name;}

    private void setFontSize()
    {
    	FONT_STORY_TITLE = AutohomeAppConfig.FONT_STORY_TITLE;
    	FONT_SECTION_TITLE = AutohomeAppConfig.FONT_SECTION_TITLE;
    	FONT_SECTION_DESCRIPTION = AutohomeAppConfig.FONT_SECTION_DESCRIPTION;

    	if (_labelStoryTitle != null) {_labelStoryTitle.setFont(FONT_STORY_TITLE);}
    	if (_labelSectionTitle != null) {_labelSectionTitle.setFont(FONT_STORY_TITLE);}
    	if (_labelSectionDescription != null) {_labelSectionDescription.setFont(FONT_SECTION_DESCRIPTION);}
    }

    protected void paint(Graphics g)
    {
        // News Title Bitmap
    	if (_iconStoryTitle != null)
    	{
       		g.drawBitmap(HPADDING, (getHeight() - _iconStoryTitle.getHeight()) / 2, _iconStoryTitle.getWidth(), _iconStoryTitle.getHeight(), _iconStoryTitle, 0, 0);    		
    	}

        // News Title Text
    	if (_labelStoryTitle != null)
    	{
            try
            {
            	g.setFont(FONT_STORY_TITLE);
            	g.pushRegion(_leftOffset, (getHeight() - _labelStoryTitle.getHeight()) / 2, _labelStoryTitle.getWidth(), _labelStoryTitle.getHeight(), 0, 0);
            	_labelStoryTitle.paint(g);
            } finally {g.popContext();}
    	}

    	//Section Title Text
    	if (_labelSectionTitle != null)
    	{
    		int top = 0;
    		//int oldColor = g.getColor();

    		if (_labelSectionDescription != null)
    		{
    			top = (getHeight() - _labelSectionTitle.getHeight() - _labelSectionDescription.getHeight()) / 2;

        		try
        		{
        			g.setFont(FONT_SECTION_TITLE);
        			g.pushRegion(_leftOffset, top, _labelSectionTitle.getWidth(), _labelSectionTitle.getHeight(), 0, 0);
        			_labelSectionTitle.paint(g);
        		} finally {g.popContext();}

        		try
        		{
        			g.setFont(FONT_SECTION_DESCRIPTION);
        			g.setColor(g.isDrawingStyleSet(Graphics.DRAWSTYLE_FOCUS) ? Color.WHITE : Color.GRAY);
        			g.pushRegion(_leftOffset, getHeight() - _labelSectionDescription.getHeight() - HPADDING, _labelSectionDescription.getWidth(), _labelSectionDescription.getHeight(), 0, 0);
        			_labelSectionDescription.paint(g);
        		} finally {g.popContext();}
    		} else {
    			top = (getHeight() - _labelSectionTitle.getHeight()) /2;

        		try
        		{
        			g.setFont(FONT_SECTION_TITLE);
        			g.pushRegion(_leftOffset, top, _labelSectionTitle.getWidth(), _labelSectionTitle.getHeight(), 0, 0);
        			_labelSectionTitle.paint(g);
        		} finally {g.popContext();}
    		}
    	}
    }

    protected void paintBackground(Graphics g)
    {
        if(_drawPosition < 0)
        {
            super.paintBackground(g);
            return;
        }

        int oldColour = g.getColor();
        int background = g.isDrawingStyleSet(Graphics.DRAWSTYLE_FOCUS) ? COLOR_BACKGROUND_FOCUS : COLOR_BACKGROUND;

        try {
            if(_drawPosition == 0)
            {
                // Top
                g.setColor(background);
                g.fillRoundRect(0, 0, getWidth(), getHeight() + CORNER_RADIUS, CORNER_RADIUS, CORNER_RADIUS);
                g.setColor(COLOR_BORDER);
                g.drawRoundRect(0, 0, getWidth(), getHeight() + CORNER_RADIUS, CORNER_RADIUS, CORNER_RADIUS);
                g.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
            } else if(_drawPosition == 1)
            {
                // Bottom 
                g.setColor(background);
                g.fillRoundRect(0, -CORNER_RADIUS, getWidth(), getHeight() + CORNER_RADIUS, CORNER_RADIUS, CORNER_RADIUS);
                g.setColor(COLOR_BORDER);
                g.drawRoundRect(0, -CORNER_RADIUS, getWidth(), getHeight() + CORNER_RADIUS, CORNER_RADIUS, CORNER_RADIUS);
            } else if(_drawPosition == 2)
            {
                // Middle
                g.setColor(background);
                g.fillRoundRect(0, -CORNER_RADIUS, getWidth(), getHeight() + 2 * CORNER_RADIUS, CORNER_RADIUS, CORNER_RADIUS);
                g.setColor(COLOR_BORDER);
                g.drawRoundRect(0, -CORNER_RADIUS, getWidth(), getHeight() + 2 * CORNER_RADIUS, CORNER_RADIUS, CORNER_RADIUS);
                g.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
            } else {
                // Single
                g.setColor(background);
                g.fillRoundRect(0, 0, getWidth(), getHeight(), CORNER_RADIUS, CORNER_RADIUS);
                g.setColor(COLOR_BORDER);
                g.drawRoundRect(0, 0, getWidth(), getHeight(), CORNER_RADIUS, CORNER_RADIUS);
            }
        } finally {g.setColor(oldColour);}
    }

    protected void drawFocus(Graphics g, boolean on)
    {
        if(_drawPosition >= 0)
        {
            boolean oldDrawStyleFocus = g.isDrawingStyleSet(Graphics.DRAWSTYLE_FOCUS);
            try {
                if(on)
                {
                	g.setColor(Color.WHITE);
                    g.setDrawingStyle(Graphics.DRAWSTYLE_FOCUS, true);
                }
                paintBackground(g);
                paint(g);
            } finally {
                g.setDrawingStyle(Graphics.DRAWSTYLE_FOCUS, oldDrawStyleFocus);
            }
        }
    }

    protected boolean keyChar(char character, int status, int time) 
    {
    	switch (character)
    	{
			case Characters.ENTER:
	            clickButton();
	            return true;
        }

        return super.keyChar(character, status, time);
    }

    protected boolean navigationUnclick(int status, int time) 
    {
    	if ((status & KeypadListener.STATUS_FOUR_WAY) == KeypadListener.STATUS_FOUR_WAY)
    	{
        	clickButton();
        	return true;
    	}

    	return super.navigationClick(status, time);
    }

    protected boolean trackwheelClick(int status, int time)
    {
    	if ((status & KeypadListener.STATUS_TRACKWHEEL) == KeypadListener.STATUS_TRACKWHEEL)
    	{
       		clickButton();
       		return true;
    	}

    	return super.trackwheelClick(status, time);
    }

    protected boolean invokeAction(int action) 
    {
    	switch(action)
    	{
    		case ACTION_INVOKE:
           		clickButton();
           		return true;
    	}

    	return super.invokeAction(action);
    }

    protected boolean touchEvent(TouchEvent message)
    {
        int x = message.getX(1);
        int y = message.getY(1);

        if (x < 0 || y < 0 || x > getExtent().width || y > getExtent().height) {return false;}

        switch (message.getEvent())
        {
            case TouchEvent.UNCLICK:
           		clickButton();
           		return true;
        }

        return super.touchEvent(message);
    }

    public void clickButton() {fieldChangeNotify(0);}

    public void setDirty(boolean dirty) {}
    public void setMuddy(boolean muddy) {}
}