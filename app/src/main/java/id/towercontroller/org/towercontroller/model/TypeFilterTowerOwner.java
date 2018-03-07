package id.towercontroller.org.towercontroller.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hafid on 12/11/2017.
 */

public class TypeFilterTowerOwner extends TypeFilter {
    @SerializedName("kepemilikanMenaraId")
    private long kepemilikanMenaraId;
    @SerializedName("nama_provider")
    private String nama_provider;

    public long getKepemilikanMenaraId() {
        return kepemilikanMenaraId;
    }

    public void setKepemilikanMenaraId(long kepemilikanMenaraId) {
        this.kepemilikanMenaraId = kepemilikanMenaraId;
    }

    public String getNama_provider() {
        return nama_provider;
    }

    public void setNama_provider(String nama_provider) {
        this.nama_provider = nama_provider;
    }
}
