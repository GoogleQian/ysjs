<template>
    <el-form :model="formData" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <slot name="form"></slot>
        
        
    </el-form>
</template>

<script>
export default {
  props: {
    formData: {
      type: Object,
      default: function() {
        return {};
      }
    },
    labelWidth: {
      type: String,
      default: "100px"
    }
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          alert("submit!");
          this.$emit('submit', {...this.formData})
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    }
  }
};
</script>

<style>
</style>
