package id.towercontroller.org.towercontroller.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hafid on 12/11/2017.
 */

public class TypeFilterTowerOwnerType extends TypeFilter{
    @SerializedName("tipePemilikId")
    private long tipePemilikId;
    @SerializedName("tipePemilik")
    private String tipePemilik;

    public long getTipePemilikId() {
        return tipePemilikId;
    }

    public void setTipePemilikId(long tipePemilikId) {
        this.tipePemilikId = tipePemilikId;
    }

    public String getTipePemilik() {
        return tipePemilik;
    }

    public void setTipePemilik(String tipePemilik) {
        this.tipePemilik = tipePemilik;
    }
}
