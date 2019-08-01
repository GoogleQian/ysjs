<template>
  <keep-alive>
    <div class="set-panel top-border">
      <p style="margin-bottom: 10px;">设备编号：{{$route.query.device_id}}</p>
      <el-tabs v-model="activeName" :tab-position="tabPosition" @tab-click="handleClick" :stretch="true">
        <el-tab-pane v-for="item in set_func" :key="item.name" :label="item.name" :name="item.name">
          <!-- 加v-if避免所有tab下组件都触发created -->
          <Setting :item="{}" :currentTabComponent="item.component" v-if="tab === item.name"></Setting>
        </el-tab-pane>
      </el-tabs>
    </div>
  </keep-alive>
</template>

<script>
import Setting from "./setting/setting";
import { set_func } from "./data";
export default {
  data() {
    return {
      tab: "机型设置",
      activeName: "机型设置",
      tabPosition: "left"
    };
  },
  created() {
    this.$store.commit("setBreadcrumbList", [
      { path: "/content/dev", title: "设备管理" },
      { path: `/content/set-panel`, title: "设备设置" }
    ]);
  },
  mounted() {
    if (this.$store.state.user.useragent === "phone") {
      this.tabPosition = "top";
    }
  },
  methods: {
    handleClick(tab, event) {
      console.log(tab.name);
      this.tab = tab.name;
    }
  },
  computed: {
    set_func() {
      // userId == 1时才有"系统参数设置"
      let _set_func = [];
      if (localStorage.getItem("userId") != 1) {
        _set_func = set_func.filter(item => item.name !== "系统参数设置");
      }else {
        _set_func = set_func;
      }
      if (this.$store.state.app.devModel === 0) {
        // 步进
        return _set_func.filter(item => item.name !== "手动消毒");
      } else {
        return _set_func;
      }
    }
  },
  components: { Setting }
};
</script>

<style lang="less" scoped>
@import url("./index.less");
</style>
