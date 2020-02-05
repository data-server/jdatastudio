import React, {Fragment} from "react";
import {Admin, Resource, resolveBrowserLocale} from "react-admin";
import {UserCreate, UserEdit, UserList, UserShow} from "./admin/User";
import {RoleCreate, RoleEdit, RoleList, RoleShow} from "./admin/Role";
import {DataSourceCreate, DataSourceEdit, DataSourceList} from "./sys/DataSource";
import {EntityEdit, EntityList} from "./sys/Entity";
import {FieldEdit} from "./sys/Field";
import {PermissionEdit} from "./admin/Permission";
import {url} from "./constants";
import jsonServerProvider from "./dataProvider";
import chineseMessages from "./i18n/zh-hans";
import ActionSupervisorAccount from "@material-ui/icons/SupervisorAccount";
import ActionAccessibility from "@material-ui/icons/Accessibility";
import StorageIcon from "@material-ui/icons/Storage";
import Message from "@material-ui/icons/Message";
import {authProvider, httpClient} from "./authProvider";
import addUploadFeature from './addUploadFeature';
import {CRUDCreate, CRUDEdit, CRUDList, CRUDShow} from "./crud";
import polyglotI18nProvider from 'ra-i18n-polyglot';
import englishMessages from 'ra-language-english';

const i18nProvider = polyglotI18nProvider(locale =>
        locale === 'en' ? englishMessages : chineseMessages,
    'ch'
);

const fetchResources = permissions =>
    httpClient(url + "/schemas")
        .then(response => {
            return response.json;
        })
        .then(json => {
            let resources = [];
            json.map(resource =>
                resources.push(<Resource
                    key={resource.id}
                    name={"api/" + resource.eid}
                    options={resource}
                    list={resource.r ? CRUDList : null}
                    edit={resource.u ? CRUDEdit : null}
                    create={resource.c ? CRUDCreate : null}
                    show={resource.r ? CRUDShow : null}
                />)
            )
            if (permissions.indexOf("ROLE_ADMIN") >= 0) {
                resources = resources.concat(adminResources);
            }
            return resources;
        })
        .catch(error => {
            if (error.status === 401) {
                localStorage.removeItem('token');
                localStorage.removeItem('permissions');
                window.location.replace('/#/login');
            }
        });

const adminResources = [
    <Resource name="users" icon={ActionSupervisorAccount} list={UserList} create={UserCreate} edit={UserEdit}
              show={UserShow}/>,
    <Resource name="roles" icon={ActionAccessibility} list={RoleList} create={RoleCreate} edit={RoleEdit}
              show={RoleShow}/>,
    <Resource name="_datasource" icon={StorageIcon} list={DataSourceList} create={DataSourceCreate}
              edit={DataSourceEdit}/>,
    <Resource name="_entity" icon={Message} list={EntityList} edit={EntityEdit}/>,
    <Resource name="_field" icon={Message} edit={FieldEdit}/>,
    <Resource name="_permission" edit={PermissionEdit}/>
]
const dataProvider = jsonServerProvider(url, httpClient);
const uploadCapableDataProvider = addUploadFeature(dataProvider);
const App = () => (
    <Admin authProvider={authProvider} dataProvider={uploadCapableDataProvider}
           i18nProvider={i18nProvider}>
        {fetchResources}
    </Admin>
);

export default App;
