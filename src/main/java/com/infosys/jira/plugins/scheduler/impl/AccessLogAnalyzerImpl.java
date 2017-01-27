package com.infosys.jira.plugins.scheduler.impl;

import com.atlassian.plugin.spring.scanner.annotation.export.ExportAsService;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.sal.api.scheduling.PluginScheduler;
import com.infosys.jira.plugins.email.EmailReport;
import com.infosys.jira.plugins.email.EmailSender;
import com.infosys.jira.plugins.excel.ExcelReportGenerator;
import com.infosys.jira.plugins.logparser.LogFileParser;
import com.infosys.jira.plugins.scheduler.AccessLogAnalyzer;
import com.atlassian.sal.api.lifecycle.LifecycleAware;
import com.infosys.jira.plugins.scheduler.AccessLogAnalyzerJob;
import com.infosys.jira.plugins.util.PluginConstants;
import org.apache.log4j.Logger;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by Raj on 21/01/2017.
 */
@ExportAsService({AccessLogAnalyzer.class, LifecycleAware.class})
@Named
public class AccessLogAnalyzerImpl implements AccessLogAnalyzer, LifecycleAware {

    private static final Logger logger = Logger.getLogger(AccessLogAnalyzerImpl.class);

    static final String KEY = AccessLogAnalyzerImpl.class.getName() + ":instance";

    private static final String JOB_NAME = AccessLogAnalyzerImpl.class.getName() + ":job";

    private static final long INTERVAL = TimeUnit.DAYS.toMillis(1);

    private PluginScheduler pluginScheduler;

    @Inject
    public AccessLogAnalyzerImpl(@ComponentImport PluginScheduler pluginScheduler) {
        this.pluginScheduler = pluginScheduler;
    }

    @Override
    public void analyze() throws Exception {
        LogFileParser logFileParser = new LogFileParser(PluginConstants.TARGET_ACCESS_LOG_FILE);

        Map<String, EmailReport> emailReports = logFileParser.parse();

        ExcelReportGenerator excelReportGenerator = new ExcelReportGenerator();
        excelReportGenerator.generate(emailReports);

        EmailSender emailSender = new EmailSender();
        emailSender.send(emailReports, PluginConstants.EXCEL_REPORT_PATH);


    }

    @Override
    public void onStart() {
        schedule();
    }

    private void schedule() {
        final Map<String, Object> jobDataMap = new HashMap<>();
        jobDataMap.put(KEY, AccessLogAnalyzerImpl.this);
        pluginScheduler.scheduleJob(JOB_NAME, AccessLogAnalyzerJob.class, jobDataMap, setStartTime(), INTERVAL);
    }

    private Date setStartTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("EST"));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 30);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date startDate = calendar.getTime();
        return startDate;
    }

    public static String getKey() {
        return KEY;
    }
}
