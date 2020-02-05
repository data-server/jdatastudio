import React from "react";
import {Datagrid,SelectInput,SelectField,Edit,required, List, TextField,Create,SimpleForm,TextInput,EditButton} from "react-admin";
import {WithoutExportActions} from '../util/util';
import {methodType} from '../constants';
export const ResourceList = (props) => (
    <List {...props} actions={<WithoutExportActions/>}  bulkActions={false}>
        <Datagrid>
            <TextField source="name"/>
            <TextField source="url"/>
            <SelectField source="method" choices={methodType()} />
            <EditButton/>
        </Datagrid>
    </List>
);

export const ResourceCreate = (props) => (
    <Create {...props}>
        <SimpleForm>
            <TextInput source="name" validate={[required()]}/>
            <TextInput source="url" validate={[required()]}/>
            <SelectInput source="method" choices={methodType()} validate={[required()]} />
        </SimpleForm>
    </Create>
);

export const ResourceEdit = (props) => (
    <Edit {...props}>
        <SimpleForm>
            <TextInput source="name" validate={[required()]}/>
            <TextInput source="url" validate={[required()]}/>
            <SelectInput source="method" choices={methodType()} validate={[required()]}/>
        </SimpleForm>
    </Edit>
);