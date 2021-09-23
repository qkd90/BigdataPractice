package com.hmlyinfo.app.soutu.journey;

//存放景点间所需要的时间，仅用来存放数据，为SearchJourney服务
public class CarTimeBetween {
	private Long from;       //开始的景点代码
	private Long to;         //结束的景点代码
	private int cost;       //景点间所需要花费的时间


	public CarTimeBetween(Long from, Long to, int cost) {
		this.from = from;
		this.to = to;
		this.cost = cost;
	}

	public CarTimeBetween() {

	}

	public Long getFrom() {
		return from;
	}

	public void setFrom(Long from) {
		this.from = from;
	}

	public Long getTo() {
		return to;
	}

	public void setTo(Long to) {
		this.to = to;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public CarTimeBetween myclone() {
		CarTimeBetween t = new CarTimeBetween();
		t.setCost(this.cost);
		t.setFrom(this.from);
		t.setTo(this.to);
		return t;
	}

}
