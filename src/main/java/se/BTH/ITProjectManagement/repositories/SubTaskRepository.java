package se.BTH.ITProjectManagement.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import se.BTH.ITProjectManagement.models.SubTask;
import se.BTH.ITProjectManagement.models.Task;
import se.BTH.ITProjectManagement.models.User;

import java.util.List;
import java.util.Optional;

public interface SubTaskRepository extends MongoRepository<SubTask,String> {
    Optional<SubTask> findByName(@Param("name") String name);

    Optional<SubTask> findByUsers(@Param("user") User user);
}
