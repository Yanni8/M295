package dev.ynnk.m295.service;

import dev.ynnk.m295.helper.language.ErrorMessage;
import dev.ynnk.m295.model.User;
import dev.ynnk.m295.repository.UserRepository;
import dev.ynnk.m295.helper.patch.AutoPatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User saveUser(User user){
        if (user == null){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, ErrorMessage.noObjectPresent("user"));
        }
        return this.userRepository.save(user);
    }

    public User getUserById(long id){
        return this.userRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        ErrorMessage.notFoundById("User", id)));
    }

    public void deleteUser(long id){
        this.getUserById(id);
        this.userRepository.deleteById(id);
    }

    public User patchUser(User user, long id){

        User dbUser = this.getUserById(id);

        User patchedUser = AutoPatch.patch(user, dbUser);
        if (patchedUser == null){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ErrorMessage.support(512));
        }

        this.userRepository.save(patchedUser);

        return patchedUser;
    }


    public User updateUser(User user, long id){
        user.setId(id);
        this.getUserById(id);
        return this.userRepository.save(user);
    }

    public List<User> getAllUser(){
        return userRepository.findAll();
    }


    public User getUserByJwt(Jwt jwt) {
        String username = jwt.getClaim("preferred_username");
        return this.userRepository.findByUsername(username)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "There doesn't exist a User by the Username " + username)
                );
    }
}
