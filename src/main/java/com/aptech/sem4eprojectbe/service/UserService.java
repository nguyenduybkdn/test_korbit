package com.aptech.sem4eprojectbe.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.aptech.sem4eprojectbe.common.model.IdModel;
import com.aptech.sem4eprojectbe.entity.UserEntity;
import com.aptech.sem4eprojectbe.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptEncoder;

    @CacheEvict(value = "users", allEntries = true)
    public UserEntity insertUser(UserEntity user) {
        user.setPassword(bCryptEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Cacheable("users")
    public List<UserEntity> getAllUsers() {
        System.out.println(">>> Call database all users");
        return userRepository.findByDeletedIsFalse();
    }

    @Cacheable(value = "user", key = "#idModel.getId()")
    public Optional<UserEntity> getUserById(IdModel idModel) {
        System.out.println(">>> Call database user id " + idModel.getId());
        return userRepository.findById(idModel.getId());
    }

    @Caching(evict = { @CacheEvict(value = "users", allEntries = true) }, put = {
            @CachePut(value = "user", key = "#user.getId()") })
    public UserEntity updateUser(UserEntity user) {
        return userRepository.findById(user.getId())
                .map(userItem -> {
                    userItem.setFirstname(user.getFirstname());
                    userItem.setLastname(user.getLastname());
                    userItem.setAddress(user.getAddress());
                    userItem.setDistrict(user.getDistrict());
                    userItem.setCity(user.getCity());
                    userItem.setRole(user.getRole());
                    userItem.setPhone(user.getPhone());
                    userItem.setEmail(user.getEmail());
                    userItem.setPassword(bCryptEncoder.encode(user.getPassword()));
                    userItem.setDeleted(user.getDeleted());
                    return userRepository.save(userItem);
                })
                .orElseGet(() -> {
                    UserEntity newUser = new UserEntity();
                    newUser.setFirstname(user.getFirstname());
                    newUser.setLastname(user.getLastname());
                    newUser.setAddress(user.getAddress());
                    newUser.setDistrict(user.getDistrict());
                    newUser.setCity(user.getCity());
                    newUser.setRole(user.getRole());
                    newUser.setPhone(user.getPhone());
                    newUser.setEmail(user.getEmail());
                    newUser.setPassword(bCryptEncoder.encode(user.getPassword()));
                    newUser.setDeleted(false);
                    return userRepository.save(newUser);
                });
    }

    @Caching(evict = { @CacheEvict(value = "users", allEntries = true) }, put = {
            @CachePut(value = "user", key = "#user.getId()") })
    public UserEntity updateUserWithoutPassword(UserEntity user) {
        return userRepository.findById(user.getId())
                .map(userItem -> {
                    userItem.setFirstname(user.getFirstname());
                    userItem.setLastname(user.getLastname());
                    userItem.setAddress(user.getAddress());
                    userItem.setDistrict(user.getDistrict());
                    userItem.setCity(user.getCity());
                    userItem.setRole(user.getRole());
                    userItem.setPhone(user.getPhone());
                    userItem.setEmail(user.getEmail());
                    userItem.setDeleted(user.getDeleted());
                    return userRepository.save(userItem);
                })
                .orElseGet(() -> {
                    UserEntity newUser = new UserEntity();
                    newUser.setFirstname(user.getFirstname());
                    newUser.setLastname(user.getLastname());
                    newUser.setAddress(user.getAddress());
                    newUser.setDistrict(user.getDistrict());
                    newUser.setCity(user.getCity());
                    newUser.setRole(user.getRole());
                    newUser.setPhone(user.getPhone());
                    newUser.setEmail(user.getEmail());
                    newUser.setPassword(bCryptEncoder.encode(user.getPassword()));
                    newUser.setDeleted(false);
                    return userRepository.save(newUser);
                });
    }

}
