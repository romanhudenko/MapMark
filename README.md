# MapMark
ASR usage (CURL example, you should use symbol "@" before file path):
>curl -F audio_file=@male.wav http://127.0.0.1:9000/asr
> 
> 
> 
> endpoints 
> /api/group
> Get list of user groups   GET       /api/group
> Get group by ID           GET       /api/group/{id}
> Get group by NAME         GET       /api/group/name/{name}
> Get group contain MARK    GET       /api/group/in/{markId}
> Create new group          POST      /api/group
> Edit group by ID          PUT       /api/group/{id}
> Delete group by ID        DELETE    /api/group/{id}
> 
> /api/mark
> Get list of user marks    GET       /api/mark
> Get mark by UUID          GET       /api/mark/{uuid}
> Get mark by NAME          GET       /api/mark/name/{name}
> Get marks in group        GET       /api/mark/in/{groupId}
> Create new mark           POST      /api/mark
> edit mark by UUID         PUT       /api/mark/{uuid}
> delete mark by UUID       DELETE    /api/mark/{uuid}
>
> /api/rel
> Add mark to group         POST      /api/rel/addGroupToMark
> Remove mark from group    POST      /api/rel/removeGroupFromMark
> 
> /api/user
> Registration new user     POST      /api/user/registration
> GET       /api/user/{id}
> GET       /api/user/status
> 
> 