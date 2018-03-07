package id.towercontroller.org.towercontroller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import cz.msebera.android.httpclient.Header;
import id.towercontroller.org.towercontroller.adapter.CheckBoxItemTypeFilterConnectionFilter;
import id.towercontroller.org.towercontroller.adapter.CheckBoxItemTypeFilterTowerOwner;
import id.towercontroller.org.towercontroller.adapter.CheckBoxItemTypeFilterTowerOwnerType;
import id.towercontroller.org.towercontroller.adapter.CheckBoxItemTypeFilterTowerStatus;
import id.towercontroller.org.towercontroller.adapter.CheckBoxItemTypeFilterTowerTypeFilter;
import id.towercontroller.org.towercontroller.adapter.CheckBoxItemTypeSiteAdapter;
import id.towercontroller.org.towercontroller.config.Constants;
import id.towercontroller.org.towercontroller.connection.RestAPIControl;
import id.towercontroller.org.towercontroller.model.ItemExample;
import id.towercontroller.org.towercontroller.model.ItemMarker;
import id.towercontroller.org.towercontroller.model.MapFilter;
import id.towercontroller.org.towercontroller.model.SubmitInfo;
import id.towercontroller.org.towercontroller.model.TypeFilterConnectionFilter;
import id.towercontroller.org.towercontroller.model.TypeFilterSiteFilter;
import id.towercontroller.org.towercontroller.model.TypeFilterTowerOwner;
import id.towercontroller.org.towercontroller.model.TypeFilterTowerOwnerType;
import id.towercontroller.org.towercontroller.model.TypeFilterTowerStatus;
import id.towercontroller.org.towercontroller.model.TypeFilterTowerTypeFilter;
import id.towercontroller.org.towercontroller.model.User;
import id.towercontroller.org.towercontroller.service.LocationService;
import id.towercontroller.org.towercontroller.tools.SessionManager;
import id.towercontroller.org.towercontroller.tools.TowerControllerSessionManager;

