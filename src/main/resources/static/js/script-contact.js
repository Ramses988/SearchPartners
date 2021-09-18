$(function () {

    $('#btnContactSend').click(function () {
        if (validForm('#contactForm')) {
            $.ajax({
                type: 'POST',
                url: '/rest/contact/send',
                data: $('#contactForm').serialize(),
                success: function () {
                    window.location.replace("/feedback");
                },
                error: function(data) {
                    failNoty(data.responseText);
                }
            })
        } else {
            failNoty('Проверьте правильность заполнения полей!');
        }
    });

    $('#contact-name').on('input', function() {
        const nameInput = $('#contact-name');
        const data = nameInput.val();
        const status = nameInput.hasClass("has-error");
        if (data.length <= 30) {
            if (status) {
                nameInput.removeClass("has-error");
                nameInput.parent().find('.validation-name').empty();
            }
        }
        if (data.length > 30) {
            nameInput.addClass("has-error");
            nameInput.parent().find('.validation-name').empty();
            nameInput.parent().find('.validation-name').append("Максимум 30 символов");
        }
    });

    $('#contact-message').on('input', function() {
        const nameInput = $('#contact-message');
        const data = nameInput.val();
        const status = nameInput.hasClass("has-error");
        if (data.length > 0) {
            if (status) {
                nameInput.removeClass("has-error");
                nameInput.parent().find('.validation-name').empty();
            }
        }
    });

    $('#contact-email').on('input', function() {
        const emailInput = $('#contact-email');
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

})