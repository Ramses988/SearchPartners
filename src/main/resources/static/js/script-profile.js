$(function() {

    function getCities(id) {
        const city = $('#city');
        city.empty();
        city.append('<option value="0">Выберите из списка</option>');

        if (id > 0) {
            $.post('/rest/country/get-cities', {id: id}, function(data) {
                data.forEach((item) => {
                    city.append('<option value="' + item.id + '">' + item.name + '</option>');
                })
            });
        }
    }

    $('#country').on('change', function () {
        const id = $('#country').val();
        getCities(id);
    });

});