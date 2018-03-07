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
import id.towercontroller.org.towercontroller.model.TypeFilterTowerOwner;

/**
 * Created by Hafid on 12/11/2017.
 */

public class CheckBoxItemTypeFilterTowerOwner extends RecyclerView.Adapter<CheckBoxItemTypeFilterTowerOwner.ViewHolder> {

    private List<TypeFilterTowerOwner> type;
    private Activity activity;

    public CheckBoxItemTypeFilterTowerOwner(Activity activity, ArrayList<TypeFilterTowerOwner> type) {
        this.activity = activity;
        this.type = type;
    }

    public CheckBoxItemTypeFilterTowerOwner(Activity activity, List<TypeFilterTowerOwner> type) {
        this.activity = activity;
        this.type = type;
    }

    @Override
    public CheckBoxItemTypeFilterTowerOwner.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_checkbox_filter, parent, false);
        return new CheckBoxItemTypeFilterTowerOwner.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CheckBoxItemTypeFilterTowerOwner.ViewHolder holder, int position) {
        final TypeFilterTowerOwner typeSiteFilter = type.get(position);
        holder.cbSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeSiteFilter.setSelected(!typeSiteFilter.isSelected());
                holder.cbSelect.setChecked(typeSiteFilter.isSelected());
            }
        });
        holder.textItem.setText(typeSiteFilter.getNama_provider());
    }

    @Override
    public int getItemCount() {
        if (type != null) {
            return type.size();
        } else {
            return 0;
        }
    }

    public TypeFilterTowerOwner getItem(int position) {
        if (position < type.size()) {
            return type.get(position);
        } else {
            return null;
        }
    }

    public ArrayList<TypeFilterTowerOwner> getCheckedState() {
        ArrayList<TypeFilterTowerOwner> list = new ArrayList<>();
        for (int i = 0; i < type.size(); i++) {
            if (type.get(i).isSelected()) {
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
            ButterKnife.bind(this, v);
        }
    }
}