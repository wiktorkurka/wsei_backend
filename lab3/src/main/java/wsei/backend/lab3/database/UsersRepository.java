package wsei.backend.lab3.database;

import org.springframework.data.repository.CrudRepository;

import wsei.backend.lab3.database.entities.UserEntity;

public interface UsersRepository extends CrudRepository<UserEntity, Long> {

}
