function check_is_logged_in(callback) {
    'use strict';
    debug('check_is_logged_in')
    load_get(
        '/api/user/status',
        function(data) {
            let is_logged_in = data !== 'user not authorized';
            debug('check_is_logged_in: ' + String(is_logged_in))
            callback(is_logged_in);
        }
    );
}

window.onload = function () {
    'use strict';
    check_is_logged_in(
        function(flag) {
            if (flag) {
                move_to_page('/app.html');
            } else {
                if (window.location.href.endsWith("error")) {
                    $('#error_block').show();
                }
            }
        }
    );
}

function register() {
    var username = document.getElementById('reg_username').value,
        email = document.getElementById('reg_email').value,
        password = document.getElementById('reg_password').value,
        password_check = document.getElementById('reg_password_check').value,
        error = '';
    if (!validate_username(username)) {
        error += 'Неправильный логин!\n';
    }
    if (!validate_email(email)) {
        error += 'Неправильный адрес почты!\n';
    }
    if (!validate_password(password, password_check)) {
        error += 'Неправильный пароль или пароли не совпадают!\n';
    }
    if (error) {
        show_error(error);
    } else {
        load_post_json(
            '/api/user/registration',
            {
                'username': username,
                'email': email,
                'password': password
            },
            function() {
                move_to_page('/app.html');
            },
            function() {
                alert('Ошибка регистрации');
            }
        );
    }
}