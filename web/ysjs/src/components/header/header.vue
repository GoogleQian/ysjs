<template>
  <div class="header">
    <!-- <div class="collapse">
      <i class="iconfont icon-zhankaishouqi" @click="toggleCollapse"></i>
    </div> -->
    <div class="left">
      <i class="iconfont icon-sanheng" @click="toggleCollapse"></i>
    </div>
    <!-- <img src="@/common/img/light-title.png" alt=""> -->
    <div class="right">
      <el-dropdown class="user">
        <span class="el-dropdown-link" style="line-height: 20px;display: inline-block;cursor: pointer;">
          <!-- <i class="iconfont icon-seeuser"></i> -->
          <img src="../../common/img/Avatar.png" alt="用户头像" width="20px">
          <span class="Avatar" style="color: #fff;">&nbsp;{{username}}</span>
        </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item @click.native="logout">安全退出</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
import { logout } from "@/service/api/login";
export default {
  name: "Header",
  data() {
    return {
      menuList: []
    };
  },
  methods: {
    toggleCollapse() {
      this.$store.commit("toggleCollapse");
    },
    logout() {
      logout().then(res => {});
      this.$router.push({ path: "/login" });
      localStorage.removeItem("token");
      localStorage.removeItem("isRepairer");
      this.$store.commit("setToken", "");
      this.$store.commit("setMenus", []);
    }
  },
  computed: {
    username() {
      return localStorage.getItem("username");
    }
  }
};
</script>

<style scoped lang="less">
@import "./header.less";
</style>
