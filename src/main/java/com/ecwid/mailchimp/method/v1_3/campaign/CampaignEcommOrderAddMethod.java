package com.ecwid.mailchimp.method.v1_3.campaign;

import com.ecwid.mailchimp.MailChimpAPIVersion;
import com.ecwid.mailchimp.MailChimpMethod;
import com.ecwid.mailchimp.MailChimpObject;


/**
 * See:
 * <a href="http://apidocs.mailchimp.com/api/1.3/campaignecommorderadd.func.php">
 *      http://apidocs.mailchimp.com/api/1.3/campaignecommorderadd.func.php
 * </a>
 */
@MailChimpMethod.Method(name = "campaignEcommOrderAdd", version = MailChimpAPIVersion.v1_3)
public class CampaignEcommOrderAddMethod extends MailChimpMethod<Boolean>
{
	@Field
	public EcommOrderInfo order;
}
