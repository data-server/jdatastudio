import React from "react";
import {
    AutocompleteInput,
    ReferenceArrayInput,
    SelectArrayInput,
    AutocompleteArrayInput,
    DateInput,
    NumberInput,
    BooleanInput,
    ReferenceInput,
    SelectInput,
    TextInput
} from "react-admin";

export const renderFilter = field => {
    switch (field.component) {
        case "Text":
            return renderTextFilter(field);
        case "Select":
            return renderSelectFilter(field);
        case "Reference":
            return renderReferenceFilter(field);
        case "Boolean":
            return renderBooleanFilter(field);
        case "Number":
            return renderNumberFilter(field);
        case "Date":
            return renderDateRangeFilter(field);
        default:
            return renderTextFilter(field);
    }
};

const renderDateRangeFilter = (field) => (
    [<DateInput key={field.id + '_gte'} label={field.label + '开始'} source={field.name + '_gte'}
                options={{locale: 'zh-hans'}} alwaysOn={field.alwaysOn}/>,
        <DateInput key={field.id + '_lte'} label={field.label + '截止'} source={field.name + '_lte'}
                   options={{locale: 'zh-hans'}} alwaysOn={field.alwaysOn}/>]
)

const renderNumberFilter = (field) => (
    [<NumberInput key={field.id + '_gte'} source={field.name + '_gte'} label={field.label + ' 大于等于'}
                  alwaysOn={field.alwaysOn}/>,
        <NumberInput key={field.id + '_lte'} source={field.name + '_lte'} label={field.label + ' 小于等于'}
                     alwaysOn={field.alwaysOn}/>]
)

const renderTextFilter = field => (
    <TextInput
        key={field.name}
        label={field.label}
        source={
            field.isFilter && field.fuzzyQuery ? field.name + "_like" : field.name
        }
        type={field.type}
        alwaysOn={field.alwaysOn}
        resettable
    />
);


const renderSelectFilter = field => (
    field.multiFilter ?
        <AutocompleteArrayInput
            key={field.name}
            label={field.label}
            source={field.name}
            choices={field.choices}
            alwaysOn={field.alwaysOn}
        /> : <AutocompleteInput
            key={field.name}
            label={field.label}
            source={field.name}
            choices={field.choices}
            alwaysOn={field.alwaysOn}
        />
);


const renderBooleanFilter = field => (
    <BooleanInput
        key={field.name}
        label={field.label}
        source={field.name}
        alwaysOn={field.alwaysOn}
    />
);

const renderReferenceFilter = field => (
    field.multiFilter ?
        <ReferenceArrayInput
            key={field.name}
            label={field.label}
            source={field.name}
            reference={"api/" + field.reference}
            alwaysOn={field.alwaysOn}
        >
            <AutocompleteArrayInput optionText={field.referenceOptionText}/>
        </ReferenceArrayInput> : <ReferenceInput
            key={field.name}
            label={field.label}
            source={field.name}
            reference={"api/" + field.reference}
            alwaysOn={field.alwaysOn}
        >
            <SelectInput optionText={field.referenceOptionText}/>
        </ReferenceInput>
);
