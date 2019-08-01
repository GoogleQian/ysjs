// 表格+翻页  包含删、改、查功能
// 接受props:tableData(表格数据)、tableItems(表格条目及操作类型)、加载状态、pageInfo(包含每页条数、当前页、总条数)
// 向上传递:查、改、删
<template>
  <div class="crud">
    <el-table class="table" :data="_tableData" border style="width: 100%" v-loading="table.loading" height="533">
      <template v-for="item in tableItems.items">
        <el-table-column v-if="!item.type" :label="item.label" :prop="`${item.value}`" :width="item.width" :key="item.label" :formatter="item.formatter">
          <!-- <template slot-scope="scope">
            {{scope.row[item.value]}}
          </template> -->
        </el-table-column>

        <!-- 定时开关机 -->
        <el-table-column v-if="item.type === 'open_close'" :label="item.label" :width="item.width" :key="item.label">
          <template slot-scope="scope">
            <el-button size="mini" @click="open_close(scope.row)">详情</el-button>
          </template>
        </el-table-column>

        <!-- 时间戳 -->
        <el-table-column v-if="item.type === 'stamp'" :label="item.label" :width="item.width" :key="item.label">
          <template slot-scope="scope">
            {{scope.row[item.value] | formatDate}}
          </template>
        </el-table-column>

        <!-- 出水类型 -->
        <el-table-column v-if="item.type === 'water_temp'" :label="item.label" :width="item.width" :key="item.label">
          <template slot-scope="scope">
            <el-tag type="danger" v-if="scope.row[item.value] === 1">开水</el-tag>
            <el-tag type="warning" v-if="scope.row[item.value] == 2">热水</el-tag>
            <el-tag v-if="scope.row[item.value] == 3">常温水</el-tag>
            <el-tag type="info" v-if="scope.row[item.value] == 4">冷水</el-tag>
          </template>
        </el-table-column>

        <!-- 设备最新状态 -->
        <el-table-column v-if="item.type === '_water_status'" :label="item.label" :width="item.width" :key="item.label">
          <template slot-scope="scope">
            <el-tag type="info" v-if="scope.row[item.value] === 1">异常</el-tag>
            <el-tag type="success" v-if="scope.row[item.value] !== 1">正常</el-tag>
          </template>
        </el-table-column>

        <!-- 设备最新状态 -->
        <el-table-column v-if="item.type === 'dev_status'" :label="item.label" :width="item.width" :key="item.label">
          <template slot-scope="scope">
            <!-- <el-tag type="info" v-if="scope.row[item.value] === 1">异常</el-tag> -->
            <el-tag type="success">正常</el-tag>
          </template>
        </el-table-column>

        <!-- 设备最新状态 -->
        <el-table-column v-if="item.type === 'money'" :label="item.label" :width="item.width" :key="item.label">
          <template slot-scope="scope">
            {{scope.row[item.value] / 100}}
          </template>
        </el-table-column>

        <!-- 支付状态 -->
        <el-table-column v-if="item.type === 'pay_status'" :label="item.label" :width="item.width" :key="item.label">
          <template slot-scope="scope">
            <el-tag v-if="scope.row[item.value] === 1">待支付</el-tag>
            <el-tag type="success" v-if="scope.row[item.value] === 2">已支付</el-tag>
            <el-tag type="info" v-if="scope.row[item.value] === 3">已退款</el-tag>
          </template>
        </el-table-column>

        <!-- 出水状态 -->
        <el-table-column v-if="item.type === 'sell_status'" :label="item.label" :width="item.width" :key="item.label">
          <template slot-scope="scope">
            <el-tag type="success" v-if="scope.row[item.value] === 0">订单已取消</el-tag>
            <el-tag type="success" v-if="scope.row[item.value] === 1">已出水</el-tag>
            <el-tag v-if="scope.row[item.value] === 2">待出水</el-tag>
            <el-tag type="error" v-if="scope.row[item.value] === 3">售卖失败</el-tag>
          </template>
        </el-table-column>

        <!-- 最新水质状况 -->
        <el-table-column v-if="item.type === 'water_status'" :label="item.label" :width="item.width" :key="item.label">
          <template slot-scope="scope">
            <el-tag type="success" v-if="scope.row[item.value] === 0">正常</el-tag>
            <el-tag v-if="scope.row[item.value] === 1">异常</el-tag>
            <el-button size="mini" type="text" @click="viewDetail(scope.row)">详情</el-button>
          </template>
        </el-table-column>

        <el-table-column :label="item.label" v-if="item.type === 'geo'" :key="item.label">
          <template slot-scope="scope">
            [{{scope.row.device_x}},{{scope.row.device_y}}]
          </template>
        </el-table-column>
      </template>

      <el-table-column label="操作" v-if="tableItems.actions.length>0" :width="operaWidth">
        <template slot-scope="scope">
          <el-button v-if="tableItems.actions.indexOf('编辑') !== -1" @click="edit(scope.row)" size="mini">编辑</el-button>
          <el-button v-if="tableItems.actions.indexOf('商户管理') !== -1" @click="merchant(scope.row)" size="mini">商户管理</el-button>
          <el-button v-if="tableItems.actions.indexOf('通道管理') !== -1" @click="pip(scope.row)" size="mini">通道管理</el-button>
          <el-button v-if="tableItems.actions.indexOf('参数设置') !== -1" @click="set(scope.row)" size="mini">参数设置</el-button>
          <el-button v-if="tableItems.actions.indexOf('管理') !== -1" @click="manage(scope.row)" size="mini">管理</el-button>
          <el-button v-if="tableItems.actions.indexOf('设备二维码') !== -1" @click="QRcode(scope.row)" size="mini">设备二维码</el-button>
          <el-button v-if="tableItems.actions.indexOf('删除') !== -1" @click="open2(scope.row)" type="danger" size="mini">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination class="pagination" :total="pageInfo.totalItems" @size-change="sizeChange" :page-sizes="[10, 20, 30, 40]" :page-size="pageInfo.page_size" :current-page="pageInfo.page" @current-change="pageChange" background layout="sizes, prev, pager, next"></el-pagination>
  </div>
