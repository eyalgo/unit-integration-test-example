package org.eyalgo.testing.example.cassandra.repositories;

import java.util.UUID;
import org.eyalgo.testing.example.domain.User;
import org.springframework.data.repository.Repository;

public interface UserRepository extends Repository<User, UUID> {
  void save(User user);
}
