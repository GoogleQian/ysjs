webpackJsonp([24],{WodU:function(e,t){},pU51:function(e,t,n){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var l={props:{tableData:{type:Array,default:function(){return[{}]}},total:{default:0,type:Number},selectedIds:{type:Array,default:function(){return[]}}},data:function(){return{currentPage:1}},updated:function(){console.log("updated"),this.toggleSelection()},computed:{},watch:{tableData:function(e,t){this.toggleSelection()},selectedIds:function(e,t){this.toggleSelection()}},methods:{toggleSelection:function(){var e=this,t=[];(t=this.tableData.filter(function(t){return e.selectedIds.includes(t.id)}))?t.forEach(function(t){e.$refs.multipleTable.toggleRowSelection(t)}):this.$refs.multipleTable.clearSelection()},handleSelectionChange:function(e){this.multipleSelection=e;var t=this.multipleSelection.map(function(e){return e.id});this.$emit("selectionChange",t)},handleCurrentChange:function(e){this.currentPage=e,this.$emit("currentChange",e)}},beforeDestroy:function(){console.log("destory"),this.$refs.multipleTable.clearSelection()}},a={render:function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{staticClass:"devList"},[n("el-table",{ref:"multipleTable",staticStyle:{width:"100%"},attrs:{data:e.tableData,"tooltip-effect":"dark","max-height":"300",border:""},on:{"selection-change":e.handleSelectionChange}},[n("el-table-column",{attrs:{type:"selection",width:"55"}}),e._v(" "),n("el-table-column",{attrs:{prop:"id",label:"Id",width:"120"}}),e._v(" "),n("el-table-column",{attrs:{prop:"device_id",label:"设备编号","show-overflow-tooltip":""}})],1),e._v(" "),n("el-pagination",{staticStyle:{"margin-top":"10px"},attrs:{"current-page":e.currentPage,"page-size":50,layout:"prev, pager, next, jumper",total:e.total},on:{"current-change":e.handleCurrentChange,"update:currentPage":function(t){e.currentPage=t}}})],1)},staticRenderFns:[]};var i=n("VU/8")(l,a,!1,function(e){n("WodU")},null,null);t.default=i.exports}});