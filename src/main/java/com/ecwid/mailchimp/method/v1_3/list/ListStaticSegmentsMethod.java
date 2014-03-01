package com.ecwid.mailchimp.method.v1_3.list;

import com.ecwid.mailchimp.MailChimpAPIVersion;
import com.ecwid.mailchimp.MailChimpMethod;

/**
 * See http://apidocs.mailchimp.com/api/1.3/liststaticsegments.func.php
 */
@MailChimpMethod.Method(name = "listStaticSegments", version = MailChimpAPIVersion.v1_3)
public class ListStaticSegmentsMethod extends HasListIdMethod<ListStaticSegmentsResult> { }
