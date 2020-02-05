import React from "react";
import {
    ArrayField,
    BooleanField,
    BooleanInput,
    ChipField,
    Create,
    Datagrid,
    Edit,
    EditButton,
    Filter,
    FormDataConsumer,
    List,
    ReferenceArrayField,
    ReferenceArrayInput,
    required,
    SelectArrayInput,
    Show,
    ShowButton,
    SimpleForm,
    SimpleShowLayout,
    SingleFieldList,
    TextField,
    TextInput
} from "react-admin";
import PasswordInput from "./PasswordInput";
import { Field } from 'react-final-form';
import {WithoutExportActions} from "../util/util";

const UserFilter = (props) => (
    <Filter {...props}>
        <TextInput source="username" alwaysOn/>
    </Filter>
);

export const UserList = (props) => (
    <List {...props} actions={<WithoutExportActions/>} bulkActionButtons={false}  filters={<UserFilter/>}>
        <Datagrid>
            <TextField source="username"/>
            <ReferenceArrayField label="角色" reference="roles" source="roles">
                <SingleFieldList>
                    <ChipField source="name"/>
                </SingleFieldList>
            </ReferenceArrayField>
            <TextField source="lastPasswordResetDate"/>
            <ShowButton/>
            <EditButton/>
        </Datagrid>
    </List>
);

const PasswordRestInput = () => <Field name="password" component={PasswordInput} />
export const UserCreate = (props) => (
    <Create {...props}>
        <SimpleForm>
            <TextInput source="username" validate={[required()]}/>
            <PasswordRestInput validate={[required()]}/>
            <ReferenceArrayInput reference="roles" source="roles" perPage={1000} validate={[required()]}>
                <SelectArrayInput optionText="name"/>
            </ReferenceArrayInput>
            <BooleanInput source="enabled" defaultValue={true}/>
        </SimpleForm>
    </Create>
);

export const UserEdit = (props) => (
    <Edit {...props}>
        <SimpleForm>
            <TextInput disabled source="username"/>
            <BooleanInput source="enabled"/>
            <BooleanInput source="modifyPassword"/>
            <FormDataConsumer>
                {({formData, ...rest}) => formData.modifyPassword &&
                <PasswordRestInput {...rest}/>
                }
            </FormDataConsumer>
            <ReferenceArrayInput source="roles" reference="roles" perPage={100}>
                <SelectArrayInput optionText="name"/>
            </ReferenceArrayInput>
        </SimpleForm>
    </Edit>
);

export const UserShow = (props) => (
    <Show {...props}>
        <SimpleShowLayout>
            <TextField source="username"/>
            <BooleanField source="enabled"/>
            <ArrayField source="permissions" label="权限">
                <Datagrid>
                    <TextField source="label" label="资源名称"/>
                    <BooleanField source="r"  label="读取" />
                    <BooleanField source="e"  label="更改" />
                </Datagrid>
            </ArrayField>
        </SimpleShowLayout>
    </Show>
);
