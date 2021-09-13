package com.search_partners.web.account;

import com.search_partners.model.City;
import com.search_partners.model.User;
import com.search_partners.service.interfaces.CountryAndCityService;
import com.search_partners.service.interfaces.UserService;
import com.search_partners.util.SecurityUtil;
import com.search_partners.util.exception.ErrorNotFoundPageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AccountController {

    private final UserService userService;
    private final CountryAndCityService countryService;

    @Autowired
    public AccountController(UserService userService, CountryAndCityService countryService) {
        this.userService = userService;
        this.countryService = countryService;
    }

    @GetMapping("/login")
    public String getLogin(@RequestParam(defaultValue = "false", required = false) boolean error,
                           Model model) {
        model.addAttribute("error", error);
        return "login";
    }

    @GetMapping("/register")
    public String getRegister(Model model) {
        model.addAttribute("countries", countryService.getAllCountries());
        return "account/register";
    }

    @GetMapping("/profile")
    public String getProfile(Model model) {
        User user = userService.getUserWithCity(SecurityUtil.authUserId());
        model.addAttribute("user", user);
        model.addAttribute("countries", countryService.getAllCountries());
        List<City> cityList = new ArrayList<>();
        cityList.add(new City(0, "Выберите из списка", "any"));
        if (user.getCountry().getId() > 0)
            cityList.addAll(countryService.getCities(user.getCountry().getId()));
        model.addAttribute("cities", cityList);
        return "account/profile";
    }

    @GetMapping("/confirm-account/{token}")
    public String confirmUserAccount(@PathVariable(name="token") String token, Model model) throws ErrorNotFoundPageException {
        userService.activeUser(token);
        model.addAttribute("title", "Ваш email адрес успешно подтвержден");
        model.addAttribute("headline", "Спасибо за регистрацию!");
        model.addAttribute("text", "Спасибо за регистрацию на нашем сайте.<br/> Ваш email успешно подтвержден, теперь вы можете войти в Личный кабинет.<br/>\n" +
                "                Для входа в Личный кабинет перейдите по <a href=\"/login\">ссылке</a>");
        return "account/info";
    }

    @GetMapping("/register-success")
    public String registerSuccess(Model model) {
        model.addAttribute("title", "Вы зарегистрированы");
        model.addAttribute("headline", "Регистрация завершена");
        model.addAttribute("text", "<h5>На вашу почту отправлено подтверждение</h5><p>На Ваш почтовый ящик отправлено сообщение, содержащее ссылку для подтверждения правильности e-mail адреса. Пожалуйста, перейдите по ссылке для завершения\n" +
                " регистрации.</p>\n" +
                "<p>Если письмо не пришло в течение 15 минут, проверьте папку «Спам». Если письмо вдруг попало в эту папку, откройте письмо, нажмите кнопку «Не спам» и\n" +
                "перейдите по ссылке подтверждения. Если же письма нет и в папке «Спам», напишите нам через форму обратной связи в разделе <a href='#'>«Контакты»</a>.</p>");
        return "account/info";
    }

}