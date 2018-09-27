package com.example.tannerthurman.beautifulbulldog;

import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.content.Intent;
import android.view.View;
import android.graphics.Bitmap;
import java.io.ByteArrayOutputStream;

import io.realm.Realm;

public class NewBulldogActivity extends AppCompatActivity {

    private EditText nameField;
    private EditText ageField;
    private Button saveButton;
    private ImageButton imageButton;
    Realm realm = Realm.getDefaultInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_bulldog);

        nameField = (EditText) findViewById(R.id.name_field);
        ageField = (EditText) findViewById(R.id.age_field);
        saveButton = (Button) findViewById(R.id.save_button);
        imageButton = (ImageButton) findViewById(R.id.imageButton);

        imageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, 1);
                }
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(!nameField.getText().toString().matches("") && !ageField.getText().toString().matches("") && imageButton.getDrawable() != null){
                    realm.executeTransaction(new Realm.Transaction() {
                       @Override
                       public void execute(Realm realm) {
                           Bulldog bulldog = new Bulldog();
                           bulldog.setAge(ageField.getText().toString());
                           bulldog.setName(nameField.getText().toString());
                           bulldog.setId(realm.where(Bulldog.class).findAllSorted("id").last().getId()+1);
                           BitmapDrawable image = (BitmapDrawable) imageButton.getDrawable();
                           ByteArrayOutputStream baos = new ByteArrayOutputStream();
                           image.getBitmap().compress(Bitmap.CompressFormat.JPEG, 100, baos);
                           byte[] imageInByte = baos.toByteArray();
                           bulldog.setImage(imageInByte);

                           realm.copyToRealm(bulldog);
                           finish();

                       }
                    });
                }
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageButton.setImageBitmap(imageBitmap);
        }

    }
}
