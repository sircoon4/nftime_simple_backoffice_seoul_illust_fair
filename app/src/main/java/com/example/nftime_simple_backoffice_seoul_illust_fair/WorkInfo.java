package com.example.nftime_simple_backoffice_seoul_illust_fair;

public class WorkInfo {
    private int workId;
    private String workName;
    private String artistName;

    public WorkInfo(int workId, String workName, String artistName) {
        this.workId = workId;
        this.workName = workName;
        this.artistName = artistName;
    }

    public int getWorkId() {
        return workId;
    }

    public void setWorkId(int workId) {
        this.workId = workId;
    }

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }
}
