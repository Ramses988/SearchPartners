$(function() {

    function failNoty(msg) {
        new Noty({
            theme: 'relax',
            text: "<span class='fa fa-lg fa-exclamation-circle'></span> &nbsp;"+msg,
            type: 'error',
            layout: "topRight"
        }).show();
    }

    $('#btn-createUser').click(function () {
        $.ajax({
            type: 'POST',
            url: '/rest/account/create-user',
            data: $('#form-createUser').serialize(),
            error: function(data) {
                failNoty(data.responseText);
            }
        })
    });

});