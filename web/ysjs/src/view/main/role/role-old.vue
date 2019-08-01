<template>
    <div class="role top-border">
        <!-- 搜索 -->
        <el-form :inline="true" :model="searchData" class="demo-form-inline">
            <el-form-item label="角色名称：">
                <el-input v-model="searchData.name" placeholder="角色名称"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="search">查询</el-button>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="add">新增</el-button>
            </el-form-item>
        </el-form>
        <!-- 表格 -->
        <el-table :data="tableData" border style="width: 100%">
            <el-table-column fixed prop="roleName" label="角色名">
            </el-table-column>
            <el-table-column prop="remark" label="备注">
            </el-table-column>
            <el-table-column prop="createTime" label="创建时间">
            </el-table-column>
            <el-table-column fixed="right" label="操作">
                <template slot-scope="scope">
                    <el-button @click="edit(scope.row)" type="text" size="small">编辑</el-button>
                    <el-button @click="confirmDel(scope.row)" type="danger" size="small">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <!-- 编辑/新增 -->
        <el-dialog :title="dialogTitle" :visible.sync="dialogFormVisible">
            <el-form :model="dialogData" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
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
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="ok" :loading="oking">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
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
      searchData: {},
      tableData: [],
      dialogTitle: "编辑",
      dialogFormVisible: false,
      dialogData: {},
      rules: {},
      defaultProps: {
        children: "children",
        label: "name"
      },
      allMenu: [],
      treeLoading: false,
      oking: false
    };
  },
  created() {
    this.getList();
  },
  methods: {
    getList() {
      roleList().then(res => {
        if (res.data.code === 0) {
          this.tableData = res.data.page.list;
        }
      });
    },
    getAllMenu() {
      this.treeLoading = true;
      getAllMenu().then(res => {
        this.treeLoading = false;
        this.allMenu = formatterData(res.data, "menuId");
      });
    },
    // 保存编辑或新增
    ok() {
      console.log(this.$refs.roleTree.getCheckedKeys());
      this.oking = true;
      if (this.dialogTitle === "编辑") {
        editRole({
          ...this.dialogData,
          menuIdList: this.$refs.roleTree.getCheckedKeys()
        })
          .then(res => {
            this.oking = false;
            if (res.data.code === 0) {
              this.getList();
              this.$message({
                message: "修改成功",
                type: "success"
              });
              this.dialogFormVisible = false;
            } else {
              this.$message({
                message: res.data.msg,
                type: "error"
              });
            }
          })
          .catch(() => {
            this.oking = false;
          });
      } else if (this.dialogTitle === "新增") {
        addRole({
          ...this.dialogData,
          menuIdList: this.$refs.roleTree.getCheckedKeys()
        })
          .then(res => {
            this.oking = false;
            if (res.data.code === 0) {
              this.getList();
              this.$message({
                message: "新增成功",
                type: "success"
              });
              this.dialogFormVisible = false;
            } else {
              this.$message({
                message: res.data.msg,
                type: "error"
              });
            }
          })
          .catch(() => {
            this.oking = false;
          });
      }
    },
    edit(row) {
      this.dialogTitle = "编辑";
      this.getAllMenu();
      role({ roleId: row.roleId }).then(res => {
        if (res.data.code === 0) {
          this.dialogData = res.data.role;
        }
      });
      this.dialogFormVisible = true;
    },
    add() {
      this.getAllMenu();
      this.dialogTitle = "新增";
      this.dialogData = {};
      this.dialogFormVisible = true;
    },
    search() {
      console.log("submit!");
    },
    del(row) {
      delRole([row.roleId]).then(res => {
        if (res.data.code === 0) {
          this.getList();
          this.$message({
            message: "删除成功",
            type: "success"
          });
        } else {
          this.$message({
            message: res.data.msg,
            type: "error"
          });
        }
      });
    },
    confirmDel(row) {
      this.$confirm("此操作将永久删除该文件, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          this.del(row);
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除"
          });
        });
    }
  }
};
</script>

<style lang="less" scoped>
@import "./index.less";
</style>
