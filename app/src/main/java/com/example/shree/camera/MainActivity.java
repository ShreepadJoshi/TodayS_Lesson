package com.example.shree.camera;

import android.content.Intent;


import android.content.pm.PackageManager;
import android.graphics.Bitmap;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends ActionBarActivity {
    Button b1,b2;
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1=(Button)findViewById(R.id.button);
        iv=(ImageView)findViewById(R.id.imageView);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if(data != null){


        Bitmap bitmap = (Bitmap) data.getExtras().get("data");


        if (shouldAskPermission() == true){
            String[] perms = {"android.permission. WRITE_EXTERNAL_STORAGE"};
            int permsRequestCode = 200;
            //requestPermissions(perms, permsRequestCode);
        }

        Bitmap taggedBitmap = writeInformationOnImage(bitmap);
        storeCameraPhotoInSDCard(taggedBitmap, "0123456");
        iv.setImageBitmap(taggedBitmap);
        }
    }

    private Bitmap writeInformationOnImage(Bitmap loadedBitmap) {

        Bitmap drawableBitmap = loadedBitmap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(drawableBitmap);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        canvas.drawBitmap(drawableBitmap, 0, 0, paint);
        paint.setColor(Color.rgb(110, 110, 110));
        // text size in pixels
        paint.setTextSize(8);
        paint.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        paint.setAntiAlias(true);

        paint.setShadowLayer(1f, 0f, 1f, Color.DKGRAY);
        canvas.drawText("EFGH afldk aslfjsd fopa skjdfokas", 0, 10, paint);
        canvas.save();

        return drawableBitmap;
    }


    private void storeCameraPhotoInSDCard(Bitmap bitmap, String currentDate){
        File outputFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "photo_" + currentDate + ".jpg");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int permsRequestCode, String[] permissions, int[] grantResults){

        switch(permsRequestCode){
            case 200:
                boolean writeAccepted = grantResults[0]== PackageManager.PERMISSION_GRANTED;
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private boolean shouldAskPermission(){

        return(Build.VERSION.SDK_INT> Build.VERSION_CODES.LOLLIPOP_MR1);

    }

}