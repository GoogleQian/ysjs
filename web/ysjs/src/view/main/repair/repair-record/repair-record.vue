<template>
  <div class="box">
    <!-- 搜索 -->
    <z-search :searchItems="searchItems" @search="search"></z-search>
    <!-- 功能+table -->
    <z-table :tableData="tableData" :tableColumns="tableColumns" :page="page" :funcs="funcs" @func="func" @handleCurrentChange="handleCurrentChange" v-loading="tableLoading"></z-table>
    <!-- 新增/编辑dialog -->
    <el-dialog :title="dialogTitle" :visible.sync="dialogVisible">
      <el-form :model="dialogData" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="设备选择:" v-if="dialogTitle === '新增'">
          <dev-list @selectionChange="selectionChange"></dev-list>
        </el-form-item>
        <el-form-item label="设备编码:" prop="devCode" v-if="dialogTitle === '编辑'">
          <el-input v-model="dialogData.devCode" :disabled="true"></el-input>
        </el-form-item>
        <!-- <el-input v-model="dialogData.repairerId"></el-input> -->
        <el-form-item label="维修人员:" prop="repairerId" v-if="!isRepairer">
          <el-select v-model="dialogData.repairerId">
            <el-option v-for="option in allRepair" :value="option.id" :key="option.id" :label="option.realName"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="维修时间:" prop="maintenanceTime">
          <el-date-picker v-model="dialogData.maintenanceTime" type="datetime" value-format="yyyy-MM-dd HH:mm:ss"></el-date-picker>
        </el-form-item>
        <el-form-item label="维修详情:" prop="content">
          <el-input type="textarea" v-model="dialogData.content"></el-input>
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
import DevList from "./dev-list";
import { getList, del, add, edit, allotRepairer } from "./api.js";
import { tableColumns, searchItems, funcs } from "./data";
export default {
  data() {
    return {
      tableData: [],
      page: {
        total: 0,
        currentPage: 1
      },
      funcs,
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
      allDev: [],
      allRepair: [],
      selectDevs: []
    };
  },
  created() {
    this.getList();
  },
  methods: {
    selectionChange(devs) {
      this.selectDevs = devs;
    },
    getList() {
      this.tableLoading = true;
      const params = {
        page: this.page.currentPage,
        ...this.searchForm,
        repairerId: Number(localStorage.getItem("userId"))
      };
      getList(params).then(res => {
        this.tableLoading = false;
        this.tableData = res.data.datas.list;
        this.page.total = res.data.datas.itemCounts;
      });
    },
    search(searchForm) {
      console.log(searchForm);
      if (searchForm.maintenanceTime) {
        searchForm.startTime = searchForm.maintenanceTime[0];
        searchForm.endTime = searchForm.maintenanceTime[1];
        delete searchForm.maintenanceTime;
      }
      this.page.currentPage = 1;
      this.searchForm = { ...searchForm };
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
          const params = {
            ...this.dialogData,
            repairerId: Number(localStorage.getItem("userId"))
          };
          if (this.dialogTitle === "编辑") {
            edit({
              id: params.id,
              data: { ...params }
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
            if (this.selectDevs.length > 1) {
              this.$message({
                message: "只能选择一个设备",
                type: "warning"
              });
              return
            }
            params.devCode = this.selectDevs[0];
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
    isRepairer() {
      return localStorage.getItem("isRepairer");
    }
  },
  components: { ZTable, ZSearch, DevList }
};
</script>

<style scoped lang="less">
@import "./index.less";
</style>
