/*
 *  (c) 2008-2012 Creative Development LLC. All rights reserved.
 *                http://www.qualiteam.biz/
 */
package com.ecwid.mailchimp.method;

import com.ecwid.mailchimp.annotation.MailChimpField;
import java.util.List;

/**
 * See http://apidocs.mailchimp.com/api/1.3/listbatchsubscribe.func.php
 * 
 * @author Vasily Karyaev <v.karyaev@gmail.com>
 */
public class ListBatchSubscribe extends MailChimpMethod<ListBatchSubscribeResult> {

	@MailChimpField
	public String id;
	
	@MailChimpField
	public List<MergeVars> batch;
	
	@MailChimpField
	public Boolean double_optin;
	
	@MailChimpField
	public Boolean update_existing;
	
	@MailChimpField
	public Boolean replace_interests;

	@Override
	public Class<ListBatchSubscribeResult> getResultType() {
		return ListBatchSubscribeResult.class;
	}
}
