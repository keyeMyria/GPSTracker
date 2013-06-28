package test.gpstracker.model;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import android.location.Location;


import com.google.android.gms.maps.model.LatLng;
public class LocationItem implements Serializable  {

	private int ID;
	private String locationID;
	private double longitude;
	private double latitude;
	private String locationName;
	private boolean isUpdated;

	public LocationItem() {

		this.ID = 0;
		this.locationID = "0";
		this.longitude = 0;
		this.latitude = 0;
		this.locationName = "0"; 
		this.isUpdated = false;
	}

	public LocationItem(String locationID, double longitude, double latitude, String locationName, boolean isUpdated) {
		this.locationID = locationID;
		this.longitude = longitude;
		this.latitude = latitude;
		this.locationName = locationName;
		this.isUpdated = isUpdated;
	}
	
	public int getID(){
		return ID;
	}
	
	public void setID(int id){
		this.ID = id;
	}

	public String getLocationID() {
		return locationID;
	}

	public void setLocationID(String locationID) {
		this.locationID = locationID;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public boolean isUpdated() {
		return isUpdated;
	}

	public void setUpdated(boolean isUpdated) {
		this.isUpdated = isUpdated;
	}

	
	public static LocationItem jsonToPontoReferencia(JSONObject pontoReferencia) {
        try {
            LocationItem result = new LocationItem();
            JSONObject geometry = (JSONObject) pontoReferencia.get("geometry");
            JSONObject location = (JSONObject) geometry.get("location");
            result.setLatitude((Double) location.get("lat"));
            result.setLongitude((Double) location.get("lng"));
            result.setLocationName(pontoReferencia.getString("name"));
            result.setLocationID(pontoReferencia.getString("id"));
            result.setUpdated(true);
            return result;
        } catch (JSONException ex) {
            Logger.getLogger(LocationItem.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

	public LatLng getLatLng() {
		return new LatLng(this.latitude, this.longitude);
	}


	public Location getLocation() {
		Location aLocation = new Location("location");
		aLocation.setLongitude(this.longitude);
		aLocation.setLatitude(this.latitude);
		return aLocation;
	}	
}
