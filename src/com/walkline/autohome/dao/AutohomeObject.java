package com.walkline.autohome.dao;

import com.walkline.autohome.AutohomeException;
import com.walkline.autohome.AutohomeSDK;
import com.walkline.util.json.JSONObject;

public class AutohomeObject implements com.walkline.autohome.inf.Object
{
	protected AutohomeSDK autohome;
	protected JSONObject jsonObject;

	public AutohomeObject(AutohomeSDK pAutohome, JSONObject pJsonObject) throws AutohomeException
	{
		if ((pAutohome == null) || (pJsonObject == null)) {
			throw new AutohomeException("Unable to create Autohome AutohomeObject.");
		}

		autohome = pAutohome;
		jsonObject = pJsonObject;
	}
}