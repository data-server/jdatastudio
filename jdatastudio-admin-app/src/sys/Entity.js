import React from "react";
import {
    AutocompleteInput,
    BooleanField,
    Datagrid,
    Edit,
    EditButton,
    Filter,
    FormTab,
    List,
    ReferenceInput,
    ReferenceManyField,
    TabbedForm,
    TextField,
    TextInput
} from "react-admin";
import {WithoutExportActions} from "../util/util";

const EntityFilter = (props) => (
    <Filter {...props}>
        <ReferenceInput source="jdatasource" reference="_datasource" alwaysOn>
            <AutocompleteInput optionText="name"/>
        </ReferenceInput>
    </Filter>
)

export const EntityList = (props) => (
    <List {...props} actions={<WithoutExportActions/>} bulkActionButtons={false} filters={<EntityFilter />}>
        <Datagrid>
            <TextField source="name"/>
            <TextField source="label"/>
            <EditButton/>
        </Datagrid>
    </List>
);

export const EntityEdit = (props) => (
    <Edit {...props}>
        <TabbedForm>
            <FormTab label="对象信息">
                <TextInput disabled source="name"/>
                <TextInput source="label" resettable/>
            </FormTab>
            <FormTab label="字段" path="fields">
                <ReferenceManyField
                    addLabel={false}
                    reference="_field"
                    target="eid"
                >
                    <Datagrid>
                        <TextField label="字段名称" source="name"/>
                        <TextField label="字段标签" source="label"/>
                        <BooleanField label="是否主键" source="partOfPrimaryKey"/>
                        <EditButton />
                    </Datagrid>
                </ReferenceManyField>
            </FormTab>
        </TabbedForm>
    </Edit>
);


