package org.mapmark.service;

import org.mapmark.model.MarkBasic;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class MarkService {

    private Map<String, MarkBasic> db = new HashMap<>() {{
        put("1", new MarkBasic("1", "test"));
    }};


    public Collection<MarkBasic> get() {
        return db.values();
    }

    public MarkBasic get(String id) {
        return db.get(id);
    }

    public MarkBasic remove(String id) {
        return db.remove(id);

    }

    public MarkBasic save(String name) {

        String uuid = UUID.randomUUID().toString();
        MarkBasic markBasic = new MarkBasic();
        markBasic.setId(uuid);
        markBasic.setName(name);

        db.put(uuid, markBasic);
        return markBasic;

    }
}
