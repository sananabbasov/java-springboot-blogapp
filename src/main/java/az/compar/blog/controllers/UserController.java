package az.compar.blog.controllers;

import az.compar.blog.payloads.ApiResponse;
import az.compar.blog.payloads.UserDto;
import az.compar.blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {


    @Autowired
    private UserService userService;


    @PostMapping("/create")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        UserDto createUserDto = userService.createUser(userDto);
        return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable("userId") Integer uid) {
        UserDto updatedUser = userService.updateUser(userDto, uid);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<?> removeUser(@PathVariable("userId") Integer uid) {
        userService.deleteUser(uid);
        return new ResponseEntity(new ApiResponse("User deleted successfully", false), HttpStatus.OK);
    }

    @GetMapping("/getall")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }


    @GetMapping("/get/{userId}")
    public ResponseEntity<UserDto> getById(@PathVariable("userId") Integer uid)
    {
        return ResponseEntity.ok(userService.getUserById(uid));
    }
}
