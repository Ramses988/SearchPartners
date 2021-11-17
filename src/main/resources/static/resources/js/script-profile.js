$(function() {

    $('.unique-login').focusout(function () {
        let errorInput = $(".unique-login");
        let checkName = $("#check-name").val();
        let name = errorInput.val();
        if (name.trim() !== '' && name !== checkName ) {
            $.post("/rest/account/checkLogin", {name : name}, function (data) {
                if (data) {
                    errorInput.addClass("has-error");
                    errorInput.parent().find('.validation-name').empty();
                    errorInput.parent().find('.validation-name').append("Указанный логин уже существует!");
                }
            });
        }
    });

    $('#realName').on('input', function(e) {
        const nameInput = $('#realName');
        const data = nameInput.val();
        const status = nameInput.hasClass("has-error");
        if (data.length <= 30) {
            if (status) {
                nameInput.removeClass("has-error");
                $('#validation-realName').empty();
            }
        }
        if (data.length > 30) {
            if (!status) {
                nameInput.addClass("has-error");
                $('#validation-realName').append("Максимум 30 символов");
            }
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

    $('#btn-updateProfile').click(function () {
        $.ajax({
            type: 'POST',
            url: '/rest/account/change-user-profile',
            data: $('#userProfile').serialize(),
            success: function () {
                successNoty('Профиль обновлен');
            },
            error: function () {
                failNoty('Ошибка обновления профиля');
            }
        })
    });

    $('#btn-resetPassword').click(function () {
        if (validForm('#resetPasswordForm')) {
            $.ajax({
                type: 'POST',
                url: '/rest/account/change-password',
                data: $('#resetPasswordForm').serialize(),
                success: function () {
                    successNoty('Пароль успешно изменен!');
                    $('#currentPassword').val('');
                    $('#newPassword').val('');
                    $('#confirmPassword').val('');
                },
                error: function (data) {
                    failNoty(data.responseText);
                }
            })
        } else {
            failNoty('Проверьте правильность заполнения полей!');
        }
    });

    $('#currentPassword').on('input', function() {
        validPassword('#currentPassword');
    });

    $('#newPassword').on('input', function() {
        validPassword('#newPassword');
    });

    $('#confirmPassword').on('input', function() {
        validPassword('#confirmPassword');
    });

});