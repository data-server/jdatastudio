import React from "react";
import {
    ArrayInput,
    AutocompleteInput,
    BooleanInput,
    DisabledInput,
    Edit,
    FormTab,
    ReferenceInput,
    SelectInput,
    SimpleFormIterator,
    TabbedForm,
    TextInput
} from "react-admin";
import {operatorType} from "../constants";

const redirect = (basePath, id, data) => `/roles/` + id.substr(0, id.lastIndexOf("_")) + "/show";

export const PermissionEdit = (props) => {
    let id = props.id;
    let eid = id.substr(id.lastIndexOf("_") + 1);
    return <Edit {...props}>
        <TabbedForm redirect={redirect}>
            <FormTab label="角色授权">
                <TextInput disabled source="name"/>
                <TextInput source="label"/>
                <BooleanInput source="r"/>
                <BooleanInput source="c"/>
                <BooleanInput source="u"/>
                <BooleanInput source="d"/>
            </FormTab>
            <FormTab label="数据过滤">
                <ArrayInput source="filters" label="过滤器">
                    <SimpleFormIterator>
                        <ReferenceInput source="field" label="字段" reference="_field" filter={{eid: eid}}>
                            <AutocompleteInput optionValue="name" optionText="label"/>
                        </ReferenceInput>
                        <SelectInput source="operator" label="操作符" choices={operatorType()}/>
                        <TextInput source="value" label="过滤值"/>
                    </SimpleFormIterator>
                </ArrayInput>
            </FormTab>
        </TabbedForm>
    </Edit>
}
