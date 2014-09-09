package com.walkline.autohome;

import java.io.UnsupportedEncodingException;

import com.walkline.app.AutohomeAppConfig;
import com.walkline.autohome.dao.AutohomeTopicList;
import com.walkline.autohome.inf.TopicList;
import com.walkline.util.Function;
import com.walkline.util.StringUtility;
import com.walkline.util.json.JSONObject;
import com.walkline.util.json.JSONTokener;
import com.walkline.util.network.HttpClient;
import com.walkline.util.network.MyConnectionFactory;

public class AutohomeSDK
{
	protected HttpClient _http;

	public static AutohomeSDK getInstance() {return new AutohomeSDK();}

	protected AutohomeSDK()
	{
		MyConnectionFactory cf = new MyConnectionFactory();

		_http = new HttpClient(cf);
	}

	private TopicList getTopicList(JSONObject jsonObject) throws AutohomeException {return new AutohomeTopicList(this, jsonObject);}
	public TopicList getTopicList(String page) throws AutohomeException {return getTopicList(page, null);}
	private TopicList getTopicList(final String page, final Object state)
	{
		TopicList result = null;
		JSONObject jsonObject = new JSONObject();

		try {
			String api = AutohomeAppConfig.queryTopicListRequestURL;
			api = StringUtility.replace(api, "$PAGECOUNT$", page);
			jsonObject = doRequest(api);

			result = (jsonObject != null ? getTopicList(jsonObject) : null);
		} catch (AutohomeException e) {Function.errorDialog(e.toString());}

		return result;
	}

	//private StringBuffer getTopicDetails(JSONObject jsonObject) throws AutohomeException {return new AutohomeTopicList(this, jsonObject);}
	public String getTopicDetails(String topicId) throws AutohomeException {return getTopicDetails(topicId, null);}
	private String getTopicDetails(final String topicId, final Object state)
	{
		String result = null;
		String stringObject = null;

		try {
			String api = AutohomeAppConfig.queryTopicRequestURL;
			api = StringUtility.replace(api, "$TOPICID$", topicId);
			stringObject = new String(doRequestRAW(api));

			try {
				result = (stringObject != null ? new String(stringObject.getBytes(), "utf-8") : null);
			} catch (UnsupportedEncodingException e) {}
		} catch (AutohomeException e) {Function.errorDialog(e.toString());}

		return result;
	}

