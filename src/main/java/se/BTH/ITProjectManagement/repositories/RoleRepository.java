package se.BTH.ITProjectManagement.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;
import se.BTH.ITProjectManagement.models.Role;
import se.BTH.ITProjectManagement.models.RoleName;

import java.util.Optional;

@Transactional
public interface RoleRepository extends MongoRepository<Role,String> {
    Role findByName(RoleName name);
}
