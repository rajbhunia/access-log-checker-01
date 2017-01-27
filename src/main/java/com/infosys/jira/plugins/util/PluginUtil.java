package com.infosys.jira.plugins.util;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Raj on 22/01/2017.
 */
public class PluginUtil {

    static Logger logger = Logger.getLogger(PluginUtil.class);

    public static String findLogFile() {
        String previousDay = getPreviousDate();
        logger.info("Previous date string : " + previousDay);
        final String accessLogFile = "access_log."+previousDay;
        logger.info("Log file to deal with "+ accessLogFile);
        return accessLogFile;
    }

    public static String getPreviousDate(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(DateUtils.addDays(new Date(), -1));
    }
}
