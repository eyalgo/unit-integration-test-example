package org.eyalgo.testing.example.cassandra;

import org.eyalgo.testing.example.cassandra.conf.CassandraConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class CassandraApplication {

  public static void main(String[] args) {
    ConfigurableApplicationContext context = SpringApplication.run(CassandraApplication.class, args);
    CassandraConfiguration configuration = context.getBean(CassandraConfiguration.class);

    System.out.println(">>>>>> " + configuration.getKeySpace());
  }
}
