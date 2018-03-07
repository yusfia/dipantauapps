package id.towercontroller.org.towercontroller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

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
import id.towercontroller.org.towercontroller.adapter.ReportResAdapter;
import id.towercontroller.org.towercontroller.config.Constants;
import id.towercontroller.org.towercontroller.connection.RestAPIControl;
import id.towercontroller.org.towercontroller.model.ReportResult;
import id.towercontroller.org.towercontroller.model.User;
import id.towercontroller.org.towercontroller.tools.TowerControllerSessionManager;

public class ReportResultActivity extends AppCompatActivity {
    public static final String TAG = "[ReportResultActivity]";

    @BindView(R.id.listReportRes)
    RecyclerView recyclerView;

    private ReportResAdapter reportResAdapter;
    private List<ReportResult> reportResultList = new ArrayList<>();

    private User user;
    private TowerControllerSessionManager towerControllerSessionManager;
    private Gson g = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_result);
        ButterKnife.bind(this);
        towerControllerSessionManager = new TowerControllerSessionManager(this, getApplicationContext());
        user = towerControllerSessionManager.getUserDetail();
        reportResAdapter = new ReportResAdapter(this, reportResultList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        //recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(reportResAdapter);
        reportResAdapter.notifyDataSetChanged();
        getJadwalOnDb();
    }

    private void getJadwalOnDb() {
        RequestParams params = new RequestParams();
        Log.i(TAG, "Account ID : " + user.getId());
        RestAPIControl.get(Constants.BASE_GET_LAPORAN_PETUGAS + user.getId(), params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                boolean message = false;
                try {
                    message = response.getBoolean("success");
                    if (message) {
                        reportResultList.clear();
                        JSONArray arrObject = response.getJSONArray("data");
                        for (int i = 0; i < arrObject.length(); i++) {
                            JSONObject itemexample = arrObject.getJSONObject(i);
                            ReportResult item = g.fromJson(itemexample.toString(), ReportResult.class);
                            reportResultList.add(item);
                        }
                        reportResAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getApplicationContext(), response.getString("messages"), Toast.LENGTH_LONG).show();
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
