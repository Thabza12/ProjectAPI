package Vico.ProjectAPI.repositories;

import Vico.ProjectAPI.domain.ProjectTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectTaskRepository extends PagingAndSortingRepository<ProjectTask, String> {

    @Query("{\"id\": {$regex : ?0, $options: 'i'}}")
    Page<ProjectTask> search(String keyword, Pageable pageable);

    @Query("{\"name\": {$regex : ?0, $options: 'i'}}")
    List<ProjectTask> search(String keyword);

    ProjectTask insert(ProjectTask projectTask);


//    ProjectTask insert(ProjectTask projectTask);
}
