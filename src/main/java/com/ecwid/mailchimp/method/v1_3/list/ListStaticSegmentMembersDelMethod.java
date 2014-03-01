package com.ecwid.mailchimp.method.v1_3.list;

import com.ecwid.mailchimp.MailChimpAPIVersion;
import com.ecwid.mailchimp.MailChimpMethod;

import java.util.List;

/**
 * See http://apidocs.mailchimp.com/api/1.3/liststaticsegmentmembersdel.func.php
 */
@MailChimpMethod.Method(name = "listStaticSegmentMembersDel", version = MailChimpAPIVersion.v1_3)
public class ListStaticSegmentMembersDelMethod extends HasListIdMethod<StaticSegmentMembersInfo> {

	@Field
	public Integer seg_id;

	@Field
	public List<String> batch;
}
