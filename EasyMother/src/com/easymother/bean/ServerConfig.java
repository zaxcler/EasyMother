package com.easymother.bean;

public class ServerConfig {
	
	private ConfigBZJ order_bzj;
	private ConfigDefaultDays order_defaul_days;
	private ConfigJZJ order_long_limit_days;
	private ConfigLongDays order_jzj;
	private ConfigOrderEvery order_every;
	
	public ConfigBZJ getOrder_bzj() {
		return order_bzj;
	}
	public void setOrder_bzj(ConfigBZJ order_bzj) {
		this.order_bzj = order_bzj;
	}
	public ConfigDefaultDays getOrder_defaul_days() {
		return order_defaul_days;
	}
	public void setOrder_defaul_days(ConfigDefaultDays order_defaul_days) {
		this.order_defaul_days = order_defaul_days;
	}
	public ConfigJZJ getOrder_long_limit_days() {
		return order_long_limit_days;
	}
	public void setOrder_long_limit_days(ConfigJZJ order_long_limit_days) {
		this.order_long_limit_days = order_long_limit_days;
	}
	public ConfigLongDays getOrder_jzj() {
		return order_jzj;
	}
	public void setOrder_jzj(ConfigLongDays order_jzj) {
		this.order_jzj = order_jzj;
	}
	public ConfigOrderEvery getOrder_every() {
		return order_every;
	}
	public void setOrder_every(ConfigOrderEvery order_every) {
		this.order_every = order_every;
	}

	
}
