package com.ecwid.mailchimp.method.v2_0.campaigns;

import com.ecwid.mailchimp.MailChimpObject;

import java.util.Date;
import java.util.List;

/**
 * Result of the campaigns/list operation including valid data and any errors
 */
public class ListMethodResult extends MailChimpObject {

    @Field
    public Integer total;

    @Field
    public List<Data> data;

    @Field
    public List<Error> errors;

    public static class Data extends MailChimpObject {

        @Field
        public String id;

        @Field
        public Integer web_id;

        @Field
        public String list_id;

        @Field
        public Integer folder_id;

        @Field
        public Integer template_id;

        @Field
        public String content_type;

        @Field
        public String title;

        @Field
        public String type;

        @Field
        public Date create_time;

        @Field
        public Date send_time;

        @Field
        public Integer emails_sent;

        @Field
        public String status;

        @Field
        public String from_name;

        @Field
        public String from_email;

        @Field
        public String subject;

        // TODO: there are more fields
    }

    public static class Error extends MailChimpObject {

        @Field
        public String filter;

        @Field
        public String value;

        @Field
        public Integer code;

        @Field
        public String error;
    }

}