	/*
	private ExperienceList getExperienceList(JSONObject jsonObject) throws AutohomeException {return new SMZDMExperienceList(this, jsonObject);}
	public ExperienceList getExperienceList(String page) throws AutohomeException {return getExperienceList(page, null, null);}
	public ExperienceList getExperienceList(final String page, final AsyncCallback listener, final Object state)
	{
		if (listener != null) {
			new Thread() {
				public void run() {
					try {
						ExperienceList result = null;
						result = getExperienceList(page);
						listener.onComplete(result, null);
					} catch (Exception e) {
						listener.onException(e, null);
					}
				}
			}.start();

			return null;
		} else {
			ExperienceList result = null;
			JSONObject jsonObject = new JSONObject();

			try {
				String api = SMZDMAppConfig.queryExperienceListRequestURL;
				api = StringUtility.replace(api, "$PAGE$", page);
				jsonObject = doRequest(api);

				result = (jsonObject != null ? getExperienceList(jsonObject) : null);
			} catch (AutohomeException e) {Function.errorDialog(e.toString());}

			return result;
		}
	}

	private ExperienceDetails getExperienceDetails(JSONObject jsonObject) throws AutohomeException {return new SMZDMExperienceDetails(this, jsonObject);}
	public ExperienceDetails getExperienceDetails(String postId) throws AutohomeException {return getExperienceDetails(postId, null, null);}
	public ExperienceDetails getExperienceDetails(final String postId, final AsyncCallback listener, final Object state)
	{
		if (listener != null) {
			new Thread() {
				public void run() {
					try {
						ExperienceDetails result = null;
						result = getExperienceDetails(postId);
						listener.onComplete(result, null);
					} catch (Exception e) {
						listener.onException(e, null);
					}
				}
			}.start();

			return null;
		} else {
			ExperienceDetails result = null;
			JSONObject jsonObject = new JSONObject();

			try {
				String api = SMZDMAppConfig.queryExperienceDetailsRequestURL;
				api = StringUtility.replace(api, "$POSTID$", postId);
				jsonObject = doRequest(api);

				result = (jsonObject != null ? getExperienceDetails(jsonObject) : null);
			} catch (AutohomeException e) {Function.errorDialog(e.toString());}

			return result;
		}
	}

	/*
	private HotStories getHotStories(JSONObject jsonObject) throws SMZDMException {return new ZhihuHotStories(this, jsonObject);}
	public HotStories getHotStories() throws SMZDMException {return getHotStories(null, null);}
	public HotStories getHotStories(final AsyncCallback listener, final Object state)
	{
		if (listener != null) {
			new Thread() {
				public void run() {
					try {
						HotStories result = null;
						result = getHotStories();
						listener.onComplete(result, null);
					} catch (Exception e) {
						listener.onException(e, null);
					}
				}
			}.start();

			return null;
		} else {
			HotStories result = null;
			JSONObject jsonObject = new JSONObject();

			try {
				jsonObject = doRequest(SMZDMAppConfig.queryHotStoriesRequestURL);

				result = (jsonObject != null ? getHotStories(jsonObject) : null);
			} catch (SMZDMException e) {Function.errorDialog(e.toString());}

			return result;
		}
	}
	*/





//**************************************************************************************************
//
//             //              //      //    //      ////////////////            //      //        
//             //            //    //  //  //                    //          //  //  //  //        
// //////////  //          //////////  ////        //          //      //        //      //        
//     //    ////////////              //      //  //  //    //    //  //  ////////////  ////////  
//     //      //      //    ////////    ////////  //    //  //  //    //      ////    //    //    
//     //      //      //    //    //              //        //        //    //  ////    //  //    
//     //      //      //    ////////  //          //    //  //  //    //  //    //  //  //  //    
//     //      //      //    //    //  //    //    //  //    //    //  //      //        //  //    
//     ////////        //    ////////  //  //      //        //        //  //////////    //  //    
// ////      //        //    //    //  ////    //  //      ////        //    //    //      //      
//         //          //    //    //  //      //  //                  //      ////      //  //    
//       //        ////      //  ////    ////////  //////////////////////  ////    //  //      //  
//
//**************************************************************************************************

	public byte[] doRequestRAW(String imageUrl) throws AutohomeException
	{
		byte[] result = null;
		StringBuffer responseBuffer = null;

		try {
			responseBuffer = _http.doGet(imageUrl);				

			if ((responseBuffer == null) || (responseBuffer.length() <= 0))
			{
				result = null;
			} else {
				result = responseBuffer.toString().getBytes();				
			}
		} catch (Exception e) {
			throw new AutohomeException("[doRequestRAW Exception]\n\n" + e.getMessage());
		} catch (Throwable t) {
			throw new AutohomeException("[doRequestRAW Throwable]\n\n" + t.getMessage());
		}

		return result;
	}

	private JSONObject doRequest(String api) throws AutohomeException
	{
		StringBuffer responseBuffer = new StringBuffer();
		JSONObject result = new JSONObject();

		try {
			responseBuffer = _http.doGet(api);

			if ((responseBuffer == null) || (responseBuffer.length() <= 0))
			{
				result = null;
			} else {
				result = new JSONObject(new JSONTokener(new String(responseBuffer.toString().getBytes(), "utf-8")));
			}
		} catch (Exception e) {
			throw new AutohomeException(e.getMessage());
		} catch (Throwable t) {
			throw new AutohomeException(t.getMessage());
		}

		return result;
	}
}