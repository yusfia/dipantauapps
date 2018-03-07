package id.towercontroller.org.towercontroller.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hafid on 12/11/2017.
 */

public class TypeFilterTowerStatus extends TypeFilter{
    @SerializedName("statusMenaraId")
    private long statusMenaraId;
    @SerializedName("statusMenara")
    private String statusMenara;

    public long getStatusMenaraId() {
        return statusMenaraId;
    }

    public void setStatusMenaraId(long statusMenaraId) {
        this.statusMenaraId = statusMenaraId;
    }

    public String getStatusMenara() {
        return statusMenara;
    }

    public void setStatusMenara(String statusMenara) {
        this.statusMenara = statusMenara;
    }
}
