package test.gpstracker.adapter;
 
import java.util.ArrayList;
import java.util.HashMap;  
import test.gpstracker.R;
import test.gpstracker.model.LocationItem;
import test.gpstracker.model.SortedHistoryItem;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ExpandableListAdapter;

public class GPSExpandableListAdapter extends BaseExpandableListAdapter implements ExpandableListAdapter  {
	public Context context;
	CheckBox checkBox;
    private LayoutInflater vi;
    private ArrayList<SortedHistoryItem> data;
    int _objInt;
    public static Boolean checked[] = new Boolean[1];
  
    HashMap<Long,Boolean> checkboxMap = new HashMap<Long,Boolean>();
    private static final int GROUP_ITEM_RESOURCE = R.layout.group_item;
    private static final int CHILD_ITEM_RESOURCE = R.layout.child_item;
    public String []check_string_array;
    
    public GPSExpandableListAdapter(Context context, Activity activity, ArrayList<SortedHistoryItem>data) {
        this.data = data;
        this.context = context;
        vi = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        
        _objInt = data.size(); 
        check_string_array = new String[_objInt];
        popolaCheckMap();
    }
    public void popolaCheckMap(){

    	SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);  
    	String buffer = null;
        
        for(int i=0; i<_objInt; i++){
        	buffer = settings.getString(String.valueOf((int)i),"false");
        	if(buffer.equals("false"))
        		checkboxMap.put((long)i, false);
        	else checkboxMap.put((long)i, true);
        }
    }
    
    public class CheckListener implements OnCheckedChangeListener{

        long pos;

        public void setPosition(long p){
            pos = p;
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView,
                boolean isChecked) {
        	Log.i("checkListenerChanged", String.valueOf(pos)+":"+String.valueOf(isChecked));
            checkboxMap.put(pos, isChecked); 
            if(isChecked == true) check_string_array[(int)pos] = "true";
            else				  check_string_array[(int)pos] = "false";
           // save checkbox state of each group
            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);                 
            SharedPreferences.Editor preferencesEditor = settings.edit(); 
            preferencesEditor.putString(String.valueOf((int)pos), check_string_array[(int)pos]);
            preferencesEditor.commit(); 
        }
    }
    public Object getChild(int groupPosition, int childPosition) {
        return data.get(groupPosition).getHistory(childPosition);// data[groupPosition][childPosition];
    }

    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    public int getChildrenCount(int groupPosition) {
        return data.get(groupPosition).getSize();// data[groupPosition].length;
    }

    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View v = convertView;
        String child =String.valueOf(((LocationItem) getChild(groupPosition, childPosition)).getLocationID());
        
        if (child != null) {
            v = vi.inflate(CHILD_ITEM_RESOURCE, null);
            ViewHolder holder = new ViewHolder(v);
            holder.text.setText(Html.fromHtml(child));
            
//            holder.imageview.setImageResource(id_res);
        }
        return v;
    }

    public Object getGroup(int groupPosition) {
        return data.get(groupPosition);
    }

    public int getGroupCount() {
        return data.size();// data.length;
    }


    public long getGroupId(int groupPosition) {
        return groupPosition;
    }


    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View v = convertView;
        String group = ((SortedHistoryItem) getGroup(groupPosition)).getDate();
        long group_id = getGroupId(groupPosition);
        
        if (group != null) {
            v = vi.inflate(GROUP_ITEM_RESOURCE, null);
            ViewHolder holder = new ViewHolder(v);

            holder.text.setText(Html.fromHtml(group));

         
            CheckListener checkL = new CheckListener();
            checkL.setPosition(group_id);
           
        }
        return v;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    public boolean hasStableIds() {
        return true;
    }
    
    public class ViewHolder {

        public TextView text;
       
        public ViewHolder(View v) {
            this.text = (TextView)v.findViewById(R.id.text1);
        }

    }
} 