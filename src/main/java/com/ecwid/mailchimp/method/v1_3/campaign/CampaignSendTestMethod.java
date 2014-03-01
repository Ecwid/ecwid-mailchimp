package com.ecwid.mailchimp.method.v1_3.campaign;

import com.ecwid.mailchimp.MailChimpAPIVersion;
import com.ecwid.mailchimp.MailChimpMethod;
import com.ecwid.mailchimp.MailChimpObject;

import java.util.List;

/**
 * See:
 * <a href="http://apidocs.mailchimp.com/api/1.3/campaignsendtest.func.php">
 *      http://apidocs.mailchimp.com/api/1.3/campaignsendtest.func.php
 * </a>
 *
 * @author Massimo Lusetti <mlusetti@gmail.com>
 *
 */
@MailChimpMethod.Method(name = "campaignSendTest", version = MailChimpAPIVersion.v1_3)
public class CampaignSendTestMethod extends CampaingRelatedMethod<Boolean>
{
    @MailChimpObject.Field
    public List<String> test_emails;

    @MailChimpObject.Field
    public String send_type;
}
