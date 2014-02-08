package org.pec.picsy.entity;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.datastore.Key;

@Entity
public class PicsyPic {

	// Photo, Desc, By, CreatedOn
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;
	
	@Basic
	BlobKey photo;
	@Basic
	String desc;
	@Basic
	String by;
	@Basic
	Long createdOn;
	@Basic
	String servingUrl;
	
	public PicsyPic(BlobKey photo, String desc, String by) {
		this.photo = photo;
		this.desc = desc;
		this.by = by;
		this.createdOn = System.currentTimeMillis();
	}

	public BlobKey getPhoto() {
		return photo;
	}

	public void setPhoto(BlobKey photo) {
		this.photo = photo;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getBy() {
		return by;
	}

	public void setBy(String by) {
		this.by = by;
	}

	public Long getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Long createdOn) {
		this.createdOn = createdOn;
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public String getServingUrl() {
		return servingUrl;
	}

	public void setServingUrl(String servingUrl) {
		this.servingUrl = servingUrl;
	}
	
	
}
