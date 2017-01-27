package com.infosys.jira.plugins.util;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.config.util.JiraHome;
import org.apache.commons.lang3.time.DateUtils;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Raj on 22/01/2017.
 */
public class PluginConstants {

    // This needs to be changed when deploying it to producton.
    public static final String JIRA_ACCESS_LOG_HOME = "D:\\Atlassian\\JIRA\\logs";

    public static final String JIRA_ACCESS_LOG_FILE_PREFIX = "access_log";

    public static final String TARGET_ACCESS_LOG_FILE = getTargetAccessFile();

    public static final String PLUGIN_PRIVATE_DIRECTORY_NAME = "access-log-checker";

    public static final String PLUGIN_PRIVATE_HOME = getJiraHomePath() + File.separator + PLUGIN_PRIVATE_DIRECTORY_NAME;

    public static final String EXCEL_REPORT_NAME = "Browser_Usage_Report_" + getPreviousDate() + ".xls";

    public static final String SERIAL_NUMBER = "Serial No.";

    public static final String USER_NAME = "User Name";

    public static final String DISPLAY_NAME = "Display Name";

    public static final String EMAIL_ADDRESS = "Email Address";

    public static final String BROWSER = "Browser";

    public static final String EXCEL_REPORT_PATH = getJiraHomePath() +
            File.separator + PLUGIN_PRIVATE_DIRECTORY_NAME + File.separator + EXCEL_REPORT_NAME;

    private static String getTargetAccessFile() {
        return JIRA_ACCESS_LOG_HOME + File.separator + JIRA_ACCESS_LOG_FILE_PREFIX + "." + getPreviousDate();
    }

    private static String getJiraHomePath() {
        return ComponentAccessor.getComponentOfType(JiraHome.class).getHomePath();
    }

    private static String getPreviousDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(DateUtils.addDays(new Date(), -1));
    }


}
