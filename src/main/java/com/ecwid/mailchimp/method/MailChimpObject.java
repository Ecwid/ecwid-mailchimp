/*
 *  (c) 2008-2012 Creative Development LLC. All rights reserved.
 *                http://www.qualiteam.biz/
 */
package com.ecwid.mailchimp.method;

import com.ecwid.mailchimp.MailChimpGsonFactory;


/**
 * Base class for all objects wrapping MailChimp API calls.
 * 
 * @author Vasily Karyaev <v.karyaev@gmail.com>
 */
public abstract class MailChimpObject {
	@Override
	public final String toString() {
		return getClass().getSimpleName()+":"+toJson();
	}
	
	public final String toJson() {
		return MailChimpGsonFactory.createGson().toJson(this);
	}
	
	public static <T extends MailChimpObject> T fromJson(String json, Class<T> clazz) {
		return MailChimpGsonFactory.createGson().fromJson(json, clazz);
	}
}
