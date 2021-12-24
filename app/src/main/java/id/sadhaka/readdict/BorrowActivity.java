package id.sadhaka.readdict;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class BorrowActivity extends AppCompatActivity {

    private EditText uname, bookTitle, borrowDate, returnDate;
    private Button borrowBtn;
    private CheckBox terms;

    private String strUsername, strTitle, strDateBorrow, strDateReturn, strTerms, strStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrow);

        uname = (EditText) findViewById(R.id.userNameBorrow);
        bookTitle = (EditText) findViewById(R.id.bookTitle);
        borrowDate = (EditText) findViewById(R.id.dateBorrow);
        returnDate = (EditText) findViewById(R.id.dateReturn);
        terms = (CheckBox) findViewById(R.id.checkAgree);
        borrowBtn = (Button) findViewById(R.id.btnBorrow);

        if (!terms.isChecked()){
            borrowBtn.setAlpha(.5f);
            borrowBtn.setEnabled(false);
        }

        terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!terms.isChecked()){
                    borrowBtn.setAlpha(.5f);
                    borrowBtn.setEnabled(false);
                } else {
                    borrowBtn.setAlpha(1);
                    borrowBtn.setEnabled(true);
                }
            }
        });

        borrowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strUsername = uname.getText().toString();
                strTitle = bookTitle.getText().toString();
                strDateBorrow = borrowDate.getText().toString();
                strDateReturn = returnDate.getText().toString();

                //mengatur status peminjaman
                if(terms.isChecked()){
                    strTerms = "Agree";
                    strStatus = "Borrowed";
                } else {
                    strTerms = "Not Agree";
                }

                dialogAlertBorrow();
            }
        });
    }

    private void dialogAlertBorrow(){
        AlertDialog.Builder dialogAlertBuilder = new AlertDialog.Builder(BorrowActivity.this);

        //membuat judul alert dialog
        dialogAlertBuilder.setTitle("Your Data");

        //mengatur teks dan button pada alert dialog
        dialogAlertBuilder
                .setMessage(
                        "Username : " +strUsername.toString()+ "\n" +
                        "Title : " +strTitle.toString()+ "\n" +
                        "Borrow Date : " +strDateBorrow.toString()+ "\n" +
                        "Return Date : " +strDateReturn.toString() + "\n" +
                        "Terms : "+strTerms.toString()+ "\n")
                .setPositiveButton("Next", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //proses memasukkan nilai ke model data peminjaman
                        DBHelper dbHelper = new DBHelper(getApplicationContext());
                        BorrowHandler borrowHandler = new BorrowHandler();
                        borrowHandler.setUsername(strUsername.toString());
                        borrowHandler.setTitle(strTitle.toString());
                        borrowHandler.setDate_borrow(strDateBorrow.toString());
                        borrowHandler.setDate_return(strDateReturn.toString());
                        borrowHandler.setStatus(strStatus.toString());

                        //memasukkan ke database
                        boolean addBorrow = dbHelper.insertborrowdata(borrowHandler);

                        if (addBorrow) {
                            Toast.makeText(BorrowActivity.this, "Add data success!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(BorrowActivity.this, "Add data failed!", Toast.LENGTH_SHORT).show();
                        }
                        dbHelper.close();

                        //membersihkan inputan
                        uname.getText().clear();
                        bookTitle.getText().clear();
                        borrowDate.getText().clear();
                        returnDate.getText().clear();

                        //ke halaman selanjutnya (list peminjaman)
                        Intent gotoBorrow = new Intent(BorrowActivity.this,BorrowedListActivity.class);
                        startActivity(gotoBorrow);

                    }
                })
                .setNegativeButton("Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

        //membuat alert dialog dari builder
        AlertDialog dialog = dialogAlertBuilder.create();

        //menampilkan alert dialog
        dialog.show();
    }
}