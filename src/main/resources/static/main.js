let curUser;
fillHeaderAndUIPage()

async function fillHeaderAndUIPage () {
    fetch("/api/user")
        .then(async response => {
            await response.json().then(user => {
                let roles = rolesToString(user.roles);
                $("#header_username").text(user.username);
                $("#header_roles").text('with roles: ' + roles);
                $("#ui_id").text(user.id);
                $("#ui_name").text(user.name);
                $("#ui_lastname").text(user.lastname);
                $("#ui_username").text(user.username);
                $("#ui_roles").text(roles);
                curUser = user;
            })
        })
}

function rolesToString(roles) {
    return roles.map(r => r.name).join(' ');
}