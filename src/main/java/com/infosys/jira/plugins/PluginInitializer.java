package com.infosys.jira.plugins;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.config.util.JiraHome;
import com.atlassian.plugin.spring.scanner.annotation.export.ExportAsService;
import com.infosys.jira.plugins.util.PluginUtil;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.inject.Named;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Raj on 21/01/2017.
 */

@ExportAsService({PluginInitializer.class})
@Named("pluginListener")
public class PluginInitializer implements InitializingBean, DisposableBean {

    private static final Logger logger = Logger.getLogger(PluginInitializer.class);

    private final Path pluginPrivateDirectoryPath;

    public PluginInitializer() {
        JiraHome jiraHome = ComponentAccessor.getComponentOfType(JiraHome.class);
        pluginPrivateDirectoryPath = Paths.get(jiraHome.getHomePath() + File.separator + "access-log-checker");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("Starting plugin initialization process...");
        createPluginDirectory();
        logger.info("Plugin initialization completed successfully.");
        PluginUtil.findLogFile();
    }

    @Override
    public void destroy() throws Exception {
        logger.info("Starting plugin un-installation process...");
        deletePluginDirectory();
        logger.info("Plugin successfully un-installed from JIRA.");
    }

    private void createPluginDirectory() {
        try {
            Path newDIr = Files.createDirectories(pluginPrivateDirectoryPath);
            logger.info("Private plugin directory " + newDIr + " is created.");
        } catch (IOException e) {
            logger.error(ExceptionUtils.getStackTrace(e));
        }
    }

    private void deletePluginDirectory() {
        try{
            Files.delete(pluginPrivateDirectoryPath);
            logger.info("Private plugin directory is deleted.");
        }catch (IOException e){
            logger.error(ExceptionUtils.getStackTrace(e));
        }
    }
}
