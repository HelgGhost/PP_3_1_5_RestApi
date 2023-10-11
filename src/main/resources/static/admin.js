$(async function () {
    await fillRoles('#newUserRoles');
    await fillUsers()
})
$('#nu_add').on('click', addUserSubmit)

async function getRoles() {
    let response = await fetch("/api/roles");
    return response.json();
}

async function getUsers() {
    let response = await fetch("/api");
    return response.json();

}

async function fillRoles(id, hasRoles = []) {
    let roles = await getRoles();
    $(id).empty();
    roles.forEach(role => {
        $(id).append('<option' +
            (hasRoles.some(r => r.name === role.name) ? ' selected="selected"' : '') +
            '>' +
            role.name +
            '</option>');
    })
}

async function fillEditDelete(id, edit = true) {
    let response = await fetch("/api/" + id);
    let user = await response.json();
    $("#eu_id").val(user.id);
    $("#eu_name").val(user.name).prop('readonly', !edit);
    $("#eu_lastname").val(user.lastname).prop('readonly', !edit);
    $("#eu_username").val(user.username).prop('readonly', !edit);
    $("#eu_password").val('').prop('readonly', !edit);
    $("#eu_roles").prop('disabled', !edit);
    $('#eu_error').text('');

    fillRoles('#eu_roles', user.roles);
    if (edit) {
        $("#eu_action").off("click").click(updateUserSubmit).text('Edit').removeClass('btn-danger')
            .addClass('btn-primary');
    } else {
        $("#eu_action").off("click").click(deleteUserSubmit).text('Delete').removeClass('btn-primary')
            .addClass('btn-danger');
    }
}

async function fillUsers() {
    let users = await getUsers();
    $("#users").empty();
    users.forEach(user => {
        $("#users").append('<tr>' +
            '<th scope="row">' + user.id + '</th>' +
            '<td>' + user.name + '</td>' +
            '<td>' + user.lastname + '</td>' +
            '<td>' + user.username + '</td>' +
            '<td>' + rolesToString(user.roles) + '</td>' +
            '<td><button type="button" class="btn btn-primary" data-bs-toggle="modal"' +
            'data-bs-target="#editUser" onclick="fillEditDelete(' + user.id + ')">Edit</button></td>' +
            '<td><button type="button" class="btn btn-danger" data-bs-toggle="modal"' +
            'data-bs-target="#editUser" onclick="fillEditDelete(' + user.id + ', false)">Delete</button></td>' +
            '</tr>');
    })
}

async function updateUserSubmit() {
    let editUserForm = $('#editUser');
    let user = {
        id: editUserForm.find('#eu_id').val().trim(),
        name: editUserForm.find('#eu_name').val().trim(),
        lastname: editUserForm.find('#eu_lastname').val().trim(),
        username: editUserForm.find('#eu_username').val().trim(),
        password: editUserForm.find('#eu_password').val().trim(),
        roles: editUserForm.find('#eu_roles').val()
    };
    fetch('api/' + user.id, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(user)
    }).then(async response => {
        if (response.ok) {
            if (curUser.id == user.id && curUser.username != user.username) {//Если изменили имя текущего пользователя то логаут
                $('#logout').submit();
            }
            await fillUsers();
            await fillHeaderAndUIPage()
            $('#editUser').modal("hide");
        } else {
            let result = await response.json();
            let errText = result.map(r => r.defaultMessage).join('<br>');
            $('#eu_error').text(errText);
        }
    });
}

function addUserSubmit() {
    let newUserForm = $('#newUser');
    let user = {
        name: newUserForm.find('#name').val().trim(),
        lastname: newUserForm.find('#lastname').val().trim(),
        username: newUserForm.find('#username').val().trim(),
        password: newUserForm.find('#password').val().trim(),
        roles: newUserForm.find('#newUserRoles').val()
    };
    fetch('api', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(user)
    }).then(async response => {
        if (response.ok) {
            await fillUsers();
            $('#nav-home-tab').tab('show');
            $('#nu_error').text('');
            $('#name').val('');
            $('#lastname').val('');
            $('#username').val('');
            $('#password').val('');
            $('#newUserRoles').val([]);

        } else {
            let result = await response.json();
            let errText = result.map(r => r.defaultMessage).join('<br>');
            $('#nu_error').html(errText);
        }
    });
}

async function deleteUserSubmit() {
    fetch('api/' + $('#eu_id').val(), {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        }
    }).then(async response => {
        if (response.ok) {
            await fillUsers();
            $('#editUser').modal("hide");
            if ($('#eu_id').val() == curUser.id) {//Если удалили текущего пользователя то логаут
                $('#logout').submit();
            }
        } else {
            console.log(response);
        }
    });

}