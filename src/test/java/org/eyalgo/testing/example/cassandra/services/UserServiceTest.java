package org.eyalgo.testing.example.cassandra.services;

import static org.mockito.Mockito.verify;

import java.util.Date;
import java.util.UUID;
import org.eyalgo.testing.example.cassandra.repositories.UserRepository;
import org.eyalgo.testing.example.cassandra.services.UserService;
import org.eyalgo.testing.example.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
  @InjectMocks
  private UserService service;

  @Mock
  private UserRepository repository;

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

    verify(repository).save(user);
  }
}
