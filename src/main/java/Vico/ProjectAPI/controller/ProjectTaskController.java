package Vico.ProjectAPI.controller;

import Vico.ProjectAPI.domain.Project;
import Vico.ProjectAPI.domain.ProjectTask;
import Vico.ProjectAPI.exceptions.ProjectIdException;
import Vico.ProjectAPI.repositories.ProjectRepository;
import Vico.ProjectAPI.repositories.ProjectTaskRepository;
//import Vico.ProjectAPI.services.ProjectTaskService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("ProjectTask")
@Slf4j
public class ProjectTaskController {

    @Getter
    @Autowired
    ProjectTaskRepository projectTaskRepository;

    @GetMapping(value = "/{page}/{perPage}/{sortOrder}/{sortField}")
    public Page<ProjectTask> search(@RequestParam(required = false, value = "keyword") String keyword,
                                @PathVariable("page") int page,
                                @PathVariable("perPage") int perPage,
                                @PathVariable("sortOrder") Sort.Direction sort,
                                @PathVariable("sortField") String sortField) throws ProjectIdException {

        page = page - 1;
        PageRequest pageRequest = PageRequest.of(page, perPage, sort, sortField);
        if (keyword == null) {
            keyword = "";
        }
        return getProjectTaskRepository().search(keyword, pageRequest);
    }

    @GetMapping(value = "/all")
    public Iterable<ProjectTask> list() {

        return projectTaskRepository.findAll();
    }

    @PostMapping("/postProjectTask")
    public ProjectTask postProjectTask(@RequestBody ProjectTask projectTask) throws ProjectIdException {
        return projectTaskRepository.insert(projectTask);
    }

    @PutMapping(value = "/{id}")
    public ProjectTask save(@RequestBody ProjectTask projectTask)  {
        return projectTaskRepository.save(projectTask);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") String id)  {
        projectTaskRepository.deleteById(id);

    }

    @GetMapping(value = "/{id}")
    public String retrieve(@PathVariable("id") String id)  {
        Optional option = projectTaskRepository.findById(id);
        if (!option.isPresent()) {
            throw new ProjectIdException("item not found {id='" + id + "'}");
        }
        option.get();
        return id;
    }

//    @Autowired
//    ProjectTaskService service;
//
//    @GetMapping
//    public Collection<ProjectTask> getPerson() {
//
//        return service.getProjectTask();
//    }
//
//    @PostMapping("/postProjectTask")
//    public ProjectTask postProjectTask(@RequestBody ProjectTask projectTask) throws ProjectIdException {
//        return service.createProjectTask(projectTask);
//    }
//
//    @PutMapping(value = "/{id}")
//    public ProjectTask save(@RequestBody ProjectTask projectTask) throws ProjectIdException {
//        return service.save(projectTask);
//    }


}
