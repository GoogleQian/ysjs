<template>
  <div class="box">
    <!-- 搜索 -->
    <z-search :searchItems="searchItems" @search="search"></z-search>
    <!-- 功能+table -->
    <z-table :tableData="tableData" :tableColumns="tableColumns" :page="page" :funcs="funcs" @func="func" @handleCurrentChange="handleCurrentChange" v-loading="tableLoading"></z-table>
    <!-- 新增/编辑dialog -->
    <el-dialog :title="dialogTitle" :visible.sync="dialogVisible">
      <el-form :model="dialogData" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <!--不生成创建时间字段-->
        <el-form-item label="设备编码:" prop="devCode">
          <el-input v-model="dialogData.devCode" disabled></el-input>
        </el-form-item>
        <el-form-item label="上报人:" prop="reportName">
          <el-input v-model="dialogData.reportName" disabled></el-input>
        </el-form-item>
        <el-form-item label="联系电话:" prop="phoneNum">
          <el-input v-model="dialogData.phoneNum" disabled></el-input>
        </el-form-item>
        <el-form-item label="处理状态" prop="status">
          <el-select v-model="dialogData.status">
            <el-option label="已处理" :value="1"></el-option>
            <el-option label="未处理" :value="0"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="具体内容:" prop="msg">
          <el-input v-model="dialogData.msg" type="textarea" disabled></el-input>
        </el-form-item>
        <el-form-item label="上报时间:" prop="reportTime">
          <el-date-picker v-model="dialogData.reportTime" type="datetime" value-format="yyyy-MM-dd HH:mm:ss" disabled></el-date-picker>
        </el-form-item>
        <el-form-item label="备注:" prop="remark">
          <el-input v-model="dialogData.remark" type="textarea"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitForm('ruleForm')" :loading="btnLoading">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import ZTable from "@/components/z-table/z-table";
import ZSearch from "@/components/z-search/z-search";
import { getList, del, add, edit } from "./api.js";
import { tableColumns, searchItems, funcs } from "./data";

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
      btnLoading: false,
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
      const params = { page: this.page.currentPage, ...this.searchForm };
      getList(params).then(res => {
        this.tableLoading = false;
        this.tableData = res.data.datas.list;
        this.page.total = res.data.datas.itemCounts;
      });
    },
    search(searchForm) {
      console.log(searchForm);
      this.page.currentPage = 1;
      if (searchForm.reportTime) {
        searchForm.startTime = searchForm.reportTime[0];
        searchForm.endTime = searchForm.reportTime[1];
      }
      this.searchForm = searchForm;
      delete this.searchForm.reportTime;
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
      this.dialogTitle = "新增";
      this.dialogData = {};
      this.dialogVisible = true;
    },
    edit(row) {
      this.dialogTitle = "编辑";
      this.dialogData = { ...row };
      this.dialogVisible = true;
    },
    del(row) {
      this.$confirm("此操作将永久删除所选数据, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          del({ id: row.id }).then(res => {
            console.log("shanchu ");
            this.$message({
              message: res.data.ret === 0 ? "删除成功" : res.data.msg,
              type: res.data.ret === 0 ? "success" : "error"
            });
            this.getList();
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
    },
    submitForm(formName) {
      //不解析下面代码

      this.$refs[formName].validate(valid => {
        if (valid) {
          const params = this.dialogData;
          if (this.dialogTitle === "编辑") {
            edit({
              id: this.dialogData.id,
              data: { ...this.dialogData }
            }).then(res => {
              if (res.data.ret == 0) {
                this.$message({
                  message: "修改成功",
                  type: "success"
                });
                this.dialogVisible = false;
                this.getList();
              } else {
                this.$message({
                  message: res.data.msg,
                  type: "error"
                });
              }
            });
          } else if (this.dialogTitle === "新增") {
            add(params).then(res => {
              if (res.data.ret == 0) {
                this.$message({
                  message: "新增成功",
                  type: "success"
                });
                this.dialogVisible = false;
                this.getList();
              } else {
                this.$message({
                  message: res.data.msg,
                  type: "error"
                });
              }
            });
          }
        } else {
          return false;
        }
      });
    }
  },
  computed: {
    funcs() {
      return this.$store.state.user.funcs;
    }
  },
  components: { ZTable, ZSearch }
};
</script>

<style scoped lang="less">
@import "./index.less";
</style>
