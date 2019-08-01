<template>
  <div class="water-data">
    <el-card class="box-card" :body-style="{'background': 'rgb(45, 71, 103)'}" style="border: none">
      <div slot="header" class="clearfix">
        <span>
          用户用水统计
        </span>
        <div style="float: right;width: 400px;">
          <el-radio-group v-model="dataType" size="mini" @change="changeType">
            <el-radio-button v-for="type in dateTypeOptions" :label="type.value" :key="type.value">{{type.label}}</el-radio-button>
          </el-radio-group>
        </div>
      </div>
      <el-row :gutter="20">
        <el-col :span="8">
          <el-card class="box-card" style="height: 400px;">
            <div slot="header" class="clearfix">
              <span>用户平均用水数据</span>
            </div>

            <el-col :span="8" class="box">
              <div v-for="item in averageData" :key="item.id" class="item" :style="'background: ' + item.color">
                <div class="count">
                  <template>{{item.value}}{{item.unit}}</template>
                </div>
                <div class="item-name">
                  {{item.name}}
                </div>
              </div>
            </el-col>

            <!-- <div v-for="item in averageData" :key="item.id" class="text item">
            {{item.name}}: {{item.value}}
          </div> -->
          </el-card>
        </el-col>
        <el-col :span="16">
          <el-card class="box-card" style="height: 400px;">
            <div slot="header" class="clearfix">
              <span>用水总量</span>
            </div>
            <div style="height: 300px;">
              <Chart :size="['100%', '100%']" :watchShallow="false" :options="totalOptions"></Chart>
            </div>
          </el-card>
        </el-col>
        <el-col :span="12" style="margin-top: 20px;">
          <el-card class="box-card" style="height: 400px;">
            <div slot="header" class="clearfix">
              <span>平均用水频次</span>
            </div>
            <div style="height: 300px;">
              <Chart :size="['100%', '100%']" :watchShallow="false" :options="frequencyOptions"></Chart>
            </div>
          </el-card>
        </el-col>
        <el-col :span="12" style="margin-top: 20px;">
          <el-card class="box-card" style="height: 400px;">
            <div slot="header" class="clearfix">
              <span>单次使用时长</span>
            </div>
            <div style="height: 300px;">
              <Chart :size="['100%', '100%']" :watchShallow="false" :options="timeOptions"></Chart>
            </div>
          </el-card>
        </el-col>
        <el-col :span="24" style="margin-top: 20px;">
          <el-card class="box-card" style="height: 600px;">
            <div slot="header" class="clearfix">
              <span>水数据分析</span>
            </div>
            <div style="height: 500px;">
              <Chart :size="['100%', '100%']" :watchShallow="false" :options="waterOptions"></Chart>
            </div>
          </el-card>
        </el-col>
        <el-col :span="24" style="margin-top: 20px;">
          <el-card class="box-card" style="height: 600px;">
            <div slot="header" class="clearfix">
              <span>设备数量统计</span>
            </div>
            <div style="height: 500px;">
              <Chart :size="['100%', '100%']" :watchShallow="false" :options="devNumOptions"></Chart>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </el-card>
  </div>
</template>

