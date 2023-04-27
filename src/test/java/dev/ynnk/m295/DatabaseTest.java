package dev.ynnk.m295;

import dev.ynnk.m295.model.User;
import dev.ynnk.m295.repository.UserRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest()
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class DatabaseTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void insertVehicle() {
        User user = new User();
        user.setUsername("Test");
        user.setFirstName("Firstname");
        user.setLastName("Lastname");
        User dbUser = this.userRepository.save(user);

        assertNotNull(dbUser.getId());
        user.setId(dbUser.getId());
        assertEquals(dbUser, user);
    }

}
