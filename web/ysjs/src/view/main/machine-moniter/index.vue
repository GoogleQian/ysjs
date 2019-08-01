<template>
  <div class="machine-moiniter">
    <el-row :gutter="20">
      <el-col :span="16">
        <el-card class="box-card" style="height: 400px;">
          <div slot="header" class="clearfix">
            <span>设备总览</span>
          </div>
          <el-row :gutter="20">
            <el-col :span="8" class="box">
              <div v-for="item in devOverview" :key="item.id" class="item" :style="'background: ' + item.color">
                <count-to class="count" :end="item.value"></count-to>
                <div class="item-name">
                  {{item.name}}
                </div>
              </div>
            </el-col>
            <el-col :span="14" style="height: 300px;">
              <Chart :size="['100%', '100%']" :watchShallow="false" :options="devOptions"></Chart>
            </el-col>
          </el-row>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card class="box-card" style="height: 400px;">
          <div slot="header" class="clearfix">
            <span>设备异常类型分布</span>
          </div>
          <div style="height: 300px">
            <Chart :size="['100%', '100%']" :watchShallow="false" :options="errorOptions"></Chart>
          </div>
        </el-card>
      </el-col>
      <el-col :span="24" style="margin-top: 10px;">
        <el-card class="box-card" style="height: 700px;">
          <div slot="header" class="clearfix">
            <span>设备异常统计</span>
            <div style="float: right;width: 400px;">
              <el-radio-group v-model="dataType" size="mini" @change="changeType">
                <el-radio-button v-for="type in dataTypeOptions" :label="type.value" :key="type.value">{{type.label}}</el-radio-button>
              </el-radio-group>
            </div>
          </div>
          <div style="height: 600px">
            <Chart :size="['100%', '100%']" :watchShallow="false" :options="abnormalOptions"></Chart>
          </div>
        </el-card>
      </el-col>
      <el-col :span="24" style="margin-top: 10px;">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>设备异常报警</span>
            <!-- <el-button style="float: right; padding: 3px 0" type="text">操作按钮</el-button> -->
          </div>
          <div>
            <el-table :data="tableData" style="width: 100%" size="small" :stripe="true">
              <el-table-column prop="deviceId" label="设备编号" width="180">
              </el-table-column>
              <el-table-column prop="filterType" label="滤芯类型" width="180">
              </el-table-column>
              <el-table-column prop="alertInfo" label="报警详情">
              </el-table-column>
              <el-table-column prop="alertTime" label="报警时间">
              </el-table-column>
              <el-table-column prop="changeTime" label="最迟更换时间">
              </el-table-column>
              <el-table-column prop="contactPerson" label="联系人">
              </el-table-column>
              <el-table-column prop="contactNumber" label="联系方式">
              </el-table-column>
            </el-table>
            <el-pagination :page-size="10" :pager-count="11" layout="prev, pager, next" :total="3" style="margin-top: 20px;">
            </el-pagination>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import CountTo from "@/components/count-to/index.vue";
import Chart from "@/components/chart/index.vue";
import { renderX, randomNum } from "@/common/js/unit";
import {
  tableData,
  devOptions,
  errorOptions,
  devOverview,
  abnormalOptions,
  dataTypeOptions
} from "./data";
export default {
  components: {
    Chart,
    CountTo
  },
  data() {
    return {
      dataType: "week",
      dataTypeOptions,
      tableData,
      devOptions,
      errorOptions,
      abnormalOptions,
      devOverview
    };
  },
  methods: {
    changeType() {
      // X轴
      this.abnormalOptions.xAxis[0].data = renderX(this.dataType);
      this.abnormalOptions.series[0].data = this.abnormalOptions.xAxis[0].data.map(
        item => randomNum(100, 300)
      );
      this.abnormalOptions.series[1].data = this.abnormalOptions.xAxis[0].data.map(
        item => randomNum(100, 300)
      );
      this.abnormalOptions.series[2].data = this.abnormalOptions.xAxis[0].data.map(
        item => randomNum(100, 300)
      );
      this.abnormalOptions.series[3].data = this.abnormalOptions.xAxis[0].data.map(
        item => randomNum(100, 300)
      );
    }
  }
};
</script>

<style lang="less" scoped>
.box {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-around;
  height: 300px;
  text-align: center;
}
.item {
  height: 80px;
  width: 200px;
  background: red;
  border-radius: 5px;
  color: #fff;
  padding: 5px;
  cursor: pointer;
}
.item-name {
  color: #eee;
}
.count {
  font-size: 50px;
}
</style>
