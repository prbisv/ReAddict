package id.sadhaka.readdict;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;

import java.util.ArrayList;

public class BorrowedListActivity extends AppCompatActivity {

    private DBHelper readdict;
    protected RecyclerView recyclerView;
    protected RecyclerView.Adapter borrowedList;

    //menyimpan data dari model
    private ArrayList<BorrowHandler> borrowHandler = new ArrayList<BorrowHandler>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrowed_list);

        readdict = new DBHelper(this);
        recyclerView = (RecyclerView) findViewById(R.id.borrowedList);

        final DBHelper dh = new DBHelper(getApplicationContext());

        //memanggil seluruh data peminjaman
        Cursor cursor = dh.getBorrowData();
        cursor.moveToFirst();

        //memastikan ada/tidaknya data
        if (cursor.getCount() > 0) {
            while (!cursor.isAfterLast()) {
                BorrowHandler borrowHandlerList = new BorrowHandler();
                borrowHandlerList.setId((cursor.getInt(cursor.getColumnIndexOrThrow("id"))));
                borrowHandlerList.setTitle((cursor.getString(cursor.getColumnIndexOrThrow("title"))));
                borrowHandlerList.setDate_borrow((cursor.getString(cursor.getColumnIndexOrThrow("date_borrow"))));
                borrowHandlerList.setDate_return((cursor.getString(cursor.getColumnIndexOrThrow("date_return"))));
                borrowHandlerList.setStatus(cursor.getString(cursor.getColumnIndexOrThrow("status")));
                borrowHandler.add(borrowHandlerList);
                cursor.moveToNext();
            }
            dh.close();
        }

        //untuk memasukkan nilai ke recycler view melalui adapter
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        borrowedList = new BorrowedListAdapter(borrowHandler, BorrowedListActivity.this, recyclerView);
        recyclerView.setAdapter(borrowedList);
    }
}