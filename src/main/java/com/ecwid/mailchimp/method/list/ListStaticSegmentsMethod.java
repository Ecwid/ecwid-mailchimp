package com.ecwid.mailchimp.method.list;

import com.ecwid.mailchimp.MailChimpMethod;

/**
 *
 */
@MailChimpMethod.Name("listStaticSegments")
public class ListStaticSegmentsMethod extends HasListIdMethod<ListStaticSegmentsResult>
{
    /**
     * Get the class object representing method result type.
     */
    @Override
    public Class<ListStaticSegmentsResult> getResultType()
    {
        return ListStaticSegmentsResult.class;
    }
}
