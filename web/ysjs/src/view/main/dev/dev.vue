<template>
  <div class="dev top-border">
    <!-- 搜索 -->
    <div style="overflow: hidden">
      <z-search style="float: left" :searchItems="searchItems" @search="search"></z-search>
      <el-button style="float:right;margin-left: 10px;" type="success" @click="getList">刷新</el-button>
      <el-button style="float:right" @click="printTable">打印表格</el-button>
    </div>
    <!-- 功能+table -->
    <z-table :tableData="tableData" :tableColumns="tableColumns" :page="page" :funcs="funcs" @func="func" @handleCurrentChange="handleCurrentChange" v-loading="tableLoading"></z-table>
    <!-- <el-dialog :title="repairDialog.title" :visible.sync="repairDialog.visible">
      <el-select v-model="repairId">
        <el-option v-for="item in allRepairs" :label="item.loginName" :value="item.id" :key="item.id"></el-option>
      </el-select>
      <el-form style="margin-top: 20px;">
        <el-form-item class="demo-ruleForm">
          <el-button @click="confirmRepairSet" type="primary">确定</el-button>
          <el-button @click="cancelRepairSet">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog> -->
    <!-- 新增/编辑dialog -->
    <el-dialog :title="dialogTitle" :visible.sync="dialogVisible">
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
    </el-dialog>
  </div>
</template>

