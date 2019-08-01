<template>
  <div class="user">
    <search-form :searchParams="searchData.searchParams" :searchItem="searchData.searchItem" @search="filterSearch" @add="add" :needSearch="false" :needAdd="true"></search-form>
    <crud ref="table" :tableItems="tableItems" :tableData="tableData" :table="table" :pageInfo="pageInfo" @search="search" @del="del" @edit="edit"></crud>

    <el-dialog :title="dialogAddForm.title" :visible.sync="dialogAddForm.visible">
      <my-form @submit="submit" :form="addForm.form" :rules="addForm.rules" :formItems="addForm.formItems" :btn="addForm.btn"></my-form>
    </el-dialog>
  </div>
</template>

<script>
import md5 from "md5";
import SearchForm from "@/components/searchForm/searchForm";
import crud from "@/components/crud/crud";
import MyForm from "@/components/form/form";
import { addForm, searchData, tableItems } from "./data";
import { getDevList, editDevDetail } from "@/service/api/agent";
import {
  addAgent,
  deleteAgent,
  updateAgent,
  getAgentList
} from "@/service/api/agent";

export default {
  name: "user",
  data() {
    return {
      addForm,
      searchData,
      password: "",
      table: {
        loading: false
      },
      tableItems,
      pageInfo: {
        totalItems: 0,
        page_size: 10,
        page: 1
      },
      tableData: [],
      dialogAddForm: {
        visible: false,
        title: "新增"
      },
      backUpData: {},
      editRowData: {}
    };
  },
  methods: {
    search() {
      this.table.loading = true;
      // 查询列表
      let params = {
        parent_id: sessionStorage.getItem("parent_id"),
        ...this.pageInfo
      }; //组织params，然后做ajax保存
      delete params.totalItems;
      getAgentList(params).then(
        res => {
          console.log(res.data.datas.list)
          this.table.loading = false;
          this.tableData = res.data.datas.list;
          // this.tableData = this.tableData.filter(item => {
          //   if(item.parent_id === item.sub_id){ // 屏蔽parent_id和sub_id相等的那条数据
          //     res.data.datas.itemCounts--;
          //     return false
          //   }
          //   return true
          // })
          this.pageInfo.totalItems = res.data.datas.itemCounts;
        },
        error => {
          this.table.loading = false;
        }
      );
    },
    filterSearch() {
      this.pageInfo.page = 1;
      this.search();
    },
    del(row) {
      this.table.loading = true;
      let params = {
        id: row.id
      };
      deleteAgent(params).then(res => {
        if(res.data.ret === 0){
          this.$message({
            message: '删除完成',
            duration: 5 * 1000,
            type: 'success'
          })
        }
        this.table.loading = false;
        this.search();
      });
    },
    add() {
      this.addForm.btn = { text: "保存", loading: false };
      this.addForm.form = JSON.parse(JSON.stringify(this.backUpData.form));
      this.addForm.rules = { ...this.backUpData.rules };
      this.dialogAddForm = {
        visible: true,
        text: "新增"
      };
    },
    edit(row) {
      this.password = JSON.parse(JSON.stringify(row.password));
      this.addForm.btn = { text: "保存", loading: false };
      this.addForm.form = { ...row };
      this.addForm.rules = {
        brandname: [
          { required: true, message: "请填写用户名", trigger: "blur" }
        ],
        username: [{ required: true, message: "请填写用户名", trigger: "blur" }]
      };
      this.dialogAddForm = {
        visible: true,
        text: "编辑"
      };
      this.editRowData = row;
    },
    submit() {
      // 点击确认新增按钮
      this.addForm.btn.loading = true;
      let params = {
        parent_id: sessionStorage.getItem("parent_id"),
        brandname: this.addForm.form.brandname,
        username: this.addForm.form.username,
        password: md5(this.addForm.form.password)
      };
      if (this.dialogAddForm.text === "新增") {
        addAgent(params).then(
          res => {
            if(res.data.ret === 207){
              this.$message({
                message: '该商户名已存在',
                duration: 5 * 1000,
                type: 'error'
              })
              this.addForm.btn.loading = false;
              return
            }
            this.addForm.btn.loading = false;
            this.dialogAddForm = false;
            this.search();
            this.addForm.form = { ...this.backUpData.form }; // 关闭dialog时重置表单数据
          },
          err => {
            this.addForm.form = { ...this.backUpData.form }; // 关闭dialog时重置表单数据
          }
        );
      } else {
        let _password = "";
        if (this.addForm.form.password !== this.password) {
          // 如果密码修改了，就md5一下
          this.addForm.form.password = md5(this.addForm.form.password);
        }
        updateAgent(this.addForm.form).then(res => {
          if(res.data.ret === 0){
            this.$message({
              message: '修改成功',
              duration: 5 * 1000,
              type: 'success'
            })
          }else {
            this.$message({
              message: res.data.msg,
              duration: 5 * 1000,
              type: 'error'
            })
          }
          this.addForm.btn.loading = false;
          this.dialogAddForm = false;
          this.search();
          this.addForm.form = { ...this.backUpData.form }; // 关闭dialog时重置表单数据
        });
      }
    }
  },
  mounted() {
    this.backUpData.form = { ...this.addForm.form };
    this.backUpData.formItems = [...this.addForm.formItems];
    this.backUpData.rules = { ...this.addForm.rules };
    // this.search();
  },
  components: {
    crud,
    SearchForm,
    MyForm
  }
};
</script>

<style lang="less" scoped>
@import url("./user.less");
</style>
