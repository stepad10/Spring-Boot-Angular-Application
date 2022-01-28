/*
 * UserServiceImplTest
 *
 * 0.1
 *
 * Author: J. Janský
 */

package cz.profinit.sportTeamManager.service.user;

import cz.profinit.sportTeamManager.configuration.StubRepositoryConfiguration;
import cz.profinit.sportTeamManager.exceptions.EntityAlreadyExistsException;
import cz.profinit.sportTeamManager.exceptions.EntityNotFoundException;
import cz.profinit.sportTeamManager.mappers.UserMapper;
import cz.profinit.sportTeamManager.model.user.Guest;
import cz.profinit.sportTeamManager.model.user.RegisteredUser;
import cz.profinit.sportTeamManager.model.user.RoleEnum;
import cz.profinit.sportTeamManager.repositories.user.UserRepository;
import cz.profinit.sportTeamManager.stubs.stubRepositories.user.StubUserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Unit tests for User service implementation
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = StubRepositoryConfiguration.class)
@ActiveProfiles({"stub_repository"})
public class UserServiceImplTest {
    private UserServiceImpl userService;
    private RegisteredUser user;
    private RegisteredUser user2;

    private static final String key = "AES";

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Before a test create new UserServiceImpl using stub repositories and create two stub users.
     */
    @Before
    public void setUp() {
        UserRepository userRepository = new StubUserRepository();

        userService = new UserServiceImpl(passwordEncoder, userRepository);
        user = new RegisteredUser("Ivan", "Stastny", "pass", "is@gmail.com", RoleEnum.USER);
        user2 = new RegisteredUser("Tomas", "Smutny", "pass", "ab@gmail.com", RoleEnum.USER);
    }

    /**
     * Tests a successful registration of the new user.
     */
    @Test
    public void newUserRegistration() throws EntityAlreadyExistsException {
        RegisteredUser newUser = userService.newUserRegistration(user2);
        assertEquals(user2.getName(), newUser.getName());
        assertEquals(user2.getSurname(), newUser.getSurname());
        assertEquals(user2.getEmail(), newUser.getEmail());
        assertEquals(user2.getRole(), newUser.getRole());
        assertNotEquals(user2.getPassword(), newUser.getPassword());
    }

    /**
     * Tests unsuccessful registration of already registered user.
     */
    @Test
    public void registrationOfExistingUser() {
        try {
            user = userService.newUserRegistration(user);
        } catch (Exception e) {
            assertEquals("Account with e-mail address " + user.getEmail() + "already exists.", e.getMessage());
        }
    }


    /**
     * Tests a successful user logging.
     */
    @Test
    public void userLogInSuccess() {
        RegisteredUser user = userService.userLogIn("is@gmail.com", "pass");
        assertEquals("is@gmail.com", user.getEmail());
        assertEquals("$2a$10$ruiQYEnc3bXdhWuCC/q.E.D.1MFk2thcPO/fVrAuFDuugjm3XuLZ2", user.getPassword());
        assertEquals("Stastny", user.getSurname());
        assertEquals("Ivan", user.getName());
        assertEquals(RoleEnum.USER, user.getRole());
    }

    /**
     * Tests an unsuccessful logging of non-existent user.
     */
    @Test
    public void userLogInNotExistingUser() {
        try {
            RegisteredUser user = userService.userLogIn("is@gmal.com", "pass");
            assertNull(user);
        } catch (Exception e) {
            assertEquals("User and password do not match", e.getMessage());
        }
    }

    /**
     * Tests an unsuccessful logging of existent user with .
     */
    @Test
    public void userLogInBadPassword() {
        try {
            RegisteredUser user = userService.userLogIn("is@gmail.com", "pass24");
            assertNull(user);
        } catch (Exception e) {
            assertEquals("User and password do not match", e.getMessage());
        }
    }

    /**
     * Testing creation of a new Guest invitation. Positive ending
     * @throws EntityNotFoundException Thrown when event is not found
     */
    @Test
    public void createNewGuestCreatesNewGuest() throws EntityNotFoundException {
        Guest guest = userService.createNewGuest("Karel", 0L);
        assertEquals("Karel",guest.getName());
        assertEquals(RoleEnum.GUEST,guest.getRole());
        assertEquals("mxPR4fbWzvai60UMLhD3aw==",guest.getUri());
    }

    /**
     * Testing findGuestByUri finds guest by given URI. Positive ending
     * @throws EntityNotFoundException thrown when Guest is not found
     */
    @Test
    public void findGuestByUriFindsGuestByUri() throws EntityNotFoundException {
       Guest guest =  userService.findGuestByUri("mxPR4fbWzvai60UMLhD3aw==");
        assertEquals("Karel",guest.getName());
        assertEquals(RoleEnum.GUEST,guest.getRole());
        assertEquals("mxPR4fbWzvai60UMLhD3aw==",guest.getUri());
    }

    /**
     * Testing findGuestBuUri throws exception when Guest is not found
     */
    @Test
    public void findGuestByUriThrownEntityNotFoundExceptionForNonExistentGuest() {
        try {
            userService.findGuestByUri("mxPR4fbWzvai60UMLhD3aw==");
        } catch (EntityNotFoundException e) {
            assertEquals("Guest entity not found!", e.getMessage());
        }
    }
}