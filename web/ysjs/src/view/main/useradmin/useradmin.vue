<template>
  <div class="useradmin" v-loading="getDataing">
    <el-button @click="handleAdd">新增</el-button>

    <el-table :data="tableData" style="width: 100%;margin-top: 10px;" highlight-current-row height="491" border>
      <el-table-column label="用户名">
        <template slot-scope="scope">
          <i class="el-icon-time"></i>
          <span style="margin-left: 10px">{{ scope.row.username }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" width="800">
        <template slot-scope="scope">
          <div v-show="!scope.row.isEdit">
            <div class="remarksText">{{scope.row.remark}}</div>
            <el-button @click="edit(scope.$index, scope.row);" size="mini" type="text" style="float: right">
              <i class="el-icon-edit"></i>
            </el-button>
          </div>
          <div v-show="scope.row.isEdit">
            <el-input class="remarksEdit" placeholder="请输入内容" v-model="scope.row._remark" style="width: 200px" size="mini"></el-input>
            <el-button type="warning" plain @click="cancel(scope.$index, scope.row);" size="mini">
              <i class="el-icon-back"></i>取消</el-button>
            <el-button type="success" plain @click="save(scope.$index, scope.row);" size="mini">
              <i class="el-icon-success"></i>保存</el-button>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="300">
        <template slot-scope="scope">
          <!-- <el-button size="mini" @click.stop.prevent="handleEdit(scope.$index, scope.row)">编辑</el-button> -->
          <el-button size="mini" @click="handleCurrentChange(scope.$index, scope.row)">权限设置</el-button>
          <el-button size="mini" @click.stop.prevent="handlePsdUpdate(scope.$index, scope.row)">修改密码</el-button>
          <el-button size="mini" type="danger" @click.stop.prevent="handleDelete(scope.$index, scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <div class="userinfo" v-if="selectId" v-loading="userLoading">
      <div style="margin-top: 20px">
        <p>用户权限设置
          <el-tag type="success">{{userInfo.username}}</el-tag>
        </p>
        <el-checkbox-group v-model="userInfo.menus" size="mini" style="float: left;">
          <el-checkbox label="devMap" border>设备分布</el-checkbox>
          <el-checkbox label="dev" border>设备管理</el-checkbox>
          <el-checkbox label="history" border>水质数据</el-checkbox>
          <el-checkbox label="filter" border>滤芯更换</el-checkbox>
          <el-checkbox label="user" border>用户管理</el-checkbox>
        </el-checkbox-group>
        <el-button @click="update_permi" style="float: right" >保存</el-button>
      </div>
    </div>

    <el-dialog :title="addForm.title" :visible.sync="addForm.show">
      <el-form :model="form" :rules="rules" ref="addForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="form.username"></el-input>
        </el-form-item>
        <el-form-item type="password" label="密码" prop="psd">
          <el-input v-model="form.psd"></el-input>
        </el-form-item>
        <el-form-item type="password" label="确认密码" prop="psd_confirm">
          <el-input v-model="form.psd_confirm"></el-input>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button  @click="submitForm('addForm')">{{addForm.btnText}}</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
    <el-dialog title="提示" :visible.sync="deleteRow" width="30%">
      <span>这是一段信息</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="deleteRow = false">取 消</el-button>
        <el-button  @click="userdelete">确 定</el-button>
      </span>
    </el-dialog>
    <!-- 修改密码 -->
    <el-dialog title="修改密码" :visible.sync="psd_update.show">
      <el-form :model="psd" :rules="psd_update.rules" ref="psd" label-width="100px" class="demo-ruleForm">
        <el-form-item label="密码" prop="psd">
          <el-input v-model="psd.psd"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="psd_confirm">
          <el-input v-model="psd.psd_confirm"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button  @click="submitForm1('psd')">{{addForm.btnText}}</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
    <el-dialog title="提示" :visible.sync="deleteRow" width="30%">
      <span>这是一段信息</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="deleteRow = false">取 消</el-button>
        <el-button  @click="userdelete">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import md5 from "md5";
import {
  getUserList,
  update_permi,
  userAdd,
  userEdit,
  userDelete,
  psdUpdate,
  remarkUpdate
} from "@/service/api/useradmin";
export default {
  name: "useradmin",
  data() {
    var psd_confirm = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请再次输入密码"));
      } else if (value !== this.form.psd) {
        console.log(this.form.psd);
        callback(new Error("两次输入密码不一致!"));
      } else {
        callback();
      }
    };
    var psd_confirm1 = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请再次输入密码"));
      } else if (value !== this.psd.psd) {
        console.log(this.psd.psd);
        callback(new Error("两次输入密码不一致!"));
      } else {
        callback();
      }
    };
    return {
      selectId: null,
      userLoading: false,
      getDataing: false,
      deleteRow: false,
      tableData: [],
      id: null,
      psd: {
        id: null,
        psd: null,
        psd_confirm: null
      },
      psd_update: {
        show: false,
        rules: {
          psd: [{ required: true, message: "请输入密码", trigger: "blur" }],
          psd_confirm: [
            { required: true, message: "请确认密码", trigger: "blur" },
            { validator: psd_confirm1, trigger: "blur" }
          ]
        }
      },
      userInfo: {
        id: null,
        username: null,
        remark: null,
        auths: []
      },
      addForm: {
        show: false,
        title: "新增",
        btnText: "保存"
      },
      form: {
        username: "",
        remark: "",
        psd: "",
        psd_confirm: ""
      },
      rules: {
        username: [
          { required: true, message: "请输入用户名", trigger: "blur" },
          { min: 3, max: 15, message: "长度在 3 到 15 个字符", trigger: "blur" }
        ],
        // psd: [{ required: true, message: "请输入密码", trigger: "blur" }],
        psd_confirm: [
          // { required: true, message: "请确认密码", trigger: "blur" },
          { validator: psd_confirm, trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getUserList();
  },
  methods: {
    getUserList() {
      this.selectId = null;
      this.getDataing = true;
      //   获取用户列表;
      getUserList({ page_size: 1000 }).then(res => {
        this.getDataing = false;
        let dataList = res.data.datas;
        let user_id = sessionStorage.getItem("user_id");
        // 删除超级管理员
        dataList = dataList.filter(item => item.user_id != user_id);
        dataList.map(item => {
          item.isEdit = false;
        });
        this.tableData = dataList;
      });
    },
    update_permi() {
      // 修改权限
      let params = {
        id: this.selectId,
        menus: this.userInfo.menus.join(",")
      };
      update_permi(params).then(res => {
        if (res.data.ret == 0) {
          this.$message({
            type: "success",
            message: "修改成功"
          });
          this.getUserList();
        }
      });
    },
    userAdd() {
      // 新增用户
      let params = { ...this.form };
      params.psd = md5(params.psd);
      params.psd_confirm = md5(params.psd_confirm);
      userAdd(params).then(res => {
        if (res.data.ret == 0) {
          this.addForm.show = false;
          this.$message({
            message: "新增完成",
            type: "success"
          });
          this.getUserList();
        }
      });
    },
    userEdit() {
      let params = { ...this.form, id: this.id };
      params.psd = md5(params.psd);
      params.psd_confirm = md5(params.psd_confirm);
      userEdit(params).then(res => {
        if (res.data.ret == 0) {
          this.$message({
            message: "修改完成",
            type: "success"
          });
          this.addForm.show = false;
          this.getUserList();
        }
      });
    },
    userdelete() {
      let params = {
        id: this.id
      };
      this.deleteRow = false;
      userDelete(params).then(res => {
        if (res.data.ret == 0) {
          this.$message({
            message: "删除完成",
            type: "success"
          });
          this.getUserList();
        }
      });
    },
    handleCurrentChange(index, row) {
      // 修改权限
      if (row) {
        this.selectId = row.id || "";
        this.userInfo = { ...row };
      }
    },
    handleAdd() {
      this.addForm.show = true;
      this.addForm.title = "新增";
      this.form = { ...this.backupForm };
    },
    handleDelete(index, row) {
      this.deleteRow = true;
      this.id = row.id;
    },
    handleEdit(index, row) {
      this.form = {
        username: row.username,
        remark: row.remark
      };
      this.id = row.id;
      this.addForm.show = true;
      this.addForm.title = "编辑";
    },
    submitForm1(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          console.log("valid");
          let params = { ...this.psd };
          params.psd = md5(params.psd);
          params.psd_confirm = md5(params.psd_confirm);
          psdUpdate(params).then(res => {
            if (res.data.ret == 0) {
              this.$message({
                message: "修改成功",
                type: "success"
              });
              this.psd.show = false;
            }
          });
        } else {
          console.log("invalid");
        }
      });
    },
    submitForm(formName) {
      console.log(1);
      this.$refs[formName].validate(valid => {
        if (valid) {
          //   alert("submit");
          if (this.addForm.title == "新增") {
            this.userAdd();
          } else if (this.addForm.title == "编辑") {
            this.userEdit();
          }
        } else {
          console.log("参数错误");
          return false;
        }
      });
    },
    edit(index, row) {
      row.isEdit = true;
      row._remark = row.remark;
    },
    save(index, row) {
      row.isEdit = false;
      let params = {
        id: row.id,
        remark: row._remark
      };
      remarkUpdate(params).then(res => {
        if (res.data.ret == 0) {
          this.$message({
            message: "修改成功",
            type: "success"
          });
          row.remark = row._remark;
          this.getUserList();
        }
      });
    },
    cancel(index, row) {
      row.isEdit = false;
    },
    handlePsdUpdate(index, row) {
      this.psd_update.show = true;
      this.psd.id = row.id;
      // psdUpdate()
    }
  },
  computed: {
    backupForm() {
      return { ...this.form };
    }
  }
};
</script>

<style lang="less" scoped>
@import url("./useradmin.less");
</style>

