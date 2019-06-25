package se.BTH.ITProjectManagement.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import se.BTH.ITProjectManagement.models.RoleName;
import se.BTH.ITProjectManagement.models.User;
import se.BTH.ITProjectManagement.repositories.RoleRepository;
import se.BTH.ITProjectManagement.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(User user) {
        List list=new ArrayList();
        list.add(roleRepository.findByName(RoleName.ROLE_USER));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(new ArrayList<>(list));
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        User user=userRepository.findByUsername(username);
        return user;
    }

    @Override
    public User findByRoles(RoleName role) {
        User user = userRepository.findByRoles(RoleName.ROLE_USER);
        return user;
    }
}