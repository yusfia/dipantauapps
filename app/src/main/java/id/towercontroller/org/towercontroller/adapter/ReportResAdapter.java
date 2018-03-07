package id.towercontroller.org.towercontroller.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.towercontroller.org.towercontroller.R;
import id.towercontroller.org.towercontroller.model.ReportResult;

/**
 * Created by Hafid on 3/7/2018.
 */

public class ReportResAdapter extends RecyclerView.Adapter<ReportResAdapter.ViewHolder> {

    private List<ReportResult> reportResults;
    private Activity activity;

    public ReportResAdapter(Activity activity, List<ReportResult> reportResults) {
        this.activity = activity;
        this.reportResults = reportResults;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_report_result, parent, false);
        return new ReportResAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ReportResult res = reportResults.get(position);
        holder.dateOfReport.setText(res.getTanggalLaporan());
        holder.description.setText(res.getLaporanDescription());
        holder.position.setText("Koordinat Lokasi(" + res.getLatitudeMenaraLaporan() + "," + res.getLongitudeMenaraLaporan() + ")");
        Glide.with(activity)
                .load(res.getFotoMenaraLaporanPath())
                .into(holder.photoPic);
    }

    @Override
    public int getItemCount() {
        return reportResults.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.dateOfReport)
        public TextView dateOfReport;
        @BindView(R.id.description)
        public TextView description;
        @BindView(R.id.position)
        public TextView position;
        @BindView(R.id.photoPic)
        public ImageView photoPic;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

}
