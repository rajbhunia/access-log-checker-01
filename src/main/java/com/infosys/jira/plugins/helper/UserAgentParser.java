package com.infosys.jira.plugins.helper;

/**
 * Created by Raj on 22/01/2017.
 */
public class UserAgentParser {

    public Browser getBrowser(final String userAgent) {
        Browser browser = new Browser();
        if (userAgent.contains(Browsers.SAFARI.getName()) && userAgent.contains("Version")) {
            browser.setBrowserName(Browsers.SAFARI.getName());
            browser.setVersion(userAgent.substring(userAgent.indexOf("Version")).split(" ")[0].split("/")[1]);
        } else if (userAgent.contains(Browsers.CHROME.getName())) {
            browser.setBrowserName(Browsers.CHROME.getName());
            browser.setVersion(userAgent.substring(userAgent.indexOf(Browsers.CHROME.getName())).split(" ")[0].split("/")[1]);
        } else if (userAgent.contains("rv:11")) {
            browser.setBrowserName(Browsers.MSIE.getName());
            browser.setVersion("11");
        } else if (userAgent.contains("MSIE")) {
            browser.setBrowserName(Browsers.MSIE.getName());
            browser.setVersion(userAgent.substring(userAgent.indexOf("MSIE")).split(" ")[1].replace(";", ""));
        } else if (userAgent.contains(Browsers.FIREFOX.getName())) {
            browser.setBrowserName(Browsers.FIREFOX.getName());
            browser.setVersion(userAgent.substring(userAgent.indexOf(Browsers.FIREFOX.getName())).split("/")[1]);
        }
        return browser;
    }
}
