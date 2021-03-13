package Vico.ProjectAPI.controller;

import Vico.ProjectAPI.domain.Project;
import Vico.ProjectAPI.exceptions.ProjectIdException;
import Vico.ProjectAPI.exceptions.ProjectIdExceptionResponse;
import Vico.ProjectAPI.repositories.ProjectRepository;
//import Vico.ProjectAPI.services.ProjectService;
//import Vico.ProjectAPI.services.ValidationErrorService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
//@Validated
@RequestMapping("/Project")
@Slf4j
public class ProjectController {

    @Getter
    @Autowired
    ProjectRepository repository;

    @GetMapping(value = "/{page}/{perPage}/{sortOrder}/{sortField}")
    public Page<Project> search(@RequestParam(required = false, value = "keyword") String keyword,
                             @PathVariable("page") int page,
                             @PathVariable("perPage") int perPage,
                             @PathVariable("sortOrder") Sort.Direction sort,
                             @PathVariable("sortField") String sortField) throws ProjectIdException {

        page = page - 1;
        PageRequest pageRequest = PageRequest.of(page, perPage, sort, sortField);
        if (keyword == null) {
            keyword = "";
        }
        return getRepository().search(keyword, pageRequest);
    }

    @GetMapping(value = "/all")
    public Iterable<Project> list() {

        return repository.findAll();
    }

    @PostMapping("/postProject")
    public Project postProject(@RequestBody Project project) throws ProjectIdException {
        if (project == project ){
            throw new ProjectIdException("project id exists");
        }
        return repository.insert(project);
    }

    @PutMapping(value = "/{id}")
    public Project save(@RequestBody Project project) throws ProjectIdException {
        return repository.save(project);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") String id) throws ProjectIdException {
        repository.deleteById(id);

    }

    @GetMapping(value = "/{id}")
    public String retrieve(@PathVariable("id") String id) throws ProjectIdException {
        Optional option = repository.findById(id);
        if (!option.isPresent()) {
            throw new ProjectIdException("item not found {id='" + id + "'}");
        }
        option.get();
        return id;
    }



//    @Autowired
//    ProjectService projectService;
//
//    @Autowired
//    ValidationErrorService validationErrorService;
//    String projectID;
//
//    @GetMapping
//    public Collection<Project> getProjects(){
//        return projectService.getProjects();
//    }
//
//    @PostMapping("")
//    public Object createNewProject(@Valid @RequestBody Project project, BindingResult result){
//
//        ResponseEntity<?> errorMap = validationErrorService.MapValidationService(result);
//        if(errorMap!=null) return errorMap;
//        return projectService.createProject(project);
//
//       // Project project1 = projectService.saveOrUpdateProject(project);
//        //return new ResponseEntity<Project>(project, HttpStatus.CREATED);
//    }
//
//    @GetMapping(value = "/{id}")
//    public Optional<Project> getProjectById(@PathVariable int id){
//        return projectService.getProjectById(id);
//    }

//    @DeleteMapping(value = "/{id}")
//    public ResponseEntity<?> deleteProject(@PathVariable int id) throws ProjectIdException {
//        projectService.deleteProjectById(id);
//
//        return new ResponseEntity<String>("Project with ID '"+id+"' was deleted", HttpStatus.OK);
//    }
}
