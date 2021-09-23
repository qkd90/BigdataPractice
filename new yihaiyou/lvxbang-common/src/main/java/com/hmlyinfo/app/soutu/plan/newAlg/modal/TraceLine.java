package com.hmlyinfo.app.soutu.plan.newAlg.modal;

public class TraceLine {//景点参观顺序，记录了，起始点，终止点，trace[]记录下一个游玩景点ID
	public int start;
	public int end;
	public long trace[];

	public TraceLine(int start, int end, int size) {
		this.start = start;
		this.end = end;
		trace = new long[size];
		for (int i = 0; i < size; i++) {
			trace[i] = -1;
		}

	}

	public TraceLine() {

	}

	;
}
