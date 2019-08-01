// 可用于条件查询
// 接受props needAdd（是否包含新增功能）、searchParams(查询参数)、searchItem(查询条目)
// 向上传递查询事件和新增事件
<template>
  <div class="searchForm">
    <el-form :inline="true" :model="searchParams" class="demo-form-inline">
      <el-form-item v-for="item in searchItem" :key="item.label" :label="item.label">
        <!-- 普通文本框 type === text -->
        <el-input v-if="item.type==='text'" v-model="searchParams[item.value]" :placeholder="item.placeholder"></el-input>
        <!-- select -->
        <el-select v-if="item.type==='select'" v-model="searchParams[item.value]" :placeholder="item.placeholder || '请选择'">
          <el-option v-for="option in item.options" :key="option.value" :label="option.label" :value="option.value"></el-option>
        </el-select>
        <!-- 日期选择器 type === 'date' -->
        <el-date-picker value-format="timestamp" v-if="item.type==='date'" v-model="searchParams[item.value]" type="date" placeholder="选择日期"></el-date-picker>
        <!-- 日期范围选择器 type === 'daterange' -->
        <el-date-picker value-format="timestamp" v-if="item.type==='daterange'" v-model="searchParams[item.value]" type="daterange" align="right" unlink-panels range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
        <!-- 日期范围选择器 含时分秒 type === 'datetimerange' -->
        <el-date-picker value-format="timestamp" v-if="item.type==='datetimerange'" v-model="searchParams[item.value]" type="datetimerange" align="right" unlink-panels range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button @click="onSearch" v-if="needSearch">查询</el-button>
        <el-button @click="onAdd" v-if="needAdd">新增</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  name: "searchForm",
  data() {
    return {};
  },
  methods: {
    onSearch() {
      this.$emit("search", { ...this.searchParams });
    },
    onAdd() {
      this.$emit("add");
    }
  },
  props: {
    needAdd: Boolean,
    needSearch: {
      type: Boolean,
      default: true
    },
    searchParams: {
      type: Object,
      default: function() {
        return {};
      }
    },
    searchItem: {
      type: Array,
      default: function() {
        return [];
      }
    }
  }
};
</script>

<style lang="less" scoped>
@import url("./searchForm.less");
</style>
