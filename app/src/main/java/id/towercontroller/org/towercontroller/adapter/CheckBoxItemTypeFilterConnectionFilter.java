package id.towercontroller.org.towercontroller.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.towercontroller.org.towercontroller.R;
import id.towercontroller.org.towercontroller.model.TypeFilterConnectionFilter;
import id.towercontroller.org.towercontroller.model.TypeFilterSiteFilter;

/**
 * Created by Hafid on 12/11/2017.
 */

public class CheckBoxItemTypeFilterConnectionFilter extends RecyclerView.Adapter<CheckBoxItemTypeFilterConnectionFilter.ViewHolder> {

    private List<TypeFilterConnectionFilter> type;
    private Activity activity;

    public CheckBoxItemTypeFilterConnectionFilter(Activity activity, ArrayList<TypeFilterConnectionFilter> type){
        this.activity = activity;
        this.type = type;
    }

    public CheckBoxItemTypeFilterConnectionFilter(Activity activity, List<TypeFilterConnectionFilter> type){
        this.activity = activity;
        this.type = type;
    }

    @Override
    public CheckBoxItemTypeFilterConnectionFilter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_checkbox_filter, parent, false);
        return new CheckBoxItemTypeFilterConnectionFilter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CheckBoxItemTypeFilterConnectionFilter.ViewHolder holder, int position) {
        final TypeFilterConnectionFilter typeSiteFilter = type.get(position);
        holder.cbSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeSiteFilter.setSelected(!typeSiteFilter.isSelected());
                holder.cbSelect.setChecked(typeSiteFilter.isSelected());
            }
        });
        holder.textItem.setText(typeSiteFilter.getTipeJaringanMenara());
    }

    @Override
    public int getItemCount() {
        if (type!=null) {
            return type.size();
        } else {
            return 0;
        }
    }

    public TypeFilterConnectionFilter getItem(int position){
        if (position < type.size()){
            return type.get(position);
        } else {
            return null;
        }
    }

    public ArrayList<TypeFilterConnectionFilter> getCheckedState(){
        ArrayList<TypeFilterConnectionFilter> list = new ArrayList<>();
        for (int i = 0; i < type.size(); i++){
            if (type.get(i).isSelected()){
                list.add(type.get(i));
            }
        }
        return list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textItem)
        public TextView textItem;
        @BindView(R.id.checkItem)
        public CheckBox cbSelect;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this,v);
        }
    }
}