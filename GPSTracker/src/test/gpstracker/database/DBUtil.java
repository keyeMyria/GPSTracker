package test.gpstracker.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import test.gpstracker.R;

public class DBUtil {

	private static DBUtil util = new DBUtil();
	private static DatabaseHelper helper = null;
	private static Context context = null;

	public static void initialize(final Context context) throws Exception {
		DBUtil.context = context;
		if (helper == null) {

			final String dbName = context.getString(R.string.db_filename);
			int version = 1;

			try {
				version = Integer.parseInt(context
						.getString(R.string.db_version));
			} catch (final Exception e) {

			}

			helper = util.new DatabaseHelper(context, dbName, version);
			try {
				helper.getWritableDatabase();
			} catch (Exception e) {
				Log.e("toan", "toan bug" + e.getMessage());
				close();
				helperDestroy();
				throw e;
			}

			if (helper.initialized == null) {
				helper.initialized = true;
			}
		}
	}

	public static void close() {
		if (helper != null) {
			try {
				helper.close();
			} catch (Exception ignore) {
			}
		}
	}

	public static void helperDestroy() {
		helper = null;
	}

	public static SQLiteDatabase getWritableDatabase() {
		return helper.getWritableDatabase();
	}

	public static SQLiteDatabase getReadableDatabase() {
		return helper.getReadableDatabase();
	}

	public static SQLiteDatabase getInstance(final Context context)
			throws Exception {
		initialize(context);
		return helper.getWritableDatabase();
	}

	public static boolean isInitialized() {
		if (helper != null) {
			return helper.isInitialized();
		}
		return false;
	}
	class DatabaseHelper extends SQLiteOpenHelper {

		private Context context = null;
		private Boolean initialized;

		public boolean isInitialized() {
			return ((this.initialized != null) && this.initialized);
		}

		public DatabaseHelper(final Context context, final String fileName,
				final int version) {
			super(context, fileName, null, version);
			this.context = context;
		}

		private String generateDBCreateQuery() {
			StringBuilder s = new StringBuilder();

			s.append(LocationDAO.getCreateTableSQL());
			s.append("   ");
			s.append(HistoryDAO.getCreateTableSQL());

			return s.toString();
		}

		private String generateDBDropTableQuery() {
			StringBuilder s = new StringBuilder();

			s.append(" DROP TABLE IF EXISTS ");
			s.append(LocationDAO.TABLE_NAME);
			s.append(";  ");

			s.append(" DROP TABLE IF EXISTS ");
			s.append(HistoryDAO.TABLE_NAME);
			s.append(";  ");

			return s.toString();
		}

		@Override
		public void onCreate(final SQLiteDatabase db) {
			db.execSQL(generateDBCreateQuery());
		}

		@Override
		public void onUpgrade(final SQLiteDatabase db, final int oldVersion,
				final int newVersion) {
			db.execSQL(generateDBDropTableQuery());
			onCreate(db);
		}
	}

}
