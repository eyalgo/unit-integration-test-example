package org.eyalgo.testing.example.cassandra.services;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;
import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.eyalgo.testing.example.cassandra.CassandraApplication;
import org.eyalgo.testing.example.cassandra.conf.CassandraConfiguration;
import org.eyalgo.testing.example.cassandra.repositories.UserCrudRepository;
import org.eyalgo.testing.example.cassandra.services.UserService;
import org.eyalgo.testing.example.domain.User;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.cassandra.core.CassandraAdminTemplate;
import org.springframework.data.cassandra.core.convert.MappingCassandraConverter;
import org.springframework.data.cassandra.core.cql.CqlIdentifier;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CassandraApplication.class)
@ContextConfiguration
// Using a different YML file for testing
// https://stackoverflow.com/questions/38711871/load-different-application-yml-in-springboot-test/
@ActiveProfiles("integration-test")
public class UserServiceIT {
  @Autowired
  private UserService service;

  @Autowired
  private UserCrudRepository userCrudRepository;

  @Autowired
  private CassandraConfiguration cassandraConfiguration;

  private static CassandraAdminTemplate adminTemplate;

  @BeforeClass
  public static void setupDb() throws Exception {
    EmbeddedCassandraServerHelper.startEmbeddedCassandra();
    CassandraConnector.connect(EmbeddedCassandraServerHelper.getHost(), EmbeddedCassandraServerHelper.getNativeTransportPort());
    createKeyspace();
    adminTemplate = new CassandraAdminTemplate(CassandraConnector.cluster.connect("users_support"),
        new MappingCassandraConverter());
  }

  @AfterClass
  public static void shutdownDb() {
    CassandraConnector.close();
  }

  private static void createKeyspace() {
    StringBuilder sb =
        new StringBuilder("CREATE KEYSPACE IF NOT EXISTS ")
            .append("users_support")
            .append(" WITH replication = {")
            .append("'class':'").append("SimpleStrategy")
            .append("','replication_factor':").append(1)
            .append("};");

    String query = sb.toString();
    CassandraConnector.session.execute(query);
  }

  @Before
  public void createTables() {
    adminTemplate.createTable(
        true,
        CqlIdentifier.of("users"),
        User.class,
        new HashMap<String, Object>()
    );
  }

  @After
  public void tearDown() {
    // Following line is to avoid NPE
    // See https://github.com/jsevellec/cassandra-unit/issues/278
    EmbeddedCassandraServerHelper.getSession();
    EmbeddedCassandraServerHelper.cleanEmbeddedCassandra();
  }

  @Test
  public void whenSavingANewUserThenShouldSendToRepository() {
    Date now = new Date();
    User user = User.builder()
        .firstName("John")
        .lastName("Doe")
        .info("some-info")
        .created(now)
        .id(UUID.randomUUID())
        .lastUpdated(now)
        .build();
    service.saveUser(user);
  }

  private static final class CassandraConnector {
    private static Cluster cluster;
    private static Session session;

    private static void connect(String address, Integer port) {
      cluster = Cluster.builder()
          .addContactPoint(address)
          .withPort(port)
          .withoutMetrics()
          .withoutJMXReporting()
          .build();
      session = cluster.connect();
    }

    private static void close() {
      session.close();
      cluster.close();
    }
  }
}
