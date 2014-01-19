package com.ecwid.mailchimp.method.v1_3.campaign;

import com.ecwid.mailchimp.MailChimpObject;

public class EcommOrderItemInfo extends MailChimpObject {
	@Field
	public int line_num;
	
	@Field
	public int product_id;
	
	@Field
	public String sku;
	
	@Field
	public String product_name;
	
	@Field
	public int category_id;
	
	@Field
	public String category_name;
	
	@Field
	public double qty;
	
	@Field
	public double cost;
}
