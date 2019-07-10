package org.eyalgo.testing.example.logging;

import lombok.extern.slf4j.Slf4j;
import org.eyalgo.testing.example.domain.User;

@Slf4j
public class ExampleLogging {
  public void userIsLoggedIn(User user) {
    log.debug("The user [{} {}] is logged in.", user.getFirstName(), user.getLastName());
  }
}
