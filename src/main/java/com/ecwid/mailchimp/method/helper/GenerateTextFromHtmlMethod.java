package com.ecwid.mailchimp.method.helper;

import com.ecwid.mailchimp.MailChimpMethod;
import com.ecwid.mailchimp.method.campaign.CampaingRelatedMethod;

import java.util.Map;

/**
 * See:
 * <a href="http://apidocs.mailchimp.com/api/1.3/generatetext.func.php">
 *      http://apidocs.mailchimp.com/api/1.3/generatetext.func.php
 * </a>
 * this class implementens just the <code>html</code> type
 *
 * @author Massimo Lusetti <mlusetti@gmail.com>
 *
 */
@MailChimpMethod.Name("generateText")
public class GenerateTextFromHtmlMethod extends MailChimpMethod<String>
{
    @Field
    public String type;
    @Field
    public String content;


    /**
     * Get the class object representing method result type.
     */
    @Override
    public Class<String> getResultType()
    {
        return String.class;
    }
}
