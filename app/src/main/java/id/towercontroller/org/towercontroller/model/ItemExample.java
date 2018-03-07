package id.towercontroller.org.towercontroller.model;

/**
 * Created by Hafid on 12/1/2017.
 */

import com.google.gson.annotations.SerializedName;

public class ItemExample {
    @SerializedName("menaraId")
    private Long id;
    @SerializedName("kodeMenara")
    private String kodeMenara;
    @SerializedName("namaMenara")
    private String namaMenara;
    @SerializedName("tinggiMenara")
    private Double tinggiMenara;
    @SerializedName("lebarMenara")
    private Double lebarMenara;
    @SerializedName("pathFotoMenara")
    private String pathFotoMenara;
    @SerializedName("alamatMenara")
    private String alamatMenara;
    @SerializedName("luasTanahMenara")
    private String luasTanahMenara;
    @SerializedName("sectoral")
    private String sectoral;
    @SerializedName("radioKurangDari")
    private String radioKurangDari;
    @SerializedName("radioLebihDari")
    private String radioLebihDari;
    @SerializedName("menaraIndor")
    private String menaraIndor;
    @SerializedName("menaraOutdoor")
    private String menaraOutdoor;
    @SerializedName("tipeJaringanMenaraId")
    private Long tipeJaringanMenaraId;
    @SerializedName("kepemilikanMenaraId")
    private Long kepemilikanMenaraId;
    @SerializedName("statusMenaraId")
    private Long statusMenaraId;
    @SerializedName("tipePemilikId")
    private Long tipePemilikId;
    @SerializedName("tipeMenaraId")
    private Long tipeMenaraId;
    @SerializedName("tipeSiteId")
    private String tipeSiteId;
    @SerializedName("latitudeMenara")
    private Double latitude;
    @SerializedName("longitudeMenara")
    private Double longitude;
    private TypeFilterSiteFilter category;
    private boolean visibleInfo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKodeMenara() {
        return kodeMenara;
    }

    public void setKodeMenara(String kodeMenara) {
        this.kodeMenara = kodeMenara;
    }

    public String getNamaMenara() {
        return namaMenara;
    }

    public void setNamaMenara(String namaMenara) {
        this.namaMenara = namaMenara;
    }

    public Double getTinggiMenara() {
        return tinggiMenara;
    }

    public void setTinggiMenara(Double tinggiMenara) {
        this.tinggiMenara = tinggiMenara;
    }

    public Double getLebarMenara() {
        return lebarMenara;
    }

    public void setLebarMenara(Double lebarMenara) {
        this.lebarMenara = lebarMenara;
    }

    public String getPathFotoMenara() {
        return pathFotoMenara;
    }

    public void setPathFotoMenara(String pathFotoMenara) {
        this.pathFotoMenara = pathFotoMenara;
    }

    public String getAlamatMenara() {
        return alamatMenara;
    }

    public void setAlamatMenara(String alamatMenara) {
        this.alamatMenara = alamatMenara;
    }

    public String getLuasTanahMenara() {
        return luasTanahMenara;
    }

    public void setLuasTanahMenara(String luasTanahMenara) {
        this.luasTanahMenara = luasTanahMenara;
    }

    public String getSectoral() {
        return sectoral;
    }

    public void setSectoral(String sectoral) {
        this.sectoral = sectoral;
    }

    public String getRadioKurangDari() {
        return radioKurangDari;
    }

    public void setRadioKurangDari(String radioKurangDari) {
        this.radioKurangDari = radioKurangDari;
    }

    public String getRadioLebihDari() {
        return radioLebihDari;
    }

    public void setRadioLebihDari(String radioLebihDari) {
        this.radioLebihDari = radioLebihDari;
    }

    public String getMenaraIndor() {
        return menaraIndor;
    }

    public void setMenaraIndor(String menaraIndor) {
        this.menaraIndor = menaraIndor;
    }

    public String getMenaraOutdoor() {
        return menaraOutdoor;
    }

    public void setMenaraOutdoor(String menaraOutdoor) {
        this.menaraOutdoor = menaraOutdoor;
    }

    public Long getTipeJaringanMenaraId() {
        return tipeJaringanMenaraId;
    }

    public void setTipeJaringanMenaraId(Long tipeJaringanMenaraId) {
        this.tipeJaringanMenaraId = tipeJaringanMenaraId;
    }

    public Long getKepemilikanMenaraId() {
        return kepemilikanMenaraId;
    }

    public void setKepemilikanMenaraId(Long kepemilikanMenaraId) {
        this.kepemilikanMenaraId = kepemilikanMenaraId;
    }

    public Long getStatusMenaraId() {
        return statusMenaraId;
    }

    public void setStatusMenaraId(Long statusMenaraId) {
        this.statusMenaraId = statusMenaraId;
    }

    public Long getTipePemilikId() {
        return tipePemilikId;
    }

    public void setTipePemilikId(Long tipePemilikId) {
        this.tipePemilikId = tipePemilikId;
    }

    public Long getTipeMenaraId() {
        return tipeMenaraId;
    }

    public void setTipeMenaraId(Long tipeMenaraId) {
        this.tipeMenaraId = tipeMenaraId;
    }

    public String getTipeSiteId() {
        return tipeSiteId;
    }

    public void setTipeSiteId(String tipeSiteId) {
        this.tipeSiteId = tipeSiteId;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public TypeFilterSiteFilter getCategory() {
        return category;
    }

    public void setCategory(TypeFilterSiteFilter category) {
        this.category = category;
    }

    public boolean isVisibleInfo() {
        return visibleInfo;
    }

    public void setVisibleInfo(boolean visibleInfo) {
        this.visibleInfo = visibleInfo;
    }

    @Override
    public String toString() {
        return "ItemExample{" +
                "id=" + id +
                ", kodeMenara='" + kodeMenara + '\'' +
                ", namaMenara='" + namaMenara + '\'' +
                ", tinggiMenara=" + tinggiMenara +
                ", lebarMenara=" + lebarMenara +
                ", pathFotoMenara='" + pathFotoMenara + '\'' +
                ", alamatMenara='" + alamatMenara + '\'' +
                ", luasTanahMenara='" + luasTanahMenara + '\'' +
                ", sectoral='" + sectoral + '\'' +
                ", radioKurangDari='" + radioKurangDari + '\'' +
                ", radioLebihDari='" + radioLebihDari + '\'' +
                ", menaraIndor='" + menaraIndor + '\'' +
                ", menaraOutdoor='" + menaraOutdoor + '\'' +
                ", tipeJaringanMenaraId=" + tipeJaringanMenaraId +
                ", kepemilikanMenaraId=" + kepemilikanMenaraId +
                ", statusMenaraId=" + statusMenaraId +
                ", tipePemilikId=" + tipePemilikId +
                ", tipeMenaraId=" + tipeMenaraId +
                ", tipeSiteId='" + tipeSiteId + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", category=" + category +
                ", visibleInfo=" + visibleInfo +
                '}';
    }
}
