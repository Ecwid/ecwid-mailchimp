package com.ecwid.mailchimp.method.v1_3.list;

import com.ecwid.mailchimp.MailChimpAPIVersion;
import com.ecwid.mailchimp.MailChimpMethod;

/**
 * See http://apidocs.mailchimp.com/api/1.3/liststaticsegmentreset.func.php
 */
@MailChimpMethod.Method(name = "listStaticSegmentReset", version = MailChimpAPIVersion.v1_3)
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
