<template>
  <div class="market-manage top-border">
    <!-- 搜索 -->
    <z-search :searchItems="searchItems" @search="search"></z-search>
    <!-- 功能+table -->
    <z-table :tableData="tableData" :tableColumns="tableColumns" :page="page" :funcs="funcs" @func="func" @handleCurrentChange="handleCurrentChange" v-loading="tableLoading"></z-table>
    <!-- 新增/编辑dialog -->
    <el-dialog :title="'优惠券'+dialogTitle" :visible.sync="dialogVisible">
      <el-form :model="dialogData" ref="ruleForm" label-width="120px" class="demo-ruleForm">
        <el-form-item label="价格(元)">
          <el-input v-model.number="dialogData.str_price"></el-input>
        </el-form-item>
        <el-form-item label="数量">
          <el-input v-model.number="dialogData.num"></el-input>
        </el-form-item>
        <el-form-item label="开始时间">
          <!-- <el-input v-model="dialogData.start_time"></el-input> -->
          <el-date-picker v-model="dialogData.start_time" type="datetime" placeholder="选择日期" value-format="yyyy-MM-dd HH:mm:ss">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间">
          <!-- <el-input v-model="dialogData.end_time"></el-input> -->
          <el-date-picker v-model="dialogData.end_time" type="datetime" placeholder="选择日期" value-format="yyyy-MM-dd HH:mm:ss">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="设备">
          <el-select v-model="dialogData.dev_id" placeholder="请选择设备" clearable="">
            <el-option v-for="item in allDev" :key="item.id" :label="item.device_id" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitForm('ruleForm')">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import ZTable from "@/components/z-table/z-table";
import ZSearch from "@/components/z-search/z-search";
import { getList, delAd } from "@/service/api/ad";
import { tableColumns, searchItems } from "./data";
export default {
  data() {
    return {
      tableData: [],
      page: {
        total: 0,
        currentPage: 1
      },
      tableColumns,
      searchItems,
      tableLoading: false,
      searchForm: {},
      dialogTitle: "新增",
      dialogVisible: false,
      labelWidth: "100px",
      dialogData: {
        schemalist: []
      },
      allDev: []
    };
  },
  created() {
    this.getList();
  },
  methods: {
    getList() {
      this.tableLoading = true;
      const params = {
        pageSize: 10,
        page: this.page.currentPage,
        ...this.searchForm
      };
      getList(params).then(res => {
        this.tableLoading = false;
        this.tableData = res.data.content;
        this.page.total = res.data.totalElements;
      });
    },
    search(searchForm) {
      this.page.currentPage = 1;
      this.searchForm = searchForm;
      this.getList();
    },
    func({ opera, row }) {
      switch (opera) {
        case "修改":
          this.edit(row);
          break;
        case "新增":
          this.add();
          break;
        case "删除":
          this.del(row);
          break;
      }
    },
    add() {
      this.$router.push({ path: "/content/editAd" });
      //   this.dialogTitle = "新增";
      //   this.dialogData = {};
      //   this.dialogVisible = true;
      //   this.getAllDev();
    },
    edit(row) {
      console.log(row);
      this.$router.push({
        path: "/content/editAd",
        query: { row: JSON.stringify(row) }
      });
      //   this.dialogTitle = "编辑";
      //   this.dialogData = { ...row };
      //   this.dialogVisible = true;
      //   this.getAllDev({ device_id: row.dev_no, id: row.dev_id });
    },
    del(row) {
      console.log(row);
      this.$confirm("此操作将永久删除该条数据, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          delAd({ adId: row.id }).then(res => {
            console.log(res);
            if (res.data.ret === 0) {
              this.$message({
                message: "删除成功",
                type: "success"
              });
              this.getList()
            } else {
              this.$message({
                message: "删除失败",
                type: "error"
              });
            }
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除"
          });
        });
    },
    handleCurrentChange(val) {
      this.page.currentPage = val;
      this.getList();
    }
    // submitForm(formName) {
    //   this.$refs[formName].validate(valid => {
    //     if (valid) {
    //       const params = this.dialogData;
    //       if (this.dialogTitle === "编辑") {
    //         editCoupons({
    //           couponId: this.dialogData.id,
    //           data: { ...this.dialogData }
    //         }).then(res => {
    //           if (res.data.ret == 0) {
    //             this.$message({
    //               message: "修改成功",
    //               type: "success"
    //             });
    //             this.dialogVisible = false;
    //             this.getList();
    //           } else {
    //             this.$message({
    //               message: res.data.msg,
    //               type: "error"
    //             });
    //           }
    //         });
    //       } else if (this.dialogTitle === "新增") {
    //         addCoupons(params).then(res => {
    //           if (res.data.ret == 0) {
    //             this.$message({
    //               message: "新增成功",
    //               type: "success"
    //             });
    //             this.dialogVisible = false;
    //             this.getList();
    //           } else {
    //             this.$message({
    //               message: res.data.msg,
    //               type: "error"
    //             });
    //           }
    //         });
    //       }
    //     } else {
    //       return false;
    //     }
    //   });
    // },
    // addItem(index) {
    //   this.dialogData.schemalist.splice(index + 1, 0, {});
    // },
    // delItem(index) {
    //   this.dialogData.schemalist.splice(index, 1);
    // }
  },
  computed: {
    funcs() {
      return this.$store.state.user.funcs;
    }
  },
  components: { ZTable, ZSearch }
};
</script>

<style lang="less" scoped>
@import "./index.less";
</style>
