package com.walkline.autohome.dao;

import org.json.me.JSONObject;
import com.walkline.autohome.AutohomeException;
import com.walkline.autohome.AutohomeSDK;

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