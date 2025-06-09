package com.example;

import com.example.utils.IPUpdater;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;

@SpringBootApplication
@MapperScan("com.example.mapper")
public class SpringbootApplication implements CommandLineRunner {

    @Autowired
    private IPUpdater ipUpdater;

    public static void main(String[] args) {
        SpringApplication.run(SpringbootApplication.class, args);
        System.out.println("\n================================ start =================================");
    }

    @Override
    public void run(String... args) {
        ipUpdater.updateAvatarIp();
        ipUpdater.updateIpInUrl("news","img","localhost",9090);
        ipUpdater.updateIpInUrl("video","file","localhost",9090);
        ipUpdater.updateIpInUrl("banner","img","localhost",9090);
        ipUpdater.updateIpInUrl("admin","avatar","localhost",9090);
        ipUpdater.updateIpInUrl("question","img","localhost",9090);
        ipUpdater.updateIpInUrl("user","avatar","localhost",9090);


    }
}
