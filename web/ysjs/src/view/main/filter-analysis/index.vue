<template>
  <div class="filter-analysis">
    <el-row :gutter="20">
      <el-col :span="8">
        <el-card class="box-card" style="height: 400px;">
          <div slot="header" class="clearfix">
            <span>滤芯正常使用寿命</span>
            <!-- <el-button style="float: right; padding: 3px 0" type="text">操作按钮</el-button> -->
          </div>
          <el-row :gutter="20">
            <el-col :span="10" v-for="item in filters" :key="item.id" class="item clearfix">
              <el-col :span="8" class="left">
                <i :class="item.icon" :style="'font-size: 50px;color:'+item.color"></i>
              </el-col>
              <el-col :span="16" class="right" style="height: 50px;overflow: hidden;white-space:nowrap">
                <p style="color: #333; font-size: 20px;">{{item.name}}</p>
                <p>{{item.value}}</p>
              </el-col>
            </el-col>
          </el-row>
        </el-card>
      </el-col>
      <el-col :span="16">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>滤芯到期预警统计</span>
            <div style="float: right;width: 400px;">
              <el-radio-group v-model="dataType" size="mini" @change="changeType">
                <el-radio-button v-for="type in dataTypeOptions" :label="type.value" :key="type.value">{{type.label}}</el-radio-button>
              </el-radio-group>
            </div>
            <!-- <el-button style="float: right; padding: 3px 0" type="text">操作按钮</el-button> -->
          </div>
          <div style="height: 300px;">
            <Chart :size="['100%', '100%']" :watchShallow="false" :options="filterOptions"></Chart>
          </div>
        </el-card>
      </el-col>
      <el-col :span="24" style="margin-top: 10px;">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>滤芯预警</span>
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
import Chart from "@/components/chart/index.vue";
import { randomNum } from "@/common/js/unit.js";
import Moment from "moment";
export default {
  data() {
    const time = new Date();
    return {
      dataType: "week",
      dataTypeOptions: [
        { label: "最近一周", value: "week" },
        { label: "最近一月", value: "month" },
        { label: "最近一年", value: "year" },
        { label: "地区", value: "area" }
      ],
      tableData: [
        {
          deviceId: "X-01-1232451236",
          filterType: "PP棉滤芯",
          alertInfo: "滤芯老化",
          alertTime: "2018-08-22",
          changeTime: "2018-08-29",
          contactPerson: "王二仁",
          contactNumber: "13211111111"
        },
        {
          deviceId: "X-01-1232451235",
          filterType: "PP棉滤芯",
          alertInfo: "滤芯老化",
          alertTime: "2018-08-21",
          changeTime: "2018-08-28",
          contactPerson: "张大花",
          contactNumber: "17611167892"
        },
        {
          deviceId: "X-01-1232451232",
          filterType: "PP棉滤芯",
          alertInfo: "滤芯老化",
          alertTime: "2018-08-20",
          changeTime: "2018-08-27",
          contactPerson: "李三",
          contactNumber: "15632659896"
        }
      ],
      filters: [
        {
          name: "PP棉滤芯",
          value: "8个月",
          color: "green",
          id: 1,
          type: "success",
          icon: "el-icon-menu"
        },
        {
          name: "活性炭",
          value: "8个月",
          id: 2,
          color: "#40c9c6",
          type: "warning",
          icon: "el-icon-menu"
        },
        {
          name: "PP棉滤芯",
          value: "8个月",
          id: 3,
          color: "#36a3f7",
          type: "info",
          icon: "el-icon-menu"
        },
        {
          name: "PP棉滤芯",
          value: "8个月",
          id: 4,
          color: "#f4516c",
          type: "default",
          icon: "el-icon-menu"
        }
      ],
      filterOptions: {
        color: ["#a04"],
        tooltip: {
          trigger: "axis",
          axisPointer: {
            type: "shadow"
          }
        },
        grid: {
          left: "50",
          right: "4%",
          top: "3%",
          bottom: "10%",
          containerLabel: true
        },
        xAxis: [
          {
            type: "category",
            data: [
              Moment(time).format("M-DD"),
              Moment(time - 1 * 24 * 60 * 60 * 1000).format("M-DD"),
              Moment(time - 2 * 24 * 60 * 60 * 1000).format("M-DD"),
              Moment(time - 3 * 24 * 60 * 60 * 1000).format("M-DD"),
              Moment(time - 4 * 24 * 60 * 60 * 1000).format("M-DD"),
              Moment(time - 5 * 24 * 60 * 60 * 1000).format("M-DD"),
              Moment(time - 6 * 24 * 60 * 60 * 1000).format("M-DD")
            ],
            axisTick: {
              alignWithLabel: true
            }
          }
        ],
        yAxis: [
          {
            type: "value"
          }
        ],
        series: [
          {
            name: "待更换",
            type: "bar",
            data: [10, 52, 200, 334, 390, 330, 220]
          }
        ],
        color: "#ca7a74"
      }
    };
  },
  components: {
    Chart
  },
  methods: {
    changeType() {
      const time = new Date();
      if (this.dataType === "week") {
        this.filterOptions.xAxis[0].data = [
          Moment(time).format("M-DD"),
          Moment(time - 1 * 24 * 60 * 60 * 1000).format("M-DD"),
          Moment(time - 2 * 24 * 60 * 60 * 1000).format("M-DD"),
          Moment(time - 3 * 24 * 60 * 60 * 1000).format("M-DD"),
          Moment(time - 4 * 24 * 60 * 60 * 1000).format("M-DD"),
          Moment(time - 5 * 24 * 60 * 60 * 1000).format("M-DD"),
          Moment(time - 6 * 24 * 60 * 60 * 1000).format("M-DD")
        ];
        this.filterOptions.series[0].data = [10, 52, 200, 334, 390, 330, 220];
        console.log(this.filterOptions.xAxis[0].data);
      } else if (this.dataType === "month") {
        this.filterOptions.xAxis[0].data = [
          Moment(time).format("M-DD"),
          Moment(time - 1 * 24 * 60 * 60 * 1000).format("M-DD"),
          Moment(time - 2 * 24 * 60 * 60 * 1000).format("M-DD"),
          Moment(time - 3 * 24 * 60 * 60 * 1000).format("M-DD"),
          Moment(time - 4 * 24 * 60 * 60 * 1000).format("M-DD"),
          Moment(time - 5 * 24 * 60 * 60 * 1000).format("M-DD"),
          Moment(time - 6 * 24 * 60 * 60 * 1000).format("M-DD"),
          Moment(time - 7 * 24 * 60 * 60 * 1000).format("M-DD"),
          Moment(time - 8 * 24 * 60 * 60 * 1000).format("M-DD"),
          Moment(time - 9 * 24 * 60 * 60 * 1000).format("M-DD"),
          Moment(time - 10 * 24 * 60 * 60 * 1000).format("M-DD"),
          Moment(time - 11 * 24 * 60 * 60 * 1000).format("M-DD"),
          Moment(time - 12 * 24 * 60 * 60 * 1000).format("M-DD"),
          Moment(time - 13 * 24 * 60 * 60 * 1000).format("M-DD"),
          Moment(time - 14 * 24 * 60 * 60 * 1000).format("M-DD"),
          Moment(time - 15 * 24 * 60 * 60 * 1000).format("M-DD"),
          Moment(time - 16 * 24 * 60 * 60 * 1000).format("M-DD"),
          Moment(time - 17 * 24 * 60 * 60 * 1000).format("M-DD"),
          Moment(time - 18 * 24 * 60 * 60 * 1000).format("M-DD"),
          Moment(time - 19 * 24 * 60 * 60 * 1000).format("M-DD"),
          Moment(time - 20 * 24 * 60 * 60 * 1000).format("M-DD"),
          Moment(time - 21 * 24 * 60 * 60 * 1000).format("M-DD"),
          Moment(time - 22 * 24 * 60 * 60 * 1000).format("M-DD"),
          Moment(time - 23 * 24 * 60 * 60 * 1000).format("M-DD"),
          Moment(time - 24 * 24 * 60 * 60 * 1000).format("M-DD"),
          Moment(time - 25 * 24 * 60 * 60 * 1000).format("M-DD"),
          Moment(time - 26 * 24 * 60 * 60 * 1000).format("M-DD"),
          Moment(time - 27 * 24 * 60 * 60 * 1000).format("M-DD"),
          Moment(time - 28 * 24 * 60 * 60 * 1000).format("M-DD"),
          Moment(time - 29 * 24 * 60 * 60 * 1000).format("M-DD"),
          Moment(time - 30 * 24 * 60 * 60 * 1000).format("M-DD")
        ];
        this.filterOptions.series[0].data = [
          ...this.filterOptions.xAxis[0].data.map(item => randomNum(300, 800))
        ];
      }else if(this.dataType === 'year'){
        const month = Moment(time).format("MM")
        this.filterOptions.xAxis[0].data = ['09', '08', '07', '06', '05', '04', '03', '02', '01', '12', '11', '10'].reverse();
        this.filterOptions.series[0].data = this.filterOptions.xAxis[0].data.map(item => randomNum(300, 800));
      }else if(this.dataType === 'area'){
        this.filterOptions.xAxis[0].data = ["北京", "深圳", "上海", "重庆", "西安", "广州"];
        this.filterOptions.series[0].data = this.filterOptions.xAxis[0].data.map(item => randomNum(300, 800));
      }
    }
  }
};
</script>

<style lang="less" scoped>
.item {
  background: rgba(1, 1, 1, 0.2);
  margin-left: 20px;
  margin-top: 20px;
  padding-top: 30px;
  padding-bottom: 30px;
  border-radius: 5px;
  cursor: pointer;
}
</style>
