package Vico.ProjectAPI.repositories;

import Vico.ProjectAPI.domain.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends PagingAndSortingRepository<Project, String> {

    @Query("{\"projectName\": {$regex : ?0, $options: 'i'}}")
    Page<Project> search(String keyword, Pageable pageable);

    @Query("{\"name\": {$regex : ?0, $options: 'i'}}")
    List<Project> search(String keyword);

    Project insert(Project project);

//    @Override
//    List<Project> findAll();
//    Optional<Project> findByProjectId(int id);
//
//    void delete(Optional<Project> project);
}
