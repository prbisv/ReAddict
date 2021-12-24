package id.sadhaka.readdict;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    //membuat database
    public DBHelper(Context context) {
        super(context, "readdict.db", null, 1);
//        context.deleteDatabase("readdict.db");
    }

    //membuat tabel
    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table borrow(id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, title TEXT, date_borrow TEXT, date_return TEXT, status TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop Table if exists borrow");
    }

    //insert data peminjaman
    public Boolean insertborrowdata(BorrowHandler borrowHandler) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", borrowHandler.getUsername());
        contentValues.put("title", borrowHandler.getTitle());
        contentValues.put("date_borrow", borrowHandler.getDate_borrow());
        contentValues.put("date_return", borrowHandler.getDate_return());
        contentValues.put("status", borrowHandler.getStatus());
        return DB.insert("borrow", null, contentValues) > 0;
    }

    //mengambil data peminjaman
    public Cursor getBorrowData(){
        SQLiteDatabase DB = getReadableDatabase();
        return DB.rawQuery("select * from borrow", null);
    }

    public Cursor getBorrowDetail(int id_pinjam){
        SQLiteDatabase DB = getReadableDatabase();
        return DB.rawQuery("select * from borrow where id = " + id_pinjam, null);
    }

    //edit data peminjaman
    public boolean editborrowdata(BorrowHandler borrowHandler, int id_pinjam) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("date_return", borrowHandler.getDate_return());
        return db.update("borrow", values, "id" + "=" + id_pinjam, null) > 0;
    }

    //hapus data peminjaman
    public boolean deleteborrowdata (int id_pinjam) {
        SQLiteDatabase db = getReadableDatabase();
        return db.delete("borrow", "id" + "=" + id_pinjam, null) > 0;
    }

    //edit status peminjaman
    public boolean returnbook(BorrowHandler borrowHandler, int id_pinjam) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("status", borrowHandler.getStatus());
        return db.update("borrow", values, "id" + "=" + id_pinjam, null) > 0;
    }

}