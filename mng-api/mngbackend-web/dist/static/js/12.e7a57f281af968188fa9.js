webpackJsonp([12,24],{"0e2F":function(e,t){},WodU:function(e,t){},pU51:function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var i={props:{tableData:{type:Array,default:function(){return[{}]}},total:{default:0,type:Number},selectedIds:{type:Array,default:function(){return[]}}},data:function(){return{currentPage:1}},updated:function(){console.log("updated"),this.toggleSelection()},computed:{},watch:{tableData:function(e,t){this.toggleSelection()},selectedIds:function(e,t){this.toggleSelection()}},methods:{toggleSelection:function(){var e=this,t=[];(t=this.tableData.filter(function(t){return e.selectedIds.includes(t.id)}))?t.forEach(function(t){e.$refs.multipleTable.toggleRowSelection(t)}):this.$refs.multipleTable.clearSelection()},handleSelectionChange:function(e){this.multipleSelection=e;var t=this.multipleSelection.map(function(e){return e.id});this.$emit("selectionChange",t)},handleCurrentChange:function(e){this.currentPage=e,this.$emit("currentChange",e)}},beforeDestroy:function(){console.log("destory"),this.$refs.multipleTable.clearSelection()}},l={render:function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticClass:"devList"},[a("el-table",{ref:"multipleTable",staticStyle:{width:"100%"},attrs:{data:e.tableData,"tooltip-effect":"dark","max-height":"300",border:""},on:{"selection-change":e.handleSelectionChange}},[a("el-table-column",{attrs:{type:"selection",width:"55"}}),e._v(" "),a("el-table-column",{attrs:{prop:"id",label:"Id",width:"120"}}),e._v(" "),a("el-table-column",{attrs:{prop:"device_id",label:"设备编号","show-overflow-tooltip":""}})],1),e._v(" "),a("el-pagination",{staticStyle:{"margin-top":"10px"},attrs:{"current-page":e.currentPage,"page-size":50,layout:"prev, pager, next, jumper",total:e.total},on:{"current-change":e.handleCurrentChange,"update:currentPage":function(t){e.currentPage=t}}})],1)},staticRenderFns:[]};var o=a("VU/8")(i,l,!1,function(e){a("WodU")},null,null);t.default=o.exports},"xy7+":function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var i=a("Dd8w"),l=a.n(i),o=a("VjnB"),n=a("0N3M");function s(e){var t=e.user_id;return Object(n.a)({url:"sys/user/info/"+t,method:"get"})}var r=[{label:"商户名",value:"mchName"},{label:"账户名",value:"username"},{label:"手机号",value:"mobile"},{label:"设备数",value:"devNum"},{label:"创建时间",value:"createTime"},{label:"合作开始日期",value:"cooperationTime"},{label:"备注",value:"remark"},{label:"公众账号ID",value:"remark",formatter:function(e){return(e.wxConfig||{}).appId}},{label:"微信商户ID",value:"remark",formatter:function(e){return(e.wxConfig||{}).mchId}}],c=a("oRRU"),d=a("pU51"),u=a("PJh5"),g=a.n(u),m={data:function(){return{headers:{token:null},searchData:{},tableData:[],tableColumns:r,page:{total:0,currentPage:1},tableLoading:!1,dialogTitle:"编辑",dialogFormVisible:!1,dialogData:{wxConfig:{}},rules:{},defaultProps:{children:"children",label:"name"},allUnbindDev:[],allRole:[],oking:!1,total:0,currentPage:1,user_id:null,selectedIds:[]}},created:function(){this.getList()},mounted:function(){this.headers.token=localStorage.getItem("token"),console.log(this.$refs)},methods:{handleAvatarSuccess:function(e,t){0===e.ret?(this.dialogData.wxConfig.cert=e.datas,this.dialogData.wxConfig=l()({},this.dialogData.wxConfig,{cert:e.datas}),this.$message({message:"上传成功",type:"success"})):this.$message({message:e.msg,type:"error"})},beforeAvatarUpload:function(e){return!0},formatterTime:function(e,t,a){return g()(a).format("YYYY-MM-DD hh:mm:ss")},getList:function(){var e,t=this;(e={limit:10,page:this.page.currentPage},Object(n.a)({url:"/sys/user/list",method:"get",params:e})).then(function(e){0===e.data.code&&(t.tableData=e.data.page.list,console.log(e.data.page),t.page.total=e.data.page.totalCount)})},getRoles:function(){var e=this;Object(c.f)().then(function(t){e.allRole=t.data.page.list})},unbindDevs:function(){var e,t=this;(e={page:this.currentPage,user_id:this.user_id},Object(n.a)({url:"/device/unbind",method:"get",params:e})).then(function(e){t.allUnbindDev=e.data.datas.list,t.total=e.data.datas.itemCounts})},func:function(e){var t=e.opera,a=e.row;switch(t){case"修改":this.edit(a);break;case"新增":this.add();break;case"删除":this.del(a);break;case"查看":this.view(a)}},currentChange:function(e){this.currentPage=e,this.unbindDevs()},handleCurrentChange:function(e){this.page.currentPage=e,this.getList()},selectionChange:function(e){this.dialogData.deviceIdList=e},ok:function(){var e,t=this;this.oking=!0,"编辑"===this.dialogTitle?(e=l()({},this.dialogData),Object(n.a)({url:"/sys/user/update",method:"post",data:e})).then(function(e){t.oking=!1,0===e.data.code?(t.getList(),t.$message({message:"修改成功",type:"success"}),t.dialogFormVisible=!1):t.$message({message:e.data.msg,type:"error"})}).catch(function(){t.oking=!1}):"新增"===this.dialogTitle&&function(e){return Object(n.a)({url:"/sys/user/save",method:"post",data:e})}(l()({},this.dialogData)).then(function(e){t.oking=!1,0===e.data.code?(t.getList(),t.$message({message:"新增成功",type:"success"}),t.dialogFormVisible=!1):t.$message({message:e.data.msg,type:"error"})}).catch(function(){t.oking=!1})},edit:function(e){var t=this;this.dialogTitle="编辑",this.currentPage=1,this.user_id=e.userId,this.unbindDevs(),this.getRoles(),s({user_id:e.userId}).then(function(e){0===e.data.code&&(t.dialogData=l()({},e.data.user),t.dialogData.wxConfig=t.dialogData.wxConfig||{},t.selectedIds=t.dialogData.deviceIdList)}),this.dialogFormVisible=!0},view:function(e){var t=this;this.dialogTitle="详情",this.currentPage=1,this.user_id=e.userId,this.unbindDevs(),this.getRoles(),s({user_id:e.userId}).then(function(e){0===e.data.code&&(t.dialogData=l()({},e.data.user),t.dialogData.wxConfig=t.dialogData.wxConfig||{},t.selectedIds=t.dialogData.deviceIdList)}),this.dialogFormVisible=!0},add:function(){this.dialogTitle="新增",this.currentPage=1,this.user_id=null,this.selectedIds=[],this.unbindDevs(),this.getRoles(),this.dialogData={wxConfig:{},roleIdList:[]},this.dialogFormVisible=!0},search:function(){console.log("submit!")},del:function(e){var t=this;this.$confirm("此操作将永久删除该条数据, 是否继续?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then(function(){var a;(a=[e.userId],Object(n.a)({url:"/sys/user/delete",method:"post",data:a})).then(function(e){0===e.data.code?(t.getList(),t.$message({message:"删除成功",type:"success"})):t.$message({message:e.data.msg,type:"error"})})}).catch(function(){t.$message({type:"info",message:"已取消删除"})})},confirmDel:function(e){var t=this;this.$confirm("此操作将永久删除该文件, 是否继续?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then(function(){t.del(e)}).catch(function(){t.$message({type:"info",message:"已取消删除"})})}},computed:{funcs:function(){return this.$store.state.user.funcs}},components:{DevList:d.default,ZTable:o.a}},p={render:function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticClass:"account top-border"},[a("el-form",{staticClass:"demo-form-inline",attrs:{inline:!0,model:e.searchData}},[a("el-form-item",{attrs:{label:"账户名："}},[a("el-input",{attrs:{placeholder:"账户名"},model:{value:e.searchData.name,callback:function(t){e.$set(e.searchData,"name",t)},expression:"searchData.name"}})],1),e._v(" "),a("el-form-item",[a("el-button",{attrs:{type:"primary"},on:{click:e.search}},[e._v("查询")])],1)],1),e._v(" "),a("z-table",{directives:[{name:"loading",rawName:"v-loading",value:e.tableLoading,expression:"tableLoading"}],attrs:{tableData:e.tableData,tableColumns:e.tableColumns,page:e.page,funcs:e.funcs},on:{func:e.func,handleCurrentChange:e.handleCurrentChange}}),e._v(" "),a("el-dialog",{attrs:{title:e.dialogTitle,visible:e.dialogFormVisible},on:{"update:visible":function(t){e.dialogFormVisible=t}}},[a("el-form",{staticClass:"demo-ruleForm",attrs:{model:e.dialogData,rules:e.rules,"label-width":"150px",disabled:"详情"===e.dialogTitle}},[a("el-form-item",{attrs:{label:"商户名：",prop:"mchName"}},[a("el-input",{model:{value:e.dialogData.mchName,callback:function(t){e.$set(e.dialogData,"mchName",t)},expression:"dialogData.mchName"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"账户名：",prop:"username"}},[a("el-input",{model:{value:e.dialogData.username,callback:function(t){e.$set(e.dialogData,"username",t)},expression:"dialogData.username"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"角色：",prop:"roleIdList"}},[a("el-select",{staticStyle:{width:"100%"},attrs:{multiple:"",placeholder:"请选择"},model:{value:e.dialogData.roleIdList,callback:function(t){e.$set(e.dialogData,"roleIdList",t)},expression:"dialogData.roleIdList"}},e._l(e.allRole,function(e){return a("el-option",{key:e.roleId,attrs:{label:e.roleName,value:e.roleId}})}))],1),e._v(" "),a("el-form-item",{attrs:{label:"密码：",prop:"password"}},[a("el-input",{attrs:{type:"password"},model:{value:e.dialogData.password,callback:function(t){e.$set(e.dialogData,"password",t)},expression:"dialogData.password"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"手机号：",prop:"mobile"}},[a("el-input",{model:{value:e.dialogData.mobile,callback:function(t){e.$set(e.dialogData,"mobile",t)},expression:"dialogData.mobile"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"合作开始日期：",prop:"cooperationTime"}},[a("el-date-picker",{attrs:{type:"date",placeholder:"选择日期","value-format":"yyyy-MM-dd"},model:{value:e.dialogData.cooperationTime,callback:function(t){e.$set(e.dialogData,"cooperationTime",t)},expression:"dialogData.cooperationTime"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"备注：",prop:"remark"}},[a("el-input",{attrs:{type:"textarea"},model:{value:e.dialogData.remark,callback:function(t){e.$set(e.dialogData,"remark",t)},expression:"dialogData.remark"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"公众账号ID：",prop:"appId"}},[a("el-input",{model:{value:e.dialogData.wxConfig.appId,callback:function(t){e.$set(e.dialogData.wxConfig,"appId",t)},expression:"dialogData.wxConfig.appId"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"公众账号身份密钥：",prop:"appSecret"}},[a("el-input",{model:{value:e.dialogData.wxConfig.appSecret,callback:function(t){e.$set(e.dialogData.wxConfig,"appSecret",t)},expression:"dialogData.wxConfig.appSecret"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"开发者密码：",prop:" secret"}},[a("el-input",{model:{value:e.dialogData.wxConfig.secret,callback:function(t){e.$set(e.dialogData.wxConfig,"secret",t)},expression:"dialogData.wxConfig.secret"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"微信商户ID：",prop:"mchId"}},[a("el-input",{model:{value:e.dialogData.wxConfig.mchId,callback:function(t){e.$set(e.dialogData.wxConfig,"mchId",t)},expression:"dialogData.wxConfig.mchId"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"证书：",prop:"cert"}},[a("el-upload",{staticClass:"avatar-uploader",attrs:{action:"/api/znss/file/cert",headers:e.headers,"show-file-list":!1,"on-success":e.handleAvatarSuccess,"before-upload":e.beforeAvatarUpload}},[a("el-button",{attrs:{type:"success"}},[e._v("上传证书")]),e._v(" "+e._s(e.dialogData.wxConfig.cert)+"\n        ")],1)],1),e._v(" "),a("el-form-item",{attrs:{label:"绑定设备："}},[a("dev-list",{attrs:{tableData:e.allUnbindDev,total:e.total,selectedIds:e.selectedIds},on:{currentChange:e.currentChange,selectionChange:e.selectionChange}})],1)],1),e._v(" "),a("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[a("el-button",{on:{click:function(t){e.dialogFormVisible=!1}}},[e._v("取 消")]),e._v(" "),a("el-button",{attrs:{type:"primary",loading:e.oking},on:{click:e.ok}},[e._v("确 定")])],1)],1)],1)},staticRenderFns:[]};var f=a("VU/8")(m,p,!1,function(e){a("0e2F")},"data-v-16474316",null);t.default=f.exports}});