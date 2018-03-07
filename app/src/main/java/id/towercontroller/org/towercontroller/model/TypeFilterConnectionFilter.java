package id.towercontroller.org.towercontroller.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hafid on 12/11/2017.
 */

public class TypeFilterConnectionFilter extends TypeFilter {
    @SerializedName("tipeJaringanMenaraId")
    private long tipeJaringanMenaraId;
    @SerializedName("tipeJaringanMenara")
    private String tipeJaringanMenara;

    public long getTipeJaringanMenaraId() {
        return tipeJaringanMenaraId;
    }

    public void setTipeJaringanMenaraId(long tipeJaringanMenaraId) {
        this.tipeJaringanMenaraId = tipeJaringanMenaraId;
    }

    public String getTipeJaringanMenara() {
        return tipeJaringanMenara;
    }

    public void setTipeJaringanMenara(String tipeJaringanMenara) {
        this.tipeJaringanMenara = tipeJaringanMenara;
    }

}
