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
        <el-form-item label="维修员姓名:" prop="realName">
          <el-input v-model="dialogData.realName"></el-input>
        </el-form-item>
        <el-form-item label="手机号:" prop="phone">
          <el-input v-model="dialogData.phone"></el-input>
        </el-form-item>
        <el-form-item label="登录名:" prop="loginName">
          <el-input v-model="dialogData.loginName"></el-input>
        </el-form-item>
        <el-form-item label="密码:" prop="pwd">
          <el-input v-model="dialogData.pwd"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitForm('ruleForm')" :loading="btnLoading">确 定</el-button>
      </div>
    </el-dialog>
    <!-- 分配设备 -->
    <el-dialog :title="devDialog.title" :visible.sync="devDialog.visible">
      <!-- allUnbindDev:所有未分配的设备   total: 总条数 selectedIds: 已分配给该用户的设备 -->
      <dev-list :tableData="allUnbindDev" ref="dev-list" :total="total" :selectedIds="selectedIds" @currentChange="currentChange" @selectionChange="selectionChange"></dev-list>
      <div slot="footer" class="dialog-footer">
        <el-button @click="devDialog.visible = false">取 消</el-button>
        <el-button type="primary" @click="submitDev('devForm')" :loading="btnLoading">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import ZTable from "@/components/z-table/z-table";
import ZSearch from "@/components/z-search/z-search";
import DevList from "./devList";
import {
  getList,
  del,
  add,
  edit,
  getDevDetail,
  allotRepairer,
  getRepairDev,
  getAllDev
} from "./api.js";
import { tableColumns, searchItems, funcs } from "./data";

export default {
  data() {
    return {
      tableData: [],
      page: {
        total: 0,
        currentPage: 1
      },
      // funcs,
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
      devDialogData: { roleIdList: [] },
      devDialog: {
        title: "分配设备",
        visible: false
      },
      allUnbindDev: [],
      total: 0,
      selectedIds: [], // 供dev-list使用的设备列表
      ids: null, // 供提交的设备id列表
      id: null // 维修员id
    };
  },
  created() {
    this.getList();
  },
  methods: {
    getList() {
      this.tableLoading = true;
      const params = {
        page: this.page.currentPage,
        ...this.searchForm
      };
      getList(params).then(res => {
        this.tableLoading = false;
        this.tableData = res.data.datas.list;
        this.page.total = res.data.datas.itemCounts;
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
        case "分配设备":
          this.setDev(row);
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
    currentChange(val) {
      this.currentPage = val;
      this.devDialogData.params.page = val;
      this.getAllDev(this.devDialogData);
    },
    selectionChange(ids) {
      this.ids = ids;
    },
    handleCurrentChange(val) {
      this.page.currentPage = val;
      this.getList();
    },
    async getRepairDev(params) {
      // 获取所有未绑定设备
      await getRepairDev(params).then(res => {
        if (res.data.datas.list) {
          this.selectedIds = res.data.datas.list.map(item => item.id);
        }
      });
    },
    // 获取当前维修员下的设备
    async getAllDev(params) {
      await getAllDev(params).then(res => {
        if (res.data.ret === 0) {
          this.allUnbindDev = res.data.datas.list;
          this.total = res.data.datas.itemCounts;
        }
      });
    },
    async setDev(row) {
      this.currentPage = 1;
      this.devDialogData = { roleIdList: [] };
      this.devDialog.visible = true;
      this.id = null;
      this.ids = null;
      this.id = row.id;
      // 获取未绑定的设备列表（所有设备）
      await this.getRepairDev({
        id: row.id,
        params: { page: 1, pageSize: 999 }
      });
      // 获取当前维修员已绑定设备
      this.devDialogData = { id: row.id, params: { page: 1 } };
      await this.getAllDev(this.devDialogData);
      this.$refs['dev-list'].toggleSelection()
    },
    submitDev() {
      allotRepairer({
        repairId: this.id,
        params: { devIds: [...this.ids].join(",") }
      }).then(res => {
        if (res.data.ret === 0) {
          this.$message({
            message: "分配成功",
            type: "success"
          });
          this.devDialog.visible = false;
        } else {
          this.$message({
            type: "error",
            message: res.data.msg
          });
        }
      });
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
  components: { ZTable, ZSearch, DevList }
};
</script>

<style scoped lang="less">
@import "./index.less";
</style>
