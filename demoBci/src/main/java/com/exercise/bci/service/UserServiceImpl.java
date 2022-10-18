package com.exercise.bci.service;

import com.exercise.bci.dto.PhoneDTO;
import com.exercise.bci.dto.RequestDTO;
import com.exercise.bci.dto.ResponseDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.exercise.bci.entity.Phone;
import com.exercise.bci.entity.User;
import com.exercise.bci.exceptions.InvalidDataException;
import com.exercise.bci.exceptions.UserAlreadyExistsException;
import com.exercise.bci.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Override
    public ResponseDTO CreateUser(RequestDTO req) throws InvalidDataException, UserAlreadyExistsException {
        if (!req.getEmail().isValid() || !req.getPassword().isValid()) {
            throw new InvalidDataException("Email or password is invalid");
        }

        Optional<User> userFound = Optional.ofNullable(repository.findByEmail(req.getEmail().getValue()));
        if (userFound.isPresent()) {
            throw new UserAlreadyExistsException("User " + req.getEmail().getValue() + " already exists");
        }

        User user = new User(req.getName(), req.getEmail().getValue(), req.getPassword().getValueEncrypted(), toPhoneEntityList(req.getPhones()));
        user.getPhones().forEach(phone -> phone.setUserPhone(user));
        User userSaved = repository.save(user);

        return toResponseDTO(userSaved);
    }

    private List<Phone> toPhoneEntityList(List<PhoneDTO> phones) {
        if (phones == null) {
            return new ArrayList<>();
        }
        List<Phone> phoneEntityList = new ArrayList<>();
        phones.forEach(p -> {
            phoneEntityList.add(new Phone(p.getNumber(), p.getCityCode(), p.getCountryCode()));
        });
        return phoneEntityList;
    }

    private ResponseDTO toResponseDTO(User user) {
        ResponseDTO res = new ResponseDTO();
        res.setId(user.getUserId());
        res.setCreated(user.getCreated());
        res.setLastLogin(user.getLastLogin());
        res.setIsActive(user.getIsActive());
        res.setToken(user.getToken());
        return res;
    }
}
