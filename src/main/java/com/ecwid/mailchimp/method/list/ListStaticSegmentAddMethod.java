package com.ecwid.mailchimp.method.list;

import com.ecwid.mailchimp.MailChimpMethod;

/**
 * See http://apidocs.mailchimp.com/api/1.3/liststaticsegmentadd.func.php
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
