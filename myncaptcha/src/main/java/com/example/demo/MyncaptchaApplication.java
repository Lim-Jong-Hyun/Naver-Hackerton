package com.example.demo;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@MapperScan(value= {"com.example.demo.member.mapper"}) //mapper package인식
public class MyncaptchaApplication {

	   public static void main(String[] args) {
	        SpringApplication.run(MyncaptchaApplication.class, args);
	    }
	    
	    /*
	     * Bean-스프링 객체의 단위
	     * SqlSessionFactory 설정 
	     * MyBatis의 SqlSessionFactory에 DataSource를 주입하여
	     * SqlSessionFactory 객체 반환
	     */
	    @Bean
	    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception{
	        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();       
	        sessionFactory.setDataSource(dataSource);
	        return sessionFactory.getObject();
	        
	    }
	 
}
