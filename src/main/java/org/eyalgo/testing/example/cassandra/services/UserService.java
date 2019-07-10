package org.eyalgo.testing.example.cassandra.services;

import org.eyalgo.testing.example.cassandra.repositories.UserRepository;
import org.eyalgo.testing.example.domain.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  private final UserRepository repository;

  public UserService(UserRepository repository) {
    this.repository = repository;
  }

  public void saveUser(User user) {
    repository.save(user);
  }
}
