webpackJsonp([42],{"0YDb":function(t,e){},b7JW:function(t,e,o){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var a=o("Dd8w"),n=o.n(a),i=o("0N3M");var l={data:function(){return{loginInfo:{loginName:"",captcha:"",pwd:""},key:null,containerMobileStyle:"",formMobileStyle:"",imgValid:null}},created:function(){this.refreshImg()},mounted:function(){"phone"===this.$store.state.user.useragent&&(window.onresize=function(){"input"!=document.activeElement.tagName&&"textarea"!=document.activeElement.tagName||setTimeout(function(){var t=document.activeElement.getBoundingClientRect().top;window.scrollTo(0,t)},0)},this.containerMobileStyle="align-items: flex-start",this.formMobileStyle="padding: 10px;margin-top: 10px;")},methods:{onSubmit:function(){var t,e=this;this.loginInfo.key=this.key,(t=n()({},this.loginInfo,{captcha:this.loginInfo.captcha&&this.loginInfo.captcha.toLocaleLowerCase()}),Object(i.a)({url:"/repairers/login",method:"post",data:t})).then(function(t){0==t.data.ret?(localStorage.setItem("token",t.data.datas.token),localStorage.setItem("username",e.loginInfo.loginName),localStorage.setItem("isRepairer",t.data.datas.isRepairer),localStorage.setItem("userId",t.data.datas.userId),e.$router.push({path:"/repair"})):(e.$message({message:t.data.msg,type:"error"}),e.refreshImg(),e.loginInfo.captcha=null)})},refreshImg:function(){console.log("refresh"),this.key=Math.random(),this.imgValid="/api/access/captcha?key="+this.key}}},s={render:function(){var t=this,e=t.$createElement,o=t._self._c||e;return o("el-form",{style:t.formMobileStyle,attrs:{model:t.loginInfo,"label-width":"0"}},[o("el-form-item",[o("el-row",[o("el-col",{attrs:{span:24}},[o("el-input",{attrs:{placeholder:"账号"},model:{value:t.loginInfo.loginName,callback:function(e){t.$set(t.loginInfo,"loginName",e)},expression:"loginInfo.loginName"}},[o("i",{staticClass:"el-input__icon icon-yonghu1 iconfont",attrs:{slot:"prefix"},slot:"prefix"})])],1)],1)],1),t._v(" "),o("el-form-item",[o("el-row",[o("el-col",{attrs:{span:24}},[o("el-input",{attrs:{placeholder:"密码",type:"password"},model:{value:t.loginInfo.pwd,callback:function(e){t.$set(t.loginInfo,"pwd",e)},expression:"loginInfo.pwd"}},[o("i",{staticClass:"el-input__icon icon-mima1 iconfont",attrs:{slot:"prefix"},slot:"prefix"})])],1)],1)],1),t._v(" "),o("el-form-item",[o("el-row",[o("el-col",{attrs:{span:12}},[o("el-input",{attrs:{placeholder:"验证码"},model:{value:t.loginInfo.captcha,callback:function(e){t.$set(t.loginInfo,"captcha",e)},expression:"loginInfo.captcha"}},[o("i",{staticClass:"el-input__icon icon-ad80-copy iconfont",attrs:{slot:"prefix"},slot:"prefix"})])],1),t._v(" "),o("el-col",{staticStyle:{"text-align":"right"},attrs:{span:10}},[o("img",{staticStyle:{cursor:"pointer"},attrs:{src:t.imgValid,alt:"",width:"112px"},on:{click:t.refreshImg}})]),t._v(" "),o("el-col",{staticStyle:{"text-align":"center",color:"#aaa"},attrs:{span:2}},[o("i",{staticClass:"el-icon-refresh",staticStyle:{cursor:"pointer"},on:{click:t.refreshImg}})])],1)],1),t._v(" "),o("el-form-item",[o("el-row",[o("el-col",{attrs:{span:12}},[o("el-button",{staticStyle:{width:"100%"},attrs:{type:"primary"},on:{click:t.onSubmit}},[t._v("登录")])],1)],1)],1)],1)},staticRenderFns:[]};var r=o("VU/8")(l,s,!1,function(t){o("0YDb")},"data-v-39d13eb8",null);e.default=r.exports}});