</template>

<script>
import QRCode from "qrcode";
export default {
  name: "crud",
  data() {
    return {
      waterDetailVisible: false,
      gridData: [{}, {}]
    };
  },
  methods: {
    search() {
      // 查
      this.$emit("search");
    },
    edit(row) {
      // 改
      this.$emit("edit", row);
    },
    set(row) {
      this.$emit("set", row);
    },
    manage(row) {
      this.$emit("manage", row);
    },
    merchant(row) {
      this.$emit("merchant", row);
    },
    pip(row) {
      this.$emit("pip", row);
    },
    QRcode(row){
      QRCode.toDataURL(`http://iotsvr.he-live.com/wx_pay/index.html#${row.device_id}`).then(res => {
        this.$confirm(`<img src="${res}" width="100%"/>`, `${row.device_id}`, {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          dangerouslyUseHTMLString: true
        });
      });
    },
    del(row) {
      row.delPop = false;
      // 删
      this.$emit("del", row);
    },
    setAgent(row) {
      this.$emit("setAgent", row);
    },
    pageChange(page) {
      // 页码变化
      this.pageInfo.page = page;
      this.search();
    },
    sizeChange(pagesize) {
      // 每页数目变化
      this.pageInfo.page_size = pagesize;
      this.pageInfo.page = 1;
      this.search();
    },
    open2(row) {
      this.$confirm(
        `此操作将永久删除该<strong style='font-weight: 700;color: red;'>${
          this.delTitle
        }</strong>, 是否继续?`,
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
          dangerouslyUseHTMLString: true
        }
      )
        .then(() => {
          this.$emit("del", row);
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除"
          });
        });
    },
    viewDetail(row) {
      this.waterDetailVisible = true;
      this.$emit("showwaterDetail", row);
    },
    open_close(row) {
      this.$emit("open_close", row);
    }
  },
  computed: {
    _tableData() {
      // 给每个数据添加delPop来标识是否显示删除确认框
      const data = this.tableData.map(item => {
        return { ...item, ...{ delPop: false } };
      });
      return data;
    },
    pages() {
      return Math.ceil(this.page.items / this.page.size);
    }
  },
  mounted() {
    // 表单创建完成就主动触发一次search
    this.search();
  },
  props: {
    operaWidth: {
      default: 220,
      type: Number
    },
    delTitle: {
      type: String,
      default: null
    },
    tableData: {
      type: Array,
      default: function() {
        return [
          {
            date: "2016-05-04",
            name: "王小虎",
            province: "上海",
            city: "普陀区",
            address: "上海市普陀区金沙江路 1518 弄",
            zip: 200333,
            id: 0
          }
        ];
      }
    },
    tableItems: {
      type: Object,
      default: function() {
        return {
          items: [{ label: "日期", value: "date", width: "100" }],
          action: ["编辑", "删除"]
        };
      }
    },
    table: {
      type: Object,
      default: function() {
        return {
          loading: true
        };
      }
    },
    pageInfo: {
      type: Object,
      default: function() {
        return {
          totalItems: 100, // 总条数
          page_size: 10, // 每页数目
          page: 1 // 当前页
        };
      }
    }
  },
  filters: {
    formatDate(val) {
      var time = new Date(val);
      var y = time.getFullYear();
      var m = time.getMonth() + 1;
      var d = time.getDate();
      var h = time.getHours();
      var mm = time.getMinutes();
      var s = time.getSeconds();
      return (
        y +
        "-" +
        add0(m) +
        "-" +
        add0(d) +
        " " +
        add0(h) +
        ":" +
        add0(mm) +
        ":" +
        add0(s)
      );
    }
  }
};
function add0(m) {
  return m < 10 ? "0" + m : m;
}
</script>

<style lang="less" scoped>
@import url("./crud.less");
</style>

