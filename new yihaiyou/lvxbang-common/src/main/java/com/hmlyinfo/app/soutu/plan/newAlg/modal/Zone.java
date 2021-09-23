package com.hmlyinfo.app.soutu.plan.newAlg.modal;

public class Zone {
	public int id;    //片区的索引id
	public int spotNum;   //片区中的点point数目
	public long pointId[]; //存放该片区内的 点point对象的id
	public float dist[][];//该片区内 ，点和点之间的距离
	public float distTime[][];//该片区内，点和点之间所需要花费的时间
	public TraceLine traceline;  //该区的浏览顺序
	public float sumTime;        //该区的总浏览时间
	public float minlen;        //该区的总浏览路径长度
	public int spotMax = 10;//预先设定该片区克旅游景点的最大数

	public Zone(int zoneId) {
		this.id = zoneId;
		this.spotNum = 0;
		this.minlen = 0;
		this.sumTime = 0;
		traceline = new TraceLine();
		pointId = new long[spotMax];
		dist = new float[spotMax][spotMax];
		distTime = new float[spotMax][spotMax];
	}

	public Zone() {
		this.spotNum = 0;
		this.minlen = 0;
		this.sumTime = 0;
		pointId = new long[spotMax];
		dist = new float[spotMax][spotMax];
		distTime = new float[spotMax][spotMax];
	}


	public Zone(Zone copyZone) {
		this.id = copyZone.id;
		this.spotNum = copyZone.spotNum;
		for (int i = 0; i < pointId.length; i++) {
			this.pointId[i] = copyZone.pointId[i];
		}
		for (int i = 0; i < dist.length; i++) {
			for (int j = 0; j < dist.length; j++) {
				this.dist[i][j] = copyZone.dist[i][j];
				this.distTime[i][j] = copyZone.distTime[i][j];
			}
		}
		this.traceline = new TraceLine();
		if (copyZone.traceline != null) {
			traceline.start = copyZone.traceline.start;
			traceline.end = copyZone.traceline.end;
			for (int i = 0; i < traceline.trace.length; i++) {
				this.traceline.trace[i] = copyZone.traceline.trace[i];
			}
		}

		this.sumTime = copyZone.sumTime;
		this.minlen = copyZone.minlen;

	}
}


