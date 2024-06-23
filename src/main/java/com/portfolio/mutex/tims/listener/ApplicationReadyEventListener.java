package com.portfolio.mutex.tims.listener;

import com.portfolio.mutex.tims.entity.User;
import com.portfolio.mutex.tims.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplicationReadyEventListener implements ApplicationListener<ApplicationReadyEvent> {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Value("${app.default.email}")
  private String defaultEmail;

  @Value("${app.default.password}")
  private String defaultPassword;

  /**
   * {@inheritDoc}
   */
  @Override
  public void onApplicationEvent(@NonNull ApplicationReadyEvent event) {
    // Persist the default user on initial start up
    if (userRepository.count() == 0) {
      User user = new User();
      user.setLastname("Administrator");
      user.setFirstname("Default");
      user.setEmail(defaultEmail);
      user.setPassword(passwordEncoder.encode(defaultPassword));
      user.setActive(true);
      user.setSystemAdmin(true);

      userRepository.save(user);
    }
  }
}
