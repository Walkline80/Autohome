package com.walkline.autohome.dao;

import com.walkline.autohome.AutohomeException;
import com.walkline.autohome.AutohomeSDK;
import com.walkline.autohome.inf.Topic;
import com.walkline.util.json.JSONObject;

public class AutohomeTopic extends AutohomeObject implements Topic
{
	private int _topic_id = 0;
	private String _title = "";
	private String _last_reply_date = "";
	private String _post_username = "";
	private int _reply_counts = 0;
	private boolean _is_closed = false;
	private String _big_pic = "";
	private String _small_pic = "";
	private String _topic_type = "";
	private int _views = 0;
	private int _post_member_id = 0;
	private String _image_url = "";
	private int _bbs_id = 0;
	private String _bbs_type = "";
	private String _bbs_name = "";

	public AutohomeTopic(AutohomeSDK autohome, JSONObject jsonObject) throws AutohomeException
	{
		super(autohome, jsonObject);

		JSONObject topic = jsonObject;
		if (topic != null)
		{
			_topic_id = topic.optInt("topicid");
			_title = topic.optString("title");
			_last_reply_date = topic.optString("lastreplydate");
			_post_username = topic.optString("postusername");
			_reply_counts = topic.optInt("replycounts");

			int isClosed = topic.optInt("isclosed");
			_is_closed = isClosed > 0 ? true : false;

			_big_pic = topic.optString("bigpic");
			_small_pic = topic.optString("smallpic");
			_topic_type = topic.optString("topictype");
			_views = topic.optInt("views");
			_post_member_id = topic.optInt("postmemberid");
			_image_url = topic.optString("imgurl");
			_bbs_id = topic.optInt("bbsid");
			_bbs_type =topic.optString("bbstype");
			_bbs_name = topic.optString("bbsname");
		}
	}

	public int getTopicId() {return _topic_id;}

	public String getTitle() {return _title;}

	public String getLastReplyDate() {return _last_reply_date;}

	public String getPostUsername() {return _post_username;}

	public int getReplyCounts() {return _reply_counts;}

	public boolean getIsClosed() {return _is_closed;}

	public String getBigPic() {return _big_pic;}

	public String getSmallPic() {return _small_pic;}

	public String getTopicType() {return _topic_type;}

	public int getViews() {return _views;}

	public int getPostMemberId() {return _post_member_id;}

	public String getImageUrl() {return _image_url;}

	public int getBbsId() {return _bbs_id;}

	public String getBbsType() {return _bbs_type;}

	public String getBbsName() {return _bbs_name;}
}