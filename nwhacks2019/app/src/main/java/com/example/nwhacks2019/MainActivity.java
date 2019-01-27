package com.example.nwhacks2019;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;


public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    Button btnCamera;
    Button btnAnalyze;
    TextView txtResult;
    View view;

    private final String API_KEY = "2a7ac6f5e6364cd089251fc0b60b6f11";
    private final String API_LINK = "https://canadacentral.api.cognitive.microsoft.com";
    Bitmap bitmap;
    // Vision Client


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCamera = (Button) findViewById(R.id.btnCamera);
        btnAnalyze = (Button) findViewById(R.id.btnAnalyze);
        txtResult = (TextView) findViewById(R.id.txt_result);
       // imageView = (ImageView) findViewById(R.id.imageView2);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
            }



        });

//        btnAnalyze.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                getTextFromImage(v);
//            }
//        });
    }


        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data){
            super.onActivityResult(requestCode, resultCode, data);
            bitmap = (Bitmap) data.getExtras().get("data");
            getTextFromImage(getView());
           // imageView.setImageBitmap(bitmap);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

            if (resultCode == RESULT_OK) {


                Intent intent = new Intent(MainActivity.this, FeedActivity.class);

                startActivity(intent);
            }




        }

        public void getTextFromImage(View v){
        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
        if(!textRecognizer.isOperational()){
            Toast.makeText(getApplicationContext(), "Text cannot be recognized!", Toast.LENGTH_SHORT).show();
            //Data data = Data.getInstance();
            //data.setTextView("lysol wipes");
        }
        else{
            Frame frame = new Frame.Builder().setBitmap(bitmap).build();
            SparseArray<TextBlock> items = textRecognizer.detect(frame);

            StringBuilder sb = new StringBuilder();

            for(int i =0;i<items.size();++i){
            TextBlock myItem = items.valueAt(i);
            sb.append(myItem.getValue());
            sb.append("\n");
            }

            txtResult.setText(sb.toString());
            Data data = Data.getInstance();
            data.setTextView(sb.toString());
        }


        }



        public TextView getTextView(){
            return txtResult;
            }

            private View getView(){
        return this.view;
            }
            private void setView(View view){
            this.view = view;
            }

    }
