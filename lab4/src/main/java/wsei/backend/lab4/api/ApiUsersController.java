package wsei.backend.lab4.api;

import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import wsei.backend.lab4.database.PagingUsersRepository;
import wsei.backend.lab4.database.UsersRepository;
import wsei.backend.lab4.database.entities.UserEntity;
import wsei.backend.lab4.models.UserModel;

@RestController
@RequestMapping("/api/v1/users")
public class ApiUsersController {

    private @Autowired UsersRepository usersRepository;
    private @Autowired PagingUsersRepository pagingUsersRepository;

    @GetMapping({ "", "{id}" })
    public Object GetUsers(
            @RequestParam(name = "page-number", required = false) Integer page,
            @RequestParam(name = "page-size", required = false, defaultValue = "25") Integer size,
            @PathVariable(name = "id", required = false) Long id) {

        try {

            if (id != null)
                return usersRepository.findById(id);

            Page<UserEntity> users;
            boolean showAll = (page == null);

            if (showAll)
                users = pagingUsersRepository.findAll(Pageable.unpaged());
            else
                users = pagingUsersRepository.findAll(PageRequest.of(page - 1, size));

            return new LinkedHashMap<String, Object>() {
                {
                    if (!showAll)
                        put("pageNumber", users.getNumber() + 1);
                    if (!showAll)
                        put("pageCount", users.getTotalPages());
                    if (!showAll)
                        put("pageSize", users.getSize());
                    if (showAll)
                        put("elementCount", users.getNumberOfElements());
                    put("users", users.getContent());
                }
            };
        } catch (Exception e) {
            return new LinkedHashMap<>() {
                {
                    put("errorMessage", "Error");
                }
            };
        }
    }

    @PostMapping()
    public Object CreateUser(@RequestBody UserModel model) {
        try {
            UserEntity user = new UserEntity(model);
            usersRepository.save(user);

            return user;
        } catch (Exception e) {
            return new LinkedHashMap<String, Object>() {
                {
                    put("errorMessage", "Error");
                }
            };
        }
    }

    @PutMapping("{id}")
    public Object UpdateUser(
            @PathVariable(name = "id", required = false) Long id,
            @RequestBody() UserModel model) {

        UserEntity user = usersRepository.findById(id).get();
        if (user.checkPassword(model.getPassword())) {
            if (model.getUsername() != null)
                user.setUsername(model.getUsername());
            if (model.getEmail() != null)
                user.setEmail(model.getEmail());
            if (model.getFirstName() != null)
                user.setFirstName(model.getFirstName());
            if (model.getLastName() != null)
                user.setLastName(model.getLastName());
            if (model.getNewPassword() != null)
                user.setPassword(model.getPassword(), model.getNewPassword());

            usersRepository.save(user);
            return user;
        } else
            return new LinkedHashMap<String, Object>() {
                {
                    put("errorMessage", "Password Missmatch");
                }
            };
    }

    @DeleteMapping("{id}")
    public boolean DeleteUser(@PathVariable(name = "id", required = false) Long id) {
        try {
            usersRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }
}
