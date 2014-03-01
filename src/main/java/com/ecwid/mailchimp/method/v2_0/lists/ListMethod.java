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

import com.ecwid.mailchimp.MailChimpAPIVersion;
import com.ecwid.mailchimp.MailChimpMethod;
import com.ecwid.mailchimp.MailChimpObject;

import java.util.Date;

/**
 * Retrieve all of the lists defined for your user account
 */
@MailChimpMethod.Method(name = "lists/list", version = MailChimpAPIVersion.v2_0)
public class ListMethod extends MailChimpMethod<ListMethodResult> {

    @Field
    public Filters filters;

    @Field
    public Integer start;

    @Field
    public Integer limit;

    @Field
    public String sort_field;

    @Field
    public String sort_dir;

    /**
     * Filters to apply to this query - all are optional:
     */
    public static class Filters extends MailChimpObject {

        @Field
        public String list_id;

        @Field
        public String list_name;

        @Field
        public String from_name;

        @Field
        public String from_email;

        @Field
        public String from_subject;

        @Field
        public Date created_before; //24 hour format in GMT, eg "2013-12-30 20:30:00"

        @Field
        public Date created_after; //24 hour format in GMT, eg "2013-12-30 20:30:00"

        @Field
        public Boolean exact;

    }
}
