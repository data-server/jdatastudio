(window.webpackJsonp=window.webpackJsonp||[]).push([[0],{462:function(e,a){e.exports={ra:{action:{delete:"\u5220\u9664",remove:"\u5220\u9664\u9009\u9879",show:"\u663e\u793a",list:"\u5217\u8868",save:"\u4fdd\u5b58",create:"\u65b0\u5efa",edit:"\u7f16\u8f91",cancel:"\u53d6\u6d88",refresh:"\u5237\u65b0",add_filter:"\u6dfb\u52a0\u641c\u7d22\u6761\u4ef6",add:"\u589e\u52a0\u9009\u9879",remove_filter:"\u5220\u9664\u641c\u7d22\u6761\u4ef6",back:"\u8fd4\u56de",export:"\u4e0b\u8f7d"},boolean:{true:"\u662f",false:"\u5426"},page:{list:"%{name} \u5217\u8868",edit:"%{name} #%{id}",show:"%{name} #%{id}",create:"\u65b0\u5efa %{name}",delete:"\u5220\u9664 %{name} #%{id}",dashboard:"\u63a7\u5236\u53f0",not_found:"\u6ca1\u6709\u627e\u5230",loading:"\u6b63\u5728\u52a0\u8f7d\u9875\u9762"},input:{file:{upload_several:"\u62d6\u52a8\u4e00\u4e9b\u6587\u4ef6\u6216\u70b9\u51fb\u4e0a\u4f20.",upload_single:"\u62d6\u52a8\u4e00\u4e2a\u6587\u4ef6\u6216\u70b9\u51fb\u4e0a\u4f20."},image:{upload_several:"\u62d6\u52a8\u4e00\u4e9b\u56fe\u7247\u6587\u4ef6\u6216\u70b9\u51fb\u4e0a\u4f20.",upload_single:"\u62d6\u52a8\u4e00\u4e2a\u56fe\u7247\u6587\u4ef6\u6216\u70b9\u51fb\u4e0a\u4f20."}},message:{yes:"\u662f",no:"\u5426",are_you_sure:"\u4f60\u786e\u5b9a?",about:"\u5173\u4e8e",not_found:"\u8f93\u5165\u4e86\u4e00\u4e2a\u9519\u8bef\u7684URL,\u6216\u8005\u975e\u6cd5\u7684\u8d85\u94fe\u63a5.",loading:"\u6b63\u5728\u52a0\u8f7d\u9875\u9762"},navigation:{no_results:"\u627e\u4e0d\u5230\u8bb0\u5f55",page_out_of_boundaries:"%{page}\u4e0d\u5728\u8303\u56f4\u5185",page_out_from_end:"\u8d85\u8fc7\u6700\u540e1\u9875",page_out_from_begin:"\u8d85\u51fa\u7b2c\u4e00\u9875",page_range_info:"%{offsetBegin}-%{offsetEnd} \u4e00\u5171 %{total}",page_rows_per_page:"\u6bcf\u9875\u6761\u6570:",next:"\u4e0b\u4e00\u9875",prev:"\u4e0a\u4e00\u9875"},auth:{username:"\u7528\u6237\u540d",password:"\u5bc6\u7801",sign_in:"\u767b\u5f55",sign_in_error:"\u9a8c\u8bc1\u5931\u8d25,\u8bf7\u91cd\u8bd5",logout:"\u6ce8\u9500"},notification:{updated:"\u8bb0\u5f55\u5df2\u66f4\u65b0",created:"\u8bb0\u5f55\u5df2\u65b0\u5efa",deleted:"\u8bb0\u5f55\u5df2\u5220\u9664",item_doesnt_exist:"\u8bb0\u5f55\u4e0d\u5b58\u5728",http_error:"\u670d\u52a1\u5668\u901a\u8baf\u9519\u8bef"},validation:{required:"\u5fc5\u987b",minLength:"\u81f3\u5c11%{min}\u4e2a\u5b57\u7b26",maxLength:"\u5fc5\u987b\u4e0d\u5927\u4e8e%{max}\u4e2a\u5b57\u7b26",minValue:"\u81f3\u5c11\u662f%{min}",maxValue:"\u4e0d\u8d85\u8fc7%{max}",number:"\u5fc5\u987b\u662f\u6570\u5b57",email:"\u5fc5\u987b\u662f\u5408\u6cd5\u7684email\u5730\u5740"}}}},513:function(e,a,t){e.exports=t(906)},518:function(e,a,t){},906:function(e,a,t){"use strict";t.r(a);var n=t(0),r=t.n(n),l=t(42),c=t.n(l),o=(t(518),t(951)),s=t(952),i=t(66),u=t(955),m=t(912),d=t(953),f=t(954),b=t(918),E=t(963),p=t(921),h=t(922),g=t(923),y=t(924),O=t(925),w=t(964),v=t(927),j=t(957),_=t(931),k=t(932),x=t(965),T=t(933),P=t(934),S=t(956),I=t(935),C=t(936),N=t(937),F=t(149),D=t(150),L=t(172),V=t(151),R=t(173),A=t(431),q=t.n(A),z=t(32),J=t.n(z),M=function(e){function a(){return Object(F.a)(this,a),Object(L.a)(this,Object(V.a)(a).apply(this,arguments))}return Object(R.a)(a,e),Object(D.a)(a,[{key:"render",value:function(){var e=this.props.input.onChange;return r.a.createElement("span",null,r.a.createElement(m.a,{source:"password",label:"\u5bc6\u7801"}),"\xa0",r.a.createElement(J.a,{label:"Reset",onClick:function(){e(function(){for(var e="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789",a="",t=0,n=e.length;t<8;++t)a+=e.charAt(Math.floor(Math.random()*n));return a}())}},r.a.createElement(q.a,null)))}}]),a}(n.Component),B=t(426),H=t(129),U=t(914),W=function(e){var a=e.basePath,t=(e.currentSort,e.displayedFilters),n=(e.exporter,e.filters),l=e.filterValues,c=e.resource,o=e.showFilter;return r.a.createElement(H.a,null,n&&r.a.cloneElement(n,{resource:c,showFilter:o,displayedFilters:t,filterValues:l,context:"button"}),r.a.createElement(U.a,{basePath:a}))},K=function(e){return r.a.createElement(u.a,e,r.a.createElement(m.a,{source:"username",alwaysOn:!0}))},Q=function(){return r.a.createElement(B.a,{name:"password",component:M,validate:[Object(O.g)()]})},X=t(962),G=t(938),Y=t(8),Z=t(125),$=t(30),ee=t(71),ae=(ee.a.get("token"),function(){var e="//"+window.location.host;return e}()),te=t(806),ne=t(411),re=function(e){var a=arguments.length>1&&void 0!==arguments[1]?arguments[1]:{};a.headers||(a.headers=new Headers({Accept:"application/json"}));var t=ee.a.get("token");return t&&"undefined"!==t&&a.headers.set("Authorization","Bearer ".concat(t)),a.credentials="same-origin",a.mode="cors",te.a.fetchJson(e,a)},le=function(e,a){if(e===ne.d){var t=a.username,n=a.password,r=new Request(ae+"/auth",{method:"POST",mode:"cors",body:JSON.stringify({username:t,password:n}),headers:new Headers({"Content-Type":"application/json"})});return fetch(r).then(function(e){if(e.status<200||e.status>=300)throw new Error(e.statusText);return e.json()}).then(function(e){var a=e.token,t=e.roleName;ee.a.set("token",a),ee.a.set("permissions",t)})}if(e===ne.e)return ee.a.remove("token"),ee.a.remove("permissions"),Promise.resolve();if(e===ne.b){var l=a.status;return 401===l||403===l?(ee.a.remove("token"),Promise.reject()):Promise.resolve()}return e===ne.a?ee.a.get("token")?Promise.resolve():Promise.reject():e===ne.c?Promise.resolve(ee.a.get("permissions")):Promise.reject("Unkown method")},ce=t(153),oe=t.n(ce),se=t(247),ie=t.n(se),ue=function(e){function a(e){var t;return Object(F.a)(this,a),(t=Object(L.a)(this,Object(V.a)(a).call(this,e))).handleCheck=function(){var e=t.props,a=e.record,n=e.showNotification,r=e.roleId,l=e.source,c=a,o=t.state.status;c.id=a.id,c[l]=!o,re(ae+"/roles/".concat(r,"/editPermission"),{method:"PUT",body:JSON.stringify(c)}).then(function(){n("\u64cd\u4f5c\u6210\u529f"),t.setState({status:!o}),Object($.c)("/permissions")}).catch(function(e){console.error(e),n("\u64cd\u4f5c\u5931\u8d25","warning")})},t.state={status:e.record[e.source]},t}return Object(R.a)(a,e),Object(D.a)(a,[{key:"render",value:function(){return this.state.status?r.a.createElement(J.a,{onClick:this.handleCheck},r.a.createElement(ie.a,null)):r.a.createElement(J.a,{onClick:this.handleCheck},r.a.createElement(oe.a,null))}}]),a}(n.Component),me=Object(Y.b)(null,{showNotification:Z.d,push:$.c})(ue),de=t(939),fe=t(940),be=t(28),Ee=t.n(be),pe=t(459),he=t.n(pe),ge=function(e){function a(){var e,t;Object(F.a)(this,a);for(var n=arguments.length,r=new Array(n),l=0;l<n;l++)r[l]=arguments[l];return(t=Object(L.a)(this,(e=Object(V.a)(a)).call.apply(e,[this].concat(r)))).handleSync=function(){var e=t.props,a=e.record,n=e.showNotification;re(ae+"/_datasource/sync/".concat(a.id),{method:"GET"}).then(function(){n("\u540c\u6b65\u6210\u529f!")}).catch(function(e){console.error(e),n(e.message,"warning")})},t}return Object(R.a)(a,e),Object(D.a)(a,[{key:"render",value:function(){return r.a.createElement(Ee.a,{onClick:this.handleSync},r.a.createElement(he.a,null),"\u540c\u6b65\u8868\u7ed3\u6784")}}]),a}(n.Component),ye=Object(Y.b)(null,{showNotification:Z.d,push:$.c})(ge),Oe=t(961),we=t(941),ve=t(942),je=t(943),_e=t(960),ke=function(e){return r.a.createElement(u.a,e,r.a.createElement(Oe.a,{source:"jdatasource",reference:"_datasource",alwaysOn:!0},r.a.createElement(we.a,{optionText:"name"})))},xe=t(944),Te=t(945),Pe=function(e,a,t){return"/_entity/"+a.substr(0,a.indexOf("_"))+"/fields"},Se=t(946),Ie=t(947),Ce=function(e,a,t){return"/roles/"+a.substr(0,a.lastIndexOf("_"))+"/show"},Ne=t(89),Fe=t(176),De=t(69),Le=t(26),Ve=function(e){var a=arguments.length>1&&void 0!==arguments[1]?arguments[1]:te.a.fetchJson;return function(t,n,r){if(t===Le.e)return Promise.all(r.ids.map(function(t){return a("".concat(e,"/").concat(n,"/").concat(t))})).then(function(e){return{data:e.map(function(e){return e.json})}});if(t===Le.i)return Promise.all(r.ids.map(function(t){return a("".concat(e,"/").concat(n,"/").concat(t),{method:"PATCH",body:JSON.stringify(r.data)})})).then(function(e){return{data:e.map(function(e){return e.json})}});if(t===Le.c)return Promise.all(r.ids.map(function(t){return a("".concat(e,"/").concat(n,"/").concat(t),{method:"DELETE"})})).then(function(e){return{data:e.map(function(e){return e.json})}});var l=function(a,t,n){var r="",l={};for(var c in n.filter)Array.isArray(n.filter[c])&&(n.filter[c+"_in"]=n.filter[c].join(","),delete n.filter[c]);switch(a){case Le.d:var o=n.pagination,s=o.page,i=o.perPage,u=n.sort,m=u.field,d=u.order,f=Object(Fe.a)({},te.a.flattenObject(n.filter),{_sort:m,_order:d,_start:(s-1)*i,_end:s*i});r="".concat(e,"/").concat(t,"?").concat(Object(De.stringify)(f));break;case Le.g:r="".concat(e,"/").concat(t,"/").concat(n.id);break;case Le.f:var b,E=n.pagination,p=E.page,h=E.perPage,g=n.sort,y=g.field,O=g.order,w=Object(Fe.a)({},te.a.flattenObject(n.filter),(b={},Object(Ne.a)(b,n.target,n.id),Object(Ne.a)(b,"_sort",y),Object(Ne.a)(b,"_order",O),Object(Ne.a)(b,"_start",(p-1)*h),Object(Ne.a)(b,"_end",p*h),b));r="".concat(e,"/").concat(t,"?").concat(Object(De.stringify)(w));break;case Le.h:r="".concat(e,"/").concat(t,"/").concat(n.id),l.method="PUT",l.body=JSON.stringify(n.data);break;case Le.a:r="".concat(e,"/").concat(t),l.method="POST",l.body=JSON.stringify(n.data);break;case Le.b:r="".concat(e,"/").concat(t,"/").concat(n.id),l.method="DELETE";break;case Le.e:var v=Object(Ne.a)({},"id_like",n.ids.join("|"));r="".concat(e,"/").concat(t,"?").concat(Object(De.stringify)(v));break;default:throw new Error("Unsupported fetch action type ".concat(a))}return{url:r,options:l}}(t,n,r),c=l.url,o=l.options;return a(c,o).then(function(e){return function(e,a,t,n){var r=e.headers,l=e.json;switch(a){case Le.d:case Le.f:if(!r.has("x-total-count"))throw new Error("The X-Total-Count header is missing in the HTTP Response. The jsonServer Data Provider expects responses for lists of resources to contain this header with the total number of results to build the pagination. If you are using CORS, did you declare X-Total-Count in the Access-Control-Expose-Headers header?");return{data:l,total:parseInt(r.get("x-total-count").split("/").pop(),10)};case Le.a:return{data:Object(Fe.a)({},n.data,{id:l.id})};default:return{data:l}}}(e,t,0,r)})}},Re=t(462),Ae=t.n(Re),qe=Object(Fe.a)({},Ae.a,{resources:{users:{name:"\u7528\u6237",fields:{username:"\u7528\u6237\u540d",password:"\u5bc6\u7801",confirmPassword:"\u786e\u8ba4\u5bc6\u7801",modifyPassword:"\u4fee\u6539\u5bc6\u7801",roles:"\u89d2\u8272",enabled:"\u662f\u5426\u6709\u6548",lastPasswordResetDate:"\u5bc6\u7801\u6700\u540e\u91cd\u7f6e\u65f6\u95f4"}},roles:{name:"\u89d2\u8272",fields:{name:"\u89d2\u8272\u540d",permissions:"\u5df2\u6709\u6388\u6743\u8d44\u6e90",users:"\u7528\u6237"},createAuth:"\u65b0\u5efa\u6388\u6743",authlist:"\u6743\u9650\u5217\u8868"},resources:{name:"\u8d44\u6e90",fields:{name:"\u8d44\u6e90\u540d\u79f0",url:"\u8def\u5f84",method:"\u65b9\u6cd5"}},_datasource:{name:"\u6570\u636e\u6e90",fields:{name:"\u6570\u636e\u6e90\u540d\u79f0",url:"URL",username:"\u7528\u6237\u540d",password:"\u5bc6\u7801",dbType:"\u6570\u636e\u6e90\u7c7b\u578b"}},_entity:{name:"\u5bf9\u8c61",fields:{name:"\u5bf9\u8c61\u540d",label:"\u5907\u6ce8",jdatasource:"\u6570\u636e\u6e90"}},_field:{name:"\u5b57\u6bb5",fields:{name:"\u5b57\u6bb5\u540d",label:"\u6807\u7b7e",partOfPrimaryKey:"\u662f\u5426\u8054\u5408\u4e3b\u952e",maxLength:"\u6700\u5927\u957f\u5ea6",required:"\u662f\u5426\u5fc5\u586b",defaultValue:"\u9ed8\u8ba4\u503c",dbColumnType:"\u539f\u59cb\u5b57\u6bb5\u7c7b\u578b",component:"\u7ec4\u4ef6\u7c7b\u578b",sensitiveType:"\u8131\u654f\u7c7b\u578b",reference:"\u5f15\u7528\u5bf9\u8c61",referenceOptionText:"\u5f15\u7528\u5bf9\u8c61\u663e\u793a\u5b57\u6bb5",showInList:"\u663e\u793a\u5728\u5217\u8868",showInShow:"\u663e\u793a\u5728\u8be6\u60c5",showInEdit:"\u663e\u793a\u5728\u7f16\u8f91",showInCreate:"\u663e\u793a\u5728\u65b0\u5efa",showInFilter:"\u4ee5\u6b64\u5b57\u6bb5\u8fc7\u6ee4",alwaysOn:"\u8fc7\u6ee4\u6761\u4ef6\u603b\u663e\u793a",sortable:"\u652f\u6301\u6392\u5e8f",choices:"\u9009\u9879\u503c",mainField:"\u4e3b\u5b57\u6bb5(\u5f15\u7528\u5b57\u6bb5\u6839\u636e\u6b64\u5b57\u6bb5\u5339\u914d)",multiFilter:"\u591a\u9009\u8fc7\u6ee4"}},_permission:{name:"\u6388\u6743",fields:{label:"\u6807\u7b7e",c:"\u65b0\u589e",r:"\u8bfb\u53d6",u:"\u7f16\u8f91",d:"\u5220\u9664",filters:{name:"\u8fc7\u6ee4\u5668",fields:{field:"\u5b57\u6bb5",operator:"\u64cd\u4f5c\u7b26",value:"\u8fc7\u6ee4\u503c"}}}}}}),ze=t(468),Je=t.n(ze),Me=t(469),Be=t.n(Me),He=t(470),Ue=t.n(He),We=t(331),Ke=t.n(We),Qe=t(4),Xe=t.n(Qe),Ge=t(64),Ye=t.n(Ge),Ze=t(255),$e=t.n(Ze),ea=t(5),aa=t(465),ta=t.n(aa),na=t(412),ra=t(463),la=t(425),ca=t(6),oa=t.n(ca),sa=t(254),ia=t.n(sa),ua=t(53),ma=t.n(ua),da=t(79),fa=t.n(da),ba=t(126),Ea=t(44),pa=function(e){var a=e.meta,t=(a=void 0===a?{}:a).touched,n=a.error,l=Object(ra.a)({},e.input),c=Object(i.a)(e,["meta","input"]);return r.a.createElement(ma.a,Object.assign({error:!(!t||!n),helperText:t&&n},l,c,{fullWidth:!0}))},ha=function(e,a,t){var n=t.redirectTo;return a(Object(ba.h)(e,n))},ga=oa()(Object(ea.withStyles)(function(){return{form:{padding:"0 1em 1em 1em"},input:{marginTop:"1em"},button:{width:"100%"}}}),Ea.a,Object(Y.b)(function(e){return{isLoading:e.admin.loading>0}}),Object(la.a)({form:"signIn",validate:function(e,a){var t={},n=a.translate;return e.username||(t.username=n("ra.validation.required")),e.password||(t.password=n("ra.validation.required")),t}}))(function(e){var a=e.classes,t=e.isLoading,n=e.handleSubmit,l=e.translate;return r.a.createElement("form",{onSubmit:n(ha)},r.a.createElement("div",{className:a.form},r.a.createElement("div",{className:a.input},r.a.createElement(B.a,{name:"username",component:pa,label:l("ra.auth.username"),disabled:t})),r.a.createElement("div",{className:a.input},r.a.createElement(B.a,{name:"password",component:pa,label:l("ra.auth.password"),type:"password",disabled:t}))),r.a.createElement(ia.a,null,r.a.createElement(Ee.a,{variant:"raised",type:"submit",color:"secondary",disabled:t,className:a.button},t&&r.a.createElement(fa.a,{size:25,thickness:2}),l("ra.auth.sign_in"))))}),ya=t(464),Oa=function(e){var a=e.classes,t=e.className,n=e.loginForm,l=Object(i.a)(e,["classes","className","loginForm"]);return r.a.createElement("div",Object.assign({className:Xe()(a.main,t)},function(e){e.classes,e.className,e.location,e.title,e.array,e.theme,e.staticContext;return Object(i.a)(e,["classes","className","location","title","array","theme","staticContext"])}(l)),r.a.createElement(Ye.a,{className:a.card},r.a.createElement("div",{className:a.avatar},r.a.createElement($e.a,{className:a.icon},r.a.createElement(ta.a,null))),n),r.a.createElement(na.a,null))};Oa.defaultProps={loginForm:r.a.createElement(ga,null)};var wa=Object(ea.withStyles)(function(e){return{main:{display:"flex",flexDirection:"column",minHeight:"100vh",alignItems:"center",justifyContent:"center",backgroundColor:"red",backgroundSize:"cover"},card:{minWidth:300},avatar:{margin:"1em",display:"flex",justifyContent:"center"},icon:{backgroundColor:ya.red[500]}}})(Oa),va=t(907),ja=t(948),_a=t(949),ka=t(959),xa=function(e){switch(e.component){case"Text":return Pa(e);case"Select":return Ta(e);case"Reference":return Sa(e);case"Boolean":return Ia(e);default:return Pa(e)}},Ta=function(e){return r.a.createElement(de.a,{key:e.name,source:e.name,label:e.label,choices:e.choices,sortable:e.sortable})},Pa=function(e){return"email"===e.type?r.a.createElement(ja.a,{key:e.name,label:e.label,source:e.name,sortable:e.sortable}):"url"===e.type?r.a.createElement(_a.a,{key:e.name,label:e.label,source:e.name,sortable:e.sortable}):r.a.createElement(b.a,{key:e.name,label:e.label,source:e.name,sortable:e.sortable})},Sa=function(e){return r.a.createElement(ka.a,{key:e.name,label:e.label,source:e.name,reference:"api/"+e.reference,linkType:"show",sortable:e.sortable},r.a.createElement(b.a,{source:e.referenceOptionText}))},Ia=function(e){return r.a.createElement(C.a,{key:e.name,label:e.label,source:e.name,sortable:e.sortable})},Ca=t(950),Na=t(958),Fa=function(e){return[r.a.createElement(Ca.a,{key:e.id+"_gte",label:e.label+"\u5f00\u59cb",source:e.name+"_gte",options:{locale:"zh-hans"},alwaysOn:e.alwaysOn}),r.a.createElement(Ca.a,{key:e.id+"_lte",label:e.label+"\u622a\u6b62",source:e.name+"_lte",options:{locale:"zh-hans"},alwaysOn:e.alwaysOn})]},Da=function(e){return[r.a.createElement(Te.a,{key:e.id+"_gte",source:e.name+"_gte",label:e.label+" \u5927\u4e8e\u7b49\u4e8e",alwaysOn:e.alwaysOn}),r.a.createElement(Te.a,{key:e.id+"_lte",source:e.name+"_lte",label:e.label+" \u5c0f\u4e8e\u7b49\u4e8e",alwaysOn:e.alwaysOn})]},La=function(e){return r.a.createElement(m.a,{key:e.name,label:e.label,source:e.isFilter&&e.fuzzyQuery?e.name+"_like":e.name,type:e.type,alwaysOn:e.alwaysOn,resettable:!0})},Va=function(e){return e.multiFilter?r.a.createElement(Na.a,{key:e.name,label:e.label,source:e.name,choices:e.choices,alwaysOn:e.alwaysOn}):r.a.createElement(we.a,{key:e.name,label:e.label,source:e.name,choices:e.choices,alwaysOn:e.alwaysOn})},Ra=function(e){return r.a.createElement(k.a,{key:e.name,label:e.label,source:e.name,alwaysOn:e.alwaysOn})},Aa=function(e){return e.multiFilter?r.a.createElement(j.a,{key:e.name,label:e.label,source:e.name,reference:"api/"+e.reference,alwaysOn:e.alwaysOn},r.a.createElement(Na.a,{optionText:e.referenceOptionText})):r.a.createElement(Oe.a,{key:e.name,label:e.label,source:e.name,reference:"api/"+e.reference,alwaysOn:e.alwaysOn},r.a.createElement(fe.a,{optionText:e.referenceOptionText}))},qa=function(e){return r.a.createElement(va.a,Object.assign({rowsPerPageOptions:[10,25,50,100]},e))},za=function(e){var a=e.options;return r.a.createElement(d.a,Object.assign({},e,{pagination:r.a.createElement(qa,null),filters:r.a.createElement(Ja,e),title:a.label,bulkActionButtons:!1}),r.a.createElement(f.a,null,a.fields.filter(function(e){return e.showInList}).map(xa),r.a.createElement(g.a,null),a.u?r.a.createElement(y.a,null):r.a.createElement(n.Fragment,null)))},Ja=function(e){var a=e.options;return r.a.createElement(u.a,e,a.fields.filter(function(e){return e.showInFilter}).map(Ma))},Ma=function(e){return function(e){switch(e.component){case"Text":return La(e);case"Select":return Va(e);case"Reference":return Aa(e);case"Boolean":return Ra(e);case"Number":return Da(e);case"Date":return Fa(e);default:return La(e)}}(e)},Ba=function(e){switch(e.component){case"Text":return Ha(e);case"Select":return Ua(e);case"Reference":return Ka(e);case"Boolean":return Wa(e);default:return Ha(e)}},Ha=function(e){return r.a.createElement(m.a,{key:e.name,label:e.label,source:e.isFilter&&e.fuzzyQuery?e.name+"_like":e.name,type:e.type,defaultValue:e.defaultValue,validate:Qa(e),resettable:!0})},Ua=function(e){return r.a.createElement(fe.a,{key:e.name,label:e.label,source:e.name,choices:e.choices,defaultValue:e.defaultValue,validate:Qa(e)})},Wa=function(e){return r.a.createElement(k.a,{key:e.name,label:e.label,source:e.name,defaultValue:e.defaultValue})},Ka=function(e){return r.a.createElement(Oe.a,{key:e.name,label:e.label,source:e.name,reference:"api/"+e.reference,validate:Qa(e)},r.a.createElement(we.a,{optionText:e.referenceOptionText}))},Qa=function(e){var a=[];return e.isFilter?a:(e.required&&a.push(Object(O.g)()),"email"===e.type&&"Text"===e.component&&a.push(Object(O.a)()),"Number"===e.component&&(a.push(Object(O.f)()),e.minValue&&a.push(Object(O.e)(e.minValue)),e.maxValue&&a.push(Object(O.c)(e.maxValue))),e.minLength&&a.push(Object(O.d)(e.minLength)),e.maxLength&&a.push(Object(O.b)(e.maxLength)),a)},Xa=t(805),Ga=function(e){var a=e.options;return r.a.createElement(x.a,Object.assign({actions:r.a.createElement(Xa.a,{options:e})},e,{title:a.label}),r.a.createElement(v.a,{redirect:a.redirect},a.fields.filter(function(e){return e.showInEdit}).map(Ba)))},Ya=function(e){var a=e.options;return r.a.createElement(w.a,Object.assign({},e,{title:a.label}),r.a.createElement(v.a,{redirect:a.redirect},a.fields.filter(function(e){return e.showInCreate}).map(Ba)))},Za=function(e){var a=e.options;return r.a.createElement(S.a,Object.assign({},e,{title:a.label}),r.a.createElement(X.a,null,r.a.createElement(G.a,{label:"Detail"},a.fields.filter(function(e){return e.showInShow}).map(xa),a.related?a.related.map($a):null)))},$a=function(e){return r.a.createElement(_e.a,{key:"rl"+e.id,reference:"api/"+e.name,target:e.relatedTarget,addLabel:!1},r.a.createElement(f.a,null,e.fields.filter(function(e){return e.showInList}).map(xa),r.a.createElement(g.a,null),e.u?r.a.createElement(y.a,null):r.a.createElement(n.Fragment,null)))},et=function(e){return qe},at=function(e){return re(ae+"/schemas").then(function(e){return e.json}).then(function(a){var t=[];return a.map(function(e){return t.push(r.a.createElement(o.a,{key:e.id,name:"api/"+e.eid,options:e,list:e.r?za:r.a.createElement(n.Fragment,null),edit:e.u?Ga:r.a.createElement(n.Fragment,null),create:e.c?Ya:null,show:e.r?Za:r.a.createElement(n.Fragment,null)}))}),e.indexOf("ROLE_ADMIN")>=0&&(t=t.concat(tt)),t}).catch(function(e){401===e.status&&(localStorage.removeItem("token"),localStorage.removeItem("permissions"),window.location.replace("/#/login"))})},tt=[r.a.createElement(o.a,{name:"users",icon:Je.a,list:function(e){return r.a.createElement(d.a,Object.assign({},e,{actions:r.a.createElement(W,null),bulkActions:!1,filters:r.a.createElement(K,null)}),r.a.createElement(f.a,null,r.a.createElement(b.a,{source:"username"}),r.a.createElement(E.a,{label:"\u89d2\u8272",reference:"roles",source:"roles"},r.a.createElement(p.a,null,r.a.createElement(h.a,{source:"name"}))),r.a.createElement(b.a,{source:"lastPasswordResetDate"}),r.a.createElement(g.a,null),r.a.createElement(y.a,null)))},create:function(e){return r.a.createElement(w.a,e,r.a.createElement(v.a,null,r.a.createElement(m.a,{source:"username",validate:[Object(O.g)()]}),r.a.createElement(Q,{validate:[Object(O.g)()]}),r.a.createElement(j.a,{reference:"roles",source:"roles",perPage:1e3,validate:[Object(O.g)()]},r.a.createElement(_.a,{optionText:"name"})),r.a.createElement(k.a,{source:"enabled",defaultValue:!0})))},edit:function(e){return r.a.createElement(x.a,e,r.a.createElement(v.a,null,r.a.createElement(T.a,{source:"username"}),r.a.createElement(k.a,{source:"enabled"}),r.a.createElement(k.a,{source:"modifyPassword"}),r.a.createElement(P.a,null,function(e){var a=e.formData,t=Object(i.a)(e,["formData"]);return a.modifyPassword&&r.a.createElement(Q,t)}),r.a.createElement(j.a,{source:"roles",reference:"roles",perPage:100},r.a.createElement(_.a,{optionText:"name"}))))},show:function(e){return r.a.createElement(S.a,e,r.a.createElement(I.a,null,r.a.createElement(b.a,{source:"username"}),r.a.createElement(C.a,{source:"enabled"}),r.a.createElement(N.a,{source:"permissions",label:"\u6743\u9650"},r.a.createElement(f.a,null,r.a.createElement(b.a,{source:"label",label:"\u8d44\u6e90\u540d\u79f0"}),r.a.createElement(C.a,{source:"r",label:"\u8bfb\u53d6"}),r.a.createElement(C.a,{source:"e",label:"\u66f4\u6539"})))))}}),r.a.createElement(o.a,{name:"roles",icon:Be.a,list:function(e){return r.a.createElement(d.a,Object.assign({},e,{actions:r.a.createElement(W,null),bulkActions:!1}),r.a.createElement(f.a,null,r.a.createElement(b.a,{source:"name"}),r.a.createElement(g.a,{label:"\u6388\u6743"})))},create:function(e){return r.a.createElement(w.a,e,r.a.createElement(v.a,{redirect:"show"},r.a.createElement(m.a,{source:"name",validate:[Object(O.g)()]})))},edit:function(e){return r.a.createElement(x.a,e,r.a.createElement(v.a,null,r.a.createElement(m.a,{source:"name",validate:[Object(O.g)()]})))},show:function(e){return r.a.createElement(S.a,e,r.a.createElement(X.a,null,r.a.createElement(G.a,{label:"\u89d2\u8272\u6388\u6743"},r.a.createElement(b.a,{source:"name",validate:[Object(O.g)()]}),r.a.createElement(N.a,{source:"permissions"},r.a.createElement(f.a,null,r.a.createElement(b.a,{source:"name",label:"\u8d44\u6e90\u540d\u79f0"}),r.a.createElement(b.a,{source:"label",label:"\u6807\u7b7e"}),r.a.createElement(me,{source:"r",label:"\u8bfb\u53d6",roleId:e.id}),r.a.createElement(me,{source:"u",label:"\u4fee\u6539",roleId:e.id}),r.a.createElement(me,{source:"c",label:"\u65b0\u5efa",roleId:e.id}),r.a.createElement(me,{source:"d",label:"\u5220\u9664",roleId:e.id}),r.a.createElement(y.a,{label:"\u7f16\u8f91\u6743\u9650",basePath:"/_permission"})))),r.a.createElement(G.a,{label:"\u8be5\u89d2\u8272\u4e0b\u7684\u7528\u6237"},r.a.createElement(N.a,{source:"users"},r.a.createElement(f.a,null,r.a.createElement(b.a,{source:"username",label:"\u7528\u6237\u540d"}),r.a.createElement(b.a,{source:"enabled",label:"\u662f\u5426\u542f\u7528"}),r.a.createElement(b.a,{source:"lastPasswordResetDate",label:"\u5bc6\u7801\u6700\u540e\u91cd\u7f6e\u65f6\u95f4"}))))))}}),r.a.createElement(o.a,{name:"_datasource",icon:Ue.a,list:function(e){return r.a.createElement(d.a,Object.assign({},e,{actions:r.a.createElement(W,null),bulkActions:!1}),r.a.createElement(f.a,null,r.a.createElement(b.a,{source:"name"}),r.a.createElement(b.a,{source:"url"}),r.a.createElement(de.a,{source:"dbType",choices:[{id:"mysql",name:"MySql"},{id:"elasticsearch",name:"Elasticsearch"},{id:"mongo",name:"Mongodb"}]}),r.a.createElement(ye,null),r.a.createElement(y.a,null)))},create:function(e){return r.a.createElement(w.a,e,r.a.createElement(v.a,{redirect:"list"},r.a.createElement(m.a,{source:"name",validate:[Object(O.g)()]}),r.a.createElement(fe.a,{source:"dbType",validate:[Object(O.g)()],choices:[{id:"mysql",name:"MySql"},{id:"elasticsearch",name:"Elasticsearch"},{id:"mongo",name:"Mongodb"}]}),r.a.createElement(m.a,{source:"url",validate:[Object(O.g)()]}),r.a.createElement(m.a,{source:"username",validate:[Object(O.g)()]}),r.a.createElement(m.a,{source:"password",validate:[Object(O.g)()]})))},edit:function(e){return r.a.createElement(x.a,e,r.a.createElement(v.a,null,r.a.createElement(fe.a,{source:"dbType",validate:[Object(O.g)()],choices:[{id:"mysql",name:"MySql"},{id:"elasticsearch",name:"Elasticsearch"},{id:"mongo",name:"Mongodb"}]}),r.a.createElement(m.a,{source:"name",validate:[Object(O.g)()]}),r.a.createElement(m.a,{source:"url",validate:[Object(O.g)()]}),r.a.createElement(m.a,{source:"username",validate:[Object(O.g)()]}),r.a.createElement(m.a,{source:"password",validate:[Object(O.g)()]})))}}),r.a.createElement(o.a,{name:"_entity",icon:Ke.a,list:function(e){return r.a.createElement(d.a,Object.assign({},e,{actions:r.a.createElement(W,null),bulkActions:!1,filters:r.a.createElement(ke,null)}),r.a.createElement(f.a,null,r.a.createElement(b.a,{source:"name"}),r.a.createElement(b.a,{source:"label"}),r.a.createElement(y.a,null)))},edit:function(e){return r.a.createElement(x.a,e,r.a.createElement(ve.a,null,r.a.createElement(je.a,{label:"\u5bf9\u8c61\u4fe1\u606f"},r.a.createElement(T.a,{source:"name"}),r.a.createElement(m.a,{source:"label",resettable:!0})),r.a.createElement(je.a,{label:"\u5b57\u6bb5",path:"fields"},r.a.createElement(_e.a,{addLabel:!1,reference:"_field",target:"eid"},r.a.createElement(f.a,null,r.a.createElement(b.a,{label:"\u5b57\u6bb5\u540d\u79f0",source:"name"}),r.a.createElement(b.a,{label:"\u5b57\u6bb5\u6807\u7b7e",source:"label"}),r.a.createElement(C.a,{label:"\u662f\u5426\u4e3b\u952e",source:"partOfPrimaryKey"}),r.a.createElement(y.a,null))))))}}),r.a.createElement(o.a,{name:"_field",icon:Ke.a,edit:function(e){return r.a.createElement(x.a,e,r.a.createElement(ve.a,{redirect:Pe},r.a.createElement(je.a,{label:"\u5b57\u6bb5"},r.a.createElement(T.a,{source:"name"}),r.a.createElement(fe.a,{source:"component",choices:[{id:"Text",name:"\u6587\u672c"},{id:"Number",name:"\u6570\u5b57"},{id:"Select",name:"\u9009\u62e9"},{id:"Reference",name:"\u5f15\u7528"},{id:"Date",name:"\u65e5\u671f"}]}),r.a.createElement(m.a,{source:"label",validate:[Object(O.g)()]}),r.a.createElement(P.a,null,function(e){var a=e.formData;return Object(i.a)(e,["formData"]),"Reference"===a.component&&r.a.createElement(Oe.a,{source:"reference",label:"\u5f15\u7528\u5bf9\u8c61",reference:"_entity"},r.a.createElement(we.a,{optionText:"name"}))}),r.a.createElement(P.a,null,function(e){var a=e.formData;return Object(i.a)(e,["formData"]),"Reference"===a.component&&a.reference&&r.a.createElement(Oe.a,{source:"referenceOptionText",label:"\u663e\u793a\u7684\u5b57\u6bb5",reference:"_field",filter:{eid:a.reference}},r.a.createElement(we.a,{optionValue:"name",optionText:"label"}))}),r.a.createElement(P.a,null,function(e){var a=e.formData;return Object(i.a)(e,["formData"]),"Select"===a.component&&r.a.createElement(xe.a,{source:"choicesStr",label:"\u9009\u9879\uff0cvalue|name"})}),r.a.createElement(k.a,{source:"mainField"}),r.a.createElement(k.a,{source:"partOfPrimaryKey"}),r.a.createElement(fe.a,{source:"sensitiveType",choices:[{id:"nonsensitive",name:"\u975e\u654f\u611f\u6570\u636e"},{id:"sensitive",name:"\u5176\u4ed6\u654f\u611f\u6570\u636e"},{id:"mobile",name:"\u624b\u673a"},{id:"card",name:"\u94f6\u884c\u5361\u53f7"},{id:"id",name:"\u8eab\u4efd\u8bc1\u53f7"}]})),r.a.createElement(je.a,{label:"\u5217\u8868"},r.a.createElement(k.a,{source:"showInList"}),r.a.createElement(k.a,{source:"sortable"}),r.a.createElement(k.a,{source:"showInShow"})),r.a.createElement(je.a,{label:"\u7b5b\u9009"},r.a.createElement(k.a,{source:"showInFilter"}),r.a.createElement(k.a,{source:"alwaysOn"}),r.a.createElement(P.a,null,function(e){var a=e.formData;return Object(i.a)(e,["formData"]),["Reference","Select"].includes(a.component)&&r.a.createElement(k.a,{source:"multiFilter",label:"\u591a\u9009\u8fc7\u6ee4"})})),r.a.createElement(je.a,{label:"\u7f16\u8f91"},r.a.createElement(k.a,{source:"showInEdit"}),r.a.createElement(k.a,{source:"showInCreate"}),r.a.createElement(Te.a,{source:"maxLength"}),r.a.createElement(k.a,{source:"required"}),r.a.createElement(m.a,{source:"defaultValue"}),r.a.createElement(T.a,{source:"dbColumnType"}))))}}),r.a.createElement(o.a,{name:"_permission",edit:function(e){var a=e.id,t=a.substr(a.lastIndexOf("_")+1);return r.a.createElement(x.a,e,r.a.createElement(ve.a,{redirect:Ce},r.a.createElement(je.a,{label:"\u89d2\u8272\u6388\u6743"},r.a.createElement(T.a,{source:"name"}),r.a.createElement(m.a,{source:"label"}),r.a.createElement(k.a,{source:"r"}),r.a.createElement(k.a,{source:"c"}),r.a.createElement(k.a,{source:"u"}),r.a.createElement(k.a,{source:"d"})),r.a.createElement(je.a,{label:"\u6570\u636e\u8fc7\u6ee4"},r.a.createElement(Se.a,{source:"filters",label:"\u8fc7\u6ee4\u5668"},r.a.createElement(Ie.a,null,r.a.createElement(Oe.a,{source:"field",label:"\u5b57\u6bb5",reference:"_field",filter:{eid:t}},r.a.createElement(we.a,{optionValue:"name",optionText:"label"})),r.a.createElement(fe.a,{source:"operator",label:"\u64cd\u4f5c\u7b26",choices:[{id:"eq",name:"\u7b49\u4e8e"},{id:"gte",name:"\u5927\u4e8e\u7b49\u4e8e"},{id:"gt",name:"\u5927\u4e8e"},{id:"lte",name:"\u5c0f\u4e8e\u7b49\u4e8e"},{id:"lt",name:"\u5c0f\u4e8e"},{id:"like",name:"\u6a21\u7cca\u5339\u914d"},{id:"in",name:"\u5b58\u5728\u4e8e(,\u5206\u9694)"}]}),r.a.createElement(m.a,{source:"value",label:"\u8fc7\u6ee4\u503c"}))))))}})],nt=function(){return r.a.createElement(s.a,{loginPage:wa,authProvider:le,dataProvider:Ve(ae,re),locale:"en",i18nProvider:et},at)};Boolean("localhost"===window.location.hostname||"[::1]"===window.location.hostname||window.location.hostname.match(/^127(?:\.(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)){3}$/));c.a.render(r.a.createElement(nt,null),document.getElementById("root")),"serviceWorker"in navigator&&navigator.serviceWorker.ready.then(function(e){e.unregister()})}},[[513,2,1]]]);
//# sourceMappingURL=main.66efbd61.chunk.js.map