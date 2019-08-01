<template>
    <div class="sale-plan top-border">
        <!-- 搜索 -->
        <z-search :searchItems="searchItems" @search="search"></z-search>
        <!-- 功能+table -->
        <z-table :tableData="tableData" :tableColumns="tableColumns" :page="page" :funcs="funcs" @func="func" @handleCurrentChange="handleCurrentChange" v-loading="tableLoading"></z-table>
        <!-- 新增/编辑dialog -->
        <el-dialog :title="dialogTitle" :visible.sync="dialogVisible">
            <el-form :model="dialogData" ref="ruleForm" label-width="100px" class="demo-ruleForm">
                <el-form-item label="方案名" prop="pro_name">
                    <el-input v-model="dialogData.pro_name"></el-input>
                </el-form-item>
                <el-row v-for="(item, index) in dialogData.schemalist" :key="item.id">
                    <el-col :span="10">
                        <el-form-item label="水量名" prop="name">
                            <el-input v-model="item.name"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="10">
                        <el-form-item label="水量" prop="water_amount">
                            <el-input v-model.number="item.water_amount" style="width: 70%;"></el-input> ML
                        </el-form-item>
                    </el-col>
                    <el-col :span="2">
                        <el-button @click="addItem(index)">+</el-button>
                    </el-col>
                    <el-col :span="2" v-if="index !== 0">
                        <el-button @click="delItem(index)">-</el-button>
                    </el-col>
                </el-row>
                <el-form-item>
                    <el-button @click="submitForm('ruleForm')">保存</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
        <!-- <el-dialog :title="dialogTitle" :visible.sync="dialogVisible">
            <el-form :model="dialogData" ref="ruleForm" label-width="100px" class="demo-ruleForm">
                <el-form-item label="设备编号：" prop="device_id">
                    <el-input v-model="dialogData.device_id" :disabled="dialogTitle === '编辑'"></el-input>
                </el-form-item>
                <el-form-item label="IMEI：" prop="imei">
                    <el-input v-model="dialogData.imei"></el-input>
                </el-form-item>
                <el-form-item label="地址：" prop="address">
                    <el-input v-model="dialogData.address"></el-input>
                </el-form-item>
                <el-form-item label="备注：" prop="remark">
                    <el-input v-model="dialogData.remark"></el-input>
                </el-form-item>
                <el-form-item label="地理位置：" prop="pt">
                    <div id="map" style="width: 100%;height: 300px;"></div>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="submitForm('ruleForm')">保存</el-button>
                    <el-button @click="cancel()">取消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog> -->
    </div>
</template>

<script>
import ZTable from "@/components/z-table/z-table";
import ZSearch from "@/components/z-search/z-search";
import { getList, editDetail, add, del } from "@/service/api/sale-plan.js";
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
      }
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
      this.dialogData = {
        schemalist: [{}]
      };
      this.dialogVisible = true;
    },
    edit(row) {
      this.dialogTitle = "编辑";
      this.dialogData = { ...row };
      this.dialogVisible = true;
      if (
        !this.dialogData.schemalist ||
        this.dialogData.schemalist.length === 0
      ) {
        this.dialogData.schemalist = [{}];
      }
    },
    del(row) {
      console.log(row);
      this.$confirm("此操作将永久删除该条数据, 是否继续?", "提示", {
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
    handleCurrentChange(val) {
      this.page.currentPage = val;
      this.getList();
    },
    submitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          const params = this.dialogData;
          params.water_amount = params.schemalist
            .map(item => item.water_amount)
            .join(",");
          if (this.dialogTitle === "编辑") {
            editDetail(params).then(res => {
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
    },
    addItem(index) {
      this.dialogData.schemalist.splice(index + 1, 0, {});
    },
    delItem(index) {
      this.dialogData.schemalist.splice(index, 1);
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

<style lang="less" scoped>
.sale-plan {
  padding: 10px;
  margin: 10px;
}
</style>
