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

function logout() {
    load_get(
        '/logout',
        function() {
            move_to_page('index.html');
        }
    )
}

window.onload = function () {
    'use strict';
    check_is_logged_in(
        function(flag) {
            if (!flag) {
                move_to_page('/index.html');
            }
        }
    );
 }