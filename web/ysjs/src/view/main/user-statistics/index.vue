<template>
  <div class="statistics">
    <div class="total">
      <el-card class="top-border">
        <div slot="header" class="clearfix">
          <span>设备：</span>
          <el-select v-model="queryParams.devNo" filterable>
            <el-option :value="option.device_id" :label="option.device_id" v-for="option in devList" :key="option.device_id"></el-option>
          </el-select>
          <!-- <el-autocomplete class="inline-input" v-model="queryParams.devNo" :fetch-suggestions="querySearch" placeholder="请输入内容"></el-autocomplete> -->
          <span>时间段</span>
          <el-select v-model="queryParams.type">
            <el-option :value="1" label="当日"></el-option>
            <el-option :value="2" label="最近七天"></el-option>
            <el-option :value="3" label="最近一月"></el-option>
            <el-option :value="0" label="自定义查询日期"></el-option>
          </el-select>
          <el-date-picker v-model="queryParams.startTime" :disabled="queryParams.type !== 0" value-format="yyyy-MM-dd" type="date" placeholder="开始时间">
          </el-date-picker>
          <el-date-picker v-model="queryParams.endTime" :disabled="queryParams.type !== 0" value-format="yyyy-MM-dd" type="date" placeholder="结束时间">
          </el-date-picker>
          <el-button @click="getData">查看</el-button>
        </div>
        <el-row :gutter="20">
          <el-col :span="15" class="box1">
            <!-- overview -->
            <div class="item" style="background: #2d8cf0">
              <div class="left">
                <count-to :decimals="0" class="count" :end="Number(dataOverview.usageCount) || 0">
                  <span>&nbsp;&nbsp;</span>
                </count-to>
                <div class="item-name">
                  订单数
                </div>
              </div>
              <div class="right">
                <i class="iconfont icon-dingdanguanli-"></i>
              </div>
            </div>
            <div class="item" style="background: #19be6b">
              <div class="left">
                <count-to :decimals="2" class="count" :end="Number(dataOverview.moneyAmount) || 0">
                  <span>元</span>
                </count-to>
                <div class="item-name">
                  订单总额
                </div>
              </div>
              <div class="right">
                <i class="iconfont icon-qian"></i>
              </div>
            </div>
            <div class="item" style="background: #ed3f14">
              <div class="left">
                <count-to :decimals="2" class="count" :end="Number(dataOverview.waterAmount) || 0">
                  <span>升</span>
                </count-to>
                <div class="item-name">
                  累计售水量
                </div>
              </div>
              <div class="right">
                <i class="iconfont icon-leijishuiliang"></i>
              </div>
            </div>
          </el-col>
          <el-col :span="8" style="height: 300px;border-left: 1px solid #cecece;">
            <!-- 大中小 -->
            <Chart :size="['100%', '100%']" :watchShallow="false" :options="typeOptions"></Chart>
          </el-col>
        </el-row>
      </el-card>
      <el-card class="top-border" style="margin-top: 10px;">
        <div slot="header" class="clearfix">
          <span>销售排行</span>
          <!-- <span>时间段</span> -->
          <el-select v-model="rankParams.type">
            <el-option :value="1" label="当日"></el-option>
            <el-option :value="2" label="最近七天"></el-option>
            <el-option :value="3" label="最近一月"></el-option>
            <el-option :value="0" label="自定义查询日期"></el-option>
          </el-select>
          <el-date-picker v-model="rankParams.startTime" :disabled="rankParams.type !== 0" value-format="yyyy-MM-dd" type="date" placeholder="开始时间">
          </el-date-picker>
          <el-date-picker v-model="rankParams.endTime" :disabled="rankParams.type !== 0" value-format="yyyy-MM-dd" type="date" placeholder="结束时间">
          </el-date-picker>
          <el-button @click="getRankData">查看</el-button>
        </div>
        <el-row :gutter="20">
          <el-radio-group v-model="barTyp" @change="renderChart">
            <el-radio-button label="订单数量排行"></el-radio-button>
            <el-radio-button label="销售金额排行"></el-radio-button>
            <el-radio-button label="售水量排行"></el-radio-button>
          </el-radio-group>
          <el-col :span="24" class="bar">
            <Chart :size="['100%', '100%']" :watchShallow="false" :options="devOptions"></Chart>
          </el-col>
        </el-row>
      </el-card>
    </div>
  </div>
