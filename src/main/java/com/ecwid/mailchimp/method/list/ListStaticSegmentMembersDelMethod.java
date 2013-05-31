package com.ecwid.mailchimp.method.list;

import com.ecwid.mailchimp.MailChimpMethod;

import java.util.List;

/**
 * See http://apidocs.mailchimp.com/api/1.3/liststaticsegmentmembersdel.func.php
 */
@MailChimpMethod.Name("listStaticSegmentMembersDel")
public class ListStaticSegmentMembersDelMethod extends HasListIdMethod<StaticSegmentMembersInfo>
{
	@Field
	public Integer seg_id;

	@Field
	public List<String> batch;


    /**
     * Get the class object representing method result type.
     */
    @Override
    public Class<StaticSegmentMembersInfo> getResultType()
    {
        return StaticSegmentMembersInfo.class;
    }
}
