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

import java.util.List;

import com.ecwid.mailchimp.MailChimpObject;
 
 /**
  * Various extra options based on the campaign type
  * 
  * See:
  * <a href="http://apidocs.mailchimp.com/api/2.0/campaigns/create.php">
  *      http://apidocs.mailchimp.com/api/2.0/campaigns/create.php
  * </a>
  * 
  * @author Benjamin Warncke
  */
 
public class TypeOpts extends MailChimpObject {
	@Field
	public Rss rss;
	 
	@Field
	public Absplit absplit;
	 
	@Field
	public Auto auto;
	
	/**
     * optional used for "daily" schedules only, an array of the ISO-8601 
     * weekday numbers to send on
     */
    public static class Days extends MailChimpObject {
    	@Field
        public Boolean one; // Monday;
    	
    	@Field
        public Boolean two; // Tuesday;
    	
    	@Field
        public Boolean three; // Wednesday;
    	
    	@Field
        public Boolean four; // Thursday;
    	
    	@Field
        public Boolean five; // Friday;
    	
    	@Field
        public Boolean six; // Saturday;
    	
    	@Field
        public Boolean seven; // Sunday;
    }
	
	/**
     * For RSS Campaigns this, struct should contain:
     */
    public static class Rss extends MailChimpObject {
        @Field
        public String url;

        @Field
        public String schedule;

        @Field
        public String schedule_hour;
        
        @Field
        public String schedule_weekday;
        
        @Field
        public String schedule_monthday;
        
        @Field
        public Days days;
    }
    
    /**
     * For A/B Split campaigns, this struct should contain:
     */
    public static class Absplit extends MailChimpObject {
    	@Field
    	public String split_test;
    	
    	@Field
    	public String pick_winner;
    	
    	@Field
    	public Integer wait_units;
    	
    	@Field
    	public Integer wait_time;
    	
    	@Field
    	public Integer split_size;
    	
    	@Field
    	public String from_name_a;
    	
    	@Field
    	public String from_name_b;
    	
    	@Field
    	public String from_email_a;
    	
    	@Field
    	public String from_email_b;
    	
    	@Field
    	public String subject_a;
    	
    	@Field
    	public String subject_b;
    }

    /**
     * For AutoResponder campaigns, this struct should contain:
     */
    public static class Auto extends MailChimpObject {
    	@Field
    	public String offset_units;
    	
    	@Field
    	public String offset_time;
    	
    	@Field
    	public String offset_dir;
    	
    	@Field
    	public String event;
    	
    	@Field
    	public String event_datemerge;
    	
    	@Field
    	public String campaign_id;
    	
    	@Field
    	public String campaign_url;
    	
    	@Field
    	public Integer schedule_hour;
    	
    	@Field
    	public Boolean use_import_time;
    	
    	@Field
    	public Days days;
    }
}
 