package com.dataserver.dataapi.data;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JdsFile {
    String title;
    String id;

    public String toPersistentStr() {
        return id + ";" + title;
    }

    public JdsFile toPojo(String persistentStr) {
        String[] jdsFile = persistentStr.split(";");
        return JdsFile.builder().id(jdsFile[0]).title(jdsFile[1]).build();
    }
}
