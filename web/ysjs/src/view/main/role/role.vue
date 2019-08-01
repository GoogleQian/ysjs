<template>
    <div class="role top-border">
        <!-- 搜索 -->
        <z-search :searchItems="searchItems" @search="search"></z-search>
        <!-- 功能+table -->
        <z-table :tableData="tableData" :tableColumns="tableColumns" :page="page" :funcs="funcs" @func="func" @handleCurrentChange="handleCurrentChange" v-loading="tableLoading"></z-table>
        <!-- 新增/编辑dialog -->
        <el-dialog :title="dialogTitle" :visible.sync="dialogVisible">
            <el-form :model="dialogData" ref="ruleForm" label-width="100px" class="demo-ruleForm">
                <el-form-item label="角色名称：" prop="roleName">
                    <el-input v-model="dialogData.roleName"></el-input>
                </el-form-item>
                <el-form-item label="权限设置：" prop="menuIdList">
                    <el-tree v-loading="treeLoading" :data="allMenu" :default-checked-keys="dialogData.menuIdList || []" show-checkbox node-key="menuId" default-expand-all :props="defaultProps" check-strictly ref="roleTree">
                    </el-tree>
                </el-form-item>
                <el-form-item label="备注：" prop="remark">
                    <el-input type="textarea" v-model="dialogData.remark"></el-input>
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
import { tableColumns, searchItems } from "./data";
import {
  roleList,
  role,
  addRole,
  editRole,
  delRole,
  getAllMenu
} from "@/service/api/role";
import { formatterData } from "@/common/js/formatterData";
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
      allMenu: [],
      treeLoading: false,
      defaultProps: {
        children: "children",
        label: "name"
      },
      btnLoading: false
    };
  },
  created() {
    this.getList();
  },
  methods: {
    getList() {
      this.tableLoading = true;
      const params = { page: this.page.currentPage, ...this.searchForm };
      roleList(params).then(res => {
        this.tableLoading = false;
        this.tableData = res.data.page.list;
        // this.page.total = res.data.page.totalCount;
      });
    },
    getAllMenu(params) {
      this.treeLoading = true;
      getAllMenu().then(res => {
        this.treeLoading = false;
        this.allMenu = formatterData(res.data, "menuId");
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
      this.dialogTitle = "新增";
      this.dialogData = {};
      this.dialogVisible = true;
      this.getAllMenu();
    },
    edit(row) {
      this.dialogTitle = "编辑";
      this.dialogData = { ...row };
      this.dialogVisible = true;
      this.getAllMenu();
      role({ roleId: row.roleId }).then(res => {
        if (res.data.code === 0) {
          this.dialogData = res.data.role;
        }
      });
    },
    del(row) {
      this.$confirm("此操作将永久删除该条数据, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          delRole([row.roleId]).then(res => {
            this.$message({
              message: res.data.code === 0 ? "删除成功" : res.data.msg,
              type: res.data.code === 0 ? "success" : "error"
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
      this.$refs[formName].validate(valid => {
        if (valid) {
          const params = this.dialogData;
          this.btnLoading = true;
          if (this.dialogTitle === "编辑") {
            editRole({
              ...this.dialogData,
              menuIdList: this.$refs.roleTree.getCheckedKeys()
            }).then(res => {
              this.btnLoading = false;
              if (res.data.code == 0) {
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
            addRole({
              ...this.dialogData,
              menuIdList: this.$refs.roleTree.getCheckedKeys()
            }).then(res => {
              if (res.data.code == 0) {
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
.role {
  padding: 10px;
  margin: 10px;
}
</style>
