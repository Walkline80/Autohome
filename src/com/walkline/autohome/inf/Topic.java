package com.walkline.autohome.inf;

public interface Topic
{
	public int getTopicId();

	public String getTitle();

	public String getLastReplyDate();

	public String getPostUsername();

	public int getReplyCounts();

	public boolean getIsClosed();

	public String getBigPic();

	public String getSmallPic();

	public String getTopicType();

	public int getViews();

	public int getPostMemberId();

	public String getImageUrl();

	public int getBbsId();

	public String getBbsType();

	public String getBbsName();
}