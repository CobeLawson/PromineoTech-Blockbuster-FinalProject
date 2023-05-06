package com.promineotech.blockbuster;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.promineotech.blockbuster"})
public class CheckOut {
  
  //Main App method to start spring and begin running the project
  public static void main(String[] args) {
    SpringApplication.run(CheckOut.class, args);
  }
    
}
