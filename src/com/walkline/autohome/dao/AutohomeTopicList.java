package com.walkline.autohome.dao;

import java.util.Vector;
import org.json.me.JSONArray;
import org.json.me.JSONException;
import org.json.me.JSONObject;
import com.walkline.autohome.AutohomeException;
import com.walkline.autohome.AutohomeSDK;
import com.walkline.autohome.inf.Topic;
import com.walkline.autohome.inf.TopicList;
import com.walkline.util.Function;

public class AutohomeTopicList extends AutohomeObject implements TopicList
{
	private String _message = "";
	private int _return_code = 0;
	private int _page_index = 0;
	private int _page_count = 0;
	private int _row_count =0;
	private Vector _list = new Vector();

	public AutohomeTopicList(AutohomeSDK autohome, JSONObject jsonObject) throws AutohomeException
	{
		super(autohome, jsonObject);

		JSONObject topicList = jsonObject;
		if (topicList != null)
		{
			_message = topicList.optString("message");
			_return_code = topicList.optInt("returncode");

			JSONObject result = topicList.optJSONObject("result");
			if (result != null)
			{
				_page_index = result.optInt("pageindex");
				_page_count = result.optInt("pagecount");
				_row_count = result.optInt("rowcount");
			}

			JSONArray listArray = topicList.optJSONArray("list");
			if (listArray != null)
			{
				JSONObject topicObject;
				for (int i=0; i<listArray.length(); i++)
				{
					try {
						topicObject = (JSONObject) listArray.get(i);

						Topic topic = new AutohomeTopic(autohome, topicObject);
						if (topic != null) {_list.addElement(topic);}
					} catch (JSONException e) {Function.errorDialog(e.toString());}
				}
			}
		}
	}

	public String getMessage() {return _message;}

	public int getReturnCode() {return _return_code;}

	public int getPageIndex() {return _page_index;}

	public int getPageCount() {return _page_count;}

	public int getRowCount() {return _row_count;}

	public Vector getTopicList() {return _list;}
}