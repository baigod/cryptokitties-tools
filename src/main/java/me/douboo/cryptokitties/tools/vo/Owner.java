/**
  * Copyright 2017 bejson.com 
  */
package me.douboo.cryptokitties.tools.vo;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

/**
 * Auto-generated: 2017-12-07 20:48:12
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Owner implements Serializable {

	private static final long serialVersionUID = 1L;

	private String address;
	private String nickname;
	private String image;

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress() {
		return address;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getNickname() {
		return StringUtils.isEmpty(nickname) ? "" : nickname.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "*");
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getImage() {
		return image;
	}

}