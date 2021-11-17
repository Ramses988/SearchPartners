$(function() {

    $('.unique-login').focusout(function () {
        let errorInput = $(".unique-login");
        let name = errorInput.val();
        if (name.trim() !== '') {
            $.post("/rest/account/checkLogin", {name : name}, function (data) {
                if (data) {
                    errorInput.addClass("has-error");
                    errorInput.parent().find('.validation-name').empty();
                    errorInput.parent().find('.validation-name').append("Указанный логин уже существует!");
                }
            });
        }
    });

    $('#btn-createUser').click(function () {
        if (validForm('#form-createUser')) {
            $.ajax({
                type: 'POST',
                url: '/rest/account/create-user',
                data: $('#form-createUser').serialize(),
                success: function () {
                    window.location.replace("/register-success");
                },
                error: function(data) {
                    failNoty(data.responseText);
                }
            })
        } else {
            failNoty('Проверьте правильность заполнения полей!');
        }
    });

    $('#name').on('input', function() {
        const nameInput = $('#name');
        const data = nameInput.val();
        const status = nameInput.hasClass("has-error");
        if (data.length <= 15) {
            if (status) {
                nameInput.removeClass("has-error");
                nameInput.parent().find('.validation-name').empty();
            }
        }
        if (data.length > 15) {
            nameInput.addClass("has-error");
            nameInput.parent().find('.validation-name').empty();
            nameInput.parent().find('.validation-name').append("Максимум 15 символов");
        }
    });

    $('#email').on('input', function() {
        const emailInput = $('#email');
        const data = emailInput.val();
        const status = emailInput.hasClass("has-error");
        if (!email(data)) {
            if (status) {
                emailInput.removeClass("has-error");
                emailInput.parent().find('.validation-name').empty();
            }
        }
        if (email(data)) {
            emailInput.addClass("has-error");
            emailInput.parent().find('.validation-name').empty();
            emailInput.parent().find('.validation-name').append("Email должен иметь формат адреса электронной почты");
        }
    });

    $('#userPassword').on('input', function() {
        validPassword('#userPassword');
    });

    $('#confirmPassword').on('input', function() {
        validPassword('#confirmPassword');
    });

});