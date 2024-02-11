var username, y_map, editing_mark,
    marks = [],
    groups = [];

var marks_modal = document.getElementById('marks_modal'),
    groups_modal = document.getElementById('groups_modal'),
    new_mark_modal = new bootstrap.Modal(document.getElementById('new_mark_modal')),
    choose_action_modal = new bootstrap.Modal(document.getElementById('choose_action_modal')),
    groups_tbody = document.getElementById('groups_tbody'),
    groups_modal = document.getElementById('groups_modal'),
    new_group_modal = new bootstrap.Modal(document.getElementById('new_group_modal')),
    edit_group_modal = new bootstrap.Modal(document.getElementById('edit_group_modal'));

marks_modal.addEventListener('shown.bs.modal', () => {
        load_user_marks();
    }
);
groups_modal.addEventListener('shown.bs.modal', () => {
        load_user_groups();
    }
);

function edit_mark() {
    'use strict';
    choose_action_modal.hide();
}

function edit_mark_request(event) {
    'use strict';
}

function is_has_mark_id(mark_id) {
    debug('is_has_mark_id');
    var i, temp_mark;
    for (i = 0; i < marks.length; i += 1) {
        temp_mark = marks[i];
        if (temp_mark.data.id === mark_id) {
            return true;
        }
    }
    return false;
}

function remove_mark(mark) {
    'use strict';
    debug('remove_mark');
    y_map.geoObjects.remove(mark.ymap);
    marks_tbody.removeChild(mark.tr);
    choose_action_modal.hide();
    fetch('/api/mark/' + mark.data.id, {
        method: 'DELETE'
    });
    var i, temp_mark;
    for (i = 0; i < marks.length; i += 1) {
        temp_mark = marks[i];
        console.log(temp_mark.data.id);
        console.log(mark.data.id);
        if (temp_mark.data.id === mark.data.id) {
            console.log(marks);
            marks.splice(i, 1);
            console.log(marks);
            if (marks.length === 0) {
                marks_tbody.innerHTML = '<tr><td>Тут пока пусто.</td></tr>';
            }
        }
    }
}

function delete_mark_request(event) {
    'use strict';
    debug('delete_mark_request');
    var temp_mark, i, id = event.target.getAttribute('data-id');
    for (i = 0; i < marks.length; i += 1) {
        temp_mark = marks[i];
        if (temp_mark.data.id === id) {
            remove_mark(temp_mark);
            break;
        }
    }
}

function delete_editing_mark() {
    'use strict';
    debug('delete_editing_mark');
    remove_mark(editing_mark);
}

function update_filter() {
    'use strict';
    var i, mark, filter_text = document.getElementById("mark_filter_input").value;
    for (i = 0; i < marks.length; i += 1) {
        mark = marks[i];
        if (mark.data.name.toLowerCase().includes(filter_text.toLowerCase())) {
            mark.tr.setAttribute('class', '');
        } else {
            mark.tr.setAttribute('class', 'visually-hidden');
        }
    }
}

function clear_filter_field() {
    'use strict';
    document.getElementById("mark_filter_input").value = '';
    update_filter();
}

