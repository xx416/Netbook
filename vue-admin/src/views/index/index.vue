<template>
  <div>
    <div class="common-layout">
      <el-container>
        <el-aside width="200px">
          <h4 class="mb-2" style="text-align: center;">商品后台（开发中）</h4>
          <el-menu
              default-active="1"
              :router="router"
              class="el-menu-vertical-demo"
              @open="handleOpen"
              @close="handleClose"
          >
            <el-menu-item index="basic">
              <el-icon><icon-menu /></el-icon>
              <span>基本信息</span>
            </el-menu-item>
            <el-menu-item index="book">
              <el-icon><document /></el-icon>
              <span>商品管理</span>
            </el-menu-item>
            <el-menu-item index="order">
              <el-icon><document /></el-icon>
              <span>订单管理</span>
            </el-menu-item>
            <el-menu-item index="tag">
              <el-icon><document /></el-icon>
              <span>标签管理</span>
            </el-menu-item>
            <el-menu-item index="press">
              <el-icon><document /></el-icon>
              <span>出版社管理</span>
            </el-menu-item>
            <el-menu-item index="news">
              <el-icon><setting /></el-icon>
              <span>推送管理</span>
            </el-menu-item>
            <el-menu-item index="userinfo">
              <el-icon><setting /></el-icon>
              <span>用户管理</span>
            </el-menu-item>
            <el-menu-item index="log">
              <el-icon><setting /></el-icon>
              <span>系统日志</span>
            </el-menu-item>
          </el-menu>
        </el-aside>
        <el-main>
          <router-view/>
        </el-main>
      </el-container>
    </div>
  </div>
</template>

<script setup>
import {
  Document,
  Menu as IconMenu,
  Location,
  Setting,
} from '@element-plus/icons-vue'
import {ref} from "vue";

const router = ref(true)
const handleOpen = (key,keyPath) => {
  console.log(key,keyPath)
}
const handleClose = (key,keyPath) => {
  console.log(key, keyPath)
}
</script>

<style scoped>
</style>