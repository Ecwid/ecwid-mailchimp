/*
 *  (c) 2008-2012 Creative Development LLC. All rights reserved.
 *                http://www.qualiteam.biz/
 */
package com.ecwid.mailchimp.method;

/**
 * Abstract class representing MailChimp API call.
 *
 * @param R type of the method call result
 * 
 * @author Vasily Karyaev <v.karyaev@gmail.com>
 */
public abstract class MailChimpMethod<R> extends MailChimpObject {

	/**
	 * Get MailChimp API method name.
	 * By default returns the simple class name with the first letter lowercased.
	 */
	public String getMethodName() {
		String name = getClass().getSimpleName();
		String head = name.substring(0, 1).toLowerCase();
		String tail = name.substring(1);
		return head + tail;
	}

	/**
	 * Get the class object representing method result type.
	 */
	public abstract Class<R> getResultType();
}
