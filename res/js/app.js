var username, y_map, editing_mark, editing_group, moving_mark,
        marks = [],
        groups = [];

var add_mark_select = document.getElementById('add_mark_select'),
    marks_modal = document.getElementById('marks_modal'),
    marks_modal_bootstrap = new bootstrap.Modal(marks_modal),
    groups_modal = document.getElementById('groups_modal'),
    groups_modal_bootstrap = new bootstrap.Modal(document.getElementById('groups_modal')),
    new_mark_modal = new bootstrap.Modal(document.getElementById('new_mark_modal')),
    edit_mark_modal = new bootstrap.Modal(document.getElementById('edit_mark_modal')),
    choose_action_modal = new bootstrap.Modal(document.getElementById('choose_action_modal')),
    groups_tbody = document.getElementById('groups_tbody'),
    new_group_modal = new bootstrap.Modal(document.getElementById('new_group_modal')),
    edit_group_modal = new bootstrap.Modal(document.getElementById('edit_group_modal')),
    group_marks_tbody = document.getElementById('group_marks_tbody'),
    group_marks_modal = new bootstrap.Modal(document.getElementById('group_marks_modal'));

marks_modal.addEventListener('shown.bs.modal', () => {
        load_user_marks();
    }
);
groups_modal.addEventListener('shown.bs.modal', () => {
        load_user_groups();
    }
);

function update_mark() {
    'use strict';
    var mark = editing_mark,
        name = document.getElementById('edit_mark_name').value,
        colorHex = document.getElementById('edit_mark_color').value,
        data = {
            'name': name,
            'latitude': mark.data.latitude,
            'longitude': mark.data.longitude,
            'colorHex': colorHex.substring(1)
        },
        validation = validate_mark(data);
        mark.data.name = name;
        mark.data.colorHex = colorHex.substring(1);
    if (validation.error) {
        alert(validation.message);
    } else {
        mark.tr.firstChild.innerText = name;
        y_map.geoObjects.remove(mark.ymap);
        mark.ymap = new ymaps.Placemark(
            [mark.data.longitude, mark.data.latitude],
            {
                iconCaption: name
            }, {
                preset: 'islands#icon',
                iconColor: colorHex
            }
        );
        mark.ymap.events.add('click', function () {
            editing_mark = mark;
            choose_action_modal.show();
        });
        y_map.geoObjects.add(mark.ymap);
        fetch('/api/mark/' + mark.data.id, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        });
        edit_mark_modal.hide();
    }
}

function move_mark_request() {
    'use strict';
    moving_mark = true;
    choose_action_modal.hide();
    alert('Дважды щёлкните на карте куда хотели бы перенести метку');
}

function move_mark(lon, lat) {
    'use strict';
    'use strict';
    var mark = editing_mark,
        name = editing_mark.data.name,
        colorHex = editing_mark.data.colorHex,
        data = {
            'id': mark.data.id,
            'name': name,
            'latitude': lat,
            'longitude': lon,
            'colorHex': colorHex
        };
    y_map.geoObjects.remove(mark.ymap);
    mark.ymap = new ymaps.Placemark(
        [data.longitude, data.latitude],
        {
            iconCaption: name
        }, {
            preset: 'islands#icon',
            iconColor: '#' + colorHex
        }
    );
    mark.ymap.events.add('click', function () {
        editing_mark = mark;
        choose_action_modal.show();
    });
    y_map.geoObjects.add(mark.ymap);
    fetch('/api/mark/' + mark.data.id, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    });
    mark.data = data;
}

function edit_mark() {
    'use strict';
    choose_action_modal.hide();
    document.getElementById('edit_mark_lon').value = editing_mark.data.longitude;
    document.getElementById('edit_mark_lat').value = editing_mark.data.latitude;
    document.getElementById('edit_mark_name').value = editing_mark.data.name;
    document.getElementById('edit_mark_color').value = "#" + editing_mark.data.colorHex;
    edit_mark_modal.show();
}

