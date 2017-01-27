package com.infosys.jira.plugins.scheduler;

import com.atlassian.sal.api.scheduling.PluginJob;
import com.infosys.jira.plugins.scheduler.impl.AccessLogAnalyzerImpl;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import java.util.Map;

/**
 * Created by Raj on 21/01/2017.
 */
public class AccessLogAnalyzerJob implements PluginJob {

    private static final Logger logger = Logger.getLogger(AccessLogAnalyzerJob.class);
    @Override
    public void execute(Map<String, Object> map) {
        final AccessLogAnalyzerImpl accessLogAnalyzer = (AccessLogAnalyzerImpl)map.get(AccessLogAnalyzerImpl.getKey());
        try{
            accessLogAnalyzer.analyze();
        }catch (Exception ex){
            logger.error(ExceptionUtils.getStackTrace(ex));
        }
    }
}
