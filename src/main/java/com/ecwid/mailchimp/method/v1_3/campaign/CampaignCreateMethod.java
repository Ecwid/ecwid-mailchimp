package com.ecwid.mailchimp.method.v1_3.campaign;

import com.ecwid.mailchimp.MailChimpAPIVersion;
import com.ecwid.mailchimp.MailChimpMethod;
import com.ecwid.mailchimp.MailChimpObject;


/**
 * See:
 * <a href="http://apidocs.mailchimp.com/api/1.3/campaigncreate.func.php">
 *      http://apidocs.mailchimp.com/api/1.3/campaigncreate.func.php
 * </a>
 *
 * @author Massimo Lusetti <mlusetti@gmail.com>
 *
 */
@MailChimpMethod.Method(name = "campaignCreate", version = MailChimpAPIVersion.v1_3)
public class CampaignCreateMethod extends CampaingRelatedMethod<String> {
	@Field
	public CampaignType type;

	@Field
	public MailChimpObject options;

	@Field
	public MailChimpObject segment_opts;

	@Field
	public MailChimpObject content;
}