<script>
import QRCode from "qrcode";
import { _initBdMap, _addOverLay, _MarkerClusterer } from ".././bdmap/bdmap.js";
import BMap from "BMap";
import BMapLib from "BMapLib";
import ZTable from "@/components/z-table/z-table";
import ZSearch from "@/components/z-search/z-search";
import {
  getDevList,
  editDevDetail,
  addDev,
  delDev,
  allotRepairer,
  getAllRepairs
} from "@/service/api/devList";
import { getModel } from "@/service/api/set";
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
      dialogData: {},
      allRepairs: [],
      repairDialog: {
        title: "分配维修员",
        visible: false
      },
      ids: null,
      repairId: null
    };
  },
  created() {
    this.getList();
    this.$store.dispatch("setMenus");
  },
  methods: {
    getList() {
      this.tableLoading = true;
      const params = { page: this.page.currentPage, ...this.searchForm };
      getDevList(params).then(res => {
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
        case "商户管理":
          this.merchant(row);
          break;
        case "通道管理":
          this.pip(row);
          break;
        case "参数设置":
          this.set(row);
          break;
        case "设备二维码":
          this.qrCode(row);
          break;
        case "手动操作":
          this.devCtrl(row);
          break;
        case "恢复默认设置":
          this.defaultSet(row);
          break;
        case "机型设置":
          this.modelSet(row);
          break;
        case "系统参数设置":
          this.sysSet(row);
          break;
        case "定时启动和取消":
          this.timingSet(row);
          break;
        case "用户参数设置":
          this.userSet(row);
          break;
        case "分配维修员":
          this.setRepair(row);
          break;
      }
    },
    printTable() {
      console.log(this.tableColumns)
      this.$router.push({
        name: "打印",
        params: {
          tableColumns: this.tableColumns,
          tableData: this.tableData,
          componentWidth: 1200
        }
      });
    },
    add() {
      this.dialogTitle = "新增";
      this.dialogData = {};
      this.dialogVisible = true;
      this.$nextTick(() => {
        this.showMap();
      });
    },
    setRepair(row) {
      // 获取所有维修员
      this.getAllRepairs();
      this.repairDialog.visible = true;
      this.ids = row.map(item => item.id);
      // allotRepairer({
      //   repairId: this.repairId,
      //   params: { devIds: ids.join(",") }
      // }).then(res => {});
    },
    confirmRepairSet() {
      allotRepairer({
        repairId: this.repairId,
        params: { devIds: this.ids.join(",") }
      }).then(res => {
        if (res.data.ret === 0) {
          this.$message({
            message: "分配成功",
            type: "success"
          });
          this.repairDialog.visible = false;
        }
      });
    },
    cancelRepairSet() {
      this.repairDialog.visible = false;
    },
    getAllRepairs() {
      getAllRepairs().then(res => {
        this.allRepairs = res.data.datas;
      });
    },
    edit(row) {
      this.dialogTitle = "编辑";
      this.dialogData = { ...row };
      this.dialogVisible = true;
      this.$nextTick(() => {
        this.showMap(this.dialogData.lng, this.dialogData.lat);
      });
    },
    del(row) {
      console.log(row);
      this.$confirm("此操作将永久删除该设备, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          delDev({ id: row.id }).then(res => {
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
    merchant(row) {
      this.$router.push({
        name: "merchant",
        query: { user_id: row.user_id, id: row.id }
      });
    },
    pip(row) {
      this.$router.push({
        name: "pip",
        query: { device_id: row.device_id, id: row.id }
      });
    },
    set(row) {
      this.$router.push({
        name: "set-panel",
        query: { device_id: row.device_id, id: row.id }
      });
    },
    devCtrl(row) {
      this.$router.push({
        name: "dev-ctrl",
        query: {
          device_id: row.device_id,
          id: row.id,
          machine_status: row.machine_status
        }
      });
    },
    defaultSet(row) {
      this.$router.push({
        name: "default-set",
        query: {
          device_id: row.device_id,
          id: row.id
        }
      });
    },
    modelSet(row) {
      this.$router.push({
        name: "model-set",
        query: {
          device_id: row.device_id,
          id: row.id
        }
      });
    },
    sysSet(row) {
      console.log("sysset");
      this.$router.push({
        name: "sys-set",
        query: {
          device_id: row.device_id,
          id: row.id
        }
      });
    },
    timingSet(row) {
      this.$router.push({
        name: "timing-set",
        query: {
          device_id: row.device_id,
          id: row.id
        }
      });
    },
    userSet(row) {
      this.$router.push({
        name: "user-set",
        query: {
          device_id: row.device_id,
          id: row.id
        }
      });
    },
    qrCode(row) {
      QRCode.toDataURL(
        `http://iotsvr.he-live.com/wx_pay/index.html#${row.device_id}`
      ).then(res => {
        this.$confirm(`<img src="${res}" width="100%"/>`, `${row.device_id}`, {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          dangerouslyUseHTMLString: true
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
          if (this.dialogTitle === "编辑") {
            editDevDetail({ ...this.dialogData }).then(res => {
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
            addDev({ ...this.dialogData }).then(res => {
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
    cancel() {
      this.dialogVisible = false;
    },
    showMap(lng = "", lat = "") {
      const map = _initBdMap({
        domId: "map",
        pt: [lng, lat],
        maxzoom: 18,
        minzoom: 5,
        zoom: 15
      });
      if (lng != "" && lat != "") {
        let point = new BMap.Point(lng, lat);
        let marker = new BMap.Marker(point);
        map.addOverlay(marker);
      }
      map.addEventListener("click", e => {
        map.clearOverlays();
        let marker = new BMap.Marker(new BMap.Point(e.point.lng, e.point.lat));
        this.dialogData.lng = e.point.lng;
        this.dialogData.lat = e.point.lat;
        map.addOverlay(marker);
      });
    }
  },
  computed: {
    funcs() {
      // if (this.$store.state.user.useragent === "pc") {
      // console.log(this.$store.state.user.funcs)
      return this.$store.state.user.funcs;
      // } else {
      // return ["参数设置", "通道管理", "手动操作"];
      // }
    }
  },
  mounted() {
    if (this.$store.state.user.useragent === "phone") {
      this.tableColumns = [
        { label: "设备编号", value: "device_id", width: "150" },
        { label: "备注", value: "remark" }
      ];
    }
  },
  components: { ZTable, ZSearch }
};
</script>

<style lang="less" scoped>
@import "./index.less";
</style>
