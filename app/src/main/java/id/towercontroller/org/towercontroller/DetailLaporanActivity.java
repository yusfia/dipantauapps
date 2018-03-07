package id.towercontroller.org.towercontroller;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import cz.msebera.android.httpclient.Header;
import id.towercontroller.org.towercontroller.config.CameraConst;
import id.towercontroller.org.towercontroller.config.Constants;
import id.towercontroller.org.towercontroller.connection.RestAPIControl;
import id.towercontroller.org.towercontroller.model.Report;
import id.towercontroller.org.towercontroller.model.SubmitInfo;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

public class DetailLaporanActivity extends AppCompatActivity {
    public static final String TAG = "[DetailLaporanActivity]";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.takePicture)
    ImageButton btnTakePic;
    @BindView(R.id.cameraResult)
    ImageView takenPic;
    @BindView(R.id.description)
    EditText description;
    @BindView(R.id.submit)
    Button btnSubmit;

    private Report report = new Report();
    SweetAlertDialog progressDialog;

    public void initProgressDialog() {
        progressDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        progressDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        progressDialog.setTitleText("Loading...");
        progressDialog.setContentText("Sedang menunggu proses pengiriman data!");
        progressDialog.setCanceledOnTouchOutside(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_laporan);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        initProgressDialog();
        btnTakePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int permission = ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.CAMERA);
                if (permission == PackageManager.PERMISSION_GRANTED) {
                    EasyImage.openCamera(DetailLaporanActivity.this, CameraConst.REQUEST_CODE_CAMERA);
                } else {
                    Toast.makeText(getApplicationContext(), "Penggunaan kamera belum diizinkan oleh pengguna", Toast.LENGTH_LONG).show();
                }
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                report.setDescription(description.getText().toString());
                SubmitInfo.report = report;
                try {
                    uploadPicture();
                } catch (FileNotFoundException e) {
                    Toast.makeText(getApplicationContext(), "File hasil foto tidak ditemukan!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagesPicked(@NonNull List<File> imageFiles, EasyImage.ImageSource source, int type) {
                switch (type) {
                    case CameraConst.REQUEST_CODE_CAMERA:
                        if (imageFiles.size() > 0) {
                            report.setPhoto(imageFiles.get(0));
                            RequestOptions resopt = new RequestOptions();
                            resopt.fitCenter();
                            Glide.with(DetailLaporanActivity.this)
                                    .load(report.getPhoto())
                                    .apply(resopt)
                                    .into(takenPic);
                        }
                        break;
                }
            }

            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                //Some error handling
            }

            public void onCanceled(EasyImage.ImageSource source, int type) {
                // Cancel handling, you might wanna remove taken photo if it was canceled
                if (source == EasyImage.ImageSource.CAMERA) {
                    File photoFile = EasyImage.lastlyTakenButCanceledPhoto(DetailLaporanActivity.this);
                    if (photoFile != null) photoFile.delete();
                }
            }
        });
    }

    public void uploadPicture() throws FileNotFoundException {
        RequestParams params = new RequestParams();
        params.put("photo", report.getPhoto());
        //Toast.makeText(getApplicationContext(), "File tidak ditemukan!", Toast.LENGTH_LONG).show();
        progressDialog.show();
        RestAPIControl.post(Constants.BASE_FILE_UPLOAD, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                progressDialog.dismiss();
                try {
                    if (response.getBoolean("success")){
                        SubmitInfo.report.setFileName(response.getString("filename"));
                        postDetail();
                    } else {
                        Toast.makeText(getApplicationContext(), response.getString("messages"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Foto laporan gagal di upload!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Foto laporan gagal di upload!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Foto laporan gagal di upload!", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void postDetail() {
        RequestParams params = new RequestParams();
        params.put("userId", SubmitInfo.user.getId());
        params.put("fotoMenaraLaporanPath", SubmitInfo.report.getFileName());
        params.put("longitudeMenaraLaporan", SubmitInfo.user.getLongitude());
        params.put("latitudeMenaraLaporan", SubmitInfo.user.getLatitude());
        params.put("menaraId", SubmitInfo.tower.getId());
        params.put("laporanDescription", SubmitInfo.report.getDescription());
        progressDialog.show();
        RestAPIControl.post(Constants.BASE_LAPORAN_PETUGAS, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                progressDialog.dismiss();
                try {
                    if (response.getBoolean("success")){
                        Toast.makeText(getApplicationContext(), response.getString("messages"), Toast.LENGTH_LONG).show();
                        resetSubmitInfo();
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), response.getString("messages"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Laporan gagal terkirim!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Laporan gagal terkirim!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Laporan gagal terkirim!", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void resetSubmitInfo(){
        SubmitInfo.report = null;
        SubmitInfo.tower = null;
        SubmitInfo.user = null;
    }
}
