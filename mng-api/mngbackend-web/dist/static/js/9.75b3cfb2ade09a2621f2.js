webpackJsonp([9,17],{Kee9:function(e,t){},WZwW:function(e,t,n){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var a=n("ZqHE"),i=n("iv+O"),o=n("u1uc"),s=n("TuRa"),c=n("5JCu"),r=n("TiU2"),u=n("K+1K"),d=n("svd+"),l=n("0O/G"),f=n("xiQU"),m={props:{item:{type:Object},currentTabComponent:{type:String}},data:function(){return{set_func:a.a}},components:{ModelSet:i.default,ChangeWater:o.default,ConfigReset:s.default,ManualClean:c.default,ManualWash:r.default,SysSet:u.default,TimesetModel:d.default,UserSet:l.default,WaterReset:f.default}},p={render:function(){var e=this.$createElement,t=this._self._c||e;return t("div",{staticClass:"setting"},[t("h1",{staticStyle:{"text-align":"center","font-size":"18px","font-weight":"700","line-height":"30px"}},[this._v(this._s(this.item.name))]),this._v(" "),t(this.currentTabComponent,{tag:"component",attrs:{id:this.$route.query.id}})],1)},staticRenderFns:[]};var v=n("VU/8")(m,p,!1,function(e){n("fvW0")},"data-v-38cf909f",null);t.default=v.exports},ZqHE:function(e,t,n){"use strict";n.d(t,"a",function(){return a});var a=[{name:"机型设置",component:"model-set",id:"mode",type:1,btnText:"切换到BJ",func:n("MEs8").h,desc:"模式设置"},{name:"恢复默认设置",component:"config-reset",id:"mode",type:2,btnText:"切换到BJ",func:"reset",desc:"将所有用户参数恢复到默认设置"},{name:"定时启动和取消",component:"timeset-model",id:"mode",type:1,btnText:"切换到BJ",func:"aaaaaa",desc:"模式设置"},{name:"系统参数设置",component:"sys-set",id:"mode",type:1,btnText:"切换到BJ",func:"aaaaaa",desc:"设置系统参数"},{name:"用户参数设置",component:"user-set",id:"mode",type:1,btnText:"切换到BJ",func:"aaaaaa",desc:"设置用户参数"}]},fvW0:function(e,t){},jkSA:function(e,t,n){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var a=n("WZwW"),i=n("ZqHE"),o={data:function(){return{tab:"机型设置",activeName:"机型设置",tabPosition:"left"}},created:function(){this.$store.commit("setBreadcrumbList",[{path:"/content/dev",title:"设备管理"},{path:"/content/set-panel",title:"设备设置"}])},mounted:function(){"phone"===this.$store.state.user.useragent&&(this.tabPosition="top")},methods:{handleClick:function(e,t){console.log(e.name),this.tab=e.name}},computed:{set_func:function(){var e=[];return e=1!=localStorage.getItem("userId")?i.a.filter(function(e){return"系统参数设置"!==e.name}):i.a,0===this.$store.state.app.devModel?e.filter(function(e){return"手动消毒"!==e.name}):e}},components:{Setting:a.default}},s={render:function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("keep-alive",[n("div",{staticClass:"set-panel top-border"},[n("p",{staticStyle:{"margin-bottom":"10px"}},[e._v("设备编号："+e._s(e.$route.query.device_id))]),e._v(" "),n("el-tabs",{attrs:{"tab-position":e.tabPosition,stretch:!0},on:{"tab-click":e.handleClick},model:{value:e.activeName,callback:function(t){e.activeName=t},expression:"activeName"}},e._l(e.set_func,function(t){return n("el-tab-pane",{key:t.name,attrs:{label:t.name,name:t.name}},[e.tab===t.name?n("Setting",{attrs:{item:{},currentTabComponent:t.component}}):e._e()],1)}))],1)])},staticRenderFns:[]};var c=n("VU/8")(o,s,!1,function(e){n("Kee9")},"data-v-27d2aca9",null);t.default=c.exports}});