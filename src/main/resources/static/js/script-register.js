$(function() {

    $('#btn-createUser').click(function () {
        $.ajax({
            type: 'POST',
            url: '/rest/account/create-user',
            data: $('#form-createUser').serialize()
        })
    });

});