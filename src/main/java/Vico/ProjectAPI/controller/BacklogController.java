package Vico.ProjectAPI.controller;

import Vico.ProjectAPI.domain.Backlog;
import Vico.ProjectAPI.exceptions.ProjectIdException;
import Vico.ProjectAPI.repositories.BacklogRepository;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/Backlog")
@Slf4j
public class BacklogController {

    @Getter
    @Autowired
    BacklogRepository repository;

    @GetMapping(value = "/{page}/{perPage}/{sortOrder}/{sortField}")
    public Page<Backlog> search(@RequestParam(required = false, value = "keyword") String keyword,
                                @PathVariable("page") int page,
                                @PathVariable("perPage") int perPage,
                                @PathVariable("sortOrder") Sort.Direction sort,
                                @PathVariable("sortField") String sortField) {

        page = page - 1;
        PageRequest pageRequest = PageRequest.of(page, perPage, sort, sortField);
        if (keyword == null) {
            keyword = "";
        }
        return getRepository().search(keyword, pageRequest);
    }

    @GetMapping(value = "/all")
    public Iterable<Backlog> list() {

        return repository.findAll();
    }

    @PostMapping("/postBacklog")
    public Backlog postBacklog(@RequestBody Backlog backlog)  {
        return repository.insert(backlog);
    }

    @PutMapping(value = "/{id}")
    public Backlog save(@RequestBody Backlog backlog) {
        return repository.save(backlog);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") Long id)  {
        repository.deleteById(id);

    }

    @GetMapping(value = "/{id}")
    public Long retrieve(@PathVariable("id") Long id) throws ProjectIdException {
        Optional option = repository.findById(id);
        if (!option.isPresent()) {
            throw new ProjectIdException("item not found {id='" + id + "'}");
        }
        option.get();
        return id;
    }
}
