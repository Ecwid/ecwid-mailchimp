package com.ecwid.mailchimp.method.list;

import com.ecwid.mailchimp.MailChimpMethod;

/**
 *
 */
@MailChimpMethod.Name("listStaticSegmentAdd")
public class ListStaticSegmentAddMethod extends HasListIdMethod<Integer>
{
	@Field
	public String name;


    /**
     * Get the class object representing method result type.
     */
    @Override
    public Class<Integer> getResultType()
    {
        return Integer.class;
    }
}
