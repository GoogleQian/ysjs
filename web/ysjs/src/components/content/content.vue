<style scoped lang="less">
@import "./content.less";
</style>

<template>
  <div class="content">
    <Left class="content-left" />
    <div class="right">
      <Header class="header" />
      <Breadcrumb class="breadcrumb" />
      <Main class="main" />
    </div>
  </div>
</template>

<script>
import Header from "../header/header";
import Left from "../left/left";
import Main from "../main/main";
import Breadcrumb from "../breadcrumb/breadcrumb";
export default {
  name: "Content",
  created() {
    this.$store.dispatch("setMenus");
  },
  components: { Left, Header, Main, Breadcrumb }
};
</script>
