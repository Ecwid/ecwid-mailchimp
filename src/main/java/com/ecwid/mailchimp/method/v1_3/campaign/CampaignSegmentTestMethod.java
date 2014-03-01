package com.ecwid.mailchimp.method.v1_3.campaign;

import com.ecwid.mailchimp.MailChimpAPIVersion;
import com.ecwid.mailchimp.MailChimpMethod;
import com.ecwid.mailchimp.MailChimpObject;

/**
 * See:
 * <a href="http://apidocs.mailchimp.com/api/1.3/campaignsegmenttest.func.php">
 *      http://apidocs.mailchimp.com/api/1.3/campaignsegmenttest.func.php
 * </a>
 *
 * @author Massimo Lusetti <mlusetti@gmail.com>
 *
 */
@MailChimpMethod.Method(name = "campaignSegmentTest", version = MailChimpAPIVersion.v1_3)
public class CampaignSegmentTestMethod extends MailChimpMethod<String>
{
	@Field
	public String list_id;

	@Field
    public MailChimpObject options;
}
