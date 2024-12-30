import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    private var db: SQLiteDatabase? = null

    companion object {
        private const val DATABASE_NAME = "AppDatabase.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "SheetData"
        private const val COLUMN_ID = "id"
        private const val COLUMN_1 = "column1"
        private const val COLUMN_2 = "column2"
    }

    override fun onOpen(db: SQLiteDatabase?) {
        super.onOpen(db)
        this.db = db // Keep reference if needed
    }

    fun closeDatabase() {
        db?.close()
        db = null
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = """
        CREATE TABLE $TABLE_NAME (
            $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COLUMN_1 TEXT NOT NULL,
            $COLUMN_2 TEXT NOT NULL,
            UNIQUE ($COLUMN_1, $COLUMN_2) ON CONFLICT IGNORE
        )
        """
        db?.execSQL(createTableQuery)
        Log.d("DatabaseHelper", "Table $TABLE_NAME created successfully.")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertData(column1: String, column2: String): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_1, column1)
            put(COLUMN_2, column2)
        }
        val result = db.insert(TABLE_NAME, null, values)
        if (result == -1L) {
            Log.e("DatabaseHelper", "Failed to insert row: column1=$column1, column2=$column2")
        } else {
            Log.d("DatabaseHelper", "Data inserted successfully: column1=$column1, column2=$column2")
        }
        return result
    }

    fun getAllData(): List<Pair<String, String>> {
        val dataList = mutableListOf<Pair<String, String>>()
        val db = readableDatabase
        val cursor = db.query(
            TABLE_NAME,
            arrayOf(COLUMN_1, COLUMN_2),
            null,
            null,
            null,
            null,
            null
        )
        while (cursor.moveToNext()) {
            val column1 = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_1))
            val column2 = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_2))
            dataList.add(column1 to column2)
        }
        cursor.close()
        return dataList
    }

    fun clearTable() {
        val db = writableDatabase
        db.execSQL("DELETE FROM $TABLE_NAME")
    }

    fun getTableStructure(): List<String> {
        val db = readableDatabase
        val cursor = db.rawQuery("PRAGMA table_info($TABLE_NAME)", null)
        val columns = mutableListOf<String>()
        while (cursor.moveToNext()) {
            columns.add(cursor.getString(cursor.getColumnIndexOrThrow("name")))
        }
        cursor.close()
        Log.d("DatabaseHelper", "Table structure: $columns")
        return columns
    }

    fun forceCheckpoint() {
        val db = writableDatabase
        val cursor = db.rawQuery("PRAGMA wal_checkpoint(FULL);", null)
        cursor.close()
        Log.d("DatabaseHelper", "Checkpoint executed. Data synced to AppDatabase.db.")
    }
}
