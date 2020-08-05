package cn.hui.vote.app;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

@MapperScan(basePackages = { "cn.hui.vote.dal.mapper" })
@ComponentScan(value = "cn.hui")
@SpringBootApplication(exclude = { HibernateJpaAutoConfiguration.class })
@EnableAsync
public class VoteApplication extends SpringBootServletInitializer {
    private static final Logger LOGGER = LoggerFactory.getLogger(VoteApplication.class);

    public static void main(String[] args) {
        try {
            SpringApplication.run(VoteApplication.class, args);
            LOGGER.info("Application started...");
        } catch (Throwable t) {
            System.exit(1);
        }
    }
}
