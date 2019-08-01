<template>
  <div class="filter">

    <el-form :inline="true" :model="searchParams" class="demo-form-inline">
      <el-form-item label="设备编号">
        <el-input v-model="searchParams.device_id" placeholder="设备编号"></el-input>
      </el-form-item>
      <el-form-item label="年份">
        <div class="block">
          <el-date-picker v-model="searchParams.year" type="year" placeholder="选择年" value-format="yyyy">
          </el-date-picker>
        </div>
      </el-form-item>
      <el-form-item label="月份">
        <el-select v-model="searchParams.month" placeholder="请选择" clearable>
          <el-option v-for="item in months" :key="item.value" :label="item.label" :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="更换状态">
        <el-select v-model="searchParams.replace_status" placeholder="更换状态" clearable>
          <el-option label="已完成" :value="1"></el-option>
          <el-option label="未完成" :value="0"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button  @click="getList">查询</el-button>
      </el-form-item>
      <el-form-item style="float: right">
        <el-button type="success" @click="addPlan">生成</el-button>
      </el-form-item>
    </el-form>

    <el-table class="table" :data="tableData" border style="width: 100%" v-loading="table.loading" height="442">
      <el-table-column prop="customer" label="客户" width="80">
      </el-table-column>
      <el-table-column prop="device_id" label="设备号" width="150">
      </el-table-column>
      <el-table-column prop="plan_replace_time" label="计划更换日期" width="100">
      </el-table-column>
      <el-table-column prop="real_replace_time" label="实际更换时间" width="100">
      </el-table-column>
      <el-table-column label="PP棉" width="100">
        <template slot-scope="scope">
          {{ scope.row.replace_first_filter == 0 ? '待更换': '已更换' }}
        </template>
      </el-table-column>
      <el-table-column prop="replace_second_filter" label="椰壳炭" width="100">
        <template slot-scope="scope">
          {{ scope.row.replace_second_filter == 0 ? '待更换': '已更换' }}
        </template>
      </el-table-column>
      <el-table-column prop="replace_third_filter" label="RO膜" width="100">
        <template slot-scope="scope">
          {{ scope.row.replace_third_filter == 0 ? '待更换': '已更换' }}
        </template>
      </el-table-column>
      <el-table-column prop="replace_fourth_filter" label="后置碳" width="100">
        <template slot-scope="scope">
          {{ scope.row.replace_fourth_filter == 0 ? '待更换': '已更换' }}
        </template>
      </el-table-column>
      <el-table-column prop="address" label="安装地址">
      </el-table-column>
      <el-table-column prop="repairer" label="安装人" width="100">
      </el-table-column>
      <el-table-column prop="repairer_phone_number" label="安装人手机" width="100">
      </el-table-column>
      <el-table-column prop="remark" label="备注" width="100">
      </el-table-column>
      <el-table-column label="计划执行状态" width="100">
        <template slot-scope="scope">
          {{ scope.row.replace_finshed == 0 ? '已更换': '未更换' }}
        </template>
      </el-table-column>
    </el-table>
    <div style="margin-top: 20px;">
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="page.currentPage" :page-sizes="[10, 20, 30, 40]" :page-size="page.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="page.totalItem">
      </el-pagination>
    </div>
  </div>
</template>

<script>
import { getFilterList, addPlan } from "@/service/api/filter";
export default {
  name: "filterWater",
  data() {
    return {
      tableData: [],
      table: {
        loading: false
      },
      page: {
        currentPage: 1,
        pageSize: 10,
        totalItem: 1
      },
      searchParams: {},
      months: [
        { value: '01', label: 1 },
        { value: '02', label: 2 },
        { value: '03', label: 3 },
        { value: '04', label: 4 },
        { value: '05', label: 5 },
        { value: '06', label: 6 },
        { value: '07', label: 7 },
        { value: '08', label: 8 },
        { value: '09', label: 9 },
        { value: '10', label: 10 },
        { value: '11', label: 11 },
        { value: '12', label: 12 }
      ]
    };
  },
  methods: {
    getList() {
      this.table.loading = true;
      const params = {
        service_id: 1,
        key: 'abc',
        page: this.page.currentPage,
        page_size: this.page.pageSize,
        ...this.searchParams
      };
      getFilterList(params).then(res => {
        this.table.loading = false;
        this.tableData = res.data.datas.list;
        this.page.currentPage = res.data.datas.cur_page;
        this.page.totalItem = res.data.datas.itemCounts;
      });
    },
    addPlan() {
      const params = {
        service_id: 1,
        key: 'abc'
      }
      addPlan(params).then(res => {
        if (res.data.ret == 0) {
          this.$message({
            message: "操作成功",
            type: "success"
          });
          this.dosearch();
        } else {
          this.$message({
            message: "操作失败",
            type: "error"
          });
        }
      });
    },
    handleSizeChange(e) {
      this.page.pageSize = e;
      this.getList();
    },
    handleCurrentChange(e) {
      this.page.currentPage = e;
      this.getList();
    },
    dosearch() {
      this.getList();
    }
  },
  created() {
    this.getList();
    for (var i = 0; i < 9; i++) {
      this.tableData.push(this.tableData[0]);
    }
  }
};
</script>

<style lang="less" scoped>
@import url("./filter.less");
</style>

