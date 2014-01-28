package com.ecwid.mailchimp.method.v1_3.campaign;

import java.util.Date;
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
	public Double total;
	
	@Field
	public Date order_date;
	
	@Field
	public Double shipping;
	
	@Field
	public Double tax;
	
	@Field
	public String store_id;
	
	@Field
	public String store_name;
	
	@Field
	public List<EcommOrderItemInfo> items;
	
}
