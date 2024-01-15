function debug(text) {
    'use strict';
    console.log(text);
}

function show_error(text) {
    alert(text);
}

function move_to_page(url) {
    'use strict';
    window.location.replace(url);
}

function load(url, method, data, callback) {
    'use strict';
    let xhr = new XMLHttpRequest();
    xhr.open(method, url);
    xhr.send();
    xhr.onload = function() {
        if (xhr.status != 200) {
            debug(`Ошибка ${xhr.status}: ${xhr.statusText}`);
        } else {
            callback(xhr.response);
        }
    };
}

function load_get(url, callback) {
    'use strict';
    load(url, 'GET', null, callback);
}

function load_post_json(url, json_data, callback) {
    'use strict';
    $.ajax(
        {
            type: 'post',
            url: url,
            data: JSON.stringify(json_data),
            contentType: "application/json; charset=utf-8",
            traditional: true,
            success: callback
        }
    );
}