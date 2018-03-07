package id.towercontroller.org.towercontroller.model;

/**
 * Created by Hafid on 11/28/2017.
 */

import com.google.gson.annotations.SerializedName;

public class Jadwal {
    private Long id;
    @SerializedName("userId")
    private Long userId;
    @SerializedName("userGroupName")
    private String userGroupName;
    @SerializedName("userGroupSurveyNumber")
    private Integer userGroupSurveyNumber;
    @SerializedName("kodeMenara")
    private String kodeMenara;
    @SerializedName("namaMenara")
    private String namaMenara;
    @SerializedName("alamatMenara")
    private String alamatMenara;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserGroupName() {
        return userGroupName;
    }

    public void setUserGroupName(String userGroupName) {
        this.userGroupName = userGroupName;
    }

    public Integer getUserGroupSurveyNumber() {
        return userGroupSurveyNumber;
    }

    public void setUserGroupSurveyNumber(Integer userGroupSurveyNumber) {
        this.userGroupSurveyNumber = userGroupSurveyNumber;
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

    public String getAlamatMenara() {
        return alamatMenara;
    }

    public void setAlamatMenara(String alamatMenara) {
        this.alamatMenara = alamatMenara;
    }
}
