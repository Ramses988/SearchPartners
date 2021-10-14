package com.search_partners.service;

import com.search_partners.AuthorizedUser;
import com.search_partners.model.City;
import com.search_partners.model.ConfirmToken;
import com.search_partners.model.Country;
import com.search_partners.model.User;
import com.search_partners.repository.UserRepository;
import com.search_partners.service.interfaces.ConfirmTokenService;
import com.search_partners.service.interfaces.CountryAndCityService;
import com.search_partners.service.interfaces.MailSenderService;
import com.search_partners.service.interfaces.UserService;
import com.search_partners.to.ChangePasswordDto;
import com.search_partners.to.ContactDto;
import com.search_partners.to.UserProfileDto;
import com.search_partners.to.UserRegisterDto;
import com.search_partners.util.EmailMessageUtil;
import com.search_partners.util.UserUtil;
import com.search_partners.util.exception.ErrorCheckRequestException;
import com.search_partners.util.exception.ErrorNotFoundPageException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final CountryAndCityService service;
    private final MailSenderService mailSender;
    private final ConfirmTokenService confirmTokenService;

    private final PasswordEncoder passwordEncoder = NoOpPasswordEncoder.getInstance(); // new BCryptPasswordEncoder();

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
        if (!newUser.getUserPassword().equals(newUser.getConfirmPassword()))
            throw new ErrorCheckRequestException("Пароли не совпадают!");

        User user = UserUtil.createNewFromTo(newUser);
        Country country = service.getCountry(newUser.getCountry());
        City city = service.getCity(newUser.getCity());

        if (Objects.isNull(country) || Objects.isNull(city))
            throw new ErrorCheckRequestException("Ошибка создания пользователя!");
        User currentUser = repository.findByEmail(user.getEmail()).orElse(null);

        if (Objects.isNull(currentUser)) {
            user.setPassword(UserUtil.prepareToPassword(newUser.getUserPassword(), passwordEncoder));
            user.setCountry(country);
            user.setCity(city);
            repository.save(user);
            mailSender.sendEmail(EmailMessageUtil.getRegisterMail(user, confirmTokenService.newToken(user, 1)));
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
    public void checkToken(String token) throws ErrorNotFoundPageException {
        ConfirmToken confirmToken = confirmTokenService.getToken(token, 2);
        if (Objects.isNull(confirmToken))
            throw new ErrorNotFoundPageException("Error reset password: Not token page reset-password");
    }

    @Override
    @Transactional
    public String resetPassword(String token, String password, String confirmPassword) {
        ConfirmToken confirmToken = confirmTokenService.getToken(token, 2);
        if (Objects.isNull(confirmToken))
            throw new ErrorNotFoundPageException("Error reset password: Not token page reset-password");

        if (Objects.isNull(password) || Objects.isNull(confirmPassword) || password.trim().isEmpty() || confirmPassword.trim().isEmpty())
            return "Пароли не должны быть пустыми";

        if (password.equals(confirmPassword)) {
            if (password.length() >= 7 && password.length() <= 30) {
                confirmToken.getUser().setPassword(UserUtil.prepareToPassword(password, passwordEncoder));
                repository.save(confirmToken.getUser());
                confirmTokenService.removeToken(confirmToken);
            } else {
                return "Пароль должен находиться в диапазоне от 7 до 30";
            }
        } else {
            return "Новые пароли не совпадают!";
        }
        return "Success";
    }

    @Override
    @Transactional
    public void activeUser(String token) throws ErrorNotFoundPageException {
        ConfirmToken confirmToken = confirmTokenService.getToken(token, 1);
        if (Objects.isNull(confirmToken))
            throw new ErrorNotFoundPageException("Error set user active: Page Not Found confirm-account");
        confirmToken.getUser().setEnabled(true);
        repository.save(confirmToken.getUser());
        confirmTokenService.removeToken(confirmToken);
    }

    @Override
    @Transactional
    public void resetPasswordEmail(String email) {
        User user = repository.findByEmailAndEnabled(email.toLowerCase().trim(), true).orElse(null);
        if (Objects.nonNull(user)) {
            mailSender.sendEmail(EmailMessageUtil.getResetPasswordMail(user, confirmTokenService.newToken(user, 2)));
        }
    }

    @Override
    public void sendMessageFromContact(ContactDto contact) {
        mailSender.sendEmail(EmailMessageUtil.getMailFromContact(contact));
    }

    @Override
    public User getUser(long id) {
        User user = repository.findById(id).orElse(null);
        if (Objects.isNull(user))
            throw new ErrorNotFoundPageException("Error set user active: Page Not Found - chat user id");
        return user;
    }

    @Override
    public AuthorizedUser loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.findByEmail(email.toLowerCase().trim()).orElse(null);
        //TODO: check if not found

        return new AuthorizedUser(user);
    }

    @Override
    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }
}