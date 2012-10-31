package com.ecwid.mailchimp.method.list;

import com.ecwid.mailchimp.MailChimpObject;

import java.util.Date;
import java.util.List;

/**
 *
 */
public class StaticSegmentMembersInfo extends MailChimpObject
{
    @Field
    public Integer suc;

    @Field
    public List<StaticSegmentMembersAddErrors> errors;
}
