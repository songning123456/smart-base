package com.sonin.core.context;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
@Lazy(false)
public class SpringContext implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public SpringContext() {
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContext.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

    public static void setJdbcTemplateBean(String beanName, Class clazz, DataSource dataSource) {
        ConfigurableApplicationContext context = (ConfigurableApplicationContext) getApplicationContext();
        DefaultListableBeanFactory beanDefReg = (DefaultListableBeanFactory) context.getBeanFactory();
        BeanDefinitionBuilder beanDefBuilder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
        beanDefBuilder.addPropertyValue("dataSource", dataSource);
        beanDefBuilder.setScope("singleton");
        BeanDefinition beanDefinition = beanDefBuilder.getBeanDefinition();
        if (!beanDefReg.containsBeanDefinition(beanName)) {
            beanDefReg.registerBeanDefinition(beanName, beanDefinition);
        }
    }

}
