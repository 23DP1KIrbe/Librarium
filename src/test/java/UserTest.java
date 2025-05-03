import com.example.vadimaprojekts.exceptions.*;
import com.example.vadimaprojekts.module.User;
import com.example.vadimaprojekts.service.UserService;
import com.example.vadimaprojekts.service.Session;
import org.junit.jupiter.api.*;
import com.example.vadimaprojekts.exceptions.UserNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static com.example.vadimaprojekts.service.Session.session;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    private static UserService userService = new UserService();

    @BeforeAll
    public static void setUp() throws UserExistsException {
        List<String> readList = new ArrayList<>();
        List<String> buyList = new ArrayList<>();
        User user = new User("testuser", "password123", readList, buyList);
        Session session = Session.getInstance();
        session.setUser(user);
        userService.saveUserToJson(user);
    }

    @Test
    public void testSaveUserToJson() throws UserExistsException {
        List<User> users = userService.usersFromFile();
        assertTrue(users.stream().anyMatch(u -> u.getUsername().equals("testuser")));
    }

    @Test
    public void testSaveUserToJson_UsernameExists() {
        List<String> readList = new ArrayList<>();
        List<String> buyList = new ArrayList<>();
        User user = new User("existinguser", "password123", readList, buyList);
        assertThrows(UserExistsException.class, () -> userService.saveUserToJson(user));
    }

    @Test
    public void testGetUsernameData() throws UserNotFoundException, UserExistsException {
        User retrievedUser = userService.getUsernameData("testuser");
        assertNotNull(retrievedUser);
        assertEquals("testuser", retrievedUser.getUsername());
    }

    @Test
    public void testGetUsernameData_UserNotFound() {
        assertThrows(UserNotFoundException.class, () -> userService.getUsernameData("nonexistentuser"));
    }

    @Test
    public void testEditUsernameToJson() throws UserExistsException, UserNotFoundException {
        userService.editUsernameToJson("newusername");
        User updatedUser = userService.getUsernameData("newusername");
        assertEquals("newusername", updatedUser.getUsername());
    }

    @Test
    public void testAddBookToReadList() throws UserNotFoundException, UserExistsException {
        userService.addBookToReadList("book123");
        User updatedUser = userService.getUsernameData("testuser");
        assertTrue(updatedUser.getReadList().contains("book123"));
    }

    @Test
    public void testAddBookToBuyList() throws UserNotFoundException, UserExistsException {
        userService.addBookToBuyList("book456");
        User updatedUser = userService.getUsernameData("testuser");

        assertTrue(updatedUser.getBuyList().contains("book456"));
    }

    @Test
    public void testEditPasswordToJson() throws UserNotFoundException, UserExistsException {
        try {
            userService.editPasswordToJson("newpassword123", "password123");
        } catch (Exception e) {
            fail("Password edit failed: " + e.getMessage());
        }
        User updatedUser = userService.getUsernameData("testuser");
        assertEquals("newpassword123", updatedUser.getPassword());
    }

    @Test
    public void testEditPasswordToJson_IncorrectOldPassword() throws UserExistsException {
        assertThrows(IncorrectOldPassword.class, () -> userService.editPasswordToJson("newpassword123", "wrongpassword"));
    }

    @Test
    public void testEditPasswordToJson_EnterOldPassword() throws UserExistsException {
        assertThrows(EnterYourOldPassword.class, () -> userService.editPasswordToJson("newpassword123", ""));
    }

    @Test
    public void userSessionIsSetUp() throws UserExistsException {
        session.setUser(new User("testuser", "password123", new ArrayList<>(), new ArrayList<>()));
        assertTrue(session.getUser().getUsername().equals("testuser"));
    }
}
