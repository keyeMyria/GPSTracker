package test.gpstracker.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LocationItemList implements Serializable {

	private List<LocationItem> list;

	public LocationItemList() {

		list = new ArrayList<LocationItem>();

	}

	public void add(LocationItem item) {
		list.add(item);
	}

	public void add(int index, LocationItem item) {
		list.add(index, item);
	}

	public LocationItem get(int index) {
		return list.get(index);
	}
	
	public LocationItem[] toArray(){
		return list.toArray(new LocationItem[list.size()]);
	}

	public int size() {
		return list.size();
	}

}
