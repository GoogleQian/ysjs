<template>
  <div class="breadcrumb">
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="indexPath">首页</el-breadcrumb-item>
      <el-breadcrumb-item :to="{path: breadcrumb.path}">{{breadcrumb.meta.title}}</el-breadcrumb-item>
    </el-breadcrumb>
  </div>
</template>

<script>
export default {
  name: "breadcrumb",
  computed: {
    indexPath(){
      return localStorage.getItem('isRepairer') == '1' ? {path: '/repair'} : {path: '/content'}
    },
    breadcrumb() {
      return this.$route.matched[1];
    }
  },
  created() {}
};
</script>

<style lang="less" scoped>
@import "./breadcrumb.less";
</style>
