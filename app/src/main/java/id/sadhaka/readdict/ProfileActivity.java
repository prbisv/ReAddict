package id.sadhaka.readdict;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity {
    private TextView fullnameProfile, usernameProfile, emailProfile, genderProfile, interestProfile, favbooksProfile;
    private String strFullname, strUsername, strEmail, strRadio, strSeekbar, strCheckbox;
    private ImageView profileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileImage = (ImageView) findViewById(R.id.profilePictPage);
        fullnameProfile = (TextView) findViewById(R.id.fullNameProfile);
        usernameProfile = (TextView) findViewById(R.id.userNameProfile);
        emailProfile = (TextView) findViewById(R.id.emailAddressProfile);
        genderProfile = (TextView) findViewById(R.id.genderProfile);
        interestProfile = (TextView) findViewById(R.id.interestProfile);
        favbooksProfile = (TextView)findViewById(R.id.favBooksProfile);

        //mendapatkan data dari inputan sebelumnya untuk ditampilkan di halaman profil
        Intent getData = getIntent();
        strFullname = getData.getStringExtra("Fullname");
        strUsername = getData.getStringExtra("Username");
        strEmail = getData.getStringExtra("Email");
        strRadio = getData.getStringExtra("Gender");
        strSeekbar = getData.getStringExtra("Interest");
        strCheckbox = getData.getStringExtra("Favorite Books");

        //mengatur hasil inputan yang ditampilkan pada halaman profil
        fullnameProfile.setText(strFullname);
        usernameProfile.setText(strUsername);
        emailProfile.setText(strEmail);
        genderProfile.setText(strRadio);
        interestProfile.setText(strSeekbar);
        favbooksProfile.setText(strCheckbox);
    }

    protected void onResume(){
        Toast.makeText(getApplicationContext(), "This is profile page", Toast.LENGTH_SHORT).show();
        Log.i("Status", "This is profile page");
        super.onResume();
    }

    protected void onStop(){
        Toast.makeText(getApplicationContext(), "Page switched", Toast.LENGTH_SHORT).show();
        Log.i("State", "Page switched");
        super.onStop();
    }

    protected void onDestroy(){
        Toast.makeText(getApplicationContext(), "Bye bye!", Toast.LENGTH_SHORT).show();
        Log.i("State", "Bye bye!");
        super.onDestroy();
    }

}