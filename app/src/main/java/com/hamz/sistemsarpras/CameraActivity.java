package com.hamz.sistemsarpras;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.hamz.sistemsarpras.api.ApiClient;
import com.hamz.sistemsarpras.api.ApiInterface;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CameraActivity extends AppCompatActivity{

    private Toolbar toolbar;
    private Button btnChooseImage, btnSend, btnBack;
    private Context context;
    private ImageView ivResult;

    //New
    String mediaPath;
    String[] mediaColoumns = { MediaStore.Video.Media._ID };
    ProgressDialog progressDialog;

    private static final int REQUEST_CAMERA = 1;
    private static final int REQUEST_GALLERY = 2;
    private String timeStamp;

    ApiInterface mApiInterface;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        initComponent();

        context = this;
        ivResult = (ImageView) findViewById(R.id.ivResult);
        btnSend  = (Button) findViewById(R.id.btnSend);

        //New
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");


        btnChooseImage = (Button) findViewById(R.id.btnChooseImage);
        btnChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] items = {"Take Photo", "Choose from Library"};

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Add Photo!");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        switch (position) {
                            case 0:
                                takePicture();
                                break;
                            case 1:
                                openGallery();
                                break;
                        }
                    }
                });
                builder.show();
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View dialogView = getLayoutInflater().inflate(R.layout.description, null);
                final EditText title = (EditText) dialogView.findViewById(R.id.etTitle);
                final EditText desc = (EditText) dialogView.findViewById(R.id.etDesc);

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(dialogView);
                builder.setTitle("Description!");
                builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        uploadFile(title.getText().toString(),desc.getText().toString());
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });

        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void initComponent() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void takePicture() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = getFile();
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(cameraIntent, REQUEST_CAMERA);
    }

    private File getFile() {
        File folder = new File("sdcard/sarpras/my_capture");

        if (!folder.exists()) {
            folder.mkdir();
        }
        timeStamp = new SimpleDateFormat("yyyyMMdd__HHmmss").format(new Date());
        File imageFile = new File(folder, "HMZ_"+ timeStamp +".jpg");
        return imageFile;
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CAMERA){
            if (resultCode == RESULT_OK) {
                mediaPath = "sdcard/camera_sarpras/HMZ_"+ timeStamp +".jpg";
                ivResult.setImageDrawable(Drawable.createFromPath(mediaPath));
            }
        }
        else if (requestCode == REQUEST_GALLERY) {
            if (resultCode == RESULT_OK) {
                Uri selectedImageUri = data.getData();

                if (selectedImageUri != null) {
                    //void buatan
                    mediaPath = getPathFromUri(selectedImageUri);
                    ivResult.setImageURI(selectedImageUri);
                }
            }
        }
    }

    private String getPathFromUri(Uri contentUri) {
        String res = null;
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, projection, null, null, null);
        if (cursor.moveToFirst()) {
            int coloumn_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(coloumn_index);
        }
        cursor.close();
        return res;
    }


    private void uploadFile(String title, String desc) {
        progressDialog.show();

        //Map is used to multipart the file using okhttp3.RequestBody
        Map<String, RequestBody> map = new HashMap<>();
        File file = new File(mediaPath);

        //Parsing any Media type file
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        map.put("file\"; filename=\"" + file.getName() + "\"", requestBody);

        RequestBody id_user = RequestBody.create(MediaType.parse("text/plain"),String.valueOf(new PrefManager(context).getIdUser()));
        RequestBody thetitle = RequestBody.create(MediaType.parse("text/plain"), title);
        RequestBody thedesc    = RequestBody.create(MediaType.parse("text/plain"), desc);

        mApiInterface = new ApiClient(context).getClient().create(ApiInterface.class);
        Call<UploadObject> call = mApiInterface.upload("token", map, id_user, thetitle, thedesc);
        call.enqueue(new Callback<UploadObject>() {
            @Override
            public void onResponse(Call<UploadObject> call, Response<UploadObject> response) {
                UploadObject uploadObject = response.body();
                if (uploadObject != null) {
                    if (uploadObject.getSuccess()) {
                        Toast.makeText(context, uploadObject.getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(context, uploadObject.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.v("Response", uploadObject.toString());
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<UploadObject> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
    }
}
