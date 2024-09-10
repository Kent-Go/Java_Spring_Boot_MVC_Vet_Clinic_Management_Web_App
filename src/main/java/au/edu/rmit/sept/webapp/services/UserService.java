package au.edu.rmit.sept.webapp.services;

import java.util.Collection;

import au.edu.rmit.sept.webapp.models.User;

public interface UserService {
  // Get all the vets
  public Collection<User> getAllUsers();

  // Get a user by their ID
  public User getUserByUserID(int userID);

  // Create a new user
  public User createUser(User user);
  
  // Get a user by their email
  public User getUserByEmail(String email);

  // Delete a user by their ID
  public void deleteUserByUserID(int userID);
}