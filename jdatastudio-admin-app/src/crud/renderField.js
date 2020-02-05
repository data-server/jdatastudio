import React from "react";
import {
    BooleanField,
    FileField,
    EmailField,
    ImageField,
    ReferenceField,
    SelectField,
    TextField,
    UrlField
} from "react-admin";
import {url} from "../constants";

export const renderField = field => {
    switch (field.component) {
        case "Text":
            return renderTextField(field);
        case "Select":
            return renderSelectField(field);
        case "Reference":
            return renderReferenceField(field);
        case "Boolean":
            return renderBooleanField(field);
        case "Image":
            return renderImageField(field);
        case "File":
            return renderFileField(field);
        default:
            return renderTextField(field);
    }
};

const renderSelectField = field => (
    <SelectField
        key={field.name}
        source={field.name}
        label={field.label}
        choices={field.choices}
        sortable={field.sortable}
    />
);

const renderTextField = field =>
    field.type === "email" ? (
        <EmailField
            key={field.name}
            label={field.label}
            source={field.name}
            sortable={field.sortable}
        />
    ) : field.type === "url" ? (
        <UrlField
            key={field.name}
            label={field.label}
            source={field.name}
            sortable={field.sortable}
        />
    ) : (
        <TextField
            key={field.name}
            label={field.label}
            source={field.name}
            sortable={field.sortable}
        />
    );

const renderReferenceField = field => (
    <ReferenceField
        key={field.name}
        label={field.label}
        source={field.name}
        reference={"api/" + field.reference}
        link="show"
        sortable={field.sortable}
    >
        <TextField source={field.referenceOptionText}/>
    </ReferenceField>
);

const renderBooleanField = field => (
    <BooleanField
        key={field.name}
        label={field.label}
        source={field.name}
        sortable={field.sortable}
    />
);

const renderImageField = field => (
    <ImageField
        key={field.name}
        label={field.label}
        source={field.name + ".src"}
        title={field.name + ".title"}
    />
);

const renderFileField = field => (
    <FileField
        key={field.name}
        label={field.label}
        source={field.name+".src"}
        target="_blank"
        title={field.name + ".title"}
    />
);