/**
  * Copyright 2017 bejson.com 
  */
package me.douboo.cryptokitties.tools.vo;

import java.io.Serializable;

/**
 * Auto-generated: 2017-12-07 20:48:12
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Status implements Serializable {

	private static final long serialVersionUID = 1L;

	private boolean is_ready;
	private boolean is_gestating;
	private long cooldown;
	private long cooldown_index;

	public boolean isIs_ready() {
		return is_ready;
	}

	public void setIs_ready(boolean is_ready) {
		this.is_ready = is_ready;
	}

	public boolean isIs_gestating() {
		return is_gestating;
	}

	public void setIs_gestating(boolean is_gestating) {
		this.is_gestating = is_gestating;
	}

	public long getCooldown() {
		return cooldown;
	}

	public void setCooldown(long cooldown) {
		this.cooldown = cooldown;
	}

	public long getCooldown_index() {
		return cooldown_index;
	}

	public void setCooldown_index(long cooldown_index) {
		this.cooldown_index = cooldown_index;
	}

}