package com.example.tannerthurman.beautifulbulldog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import io.realm.Realm;
import android.widget.ImageView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BulldogActivity extends AppCompatActivity {

    private TextView nameField;
    private TextView ageField;
    private Realm realm;
    private Button voteButton;
    private ImageView image;
    private Spinner spinner;
    public User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulldog);

        nameField = (TextView) findViewById(R.id.name_field);
        ageField = (TextView) findViewById(R.id.age_field);
        voteButton = (Button) findViewById(R.id.vote_button);
        image = (ImageView) findViewById(R.id.imageView);
        spinner = (Spinner) findViewById(R.id.spinner);

        realm = Realm.getDefaultInstance();
        String id = (String) getIntent().getStringExtra("bulldog");
        Bulldog bulldog = realm.where(Bulldog.class).equalTo("id", id).findFirst();

        Realm realm = Realm.getDefaultInstance();
        String username = (String) getIntent().getStringExtra("username");
        user = realm.where(User.class).equalTo("username", username).findFirst();

        nameField.setText(bulldog.getName());
        ageField.setText(bulldog.getAge());

        if(bulldog.getImage() != null) {
            Bitmap bmp = BitmapFactory.decodeByteArray(bulldog.getImage(), 0, bulldog.getImage().length);
            image.setImageBitmap(bmp);
        }


    }
}
