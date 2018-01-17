package conference.service;

import conference.model.Role;
import conference.model.User;
import conference.repository.RoleRepository;
import conference.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.beans.Transient;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("userService")
public class UserServiceImpl implements UserDetailsService {
    // todo any @Autowired make private
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository; //todo ManyToMany in user

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void saveUser(User user) {
//        user.setPasswd(bCryptPasswordEncoder.encode(user.getPasswd()));
        String pass = bCryptPasswordEncoder.encode(user.getPasswd());
        user.setPasswd(pass);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findUserByName(userName);
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findOne(user.getRole()));
        List<GrantedAuthority> authorities = getUserAuthority(roles);

        return new org.springframework.security.core.userdetails
                .User(user.getName(), user.getPasswd(), true, true, true, true,  authorities);
    }

    private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
        Set<GrantedAuthority> roles = new HashSet<>();
        for (Role role : userRoles) {
            roles.add(new SimpleGrantedAuthority(role.getName()));
        }

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);
        return grantedAuthorities;
    }
}