public class LaporanActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    public static String TAG = "[LaporanActivity]";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.keepCenter)
    FloatingActionButton fab;
    @BindView(R.id.btnFilter)
    FloatingActionButton btnGoToAssest;
    @BindView(R.id.btnNext)
    FloatingActionButton btnNext;
    @BindView(R.id.selectedInfo)
    LinearLayout selectedInfoMenara;
    @BindView(R.id.namaMenara)
    TextView namaMenara;
    @BindView(R.id.tinggiLebarMenara)
    TextView tinggiLebarMenara;
    @BindView(R.id.alamatMenara)
    TextView alamatMenara;
    @BindView(R.id.luasTanahMenara)
    TextView luasTanahMenara;
    @BindView(R.id.groupFilter)
    FloatingActionButton filterGroup;

    GoogleMap googleMap;
    Gson gson = new Gson();

    private IntentFilter mIntentFilter;
    private Intent serviceIntent;
    private boolean isFirstSetMap = true;
    private boolean zoomToMyLoc = false;

    private LatLng myLocation;
    private Gson g = new Gson();
    private SweetAlertDialog progressDialog;
    private Marker currentLocationMarker;

    private AlertDialog mapItems;

    private ArrayList<ItemMarker> listOfMarker = new ArrayList<>();

    private MapFilter mapFilter = new MapFilter();
    private MapFilter selectedFilter = new MapFilter();

    private RecyclerView recyclerViewTypeFilterConnectionFilter;
    private CheckBoxItemTypeFilterConnectionFilter checkBoxItemTypeFilterConnectionFilter;

    private RecyclerView recyclerViewTypeFilterTowerOwner;
    private CheckBoxItemTypeFilterTowerOwner checkBoxItemTypeFilterTowerOwner;

    private RecyclerView recyclerViewTypeFilterTowerOwnerType;
    private CheckBoxItemTypeFilterTowerOwnerType checkBoxItemTypeFilterTowerOwnerType;

    private RecyclerView recyclerViewTypeFilterTowerStatus;
    private CheckBoxItemTypeFilterTowerStatus checkBoxItemTypeFilterTowerStatus;

    private RecyclerView recyclerViewTypeFilterTowerTypeFilter;
    private CheckBoxItemTypeFilterTowerTypeFilter checkBoxItemTypeFilterTowerTypeFilter;

    private RecyclerView recyclerViewTypeSites;
    private CheckBoxItemTypeSiteAdapter typeSiteAdapter;

    private TowerControllerSessionManager towerControllerSessionManager;

    private User user = new User();

    private void initMapFilter() {
        RestAPIControl.get(Constants.BASE_FILTER_OPTION, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                mapFilter = gson.fromJson(response.toString(), MapFilter.class);
                buildItemChooserDialog();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                throwable.printStackTrace();
                //Log.e(TAG, responseString);
                Toast.makeText(getApplicationContext(), "Gagal memuat filter!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                throwable.printStackTrace();
                //Log.e(TAG, errorResponse.toString());
                Toast.makeText(getApplicationContext(), "Gagal memuat filter!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                throwable.printStackTrace();
                //Log.e(TAG, errorResponse.toString());
                Toast.makeText(getApplicationContext(), "Gagal memuat filter!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void dismissAllMarker() {
        for (int i = 0; i < listOfMarker.size(); i++) {
            listOfMarker.get(i).getMarker().remove();
        }
        listOfMarker.clear();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan);
        ButterKnife.bind(this);

        towerControllerSessionManager = new TowerControllerSessionManager(this, getApplicationContext());
        user = towerControllerSessionManager.getUserDetail();
        /*Init Filter Array*/
        initMapFilter();

        initilizeMap();

        setSupportActionBar(toolbar);
        selectedInfoMenara.setVisibility(View.GONE);
        initProgressDialog();

        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(Constants.actionPosition);

        serviceIntent = new Intent(this, LocationService.class);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zoomToMyLoc = true;
                showProgressDialog();
            }
        });

        filterGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectGroup();
            }
        });

        btnGoToAssest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mapItems != null) mapItems.show();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextAction();
            }
        });

        startService(serviceIntent);
    }

    @Override
    protected void onResume() {
        registerReceiver(mReceiver, mIntentFilter);
        super.onResume();
    }

    private void buildItemChooserDialog() {
        if (mapItems == null) {
            AlertDialog.Builder builder;
            builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            View checkBoxView = inflater.inflate(R.layout.map_marker_filter_dialog, null);

            recyclerViewTypeSites = (RecyclerView) checkBoxView.findViewById(R.id.typeSite);
            typeSiteAdapter = new CheckBoxItemTypeSiteAdapter(LaporanActivity.this, mapFilter.getSiteFilters());
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(builder.getContext());
            recyclerViewTypeSites.setLayoutManager(mLayoutManager);
            recyclerViewTypeSites.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
            recyclerViewTypeSites.setItemAnimator(new DefaultItemAnimator());
            recyclerViewTypeSites.setAdapter(typeSiteAdapter);
            typeSiteAdapter.notifyDataSetChanged();

            recyclerViewTypeFilterConnectionFilter = (RecyclerView) checkBoxView.findViewById(R.id.typeFilterConnectionFilter);
            checkBoxItemTypeFilterConnectionFilter = new CheckBoxItemTypeFilterConnectionFilter(LaporanActivity.this, mapFilter.getConnectionFilters());
            RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(builder.getContext());
            recyclerViewTypeFilterConnectionFilter.setLayoutManager(mLayoutManager1);
            recyclerViewTypeFilterConnectionFilter.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
            recyclerViewTypeFilterConnectionFilter.setItemAnimator(new DefaultItemAnimator());
            recyclerViewTypeFilterConnectionFilter.setAdapter(checkBoxItemTypeFilterConnectionFilter);
            checkBoxItemTypeFilterConnectionFilter.notifyDataSetChanged();

            recyclerViewTypeFilterTowerOwner = (RecyclerView) checkBoxView.findViewById(R.id.typeFilterTowerOwner);
            checkBoxItemTypeFilterTowerOwner = new CheckBoxItemTypeFilterTowerOwner(LaporanActivity.this, mapFilter.getTowerOwners());
            RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(builder.getContext());
            recyclerViewTypeFilterTowerOwner.setLayoutManager(mLayoutManager2);
            recyclerViewTypeFilterTowerOwner.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
            recyclerViewTypeFilterTowerOwner.setItemAnimator(new DefaultItemAnimator());
            recyclerViewTypeFilterTowerOwner.setAdapter(checkBoxItemTypeFilterTowerOwner);
            checkBoxItemTypeFilterTowerOwner.notifyDataSetChanged();

            recyclerViewTypeFilterTowerOwnerType = (RecyclerView) checkBoxView.findViewById(R.id.typeFilterTowerOwnerType);
            checkBoxItemTypeFilterTowerOwnerType = new CheckBoxItemTypeFilterTowerOwnerType(LaporanActivity.this, mapFilter.getTowerOwnerTypes());
            RecyclerView.LayoutManager mLayoutManager3 = new LinearLayoutManager(builder.getContext());
            recyclerViewTypeFilterTowerOwnerType.setLayoutManager(mLayoutManager3);
            recyclerViewTypeFilterTowerOwnerType.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
            recyclerViewTypeFilterTowerOwnerType.setItemAnimator(new DefaultItemAnimator());
            recyclerViewTypeFilterTowerOwnerType.setAdapter(checkBoxItemTypeFilterTowerOwnerType);
            checkBoxItemTypeFilterTowerOwnerType.notifyDataSetChanged();

            recyclerViewTypeFilterTowerStatus = (RecyclerView) checkBoxView.findViewById(R.id.typeTowerStatus);
            checkBoxItemTypeFilterTowerStatus = new CheckBoxItemTypeFilterTowerStatus(LaporanActivity.this, mapFilter.getTowerStatuses());
            RecyclerView.LayoutManager mLayoutManager4 = new LinearLayoutManager(builder.getContext());
            recyclerViewTypeFilterTowerStatus.setLayoutManager(mLayoutManager4);
            recyclerViewTypeFilterTowerStatus.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
            recyclerViewTypeFilterTowerStatus.setItemAnimator(new DefaultItemAnimator());
            recyclerViewTypeFilterTowerStatus.setAdapter(checkBoxItemTypeFilterTowerStatus);
            checkBoxItemTypeFilterTowerStatus.notifyDataSetChanged();

            recyclerViewTypeFilterTowerTypeFilter = (RecyclerView) checkBoxView.findViewById(R.id.typeTowerFilter);
            checkBoxItemTypeFilterTowerTypeFilter = new CheckBoxItemTypeFilterTowerTypeFilter(LaporanActivity.this, mapFilter.getTowerTypeFilters());
            RecyclerView.LayoutManager mLayoutManager5 = new LinearLayoutManager(builder.getContext());
            recyclerViewTypeFilterTowerTypeFilter.setLayoutManager(mLayoutManager5);
            recyclerViewTypeFilterTowerTypeFilter.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
            recyclerViewTypeFilterTowerTypeFilter.setItemAnimator(new DefaultItemAnimator());
            recyclerViewTypeFilterTowerTypeFilter.setAdapter(checkBoxItemTypeFilterTowerTypeFilter);
            checkBoxItemTypeFilterTowerTypeFilter.notifyDataSetChanged();

            Button okButton = (Button) checkBoxView.findViewById(R.id.okButton);
            okButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkBoxOnAction();
                }
            });

            builder.setView(checkBoxView);
            mapItems = builder.create();
        }
    }

    private void onSelectGroup() {
        dismissAllMarker();
        mapItems.dismiss();
        RestAPIControl.get(Constants.BASE_MENARA_FILTER_USERGROUP + user.getGroupId(), null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    String message = response.getString("messages");
                    if (message.equals("data ditemukan")) {
                        dismissAllMarker();

                        JSONArray arrObject = response.getJSONArray("data");
                        for (int i = 0; i < arrObject.length(); i++) {
                            JSONObject itemexample = arrObject.getJSONObject(i);
                            ItemExample item = gson.fromJson(itemexample.toString(), ItemExample.class);
                            Log.i(TAG, item.toString());
                            if (item.getLongitude() != null && item.getLongitude() != null) {
                                ItemMarker marker = new ItemMarker(googleMap, new MarkerOptions().position(new LatLng(item.getLatitude(), item.getLongitude())).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_map_tower)), item);
                                marker.getMarker();
                                listOfMarker.add(marker);
                            }
                            Log.i(TAG, item.toString());
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(getApplicationContext(), "Server/Jaringan sedang bermasalah!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(getApplicationContext(), "Server/Jaringan sedang bermasalah!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Toast.makeText(getApplicationContext(), "Server/Jaringan sedang bermasalah!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void checkBoxOnAction() {
        dismissAllMarker();
        mapItems.dismiss();
        selectedFilter.setConnectionFilters(checkBoxItemTypeFilterConnectionFilter.getCheckedState());
        selectedFilter.setSiteFilters(typeSiteAdapter.getCheckedState());
        selectedFilter.setTowerOwners(checkBoxItemTypeFilterTowerOwner.getCheckedState());
        selectedFilter.setTowerStatuses(checkBoxItemTypeFilterTowerStatus.getCheckedState());
        selectedFilter.setTowerOwnerTypes(checkBoxItemTypeFilterTowerOwnerType.getCheckedState());
        selectedFilter.setTowerTypeFilters(checkBoxItemTypeFilterTowerTypeFilter.getCheckedState());
        String sentParam = selectedFilter.getParam();
        RestAPIControl.get(Constants.BASE_MENARA_FILTER + sentParam, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    String message = response.getString("messages");
                    if (message.equals("data ditemukan")) {
                        dismissAllMarker();

                        JSONArray arrObject = response.getJSONArray("data");
                        for (int i = 0; i < arrObject.length(); i++) {
                            JSONObject itemexample = arrObject.getJSONObject(i);
                            ItemExample item = gson.fromJson(itemexample.toString(), ItemExample.class);
                            if (item.getLongitude() != null && item.getLongitude() != null) {
                                ItemMarker marker = new ItemMarker(googleMap, new MarkerOptions().position(new LatLng(item.getLatitude(), item.getLongitude())).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_map_tower)), item);
                                marker.getMarker();
                                listOfMarker.add(marker);
                            }
                            Log.i(TAG, item.toString());
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(getApplicationContext(), "Server/Jaringan sedang bermasalah!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(getApplicationContext(), "Server/Jaringan sedang bermasalah!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Toast.makeText(getApplicationContext(), "Server/Jaringan sedang bermasalah!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (isFirstSetMap) {
                myLocation = new LatLng(intent.getDoubleExtra("latitude", 0), intent.getDoubleExtra("longitude", 0));
                //animateMarker(currentLocationMarker, myLocation, false);
                currentLocationMarker = googleMap.addMarker(new MarkerOptions().draggable(false).position(myLocation).title("Set My Location"));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));
                googleMap.animateCamera(CameraUpdateFactory.zoomTo(18), 2000, null);
                currentLocationMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_myloc));
                isFirstSetMap = false;
                user.setLatitude(myLocation.latitude);
                user.setLongitude(myLocation.longitude);
            }

            if (intent.getAction().equals(Constants.actionPosition)) {
                if (zoomToMyLoc) {
                    myLocation = new LatLng(intent.getDoubleExtra("latitude", 0), intent.getDoubleExtra("longitude", 0));
                    animateMarker(currentLocationMarker, myLocation, false);
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));
                    googleMap.animateCamera(CameraUpdateFactory.zoomTo(14), 2000, null);
                    zoomToMyLoc = false;
                    user.setLatitude(myLocation.latitude);
                    user.setLongitude(myLocation.longitude);
                    dissmissProgressDialog();
                }
            }
        }
    };


    public void initProgressDialog() {
        progressDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        progressDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        progressDialog.setTitleText("Loading...");
        progressDialog.setContentText("Sedang menunggu update lokasi GPS!");
        progressDialog.setCanceledOnTouchOutside(true);
    }

    public void showProgressDialog() {
        initProgressDialog();
        progressDialog.show();
    }

    public void dissmissProgressDialog() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        SubmitInfo.tower = null;
        super.onBackPressed();
        finish();
    }

    public void nextAction() {
        if (SubmitInfo.tower != null) {
            SubmitInfo.user = user;
            Intent intent = new Intent(this, DetailLaporanActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Pilih item menara yang akan dilaporkan terlebih dahulu!", Toast.LENGTH_LONG).show();
        }
    }

    private void initilizeMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);
    }


    @Override
    protected void onPause() {
        unregisterReceiver(mReceiver);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        stopService(serviceIntent);
        super.onDestroy();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        this.googleMap.setOnMarkerClickListener(this);
    }

    public void animateMarker(final Marker marker, final LatLng toPosition,
                              final boolean hideMarker) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        Projection proj = googleMap.getProjection();
        Point startPoint = proj.toScreenLocation(marker.getPosition());
        final LatLng startLatLng = proj.fromScreenLocation(startPoint);
        final long duration = 500;

        final Interpolator interpolator = new LinearInterpolator();

        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed
                        / duration);
                double lng = t * toPosition.longitude + (1 - t)
                        * startLatLng.longitude;
                double lat = t * toPosition.latitude + (1 - t)
                        * startLatLng.latitude;
                marker.setPosition(new LatLng(lat, lng));

                if (t < 1.0) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16);
                } else {
                    if (hideMarker) {
                        marker.setVisible(false);
                    } else {
                        marker.setVisible(true);
                    }
                }
            }
        });
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (marker.equals(currentLocationMarker)) {
            return false;
        }
        ItemExample selectedObject = (ItemExample) marker.getTag();
        Toast.makeText(getApplicationContext(), selectedObject.getNamaMenara(), Toast.LENGTH_LONG).show();
        if (!selectedObject.isVisibleInfo()) {
            selectedInfoMenara.setVisibility(View.VISIBLE);
            SubmitInfo.tower = selectedObject;
            namaMenara.setText(selectedObject.getNamaMenara());
            tinggiLebarMenara.setText("Tinggi Menara : " + selectedObject.getTinggiMenara() + ", Lebar Menara : " + selectedObject.getLebarMenara());
            alamatMenara.setText("Alamat : " + selectedObject.getAlamatMenara());
            luasTanahMenara.setText("Luas Tanah Menara : " + selectedObject.getLuasTanahMenara());
            selectedObject.setVisibleInfo(true);
        } else {
            SubmitInfo.tower = null;
            selectedInfoMenara.setVisibility(View.GONE);
            selectedObject.setVisibleInfo(false);
        }
        return true;
    }
}
