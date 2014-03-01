package com.ecwid.mailchimp.method.v1_3.list;

import com.ecwid.mailchimp.MailChimpAPIVersion;
import com.ecwid.mailchimp.MailChimpMethod;

/**
 * See http://apidocs.mailchimp.com/api/1.3/liststaticsegmentdel.func.php
 */
@MailChimpMethod.Method(name = "listStaticSegmentDel", version = MailChimpAPIVersion.v1_3)
public class ListStaticSegmentDelMethod extends HasListIdMethod<Boolean> {

	@Field
	public Integer seg_id;
}
