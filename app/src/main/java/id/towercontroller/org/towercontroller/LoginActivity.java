package id.towercontroller.org.towercontroller;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import cz.msebera.android.httpclient.Header;
import id.towercontroller.org.towercontroller.config.Constants;
import id.towercontroller.org.towercontroller.config.PermissionConst;
import id.towercontroller.org.towercontroller.connection.RestAPIControl;
import id.towercontroller.org.towercontroller.model.User;
import id.towercontroller.org.towercontroller.tools.TowerControllerSessionManager;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.mainLayout)
    RelativeLayout mainLayout;
    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.button)
    Button signIn;

    private SweetAlertDialog progressDialog;

    TowerControllerSessionManager sessionManager;
    private boolean checkPermission = false;
    private static final int MY_PERMISSIONS_REQUEST = 999;

    Gson g = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        sessionManager = new TowerControllerSessionManager(this, getApplicationContext());
        if (sessionManager.isLogin()) {
            checkPermission = setPermission();
            if (checkPermission) {
                goToDashborad();
            }
        }
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin();
            }
        });
        setPermission();
    }

    private boolean setPermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, PermissionConst.listPermission, MY_PERMISSIONS_REQUEST);
            return false;
        } else {
            return true;
        }
    }

    private void doLogin() {
        initProgressDialog();
        RequestParams params = new RequestParams();
        params.put("username", username.getText().toString());
        params.put("password", password.getText().toString());
        RestAPIControl.post(Constants.BASE_LOGIN, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    response.getString("code");
                    JSONObject jsonData = response.getJSONObject("data");
                    if (jsonData.getString("messages").equals("Login Sukses")) {
                        JSONArray dataArray = jsonData.getJSONArray("data");
                        User user = g.fromJson(dataArray.getJSONObject(0).toString(), User.class);
                        sessionManager.createLoginSession(user);
                        checkPermission = setPermission();
                        if (checkPermission) {
                            goToDashborad();
                        } else {
                            Toast.makeText(getApplicationContext(), "Izin aplikasi ditolak! mohon aktifkan perizinan yang dibutuhkan.", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Login gagal! username/password salah", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    dissmissProgressDialog();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(getApplicationContext(), "Login gagal! Jaringan/Server bermasalah", Toast.LENGTH_LONG).show();
                dissmissProgressDialog();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(getApplicationContext(), "Login gagal! Jaringan/Server bermasalah", Toast.LENGTH_LONG).show();
                dissmissProgressDialog();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Toast.makeText(getApplicationContext(), "Login gagal! Jaringan/Server bermasalah", Toast.LENGTH_LONG).show();
                dissmissProgressDialog();
            }
        });
    }

    private void goToDashborad() {
        Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST:
                checkPermission = true;
                for (int i = 0; i < grantResults.length; i++) {
                    checkPermission = checkPermission && (grantResults[i] == 1);
                }
                break;
            default:
                checkPermission = false;
        }
    }

    public void initProgressDialog() {
        progressDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        progressDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        progressDialog.setTitleText("Loading...");
        progressDialog.setContentText("Sedang login!");
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.show();
    }

    public void dissmissProgressDialog() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
