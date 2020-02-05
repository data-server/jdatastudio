import React from "react";
import { Create, SimpleForm } from "react-admin";
import { renderInput } from "./renderInput";
/**
 * create page with simple form layout
 *
 * contains validators
 * @param props
 * @constructor
 */
export const CRUDCreate = props => {
  const resource = props.options;
  return (
    <Create {...props} title={resource.label}>
      <SimpleForm redirect={"list"}>
        {resource.fields.filter(field => field.showInCreate).map(renderInput)}
      </SimpleForm>
    </Create>
  );
};
