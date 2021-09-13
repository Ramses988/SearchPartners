$(function() {

    function failNoty(msg) {
        new Noty({
            theme: 'relax',
            text: "<span class='fa fa-lg fa-exclamation-circle'></span> &nbsp;"+msg,
            type: 'error',
            layout: "topRight",
            timeout: 3000
        }).show();
    }

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

    $('#password').on('input', function() {
        validPassword('#password');
    });

    $('#confirmPassword').on('input', function() {
        validPassword('#confirmPassword');
    });

});