package com.hhj.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;

//@Configuration
public class DruidConfig {

    // 默认的自动配置是判断容器中没有才会配@ConditionalOnMissingBean(DataSource.class)
    //    @ConfigurationProperties("spring.datasource")
//    @Bean
//    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() throws SQLException {
        DruidDataSource druidDataSource = new DruidDataSource();
        // druidDataSource.setUrl();
        // ...可以直接和配置文件绑定，不用这样写

        //加入监控功能
        druidDataSource.setFilters("stat,wall");
        return druidDataSource;
    }

//    @Bean
    public ServletRegistrationBean addStatViewServlet(){
        ServletRegistrationBean<StatViewServlet> registrationBean = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");
        // 可以设置登录用户民和密码
//        registrationBean.setUrlMappings(Arrays.asList("/*"));
        registrationBean.addInitParameter("loginUsername","root");
        registrationBean.addInitParameter("loginPassword","root");

        return registrationBean;

    }

    //    @Bean
    public FilterRegistrationBean webStatFilter(){
        WebStatFilter webStatFilter = new WebStatFilter();

        FilterRegistrationBean<WebStatFilter> filterRegistrationBean = new FilterRegistrationBean<>(webStatFilter);
        filterRegistrationBean.setUrlPatterns(Arrays.asList("/*"));
        filterRegistrationBean.addInitParameter("exclusions","*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");

        return filterRegistrationBean;
    }


}
