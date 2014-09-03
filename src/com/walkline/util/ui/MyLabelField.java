package com.walkline.util.ui;

import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.component.LabelField;

class MyLabelField extends LabelField
{
    public MyLabelField(String text) {super(text, LabelField.ELLIPSIS);}

	public void layout(int width, int height) {super.layout(width, height);}   

	public void paint(Graphics g) {super.paint(g);}
}