package org.eyalgo.testing.example.logging;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.org.lidalia.slf4jtest.LoggingEvent.debug;

import java.util.Date;
import java.util.UUID;
import org.eyalgo.testing.example.domain.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.org.lidalia.slf4jext.Level;
import uk.org.lidalia.slf4jtest.TestLogger;
import uk.org.lidalia.slf4jtest.TestLoggerFactory;

public class ExampleLoggingTest {
  private TestLogger logger = TestLoggerFactory.getTestLogger(ExampleLogging.class);
  private ExampleLogging writer;

  @Before
  public void setUp() {
    writer = new ExampleLogging();
    TestLoggerFactory.getInstance().setPrintLevel(Level.DEBUG);
    TestLoggerFactory.clear();
  }

  @After
  public void tearDown() {
    TestLoggerFactory.clear();
  }

  @Test
  public void shouldSendUserToLogWithCorrectMessage() {
    Date now = new Date();
    String firstName = "John";
    String lastName = "Doe";
    User user = User.builder()
        .firstName(firstName)
        .lastName(lastName)
        .info("some-info")
        .created(now)
        .id(UUID.randomUUID())
        .lastUpdated(now)
        .build();

    writer.userIsLoggedIn(user);
    assertThat(logger.getLoggingEvents()).containsExactly(debug("The user [{} {}] is logged in.", firstName, lastName));
  }
}
