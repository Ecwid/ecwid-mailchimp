/*
 * Copyright 2014 Ecwid, Inc.
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
package com.ecwid.mailchimp.method.v2_0.campaign;

import java.util.Date;
import java.util.List;

import com.ecwid.mailchimp.MailChimpObject;
import com.ecwid.mailchimp.method.v2_0.reports.SummaryMethodResult;

/**
 * containing a count of all matching campaigns, the specific ones for the current page, and any errors from the filters provided
 * 
 * @author Benjamin Warncke
 */
public class ListMethodResult extends MailChimpObject {
	
	@Field
    public Integer total;

    @Field
    public List<Data> data;

    @Field
    public List<Error> errors;

    /**
     * structs for each campaign being returned
     */
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
        public String content_edited_by;

        @Field
        public String title;

        @Field
        public String type;

        @Field
        public Date create_time;

        @Field
        public Date send_time;
        
        @Field
        public Date content_updated_time;

        @Field
        public String status;

        @Field
        public String from_name;

        @Field
        public String from_email;

        @Field
        public String subject;

        @Field
        public String to_name;

        @Field
        public String archive_url;
        
        @Field
        public String archive_url_long;
                
        @Field
        public Integer emails_sent;

        @Field
        public Boolean inline_css;

        @Field
        public String analytics;

        @Field
        public String analytics_tag;

        @Field
        public Boolean authenticate;

        @Field
        public Boolean ecomm360;

        @Field
        public Boolean auto_tweet;

        @Field
        public String auto_fb_post;

        @Field
        public Boolean auto_footer;

        @Field
        public Boolean timewarp;

        @Field
        public Date timewarp_schedule;
        
        @Field
        public Tracking tracking;

        @Field
        public String parent_id;

        @Field
        public Boolean is_child;

        @Field
        public String tests_sent;

        @Field
        public Integer tests_remain;

        @Field
        public String segment_text;

        @Field
        public SegmentOpts segment_opts;

        @Field
        public List<SavedSegment> saved_segment;

        @Field
        public List<TypeOpts> type_opts;

        @Field
        public Integer comments_total;

        @Field
        public Integer comments_unread;

        @Field
        public SummaryMethodResult summary;
    }

    /**
     * the various tracking options used
     */
    public static class Tracking extends MailChimpObject{

        @Field
        public Boolean html_clicks;

        @Field
        public Boolean text_clicks;

        @Field
        public Boolean opens;
    }

    /**
     * the various tracking options used
     */
    public static class SavedSegment extends MailChimpObject{

        @Field
        public Integer id;

        @Field
        public String type;
        
        @Field
        public String name;
    }

    /**
     * structs of any errors found while loading lists - usually just from providing invalid list ids
     */
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
