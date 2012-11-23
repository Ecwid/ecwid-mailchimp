package com.ecwid.mailchimp.method.campaign;

import com.ecwid.mailchimp.MailChimpMethod;

import java.util.Map;

/**
 * See:
 * <a href="http://apidocs.mailchimp.com/api/1.3/campaigncreate.func.php">
 *      http://apidocs.mailchimp.com/api/1.3/campaigncreate.func.php
 * </a>
 *
 * @author Massimo Lusetti <mlusetti@gmail.com>
 *
 */
@MailChimpMethod.Name("campaignSendNow")
public class CampaignSendNowMethod extends CampaingRelatedMethod<Boolean>
{
    /**
     * Get the class object representing method result type.
     */
    @Override
    public Class<Boolean> getResultType()
    {
        return Boolean.class;
    }
}
