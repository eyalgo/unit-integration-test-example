package org.eyalgo.testing.example.cassandra.repositories;

import java.util.UUID;
import org.eyalgo.testing.example.domain.User;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface UserCrudRepository extends CassandraRepository<User, UUID> {
}
