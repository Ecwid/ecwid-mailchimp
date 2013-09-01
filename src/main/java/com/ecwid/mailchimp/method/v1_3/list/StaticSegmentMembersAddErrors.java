package com.ecwid.mailchimp.method.v1_3.list;

import com.ecwid.mailchimp.MailChimpObject;

import java.util.Date;

/**
 *
 */
public class StaticSegmentMembersAddErrors extends MailChimpObject
{
    @Field
    public String email;

	@Field
	public String code;

	@Field
	public String msg;

}
