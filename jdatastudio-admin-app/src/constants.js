import store from "storejs";

const api_url = function () {
    let api_url = "//" + window.location.host;
    if (process.env.NODE_ENV === 'development') {
        // api_url = "//" + window.location.hostname + ":8081"
        api_url = "https://www.jdatastudio.com"
    }
    return api_url;
}

// function getCookie(name) {
//     var arr = document.cookie.match(new RegExp("(^| )" + name + "=([^;]*)(;|$)"));
//     if (arr != null) return unescape(arr[2]);
//     return null;
// }
export const methodType = () => [
    {id: 'POST', name: '创建'},
    {id: 'GET', name: '查询'},
    {id: 'PUT', name: '更新'},
    {id: 'DELETE', name: '删除'},
];

export const jdatastudioComponent = () => [
    {id: 'Text', name: '文本'},
    {id: 'Number', name: '数字'},
    {id: 'Select', name: '选择'},
    {id: 'Reference', name: '引用'},
    {id: 'Date', name: '日期'},
    {id: 'Image', name: '图片'},
    {id: 'File', name: '文件'},
    // {id: 'DateTime', name: '日期时间'},
];
export const sensitiveType = () => [
    {id: 'nonsensitive', name: '非敏感数据'},
    {id: 'sensitive', name: '其他敏感数据'},
    {id: 'mobile', name: '手机'},
    {id: 'card', name: '银行卡号'},
    {id: 'id', name: '身份证号'},
]


export const dbType = () => [
    {id: 'mysql', name: 'MySql'},
    {id: 'elasticsearch', name: 'Elasticsearch'},
    {id: 'mongo', name: 'Mongodb'},
];

export const operatorType = () => [
    {id: 'eq', name: '等于'},
    {id: 'gte', name: '大于等于'},
    {id: 'gt', name: '大于'},
    {id: 'lte', name: '小于等于'},
    {id: 'lt', name: '小于'},
    {id: 'like', name: '模糊匹配'},
    {id: 'in', name: '存在于(,分隔)'},
];
export const isLogin = store.get('token');
export const url = api_url();
