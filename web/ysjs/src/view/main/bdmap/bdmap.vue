<template>
  <div class="bdmap">
    <div id="bdMap" v-loading="bdmaploading" element-loading-text="拼命加载中" element-loading-spinner="el-icon-loading" element-loading-background="rgba(0, 0, 0, 0.8)"></div>
  </div>
</template>

<script>
import eventBus from "@/service/eventbus";
import { _initBdMap, _addOverLay, _MarkerClusterer } from "./bdmap.js";
import { getProList, getDevList } from "@/service/api/bdmap.js";
export default {
  name: "bdmap",
  data() {
    return {
      bdmaploading: true
    };
  },
  mounted() {
    this.init();
  },
  methods: {
    init() {
      let config = { domId: "bdMap", city: "深圳", maxzoom: 18, minzoom: 5 };
      _initBdMap(config);
      this.getDevList();
    },
    getDevList() {
      this.bdmaploading = true;
      getDevList().then(res => {
        // 请求某省设备列表
        const devList = res.data.datas;
        this.bdmaploading = false;
        _MarkerClusterer(devList); // 依据设备列表画标注
      });
    }
  }
};
</script>

<style lang="less" scoped>
@import url("./bdmap.less");
</style>
