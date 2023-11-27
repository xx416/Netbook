<template>
  <div>
    <el-row justify="space-around">
      <el-col
          v-for="(items, index) in store.list"
          :key="index"
          :span="5"
          @click="router.push('bookInfo/'+items.bookId)"
      >
        <el-card :body-style="{ padding: '0px'}" style="width: 220px;height: 300px">
          <div style="height: 100%;width: 100%;display: flex;flex-direction: column;">
            <div style="height: 220px;justify-content: center;align-items: center;">
              <img :src="`${store.baseURL}${items.image}`" style="max-width: 100%;height: 100%;object-fit: cover;">
            </div>
            <div style="height: 80px;box-sizing: border-box;text-align: left;padding: 5px 8px 2px 8px;border-top: solid 1px #b4b2b2">
              <p class="text">{{items.bookName}}</p>
              <div style="line-height: 35px">
                <el-text style="float: left;color: red;font-size: 20px">￥{{ items.price }}</el-text>
                <el-text size="small" style="float: right">月销{{ items.sales }}+</el-text>
              </div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-pagination background layout="prev, pager, next"
                   v-model:current-page.sync="cPage"
                   :total="store.countPage.totalCount"
                   :page-size="store.countPage.pageSize"
                   :page-count="store.countPage.pageCount"
    />
  </div>
</template>

<script setup>

import { ref, reactive, onMounted, onUpdated, watch } from 'vue'
import router from "@/router"
import {useBookStore} from "@/store";
import {getPageList, getPagination} from "@/net/book";

const store = useBookStore();
onMounted(() => {
  store.list = []
  const pageSize = store.countPage.pageSize;
  const currentPage = store.countPage.currentPage;
  const tagName = store.selectValue;
  getPagination({pageSize}).then(res =>{
    store.countPage.tableData = res.data.data.tableData
    store.countPage.pageCount = res.data.data.pageCount
    store.countPage.totalCount = res.data.data.totalCount
  })
  getPageList({pageSize,currentPage,tagName}).then(res =>{
    store.list = res.data.data
  })
})

const cPage = ref(1);

watch(cPage,() =>{
  store.countPage.currentPage = cPage.value
  store.list = []
  const pageSize = store.countPage.pageSize;
  const currentPage = cPage.value;
  const tagName = store.selectValue;
  getPageList({pageSize,currentPage,tagName}).then(res =>{
    store.list = res.data.data
  })
})
watch(store.selectValue,() => {
  console.log(store.selectValue)
})

</script>

<style scoped>

.el-row {
  margin-bottom: 20px;
}

.el-col {
  margin-bottom: 20px;
  border-radius: 4px;
  cursor: pointer;
}

.el-pagination{
  display: flex;
  justify-content: center;
}

.text{
  margin: 0;
  height: 45px;
  font-size: 16px;
  color: black;
  overflow:hidden;
  text-overflow:ellipsis;
  display:-webkit-box;
  -webkit-box-orient:vertical;
  -webkit-line-clamp:2;
}

</style>