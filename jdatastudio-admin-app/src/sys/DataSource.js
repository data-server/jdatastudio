import React from "react";
import {
    Create,
    Datagrid,
    Edit,
    EditButton,
    List,
    required,
    SelectField,
    SelectInput,
    SimpleForm,
    TextField,
    TextInput
} from "react-admin";
import {WithoutExportActions} from "../util/util";
import {dbType} from "../constants";
import SyncButton from "./SyncButton";

export const DataSourceList = (props) => (
    <List {...props} actions={<WithoutExportActions/>} bulkActionButtons={false}>
        <Datagrid>
            <TextField source="name"/>
            <TextField source="url"/>
            <SelectField source="dbType" choices={dbType()}/>
            <SyncButton/>
            <EditButton/>
        </Datagrid>
    </List>
);

export const DataSourceEdit = (props) => (
    <Edit {...props}>
        <SimpleForm>
            <SelectInput source="dbType" validate={[required()]} choices={dbType()}/>
            <TextInput source="name" validate={[required()]}/>
            <TextInput source="url" validate={[required()]}/>
            <TextInput source="username" validate={[required()]}/>
            <TextInput source="password" validate={[required()]}/>
        </SimpleForm>
    </Edit>

)

export const DataSourceCreate = (props) => (
    <Create {...props}>
        <SimpleForm redirect="list">
            <TextInput source="name" validate={[required()]}/>
            <SelectInput source="dbType" validate={[required()]} choices={dbType()}/>
            <TextInput source="url" validate={[required()]}/>
            <TextInput source="username" validate={[required()]}/>
            <TextInput source="password" validate={[required()]}/>
        </SimpleForm>
    </Create>
);
