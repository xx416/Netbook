<template>
  <div>
    <el-row :gutter="20" style="margin: 20px 0px">
      <el-col :span="6">
        <el-card class="box-card" style="height: 100%;display: flex">
          <img :src="`${storeBook.baseURL}${goodInfo.image}`" style="max-width: 100%;height: 100%;object-fit: cover;"/>
        </el-card>
      </el-col>
      <el-col :span="18">
        <el-card class="box-card">
          <el-row align="" :gutter="20">
            <el-col>
              <el-text style="font-size: 24px;font-weight: bold;color: black">{{ goodInfo.bookName }}</el-text>
            </el-col>
            <el-col>
              <el-text class="mx-1" size="small">月销{{ goodInfo.sales }}+</el-text>
            </el-col>
            <el-col>
              <el-row style="margin: 10px 0px">
                <el-col :span="6"><span>作者：{{ goodInfo.author }}</span></el-col>
                <el-col :span="6"><span>出版社：{{ goodInfo.pressName }}</span></el-col>
                <el-col :span="6"><span>出版时间：{{ goodInfo.addTime }}</span></el-col>
              </el-row>
            </el-col>
            <el-col style="margin: 10px 0px"><span>库存：{{ goodInfo.stock }}</span></el-col>
            <el-col style="margin: 10px 0px"><span>运费：￥{{ goodInfo.transit }}</span></el-col>
            <el-col style="margin: 10px 0px">
              <span>售价：</span>
              <span style="color: red;font-size: 24px">￥{{ goodInfo.price }}</span>
            </el-col>
          </el-row>
          <el-col style="margin-top: 20px">
            <div>
              <el-button type="primary" @click="pay()">立即购买</el-button>
              <el-button @click="joinCard()">加入购物车</el-button>
            </div>
          </el-col>
        </el-card>
      </el-col>
    </el-row>
    <el-card>
      <div>
        <el-breadcrumb separator="/">
          <el-breadcrumb-item :to="{ path: '/' }">商城</el-breadcrumb-item>
          <el-breadcrumb-item>商品详情</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
      <div>
        <el-empty :image-size="200" />
      </div>
    </el-card>
  </div>
</template>

<script setup>
import {reactive, ref} from 'vue'
import {useBookStore, useGoodCarStore} from "@/store";
import {ElMessage} from "element-plus";
import {useRoute} from "vue-router";
import {getListById} from "@/net/book";
import {unauthorized} from "@/net";

let goodInfo = ref({})
getListById({id: useRoute().params.id})
    .then((res) =>{
      goodInfo.value = res.data.data
    })

const store = useGoodCarStore();
const storeBook = useBookStore();
// 点击 添加购物车按钮
const handleClick = () => {
  console.log("加入购物车", goodInfo.value);
  //   store.$patch({
  //     count: store.count + 1,
  //   });
  store.increment(goodInfo.value);
  ElMessage({
    message: '加入购物车成功',
    type: 'success',
  })
  store.saveCard()
  console.log("store.count:", store.count);
}


function joinCard(){
  if (!unauthorized()){
    handleClick()
  }else{
    ElMessage.warning("亲，请登录");
  }
}

function pay(){
  if (!unauthorized()){
    handleClick()
    store.selectGoodCar(goodInfo.value)
    store.updateSwitch()
  }else{
    ElMessage.warning("亲，请登录");
  }
}

</script>

<style scoped>

</style>