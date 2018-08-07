package com.creditcard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CreditcardApplication  {

    private static final Logger logger = LoggerFactory.getLogger(CreditcardApplication.class);
    
    public static void main(String[] args) {

        SpringApplication.run(CreditcardApplication.class, args);

        logger.info("==========================================================");
        logger.info("======================== DEPLOYED ========================");
        logger.info("==========================================================");

    }
}
