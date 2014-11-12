package com.ecwid.mailchimp.method.v2_0.campaigns;

import com.ecwid.mailchimp.MailChimpAPIVersion;
import com.ecwid.mailchimp.MailChimpMethod;
import com.ecwid.mailchimp.MailChimpObject;

import java.util.Date;

@MailChimpMethod.Method(name = "campaigns/list", version = MailChimpAPIVersion.v2_0)
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

	public static class Filters extends MailChimpObject {

		@Field
		public String campaign_id;

		@Field
		public String parent_id;

		@Field
		public String list_id;

		@Field
		public Integer folder_id;

		@Field
		public Integer template_id;

		@Field
		public String status;

		@Field
		public String type;

		@Field
		public String from_name;

		@Field
		public String from_email;

		@Field
		public String title;

		@Field
		public String subject;

		@Field
		public Date sendtime_start; //24 hour format in GMT, eg "2013-12-30 20:30:00"

		@Field
		public Date sendtime_end; //24 hour format in GMT, eg "2013-12-30 20:30:00"

		@Field
		public Boolean uses_segment;

		@Field
		public Boolean exact;
	}
}
