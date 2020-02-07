package com.lotus.appupdater;

public class AppUpdatemodal {
    private String version = "0.0.0.0";
    private Integer versionCode;
    private String releaseNotes;
    private String apk_download_link;
    private Boolean isForcefully;

    public AppUpdatemodal() {
    }

    public AppUpdatemodal(String latestVersion, Integer latestVersionCode, Boolean isForcefully,
                          String releaseNotes, String apk_download_link) {
        this.version = latestVersion;
        this.versionCode = latestVersionCode;
        this.isForcefully = isForcefully;
        this.releaseNotes = releaseNotes;
        this.apk_download_link = apk_download_link;
    }


    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(Integer versionCode) {
        this.versionCode = versionCode;
    }

    public String getReleaseNotes() {
        return releaseNotes;
    }

    public void setReleaseNotes(String releaseNotes) {
        this.releaseNotes = releaseNotes;
    }

    public boolean isForcefully() {
        return isForcefully;
    }

    public void setForcefully(boolean forcefully) {
        isForcefully = forcefully;
    }

    public String getApk_download_link() {
        return apk_download_link;
    }

    public void setApk_download_link(String apk_download_link) {
        this.apk_download_link = apk_download_link;
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        if (version != null) {
            stringBuffer.append("Version=" + version);
        }
        if (versionCode != null) {
            stringBuffer.append("Versioncode=" + String.valueOf(versionCode));
        }
        if (isForcefully != null) {
            stringBuffer.append("IsForcefully=" + String.valueOf(isForcefully));
        }
        if (releaseNotes != null) {
            stringBuffer.append("ReleaseNotes=" + releaseNotes);
        }
        if (apk_download_link != null) {
            stringBuffer.append("apk_download_link=" + apk_download_link);
        }

        return stringBuffer.toString();
    }
}
