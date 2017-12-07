/**
  * Copyright 2017 bejson.com 
  */
package me.douboo.cryptokitties.tools.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * Auto-generated: 2017-12-07 20:48:12
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Kitty implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	private String image_url;
	private int generation;
	private Date created_at;
	private String color;
	private boolean is_fancy;
	private boolean is_exclusive;
	private String fancy_type;
	private int id;
	private Owner owner;
	private Status status;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	public String getImage_url() {
		return image_url;
	}

	public void setGeneration(int generation) {
		this.generation = generation;
	}

	public int getGeneration() {
		return generation;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getColor() {
		return color;
	}

	public void setIs_fancy(boolean is_fancy) {
		this.is_fancy = is_fancy;
	}

	public boolean getIs_fancy() {
		return is_fancy;
	}

	public void setIs_exclusive(boolean is_exclusive) {
		this.is_exclusive = is_exclusive;
	}

	public boolean getIs_exclusive() {
		return is_exclusive;
	}

	public void setFancy_type(String fancy_type) {
		this.fancy_type = fancy_type;
	}

	public String getFancy_type() {
		return fancy_type;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	public Owner getOwner() {
		return owner;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Status getStatus() {
		return status;
	}

}