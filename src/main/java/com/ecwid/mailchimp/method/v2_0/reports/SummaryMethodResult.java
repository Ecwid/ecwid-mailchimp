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
package com.ecwid.mailchimp.method.v2_0.reports;

import java.util.Date;
import java.util.List;

import com.ecwid.mailchimp.MailChimpObject;
import com.ecwid.mailchimp.method.v2_0.lists.ListMethodResult.Stats;

/**
 * result of the reports/summary operation including valid data and any errors
 * 
 * @author Benjamin Warncke
 */
public class SummaryMethodResult extends MailChimpObject {
	
	@Field
    public Integer syntax_errors;

    @Field
    public Integer hard_bounces;

    @Field
    public Integer soft_bounces;
    
    @Field
    public Integer unsubscribes;

    @Field
    public Integer abuse_reports;

    @Field
    public Integer forwards;
    
    @Field
    public Integer forwards_opens;

    @Field
    public Integer opens;

    @Field
    public Date last_open; // TODO change to string?
    
    @Field
    public Integer unique_opens;

    @Field
    public Integer clicks;

    @Field
    public Integer unique_clicks;
    
    @Field
    public Integer users_who_clicked;
    
    @Field
    public Date last_click; // TODO change to string?

    @Field
    public Integer emails_sent;
    
    @Field
    public Integer unique_likes;

    @Field
    public Integer recipient_likes;

    @Field
    public Integer facebook_likes;
    
    @Field
    public Industry industry;

    @Field
    public List<Absplit> absplit;

    @Field
    public List<Timewarp> timewarp;
    
    @Field
    public List<Timeseries> timeseries;
    
    /**
     * Various rates/percentages for the account's selected industry - empty otherwise. These will vary across calls, do not use them for anything important.
     */
    public static class Industry extends MailChimpObject {
    	
    	@Field
        public String type;
    	
    	@Field
        public Float open_rate;
    	
    	@Field
        public Float click_rate;
    	
    	@Field
        public Float bounce_rate;
    	
    	@Field
        public Float unopen_rate;
    	
    	@Field
        public Float unsub_rate;
    	
    	@Field
        public Float abuse_rate;
    }
    
    /**
     * If this was an absplit campaign, stats for the A and B groups will be returned - otherwise this is empty
     */
    public static class Absplit extends MailChimpObject {
    	
    	@Field
        public Integer bounces_a;
    	
    	@Field
        public Integer bounces_b;
    	
    	@Field
        public Integer forwards_a;
    	
    	@Field
        public Integer forwards_b;
    	
    	@Field
        public Integer abuse_reports_a;
    	
    	@Field
        public Integer abuse_reports_b;
    	
    	@Field
        public Integer unsubs_a;
    	
    	@Field
        public Integer unsubs_b;
    	
    	@Field
        public Integer recipients_click_a;
    	
    	@Field
        public Integer recipients_click_b;
    	
    	@Field
        public Integer forwards_opens_a;
    	
    	@Field
        public Integer forwards_opens_b;
    	
    	@Field
        public Integer opens_a;
    	
    	@Field
        public Integer opens_b;
    	
    	@Field
        public Date last_open_a;
    	
    	@Field
        public Date last_open_b;
    	
    	@Field
        public Integer unique_opens_a;
    	
    	@Field
        public Integer unique_opens_b;
    }
    
    /**
     * If this campaign was a Timewarp campaign, an array of structs from each timezone stats exist for. Each will contain:
     */
    public static class Timeseries extends MailChimpObject {
    	
    	@Field
        public Integer opens;
    	
    	@Field
        public Date last_open;
    	
    	@Field
        public Integer unique_opens;
    	
    	@Field
        public Integer clicks;
    	
    	@Field
        public Date last_click;
    	
    	@Field
        public Integer unique_clicks;
    	
    	@Field
        public Integer bounces;
    	
    	@Field
        public Integer total;
    	
    	@Field
        public Integer sent;
    }
    
    /**
     * structs for the first 24 hours of the campaign, per-hour stats:
     */
    public static class Timewarp extends MailChimpObject {
    	
    	@Field
        public String timestamp;
    	
    	@Field
        public Integer emails_sent;
    	
    	@Field
        public Integer unique_opens;
    	
    	@Field
        public Date recipients_click;
    }
}
