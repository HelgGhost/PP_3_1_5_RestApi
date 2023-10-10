fillRoles('#newUserRoles')
fillUsers()
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

async function fillEditDelete(id) {
    let response = await fetch("/api/" + id);
    let user = await response.json();
    $("#eu_id").val(user.id);
    $("#eu_name").val(user.name);
    $("#eu_lastname").val(user.lastname);
    $("#eu_username").val(user.username);
    fillRoles('#eu_roles', user.roles);
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
                     'data-bs-target="#delete'+ user.id + '">Delete</button></td>' +
            '</tr>');
    })
}

async function updateUserSubmit() {

}
async function addUserSubmit() {
    let newUserForm = $('#newUser');
    let user = {
        name: newUserForm.find('#name').val().trim(),
        lastname: newUserForm.find('#lastname').val().trim(),
        username: newUserForm.find('#username').val().trim(),
        password: newUserForm.find('#password').val().trim(),
        roles: newUserForm.find('#newUserRoles').val()
    };

    let response = await fetch('api', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(user)
    });

    if (response.ok) {

    }
}