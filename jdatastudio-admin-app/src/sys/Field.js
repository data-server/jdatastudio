import React from "react";
import {
    AutocompleteInput,
    BooleanInput,
    Edit,
    FormDataConsumer,
    FormTab,
    NumberInput,
    ReferenceInput,
    required,
    SelectInput,
    TabbedForm,
    TextInput
} from "react-admin";
import {jdatastudioComponent, sensitiveType} from "../constants";

const redirect = (basePath, id, data) => `/_entity/` + id.substr(0, id.indexOf("_")) + `/fields`;


export const FieldEdit = (props) => (
    <Edit {...props}>
        <TabbedForm redirect={redirect}>
            <FormTab label="字段">
                <TextInput disabled source="name"/>
                <SelectInput source="component" choices={jdatastudioComponent()}/>
                <TextInput source="label" validate={[required()]}/>
                <FormDataConsumer>
                    {({formData, ...rest}) => formData.component === 'Reference' &&
                    <ReferenceInput source="reference" label="引用对象" reference="_entity">
                        <AutocompleteInput optionText="name"/>
                    </ReferenceInput>
                    }
                </FormDataConsumer>
                <FormDataConsumer>
                    {({formData, ...rest}) => formData.component === 'Reference' && formData.reference &&
                    <ReferenceInput source="referenceOptionText" label="显示的字段" reference="_field" filter={{ eid: formData.reference }}>
                        <AutocompleteInput optionValue="name" optionText="label"/>
                    </ReferenceInput>
                    }
                </FormDataConsumer>
                <FormDataConsumer>
                    {({formData, ...rest}) => formData.component === 'Select' &&
                    <TextInput multiline source="choicesStr" label="选项，value|name"/>
                    }
                </FormDataConsumer>
                <BooleanInput source="mainField"/>
                <BooleanInput source="partOfPrimaryKey"/>
                <SelectInput source="sensitiveType" choices={sensitiveType()}/>
            </FormTab>
            <FormTab label="列表">
                <BooleanInput source="showInList"/>
                <BooleanInput source="sortable"/>
                <BooleanInput source="showInShow"/>
            </FormTab>
            <FormTab label="筛选">
                <BooleanInput source="showInFilter"/>
                <BooleanInput source="alwaysOn"/>
                <FormDataConsumer>
                    {({formData, ...rest}) => ['Reference','Select'].includes(formData.component) &&
                    <BooleanInput source="multiFilter" label={"多选过滤"}/>
                    }
                </FormDataConsumer>
            </FormTab>
            <FormTab label="编辑">
                <BooleanInput source="showInEdit"/>
                <BooleanInput source="showInCreate"/>
                <NumberInput source="maxLength"/>
                <BooleanInput source="required"/>
                <TextInput source="defaultValue"/>
                <TextInput disabled source="dbColumnType"/>
            </FormTab>
        </TabbedForm>
    </Edit>

)