function add_and_render_mark(mark_data) {
    'use strict';
    debug('add_and_render_mark');
    if (!is_has_mark_id(mark_data.id)) {
        if (marks.length === 0) {
            marks_tbody.innerHTML = '';
        }
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
            edit_div = document.createElement('div'),
            edit_but = document.createElement('button'),
            edit_i = document.createElement('i'),
            delete_but = document.createElement('button'),
            delete_i = document.createElement('i');
        name_td.appendChild(document.createTextNode(mark_data.name));
        name_td.setAttribute('class', 'align-middle');
        tr.appendChild(name_td);
        edit_td.setAttribute('class', 'd-flex flex-row-reverse');
        edit_div.setAttribute('class', 'btn-group');
        edit_div.setAttribute('role', 'group');
        edit_i.setAttribute('class', 'bi bi-wrench');
        edit_i.setAttribute('data-id', mark_data.id);
        edit_but.setAttribute('title', 'Редактировать');
        edit_but.appendChild(edit_i);
        edit_but.setAttribute('class', 'btn btn-warning');
        edit_but.setAttribute('type', 'button');
        edit_but.setAttribute('data-id', mark_data.id);
        edit_but.onclick = edit_mark_request;
        delete_i.setAttribute('class', 'bi bi-eraser-fill');
        delete_i.setAttribute('data-id', mark_data.id);
        delete_but.appendChild(delete_i);
        delete_but.setAttribute('title', 'Удалить');
        delete_but.setAttribute('class', 'btn btn-danger');
        delete_but.setAttribute('type', 'button');
        delete_but.setAttribute('data-id', mark_data.id);
        delete_but.onclick = delete_mark_request;
        edit_div.appendChild(edit_but);
        edit_div.appendChild(delete_but);
        edit_td.appendChild(edit_div);
        tr.appendChild(edit_td);
        mark_obj.tr = tr;
        marks_tbody.appendChild(tr);
        marks[marks.length] = mark_obj;
        ymap_placemark.events.add('click', function () {
            editing_mark = mark_obj;
            choose_action_modal.show();
        });
        y_map.geoObjects.add(ymap_placemark);
    }
}

function is_has_group_id(group_id) {
    debug('is_has_group_id');
    var i, temp_group;
    for (i = 0; i < groups.length; i += 1) {
        temp_group = groups[i];
        if (temp_group.data.id === group_id) {
            return true;
        }
    }
    return false;
}

function update_group() {
    'use strict';
    var i, temp_group, group,
        id = document.getElementById('edit_group_id').value,
        name = document.getElementById('edit_group_name').value,
        description = document.getElementById('edit_group_description').value,
        icon = '';
    fetch('/api/group/' + id, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(
            {
                'name': name,
                'description': description,
                'icon': icon
            }
        )
    });
    for (i = 0; i < groups.length; i += 1) {
        temp_group = groups[i];
        console.log(temp_group.data.id);
        console.log(id);
        if (temp_group.data.id == id) {
            group = temp_group;
            break;
        }
    }
    group.tr.firstChild.innerHTML = name;
    group.data.name = name;
    group.data.description = description;
    group.data.icon = icon;
    edit_group_modal.hide();
}

function edit_group_request(event) {
    'use strict';
    debug('edit_group_request');
    var group, temp_group, i, id = event.target.getAttribute('data-id');
    console.log(event.target.getAttribute('data-id'));
    for (i = 0; i < groups.length; i += 1) {
        temp_group = groups[i];
        console.log(temp_group.data.id);
        console.log(id);
        if (temp_group.data.id == id) {
            group = temp_group;
            break;
        }
    }
    document.getElementById('edit_group_id').value = group.data.id;
    document.getElementById('edit_group_name').value = group.data.name;
    document.getElementById('edit_group_description').value = group.data.description;
    edit_group_modal.show();
}

function delete_group_request(event) {
    'use strict';
    debug('delete_group_request');
    var group, temp_group, i, id = event.target.getAttribute('data-id');
    console.log(event.target.getAttribute('data-id'));
    for (i = 0; i < groups.length; i += 1) {
        temp_group = groups[i];
        console.log(temp_group.data.id);
        console.log(id);
        if (temp_group.data.id == id) {
            group = temp_group;
            break;
        }
    }
    groups_tbody.removeChild(group.tr);
    fetch('/api/group/' + group.data.id, {
        method: 'DELETE'
    });
    for (i = 0; i < groups.length; i += 1) {
        temp_group = groups[i];
        if (temp_group.data.id === group.data.id) {
            groups.splice(i, 1);
            if (groups.length === 0) {
                groups_tbody.innerHTML = '<tr><td>Тут пока пусто.</td></tr>';
            }
        }
    }
}

function show_group_request() {
    'use strict';
}

