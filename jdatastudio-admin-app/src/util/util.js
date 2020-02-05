import React from "react";
import {
    CardActions,
    CreateButton
} from "react-admin";

export const WithoutExportActions = ({
                         basePath,
                         currentSort,
                         displayedFilters,
                         exporter,
                         filters,
                         filterValues,
                         resource,
                         showFilter
                     }) => (
    <CardActions>
        {filters && React.cloneElement(filters, {
            resource,
            showFilter,
            displayedFilters,
            filterValues,
            context: 'button',
        }) }
        <CreateButton basePath={basePath}/>
    </CardActions>
);