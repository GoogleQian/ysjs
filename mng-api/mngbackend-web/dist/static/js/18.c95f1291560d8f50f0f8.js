webpackJsonp([18],{lCCq:function(t,a,e){"use strict";a.a=function(t){return Object(n.a)({url:"/waterTypes/",method:"get",params:t})};var n=e("0N3M")},rJ4S:function(t,a,e){"use strict";Object.defineProperty(a,"__esModule",{value:!0});var n=e("Dd8w"),r=e.n(n),s=e("lCCq"),i=(e("PJh5"),{name:"dev",data:function(){return{tableData:[],page:{total:0,currentPage:1},searchForm:{}}},created:function(){this.getList()},methods:{getList:function(){var t=this;this.tableLoading=!0;var a=r()({page:this.page.currentPage},this.searchForm);Object(s.a)(a).then(function(a){t.tableLoading=!1,t.tableData=a.data.datas.list,t.page.total=a.data.datas.itemCounts})}}}),l={render:function(){var t=this.$createElement,a=this._self._c||t;return a("div",{staticClass:"sale-plan top-border"},[a("el-table",{staticStyle:{width:"100%"},attrs:{data:this.tableData}},[a("el-table-column",{attrs:{prop:"type",label:"售水类型"}})],1)],1)},staticRenderFns:[]};var c=e("VU/8")(i,l,!1,function(t){e("xHdh")},"data-v-439f3f5f",null);a.default=c.exports},xHdh:function(t,a){}});