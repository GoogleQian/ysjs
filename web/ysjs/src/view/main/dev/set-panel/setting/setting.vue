<template>
  <div class="setting">
    <h1 style="text-align: center; font-size: 18px; font-weight: 700;line-height: 30px;">{{item.name}}</h1>
    <component :is="currentTabComponent" :id="$route.query.id"></component>
  </div>
</template>

<script>
import { set_func } from "../data";
import ModelSet from "./model-set/model-set";
import ChangeWater from "./change-water/change-water";
import ConfigReset from "./config-reset/config-reset";
import ManualClean from "./manual-clean/manual-clean";
import ManualWash from "./manual-wash/manual-wash";
import SysSet from "./sys-set/sys-set";
import TimesetModel from "./timeset-model/timeset-model";
import UserSet from "./user-set/user-set";
import WaterReset from "./water-reset/water-reset";
export default {
  props: {
    item: {
      type: Object
    },
    currentTabComponent: {
      type: String
    }
  },
  data() {
    return {
      set_func
    };
  },
  components: {
    ModelSet,
    ChangeWater,
    ConfigReset,
    ManualClean,
    ManualWash,
    SysSet,
    TimesetModel,
    UserSet,
    WaterReset
  }
};
</script>

<style lang="less" scoped>
@import url("./index.less");
</style>
