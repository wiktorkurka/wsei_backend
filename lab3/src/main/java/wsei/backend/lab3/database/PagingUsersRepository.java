package wsei.backend.lab3.database;

import org.springframework.data.repository.PagingAndSortingRepository;

import wsei.backend.lab3.database.entities.UserEntity;

public interface PagingUsersRepository extends PagingAndSortingRepository<UserEntity, Long> {

}
