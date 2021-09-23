package com.hmlyinfo.app.soutu.point.domain;

import com.hmlyinfo.base.persistent.BaseEntity;

import java.util.Date;

/**
 * Created by guoshijie on 2014/7/10.
 */
public class PointHistory extends BaseEntity {

	private long userId;
	private PointType pointType;
	private int interval;
	private int count;
	private int point;
	private Date modifyTime;

	public enum PointType {

		PlanAdd("planAdd", 1, 5, 10), PlanQuoted("planQuoted", 2, 5, 5), PlanCommented("planCommented", 3, 5, 5),
		PlanCollected("planCollected", 4, 5, 5), PlanShared("planShared", 5, 10, 1),
		TravelsPublish("travelsPublish", 11, 5, 10), TravelsCommented("travelsComments", 12, 5, 5),
		TravelsCollected("travelsCollected", 13, 5, 5), TravelsShared("travelsShared", 14, 10, 1),
		UserCompleteInfo("userCompleteInfo", 21, -1, 10), UserUploadHead("userUploadHead", 22, -1, 10), UserLogin("userLogin", 23, 1, 1),
		UserInvite("userInvite", 24, 0, 5), UserRecommendDelicacy("userRecommendDelicacy", 25, 5, 5),
		CommentScenic("commentScenic", 31, 5, 10), CommentHotel("commentHotel", 32, 5, 10), CommentRestaurant("commentRestaurant", 33, 5, 10),
		CommentTravels("commentTravels", 34, 5, 10), CommentPlan("commentPlan", 35, 5, 10),
		SharePlan("sharePlan", 41, 10, 1), ShareTravels("shareTravels", 42, 10, 1),
		ShareDelicacy("shareDelicacy", 43, 10, 1), ShareHotel("shareHotel", 44, 10, 1);

		private String name;
		private int value;
		private int interval;
		private int point;

		private PointType(String name, int value, int interval, int point) {
			this.name = name;
			this.value = value;
			this.interval = interval;
			this.point = point;
		}

		public String toString() {
			return String.valueOf(value);
		}

		public int getValue() {
			return value;
		}

		public String getName() {
			return name;
		}

		public int getInterval() {
			return interval;
		}

		public int getPoint() {
			return point;
		}

		public static PointType nameOf(int value) {
			for (PointType pointType : PointType.values()) {
				if (value == pointType.value) {
					return pointType;
				}
			}
			return null;
		}

	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public PointType getPointType() {
		return pointType;
	}

	public void setPointType(int type) {
		for (PointType pointType : PointType.values()) {
			if (type == pointType.value) {
				this.pointType = pointType;
			}
		}

	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}
}
