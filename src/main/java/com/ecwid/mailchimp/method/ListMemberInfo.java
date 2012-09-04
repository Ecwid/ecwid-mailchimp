/*
 *  (c) 2008-2012 Creative Development LLC. All rights reserved.
 *                http://www.qualiteam.biz/
 */
package com.ecwid.mailchimp.method;

import com.ecwid.mailchimp.annotation.MailChimpField;
import java.util.List;

/**
 * See http://apidocs.mailchimp.com/api/1.3/listmemberinfo.func.php
 *
 * @author Vasily Karyaev <v.karyaev@gmail.com>
 */
public class ListMemberInfo extends AbstractListMethod<ListMemberInfoResult> {

	@MailChimpField
	public List<String> email_address;

	@Override
	public Class<ListMemberInfoResult> getResultType() {
		return ListMemberInfoResult.class;
	}
}
