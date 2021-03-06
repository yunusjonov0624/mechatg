package mechat.group.repository;

import mechat.group.entity.Admins;
import mechat.group.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepo extends JpaRepository<Admins, String> {
    Admins findByUsername(String username);
    Optional<Admins> findById(String id);
}
