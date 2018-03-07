package id.towercontroller.org.towercontroller.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hafid on 12/11/2017.
 */

public class MapFilter {
    @SerializedName("dataTipeJaringan")
    private List<TypeFilterConnectionFilter> connectionFilters = new ArrayList<>();
    @SerializedName("dataKepemilikanMenara")
    private List<TypeFilterTowerOwner> towerOwners = new ArrayList<>();
    @SerializedName("dataTipeSite")
    private List<TypeFilterSiteFilter> siteFilters = new ArrayList<>();
    @SerializedName("dataTipeMenara")
    private List<TypeFilterTowerTypeFilter> towerTypeFilters = new ArrayList<>();
    @SerializedName("dataTipePemilikMenara")
    private List<TypeFilterTowerOwnerType> towerOwnerTypes = new ArrayList<>();
    @SerializedName("dataStatusMenara")
    private List<TypeFilterTowerStatus> towerStatuses = new ArrayList<>();

    public List<TypeFilterConnectionFilter> getConnectionFilters() {
        return connectionFilters;
    }

    public void setConnectionFilters(List<TypeFilterConnectionFilter> connectionFilters) {
        this.connectionFilters = connectionFilters;
    }

    public List<TypeFilterTowerOwner> getTowerOwners() {
        return towerOwners;
    }

    public void setTowerOwners(List<TypeFilterTowerOwner> towerOwners) {
        this.towerOwners = towerOwners;
    }

    public List<TypeFilterSiteFilter> getSiteFilters() {
        return siteFilters;
    }

    public void setSiteFilters(List<TypeFilterSiteFilter> siteFilters) {
        this.siteFilters = siteFilters;
    }

    public List<TypeFilterTowerTypeFilter> getTowerTypeFilters() {
        return towerTypeFilters;
    }

    public void setTowerTypeFilters(List<TypeFilterTowerTypeFilter> towerTypeFilters) {
        this.towerTypeFilters = towerTypeFilters;
    }

    public List<TypeFilterTowerOwnerType> getTowerOwnerTypes() {
        return towerOwnerTypes;
    }

    public void setTowerOwnerTypes(List<TypeFilterTowerOwnerType> towerOwnerTypes) {
        this.towerOwnerTypes = towerOwnerTypes;
    }

    public List<TypeFilterTowerStatus> getTowerStatuses() {
        return towerStatuses;
    }

    public void setTowerStatuses(List<TypeFilterTowerStatus> towerStatuses) {
        this.towerStatuses = towerStatuses;
    }

    public String getParam() {
        String param = "";
        if (siteFilters.size() > 0) {
            param = param + "datatipesite=";
            for (int i = 0; i < siteFilters.size(); i++) {
                param = param + siteFilters.get(i).getTipeSiteId();
                if (i != siteFilters.size() - 1) {
                    param = param + ",";
                }
            }
        }
        if (connectionFilters.size() > 0) {
            param = param + "&datatipejaringan=";
            for (int i = 0; i < connectionFilters.size(); i++) {
                param = param + connectionFilters.get(i).getTipeJaringanMenaraId();
                if (i != connectionFilters.size() - 1) {
                    param = param + ",";
                }
            }
        }
        if (towerTypeFilters.size() > 0) {
            param = param + "&datatipemenara=";
            for (int i = 0; i < towerTypeFilters.size(); i++) {
                param = param + towerTypeFilters.get(i).getTipeMenaraId();
                if (i != towerTypeFilters.size() - 1) {
                    param = param + ",";
                }
            }
        }
        if (towerOwners.size() > 0){
            param = param + "&datakepemilikanmenara=";
            for (int i = 0; i < towerOwners.size(); i++) {
                param = param + towerOwners.get(i).getKepemilikanMenaraId();
                if (i != towerOwners.size() - 1) {
                    param = param + ",";
                }
            }
        }
        if (towerOwnerTypes.size() > 0){
            param = param + "&datatipepemilikmenara=";
            for (int i = 0; i < towerOwnerTypes.size(); i++) {
                param = param + towerOwnerTypes.get(i).getTipePemilikId();
                if (i != towerOwnerTypes.size() - 1) {
                    param = param + ",";
                }
            }
        }
        if (towerStatuses.size() > 0){
            param = param + "&datastatusmenara=";
            for (int i = 0; i < towerStatuses.size(); i++) {
                param = param + towerStatuses.get(i).getStatusMenaraId();
                if (i != towerStatuses.size() - 1) {
                    param = param + ",";
                }
            }
        }
        return param;
    }
}
