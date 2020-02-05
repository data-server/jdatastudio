import React from "react";
import {
    AutocompleteInput,
    BooleanInput,
    email,
    maxLength,
    maxValue,
    minLength,
    minValue,
    number,
    ReferenceInput,
    required,
    SelectInput,
    TextInput,
    ImageInput,
    ImageField,
    FileInput,
    FileField
} from "react-admin";

export const renderInput = field => {
    switch (field.component) {
        case "Text":
            return renderTextInput(field);
        case "Select":
            return renderSelectInput(field);
        case "Reference":
            return renderReferenceInput(field);
        case "Boolean":
            return renderBooleanInput(field);
        case "Image":
            return renderImageInput(field);
        case "File":
            return renderFileInput(field);
        default:
            return renderTextInput(field);
    }
};

const renderTextInput = field => (
    <TextInput
        key={field.name}
        label={field.label}
        source={
            field.isFilter && field.fuzzyQuery ? field.name + "_like" : field.name
        }
        type={field.type}
        defaultValue={field.defaultValue}
        validate={generateValidators(field)}
        resettable
    />
);

const renderSelectInput = field => (
    <SelectInput
        key={field.name}
        label={field.label}
        source={field.name}
        choices={field.choices}
        defaultValue={field.defaultValue}
        validate={generateValidators(field)}
    />
);

const renderBooleanInput = field => (
    <BooleanInput
        key={field.name}
        label={field.label}
        source={field.name}
        defaultValue={field.defaultValue}
    />
);

const renderReferenceInput = field => (
  <ReferenceInput
    key={field.name}
    label={field.label}
    source={field.name}
    reference={field.reference}
    validate={generateValidators(field)}
  >
    <AutocompleteInput optionText={field.referenceOptionText} />
  </ReferenceInput>
);

const renderImageInput = field => (
    <ImageInput key={field.name}
                label={field.label}
                source={field.name} accept="image/*">
        <ImageField source="src" title="title" />
    </ImageInput>
)

const renderFileInput = field => (
    <FileInput key={field.name}
               label={field.label}
               source={field.name} accept="application/*">
        <FileField source="src" title="title" />
    </FileInput>
)

/**
 * generateValidators
 * @param {*} field
 */
const generateValidators = field => {
    const validators = [];
    if (field.isFilter) return validators;
    if (field.required) validators.push(required());
    if (field.type === "email" && field.component === "Text")
        validators.push(email());
    if (field.component === "Number") {
        validators.push(number());
        if (field.minValue) {
            validators.push(minValue(field.minValue));
        }
        if (field.maxValue) {
            validators.push(maxValue(field.maxValue));
        }
    }
    if (field.minLength) validators.push(minLength(field.minLength));
    if (field.maxLength) validators.push(maxLength(field.maxLength));
    return validators;
};
