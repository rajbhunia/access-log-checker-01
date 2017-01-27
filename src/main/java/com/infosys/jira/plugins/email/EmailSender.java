package com.infosys.jira.plugins.email;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.mail.Email;
import com.atlassian.mail.queue.SingleMailQueueItem;
import org.apache.log4j.Logger;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Raj on 22/01/2017.
 */
public class EmailSender {

    Logger logger = Logger.getLogger(this.getClass());

    //private final Properties properties;

    public EmailSender() {
        //properties = EmailUtil.getProperties("access-log-analyzer.properties");
    }

    public void send(Map<String, EmailReport> userMap, String excelFilePath) throws IOException, MessagingException {
        Email email = new Email("carrie.lin@amd.com");
        email.setSubject(EmailUtil.getSubject());
        email.setMimeType("text/html");
        email.setCc("dl.AMD-Infosys-JIRASupport@amd.com");
        email.setBody(EmailUtil.getBody(userMap));

        Multipart multipart = new MimeMultipart();

        MimeBodyPart attachPart = new MimeBodyPart();

        DataSource source = new FileDataSource(excelFilePath);
        attachPart.setDataHandler(new DataHandler(source));
        attachPart.setFileName(new File(excelFilePath).getName());

        multipart.addBodyPart(attachPart);

        email.setMultipart(multipart);

        SingleMailQueueItem singleMailQueueItem = new SingleMailQueueItem(email);
        ComponentAccessor.getMailQueue().addItem(singleMailQueueItem);
    }
}
