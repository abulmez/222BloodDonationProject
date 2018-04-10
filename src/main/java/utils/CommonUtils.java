package utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CommonUtils {
    private static ApplicationContext factory;

    private CommonUtils(){}

    public static ApplicationContext getFactory() {
        if(factory == null){
            factory = new ClassPathXmlApplicationContext("classpath:spring-config.xml");
        }
        return factory;
    }
}
