package com.walkline.util.ui;

import net.rim.device.api.ui.Graphics;

public class ForegroundManager extends NegativeMarginVerticalFieldManager
{
    public ForegroundManager(long style)
    {
        super(USE_ALL_HEIGHT | VERTICAL_SCROLL | style);
    }

    protected void paintBackground(Graphics g)
    {
        int oldColor = g.getColor();

        try {
            g.setColor(0xDDDDDD);
            g.fillRect(0, getVerticalScroll(), getWidth(), getHeight());
        } finally {
            g.setColor(oldColor);
        }
    }
}