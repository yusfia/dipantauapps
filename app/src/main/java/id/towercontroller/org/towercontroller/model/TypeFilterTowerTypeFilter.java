package id.towercontroller.org.towercontroller.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hafid on 12/11/2017.
 */

public class TypeFilterTowerTypeFilter extends TypeFilter {
    @SerializedName("tipeMenaraId")
    private long tipeMenaraId;
    @SerializedName("tipeMenara")
    private String tipeMenara;

    public long getTipeMenaraId() {
        return tipeMenaraId;
    }

    public void setTipeMenaraId(long tipeMenaraId) {
        this.tipeMenaraId = tipeMenaraId;
    }

    public String getTipeMenara() {
        return tipeMenara;
    }

    public void setTipeMenara(String tipeMenara) {
        this.tipeMenara = tipeMenara;
    }
}
