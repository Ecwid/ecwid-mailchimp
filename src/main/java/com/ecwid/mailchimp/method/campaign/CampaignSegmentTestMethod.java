package com.ecwid.mailchimp.method.campaign;

import com.ecwid.mailchimp.MailChimpMethod;
import com.ecwid.mailchimp.MailChimpObject;
import com.ecwid.mailchimp.method.list.HasListIdMethod;

import java.util.Map;

/**
 * See:
 * <a href="http://apidocs.mailchimp.com/api/1.3/campaignsegmenttest.func.php">
 *      http://apidocs.mailchimp.com/api/1.3/campaignsegment.func.php
 * </a>
 *
 * @author Massimo Lusetti <mlusetti@gmail.com>
 *
 */
@MailChimpMethod.Name("campaignSegmentTest")
public class CampaignSegmentTestMethod extends MailChimpMethod<String>
{
	@Field
	public String list_id;

	@Field
    public MailChimpObject options;


    /**
     * Get the class object representing method result type.
     */
    @Override
    public Class<String> getResultType()
    {
        return String.class;
    }
}
