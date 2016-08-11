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
 * If you wish to do Segmentation with this campaign this array should contain: 
 * see campaigns/segment-test(). It's suggested that you test your options 
 * against campaigns/segment-test().
 * 
 * See:
 * <a href="http://apidocs.mailchimp.com/api/2.0/campaigns/segment-test.php">
 *      http://apidocs.mailchimp.com/api/2.0/campaigns/segment-test.php
 * </a>
 * 
 * @author Benjamin Warncke
 */

public class SegmentOpts extends MailChimpObject {
	
	@Field
    public String match;
	
	@Field
	public List<Condition> conditions;
	
	/**
     * the segment used for the campaign - can be passed to campaigns/segment-test or campaigns/create()
     */
    public static class Condition extends MailChimpObject {
    	
    	@Field
        public String field;

        @Field
        public String op;

        @Field
        public String value;
    }
}