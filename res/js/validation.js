function validate_email(email) {
    var re = /\S+@\S+\.\S+/;
    return re.test(email);
}

function validate_username(username) {
    var flag = true;
    flag = flag && username != undefined;
    flag = flag && username.length > 2;
    return flag;
}

function validate_password(password, password_check) {
    var flag = true;
    flag = flag && password != undefined;
    flag = flag && password.length > 2;
    flag = flag && password === password_check;
    return flag;
}