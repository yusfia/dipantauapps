package id.towercontroller.org.towercontroller.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hafid on 12/10/2017.
 */

public class TypeFilterSiteFilter extends TypeFilter {

    @SerializedName("tipeSiteId")
    private long tipeSiteId;

    @SerializedName("tipeSite")
    private String itemName;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public long getTipeSiteId() {
        return tipeSiteId;
    }

    public void setTipeSiteId(long tipeSiteId) {
        this.tipeSiteId = tipeSiteId;
    }
}
