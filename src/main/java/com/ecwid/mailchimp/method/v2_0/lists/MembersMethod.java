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

/**
 * Get all of the list members for a list that are of a particular status and potentially matching a segment.
 * This will cause locking, so don't run multiples at once.
 *
 * @author Vasily Karyaev <v.karyaev@gmail.com>
 * @author Erik van Paassen <erik@evpwebdesign.nl>
 */
@MailChimpMethod.Method(name = "lists/members", version = MailChimpAPIVersion.v2_0)
public class MembersMethod extends ListsRelatedMethod<MembersResult> {

    @Field
    public String status;

    @Field
    public Opts opts;


    /**
     * Options to apply to this query - all are optional:
     */
    public static class Opts extends MailChimpObject {

        @Field
        public Integer start;

        @Field
        public Integer limit;

        @Field
        public String sort_field;

        @Field
        public String sort_dir;
    }
}
