package test.gpstracker;

import test.gpstracker.R;
import test.gpstracker.model.LocationItem;
import test.gpstracker.model.LocationItemList;
import android.app.Activity;
import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.MarkerOptions;
public class MapActivity extends Activity{

	LocationItemList locations;
	private GoogleMap map;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		locations = (LocationItemList)getIntent().getSerializableExtra("locations");
		setContentView(R.layout.map_layout);
		map = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
		if (locations!=null) {
			//add marker to map
			for (int i = 0; i < locations.size(); i++) 
			{
				LocationItem tmpItem = locations.get(i);
				map.addMarker(new MarkerOptions().position(tmpItem.getLatLng()).title(tmpItem.getLocationName()).icon(BitmapDescriptorFactory
						.fromResource(R.drawable.mark_blue)));
			}
			CameraPosition cameraPosition = new CameraPosition.Builder()
			.target(locations.get(0).getLatLng()).zoom(14) // Sets the zoom
			.tilt(30) // Sets the tilt of the camera to 30 degrees
			.build(); // Creates a CameraPosition from the builder
			map.animateCamera(CameraUpdateFactory
					.newCameraPosition(cameraPosition));
		}
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

}
