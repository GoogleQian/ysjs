webpackJsonp([20],{"8KiM":function(e,t,r){"use strict";t.f=function(){return Object(s.a)({url:"/dev/configs/user2",method:"get"})},t.a=function(e){var t=e.id,r=e.params;return Object(s.a)({url:"dev/configs/user/"+t,method:"get",params:r})},t.b=function(e){var t=e.id;return Object(s.a)({url:"/dev/configs/passage/"+t,method:"get"})},t.d=function(e){var t=e.id,r=e.data;return Object(s.a)({url:"/dev/configs/passages/"+t,method:"post",data:r})},t.e=function(e){var t=e.id,r=e.data;return Object(s.a)({url:"/dev/configs/passages/"+t,method:"put",data:r})},t.c=function(e){return Object(s.a)({url:"/solution/getPrice",method:"get",params:e})};var s=r("0N3M")},ICfT:function(e,t){},sY2b:function(e,t,r){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var s=r("8KiM"),a={data:function(){return{options:[],userId:null}},created:function(){this.getuserlist(),this.userId=Number(this.$route.query.user_id)},methods:{getuserlist:function(){var e=this;Object(s.f)().then(function(t){0===t.data.ret&&(e.options=t.data.datas)})},binduser:function(){var e=this;Object(s.a)({id:this.$route.query.id,params:{userId:this.userId}}).then(function(t){0===t.data.ret&&e.$message({message:"绑定成功",type:"success"})})}}},n={render:function(){var e=this,t=e.$createElement,r=e._self._c||t;return r("div",{staticClass:"manage"},[r("p",{staticStyle:{"margin-bottom":"10px"}},[e._v("设备编号："+e._s(e.$route.query.device_id))]),e._v("\n  所属经销商："),r("el-select",{attrs:{placeholder:"请选择"},model:{value:e.userId,callback:function(t){e.userId=t},expression:"userId"}},e._l(e.options,function(e){return r("el-option",{key:e.userId,attrs:{label:e.mchName+" —— "+e.username,value:e.userId}})})),e._v(" "),r("el-button",{on:{click:e.binduser}},[e._v("确定")])],1)},staticRenderFns:[]};var u=r("VU/8")(a,n,!1,function(e){r("ICfT")},"data-v-4e9ed80c",null);t.default=u.exports}});