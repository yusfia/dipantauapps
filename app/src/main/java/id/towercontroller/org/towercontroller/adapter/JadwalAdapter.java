package id.towercontroller.org.towercontroller.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.towercontroller.org.towercontroller.R;
import id.towercontroller.org.towercontroller.model.Jadwal;

/**
 * Created by Hafid on 11/28/2017.
 */

public class JadwalAdapter extends RecyclerView.Adapter<JadwalAdapter.ViewHolder> {

    private List<Jadwal> jadwalList;
    private Activity activity;

    public JadwalAdapter(Activity activity, List<Jadwal> jadwalList) {
        this.activity = activity;
        this.jadwalList = jadwalList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_jadwal, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Jadwal jadwal = jadwalList.get(position);
        holder.usergroup.setText(jadwal.getUserGroupName());
        holder.detailMenara.setText("(Kode - " + jadwal.getKodeMenara() + ") " + jadwal.getNamaMenara());
        holder.alamatMenara.setText(jadwal.getAlamatMenara());
        holder.jumlahKunjungan.setText("Jumlah Minimal Kunjungan : "+jadwal.getUserGroupSurveyNumber());
    }

    @Override
    public int getItemCount() {
        if (jadwalList != null) {
            return jadwalList.size();
        } else {
            return 0;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.userGroup)
        public TextView usergroup;
        @BindView(R.id.detailMenara)
        public TextView detailMenara;
        @BindView(R.id.alamatMenara)
        public TextView alamatMenara;
        @BindView(R.id.jumlahKunjungan)
        public TextView jumlahKunjungan;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}
