<template>
  <el-table
      ref="multipleTableRef"
      :data="carObj.carArr"
      border
      style="width: 100%"
      @selection-change="handleSelectionChange"
  >
    <el-table-column type="selection" width="45" />
<!--    <el-table-column prop="id" label="商品id" width="180" />-->
    <el-table-column prop="image" label="商品图片" width="180" >
      <template v-slot="scope">
        <img :src="`${storeBook.baseURL}${scope.row.image}`" alt="" style="width: 100px;height: 100px">
      </template>
    </el-table-column>
    <el-table-column prop="bookName" label="商品名称" width="180" />
    <el-table-column prop="price" label="单价" width="100"/>
    <el-table-column label="购买数量" width="150">
      <template #default="scope">
        <el-input-number
            size="small"
            v-model.number="scope.row.num"
            :min="1"
            :max="scope.row.stock"
            :precision="0"
        />
      </template>
    </el-table-column>
    <el-table-column label="总价" width="100" >
      <template #default="scope">
        {{ scope.row.price * scope.row.num}}
      </template>
    </el-table-column>
    <el-table-column fixed="right" label="操作">
      <template #default="scope">
        <el-button type="danger" @click="del(scope.row.id)">删除</el-button>
      </template>
    </el-table-column>
  </el-table>

  <div class="sum-container">
    共<span class="weight-text">{{ store.totalNum }}</span>件商品，共
    <span class="weight-text">{{ store.totalPrice }}</span>元
    <el-button type="success" style="width: 80px;" @click="updateState()">结 算</el-button>
  </div>
</template>

<script setup>
import { reactive, onMounted, ref, watch, nextTick } from "vue";
import {useBookStore, useGoodCarStore} from "../../store/index";
import { ElMessage, ElMessageBox } from 'element-plus'
import router from "@/router";

const store = useGoodCarStore();
const storeBook = useBookStore();
const multipleTableRef = ref(null);

//默认全选
nextTick(() =>{
  multipleTableRef.value.toggleAllSelection()
})

// 更新状态
const updateState = () => {
  if (store.selectListArr.length > 0){
    store.switch = false;
    router.push('/order')
  }else {
    ElMessage.warning("请选中商品")
  }
};


onMounted(() => {
  if (carObj.carArr.length > 0 && store.selectListArr.length > 0) {
    let idsArr = store.selectListArr.map((item) => item.id);
    //之前购物车中选中的内容的回显
    carObj.carArr.map((item) => {
      if (idsArr.includes(item.id)) {
        multipleTableRef.value.toggleRowSelection(item, true);
      } else {
        multipleTableRef.value.toggleRowSelection(item, false);
      }
    });
  }
});

//监听仓库变化，变化一次保存至数据库一次
watch(store.listArr, () => {
      store.saveCard()
})


const carObj = reactive({
  carArr: [],
});

carObj.carArr = store.listArr; //购物车数据

// 选择购物车中的商品
const handleSelectionChange = (val) => {
  store.selectGoodCar(val);
};

//删除商品按钮
const del = (id) => {
  ElMessageBox.confirm(
      '确定移除该商品吗？',
      '注意',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }
  )
      .then(() => {
        store.delGood(id-1)
        store.saveCard()
        ElMessage({
          type: 'success',
          message: '删除成功',
        })
      })
      .catch(() => {
        ElMessage({
          type: 'info',
          message: '取消删除',
        })
      })
}
</script>

<style>
.sum-container {
  padding-top: 10px;
  text-align: right;
}
.weight-text {
  font-weight: 600;
  font-size: 26px;
}
</style>