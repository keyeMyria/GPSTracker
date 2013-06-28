package test.gpstracker.model;

import java.util.Date;

public class HistoryItem {

	private int historyID;
	private Date checkinTime;
	private LocationItem location;
	
	public HistoryItem()
	{
		this.historyID =0;
		this.checkinTime = new Date();
		this.location = new LocationItem();
	}

	public HistoryItem(int historyID, String locationID, Date checkinTime, LocationItem location)
	{
		this.historyID = historyID;
		this.checkinTime = checkinTime;
		this.location = location;
	}
	
	public int getHistoryID() {
		return historyID;
	}

	public void setHIstoryId(int id) {
		this.historyID = id;
	}

	public Date getCheckinTime() {
		return checkinTime;
	}

	public void setCheckinTime(Date checkinTime) {
		this.checkinTime = checkinTime;
	}
	
	public LocationItem getLocation() {
		return location;
	}

	public void setLocation(LocationItem location) {
		this.location = location;
	}


}
