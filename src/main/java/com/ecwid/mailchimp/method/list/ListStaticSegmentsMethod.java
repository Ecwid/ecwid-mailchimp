package com.ecwid.mailchimp.method.list;

import com.ecwid.mailchimp.MailChimpMethod;

/**
 * See http://apidocs.mailchimp.com/api/1.3/liststaticsegments.func.php
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
