package com.search_partners.service;

import com.search_partners.AuthorizedUser;
import com.search_partners.model.City;
import com.search_partners.model.Country;
import com.search_partners.model.User;
import com.search_partners.repository.UserRepository;
import com.search_partners.to.ChangePasswordDto;
import com.search_partners.to.UserProfileDto;
import com.search_partners.to.UserRegisterDto;
import com.search_partners.util.UserUtil;
import com.search_partners.util.exception.ErrorCheckRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final CountryAndCityService service;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository repository, CountryAndCityService service) {
        this.repository = repository;
        this.service = service;
//        passwordEncoder = new BCryptPasswordEncoder();
        passwordEncoder = NoOpPasswordEncoder.getInstance();
    }

    @Override
    public User getUserWithCity(long id) {
        User user = repository.getUserWithCity(id).orElse(null);
        //TODO: check if not found
        return user;
    }

    @Override
    @Transactional
    public void saveUserProfile(UserProfileDto userDto, long id) {
        User user = getUser(id);
        Country country = service.getCountry(userDto.getCountry());
        City city = service.getCity(userDto.getCity());
        //TODO: check if not found User, Country and City
        user.setRealName(userDto.getRealName());
        user.setBusyness(userDto.getBusyness());
        user.setCountry(country);
        user.setCity(city);
        user.setGender(userDto.getGender());
        user.setDay(userDto.getDay());
        user.setMonth(userDto.getMonth());
        user.setYear(userDto.getYear());
        repository.save(user);
    }

    @Override
    @Transactional
    public void createUser(UserRegisterDto newUser) {
        if (!newUser.getPassword().equals(newUser.getConfirmPassword()))
            throw new ErrorCheckRequestException("Пароли не совпадают!");

        User user = UserUtil.createNewFromTo(newUser);
        Country country = service.getCountry(newUser.getCountry());
        City city = service.getCity(newUser.getCity());

        if (Objects.isNull(country) || Objects.isNull(city))
            throw new ErrorCheckRequestException("Ошибка создания пользователя!");
        User currentUser = repository.findByEmail(user.getEmail()).orElse(null);

        if (Objects.isNull(currentUser)) {
            user.setPassword(UserUtil.prepareToPassword(newUser.getPassword(), passwordEncoder));
            user.setCountry(country);
            user.setCity(city);
            repository.save(user);
        } else {
            throw new ErrorCheckRequestException("Ошибка создания пользователя!");
        }
    }

    @Override
    @Transactional
    public void changePassword(ChangePasswordDto request, long id) {
        User user = getUser(id);
        if (Objects.nonNull(user)) {
            if(!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword()))
                throw new ErrorCheckRequestException("Текущий пароль не совпадает!");
            if (!request.getNewPassword().equals(request.getConfirmPassword()))
                throw new ErrorCheckRequestException("Новые пароли не совпадают!");

            user.setPassword(UserUtil.prepareToPassword(request.getNewPassword(), passwordEncoder));
            repository.save(user);

        } else {
            throw new ErrorCheckRequestException("Ошибка смены пароля!");
        }
    }

    @Override
    public User getUser(long id) {
        User user = repository.findById(id).orElse(null);
        //TODO: check if not found
        return user;
    }

    @Override
    public AuthorizedUser loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.findByEmail(email.toLowerCase()).orElse(null);
        //TODO: check if not found

        return new AuthorizedUser(user);
    }

    @Override
    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }
}