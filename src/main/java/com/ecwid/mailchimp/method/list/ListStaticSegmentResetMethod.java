package com.ecwid.mailchimp.method.list;

import com.ecwid.mailchimp.MailChimpMethod;

/**
 *
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