</template>

<script>
import CountTo from "@/components/count-to/index.vue";
import Chart from "@/components/chart/index.vue";
import { getData, getDevList, getRankData } from "./api";
import { devOptions, typeOptions, dataOverview } from "./data";
export default {
  data() {
    return {
      barTyp: "订单数量排行",
      total: "day",
      devOptions,
      typeOptions,
      dataOverview,
      queryParams: {
        type: 1,
        devNo: "",
        startTime: null,
        endTime: null
      },
      rankParams: {
        type: 1,
        startTime: null,
        endTime: null
      },
      devList: [],
      dataRank: {}
    };
  },
  async created() {
    await this.getDevList();
    this.getData();
    await this.getRankData();
    this.devOptions.xAxis.data = this.dataRank.devNo || [];
    this.devOptions.series[0].data = this.dataRank.order || [];
  },
  components: {
    Chart,
    CountTo
  },
  methods: {
    async querySearch(queryString, cb) {
      await this.getDevList();
      var restaurants = this.devList.map(item => {
        return { value: item.device_id };
      });
      console.log(restaurants);
      var results = queryString
        ? restaurants.filter(this.createFilter(queryString))
        : restaurants;
      // 调用 callback 返回建议列表的数据
      cb(results);
    },
    createFilter(queryString) {
      return restaurant => {
        return (
          restaurant.value.toLowerCase().indexOf(queryString.toLowerCase()) ===
          0
        );
      };
    },
    changeTotal() {},
    renderChart(typ) {
      if (typ === "订单数量排行") {
        // 订单数量排名
        this.devOptions.series[0].data = this.dataRank.order || [];
      } else if (typ === "销售金额排行") {
        // 销售金额排行
        this.devOptions.series[0].data = this.dataRank.money || [];
      } else if (typ === "售水量排行") {
        // 设备售水量排名
        this.devOptions.series[0].data = this.dataRank.water || [];
      }
    },
    // 获取概览数据
    async getData() {
      await getData({ ...this.queryParams }).then(res => {
        const data = res.data.datas || {};
        this.dataOverview = data.consumeInfo || {};
        const cup = data.cup || {
          cupAmount: [{ name: null, value: 0 }],
          cupName: [null]
        };
        console.log(data.cup);
        this.typeOptions.series[0].data = cup.cupAmount || 0;
        this.typeOptions.legend.data = cup.cupName || 0;
      });
    },
    // 获取设备列表
    async getDevList() {
      await getDevList().then(res => {
        this.devList = res.data.datas;
        this.queryParams.devNo = this.devList[0].device_id;
      });
    },
    // 获取rank数据
    async getRankData() {
      await getRankData({ ...this.rankParams }).then(res => {
        this.dataRank = res.data.datas.cup;
        this.renderChart(this.barTyp);
      });
    }
  }
};
</script>

<style lang="less" scoped>
.statistics {
  padding: 0 10px;
}
.box1 {
  background: #fff;
  border: 0;
  display: flex;
  align-items: center;
  justify-content: space-around;
  height: 300px;
  text-align: center;
}
.item {
  height: 80px;
  width: 250px;
  background: red;
  border-radius: 5px;
  color: #fff;
  padding: 15px 10px;
  cursor: pointer;
  display: flex;
  justify-content: space-around;
  align-items: center;
  i {
    font-size: 50px;
  }
}
.count {
  font-size: 30px;
}
.bar {
  height: 400px;
}
</style>
