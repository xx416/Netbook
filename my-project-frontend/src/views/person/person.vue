<template>
  <div>
    <el-card class="box-card mt10">
      <div class="user">
        <div class="userTitle">个人中心</div>
        <div class="userButton">
          <el-button v-for="button in buttons" :key="button.text" :type="button.type" text @click="changeUser(button.value)">
            {{ button.text }}
          </el-button>
        </div>
      </div>
      <el-tabs :tab-position="tabPosition" :model-value="activeName" style="height: 400px" class="demo-tabs mt20">
        <el-tab-pane label="基础设置" name="1">
          <div class="title">
            <div class="avatar mb20">
              <div>
                <el-image :src="`${store.baseURL}${form.userinfo.icon}`" :fit="'scale-down'" style="height: 100%"></el-image>
              </div>
              <span style="margin: auto auto auto 20px;">
                  <el-dialog v-model="dialogTableVisible" title="修改头像">
                    <el-upload
                        v-model:file-list="fileList"
                        class="upload-demo"
                        action="#"
                        :http-request="uploadFile"
                        :limit="1"
                        :on-exceed="handleExceed"
                        :before-upload="beforeUpload"
                        :on-success="handleSuccess"
                    >
                      <el-button type="primary">上传文件</el-button>
                      <template #tip>
                        上传限制5MB(JPG)
                      </template>
                    </el-upload>
                    <el-button type="primary" @click="onSubmit()">提交</el-button>
                  </el-dialog>
                  <el-button type="primary" @click="dialogTableVisible = true">
                    修改头像
                  </el-button>
              </span>
            </div>
            <div class="individual ml20">
              <div v-for="item in personalData">
                <span>{{ item.name }}</span>
                <p>{{ item.value }}</p>
              </div>
            </div>
          </div>
          <div class="m m-block m-order">
            <div class="mt">
              <h3>我的</h3>
            </div>
          </div>
        </el-tab-pane>
        <el-tab-pane label="我的订单" name="2">
          <el-table :data="form.list"
                    style="width: 100%"
                    height="370"
          >
            <el-table-column prop="orderId" label="订单编号" width="100"/>
            <el-table-column prop="bookName" label="商品名称" width="180"/>
            <el-table-column prop="num" label="数量" width="60"/>
            <el-table-column prop="price" label="单价" width="80"/>
            <el-table-column prop="total" label="总金额"/>
            <el-table-column prop="addressee" label="收货人" width="130"/>
            <el-table-column prop="phone" label="手机号" width="130"/>
            <el-table-column prop="address" label="收货地址"/>
            <el-table-column prop="orderTime" label="下单时间"/>
          </el-table>
        </el-tab-pane>
        <el-tab-pane label="安全设置" name="3">
          <div class="mb20">安全设置</div>
          <el-form label-position="right" ref="formRef" label-width="100px" :rules="rules" :model="form"
                   style="max-width: 460px">
            <el-form-item label="旧密码" prop="oldPassword">
              <el-input v-model="form.oldPassword"/>
            </el-form-item>
            <el-form-item label="新密码" prop="newPassword">
              <el-input v-model="form.newPassword"/>
            </el-form-item>
            <el-form-item label="重复新密码" prop="repeatTheNewPassword">
              <el-input v-model="form.repeatTheNewPassword"/>
            </el-form-item>
            <el-form-item label="">
              <el-button type='primary' @click="changeThePassword('form')">修改密码</el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>
      </el-tabs>
    </el-card>
  </div>
</template>

<script setup>
import {ref, reactive, onMounted} from 'vue'
import {getListByUid} from "@/net/order";
import {ElMessage, ElMessageBox} from 'element-plus'
import {postUploadFile} from '@/net/person'
import {logout} from "@/net";
import router from "@/router";
import {getUserByToken, updateIconById} from "@/net/user";
import {useBookStore} from "@/store";

const store = useBookStore()
const tabPosition = ref('left')
const activeName = ref('1')
const dialogTableVisible = ref(false)

