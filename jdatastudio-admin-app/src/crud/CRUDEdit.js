import React from "react";
import {renderInput} from "./renderInput";
import {Edit, EditActions, SimpleForm} from "react-admin";
/**
 * edit page with the record value
 * @param props
 * @constructor
 */
export const CRUDEdit = props => {
    const resource = props.options;
    return (
        <Edit
            undoable={false}
            actions={<EditActions options={props}/>}
            {...props}
            title={resource.label}
        >
            <SimpleForm redirect={resource.redirect}>
                {resource.fields.filter(field => field.showInEdit).map(renderInput)}
            </SimpleForm>
        </Edit>
    );
};
