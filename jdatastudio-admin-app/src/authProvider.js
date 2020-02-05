import { AUTH_LOGIN, AUTH_LOGOUT, AUTH_ERROR, AUTH_CHECK,AUTH_GET_PERMISSIONS,fetchUtils  } from "react-admin";
import {url} from "./constants";
import store from "storejs";
/**
 * add token before request
 * @param url
 * @param options
 * @returns {*}
 */
export const httpClient = (urlPath, options = {}) => {
    if (!options.headers) {
        options.headers = new Headers({Accept: 'application/json'});
    }
    const token = store.get('token');
    if (token && token !== 'undefined') {
        options.headers.set('Authorization', `Bearer ${token}`);
    }
    options.credentials = 'same-origin';
    options.mode = 'cors';
    return fetchUtils.fetchJson(urlPath, options);
}

export const authProvider =  (type, params) => {
    if (type === AUTH_LOGIN) {
        const { username, password } = params;
        const request = new Request(url + "/auth", {
            method: "POST",
            mode: "cors",
            body: JSON.stringify({ username, password }),
            headers: new Headers({ "Content-Type": "application/json" })
        });
        return fetch(request)
            .then(response => {
                if (response.status < 200 || response.status >= 300) {
                    throw new Error(response.statusText);
                }
                return response.json();
            })
            .then(({ token, roleName }) => {
                store.set("token", token);
                store.set("permissions", roleName);
            });
    }
    if (type === AUTH_LOGOUT) {
        store.remove("token");
        store.remove("permissions");
        return Promise.resolve();
    }
    if (type === AUTH_ERROR) {
        const { status } = params;
        if (status === 401 || status === 403) {
            store.remove("token");
            return Promise.reject();
        }
        return Promise.resolve();
    }
    if (type === AUTH_CHECK) {
        return store.get("token") ? Promise.resolve() : Promise.reject();
    }

    if (type === AUTH_GET_PERMISSIONS) {
        return Promise.resolve(store.get("permissions"));
    }

    return Promise.reject("Unkown method");
};
