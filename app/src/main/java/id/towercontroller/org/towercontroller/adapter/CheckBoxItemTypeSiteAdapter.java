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
import id.towercontroller.org.towercontroller.model.TypeFilterSiteFilter;

/**
 * Created by Hafid on 12/10/2017.
 */

public class CheckBoxItemTypeSiteAdapter extends RecyclerView.Adapter<CheckBoxItemTypeSiteAdapter.ViewHolder> {

    private List<TypeFilterSiteFilter> typeSiteFilters;
    private Activity activity;

    public CheckBoxItemTypeSiteAdapter(Activity activity, ArrayList<TypeFilterSiteFilter> typeSiteFilters){
        this.activity = activity;
        this.typeSiteFilters = typeSiteFilters;
    }

    public CheckBoxItemTypeSiteAdapter(Activity activity, List<TypeFilterSiteFilter> typeSiteFilters){
        this.activity = activity;
        this.typeSiteFilters = typeSiteFilters;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_checkbox_filter, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final TypeFilterSiteFilter typeSiteFilter = typeSiteFilters.get(position);
        holder.cbSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeSiteFilter.setSelected(!typeSiteFilter.isSelected());
                holder.cbSelect.setChecked(typeSiteFilter.isSelected());
            }
        });
        holder.textItem.setText(typeSiteFilter.getItemName());
    }

    @Override
    public int getItemCount() {
        if (typeSiteFilters!=null) {
            return typeSiteFilters.size();
        } else {
            return 0;
        }
    }

    public TypeFilterSiteFilter getItem(int position){
        if (position < typeSiteFilters.size()){
            return typeSiteFilters.get(position);
        } else {
            return null;
        }
    }

    public ArrayList<TypeFilterSiteFilter> getCheckedState(){
        ArrayList<TypeFilterSiteFilter> list = new ArrayList<>();
        for (int i = 0; i < typeSiteFilters.size(); i++){
            if (typeSiteFilters.get(i).isSelected()){
                list.add(typeSiteFilters.get(i));
            }
        }
        return list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
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
