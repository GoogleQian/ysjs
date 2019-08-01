<template>
    <div class="upload">
        <input id="File" type="file" style="display:none" :ref="fileId" :accept="accept" :multiple="multiple" @change="upload($event)" />
        <el-button size="mini" style="width:100px"  @click="chooseFile(fileId)">{{btnText}}</el-button>
    </div>
</template>

<script>
export default {
  props: {
    fileId: {
      default: "fileInput",
      type: String
    },
    accept: {
      type: String,
      default: "image/jpeg,image/jpg,image/png"
    },
    multiple: {
      default: false,
      type: Boolean
    },
    width: {
      default: 1200,
      type: Number
    },
    height: {
      default: 679,
      type: Number
    },
    checkImg: {
      default: true,
      type: Boolean
    },
    btnText: {
      default: "选择图片"
    }
  },
  methods: {
    chooseFile(i) {
      this.$refs[i].click();
    },
    upload($event) {
        // formData.append('banner', $event.target.files[0]);
        this.$emit('_upload', $event.target.files[0]);
    }
  }
};
</script>

<style>
</style>
