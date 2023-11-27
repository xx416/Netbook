<template>
  <div>
      <el-card class="box-card" style="">
        <el-row style="margin-top: 20px;margin-bottom: 20px;">
          <el-col>
            <el-text style="font-size: 24px">确认订单：</el-text>
          </el-col>
        </el-row>
        <div v-for="item in goods">
          <el-card style="margin-bottom: 10px">
            <el-row>
              <el-col :span="4" style="text-align: center">
                <img :src="`${storeBook.baseURL}${item.image}`" style="max-width: 140px;max-height: 140px;object-fit: cover;"/>
              </el-col>
              <el-col :span="20">
                <el-col style="margin: 10px 0px"><span>{{item.bookName}}</span></el-col>
                <el-col style="margin: 10px 0px"><el-text type="info">运费：{{item.transit}}</el-text></el-col>
                <el-col style="margin: 10px 0px"><el-text type="info">购买数：{{item.num}}</el-text></el-col>
                <el-col style="margin: 10px 0px">
                  <div style="text-align: right">
                    <span style="font-size: 18px">合计：</span>
                    <span style="color: red;font-size: 18px">￥{{item.price * item.num + item.transit}}</span>
                  </div>
                </el-col>
              </el-col>
            </el-row>
          </el-card>
        </div>
        <div style="text-align: right;margin-top: 20px">
          <el-row>
            <el-col :span="6">
              <el-form-item label="收货地址：">
                <el-select
                    v-model="form.addr"
                    placeholder="选择地址"
                >
                  <el-option v-for="item in form.addrList" :label="item.addr" :value="item.addr" loading>
                    <span style="float: left">{{ item.addressee }}</span>
                    <span style="float: right;color: var(--el-text-color-secondary);font-size: 13px;">{{ item.addr }}</span>
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="2">
              <el-button @click="dialogFormVisible = true">
                + 添加地址
              </el-button>
            </el-col>
            <el-col :span="16">
              <el-button type="" style="width: 90px" round plain @click="router.push('/')">取 消 订 单</el-button>
              <el-button type="success" style="width: 90px" round plain @click="confirm()">付 款</el-button>
            </el-col>
          </el-row>
        </div>
      </el-card>

    <el-dialog v-model="dialogFormVisible" title="添加地址">
      <el-form :model="form.formAddr" :rules="rules" ref="formRef">
        <el-form-item label="收件人" :label-width="formLabelWidth" prop="addressee">
          <el-input v-model="form.formAddr.addressee" autocomplete="off" />
        </el-form-item>
        <el-form-item label="手机号" :label-width="formLabelWidth" prop="phone">
          <el-input v-model="form.formAddr.phone" autocomplete="off" />
        </el-form-item>
        <el-form-item label="详细地址" :label-width="formLabelWidth" prop="addr">
          <el-input v-model="form.formAddr.addr" autocomplete="off" />
        </el-form-item>
      </el-form>
      <template #footer>
                  <span class="dialog-footer">
                    <el-button @click="dialogFormVisible = false">关闭</el-button>
                    <el-button type="primary" @click="submit()">
                      提交
                    </el-button>
                  </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import {reactive, ref, onMounted} from "vue"
import {useBookStore, useGoodCarStore} from "@/store";
import router from "@/router";
import {getListByUid, save} from "@/net/addr";
import {ElMessage} from "element-plus";
import {confirmOrder} from "@/net/order";

const dialogFormVisible = ref(false)
const formLabelWidth = '140px'

const store = useGoodCarStore()
const storeBook = useBookStore()
const goods = store.selectListArr
const formRef = ref()
const form = ref({
  addrList:[],
  formAddr:{
    addressee:'',
    addr:'',
    phone:''
  },
  addr:''
})

const rules = reactive({
  addressee:[
    { required: true, message: '请输入名字', trigger: 'blur' }
  ],
  addr:[
    { required: true, message: '请输入地址', trigger: 'blur' }
  ],
  phone:[
    { required: true, trigger: 'blur', message: '请输入11位手机号'},
    { required: true, trigger: 'blur', min: 11, max: 11, message: '长度不符合'}
  ]

})

onMounted(() => {
  getListByUid().then(res =>{
    form.value.addrList = res.data.data
  })
})

function submit() {
  formRef.value.validate((isValid) => {
    if(isValid) {
      dialogFormVisible.value = false
      save(form.value.formAddr).then()
    }
  });
}

function confirm(){
  if (form.addr == ''){
    ElMessage({
      message: '请选择收入地址',
      type: 'warning',
    })
  }else{
    goods.forEach(item => {
      confirmOrder({
        bookId: item.bookId,
        bookName: item.bookName,
        num: item.num,
        address: form.value.addr,
        price: item.price
      }).then(res => {
        goods.forEach(item =>{
          store.delGood(item.bookId)
        })
        router.push('/success')
      })
    })
  }
}



</script>

<style scoped>

</style>