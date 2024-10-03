package au.edu.rmit.sept.webapp.services;

import au.edu.rmit.sept.webapp.models.User;
import au.edu.rmit.sept.webapp.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceImplTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userService;

    // Positive Test: Test getUserByUserID with valid user ID
    @Test
    void testGetUserByUserID_ValidID() {
        int userID = 1;
        User mockUser = new User("John", "Doe", LocalDate.of(1990, 1, 1), "Male", "123456789", "john.doe@example.com",
                "password123");

        when(userRepository.findById(userID)).thenReturn(Optional.of(mockUser));

        User user = userService.getUserByUserID(userID);

        assertNotNull(user);
        assertEquals("John", user.getFirstName());
        verify(userRepository, times(1)).findById(userID);
    }

    // Negative Test: Test getUserByUserID with invalid user ID
    @Test
    void testGetUserByUserID_InvalidID() {
        int userID = 999;

        when(userRepository.findById(userID)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.getUserByUserID(userID);
        });

        assertEquals("User not found", exception.getMessage());
        verify(userRepository, times(1)).findById(userID);
    }

    // Boundary Test: Test getUserByUserID with boundary value
    @Test
    void testGetUserByUserID_BoundaryID() {
        int userID = Integer.MAX_VALUE;
        User mockUser = new User("Jane", "Doe", LocalDate.of(1990, 1, 1), "Female", "123456789", "jane.doe@example.com",
                "password123");

        when(userRepository.findById(userID)).thenReturn(Optional.of(mockUser));

        User user = userService.getUserByUserID(userID);

        assertNotNull(user);
        assertEquals("Jane", user.getFirstName());
        verify(userRepository, times(1)).findById(userID);
    }

    // Positive Test: Test createUser
    @Test
    void testCreateUser() {
        User newUser = new User("Alice", "Smith", LocalDate.of(1992, 2, 2), "Female", "987654321",
                "alice.smith@example.com", "securePass");

        when(userRepository.save(newUser)).thenReturn(newUser);

        User savedUser = userService.createUser(newUser);

        assertNotNull(savedUser);
        assertEquals("Alice", savedUser.getFirstName());
        verify(userRepository, times(1)).save(newUser);
    }

    // Negative Test: Test createUser with null user
    @Test
    void testCreateUser_NullUser() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(null);
        });

        assertEquals("User cannot be null", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    // Positive Test: Test getUserByEmail with valid email
    @Test
    void testGetUserByEmail_Valid() {
        String email = "test.email@example.com";
        User mockUser = new User("Test", "User", LocalDate.of(1991, 1, 1), "Male", "999999999", email, "password");

        when(userRepository.findByEmail(email)).thenReturn(mockUser);

        User user = userService.getUserByEmail(email);

        assertNotNull(user);
        assertEquals(email, user.getEmail());
        verify(userRepository, times(1)).findByEmail(email);
    }

    // Negative Test: Test getUserByEmail with non-existent email
    @Test
    void testGetUserByEmail_NonExistentEmail() {
        String email = "non.existent@example.com";

        when(userRepository.findByEmail(email)).thenReturn(null);

        User user = userService.getUserByEmail(email);

        assertNull(user);
        verify(userRepository, times(1)).findByEmail(email);
    }

    // Positive Test: Test deleteUserByUserID with valid ID
    @Test
    void testDeleteUserByUserID_ValidID() {
        int userID = 1;

        doNothing().when(userRepository).deleteById(userID);

        userService.deleteUserByUserID(userID);

        verify(userRepository, times(1)).deleteById(userID);
    }

    // Negative Test: Test deleteUserByUserID with invalid ID
    @Test
    void testDeleteUserByUserID_InvalidID() {
        int userID = 999;

        doThrow(new RuntimeException("User not found")).when(userRepository).deleteById(userID);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.deleteUserByUserID(userID);
        });

        assertEquals("User not found", exception.getMessage());
        verify(userRepository, times(1)).deleteById(userID);
    }
}
