package wsei.backend.lab2.database;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import wsei.backend.lab2.database.entities.UserEntity;

@Repository

public interface UserRepository extends CrudRepository<UserEntity, Long> {
}
