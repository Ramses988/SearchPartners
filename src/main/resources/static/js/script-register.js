$(document).ajaxError(function (event, jqXHR, options, jsExc) {
    warningNoty(jqXHR);
});

function warningNoty(jqXHR) {
    new Noty({
        theme: 'relax',
        text: "<span class='fa fa-lg fa-warning'></span> &nbsp;" + jqXHR.responseJSON,
        type: "warning",
        layout: "topRight"
    }).show();
}

$(function() {

    $('#btn-createUser').click(function () {
        $.ajax({
            type: 'POST',
            url: '/rest/account/create-user',
            data: $('#form-createUser').serialize()
        })
    });

});