package test.gpstracker.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HistoryItemList {

	private Date fromTime;
	private Date toTime;
	private List<HistoryItem> list;

	public HistoryItemList() {
		fromTime = new Date();
		toTime = new Date();
		list = new ArrayList<HistoryItem>();
	}

	public void add(HistoryItem item) {
		list.add(item);		
	}

	public void add(int index, HistoryItem item) {
		list.add(index, item);
	}

	public HistoryItem get(int index) {
		return list.get(index);
	}

	public HistoryItem[] toArray() {
		return list.toArray(new HistoryItem[list.size()]);
	}

	public int size() {
		return list.size();
	}

	public Date getFromTime() {
		return fromTime;
	}

	public void setFromTime(Date fromTime) {
		this.fromTime = fromTime;
	}

	public Date getToTime() {
		return toTime;
	}

	public void setToTime(Date toTime) {
		this.toTime = toTime;
	}

}