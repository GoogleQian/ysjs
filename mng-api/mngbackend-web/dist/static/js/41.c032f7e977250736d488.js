webpackJsonp([41],{"7QcQ":function(e,a,t){"use strict";Object.defineProperty(a,"__esModule",{value:!0});var l=t("Dd8w"),i=t.n(l),o=t("VjnB"),r=t("axK0"),s=t("0N3M");var n=[{label:"用户名",value:"userName",type:"input",placeholder:"用户名"},{label:"操作信息",value:"operateInfo",type:"input",placeholder:"操作信息"},{label:"请求url",value:"reqUrl",type:"input",placeholder:"请求url"},{label:"请求方式",value:"reqType",type:"input",placeholder:"请求方式"},{label:"请求参数",value:"reqParam",type:"input",placeholder:"请求参数"},{label:"状态",value:"status",type:"select",options:[{label:"成功",value:1},{label:"失败",value:0}],placeholder:"请选择"},{label:"操作IP",value:"ip",type:"input",placeholder:"操作IP"},{label:"用户代理",value:"userAgent",type:"input",placeholder:"用户代理"},{label:"开始时间",value:"startTime",type:"datetime"},{label:"结束时间",value:"endTime",type:"datetime"}],d=[{label:"用户名",value:"userName"},{label:"操作信息",value:"operateInfo"},{label:"请求url",value:"reqUrl"},{label:"请求方式",value:"reqType"},{label:"请求参数",value:"reqParam"},{label:"请求时间(ms)",value:"reqTime"},{label:"状态",value:"status",formatter:function(e){return 1===e.status?"成功":"失败"}},{label:"操作IP",value:"ip"},{label:"用户代理",value:"userAgent"},{label:"创建时间",value:"createTime"}],u={data:function(){return{tableData:[],page:{total:0,currentPage:1},tableColumns:d,searchItems:n,tableLoading:!1,btnLoading:!1,searchForm:{},dialogTitle:"新增",dialogVisible:!1,labelWidth:"100px",dialogData:{schemalist:[]},allDev:[]}},created:function(){this.getList()},methods:{getList:function(){var e=this;this.tableLoading=!0;var a,t=i()({page:this.page.currentPage},this.searchForm);(a=t,Object(s.a)({url:"/operateLogs",method:"post",data:a})).then(function(a){e.tableLoading=!1,e.tableData=a.data.datas.list,e.page.total=a.data.datas.itemCounts})},search:function(e){console.log(e),this.page.currentPage=1,this.searchForm=e,this.getList()},func:function(e){var a=e.opera,t=e.row;switch(a){case"修改":this.edit(t);break;case"新增":this.add();break;case"删除":this.del(t)}},add:function(){this.dialogTitle="新增",this.dialogData={},this.dialogVisible=!0},edit:function(e){this.dialogTitle="编辑",this.dialogData=i()({},e),this.dialogVisible=!0},del:function(e){var a=this;this.$confirm("此操作将永久删除所选数据, 是否继续?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then(function(){var t,l;(t={id:e.id},l=t.id,Object(s.a)({url:"/operateLogs/"+l,method:"delete"})).then(function(e){console.log("shanchu "),a.$message({message:0===e.data.ret?"删除成功":e.data.msg,type:0===e.data.ret?"success":"error"}),a.getList()})}).catch(function(){a.$message({type:"info",message:"已取消删除"})})},handleCurrentChange:function(e){this.page.currentPage=e,this.getList()},submitForm:function(e){var a=this;this.$refs[e].validate(function(e){if(!e)return!1;var t,l,o,r=a.dialogData;"编辑"===a.dialogTitle?(t={id:a.dialogData.id,data:i()({},a.dialogData)},l=t.id,o=t.data,Object(s.a)({url:"/operateLogs/"+l,method:"put",data:o})).then(function(e){0==e.data.ret?(a.$message({message:"修改成功",type:"success"}),a.dialogVisible=!1,a.getList()):a.$message({message:e.data.msg,type:"error"})}):"新增"===a.dialogTitle&&function(e){return Object(s.a)({url:"/operateLogs/",method:"post",data:e})}(r).then(function(e){0==e.data.ret?(a.$message({message:"新增成功",type:"success"}),a.dialogVisible=!1,a.getList()):a.$message({message:e.data.msg,type:"error"})})})}},components:{ZTable:o.a,ZSearch:r.a}},c={render:function(){var e=this,a=e.$createElement,t=e._self._c||a;return t("div",{staticClass:"box"},[t("z-search",{attrs:{searchItems:e.searchItems},on:{search:e.search}}),e._v(" "),t("z-table",{directives:[{name:"loading",rawName:"v-loading",value:e.tableLoading,expression:"tableLoading"}],attrs:{tableData:e.tableData,tableColumns:e.tableColumns,page:e.page,funcs:e.funcs},on:{func:e.func,handleCurrentChange:e.handleCurrentChange}}),e._v(" "),t("el-dialog",{attrs:{title:e.dialogTitle,visible:e.dialogVisible},on:{"update:visible":function(a){e.dialogVisible=a}}},[t("el-form",{ref:"ruleForm",staticClass:"demo-ruleForm",attrs:{model:e.dialogData,"label-width":"100px"}},[t("el-form-item",{attrs:{label:"用户名:",prop:"userName"}},[t("el-input",{model:{value:e.dialogData.userName,callback:function(a){e.$set(e.dialogData,"userName",a)},expression:"dialogData.userName"}})],1),e._v(" "),t("el-form-item",{attrs:{label:"操作信息:",prop:"operateInfo"}},[t("el-input",{model:{value:e.dialogData.operateInfo,callback:function(a){e.$set(e.dialogData,"operateInfo",a)},expression:"dialogData.operateInfo"}})],1),e._v(" "),t("el-form-item",{attrs:{label:"请求url:",prop:"reqUrl"}},[t("el-input",{model:{value:e.dialogData.reqUrl,callback:function(a){e.$set(e.dialogData,"reqUrl",a)},expression:"dialogData.reqUrl"}})],1),e._v(" "),t("el-form-item",{attrs:{label:"请求方式:",prop:"reqType"}},[t("el-input",{model:{value:e.dialogData.reqType,callback:function(a){e.$set(e.dialogData,"reqType",a)},expression:"dialogData.reqType"}})],1),e._v(" "),t("el-form-item",{attrs:{label:"请求参数:",prop:"reqParam"}},[t("el-input",{model:{value:e.dialogData.reqParam,callback:function(a){e.$set(e.dialogData,"reqParam",a)},expression:"dialogData.reqParam"}})],1),e._v(" "),t("el-form-item",{attrs:{label:"请求时间:",prop:"reqTime"}},[t("el-input",{model:{value:e.dialogData.reqTime,callback:function(a){e.$set(e.dialogData,"reqTime",a)},expression:"dialogData.reqTime"}})],1),e._v(" "),t("el-form-item",{attrs:{label:"状态（0失败1成功）:",prop:"status"}},[t("el-input",{model:{value:e.dialogData.status,callback:function(a){e.$set(e.dialogData,"status",a)},expression:"dialogData.status"}})],1),e._v(" "),t("el-form-item",{attrs:{label:"操作IP:",prop:"ip"}},[t("el-input",{model:{value:e.dialogData.ip,callback:function(a){e.$set(e.dialogData,"ip",a)},expression:"dialogData.ip"}})],1),e._v(" "),t("el-form-item",{attrs:{label:"用户代理:",prop:"userAgent"}},[t("el-input",{model:{value:e.dialogData.userAgent,callback:function(a){e.$set(e.dialogData,"userAgent",a)},expression:"dialogData.userAgent"}})],1),e._v(" "),t("el-form-item",{attrs:{label:"开始时间"}},[t("el-date-picker",{attrs:{type:"datetime",placeholder:"选择时间","value-format":"yyyy-MM-dd HH:mm:ss"},model:{value:e.dialogData.startTime,callback:function(a){e.$set(e.dialogData,"startTime",a)},expression:"dialogData.startTime"}})],1),e._v(" "),t("el-form-item",{attrs:{label:"结束时间"}},[t("el-date-picker",{attrs:{type:"datetime",placeholder:"选择时间","value-format":"yyyy-MM-dd HH:mm:ss"},model:{value:e.dialogData.endTime,callback:function(a){e.$set(e.dialogData,"endTime",a)},expression:"dialogData.endTime"}})],1)],1),e._v(" "),t("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[t("el-button",{on:{click:function(a){e.dialogVisible=!1}}},[e._v("取 消")]),e._v(" "),t("el-button",{attrs:{type:"primary",loading:e.btnLoading},on:{click:function(a){e.submitForm("ruleForm")}}},[e._v("确 定")])],1)],1)],1)},staticRenderFns:[]};var p=t("VU/8")(u,c,!1,function(e){t("n68+")},"data-v-3a2eb379",null);a.default=p.exports},"n68+":function(e,a){}});