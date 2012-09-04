/*
 *  (c) 2008-2012 Creative Development LLC. All rights reserved.
 *                http://www.qualiteam.biz/
 */
package com.ecwid.mailchimp.method;

import com.ecwid.mailchimp.annotation.MailChimpField;
import java.util.List;

/**
 * See http://apidocs.mailchimp.com/api/1.3/listbatchunsubscribe.func.php
 * 
 * @author Vasily Karyaev <v.karyaev@gmail.com>
 */
public class ListBatchUnsubscribe extends AbstractListMethod<ListBatchUnsubscribeResult> {

	@MailChimpField
	public List<String> emails;
	
	@MailChimpField
	public Boolean delete_member;
	
	@MailChimpField
	public Boolean send_goodbye;
	
	@MailChimpField
	public Boolean send_notify;

	@Override
	public Class<ListBatchUnsubscribeResult> getResultType() {
		return ListBatchUnsubscribeResult.class;
	}
}
