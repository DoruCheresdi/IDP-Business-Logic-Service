package com.alibou.security.service;

import com.alibou.security.dtos.ChangePasswordRequest;
import com.alibou.security.dtos.UserEditRequestDto;
import com.alibou.security.entities.User;
import com.alibou.security.repository.UserRepository;
import com.alibou.security.util.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.security.Principal;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {

        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        // check if the current password is correct
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong password");
        }
        // check if the two new passwords are the same
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Password are not the same");
        }

        // update the password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        // save the new password
        userRepository.save(user);
    }

    public Page<User> findAllUsersPaged(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Unable to find user"));
    }

    public void editUser(UserEditRequestDto userEditRequestDto, Principal principal) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Unable to find user"));

        user.setEmail(userEditRequestDto.getEmail());
        user.setFirstname(userEditRequestDto.getFirstName());
        user.setLastname(userEditRequestDto.getLastName());
        userRepository.save(user);
    }

    public void saveProfilePicture(MultipartFile multipartFile, Principal connectedUser) throws IOException {
        if (multipartFile.isEmpty()) {
            throw new ResponseStatusException(BAD_REQUEST, "Please select a file to upload");
        }
        if (multipartFile.getOriginalFilename() == null) {
            throw new ResponseStatusException(BAD_REQUEST, "File has no name");
        }
        User user = userRepository.findByEmail(connectedUser.getName()).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Unable to find user"));
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

        String uploadDir = "assets/user-photos/" + user.getId();
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

        user.setProfilePicture(fileName);
        userRepository.save(user);
    }

    public void saveCV(MultipartFile multipartFile, Principal connectedUser) throws IOException {
        if (multipartFile.isEmpty()) {
            throw new ResponseStatusException(BAD_REQUEST, "Please select a file to upload");
        }
        if (multipartFile.getOriginalFilename() == null) {
            throw new ResponseStatusException(BAD_REQUEST, "File has no name");
        }
        User user = userRepository.findByEmail(connectedUser.getName()).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Unable to find user"));
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

        String uploadDir = "assets/cv/" + user.getId();
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

        user.setCvPath(fileName);
        userRepository.save(user);
    }
}
