package com.geofishing;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.transaction.annotation.Transactional;


@SpringBootApplication
public class Application extends SpringBootServletInitializer implements CommandLineRunner {


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    @Transactional(readOnly = true)
    @Override
    public void run(String... args) throws Exception {
        System.out.println("Done!");

    }

}


//@SpringBootApplication
//public class Application implements CommandLineRunner {
//
//
//	public static void main(String[] args) {
//		SpringApplication.run(Application.class, args);
//	}
//
//	@Transactional(readOnly = true)
//    @Override
//    public void run(String... args) throws Exception {
//       System.out.println("Done!");
//
//    }
//
//}
