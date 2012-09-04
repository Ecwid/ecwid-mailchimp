/*
 *  (c) 2008-2012 Creative Development LLC. All rights reserved.
 *                http://www.qualiteam.biz/
 */
package com.ecwid.mailchimp.method;

import com.ecwid.mailchimp.annotation.MailChimpField;
import java.util.Date;

/**
 * See http://apidocs.mailchimp.com/api/1.3/listmembers.func.php
 *
 * @author Vasily Karyaev <v.karyaev@gmail.com>
 */
public class ListMembers extends AbstractListMethod<ListMembersResult> {

	@MailChimpField
	public MemberStatus status;
	
	@MailChimpField
	public Date since;

	@MailChimpField
	public Integer start;

	@MailChimpField
	public Integer limit;
	
	@Override
	public Class<ListMembersResult> getResultType() {
		return ListMembersResult.class;
	}
}
