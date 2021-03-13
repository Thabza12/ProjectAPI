package Vico.ProjectAPI.repositories;

import Vico.ProjectAPI.domain.Backlog;
import Vico.ProjectAPI.domain.ProjectTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BacklogRepository extends PagingAndSortingRepository<Backlog, Long> {

    @Query("{\"id\": {$regex : ?0, $options: 'i'}}")
    Page<Backlog> search(String keyword, Pageable pageable);

    @Query("{\"name\": {$regex : ?0, $options: 'i'}}")
    List<Backlog> search(String keyword);

    Backlog insert(Backlog backlog);

}
