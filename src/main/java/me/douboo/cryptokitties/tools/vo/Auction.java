/**
  * Copyright 2017 bejson.com 
  */
package me.douboo.cryptokitties.tools.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Auto-generated: 2017-12-07 20:48:12
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Auction implements Serializable {

	private static final long serialVersionUID = 1L;

	private BigDecimal start_price;
	private BigDecimal end_price;
	private long start_time;
	private long end_time;
	private BigDecimal current_price;
	private String duration;
	private String status;
	private String type;
	private int id;
	private Seller seller;
	private Kitty kitty;

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getDuration() {
		return duration;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setSeller(Seller seller) {
		this.seller = seller;
	}

	public Seller getSeller() {
		return seller;
	}

	public void setKitty(Kitty kitty) {
		this.kitty = kitty;
	}

	public Kitty getKitty() {
		return kitty;
	}

	public BigDecimal getStart_price() {
		return start_price;
	}

	public void setStart_price(BigDecimal start_price) {
		this.start_price = start_price;
	}

	public BigDecimal getEnd_price() {
		return end_price;
	}

	public void setEnd_price(BigDecimal end_price) {
		this.end_price = end_price;
	}

	public long getStart_time() {
		return start_time;
	}

	public void setStart_time(long start_time) {
		this.start_time = start_time;
	}

	public long getEnd_time() {
		return end_time;
	}

	public void setEnd_time(long end_time) {
		this.end_time = end_time;
	}

	public BigDecimal getCurrent_price() {
		return current_price;
	}

	public void setCurrent_price(BigDecimal current_price) {
		this.current_price = current_price;
	}

}