<template>
  <!-- <div class="banner-editer top-border" style="overflow: hidden;"> -->
    <tinymce :height="300" v-model="content" />
  <!-- </div> -->
</template>

<script>
import Tinymce from "@/components/Tinymce/";
export default {
  components: { Tinymce },
  data() {
    return {
      content: null,
      fileList: []
    };
  },
  mounted() {},
  methods: {
    submitUpload() {
      this.$refs.upload.submit();
    },
    handleRemove(file, fileList) {},
    handlePreview(file) {
      console.log(file);
    }
  }
};
function getObjectURL(file) {
  var url = null;
  if (window.createObjectURL != undefined) {
    url = window.createObjectURL(file);
  } else if (window.URL != undefined) {
    // mozilla(firefox)
    url = window.URL.createObjectURL(file);
  } else if (window.webkitURL != undefined) {
    // webkit or chrome
    url = window.webkitURL.createObjectURL(file);
  }
  return url;
}
</script>

<style lang="less" scoped>
@import url("./index.less");
</style>
