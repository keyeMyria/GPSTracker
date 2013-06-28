package test.gpstracker;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.android.gms.internal.el;
import com.google.api.client.auth.oauth2.BearerToken;

import test.gpstracker.adapter.*;
import test.gpstracker.database.DBUtil;
import test.gpstracker.database.HistoryDAO;
import test.gpstracker.database.LocationDAO;
import test.gpstracker.model.HistoryItem;
import test.gpstracker.model.HistoryItemList;
import test.gpstracker.model.LocationItem;
import test.gpstracker.model.LocationItemList;
import test.gpstracker.model.SortedHistoryItem;
import test.gpstracker.util.Util;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.Contacts.Intents.UI;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;

public class MainActivity extends Activity implements OnGroupClickListener{

	ExpandableListView listView;
	GPSExpandableListAdapter adapter;
	ArrayList<SortedHistoryItem> data;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		data = new ArrayList<SortedHistoryItem>();
		listView = (ExpandableListView)findViewById(R.id.expandableListView1);
		listView.setOnGroupClickListener(this);
		createData();
		adapter = new GPSExpandableListAdapter(this, this, data);


	}
	@Override
	public boolean onGroupClick(ExpandableListView parent, View v,
			int groupPosition, long id) {
		// TODO Auto-generated method stub
		Bundle bundle = new Bundle();
		bundle.putSerializable("location", data.get(groupPosition));
		Intent i = new Intent(MainActivity.this,MapActivity.class);
		i.putExtra("location", bundle);
		startActivity(i);
		return true;

	}
	
	//create data for listview
	private void createData() {
		List<HistoryItem> items = null;
		try {
			DBUtil.getInstance(this);
			final SQLiteDatabase db = DBUtil.getReadableDatabase();
			HistoryDAO dao2 = new HistoryDAO(DBUtil.getWritableDatabase());
			items = dao2.getAllHistory();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		if (items.size()>0) {
			Date before = new Date(1999,12,12);
			SortedHistoryItem  item = new SortedHistoryItem(Util.date2string(items.get(0).getCheckinTime()));
			for (int i = 0; i < items.size(); i++) {
				if (before.compareTo(items.get(i).getCheckinTime())==0) {
					item.addHistory(items.get(i));
				}
				else {
					before = items.get(i).getCheckinTime();
					data.add(item);
					item = new SortedHistoryItem(Util.date2string(items.get(i).getCheckinTime()));
					item.addHistory(items.get(i));
				}
			}
		}
	}

}
