package com.infosys.jira.plugins.email;

import com.infosys.jira.plugins.util.PluginUtil;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Raj on 22/01/2017.
 */
public class EmailUtil {

    private static final Logger logger = Logger.getLogger(EmailUtil.class);

    public static String getSubject() {
        return "Browser usage report : " + PluginUtil.getPreviousDate();

    }

    public static Properties getProperties(String fileName) {
        Properties properties = null;
        try (InputStream inputStream = EmailUtil.class.getClassLoader().getResourceAsStream(fileName)) {
            properties = new Properties();
            properties.load(inputStream);
        } catch (IOException ex) {
            logger.error("Unable load properties file from class path.", ex);
        }
        return properties;
    }

    public static String getBody(final Map<String, EmailReport> userMap) {
        StringBuilder body = new StringBuilder();
        int i = 1;

        body.append("<!DOCTYPE html>\n");
        body.append("<html>\n");
        body.append("<body>\n");

        body.append("<div lang=\"EN-US\">");
        body.append("<div>");
        body.append("<p><span style=\"color:#2F5496\">Hi All,</span></p>");
        body.append("<p ><span style=\"color:#2F5496\">Please find the attached browser usage report for users not using Chrome.</span></p>");
        body.append("<p ><span style=\"color:#2F5496\">The list provides the users accessed http://ontrack-internal-uat.amd.com via non-Chrome browsers from 00:00:00 &ndash; 23:59:59 (CST)</span></p>");
        body.append("<p ><span style=\"color:#2F5496\">Regards,</span></p>");
        body.append("<p ><span style=\"color:#2F5496\">JIRA Team</span></p>");

//        body.append("<table border=\"1\" cellspacing=\"0\" cellpadding=\"0\" style=\"border-collapse:collapse; border:none\">");
//        body.append("<tbody>\n<tr>");
//        body.append("<td width=\"208\" valign=\"top\" style=\"width:155.8pt; border:solid #4472C4 1.0pt; border-right:none; background:#4472C4; padding:0in 5.4pt 0in 5.4pt\">");
//        body.append("<p ><b><span style=\"color:white\">Serial Number</span></b></p>\n</td>");
//        body.append("<td width=\"208\" valign=\"top\" style=\"width:155.8pt; border:solid #4472C4 1.0pt; border-right:none; background:#4472C4; padding:0in 5.4pt 0in 5.4pt\">");
//        body.append("<p ><b><span style=\"color:white\">User ID</span></b></p>\n</td>");
//        body.append("<td width=\"208\" valign=\"top\" style=\"width:155.85pt; border-top:solid #4472C4 1.0pt; border-left:none; border-bottom:solid #4472C4 1.0pt; border-right:none; background:#4472C4; padding:0in 5.4pt 0in 5.4pt\">");
//        body.append("<p ><b><span style=\"color:white\">Display Name</span></b></p></td>");
//        body.append("<td width=\"208\" valign=\"top\" style=\"width:155.85pt; border:solid #4472C4 1.0pt; border-left:none; background:#4472C4; padding:0in 5.4pt 0in 5.4pt\"><p><b><span style=\"color:white\">Browser</span></b></p></td></tr>");
//
//        for (Map.Entry<String, EmailReport> entry : userMap.entrySet()) {
//            body.append("<tr>\n");
//
//            body.append("<td width=\"208\" valign=\"top\" style=\"width:155.8pt; border:solid #8EAADB 1.0pt; border-top:none; background:#D9E2F3; padding:0in 5.4pt 0in 5.4pt\">");
//            body.append("<p ><b>" + i + "</b></p>");
//            body.append("</td>\n");
//
//            body.append("<td width=\"208\" valign=\"top\" style=\"width:155.8pt; border:solid #8EAADB 1.0pt; border-top:none; background:#D9E2F3; padding:0in 5.4pt 0in 5.4pt\">");
//            body.append("<p ><b>" + entry.getValue().getUserName() + "</b></p>");
//            body.append("</td>\n");
//
//            body.append("<td width=\"208\" valign=\"top\" style=\"width:155.8pt; border:solid #8EAADB 1.0pt; border-top:none; background:#D9E2F3; padding:0in 5.4pt 0in 5.4pt\">");
//            body.append("<p ><b>" + entry.getValue().getDisplayName() + "</b></p>");
//            body.append("</td>\n");
//
//
//            body.append("<td width=\"208\" valign=\"top\" style=\"width:155.8pt; border:solid #8EAADB 1.0pt; border-top:none; background:#D9E2F3; padding:0in 5.4pt 0in 5.4pt\">");
//            body.append("<p ><b>" + entry.getValue().getBrowser() + "</b></p>");
//            body.append("</td>\n");
//            body.append("</tr>\n");
//            i++;
//        }
//        body.append("</tbody>");
//        body.append("</table>");
        body.append("</div></div>");
        body.append("</body>\n");
        body.append("<html>\n");
        return body.toString();
    }
}
