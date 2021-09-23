package com.hmlyinfo.app.soutu.signet.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

public class SignetBook extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	/**
	 *
	 */
	private long userId;

	/**
	 *
	 */
	private String title;

	/**
	 *
	 */
	private String bookType;

	/**
	 *
	 */
	private String intro;

	/**
	 *
	 */
	private String image;

	/**
	 *
	 */
	private String music;

	/**
	 * 足迹
	 */
	private String footPrints;


	public void setUserId(long userId) {
		this.userId = userId;
	}

	@JsonProperty
	public long getUserId() {
		return userId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@JsonProperty
	public String getTitle() {
		return title;
	}

	public void setBookType(String bookType) {
		this.bookType = bookType;
	}

	@JsonProperty
	public String getBookType() {
		return bookType;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	@JsonProperty
	public String getIntro() {
		return intro;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@JsonProperty
	public String getImage() {
		return image;
	}

	public void setMusic(String music) {
		this.music = music;
	}

	@JsonProperty
	public String getMusic() {
		return music;
	}

	@JsonProperty
	public String getFootPrints() {
		return footPrints;
	}

	public void setFootPrints(String footPrints) {
		this.footPrints = footPrints;
	}
}
