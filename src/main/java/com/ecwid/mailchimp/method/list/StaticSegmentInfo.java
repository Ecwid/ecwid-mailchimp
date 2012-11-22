package com.ecwid.mailchimp.method.list;

import com.ecwid.mailchimp.MailChimpObject;

import java.util.Date;

/**
 *
 */
public class StaticSegmentInfo extends MailChimpObject
{
    @Field
    public Integer id;

    @Field
    public String name;

    @Field
    public Integer member_count;

    @Field
    public Date created_date;

    @Field
    public Date lasat_update;

    @Field
    public Date last_reset;
}
