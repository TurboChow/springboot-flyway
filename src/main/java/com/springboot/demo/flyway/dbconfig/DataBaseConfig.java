package com.springboot.demo.flyway.dbconfig;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
/**
 * 启动时自动创建数据库
 * @author
 * @date
 * @version
 */
@Configuration
@Primary
public class DataBaseConfig {
    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    /**
     * 解决@Configuration下用@Value获取不到值
     * @return
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public DataSource dataSource(){
        //根据实际情况，可更换DataSource
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(datasourceUrl);
        datasource.setUsername(username);
        datasource.setPassword(password);
        datasource.setDriverClassName(driverClassName);
        try {
            Class.forName(driverClassName);
            String fullDatasourceUrl = datasourceUrl.substring(0,datasourceUrl.indexOf("?"));
            System.out.printf(">>>fullDatasourceUrl:%s%n",fullDatasourceUrl);
            String newDatasourceUrl = fullDatasourceUrl.substring(0,fullDatasourceUrl.lastIndexOf("/"));
            System.out.printf(">>>newDatasourceUrl:%s%n",newDatasourceUrl);
            String dataBaseName = fullDatasourceUrl.substring(fullDatasourceUrl.lastIndexOf("/")+1);
            System.out.printf(">>>dataBaseName:%s%n",dataBaseName);
            //连接已经存在的数据库
            Connection connection = DriverManager.getConnection(newDatasourceUrl, username, password);
            Statement statement = connection.createStatement();
            //创建数据库
            statement.executeUpdate("create database if not exists `" + dataBaseName + "` default character set utf8 COLLATE utf8_general_ci");
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return datasource;
    }
}
