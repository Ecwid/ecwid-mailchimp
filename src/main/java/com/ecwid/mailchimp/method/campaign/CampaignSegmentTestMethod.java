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
@MailChimpMethod.Name("campaignSegmentTest")
public class CampaignSegmentTestMethod extends CampaingRelatedMethod<String>
{

    @Field
    public String match;
    @Field
    public Map<String, Object> options;
    @Field
    public Map<String, String> content;


    /**
     * Get the class object representing method result type.
     */
    @Override
    public Class<String> getResultType()
    {
        return String.class;
    }
}