function edit_mark_request(event) {
    'use strict';
    var i;
    for (i = 0; i < marks.length; i += 1) {
        if (marks[i].data.id == event.target.getAttribute('data-id')) {
            editing_mark = marks[i];
            break;
        }
    }
    edit_mark();
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

function move_to_mark_request(event) {
    'use strict';
    var i;
    for (i = 0; i < marks.length; i += 1) {
        if (marks[i].data.id == event.target.getAttribute('data-id')) {
            y_map.setCenter([marks[i].data.longitude, marks[i].data.latitude]);
            marks_modal_bootstrap.hide();
            break;
        }
    }
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
            to_mark_but = document.createElement('button'),
            to_mark_i = document.createElement('i'),
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
        to_mark_i.setAttribute('class', 'bi bi-pin-map');
        to_mark_i.setAttribute('data-id', mark_data.id);
        to_mark_but.setAttribute('title', 'К метке');
        to_mark_but.appendChild(to_mark_i);
        to_mark_but.setAttribute('class', 'btn btn-primary');
        to_mark_but.setAttribute('type', 'button');
        to_mark_but.setAttribute('data-id', mark_data.id);
        to_mark_but.onclick = move_to_mark_request;
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
        edit_div.appendChild(to_mark_but);
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
        icon = '',
        data = {
            'name': name,
            'description': description,
            'icon': icon
        },
        validation = validate_group(data);
    if (validation.error) {
        alert(validation.message);
    } else {
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
    editing_group = group;
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

function show_all_marks_request() {
    'use strict';
    debug('show_all_marks_request');
    var i;
    for (i = 0; i < marks.length; i += 1) {
        y_map.geoObjects.add(marks[i].ymap);
    }
    document.getElementById('show_all_marks_button').setAttribute('class', 'nav-item fade');
}

function show_group_request(event) {
    'use strict';
    debug('show_group_request');
    groups_modal_bootstrap.hide();
    document.getElementById('show_all_marks_button').setAttribute('class', 'nav-item');
    load_get(
        '/api/mark/in/' + event.target.getAttribute('data-id'),
        function (data) {
            var i, j, flag, arr = JSON.parse(data);
            for (i = 0; i < marks.length; i += 1) {
                flag = false;
                for (j = 0; j < arr.length; j += 1) {
                    if (marks[i].data.id == arr[j].id) {
                        flag = true;
                        break;
                    }
                }
                if (flag) {
                    y_map.geoObjects.add(marks[i].ymap);
                } else {
                    y_map.geoObjects.remove(marks[i].ymap);
                }
            }
        }
    );
}

function show_group_marks_editor_request(event) {
    'use strict';
    debug('show_group_marks_editor_request');
    var i, temp_group, id = event.target.getAttribute('data-id');
    for (i = 0; i < groups.length; i += 1) {
        temp_group = groups[i];
        if (temp_group.data.id == id) {
            editing_group = temp_group;
            edit_group_marks();
            break;
        }
    }
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
            show_marks_but = document.createElement('button'),
            show_marks_i = document.createElement('i'),
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
        show_marks_i.setAttribute('class', 'bi bi-pin-map-fill');
        show_marks_i.setAttribute('data-id', group_data.id);
        show_marks_but.setAttribute('title', 'Редактировать метки');
        show_marks_but.appendChild(show_marks_i);
        show_marks_but.setAttribute('class', 'btn btn-warning');
        show_marks_but.setAttribute('type', 'button');
        show_marks_but.setAttribute('data-id', group_data.id);
        show_marks_but.onclick = show_group_marks_editor_request;
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
        edit_div.appendChild(show_marks_but);
        edit_div.appendChild(edit_but);
        edit_div.appendChild(delete_but);
        edit_td.appendChild(edit_div);
        tr.appendChild(edit_td);
        group_obj.tr = tr;
        groups_tbody.appendChild(tr);
        groups[groups.length] = group_obj;
    }
}

function delete_mark_from_group_request(event) {
    'use strict';
    debug('delete_mark_from_group_request');
    load_post_json(
        '/api/rel/removeGroupFromMark',
        {
            'markId': event.target.getAttribute('data-id'),
            'groupId': editing_group.data.id
        },
        function (resp) {
            edit_group_marks();
        }
    );
}

function add_group_mark(mark_data) {
    'use strict';
    debug('add_group_mark');
    var tr = document.createElement('tr'),
        name_td = document.createElement('td'),
        edit_div = document.createElement('div'),
        edit_td = document.createElement('td'),
        delete_but = document.createElement('button'),
        delete_i = document.createElement('i');
    name_td.appendChild(document.createTextNode(mark_data.name));
    name_td.setAttribute('class', 'align-middle');
    edit_td.setAttribute('class', 'd-flex flex-row-reverse');
    tr.appendChild(name_td);
    delete_i.setAttribute('class', 'bi bi-eraser-fill');
    delete_i.setAttribute('data-id', mark_data.id);
    delete_but.appendChild(delete_i);
    delete_but.setAttribute('title', 'Удалить');
    delete_but.setAttribute('class', 'btn btn-danger');
    delete_but.setAttribute('type', 'button');
    delete_but.setAttribute('data-id', mark_data.id);
    delete_but.onclick = delete_mark_from_group_request;
    edit_div.appendChild(delete_but);
    edit_td.appendChild(edit_div);
    tr.appendChild(edit_td);
    group_marks_tbody.appendChild(tr);
}

function edit_group_marks() {
    'use strict';
    debug('edit_group_marks');
    group_marks_tbody.innerHTML = '';
    group_marks_modal.show();
    load_get(
        '/api/mark/in/' + editing_group.data.id,
        function (data) {
            var i, j, flag, option, arr = JSON.parse(data);
            if (arr.length === 0) {
                group_marks_tbody.innerHTML = '<tr><td>Пока тут пусто.</td></tr>';
            }
            add_mark_select.innerHTML = '';
            for (i = 0; i < marks.length; i += 1) {
                flag = true;
                for (j = 0; j < arr.length; j += 1) {
                    if (marks[i].data.id == arr[j].id) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    option = document.createElement('option');
                    option.value = marks[i].data.id;
                    option.appendChild(document.createTextNode(marks[i].data.name));
                    add_mark_select.appendChild(option);
                }
            }
            arr.forEach(
                function (mark) {
                    add_group_mark(mark);
                }
            );
        }
    );
}

function add_mark_to_group() {
    'use strict';
    debug('add_mark_to_group');
    load_post_json(
        '/api/rel/addGroupToMark',
        {
            'markId': add_mark_select.value,
            'groupId': editing_group.data.id
        },
        function (resp) {
            edit_group_marks();
        }
    );
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
    document.getElementById('group_description').value = '';
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
        zoom: 10
    });
    y_map.behaviors.disable('dblClickZoom');
    y_map.events.add('dblclick', function (e) {
        var coords = e.get('coords'),
            lon = coords[0].toPrecision(6),
            lat = coords[1].toPrecision(6);
        if (moving_mark) {
            move_mark(lon, lat);
            moving_mark = false;
        } else {
            open_new_mark_form_modal(lon, lat);
        }
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
            'colorHex': color.substring(1),
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

$(document).on('show.bs.modal', '.modal', function() {
    const zIndex = 1040 + 10 * $('.modal:visible').length;
    $(this).css('z-index', zIndex);
    setTimeout(() => $('.modal-backdrop').not('.modal-stack').css('z-index', zIndex - 1).addClass('modal-stack'));
  });