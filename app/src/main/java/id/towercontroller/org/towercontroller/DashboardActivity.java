package id.towercontroller.org.towercontroller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.towercontroller.org.towercontroller.model.ReportResult;
import id.towercontroller.org.towercontroller.tools.TowerControllerSessionManager;

public class DashboardActivity extends AppCompatActivity {
    public static final String TAG = "[DashboardActivity]";

    @BindView(R.id.btnReportRes)
    Button btnReportRes;
    @BindView(R.id.btnJadwal)
    Button btnJadwal;
    @BindView(R.id.btnLaporan)
    Button btnLaporan;
    @BindView(R.id.logout)
    Button btnLogout;

    TowerControllerSessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);
        sessionManager = new TowerControllerSessionManager(this, getApplicationContext());
        if (!sessionManager.isLogin()) {
            sessionManager.logout();
        }

        btnReportRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, ReportResultActivity.class);
                startActivity(intent);
            }
        });
        btnLaporan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, LaporanActivity.class);
                startActivity(intent);
            }
        });
        btnJadwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, JadwalActivity.class);
                startActivity(intent);
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.logout();
            }
        });
    }
}