const personalData = ref([
  {name: '已购买', value: 10},
  // { name: '已收藏', value: 10 },
  // { name: '余额', value: 10 },
  // { name: '最近浏览', value: 10 },
])
const form = ref({
  userinfo: [],
  list: [],
  file: ''
})

onMounted(() => {
  getUserByToken().then(res => {
    form.value.userinfo = res.data.data
  })
  getListByUid().then(res => {
    form.value.list = res.data.data
    console.log(res)
  })
})

const formRef = ref()
const rules = ref({
  oldPassword: [{required: true, message: '请输入旧密码', trigger: 'change'}],
  newPassword: [{required: true, message: '请输入新密码', trigger: 'change'}],
  repeatTheNewPassword: [{required: true, message: '请二次输入新密码', trigger: 'change'}]
})

const buttons = [
  {type: 'primary', text: '退出登录', value: 1},
  {type: 'primary', text: '切换账户', value: 2},
]

// 点击的按钮
const changeUser = ((value) => {
  switch (value) {
    case 1:
      out()
    case 2:
      out()
  }
})

const out = () =>{
  logout()
  router.push('/welcome')
}

const changeThePassword = () => {
  formRef.value.validate((valid, fields) => {
    if (valid) {

    } else {
      ElMessage.warning('请完整填写注册表单内容！')
    }
  })
}

const fileList = ref([])
const uploadFile = (file) => {
  form.value.file = file.file
}

const handleExceed = () => {
  ElMessage.warning('文件数量超出最大限制')
}

const handleSuccess = () => {
  dialogTableVisible.value = false
}

const beforeUpload = (file) => {
  const isJPG = file.type === 'image/jpeg';
  const isLT5M = file.size / 1024 / 1024 < 5;
  if (!isJPG) {
    ElMessage.warning('上传图片只能是JPG格式')
  }
  if (!isLT5M) {
    ElMessage.warning('上传的图片大小不能超过5MB')
  }
  return isJPG && isLT5M;
}

const onSubmit = async () => {
  if (form.value.file == ''){
    ElMessage.warning('请选择图片!!!')
    return null;
  }
  let formdata = new FormData();
  formdata.append('file', form.value.file)
  postUploadFile(formdata).then(res => {
    if (res.status == 200){
      const value = res.data
      const id = form.value.userinfo.userId
      const column = "icon"
      updateIconById({id,column,value}).then(res => {
        history.go(0)
      })
      ElMessage.success(res.data)
      dialogTableVisible.value = false
    }
  })
}


</script>

<style lang="scss">
.box-card {
  width: 100%;
  height: auto;
}

.user {
  display: flex;
  align-items: center;
  justify-content: space-between;
  border-bottom: 1px solid #ccc;
  padding-bottom: 10px;
}

.title {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
  border-bottom: 1px solid #ccc;

  .avatar {
    width: 100px;
    height: 100px;
    border: 1px solid black;
    display: flex;

    img {
      width: 100px;
      height: 100px;
    }
  }

  .individual {
    display: flex;
    margin-left: 90px;

    div {
      width: 150px;
      text-align: center;
    }
  }
}


.m-order .nav {
  padding: 15px 20px;
}

.root61 .m-order .nav .item {
  width: 90px;
}

.m-order .nav .item {
  float: left;
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
  padding: 13px 0 0;
  margin: 0 20px;
  width: 120px;
  height: 90px;
  text-align: center;
  color: #333;
  -webkit-transition: all .4s ease;
  -o-transition: all .4s ease;
  transition: all .4s ease;
}

.demo-tabs > .el-tabs__content {
  padding: 10px 32px 32px 32px;
  color: #6b778c;
  font-size: 16px;
  font-weight: 600;
}

.el-tabs--right .el-tabs__content,
.el-tabs--left .el-tabs__content {
  height: 100%;
}

.el-tabs--left .el-tabs__nav.is-left {
  flex-direction: column;
  justify-content: center;
  width: 200px;
}

.el-tabs--left .el-tabs__item.is-left {
  justify-content: center;
}</style>