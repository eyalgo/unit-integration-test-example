package org.eyalgo.testing.example.cassandra.conf;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@Getter
@Setter
@Configuration
@EnableCassandraRepositories("org.eyalgo.testing.example.cassandra.repositories")
public class CassandraConfiguration extends AbstractCassandraConfiguration {
  @Value("${spring.data.cassandra.keyspace-name}")
  private String keySpace;

  @Value("${spring.data.cassandra.contact-points}")
  private String contactPoints;

  @Value("${spring.data.cassandra.port}")
  private int port;

  @Override
  protected String getKeyspaceName() {
    return keySpace;
  }

  @Override
  protected boolean getMetricsEnabled() {
    return false;
  }

  @Override
  public String getContactPoints() {
    return contactPoints;
  }

  @Override
  public int getPort() {
    return port;
  }
}
