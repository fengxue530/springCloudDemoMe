package com.fx.configserver.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.env.PropertySourceLoader;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @auther: mabaofeng
 * @date: 2019/8/14 10:38
 * @description:  解决中文乱码问题
 */
public class MyPropertiesHandler implements PropertySourceLoader {

    private static final Logger logger = LoggerFactory.getLogger(MyPropertiesHandler.class);

    @Override
    public String[] getFileExtensions() {
        return new String[]{"properties", "xml"};
    }

    @Override
    public List<PropertySource<?>> load(String name, Resource resource) {
        Properties properties = getProperties(resource);
        if (!properties.isEmpty()) {
            PropertiesPropertySource propertiesPropertySource = new PropertiesPropertySource(name, properties);
            List<PropertySource<?>> list = new ArrayList<>();
            list.add(propertiesPropertySource);
            return list;
        }
        return null;
    }

    private Properties getProperties(Resource resource) {
        Properties properties = new Properties();
        InputStream inputStream = null;
        try {
            inputStream = resource.getInputStream();
            properties.load(new InputStreamReader(inputStream, "utf-8"));
            inputStream.close();
        } catch (IOException e) {
            logger.info("load inputstream failure...", e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.info("close IO failure ....", e);
                }
            }
        }
        return properties;
    }
}