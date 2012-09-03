/*
 *  (c) 2008-2012 Creative Development LLC. All rights reserved.
 *                http://www.qualiteam.biz/
 */
package com.ecwid.mailchimp;

/**
 * Indicates MailChimp API errors.
 * 
 * @author Vasily Karyaev <v.karyaev@gmail.com>
 */
public class MailChimpException extends Exception {

	/**
	 * Error code.
	 */
	public final int code;
	
	/**
	 * Error message.
	 */
	public final String message;
	
	public MailChimpException(int code, String message) {
		super("API Error ("+code+"): "+message);
		this.code = code;
		this.message = message;
	}
}
