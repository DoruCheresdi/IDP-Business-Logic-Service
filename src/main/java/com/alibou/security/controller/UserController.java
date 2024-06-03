package com.alibou.security.controller;

import com.alibou.security.dtos.ChangePasswordRequest;
import com.alibou.security.dtos.UserEditRequestDto;
import com.alibou.security.dtos.UserReturnDto;
import com.alibou.security.entities.User;
import com.alibou.security.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PatchMapping
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest request,
            Principal connectedUser
    ) {
        userService.changePassword(request, connectedUser);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/paged")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<Page<UserReturnDto>> findAllUsersPaged(@ParameterObject @Valid Pageable pageable) {
        Page<User> users = userService.findAllUsersPaged(pageable);
        Page<UserReturnDto> dtos = users.map(UserReturnDto::new);
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/data")
    public ResponseEntity<UserReturnDto> findUserData(Principal connectedUser) {
        User user = userService.findUserByEmail(connectedUser.getName());
        return ResponseEntity.ok(new UserReturnDto(user));
    }

    @PutMapping("/edit")
    public ResponseEntity<?> editUser(@RequestBody UserEditRequestDto userEditRequestDto, Principal principal) {
        userService.editUser(userEditRequestDto, principal);
        return ResponseEntity.ok().build();
    }
}
