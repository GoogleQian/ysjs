webpackJsonp([23],{"8gLK":function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var i=a("Dd8w"),n=a.n(i),l=a("VjnB"),o=a("axK0"),r=[{label:"角色名称",value:"roleName",type:"input",placeholder:"角色名称"}],s=[{label:"角色名",value:"roleName"},{label:"备注",value:"remark"},{label:"创建时间",value:"createTime"}],d=a("oRRU"),c=a("i7zI"),u={data:function(){return{tableData:[],page:{total:0,currentPage:1},tableColumns:s,searchItems:r,tableLoading:!1,searchForm:{},dialogTitle:"新增",dialogVisible:!1,labelWidth:"100px",dialogData:{schemalist:[]},allMenu:[],treeLoading:!1,defaultProps:{children:"children",label:"name"},btnLoading:!1}},created:function(){this.getList()},methods:{getList:function(){var e=this;this.tableLoading=!0;var t=n()({page:this.page.currentPage},this.searchForm);Object(d.f)(t).then(function(t){e.tableLoading=!1,e.tableData=t.data.page.list})},getAllMenu:function(e){var t=this;this.treeLoading=!0,Object(d.d)().then(function(e){t.treeLoading=!1,t.allMenu=Object(c.a)(e.data,"menuId")})},search:function(e){this.page.currentPage=1,this.searchForm=e,this.getList()},func:function(e){var t=e.opera,a=e.row;switch(t){case"修改":this.edit(a);break;case"新增":this.add();break;case"删除":this.del(a)}},add:function(){this.dialogTitle="新增",this.dialogData={},this.dialogVisible=!0,this.getAllMenu()},edit:function(e){var t=this;this.dialogTitle="编辑",this.dialogData=n()({},e),this.dialogVisible=!0,this.getAllMenu(),Object(d.e)({roleId:e.roleId}).then(function(e){0===e.data.code&&(t.dialogData=e.data.role)})},del:function(e){var t=this;this.$confirm("此操作将永久删除该条数据, 是否继续?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then(function(){Object(d.b)([e.roleId]).then(function(e){t.$message({message:0===e.data.code?"删除成功":e.data.msg,type:0===e.data.code?"success":"error"}),t.getList()})}).catch(function(){t.$message({type:"info",message:"已取消删除"})})},handleCurrentChange:function(e){this.page.currentPage=e,this.getList()},submitForm:function(e){var t=this;this.$refs[e].validate(function(e){if(!e)return!1;t.dialogData;t.btnLoading=!0,"编辑"===t.dialogTitle?Object(d.c)(n()({},t.dialogData,{menuIdList:t.$refs.roleTree.getCheckedKeys()})).then(function(e){t.btnLoading=!1,0==e.data.code?(t.$message({message:"修改成功",type:"success"}),t.dialogVisible=!1,t.getList()):t.$message({message:e.data.msg,type:"error"})}):"新增"===t.dialogTitle&&Object(d.a)(n()({},t.dialogData,{menuIdList:t.$refs.roleTree.getCheckedKeys()})).then(function(e){0==e.data.code?(t.$message({message:"新增成功",type:"success"}),t.dialogVisible=!1,t.getList()):t.$message({message:e.data.msg,type:"error"})})})}},computed:{funcs:function(){return this.$store.state.user.funcs}},components:{ZTable:l.a,ZSearch:o.a}},g={render:function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticClass:"role top-border"},[a("z-search",{attrs:{searchItems:e.searchItems},on:{search:e.search}}),e._v(" "),a("z-table",{directives:[{name:"loading",rawName:"v-loading",value:e.tableLoading,expression:"tableLoading"}],attrs:{tableData:e.tableData,tableColumns:e.tableColumns,page:e.page,funcs:e.funcs},on:{func:e.func,handleCurrentChange:e.handleCurrentChange}}),e._v(" "),a("el-dialog",{attrs:{title:e.dialogTitle,visible:e.dialogVisible},on:{"update:visible":function(t){e.dialogVisible=t}}},[a("el-form",{ref:"ruleForm",staticClass:"demo-ruleForm",attrs:{model:e.dialogData,"label-width":"100px"}},[a("el-form-item",{attrs:{label:"角色名称：",prop:"roleName"}},[a("el-input",{model:{value:e.dialogData.roleName,callback:function(t){e.$set(e.dialogData,"roleName",t)},expression:"dialogData.roleName"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"权限设置：",prop:"menuIdList"}},[a("el-tree",{directives:[{name:"loading",rawName:"v-loading",value:e.treeLoading,expression:"treeLoading"}],ref:"roleTree",attrs:{data:e.allMenu,"default-checked-keys":e.dialogData.menuIdList||[],"show-checkbox":"","node-key":"menuId","default-expand-all":"",props:e.defaultProps,"check-strictly":""}})],1),e._v(" "),a("el-form-item",{attrs:{label:"备注：",prop:"remark"}},[a("el-input",{attrs:{type:"textarea"},model:{value:e.dialogData.remark,callback:function(t){e.$set(e.dialogData,"remark",t)},expression:"dialogData.remark"}})],1)],1),e._v(" "),a("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[a("el-button",{on:{click:function(t){e.dialogVisible=!1}}},[e._v("取 消")]),e._v(" "),a("el-button",{attrs:{type:"primary",loading:e.btnLoading},on:{click:function(t){e.submitForm("ruleForm")}}},[e._v("确 定")])],1)],1)],1)},staticRenderFns:[]};var h=a("VU/8")(u,g,!1,function(e){a("uIVR")},"data-v-0865ccd9",null);t.default=h.exports},i7zI:function(e,t,a){"use strict";a.d(t,"a",function(){return i});var i=function(e){var t=arguments.length>1&&void 0!==arguments[1]?arguments[1]:"deptId";return e.forEach(function(a){var i=a.parentId;0===i||e.forEach(function(e){if(e[t]===i){var n=e.children;n||(n=[]),n.push(a),e.children=n}})}),e=e.filter(function(e){return 0===e.parentId})}},uIVR:function(e,t){}});