<template>
  <div class="div_1">
    <el-card style="height: auto;width: 300px;">
      <el-form :model="form" :rules="rules" ref="formRef">
        <el-form-item>
          <el-text style="margin-left: auto;margin-right: auto">商品后台管理</el-text>
        </el-form-item>
        <el-form-item label="账号：" prop="username">
          <el-input v-model="form.username"></el-input>
        </el-form-item>
        <el-form-item label="密码："  prop="password">
          <el-input type="password" v-model="form.password"></el-input>
        </el-form-item>
        <el-button type="success" style="width: 100%;" @click="userLogin()" plain>登录</el-button>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import {reactive, ref} from "vue";
import {login} from "@/net";
import router from "@/router";
import {ElMessage} from "element-plus";

const formRef = ref()
const form = reactive({
  username: '',
  password: '',
  remember: false
})

const rules = {
  username: [
    { required: true, message: '请输入用户名' }
  ],
  password: [
    { required: true, message: '请输入密码'}
  ]
}

function userLogin() {
  formRef.value.validate((isValid) => {
    if(isValid) {
      login(form.username, form.password, form.remember, () => {
        router.push("/")
      })
    }
  });
}

</script>

<style scoped>
.div_1 {
  height: 80vh;
  display: flex;
  justify-content: center;
  align-items: center;
}


</style>