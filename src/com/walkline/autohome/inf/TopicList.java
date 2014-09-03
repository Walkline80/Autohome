package com.walkline.autohome.inf;

import java.util.Vector;

public interface TopicList extends com.walkline.autohome.inf.Object
{
	public String getMessage();

	public int getReturnCode();

	public int getPageIndex();

	public int getPageCount();

	public int getRowCount();

	public Vector getTopicList();
}