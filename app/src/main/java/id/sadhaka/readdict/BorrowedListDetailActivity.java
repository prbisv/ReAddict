package id.sadhaka.readdict;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class BorrowedListDetailActivity extends AppCompatActivity {

    private TextView uname, bookTitle, borrowDate;
    private EditText returnDate;
    private Button returnBtn, editBtn, deleteBtn;
    private int id;
    private String strDateReturn, strStatus;

    private ArrayList<BorrowHandler> borrowHandler = new ArrayList<BorrowHandler>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrowed_list_detail);

        uname = (TextView) findViewById(R.id.usernameBorrow);
        bookTitle = (TextView) findViewById(R.id.titleBorrow);
        borrowDate = (TextView) findViewById(R.id.borrowDate);
        returnDate = (EditText) findViewById(R.id.returnDate);
        returnBtn = (Button) findViewById(R.id.btnReturn);
        editBtn = (Button) findViewById(R.id.btnEdit);
        deleteBtn = (Button) findViewById(R.id.btnDelete);

        Intent getId = getIntent();
        id = getId.getIntExtra("id", 0);

        //mengecek id yang dikirim ada atau tidak
        if (id > 0){
            final DBHelper dh = new DBHelper(getApplicationContext());

            //mengambil data peminjaman
            Cursor cursor = dh.getBorrowDetail(id);
            cursor.moveToFirst();

            //cek sudah sampai baris terakhir atau belum
            if (cursor.getCount() > 0) {
                while (!cursor.isAfterLast()) {
                    uname.setText((cursor.getString(cursor.getColumnIndexOrThrow("username"))));
                    bookTitle.setText((cursor.getString(cursor.getColumnIndexOrThrow("title"))));
                    borrowDate.setText((cursor.getString(cursor.getColumnIndexOrThrow("date_borrow"))));
                    returnDate.setText((cursor.getString(cursor.getColumnIndexOrThrow("date_return"))));
                    cursor.moveToNext();
                }
                dh.close();
            }

        }

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strDateReturn = returnDate.getText().toString();

                if (returnDate.length() == 0) {
                    Toast.makeText(BorrowedListDetailActivity.this, "Date cannot be empty!", Toast.LENGTH_SHORT).show();
                } else {
                    AlertDialog.Builder dialogAlertBuilder = new AlertDialog.Builder(BorrowedListDetailActivity.this);
                    dialogAlertBuilder.setTitle("Confirmation");
                    dialogAlertBuilder
                            .setMessage(
                                    "Username : " +uname.getText().toString()+ "\n" +
                                    "Title : " +bookTitle.getText().toString()+ "\n" +
                                    "Borrow Date : " +borrowDate.getText().toString()+ "\n" +
                                    "Return Date : " +strDateReturn.toString() + "\n")
                            .setPositiveButton("Next", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    DBHelper dbHelper = new DBHelper(getApplicationContext());
                                    BorrowHandler borrowHandler = new BorrowHandler();
                                    borrowHandler.setDate_return(strDateReturn.toString());

                                    //proses edit berdasarkan id
                                    boolean editBorrow = dbHelper.editborrowdata(borrowHandler, id);

                                    if (editBorrow) {
                                        Toast.makeText(BorrowedListDetailActivity.this, "Edit data success!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(BorrowedListDetailActivity.this, "Edit data failed!", Toast.LENGTH_SHORT).show();
                                    }
                                    dbHelper.close();
                                    Intent gotoList = new Intent(BorrowedListDetailActivity.this, BorrowedListActivity.class);
                                    startActivity(gotoList);
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                    AlertDialog dialog = dialogAlertBuilder.create();
                    dialog.show();
                }
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogAlertBuilder = new AlertDialog.Builder(BorrowedListDetailActivity.this);
                dialogAlertBuilder.setTitle("Confirmation");
                dialogAlertBuilder
                        .setMessage("Are you sure?")
                        .setPositiveButton("Next", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                DBHelper dbHelper = new DBHelper(getApplicationContext());

                                //proses hapus berdasarkan id
                                boolean deleteBorrow = dbHelper.deleteborrowdata(id);

                                if (deleteBorrow) {
                                    Toast.makeText(BorrowedListDetailActivity.this, "Delete data success!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(BorrowedListDetailActivity.this, "Delete data failed!", Toast.LENGTH_SHORT).show();
                                }
                                dbHelper.close();
                                Intent gotoList = new Intent(BorrowedListDetailActivity.this, BorrowedListActivity.class);
                                startActivity(gotoList);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog dialog = dialogAlertBuilder.create();
                dialog.show();
            }
        });

        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogAlertBuilder = new AlertDialog.Builder(BorrowedListDetailActivity.this);
                dialogAlertBuilder.setTitle("Confirmation");
                dialogAlertBuilder
                        .setMessage("Return this book?")
                        .setPositiveButton("Next", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                strStatus = "Return";
                                DBHelper dbHelper = new DBHelper(getApplicationContext());
                                BorrowHandler borrowHandler = new BorrowHandler();
                                borrowHandler.setStatus(strStatus.toString());

                                boolean kembaliPinjam = dbHelper.returnbook(borrowHandler,id);

                                if (kembaliPinjam) {
                                    Toast.makeText(BorrowedListDetailActivity.this, "Return book success!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(BorrowedListDetailActivity.this, "Return book failed!", Toast.LENGTH_SHORT).show();
                                }
                                dbHelper.close();
                                Intent gotoList = new Intent(BorrowedListDetailActivity.this, BorrowedListActivity.class);
                                startActivity(gotoList);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog dialog = dialogAlertBuilder.create();
                dialog.show();
            }
        });

    }
}