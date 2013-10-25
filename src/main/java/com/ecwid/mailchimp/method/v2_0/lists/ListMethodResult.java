/*
 * Copyright 2013 Ecwid, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ecwid.mailchimp.method.v2_0.lists;

import com.ecwid.mailchimp.MailChimpObject;

import java.util.Date;
import java.util.List;

/**
 * result of the lists/list operation including valid data and any errors
 */
public class ListMethodResult extends MailChimpObject {

    @Field
    public Integer total;

    @Field
    public List<Data> data;

    @Field
    public List<Error> errors;

    /**
     * structs for the lists which matched the provided filters, including the following
     */
    public static class Data extends MailChimpObject {
        @Field
        public String id;

        @Field
        public Integer web_id;

        @Field
        public String name;

        @Field
        public Date date_created;

        @Field
        public Boolean email_type_option;

        @Field
        public Boolean use_awesomebar;

        @Field
        public String default_from_name;

        @Field
        public String default_from_email;

        @Field
        public String default_subject;

        @Field
        public String default_language;

        @Field
        public Double list_rating;

        @Field
        public String subscribe_url_short;

        @Field
        public String subscribe_url_long;

        @Field
        public String beamer_address;

        @Field
        public String visibility;

        @Field
        public Stats stats;
    }

    /**
     * various stats and counts for the list - many of these are cached for at least 5 minutes
     */
    public static class Stats extends MailChimpObject{

        @Field
        public Double member_count;

        @Field
        public Double unsubscribe_count;

        @Field
        public Double cleaned_count;

        @Field
        public Double member_count_since_send;

        @Field
        public Double unsubscribe_count_since_send;

        @Field
        public Double cleaned_count_since_send;

        @Field
        public Double campaign_count;

        @Field
        public Double grouping_count;

        @Field
        public Double group_count;

        @Field
        public Double merge_var_count;

        @Field
        public Double avg_sub_rate;

        @Field
        public Double avg_unsub_rate;

        @Field
        public Double target_sub_rate;

        @Field
        public Double open_rate;

        @Field
        public Double click_rate;

    }

    public static class Error extends MailChimpObject {
        @Field
        public String param;

        @Field
        public Integer code;

        @Field
        public String error;
    }
}
