package wsei.backend.lab4.database;

import org.springframework.data.repository.PagingAndSortingRepository;

import wsei.backend.lab4.database.entities.UserEntity;

public interface PagingUsersRepository extends PagingAndSortingRepository<UserEntity, Long> {

}