<script>
import CountTo from "@/components/count-to/index.vue";
import Chart from "@/components/chart/index.vue";
import { renderX, randomNum } from "@/common/js/unit";
import {
  averageData,
  dateTypeOptions,
  totalOptions,
  timeOptions,
  frequencyOptions,
  waterOptions,
  devNumOptions
} from "./data";
export default {
  components: { Chart, CountTo },
  methods: {
    changeType() {
      // X轴
      this.totalOptions.xAxis.data = renderX(this.dataType).reverse();
      this.devNumOptions.xAxis.data = renderX(this.dataType).reverse();
      this.waterOptions.xAxis.data = renderX(this.dataType).reverse();
      this.frequencyOptions.xAxis.data = renderX(this.dataType).reverse();
      if (this.dataType === "week") {
        // 用户平均用水数据
        this.averageData[0].value = 40;
        this.averageData[1].value = 0.5;
        this.averageData[2].value = 95;
        // Y轴
        this.totalOptions.series[0].data = this.totalOptions.xAxis.data.map(
          item => randomNum(8, 15)
        );
        this.frequencyOptions.series[0].data = this.frequencyOptions.xAxis.data.map(
          item => randomNum(9, 20)
        );
      } else if (this.dataType === "month") {
        // 用户平均用水数据
        this.averageData[0].value = parseInt(40 / 7 * 30);
        this.averageData[1].value = parseInt(0.5 / 7 * 30);
        this.averageData[2].value = parseInt(95 / 7 * 30);
        // Y轴
        this.totalOptions.series[0].data = this.totalOptions.xAxis.data.map(
          item => randomNum(32, 60)
        );
        this.frequencyOptions.series[0].data = this.frequencyOptions.xAxis.data.map(
          item => randomNum(36, 80)
        );
      } else if (this.dataType === "year") {
        // 用户平均用水数据
        this.averageData[0].value = parseInt(40 / 7 * 365);
        this.averageData[1].value = parseInt(0.5 / 7 * 365);
        this.averageData[2].value = parseInt(95 / 7 * 365);
        this.frequencyOptions.series[0].data = this.frequencyOptions.xAxis.data.map(
          item => randomNum(36, 80)
        );

        this.totalOptions.series[0].data = this.totalOptions.xAxis.data.map(
          item => randomNum(32, 60)
        );
      }
      if (this.dataType === "area") {
        // 用户平均用水数据
        this.averageData[0].value = parseInt(40 / 7 * 365);
        this.averageData[1].value = parseInt(0.5 / 7 * 365);
        this.averageData[2].value = parseInt(95 / 7 * 365);
        // Y轴
        this.totalOptions.series[0].data = this.totalOptions.xAxis.data.map(
          item => randomNum(32, 60)
        );
        this.frequencyOptions.series[0].data = this.frequencyOptions.xAxis.data.map(
          item => randomNum(8, 12)
        );
      }
      this.timeOptions.series[0].data = [
        randomNum(51, 600),
        randomNum(51, 600),
        randomNum(51, 600),
        randomNum(51, 600),
        randomNum(51, 600)
      ];
      this.devNumOptions.series[0].data = this.devNumOptions.xAxis.data.map(
        item => randomNum(1, 100)
      );
      this.devNumOptions.series[1].data = this.devNumOptions.xAxis.data.map(
        item => randomNum(800, 1000)
      );

      this.waterOptions.series[0].data = this.waterOptions.xAxis.data.map(
        item => randomNum(1, 5)
      );

      this.waterOptions.series[1].data = this.waterOptions.xAxis.data.map(
        item => randomNum(80, 120)
      );

      this.waterOptions.series[2].data = this.waterOptions.xAxis.data.map(
        item => randomNum(1, 10)
      );

      this.waterOptions.series[3].data = this.waterOptions.xAxis.data.map(
        item => randomNum(40, 80)
      );

      this.waterOptions.series[4].data = this.waterOptions.xAxis.data.map(
        item => randomNum(20, 30)
      );

      this.waterOptions.series[5].data = this.waterOptions.xAxis.data.map(
        item => randomNum(20, 30)
      );

      this.waterOptions.series[6].data = this.waterOptions.xAxis.data.map(
        item => randomNum(5, 12)
      );

      this.waterOptions.series[7].data = this.waterOptions.xAxis.data.map(
        item => randomNum(20, 30)
      );
    }
  },
  data() {
    return {
      dataType: "week",
      dateTypeOptions,
      averageData,
      totalOptions,
      frequencyOptions,
      timeOptions,
      waterOptions,
      devNumOptions
    };
  }
};
</script>

<style lang="less" scoped>
.box {
  height: 300px;
  width: 100%;
  text-align: center;
}
.item {
  float: left;
  margin-left: 20px;
  margin-top: 20px;
  height: 80px;
  width: 130px;
  background: red;
  border-radius: 5px;
  color: #fff;
  padding: 15px;
  cursor: pointer;
}
.item-name {
  color: #eee;
}
.count {
  font-size: 30px;
  line-height: 50px;
}
</style>
