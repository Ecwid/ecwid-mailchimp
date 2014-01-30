package com.ecwid.mailchimp.method.v1_3.campaign;

import com.ecwid.mailchimp.MailChimpObject;

public class EcommOrderItemInfo extends MailChimpObject {
	@Field
	public Integer line_num;
	
	@Field
	public Integer product_id;
	
	@Field
	public String sku;
	
	@Field
	public String product_name;
	
	@Field
	public Integer category_id;
	
	@Field
	public String category_name;
	
	@Field
	public Double qty;
	
	@Field
	public Double cost;
}
