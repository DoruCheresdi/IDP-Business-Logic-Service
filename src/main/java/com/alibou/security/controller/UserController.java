package com.alibou.security.controller;

import com.alibou.security.dtos.ChangePasswordRequest;
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
@RequestMapping("/api/v1/users")
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
    public ResponseEntity<Page<User>> findAllUsersPaged(@ParameterObject @Valid Pageable pageable) {
        Page<User> users = userService.findAllUsersPaged(pageable);
        return ResponseEntity.ok(users);
    }
}
