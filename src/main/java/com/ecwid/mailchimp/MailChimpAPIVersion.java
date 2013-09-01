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
package com.ecwid.mailchimp;

/**
 * Specifies the API version being used.
 * 
 * @author Vasily Karyaev <v.karyaev@gmail.com>
 */
public enum MailChimpAPIVersion {
	v1_3,
	v2_0;

	@Override
	public String toString() {
		return name().substring(1).replace("_", ".");
	}
}
