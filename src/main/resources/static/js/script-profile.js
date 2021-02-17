$(function() {

    function successNoty(msg) {
        new Noty({
            theme: 'relax',
            text: "<span class='fa fa-lg fa-check'></span> &nbsp;"+msg,
            type: 'success',
            layout: "topRight",
            timeout: 3000
        }).show();
    }

    function failNoty(msg) {
        new Noty({
            theme: 'relax',
            text: "<span class='fa fa-lg fa-exclamation-circle'></span> &nbsp;"+msg,
            type: 'error',
            layout: "topRight",
            timeout: 3000
        }).show();
    }

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

});