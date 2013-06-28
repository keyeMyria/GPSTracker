package test.gpstracker.database;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import test.gpstracker.model.HistoryItem;
import test.gpstracker.model.LocationItem;
import test.gpstracker.util.Util;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class HistoryDAO {

	public static String TABLE_NAME = "HISTORY";

	public static String HISTORYID_CLM_NAME = "HistoryID";
	public static String LOCATIONID_CLM_NAME = "LocationID";
	public static String CHECKINTIME_CLM_NAME = "CheckinTIme";

	public static String HISTORYID_CLM_TYPE = "INTEGER";
	public static String LOCATIONID_CLM_TYPE = "INTEGER";
	public static String CHECKINTIME_CLM_TYPE = "DATETIME";

	public static String INSERT_SQL;
	public static String UPDATE_SQL;
	public static String SELECT_ALL_SQL;

	public SQLiteDatabase sqliteDatabase;

	/**
	 * Gan SQLiteDatabase tu ngoai vao, tot nhat lĂ  dung gettWritable
	 * @param db
	 */
	public HistoryDAO(SQLiteDatabase db) {

		initSQLQuery();
		this.sqliteDatabase = db;
	}

	/**
	 * Init cĂ¡c cĂ¢u query
	 */
	public void initSQLQuery() {
		StringBuilder s;

		s = new StringBuilder();
		s.append(" INSERT INTO ");
		s.append(HistoryDAO.TABLE_NAME);
		s.append(" ( ");
		s.append(" " + HistoryDAO.LOCATIONID_CLM_NAME);
		s.append(" ," + HistoryDAO.CHECKINTIME_CLM_NAME);
		s.append(" ) VALUES (");
		s.append(" ?");
		s.append(" ,?");
		s.append(" ); ");
		INSERT_SQL = s.toString();

		s = new StringBuilder();
		s.append(" SELECT H.*, L.* FROM ");
		s.append(" " + HistoryDAO.TABLE_NAME + " H ");
		s.append(" ," + LocationDAO.TABLE_NAME + " L ");
		s.append(" WHERE H." + LOCATIONID_CLM_NAME + " = L."
				+ LocationDAO.ID_CLM_NAME + "; ");
		SELECT_ALL_SQL = s.toString();

	}

	public List<HistoryItem> getAllHistory() {
		List<HistoryItem> list = new ArrayList<HistoryItem>();

		Cursor cursor = this.sqliteDatabase.rawQuery(SELECT_ALL_SQL, null);

		if (cursor.moveToFirst()) {

			HistoryItem item;
			LocationItem locItem;

			do {
				item = new HistoryItem();
				locItem = new LocationItem();
				readData(item, cursor);
				LocationDAO.readData(locItem, cursor);
				item.setLocation(locItem);
				list.add(item);

			} while (cursor.moveToNext());
		}

		return list;
	}

	public void insert(HistoryItem item) throws Exception {

		long k = -10;

		try {
			LocationItem locItem = item.getLocation();
			if (locItem == null) {
				throw new Exception("Location Item is null");
			}

			Object[] values = new Object[] { locItem.getID(),
					item.getCheckinTime() };

			sqliteDatabase.execSQL(INSERT_SQL, values);
		} catch (Exception e) {
			Log.e("toan", "toan bug" + e.getMessage());
			throw e;
		}
	}

	/**
	 * Luu vao CSDL bang locationID va checkinTime
	 * 
	 * @param locationID
	 * @param checkinTime
	 * @throws Exception
	 */
	public void insert(int locationID, Date checkinTime) throws Exception {

		long k = -10;

		try {
			Object[] values = new Object[] { locationID, checkinTime };
			sqliteDatabase.execSQL(INSERT_SQL, values);
		} catch (Exception e) {
			Log.e("toan", "toan bug" + e.getMessage());
			throw e;
		}
	}

	/**
	 * Lay danh sach History tu ngay toi ngay. Kiem tra from <= to truoc khi goi
	 * ham nay
	 * 
	 * @param from
	 * @param to
	 * @return
	 */
	public List<HistoryItem> getByTime(Date from, Date to) 
	{
		List<HistoryItem> list = new ArrayList<HistoryItem>();
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT H.*, L.* FROM ");
		sb.append(" " + HistoryDAO.TABLE_NAME + " H ");
		sb.append(" ," + LocationDAO.TABLE_NAME + " L ");
		sb.append(" WHERE H." + LOCATIONID_CLM_NAME + " = L."
				+ LocationDAO.ID_CLM_NAME + " ");
		sb.append(" AND H. ");
		sb.append(CHECKINTIME_CLM_NAME + " >= ? ");
		sb.append(" AND H. ");
		sb.append(CHECKINTIME_CLM_NAME + " <= ? ");
		Object[] values = new Object[] { from, to };
		Cursor cursor = this.sqliteDatabase.rawQuery(sb.toString(), null);
		if (cursor.moveToFirst()) 
		{
			HistoryItem item;
			LocationItem locItem;
			do {
				item = new HistoryItem();
				locItem = new LocationItem();
				readData(item, cursor);
				LocationDAO.readData(locItem, cursor);
				item.setLocation(locItem);
				list.add(item);

			} while (cursor.moveToNext());
		}

		return list;
	}

	/**
	 * Cap nhat Location dua vao ID Autoincrease cua Location do
	 * 
	 * @param item
	 * @throws Exception
	 */
	public void update(LocationItem item) throws Exception {
		try {
			Object[] values = new Object[] { item.getLocationID(),
					item.getLongitude(), item.getLatitude(),
					item.getLocationName(), item.isUpdated(), item.getID() };
			sqliteDatabase.execSQL(UPDATE_SQL, values);
		} catch (Exception e) {
			throw e;
		}
	}


	public static void readData(HistoryItem item, Cursor cursor) {
		LocationItem locItem;
		item.setHIstoryId(cursor.getInt(cursor
				.getColumnIndex(HISTORYID_CLM_NAME)));
		item.setCheckinTime(Util.string2date(cursor.getString(cursor
				.getColumnIndex(CHECKINTIME_CLM_NAME))));
		locItem = new LocationItem();
		LocationDAO.readData(locItem, cursor);
		item.setLocation(locItem);

	}

	/**
	 * Tao cac cau Query CREATE TABLE
	 * 
	 * @return
	 */
	public static String getCreateTableSQL() {
		StringBuilder s = new StringBuilder();
		s.append("	CREATE TABLE HISTORY (");
		s.append("	HistoryID   INTEGER  PRIMARY KEY AUTOINCREMENT ");
		s.append("						 NOT NULL,");
		s.append("	LocationID  INTEGER  NOT NULL ");
		s.append("			REFERENCES LOCATION ( ID ) ON DELETE CASCADE,");
		s.append("	CheckinTime DATETIME NOT NULL");
		s.append(" );");
		return s.toString();
	}
}
