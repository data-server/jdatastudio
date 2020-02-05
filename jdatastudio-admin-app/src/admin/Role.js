import React from "react";
import {
    ArrayField,
    Create,
    Datagrid,
    Edit,
    List,
    required,
    Show,
    ShowButton,
    SimpleForm,
    Tab,
    TabbedShowLayout,
    TextField,
    TextInput,
    EditButton
} from "react-admin";
import {WithoutExportActions} from "../util/util";
import CheckButton from "./CheckButton";

export const RoleList = (props) => (
    <List {...props} actions={<WithoutExportActions/>} bulkActionButtons={false}>
        <Datagrid>
            <TextField source="name"/>
            <ShowButton label="授权"/>
        </Datagrid>
    </List>
);

export const RoleEdit = (props) => (
    <Edit {...props}>
        <SimpleForm>
            <TextInput source="name" validate={[required()]}/>
        </SimpleForm>
    </Edit>

)

export const RoleShow = (props) => (
    <Show {...props}>
        <TabbedShowLayout>
            <Tab label="角色授权">
                <TextField source="name" validate={[required()]}/>
                <ArrayField source="permissions">
                    <Datagrid>
                        <TextField source="name" label="资源名称"/>
                        <TextField source="label" label="标签"/>
                        {/*<TextField source="url"/>*/}
                        {/*<SelectField source="method" label="方法" choices={methodType()}/>*/}
                        <CheckButton source="r"  label="读取" roleId={props.id}/>
                        <CheckButton source="u"  label="修改" roleId={props.id}/>
                        <CheckButton source="c"  label="新建" roleId={props.id}/>
                        <CheckButton source="d"  label="删除" roleId={props.id}/>
                        <EditButton label="编辑权限" basePath={'/_permission'}/>
                    </Datagrid>
                </ArrayField>
            </Tab>
            <Tab label="该角色下的用户">
                <ArrayField source="users">
                    <Datagrid>
                        <TextField source="username" label="用户名"/>
                        <TextField source="enabled" label="是否启用"/>
                        <TextField source="lastPasswordResetDate" label="密码最后重置时间"/>
                    </Datagrid>
                </ArrayField>
            </Tab>
        </TabbedShowLayout>
    </Show>
);

export const RoleCreate = (props) => (
    <Create {...props}>
        <SimpleForm redirect="show">
            <TextInput source="name" validate={[required()]}/>
        </SimpleForm>
    </Create>
);
