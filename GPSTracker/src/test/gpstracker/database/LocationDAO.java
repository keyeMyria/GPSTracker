package test.gpstracker.database;

import java.util.ArrayList;
import java.util.List;

import test.gpstracker.model.LocationItem;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class LocationDAO {

	public static String TABLE_NAME = "LOCATION";

	public static String ID_CLM_NAME = "ID";
	public static String LOCATIONID_CLM_NAME = "LocationID";
	public static String LONGITUDE_CLM_NAME = "Longitude";
	public static String LATITUDE_CLM_NAME = "Latitude";
	public static String LOCNAME_CLM_NAME = "LocationName";
	public static String ISUPDATED_CLM_NAME = "isUpdated";

	public static String ID_CLM_TYPE = "INTEGER";
	public static String LOCATIONID_CLM_TYPE = "TEXT";
	public static String LONGITUDE_CLM_TYPE = "REAL";
	public static String LATITUDE_CLM_TYPE = "REAL";
	public static String LOCNAME_CLM_TYPE = "TEXT";
	public static String ISUPDATED_CLM_TYPE = "BOOLEAN";

	public static String INSERT_SQL;
	public static String SELECT_BY_COORDINATES;
	public static String SELECT_ALL_SQL;
	public static String UPDATE_SQL;

	public SQLiteDatabase sqliteDatabase;

	public LocationDAO(SQLiteDatabase db) {

		initSQLQuery();
		this.sqliteDatabase = db;
	}

	public void initSQLQuery() {
		StringBuilder s;
		s = new StringBuilder();
		s.append(" INSERT INTO ");
		s.append(LocationDAO.TABLE_NAME);
		s.append(" ( ");
		s.append("  " + LocationDAO.LOCATIONID_CLM_NAME);
		s.append(" ," + LocationDAO.LONGITUDE_CLM_NAME);
		s.append(" ," + LocationDAO.LATITUDE_CLM_NAME);
		s.append(" ," + LocationDAO.LOCNAME_CLM_NAME);
		s.append(" ," + LocationDAO.ISUPDATED_CLM_NAME);
		s.append(" ) VALUES (");
		s.append("  ?");
		s.append(" ,?");
		s.append(" ,?");
		s.append(" ,?");
		s.append(" ,?");
		s.append(" ); ");
		INSERT_SQL = s.toString();

		s = new StringBuilder();
		s.append(" SELECT * FROM");
		s.append(" " + LocationDAO.TABLE_NAME);
		s.append(" WHERE ");
		s.append(" (");
		s.append(" " + LocationDAO.LONGITUDE_CLM_NAME + " = ? ");
		s.append(" AND ");
		s.append(" " + LocationDAO.LATITUDE_CLM_NAME + " = ? ");
		s.append(" );");
		SELECT_BY_COORDINATES = s.toString();

		s = new StringBuilder();
		s.append(" SELECT * FROM ");
		s.append(" " + LocationDAO.TABLE_NAME);
		s.append(";");
		SELECT_ALL_SQL = s.toString();

		s = new StringBuilder();
		s.append(" UPDATE ");
		s.append(TABLE_NAME);
		s.append(" SET ");
		s.append(" " + LOCATIONID_CLM_NAME + " = ?, ");
		s.append(" " + LONGITUDE_CLM_NAME + " = ?, ");
		s.append(" " + LATITUDE_CLM_NAME + " = ?, ");
		s.append(" " + LOCNAME_CLM_NAME + " = ?, ");
		s.append(" " + ISUPDATED_CLM_NAME + " = ? ");
		s.append(" WHERE ");
		s.append(" " + ID_CLM_NAME + " = ?; ");
		UPDATE_SQL = s.toString();

	}

	public void insert(LocationItem item) throws Exception {
		try {
			Object[] values = new Object[] { item.getLocationID(),
					item.getLongitude(), item.getLatitude(),
					item.getLocationName(), item.isUpdated() };

			sqliteDatabase.execSQL(INSERT_SQL, values);
		} catch (Exception e) {
			Log.e("toan", "toan bug" + e.getMessage());
			throw e;
		}
	}

	/**
	 * Lay Location co ID lon nhat (vua insert)
	 * 
	 * @return
	 */
	public LocationItem selectMaxID() {
		LocationItem item = null;
		StringBuilder sb = new StringBuilder();
		sb.append("	SELECT * FROM ");
		sb.append(TABLE_NAME);
		sb.append("		WHERE  ");
		sb.append(ID_CLM_NAME);
		sb.append("	= (	SELECT MAX( ");
		sb.append(ID_CLM_NAME);
		sb.append("	) FROM ");
		sb.append(TABLE_NAME);
		sb.append("	); ");
		Cursor cursor = sqliteDatabase.rawQuery(sb.toString(), null);
		if (cursor.moveToFirst()) {
			item = new LocationItem();
			readData(item, cursor);
		}
		cursor.close();
		return item;
	}
	
	public List<LocationItem> getItemsAllLocation() {
		List<LocationItem> list = new ArrayList<LocationItem>();
		Cursor cursor = this.sqliteDatabase.rawQuery(SELECT_ALL_SQL, null);
		if (cursor.moveToFirst()) {
			LocationItem item;
			do {
				item = new LocationItem();
				readData(item, cursor);
				list.add(item);
			} while ((cursor.moveToNext()));
		}
		return list;
	}

	/**
	 * Lay cac dia diem chua co LocationName Neu khong lay duoc return list.size
	 * = 0
	 * 
	 * @return
	 */
	public List<LocationItem> selectUnnamedLocation() {
		List<LocationItem> list = new ArrayList<LocationItem>();
		StringBuilder sb = new StringBuilder();
		sb.append(" ");
		sb.append(" SELECT * FROM ");
		sb.append(TABLE_NAME);
		sb.append("	WHERE ");
		sb.append(LOCATIONID_CLM_NAME + " = '0' ");
		sb.append(" OR ");
		sb.append(LOCATIONID_CLM_NAME + " = '' ");
		sb.append(" ; ");
		Cursor cursor = sqliteDatabase.rawQuery(sb.toString(), null);
		LocationItem item;

		if (cursor.moveToFirst()) {

			item = new LocationItem();
			readData(item, cursor);
			list.add(item);
		}
		cursor.close();
		return list;

	}

	/**
	 * Select dua vao kinh do, vi do. Neu khong co thi tra ve null
	 * 
	 * @param longitude
	 * @param latitude
	 * @return
	 */
	public LocationItem selectByCoordinates(double longitude, double latitude) {
		Object[] values = new Object[] { longitude, latitude };
		Cursor cursor = sqliteDatabase.rawQuery(SELECT_BY_COORDINATES, null);
		LocationItem item = null;
		if (cursor.moveToFirst()) 
		{
		item = new LocationItem();
			readData(item, cursor);
		}
		cursor.close();
		return item;
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

	/**
	 * Dua du lieu vao Entity tu Cursor
	 * 
	 * @param cursor
	 * @return
	 */
	public static void readData(LocationItem item, Cursor cursor) {
		item.setID(cursor.getInt(cursor.getColumnIndex(ID_CLM_NAME)));
		item.setLocationID(cursor.getString(cursor.getColumnIndex(LOCATIONID_CLM_NAME)));
		item.setLongitude(cursor.getDouble(cursor.getColumnIndex(LONGITUDE_CLM_NAME)));
		item.setLatitude(cursor.getDouble(cursor.getColumnIndex(LATITUDE_CLM_NAME)));
		item.setLocationName(cursor.getString(cursor.getColumnIndex(LOCNAME_CLM_NAME)));
		int i = cursor.getInt(cursor.getColumnIndex(ISUPDATED_CLM_NAME));
		item.setUpdated(i == 1);
	}

	/**
	 * tao Query CREATE TABLE
	 * 
	 * @return
	 */
	public static String getCreateTableSQL() {
		StringBuilder s = new StringBuilder();
		s.append(" CREATE TABLE LOCATION ( ");
		s.append(" ID           INTEGER PRIMARY KEY AUTOINCREMENT ");
		s.append(" 						NOT NULL, ");
		s.append(" LocationID   TEXT    NOT NULL, ");
		s.append(" Longitude    REAL    NOT NULL, ");
		s.append(" Latitude     REAL    NOT NULL, ");
		s.append(" LocationName TEXT    NOT NULL, ");
		s.append(" isUpdated    BOOLEAN NOT NULL ");
		s.append("); ");
		return s.toString();
	}
}
