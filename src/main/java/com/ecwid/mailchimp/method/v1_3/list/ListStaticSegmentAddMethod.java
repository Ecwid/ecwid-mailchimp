package com.ecwid.mailchimp.method.v1_3.list;

import com.ecwid.mailchimp.MailChimpAPIVersion;
import com.ecwid.mailchimp.MailChimpMethod;

/**
 * See http://apidocs.mailchimp.com/api/1.3/liststaticsegmentadd.func.php
 */
@MailChimpMethod.Method(name = "listStaticSegmentAdd", version = MailChimpAPIVersion.v1_3)
public class ListStaticSegmentAddMethod extends HasListIdMethod<Integer> {
	@Field
	public String name;
}