function add_and_render_group(group_data) {
    'use strict';
    debug('add_and_render_mark');
    if (!is_has_group_id(group_data.id)) {
        if (groups.length === 0) {
            groups_tbody.innerHTML = '';
        }
        var group_obj = {
                'data': group_data
            },
            tr = document.createElement('tr'),
            name_td = document.createElement('td'),
            edit_td = document.createElement('td'),
            edit_div = document.createElement('div'),
            show_but = document.createElement('button'),
            show_i = document.createElement('i'),
            edit_but = document.createElement('button'),
            edit_i = document.createElement('i'),
            delete_but = document.createElement('button'),
            delete_i = document.createElement('i');
        name_td.appendChild(document.createTextNode(group_data.name));
        name_td.setAttribute('class', 'align-middle');
        tr.appendChild(name_td);
        edit_td.setAttribute('class', 'd-flex flex-row-reverse');
        edit_div.setAttribute('class', 'btn-group');
        edit_div.setAttribute('role', 'group');
        show_i.setAttribute('class', 'bi bi-pin-map');
        show_i.setAttribute('data-id', group_data.id);
        show_but.setAttribute('title', 'Показать на карте');
        show_but.appendChild(show_i);
        show_but.setAttribute('class', 'btn btn-primary');
        show_but.setAttribute('type', 'button');
        show_but.setAttribute('data-id', group_data.id);
        show_but.onclick = show_group_request;
        edit_i.setAttribute('class', 'bi bi-wrench');
        edit_i.setAttribute('data-id', group_data.id);
        edit_but.setAttribute('title', 'Редактировать');
        edit_but.appendChild(edit_i);
        edit_but.setAttribute('class', 'btn btn-warning');
        edit_but.setAttribute('type', 'button');
        edit_but.setAttribute('data-id', group_data.id);
        edit_but.onclick = edit_group_request;
        delete_i.setAttribute('class', 'bi bi-eraser-fill');
        delete_i.setAttribute('data-id', group_data.id);
        delete_but.appendChild(delete_i);
        delete_but.setAttribute('title', 'Удалить');
        delete_but.setAttribute('class', 'btn btn-danger');
        delete_but.setAttribute('type', 'button');
        delete_but.setAttribute('data-id', group_data.id);
        delete_but.onclick = delete_group_request;
        edit_div.appendChild(show_but);
        edit_div.appendChild(edit_but);
        edit_div.appendChild(delete_but);
        edit_td.appendChild(edit_div);
        tr.appendChild(edit_td);
        group_obj.tr = tr;
        groups_tbody.appendChild(tr);
        groups[groups.length] = group_obj;
    }
}

function load_user_marks() {
    'use strict';
    debug('load_user_marks');
    load_get(
        '/api/mark',
        function(data) {
            var arr = JSON.parse(data);
            if (arr.length === 0) {
                marks_tbody.innerHTML = '<tr><td>Пока тут пусто.</td></tr>';
            }
            arr.forEach(
                function(mark) {
                    add_and_render_mark(mark);
                }
            );
        }
    );
}

function load_user_groups() {
    'use strict';
    debug('load_user_groups');
    load_get(
        '/api/group',
        function(data) {
            var arr = JSON.parse(data);
            if (arr.length === 0) {
                groups_tbody.innerHTML = '<tr><td>Пока тут пусто.</td></tr>';
            }
            arr.forEach(
                function(group) {
                    add_and_render_group(group);
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

function start_group_creation() {
    'use strict';
    document.getElementById('group_name').value = '';
    new_group_modal.show();
}

function create_new_group() {
    'use strict';
    var name = document.getElementById('group_name').value,
        description = document.getElementById('group_description').value,
        data = {
            'name': name,
            'description': description
        },
        validation = validate_group(data);
    if (validation.error) {
        alert(validation.message);
    } else {
        new_group_modal.hide();
        load_post_json(
            '/api/group',
            data,
            function (resp) {
                add_and_render_group(resp);
            }
        );
    }
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
    var name = document.getElementById('mark_name').value,
        color = document.getElementById('mark_color').value,
        lat = document.getElementById('new_mark_lat').value,
        lon = document.getElementById('new_mark_lon').value,
        data = {
            'name': name,
            'colorHex': color,
            'latitude': lat,
            'longitude': lon
        },
        validation = validate_mark(data);
    if (validation.error) {
        alert(validation.message);
    } else {
        new_mark_modal.hide();
        load_post_json(
            '/api/mark',
            data,
            function (resp) {
                add_and_render_mark(resp);
            }
        );
    }
}