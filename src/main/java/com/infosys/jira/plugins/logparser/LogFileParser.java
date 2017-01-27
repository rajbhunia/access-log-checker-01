package com.infosys.jira.plugins.logparser;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.user.util.UserManager;
import com.infosys.jira.plugins.email.EmailReport;
import com.infosys.jira.plugins.helper.Browser;
import com.infosys.jira.plugins.helper.Browsers;
import com.infosys.jira.plugins.helper.UserAgentParser;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Raj on 22/01/2017.
 */
public class LogFileParser {

    private final String logFileName;

    public LogFileParser(final String logFileName) {
        this.logFileName = logFileName;
    }

    public Map<String, EmailReport> parse() throws IOException {
        final List<String> linesWithOutChrome = getLinesWithOutChrome();
        final Map<String, EmailReport> emailReports = new HashMap<>();
        for (final String line : linesWithOutChrome) {
            EmailReport emailReport = getEmailReport(line);
            if(emailReport.getUserName() != null && !emailReport.getBrowser().getBrowserName().equals(Browsers.UNKNOWN.getName())){
                emailReports.put(emailReport.getUserName(), emailReport);
            }
        }
        return emailReports;
    }

    private List<String> getLinesWithOutChrome() throws IOException {
        List<String> linesWithOutChrome = new ArrayList<>();
        File logFile = new File(logFileName);
        LineIterator lineIterator = null;
        try {
            lineIterator = FileUtils.lineIterator(logFile, "UTF-8");
            while (lineIterator.hasNext()) {
                final String line = lineIterator.nextLine();
                if (!line.contains("Chrome")) {
                    linesWithOutChrome.add(line);
                }
            }
        } finally {
            LineIterator.closeQuietly(lineIterator);
        }
        return linesWithOutChrome;
    }

    private EmailReport getEmailReport(String line) {
        String userName = getUserName(line);
        EmailReport emailReport = new EmailReport();
        UserManager userManager = ComponentAccessor.getUserManager();
        ApplicationUser user = userManager.getUserByKey(userName);
        if (user != null && user.isActive()) {
            emailReport.setUserName(user.getName());
            emailReport.setDisplayName(user.getDisplayName());
            emailReport.setEmailAddress(user.getEmailAddress());
            emailReport.setBrowser(getBrowser(getUserAgent(line)));
        }
        return emailReport;
    }

    private String getUserName(String line) {
        return line.split("\\s+")[2];
    }

    private String getUserAgent(String line) {
        return line.split("\"")[5];
    }

    public Browser getBrowser(String userAgent) {
        UserAgentParser userAgentParser = new UserAgentParser();
        return userAgentParser.getBrowser(userAgent);
    }
}
