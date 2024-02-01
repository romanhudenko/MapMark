var username, y_map, editing_mark,
    marks = [];

var marks_modal = document.getElementById('marks_modal'),
    new_mark_modal = new bootstrap.Modal(document.getElementById('new_mark_modal')),
    choose_action_modal = new bootstrap.Modal(document.getElementById('choose_action_modal')),
    marks_tbody = document.getElementById('marks_tbody');

marks_modal.addEventListener('shown.bs.modal', () => {
    load_user_marks();
  }
);

function edit_mark() {
    'use strict';
    choose_action_modal.hide();
}

function delete_mark() {
    'use strict';
    y_map.geoObjects.remove(editing_mark.ymap);
    marks_tbody.removeChild(editing_mark.tr);
    choose_action_modal.hide();
    fetch('/api/mark/' + editing_mark.data.id, {
        method: 'DELETE'
    });
}

function edit_request(event) {
    'use strict';
}

function delete_request(event) {
    'use strict';
    var temp_mark, i, id = event.target.getAttribute('data-id');
    for (i = 0; i < marks.length; i += 1) {
        temp_mark = marks[i];
        if (temp_mark.data.id === id) {
            y_map.geoObjects.remove(temp_mark.ymap);
            $('td button[data-id="' + id + '"]').parent().parent().remove();
            choose_action_modal.hide();
            fetch('/api/mark/' + temp_mark.data.id, {
                method: 'DELETE'
            });
            break;
        }
    }
}

function add_and_render_mark(mark_data) {
    'use strict';
    var ymap_placemark = new ymaps.Placemark(
            [mark_data.longitude, mark_data.latitude],
            {
                iconCaption: mark_data.name
            }, {
                preset: 'islands#icon',
                iconColor: '#' + mark_data.colorHex
            }
        ),
        mark_obj = {
            'ymap': ymap_placemark,
            'data': mark_data
        },
        tr = document.createElement('tr'),
        name_td = document.createElement('td'),
        edit_td = document.createElement('td'),
        del_td = document.createElement('td'),
        edit_but = document.createElement('button'),
        delete_but = document.createElement('button');
    name_td.appendChild(document.createTextNode(mark_data.name));
    tr.appendChild(name_td);
    edit_but.appendChild(document.createTextNode('Редактировать'));
    edit_but.setAttribute('class', 'btn btn-warning');
    edit_but.setAttribute('data-id', mark_data.id);
    edit_but.onclick = edit_request;
    edit_td.appendChild(edit_but);
    tr.appendChild(edit_td);
    delete_but.appendChild(document.createTextNode('Удалить'));
    delete_but.setAttribute('class', 'btn btn-danger');
    delete_but.setAttribute('data-id', mark_data.id);
    delete_but.onclick = delete_request;
    del_td.appendChild(delete_but);
    tr.appendChild(del_td);
    mark_obj.tr = tr;
    marks_tbody.appendChild(tr);
    marks[marks.length] = mark_obj;
    ymap_placemark.events.add('click', function () {
        editing_mark = mark_obj;
        choose_action_modal.show();
    });
    y_map.geoObjects.add(ymap_placemark);
}

function load_user_marks() {
    'use strict';
    load_get(
        '/api/mark',
        function(data) {
            var arr = JSON.parse(data);
            if (arr.length > 0) {
                marks_tbody.innerHTML = '';
            }
            arr.forEach(
                function(mark) {
                    add_and_render_mark(mark);
                }
            );
        }
    );
}

function start_mark_creation() {
    'use strict';
    bootstrap.Modal.getInstance(marks_modal).hide();
    alert("Дважды щелкните на карте для создания метки по указанным координатам.");
}

function check_is_logged_in(callback) {
    'use strict';
    debug('check_is_logged_in')
    load_get(
        '/api/user/status',
        function(data) {
            let is_logged_in = data !== 'user not authorized';
            debug('check_is_logged_in: ' + String(is_logged_in))
            username = data.substring(data.indexOf(' ') + 1);
            callback(is_logged_in);
        }
    );
}

function get_username(callback) {
    load_get(
        '/api/user/'
    )
}

function logout() {
    load_get(
        '/logout',
        function() {
            move_to_page('index.html');
        }
    );
}

function open_new_mark_form_modal(lon, lat) {
    'use strict';
    document.getElementById('mark_name').value = '';
    document.getElementById('new_mark_lon').value = lon;
    document.getElementById('new_mark_lat').value = lat;
    new_mark_modal.show();
}

function start() {
    'use strict';
    check_is_logged_in(
        function(flag) {
            if (!flag) {
                move_to_page('/index.html');
            } else {
                document.getElementById('username_place').innerText = "Вы вошли как " + username;
                load_user_marks();
            }
        }
    );
}

function init() {
    y_map = new ymaps.Map("map", {
        center: [55.76, 37.64],
        zoom: 7
    });
    y_map.behaviors.disable('dblClickZoom');
    y_map.events.add('dblclick', function (e) {
        var coords = e.get('coords'),
            lon = coords[0].toPrecision(6),
            lat = coords[1].toPrecision(6);
        open_new_mark_form_modal(lon, lat);
    });
    start();
}
ymaps.ready(init);

function create_new_mark() {
    'use strict';
    new_mark_modal.hide();
    var name = document.getElementById('mark_name').value,
        color = document.getElementById('mark_color').value,
        lat = document.getElementById('new_mark_lat').value,
        lon = document.getElementById('new_mark_lon').value,
        data = {
            'name': name,
            'colorHex': color,
            'latitude': lat,
            'longitude': lon
        };
    load_post_json(
        '/api/mark',
        data,
        function (resp) {
            add_and_render_mark(resp);
        }
    );
}