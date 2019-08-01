<template>
    <div class="dev-status top-border">
        <el-form :inline="true" :model="searchParams" class="demo-form-inline">
            <el-form-item label="设备号：">
                <el-input v-model="searchParams.device_id" placeholder="设备号"></el-input>
            </el-form-item>
            <el-form-item>
                <el-date-picker value-format="yyyy-MM-dd HH:mm:ss" v-model="searchParams.timerange" type="datetimerange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
            </el-form-item>
            <el-form-item>
                <el-button  @click="onSearch">查询</el-button>
            </el-form-item>
        </el-form>
        <el-table :data="tableData" style="width: 100%" v-loading="table_loading">
            <el-table-column prop="device_id" label="设备别名/位置" width="180">
            </el-table-column>
            <el-table-column prop="device_id" label="设备编号" width="180">
            </el-table-column>
            <el-table-column prop="update_time" label="记录时间">
                <template slot-scope="scope">
                    {{scope.row.update_time}}
                </template>
            </el-table-column>
            <el-table-column prop="address" label="最新水质状况">
                <template slot-scope="scope">
                    <el-tag type="success">正常</el-tag>
                    <el-button size="mini" type="text" @click="viewDetail(scope.row)">详情</el-button>
                </template>
            </el-table-column>
            <el-table-column prop="dev_status" label="设备最新状态">
                <template slot-scope="scope">
                    水位：{{scope.row.height}}, &nbsp;&nbsp;&nbsp;温度：{{scope.row.temp}}
                </template>
            </el-table-column>
            <el-table-column prop="status" label="设备原始字节码" width="500">
            </el-table-column>
        </el-table>
        <el-pagination style="margin-top: 10px;background: #fff;" @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page.sync="searchParams.page" :page-sizes="[10, 20, 30, 40]" :page-size="searchParams.page_size" layout="sizes, prev, pager, next" :total="totalItems">
        </el-pagination>
        <el-dialog title="水质详情" :visible.sync="waterDetailShow">
            设备编号：{{waterData.device_id}}
            <table class="table" style="width: 100%;text-align:center;">
                <tr>
                    <th>名称</th>
                    <th>入水</th>
                    <th>出水</th>
                </tr>
                <tr>
                    <td>tds</td>
                    <td>74
                        <span style="color: '#3f71d6'">良</span>
                    </td>
                    <td>33
                        <span style="color: '#3f71d6'">优</span>
                    </td>
                </tr>
                <tr>
                    <td>色度</td>
                    <td>0.38</td>
                    <td>0.26</td>
                </tr>
                <tr>
                    <td>浊度</td>
                    <td>0.92</td>
                    <td>0.41</td>
                </tr>
            </table>
        </el-dialog>
    </div>
</template>

<script>
import { getDevList } from "@/service/api/dev-status";
export default {
  data() {
    return {
      table_loading: false,
      tableData: [],
      waterDetailShow: false,
      waterData: {},
      searchParams: {
        page: 1,
        page_size: 10,
        device_id: null,
        timerange: null
      },
      totalItems: 0
    };
  },
  created() {
    this.getList();
  },
  methods: {
    getList() {
      console.log(this.searchParams);
      this.table_loading = true;
      getDevList(this.searchParams).then(res => {
        console.log(res);
        this.table_loading = false;
        this.tableData = res.data.content;
        this.totalItems = res.data.totalElements;
        this.tableData.map(item => {
          item.temp =Math.ceil(Math.random()*10000)%(90-25+1)+25;
          if (item.temp % 3 == 0) {
            item.height = "高";
          } else if (item.temp % 3 == 1) {
            item.height = "中";
          } else {
            item.height = "低";
          }
          return item;
        });
      });
    },
    onSearch() {
      this.getList();
    },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.searchParams.page_size = val;
      this.getList();
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.searchParams.page = val;
      this.getList();
    },
    viewDetail(row) {
      console.log("row", row);
      this.waterDetailShow = true;
      this.waterData = { ...row };
    }
  },
  filters: {
    random(val) {
      return Math.ceil(Math.random() * 10000) % 2 + 1;
    }
  }
};
</script>

<style lang="less" scoped>
.dev-status{
    margin: 10px;
    padding: 10px;
}
.table {
  border: 1px solid #eee;
  border-collapse: collapse;
  margin-top: 20px;
  th,
  td {
    border-left: 1px solid #eee;
    border-top: 1px solid #eee;
    padding: 10px;
  }
}
</style>
