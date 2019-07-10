package org.eyalgo.testing.example.domain;

import java.util.Date;
import java.util.UUID;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class User {
  @NonNull
  UUID id;
  @NonNull
  String firstName;
  @NonNull
  String lastName;
  String info;
  @NonNull
  Date created;
  @NonNull
  Date lastUpdated;
}
