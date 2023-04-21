package dev.ynnk.m295.service;

import dev.ynnk.m295.model.User;
import dev.ynnk.m295.repository.UserRepository;
import dev.ynnk.m295.helper.patch.AutoPatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "You need to provide a User");
        }
        return this.userRepository.save(user);
    }

    public User getUserById(long id){
        return this.userRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "There doesn't exist a user with the id "  + id + " in the Database" ));
    }

    public void deleteUser(long id){
        Optional<User> optionalUser = this.userRepository.findById(id);

        if (optionalUser.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "There doesn't exist a user with the id "  + id + " in the Database" );
        }

        this.userRepository.deleteById(id);
    }

    public User patchUser(User user, long id){

        User dbUser = this.userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "There doesn't exist a user with the id "  + id + " in the Database" ));


        User patchedUser = AutoPatch.patch(user, dbUser);
        if (patchedUser == null){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Patch process failed!");
        }
        return patchedUser;
    }

}
