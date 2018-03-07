package id.towercontroller.org.towercontroller.model;

import com.google.gson.annotations.SerializedName;

import id.towercontroller.org.towercontroller.config.Constants;

/**
 * Created by Hafid on 3/7/2018.
 */

public class ReportResult {
    @SerializedName("laporanId")
    private Long laporanId;
    @SerializedName("userId")
    private Long userId;
    @SerializedName("fotoMenaraLaporanPath")
    private String fotoMenaraLaporanPath;
    @SerializedName("longitudeMenaraLaporan")
    private Double longitudeMenaraLaporan;
    @SerializedName("latitudeMenaraLaporan")
    private Double latitudeMenaraLaporan;
    @SerializedName("menaraId")
    private Long menaraId;
    @SerializedName("laporanDescription")
    private String laporanDescription;
    @SerializedName("tanggalLaporan")
    private String tanggalLaporan;

    public Long getLaporanId() {
        return laporanId;
    }

    public void setLaporanId(Long laporanId) {
        this.laporanId = laporanId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFotoMenaraLaporanPath() {
        return Constants.BASE_URL + fotoMenaraLaporanPath;
    }

    public void setFotoMenaraLaporanPath(String fotoMenaraLaporanPath) {
        this.fotoMenaraLaporanPath = fotoMenaraLaporanPath;
    }

    public Double getLongitudeMenaraLaporan() {
        return longitudeMenaraLaporan;
    }

    public void setLongitudeMenaraLaporan(Double longitudeMenaraLaporan) {
        this.longitudeMenaraLaporan = longitudeMenaraLaporan;
    }

    public Double getLatitudeMenaraLaporan() {
        return latitudeMenaraLaporan;
    }

    public void setLatitudeMenaraLaporan(Double latitudeMenaraLaporan) {
        this.latitudeMenaraLaporan = latitudeMenaraLaporan;
    }

    public Long getMenaraId() {
        return menaraId;
    }

    public void setMenaraId(Long menaraId) {
        this.menaraId = menaraId;
    }

    public String getLaporanDescription() {
        return laporanDescription;
    }

    public void setLaporanDescription(String laporanDescription) {
        this.laporanDescription = laporanDescription;
    }

    public String getTanggalLaporan() {
        return tanggalLaporan;
    }

    public void setTanggalLaporan(String tanggalLaporan) {
        this.tanggalLaporan = tanggalLaporan;
    }
}
