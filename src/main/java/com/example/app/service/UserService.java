package com.example.app.service;

import java.time.LocalDate;
import java.util.List;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.app.model.City;
import com.example.app.model.User;
import com.example.app.repository.CityRepository;
import com.example.app.repository.UserRepository;

/**
 * The application's one service. Storm's repositories join the Spring transaction the annotation
 * opens, so {@code @Transactional} is all the transaction management this needs.
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final CityRepository cityRepository;

    public UserService(UserRepository userRepository, CityRepository cityRepository) {
        this.userRepository = userRepository;
        this.cityRepository = cityRepository;
    }

    /** Everyone living in the named city. A read-only transaction; the database is told so. */
    @Transactional(readOnly = true)
    public List<User> findInCity(String cityName) {
        return userRepository.findByCityName(cityName);
    }

    /**
     * Registers a user, creating the city on first use. Both writes are in one transaction, so a
     * failure leaves neither behind.
     */
    @Transactional
    public User register(String email,
                         @Nullable LocalDate birthDate,
                         String street,
                         String postalCode,
                         String cityName) {
        City city = cityRepository.findByName(cityName)
                .orElseGet(() -> cityRepository.insertAndFetch(new City(cityName)));
        return userRepository.insertAndFetch(new User(email, birthDate, street, postalCode, city));
    }
}
