/**
  * Copyright 2017 bejson.com 
  */
package me.douboo.cryptokitties.tools.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * Auto-generated: 2017-12-07 20:53:50
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Kitty implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private String name;
	private int generation;
	private Date created_at;
	private String image_url;
	private String color;
	private String bio;
	private boolean is_fancy;
	private boolean is_exclusive;
	private String fancy_type;
	private Status status;
	private Auction auction;
	private Owner owner;
	private Matron matron;
	private Sire sire;
	private List<Children> children;
	private List<Cattributes> cattributes;

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return StringUtils.isEmpty(name) ? "" : name.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "*");
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

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	public String getImage_url() {
		return image_url;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getColor() {
		return color;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getBio() {
		return bio;
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

	public void setStatus(Status status) {
		this.status = status;
	}

	public Status getStatus() {
		return status;
	}

	public void setAuction(Auction auction) {
		this.auction = auction;
	}

	public Auction getAuction() {
		return auction;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	public Owner getOwner() {
		return owner;
	}

	public void setMatron(Matron matron) {
		this.matron = matron;
	}

	public Matron getMatron() {
		return matron;
	}

	public void setSire(Sire sire) {
		this.sire = sire;
	}

	public Sire getSire() {
		return sire;
	}

	public void setChildren(List<Children> children) {
		this.children = children;
	}

	public List<Children> getChildren() {
		return children;
	}

	public void setCattributes(List<Cattributes> cattributes) {
		this.cattributes = cattributes;
	}

	public List<Cattributes> getCattributes() {
		return cattributes;
	}

}