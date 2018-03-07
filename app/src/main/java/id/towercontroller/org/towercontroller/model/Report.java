package id.towercontroller.org.towercontroller.model;

import java.io.File;

/**
 * Created by Hafid on 12/10/2017.
 */

public class Report {

    private File photo;
    private String fileName;
    private String description;

    public File getPhoto() {
        return photo;
    }

    public void setPhoto(File photo) {
        this.photo = photo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
