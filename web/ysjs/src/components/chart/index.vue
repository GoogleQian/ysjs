/**
 * title: 图表标题
 * watchShallow： Boolean，
 * options: 图表配置
 * size: 容器尺寸
 */
<template>
  <div class="chart-component">
    <!-- chart -->
    <!-- <button @click="handleClick">click</button> -->
    <ECharts :style='"width: "+size[0]+";height: "+size[1]' :options='options' :auto-resize="true" :watchShallow="watchShallow"></ECharts>
  </div>
</template>

<script>
import ECharts from "vue-echarts/components/ECharts.vue";
import "echarts/lib/chart/bar";
import "echarts/lib/chart/line";
import "echarts/lib/chart/pie";
import "echarts/lib/chart/map";
import "echarts/lib/chart/radar";
import "echarts/lib/chart/scatter";
import "echarts/lib/chart/effectScatter";
import "echarts/lib/component/tooltip";
import "echarts/lib/component/polar";
import "echarts/lib/component/geo";
import "echarts/lib/component/legend";
import "echarts/lib/component/title";
import "echarts/lib/component/visualMap";
import "echarts/lib/component/markPoint";
export default {
  props: {
    watchShallow: { // false时对option深度监听，true时可通过mergeOptions调用Echarts实例的setOption方法
      type: Boolean,
      default: false
    },
    options: {
      type: Object,
      default: function() {
        return {
          xAxis: {
            type: "category",
            data: ["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"]
          },
          yAxis: {
            type: "value"
          },
          series: [
            {
              data: [820, 932, 901, 934, 1290, 1330, 1320],
              type: "line"
            }
          ]
        };
      }
    },
    size: {
      type: Array,
      default: function() {
        return ["100%", "100%"];
      }
    }
  },
  data() {
    return {};
  },
  methods: {
  },
  components: {
    ECharts
  }
};
</script>

<style lang="less" scoped>
.chart-component {
  width: 100%;
  height: 100%;
  text-align: center;
}
</style>
