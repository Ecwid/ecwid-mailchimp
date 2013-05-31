package com.ecwid.mailchimp.method.list;

import com.ecwid.mailchimp.MailChimpMethod;

/**
 * See http://apidocs.mailchimp.com/api/1.3/liststaticsegmentreset.func.php
 */
@MailChimpMethod.Name("listStaticSegmentReset")
public class ListStaticSegmentResetMethod extends HasListIdMethod<Boolean>
{
	@Field
	public Integer seg_id;

    /**
     * Get the class object representing method result type.
     */
    @Override
    public Class<Boolean> getResultType()
    {
        return Boolean.class;
    }
}
