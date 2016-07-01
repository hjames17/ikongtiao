package com.wetrack.ikongtiao.domain;

import lombok.Data;

/**
 * Created by zhanghong on 16/4/28.
 */
@Data
public class AppVersion {

    Integer id;
    String platform;
    String version;
    String releaseNote;
    String url;
    boolean released;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getReleaseNote() {
        return releaseNote;
    }

    public void setReleaseNote(String releaseNote) {
        this.releaseNote = releaseNote;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
