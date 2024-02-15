function validate_email(email) {
    'use strict';
    var re = /\S+@\S+\.\S+/;
    return re.test(email);
}

function validate_username(username) {
    'use strict';
    var flag = true;
    flag = flag && username !== undefined;
    flag = flag && username.length > 2;
    return flag;
}

function validate_password(password, password_check) {
    'use strict';
    var flag = true;
    flag = flag && password !== undefined;
    flag = flag && password.length > 2;
    flag = flag && password === password_check;
    return flag;
}

function validate_mark(mark_data) {
    'use strict';
    var format = /[@#$%^&*()_+\-=\[\]{};':"\\|<>\/]+/;
    debug(mark_data);
    if (mark_data.name === undefined || mark_data.name.length === 0) {
        return {
            'error': true,
            'message': 'Название метки не может быть пустым!'
        };
    } else if (format.test(mark_data.name)) {
        return {
            'error': true,
            'message': 'Название метки содержит недопустимые символы!'
        };
    } else {
        return {
            'error': false
        };
    }
}

function validate_group(group_data) {
    'use strict';
    var format = /[@#$%^&*()_+\-=\[\]{};':"\\|<>\/]+/;
    debug(group_data);
    if (group_data.name === undefined || group_data.name.length === 0) {
        return {
            'error': true,
            'message': 'Название группы не может быть пустым!'
        };
    } else if (format.test(group_data.name)) {
        return {
            'error': true,
            'message': 'Название группы содержит недопустимые символы!'
        };
    } else {
        return {
            'error': false
        };
    }
}