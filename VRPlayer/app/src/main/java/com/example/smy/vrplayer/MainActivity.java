package com.example.smy.vrplayer;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.example.smy.vrplayer.VRActivity.MultiPlayerActivity;
import com.example.smy.vrplayer.VRActivity.VRPictureShowActivity;
import com.example.smy.vrplayer.VRActivity.VRPlayerActivity;

import java.io.File;
import java.net.URI;

public class MainActivity extends Activity {

    private EditText edtPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        edtPath = (EditText)findViewById(R.id.edt_path);
    }

    public void onPlayClick(View v)
    {
        String path = edtPath.getText().toString();
        Toast.makeText(getApplicationContext(), path, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, VRPlayerActivity.class);
        intent.putExtra(VRPlayerActivity.VIDEO_PATH, path);
        startActivity(intent);
    }

    public void onPictureClick(View v){
        String path = edtPath.getText().toString();
        Intent intent = new Intent(MainActivity.this, VRPictureShowActivity.class);
        intent.putExtra(VRPictureShowActivity.PICTURE_PATH, path);
        startActivity(intent);
    }

    public void onMultiPlayClick(View v){
        String path = edtPath.getText().toString();
        Toast.makeText(getApplicationContext(), path, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, MultiPlayerActivity.class);
        intent.putExtra(MultiPlayerActivity.VIDEO_PATH, path);
        startActivity(intent);
    }

    public void onSurfaceViewClick(View v){
        startActivity(new Intent(MainActivity.this, SurfaceViewTest.class));
    }

    public void onBtnChooseFileClick(View v)
    {
        showFileChooser();
    }

    private static final int FILE_SELECT_CODE = 1;
    //file choose
    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(Intent.createChooser(intent, "Please Choose a Video File"), FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FILE_SELECT_CODE)
        {
            if (resultCode == RESULT_OK)
            {
                Uri uri = data.getData();
                if (uri != null)
                {
                    String path = uri.toString();
                    if (path.toLowerCase().startsWith("file://"))
                    {
                        // Selected file/directory path is below
                        path = (new File(URI.create(path))).getAbsolutePath();
                    } else {
                        path = getImagePath(uri, null);
                    }
                    edtPath.setText(path);
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }

            cursor.close();
        }
        return path;
    }
}
