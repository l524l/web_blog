package com.l524l.web_blog.service.impl;

import com.l524l.web_blog.exception.EmailExistError;
import com.l524l.web_blog.exception.PasswordConfirmError;
import com.l524l.web_blog.exception.UserExistError;
import com.l524l.web_blog.models.User;
import com.l524l.web_blog.models.enumes.Role;
import com.l524l.web_blog.repo.UserRepository;
import com.l524l.web_blog.service.MailSender;
import com.l524l.web_blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    final private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Value("${https.domain}")
    private String domain;

    @Autowired
    private MailSender mailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void deleteUser(long ID) {
        userRepository.deleteById(ID);
    }

    @Override
    public List<User> getByName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public User getById(long ID) {
        return userRepository.findById(ID).get();
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User saveUser(User user) throws PasswordConfirmError, UserExistError, EmailExistError {
        if (getByEmail(user.getEmail()) != null)throw new EmailExistError();
        if (!user.isPasswordCorrect()) throw new PasswordConfirmError();
        if (userRepository.findByName(user.getName()).isEmpty()){
            user.setPassword(passwordEncoder.encode(user.getPassword().trim()));
            user.setName(user.getName().trim());
            user.setEmail(user.getEmail().trim());
            user.setActivationCode(UUID.randomUUID().toString());
            user.setRoles(Collections.singleton(Role.USER));

            if (!StringUtils.isEmpty(user.getEmail())){
                String message = String.format("Activation link for %s: https://%s/activate/%s", user.getName(), domain,
                        user.getActivationCode());
                mailSender.sendEmail(user.getEmail(),"Activation code",message);
            }
            return userRepository.save(user);
        }
        throw new UserExistError();
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public boolean activateUser(String code) {
        User user = userRepository.findByActivationCode(code);
        if (user == null){
            return false;
        }
        user.setActivationCode(null);
        user.setEnable(true);
        userRepository.save(user);
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByName(s).get(0);
    }
}
