package com.ecwid.mailchimp.method.v1_3.campaign;

import com.ecwid.mailchimp.MailChimpAPIVersion;
import com.ecwid.mailchimp.MailChimpMethod;


/**
 * See:
 * <a href="http://apidocs.mailchimp.com/api/1.3/campaignsendnow.func.php">
 *      http://apidocs.mailchimp.com/api/1.3/campaignsendnow.func.php
 * </a>
 *
 * @author Massimo Lusetti <mlusetti@gmail.com>
 *
 */
@MailChimpMethod.Method(name = "campaignSendNow", version = MailChimpAPIVersion.v1_3)
public class CampaignSendNowMethod extends CampaingRelatedMethod<Boolean> { }
