package wsei.backend.lab4.database;

import org.springframework.data.jpa.repository.JpaRepository;

import wsei.backend.lab4.database.entities.UserEntity;

public interface UsersRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String Name);
}
