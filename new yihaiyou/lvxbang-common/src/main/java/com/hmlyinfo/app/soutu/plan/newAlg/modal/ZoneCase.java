package com.hmlyinfo.app.soutu.plan.newAlg.modal;

public class ZoneCase {

	public int id;    //片区的索引id
	public int spotNum;   //片区中的点point数目
	public float sumTime;        //该区的总浏览时间

	public ZoneCase(int zoneId) {
		this.id = zoneId;
		this.spotNum = 0;
		this.sumTime = 0;
	}

}
