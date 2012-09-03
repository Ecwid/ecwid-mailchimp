/*
 *  (c) 2008-2012 Creative Development LLC. All rights reserved.
 *                http://www.qualiteam.biz/
 */
package com.ecwid.mailchimp.method;

import com.ecwid.mailchimp.annotation.MailChimpField;
import java.util.List;

/**
 *
 * @author Vasily Karyaev <v.karyaev@gmail.com>
 */
public class ListMembersResult extends MailChimpObject {
	@MailChimpField
	public Integer total;
	
	@MailChimpField
	public List<ShortMemberInfo> data;
}
