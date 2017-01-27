package com.infosys.jira.plugins.helper;

/**
 * Created by Raj on 22/01/2017.
 */
public class Browser {

    private String browserName;
    private String version;

    public Browser() {
        this.browserName = Browsers.UNKNOWN.getName();
        this.version = "";
    }

    public Browser(String browserName, String version) {
        this.browserName = browserName;
        this.version = version;
    }

    public String getBrowserName() {
        return browserName;
    }

    public void setBrowserName(String browserName) {
        this.browserName = browserName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return browserName + "/" + version;
    }
}
