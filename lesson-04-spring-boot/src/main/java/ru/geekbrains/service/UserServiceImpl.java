package ru.geekbrains.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.persist.User;
import ru.geekbrains.persist.UserRepository;
import ru.geekbrains.persist.UserSpecification;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserRepr> findAll() {
        return userRepository.findAll().stream()
                .map(UserRepr::new)
                .collect(Collectors.toList());
    }

    @Override
    public Page<UserRepr> findWithFilter(String usernameFilter, Integer minAge, Integer maxAge,
                                         Integer page, Integer size, String sortField) {
        Specification<User> spec = Specification.where(null);
        if (usernameFilter != null && !usernameFilter.isBlank()) {
            spec = spec.and(UserSpecification.usernameLike(usernameFilter));
        }
        if (minAge != null) {
            spec = spec.and(UserSpecification.minAge(minAge));
        }
        if (maxAge != null) {
            spec = spec.and(UserSpecification.maxAge(maxAge));
        }
        if (sortField != null && !sortField.isBlank()) {
            return userRepository.findAll(spec, PageRequest.of(page, size, Sort.by(sortField)))
                    .map(UserRepr::new);
        }
        return userRepository.findAll(spec, PageRequest.of(page, size))
                .map(UserRepr::new);
    }

    @Transactional
    @Override
    public Optional<UserRepr> findById(long id) {
       return userRepository.findById(id)
                .map(UserRepr::new);
    }

    @Transactional
    @Override
    public void save(UserRepr user) {
        User userToSave = new User(user);
        userToSave.setPassword(passwordEncoder.encode(userToSave.getPassword()));
        userRepository.save(userToSave);
        if (user.getId() == null) {
            user.setId(userToSave.getId());
        }
    }

    @Transactional
    @Override
    public void delete(long id) {
        userRepository.deleteById(id);
    }
}
