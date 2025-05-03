import com.example.vadimaprojekts.exceptions.NotValidPasswordException;
import com.example.vadimaprojekts.exceptions.NotValidUsernameException;
import com.example.vadimaprojekts.exceptions.UserExistsException;
import com.example.vadimaprojekts.module.User;
import com.example.vadimaprojekts.service.RegisterService;
import com.example.vadimaprojekts.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserRegistrationTests {
    private UserService userService = new UserService();
    private RegisterService registerService = new RegisterService();

    @BeforeEach
    public void setUp() {
        userService = new UserService();
    }

    @Test
    public void testValidUserRegistration() throws UserExistsException, NotValidUsernameException, NotValidPasswordException {
        List<String> readList = new ArrayList<>();
        List<String> buyList = new ArrayList<>();
        User user = new User("validUser", "ValidPass123", readList, buyList);
        userService.saveUserToJson(user);
        assertTrue(userService.checkForUsername("validUser"));
    }

    @Test
    public void testDuplicateUsernameRegistration() {
        List<String> readList = new ArrayList<>();
        List<String> buyList = new ArrayList<>();
        User user1 = new User("existingUser", "ValidPass123", readList, buyList);
        try {
            userService.saveUserToJson(user1);
        } catch (Exception e) {
            fail("Error saving first user: " + e.getMessage());
        }
        User user2 = new User("existingUser", "AnotherPass123", readList, buyList);
        assertThrows(UserExistsException.class, () -> userService.saveUserToJson(user2));
    }

    @Test
    public void testInvalidUsernameRegistration() {
        assertThrows(NotValidUsernameException.class, () -> registerService.validateUsername("u"));
    }

    @Test
    public void testInvalidPasswordRegistration() throws NotValidPasswordException {
        assertThrows(NotValidPasswordException.class, () -> registerService.validatePassword("short"));
    }

    @Test
    public void testValidPasswordButInvalidUsername() throws UserExistsException {
        assertThrows(NotValidUsernameException.class, () -> registerService.validateUsername("user@invalid"));
    }
}
