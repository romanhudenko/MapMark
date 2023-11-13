package org.mapmark.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;


@Data
public class MarkBasic {


    private String Id;
    @NotEmpty
    private String name;

    /**
     * latitude
     * longitude
     * user id
     * color
     * group
     **/

    public MarkBasic() {
    }

    public MarkBasic(String Id, String name) {
        this.Id = Id;
        this.name = name;
    }
}
