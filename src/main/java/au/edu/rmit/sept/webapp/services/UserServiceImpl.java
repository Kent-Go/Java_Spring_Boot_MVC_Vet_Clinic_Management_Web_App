package au.edu.rmit.sept.webapp.services;

import java.util.Collection;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import au.edu.rmit.sept.webapp.models.User;
import au.edu.rmit.sept.webapp.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

  private UserRepository userRepository;

  @Autowired
  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public Collection<User> getAllUsers() {
    return userRepository.findAll();
  }

  @Override
  public User getUserByUserID(int userID) {
    return userRepository.findById(userID).orElseThrow(() -> new RuntimeException("User not found"));
  }

  @Override
  public User createUser(User user) {
    if (user == null) {
      throw new IllegalArgumentException("User cannot be null");
    }
    return userRepository.save(user);
  }

  @Override
  public User getUserByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  @Override
  public void deleteUserByUserID(int userID) {
    userRepository.deleteById(userID);
  }

  @Override
  public void updatePassword(String email, String newPassword) {
    User user = userRepository.findByEmail(email);
    user.setPassword(newPassword);
    userRepository.save(user);
  }
}