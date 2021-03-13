package Vico.ProjectAPI.controller;

import Vico.ProjectAPI.domain.Project;
import Vico.ProjectAPI.domain.User;
import Vico.ProjectAPI.exceptions.ProjectIdException;
import Vico.ProjectAPI.repositories.ProjectRepository;
import Vico.ProjectAPI.repositories.UserRepository;
import Vico.ProjectAPI.services.MapValidationErrorService;
import Vico.ProjectAPI.services.UserService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/User")
@Slf4j
public class UserController {

    @Getter
    @Autowired
    UserRepository repository;
    MapValidationErrorService mapValidationErrorService;
    UserService userService;

//    @Autowired
//    MapValidationErrorService mapValidationErrorService;


    @GetMapping(value = "/{page}/{perPage}/{sortOrder}/{sortField}")
    public Page<User> search(@RequestParam(required = false, value = "keyword") String keyword,
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
    public Iterable<User> list() {

        return repository.findAll();
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result){
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) return errorMap;

        User newUser = userService.saveUser(user);

        return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
    }

//    public User postUser(@RequestBody User user) throws ProjectIdException {
//        return repository.insert(user);
//    }

    @PutMapping(value = "/{id}")
    public User save(@RequestBody User user) throws ProjectIdException {
        return repository.save(user);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") Long id) throws ProjectIdException {
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
