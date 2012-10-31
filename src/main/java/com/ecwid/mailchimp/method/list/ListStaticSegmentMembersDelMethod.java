package com.ecwid.mailchimp.method.list;

import com.ecwid.mailchimp.MailChimpMethod;

import java.util.List;

/**
 *
 */
@MailChimpMethod.Name("listStaticSegmentMembersDel")
public class ListStaticSegmentMembersDelMethod extends HasListIdMethod<ListStaticSegmentMembersResult>
{
	@Field
	public String seg_id;

	@Field
	public List<String> batch;


    /**
     * Get the class object representing method result type.
     */
    @Override
    public Class<ListStaticSegmentMembersResult> getResultType()
    {
        return ListStaticSegmentMembersResult.class;
    }
}
