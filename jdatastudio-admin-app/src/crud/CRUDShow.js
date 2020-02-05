import React, {Fragment} from "react";
import {Show, Tab, TabbedShowLayout, ReferenceManyField, Datagrid, EditButton, ShowButton} from "react-admin";
import {renderField} from "./renderField";

export const CRUDShow = props => {
    const resource = props.options;
    return (
        <Show {...props} title={resource.label}>
            <TabbedShowLayout>
                <Tab label="Detail">
                    {resource.fields.filter(field => field.showInShow).map(renderField)}
                    {resource.related ? resource.related.map(renderRelation) : null}
                </Tab>
            </TabbedShowLayout>
        </Show>
    );
};

const renderRelation = entity => (
    <ReferenceManyField
        key={"rl" + entity.id}
        reference={"api/" + entity.name}
        target={entity.relatedTarget}
        addLabel={false}
    >
        <Datagrid>
            {entity.fields.filter(field => field.showInList).map(renderField)}
            <ShowButton/>
            {entity.u ? <EditButton/> : <Fragment/>}
        </Datagrid>
    </ReferenceManyField>
);
