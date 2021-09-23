package com.hmlyinfo.app.soutu.scenic.domain;

import com.hmlyinfo.app.soutu.plan.domain.type.TripType;
import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

public class NoteScenic extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * 游记id
	 */
	private long noteId;

	/**
	 * 游记天编号
	 */
	private long notedayId;

	/**
	 * 景点描述
	 */
	private String scenicDesc;

	/**
	 * 景点id
	 */
	private long scenicId;

	private TripType scenicType;

	@JsonProperty
	public int getScenicType() {
		return scenicType.value();
	}

	public void setScenicType(int scenicType) {
		this.scenicType = TripType.valueOf(scenicType);
	}

	private List<NoteImage> imageList;
	private String scenicName;

	@JsonProperty
	public List<NoteImage> getImageList() {
		return imageList;
	}

	public void setImageList(List<NoteImage> imageList) {
		this.imageList = imageList;
	}

	public void setNoteId(long noteId) {
		this.noteId = noteId;
	}

	@JsonProperty
	public long getNoteId() {
		return noteId;
	}

	public void setNotedayId(long notedayId) {
		this.notedayId = notedayId;
	}

	@JsonProperty
	public long getNotedayId() {
		return notedayId;
	}

	public void setScenicDesc(String scenicDesc) {
		this.scenicDesc = scenicDesc;
	}

	@JsonProperty
	public String getScenicDesc() {
		return scenicDesc;
	}

	@JsonProperty
	public long getScenicId() {
		return scenicId;
	}

	public void setScenicId(long scenicId) {
		this.scenicId = scenicId;
	}

	@JsonProperty
	public String getScenicName() {
		return scenicName;
	}

	public void setScenicName(String scenicName) {
		this.scenicName = scenicName;
	}
}
