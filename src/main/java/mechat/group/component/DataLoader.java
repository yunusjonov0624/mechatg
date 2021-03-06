package mechat.group.component;

import mechat.group.entity.Role;
import mechat.group.entity.User;
import mechat.group.repository.RoleRepository;
import mechat.group.repository.UserRepo;
import org.hashids.Hashids;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepo userRepository;


    @Override
    public void run(String... args) throws Exception {
        Role roleOwner = new Role("1", "ROLE_OWNER");
        Role roleAdmin = new Role("2", "ROLE_ADMIN");
        Role roleUser = new Role("3", "ROLE_USER");
        try {
            roleRepository.save(roleOwner);
            roleRepository.save(roleAdmin);
            roleRepository.save(roleUser);
        } catch (Exception e) {
        }
        try {
            User user = new User();
            user.setFullname("PROJECT OWNER");
            user.setPassword(
                    passwordEncoder.encode(
                            "owner"
                    )
            );
            Set<Role> roles = new HashSet<>(roleRepository.findAll());
            user.setRoles(roles);
            user.setUsername("owner");
            user.setPhoneNumber("+998932099924");
            userRepository.save(user);
        } catch (Exception e) {
        }
    }


    private List<String> getRoleNames(Set<Role> userRoles) {
        List<String> result = new ArrayList<>();

        userRoles.forEach(role -> {
            result.add(role.getName());
        });

        return result;
    }
}
