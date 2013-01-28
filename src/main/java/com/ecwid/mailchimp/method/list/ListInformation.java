/*
 * Copyright 2012 Ecwid, Inc.
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
package com.ecwid.mailchimp.method.list;

import com.ecwid.mailchimp.MailChimpObject;

/**
 *
 * @author Matt Farmer <matt@frmr.me>
 */
public class ListInformation extends MailChimpObject {
	@Field
  public String id;

  @Field
  public int web_id;

  @Field
  public String name;

  @Field
  public String date_created;

  @Field
  public boolean email_type_option;

  @Field
  public boolean use_awesomebar;

  @Field
  public String default_from_name;

  @Field
  public String default_from_email;

  @Field
  public String default_subject;

  @Field
  public String default_language;

  @Field
  public double list_rating;

  @Field
  public String subscribe_url_short;

  @Field
  public String subscribe_url_long;

  @Field
  public String beamer_address;

  @Field
  public ListStats stats;
}
