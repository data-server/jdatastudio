import chineseMessages from "./ra-language-chinese";

export default {
    ...chineseMessages,
    resources: {
        users: {
            name: '用户',
            fields: {
                username: '用户名',
                password: '密码',
                confirmPassword: '确认密码',
                modifyPassword: '修改密码',
                roles: '角色',
                enabled: '是否有效',
                lastPasswordResetDate: '密码最后重置时间'
            }
        },
        roles: {
            name: '角色',
            fields: {
                name: '角色名',
                permissions: '已有授权资源',
                users: '用户'
            },
            createAuth: '新建授权',
            authlist: '权限列表'
        },
        resources: {
            name: '资源',
            fields: {
                name: '资源名称',
                url: '路径',
                method: '方法',
            }
        },
        _datasource: {
            name: '数据源',
            fields: {
                name: '数据源名称',
                url: 'URL',
                username: '用户名',
                password: '密码',
                dbType: '数据源类型',
            }
        },
        _entity: {
            name: '对象',
            fields: {
                name: '对象名',
                label: '备注',
                jdatasource: '数据源'
            }
        },
        _field: {
            name: '字段',
            fields: {
                name: '字段名',
                label: '标签',
                partOfPrimaryKey: '是否联合主键',
                maxLength: '最大长度',
                required: '是否必填',
                defaultValue: '默认值',
                dbColumnType: '原始字段类型',
                component: '组件类型',
                sensitiveType: '脱敏类型',
                reference: "引用对象",
                referenceOptionText: "引用对象显示字段",
                showInList: "显示在列表",
                showInShow: "显示在详情",
                showInEdit: "显示在编辑",
                showInCreate: "显示在新建",
                showInFilter: "以此字段过滤",
                alwaysOn: "过滤条件总显示",
                sortable: "支持排序",
                choices:"选项值",
                mainField:"主字段(引用字段根据此字段匹配)",
                multiFilter:"多选过滤"
            }
        },
        _permission:{
            name:"授权",
            fields:{
                label:'标签',
                c:"新增",
                r:"读取",
                u:"编辑",
                d:"删除",
                filters:{
                 name:"过滤器",
                 fields:{
                     field:"字段",
                     operator:"操作符",
                     value:"过滤值",
                 }
                }
            }
        }
    }
};