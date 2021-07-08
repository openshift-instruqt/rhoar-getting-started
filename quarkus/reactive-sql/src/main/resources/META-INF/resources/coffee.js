function refresh() {
    $.get('/coffee', function (coffee) {
        var list = '';
        (coffee || []).forEach(function (coffee) {
            list = list
                + '<tr>'
                + '<td>' + coffee.id + '</td>'
                + '<td>' + coffee.name + '</td>'
                + '<td><a href="#" onclick="deletecoffee(' + coffee.id + ')">Delete</a></td>'
                + '</tr>'
        });
        if (list.length > 0) {
            list = ''
                + '<table><thead><th>Id</th><th>Name</th><th></th></thead>'
                + list
                + '</table>';
        } else {
            list = "No coffee in database"
        }
        $('#all-coffee').html(list);
    });
}

function deletecoffee(id) {
    $.ajax('/coffee/' + id, {method: 'DELETE'}).then(refresh);
}


$(document).ready(function () {

    $('#create-coffee-button').click(function () {
        var coffeeName = $('#coffee-name').val();
        $.post({
            url: '/coffee',
            contentType: 'application/json',
            data: JSON.stringify({name: coffeeName})
        }).then(refresh);
    });

    refresh();
});