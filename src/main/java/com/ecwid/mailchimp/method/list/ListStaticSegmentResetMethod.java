package com.ecwid.mailchimp.method.list;

import com.ecwid.mailchimp.MailChimpMethod;

/**
 *
 */
@MailChimpMethod.Name("listStaticSegmentReset")
public class ListStaticSegmentResetMethod extends HasListIdMethod<Boolean>
{
	@Field
	public String seg_id;


    /**
     * Get the class object representing method result type.
     */
    @Override
    public Class<Boolean> getResultType()
    {
        return Boolean.class;
    }
}
