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
 package com.ecwid.mailchimp.method.v1_3.list;

import com.ecwid.mailchimp.MailChimpAPIVersion;
import com.ecwid.mailchimp.MailChimpMethod;

/**
 * See http://apidocs.mailchimp.com/api/1.3/lists.func.php
 *
 * @author Matt Farmer <matt@frmr.me>
 */
@MailChimpMethod.Method(name = "lists", version = MailChimpAPIVersion.v1_3)
public class ListsMethod extends MailChimpMethod<ListsResult> {
  @Field
  public ListsMethodFilters filters = null;

  @Field
  public Integer start = null;

  @Field
  public Integer limit = null;

  @Field
  public String sort_field = null;

  @Field
  public String sort_dir = null;
}
