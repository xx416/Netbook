<template>
  <div style="height: 1080px">
    <el-result
        icon="success"
        title="支付成功 !"
        sub-title="3秒后自动返回"
    >
      <template #extra>
        <el-button type="primary" @click="router.push('/')">返回</el-button>
      </template>
    </el-result>
  </div>
</template>

<script setup>
import router from "@/router";

setTimeout(()=>{
  router.push('/')
},3000)

</script>

<style lang="scss" scoped>

</style>