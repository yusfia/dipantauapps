package id.towercontroller.org.towercontroller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;
import id.towercontroller.org.towercontroller.adapter.JadwalAdapter;
import id.towercontroller.org.towercontroller.config.Constants;
import id.towercontroller.org.towercontroller.connection.RestAPIControl;
import id.towercontroller.org.towercontroller.model.ItemExample;
import id.towercontroller.org.towercontroller.model.ItemMarker;
import id.towercontroller.org.towercontroller.model.Jadwal;
import id.towercontroller.org.towercontroller.model.User;
import id.towercontroller.org.towercontroller.tools.TowerControllerSessionManager;

public class JadwalActivity extends AppCompatActivity {
    public static String TAG = "[JadwalActivity]";

    @BindView(R.id.listJadwal)
    RecyclerView recyclerView;

    private JadwalAdapter jadwalAdapter;
    private List<Jadwal> jadwalList = new ArrayList<>();

    private User user;
    private TowerControllerSessionManager towerControllerSessionManager;
    private Gson g = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal);
        ButterKnife.bind(this);
        towerControllerSessionManager = new TowerControllerSessionManager(this, getApplicationContext());
        user = towerControllerSessionManager.getUserDetail();
        jadwalAdapter = new JadwalAdapter(this, jadwalList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        //recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(jadwalAdapter);
        jadwalAdapter.notifyDataSetChanged();
        getJadwalOnDb();
    }

    private void getJadwalOnDb(){
        RequestParams params = new RequestParams();
        params.put("userAccountId", user.getId());
        Log.i(TAG, "Account ID : "+user.getId());
        RestAPIControl.post(Constants.BASE_LIST_SURVEY_MENARA, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                String message = null;
                try {
                    message = response.getString("messages");
                    if (message.equals("data ditemukan")) {
                        jadwalList.clear();
                        JSONArray arrObject = response.getJSONArray("data");
                        for (int i = 0; i < arrObject.length(); i++) {
                            JSONObject itemexample = arrObject.getJSONObject(i);
                            Jadwal item = g.fromJson(itemexample.toString(), Jadwal.class);
                            jadwalList.add(item);
                        }
                        jadwalAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(getApplicationContext(), "Jaringan/Server bermasalah", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(getApplicationContext(), "Jaringan/Server bermasalah", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Toast.makeText(getApplicationContext(), "Jaringan/Server bermasalah", Toast.LENGTH_LONG).show();
            }
        });
    }
}
