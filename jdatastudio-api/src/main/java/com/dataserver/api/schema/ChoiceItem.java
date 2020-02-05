package com.dataserver.api.schema;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChoiceItem {
  private String id;
  private String name;

  public ChoiceItem(String id, String name) {
    this.id = id;
    this.name = name;
  }
}
