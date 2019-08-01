<template>
  <div class="account top-border">
    <!-- 搜索 -->
    <el-form :inline="true" :model="searchData" class="demo-form-inline">
      <el-form-item label="账户名：">
        <el-input v-model="searchData.name" placeholder="账户名"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="search">查询</el-button>
      </el-form-item>
    </el-form>
    <!-- 表格 -->
    <z-table :tableData="tableData" :tableColumns="tableColumns" :page="page" :funcs="funcs" @func="func" @handleCurrentChange="handleCurrentChange" v-loading="tableLoading"></z-table>

    <!-- 编辑/新增 -->
    <el-dialog :title="dialogTitle" :visible.sync="dialogFormVisible">
      <el-form :model="dialogData" :rules="rules" label-width="150px" class="demo-ruleForm" :disabled="dialogTitle === '详情'">
        <el-form-item label="商户名：" prop="mchName">
          <el-input v-model="dialogData.mchName"></el-input>
        </el-form-item>
        <el-form-item label="账户名：" prop="username">
          <el-input v-model="dialogData.username"></el-input>
        </el-form-item>
        <el-form-item label="角色：" prop="roleIdList">
          <!-- 角色列表 -->
          <el-select v-model="dialogData.roleIdList" multiple placeholder="请选择" style="width: 100%;">
            <el-option v-for="item in allRole" :key="item.roleId" :label="item.roleName" :value="item.roleId">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="密码：" prop="password">
          <el-input v-model="dialogData.password" type="password"></el-input>
        </el-form-item>
        <el-form-item label="手机号：" prop="mobile">
          <el-input v-model="dialogData.mobile"></el-input>
        </el-form-item>
        <!-- 合作开始日期TODO cooperationTime -->
        <el-form-item label="合作开始日期：" prop="cooperationTime">
           <el-date-picker v-model="dialogData.cooperationTime" type="date" placeholder="选择日期" value-format="yyyy-MM-dd"></el-date-picker>
        </el-form-item>
        <el-form-item label="备注：" prop="remark">
          <el-input v-model="dialogData.remark" type="textarea"></el-input>
        </el-form-item>

        <el-form-item label="公众账号ID：" prop="appId">
          <el-input v-model="dialogData.wxConfig.appId"></el-input>
        </el-form-item>
        <el-form-item label="公众账号身份密钥：" prop="appSecret">
          <el-input v-model="dialogData.wxConfig.appSecret"></el-input>
        </el-form-item>
        <el-form-item label="开发者密码：" prop=" secret">
          <el-input v-model="dialogData.wxConfig.secret"></el-input>
        </el-form-item>
        <el-form-item label="微信商户ID：" prop="mchId">
          <el-input v-model="dialogData.wxConfig.mchId"></el-input>
        </el-form-item>
        <el-form-item label="证书：" prop="cert">
          <el-upload class="avatar-uploader" action="/api/znss/file/cert" :headers="headers" :show-file-list="false" :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload">
            <el-button type="success">上传证书</el-button> {{dialogData.wxConfig.cert}}
          </el-upload>
          
        </el-form-item>

        <el-form-item label="绑定设备：">
          <dev-list :tableData="allUnbindDev" :total="total" :selectedIds="selectedIds" @currentChange="currentChange" @selectionChange="selectionChange"></dev-list>
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
import ZTable from "@/components/z-table/z-table";
import {
  users,
  getUser,
  addUser,
  editUser,
  delUser,
  unbindDevs
} from "@/service/api/user";
import { tableColumns } from "./data";
import { roleList } from "@/service/api/role";
import DevList from "./devList";
import Moment from "moment";
export default {
  data() {
    return {
      headers: {
        token: null
      },
      searchData: {},
      tableData: [],
      tableColumns,
      page: {
        total: 0,
        currentPage: 1
      },
      tableLoading: false,
      dialogTitle: "编辑",
      dialogFormVisible: false,
      dialogData: {
        wxConfig: {}
      },
      rules: {},
      defaultProps: {
        children: "children",
        label: "name"
      },
      allUnbindDev: [],
      allRole: [],
      oking: false,
      total: 0,
      currentPage: 1,
      user_id: null,
      selectedIds: []
    };
  },
  created() {
    this.getList();
  },
  mounted() {
    this.headers.token = localStorage.getItem("token");
    console.log(this.$refs)
  },
  methods: {
    handleAvatarSuccess(res, file) {
      if (res.ret === 0) {
        this.dialogData.wxConfig.cert = res.datas;
        this.dialogData.wxConfig = {
          ...this.dialogData.wxConfig,
          cert: res.datas
        };
        this.$message({
          message: "上传成功",
          type: "success"
        });
      } else {
        this.$message({
          message: res.msg,
          type: "error"
        });
      }
    },
    beforeAvatarUpload(file) {
      return true;
    },
    formatterTime(row, column, cellValue) {
      return Moment(cellValue).format("YYYY-MM-DD hh:mm:ss");
    },
    getList() {
      users({ limit: 10, page: this.page.currentPage }).then(res => {
        if (res.data.code === 0) {
          this.tableData = res.data.page.list;
          console.log(res.data.page)
          this.page.total = res.data.page.totalCount;
        }
      });
    },
    getRoles() {
      roleList().then(res => {
        this.allRole = res.data.page.list;
      });
    },
    unbindDevs() {
      unbindDevs({ page: this.currentPage, user_id: this.user_id }).then(
        res => {
          this.allUnbindDev = res.data.datas.list;
          this.total = res.data.datas.itemCounts;
        }
      );
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
        case "查看":
          this.view(row);
          break;
      }
    },
    currentChange(val) {
      this.currentPage = val;
      this.unbindDevs();
    },
    handleCurrentChange(val) {
      this.page.currentPage = val;
      this.getList();
    },
    selectionChange(ids) {
      this.dialogData.deviceIdList = ids;
    },
    // 保存编辑或新增
    ok() {
      this.oking = true;
      if (this.dialogTitle === "编辑") {
        editUser({
          ...this.dialogData
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
        addUser({
          ...this.dialogData
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
      this.currentPage = 1;
      this.user_id = row.userId;
      this.unbindDevs();
      this.getRoles();
      getUser({ user_id: row.userId }).then(res => {
        if (res.data.code === 0) {
          this.dialogData = { ...res.data.user };
          this.dialogData.wxConfig = this.dialogData.wxConfig || {};
          this.selectedIds = this.dialogData.deviceIdList;
        }
      });
      this.dialogFormVisible = true;
    },
    view(row){
     this.dialogTitle = "详情";
      this.currentPage = 1;
      this.user_id = row.userId;
      this.unbindDevs();
      this.getRoles();
      getUser({ user_id: row.userId }).then(res => {
        if (res.data.code === 0) {
          this.dialogData = { ...res.data.user };
          this.dialogData.wxConfig = this.dialogData.wxConfig || {};
          this.selectedIds = this.dialogData.deviceIdList;
        }
      });
      this.dialogFormVisible = true;
    },
    add() {
      this.dialogTitle = "新增";
      this.currentPage = 1;
      this.user_id = null;
      this.selectedIds = [];
      this.unbindDevs();
      this.getRoles();
      this.dialogData = { wxConfig: {}, roleIdList: [] };
      this.dialogFormVisible = true;
    },
    search() {
      console.log("submit!");
    },
    del(row) {
      this.$confirm("此操作将永久删除该条数据, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          delUser([row.userId]).then(res => {
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
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除"
          });
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
  },
  computed: {
    funcs() {
      return this.$store.state.user.funcs;
    }
  },
  components: { DevList, ZTable }
};
</script>

<style lang="less" scoped>
@import "./index.less";
</style>
