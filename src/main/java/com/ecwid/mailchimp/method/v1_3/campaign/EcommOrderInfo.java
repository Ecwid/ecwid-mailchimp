package com.ecwid.mailchimp.method.v1_3.campaign;

import java.util.List;

import com.ecwid.mailchimp.MailChimpObject;


public class EcommOrderInfo extends MailChimpObject {
	@Field
	public String id;
	
	@Field
	public String campaign_id;
	
	@Field
	public String email_id;
	
	@Field
	public double total;
	
	@Field
	public String order_date;
	
	@Field
	public double shipping;
	
	@Field
	public double tax;
	
	@Field
	public String store_id;
	
	@Field
	public String store_name;
	
	@Field
	public List<EcommOrderItemInfo> items;
	
}
