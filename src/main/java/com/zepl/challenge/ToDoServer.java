package com.zepl.challenge;

import com.zepl.challenge.dao.ToDoDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.io.FileSystemResource;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;


/**
 * ToDo server for handling Restful service from clients by using spring boot mechanism.
 */
@SpringBootApplication
@ServletComponentScan(basePackages = "com.zepl.challenge")
public class ToDoServer extends SpringBootServletInitializer implements ApplicationListener<ApplicationReadyEvent>, EnvironmentAware, ApplicationContextAware {

    private final static Logger log = LoggerFactory.getLogger(ToDoServer.class);
    @Value("${server.port}")
    private String serverPort;
    @Value("${server.address}")
    private String serverAddress;
    @Value("${spring.datasource.driverClassName}")
    private String driverClassName;
    @Value("${spring.datasource.url}")
    private String dbUrl;
    @Value("${spring.datasource.username}")
    private String dbUser;
    @Value("${spring.datasource.password}")
    private String dbPassword;

    public ToDoServer() {
        try {

        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(ex.getMessage());
        }

    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ToDoServer.class, args);
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        try {
            log.info("\nStarting ToDoServer: http://" + serverAddress + ":" + serverPort + "\n");
            ToDoDao.initTablesIfNotExist(driverClassName, dbUrl, dbUser, dbPassword);
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(ex.getMessage());
        }

    }

    @Override
    public void setEnvironment(Environment environment) {
        log.debug("setEnvironment {}", environment);
        //Set up Relative path of Configuration directory/folder, should be at the root of the project or the same folder where the jar/war is placed or being run from
        String configFolder = "config";
        //All static property file names here
        List<String> propertyFiles = Arrays.asList("application.properties", "server.properties");
        //This is also useful for appending the profile names
        Arrays.asList(environment.getActiveProfiles()).stream().forEach(environmentName -> propertyFiles.add(String.format("application-%s.properties", environmentName)));
        for (String configFileName : propertyFiles) {
            File configFile = new File(configFolder, configFileName);
            log.info("\n\n\n\n");
            log.info(String.format("looking for configuration %s from %s", configFileName, configFolder));
            FileSystemResource springResource = new FileSystemResource(configFile);
            log.info("Config file : {0}", (configFile.exists() ? "FOund" : "Not Found"));
            if (configFile.exists()) {
                try {
                    log.info(String.format("Loading configuration file %s", configFileName));
                    PropertiesFactoryBean pfb = new PropertiesFactoryBean();
                    pfb.setFileEncoding("UTF-8");
                    pfb.setLocation(springResource);
                    pfb.afterPropertiesSet();
                    Properties properties = pfb.getObject();
                    PropertiesPropertySource externalConfig = new PropertiesPropertySource("externalConfig", properties);
                    ((ConfigurableEnvironment) environment).getPropertySources().addFirst(externalConfig);
                } catch (IOException ex) {
                    log.error(ex.getMessage());
                }
            } else {
                log.info(String.format("Cannot find Configuration file %s... \n\n\n\n", configFileName));

            }

        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        log.info("Set application context. App Config Property: {}", applicationContext.getEnvironment().getProperty("app_config_dir"));
    }

}
