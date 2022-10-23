package com.exercise.bci.service;

import com.exercise.bci.dto.*;

import java.time.LocalDateTime;
import java.util.*;

import com.exercise.bci.entity.Phone;
import com.exercise.bci.entity.User;

import com.exercise.bci.exceptions.*;
import com.exercise.bci.repository.UserRepository;
import com.exercise.bci.security.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.exercise.bci.enums.Constants.BEARER_PREFIX;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private Token jwtToken;

    @Override
    public UserMetadataDTO createUser(UserDataDTO req) throws InvalidDataException, UserAlreadyExistsException, UnexpectedException {
        if (!req.getEmail().isValid() || !req.getPassword().isValid()) {
            throw new InvalidDataException("Email or password is invalid");
        }

        Optional<User> userFound = Optional.ofNullable(repository.findByEmail(req.getEmail().getValue()));
        if (userFound.isPresent()) {
            throw new UserAlreadyExistsException("User " + req.getEmail().getValue() + " already exists");
        }

        User user = new User(req.getName(), req.getEmail().getValue(), req.getPassword().getValueEncrypted(),
                toPhoneEntityList(req.getPhones()), toJwtToken(req.getEmail().getValue()));
        user.getPhones().forEach(phone -> phone.setUserPhone(user));
        User userSaved = repository.save(user);

        return toResponseDTO(userSaved);
    }

    private List<Phone> toPhoneEntityList(List<PhoneDTO> phones) {
        List<Phone> phoneEntityList = new ArrayList<>();
        if (phones == null) {
            return phoneEntityList;
        }
        phones.forEach(p -> phoneEntityList.add(Phone.convertFrom(p)));
        return phoneEntityList;
    }

    private UserMetadataDTO toResponseDTO(User user) {
        return new UserMetadataDTO(user.getUserId(), user.getCreated(), user.getLastLogin(),
                user.getToken(), user.getIsActive());
    }

    private String toJwtToken(String username) throws UnexpectedException {
        return jwtToken.generate(username);
    }

    public String getUsernameFrom(String authHeader) throws InvalidAuthenticationCredentialsException {
        String prefix = BEARER_PREFIX.getValue();
        if (authHeader != null && authHeader.startsWith(prefix)) {
            String token = authHeader.replace(prefix, "");
            return jwtToken.getClaimsFromToken(token).getSubject();
        } else {
            throw new InvalidAuthenticationCredentialsException("Invalid or missing authentication header");
        }
    }

    @Override
    public LoginResponseDTO getUserInfo(String username) throws UserNotFoundException, UnexpectedException {
        Optional<User> userFound = Optional.ofNullable(repository.findByEmail(username));
        if (!userFound.isPresent()) {
            throw new UserNotFoundException("User " + username + " not found");
        }
        User user = userFound.get();
        user.setToken(toJwtToken(username));
        user.setLastLogin(LocalDateTime.now());
        repository.save(user);
        return toLoginResponseDTO(user);
    }

    private LoginResponseDTO toLoginResponseDTO(User user) {
        UserDataDTO userData = new UserDataDTO(user.getName(), new EmailDTO(user.getEmail()),
                new PasswordDTO(user.getPassword()), toPhoneDTOList(user.getPhones()));
        UserMetadataDTO metadata = new UserMetadataDTO(user.getUserId(), user.getCreated(), user.getLastLogin(),
                user.getToken(), user.getIsActive());

        return new LoginResponseDTO(metadata, userData);
    }

    private List<PhoneDTO> toPhoneDTOList(List<Phone> phones) {
        List<PhoneDTO> phoneDTOList = new ArrayList<>();
        if (phones == null) {
            return phoneDTOList;
        }
        phones.forEach(p -> phoneDTOList.add(PhoneDTO.convertFrom(p)));
        return phoneDTOList;
    }
}
