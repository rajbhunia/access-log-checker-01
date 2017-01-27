package com.infosys.jira.plugins.helper;

/**
 * Created by Raj on 22/01/2017.
 */
public enum Browsers {

    CHROME("Chrome"),
    MSIE("Internet Explorer"),
    SAFARI("Safari"),
    FIREFOX("Firefox"),
    UNKNOWN("Unknown");

    private String name;

    public String getName() {
        return name;
    }

    Browsers(String name) {
        this.name = name;
    }
}
