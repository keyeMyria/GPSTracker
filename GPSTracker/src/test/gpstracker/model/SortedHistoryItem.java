package test.gpstracker.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SortedHistoryItem implements Serializable {
	String date;
	List<HistoryItem> list;
	
	public SortedHistoryItem(String date) {
		this.date = date;
		list = new ArrayList<HistoryItem>();
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	
	public List<HistoryItem> getListHistoryItems() {
		return list;
	}
	public void setListHistoryItems(List<HistoryItem> historyItems) {
		this.list = historyItems;
	}
	
	public void addHistory(HistoryItem historyItem) {
		list.add(historyItem);
	}
	
	public HistoryItem getHistory(int index) {
		return list.get(index);
	}
	
	public int getSize() {
		return list.size();
	}
}
