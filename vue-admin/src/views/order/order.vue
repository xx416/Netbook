<template>
  <div>
    <div class="common-layout">
      <el-container>
        <el-header>订单管理</el-header>
        <el-main>
          <el-row>
            <el-col>
              <el-form-item label="关键字:">
                <el-input v-model="selectValue" style="width: 200px" placeholder="用户名或邮箱"></el-input>
                <el-button type="primary" style="margin-left: 15px" @click="select()">搜索</el-button>
                <el-button type="primary" style="margin-left: 15px" @click="add()" disabled>+ 添加商品</el-button>
                <el-button type="danger" style="margin-left: 15px" @click="del()">删除</el-button>
              </el-form-item>
            </el-col>
            <el-col>
              <el-table
                  :data="tableList"
                  max-height="1000px"
                  @selection-change="handleSelectionChange"
                  border
                  style="width: 100%"
              >
                <el-table-column type="selection" width="55"/>
                <el-table-column align="center" prop="orderId" label="编号" width="60"/>
                <el-table-column align="center" prop="bookName" label="商品"/>
                <el-table-column align="center" prop="num" label="购买数量" width="90"/>
                <el-table-column align="center" prop="price" label="单价" width="90">
                  <template #default="scope">
                    <span>{{scope.row.price}}￥</span>
                  </template>
                </el-table-column>
                <el-table-column align="center" prop="total" label="总金额" width="90">
                  <template #default="scope">
                    <span>{{scope.row.total}}￥</span>
                  </template>
                </el-table-column>
                <el-table-column align="center" prop="username" label="下单账户" width="90"/>
                <el-table-column align="center" prop="addressee" label="收货人"/>
                <el-table-column align="center" prop="address" label="收货地址"/>
                <el-table-column align="center" prop="phone" label="联系方式"/>
                <el-table-column align="center" prop="ispay" label="付款状态">
                  <template #default="scope">
                    <el-button v-if="scope.row.ispay" type="success">付款成功</el-button>
                    <el-button v-else type="danger">未支付</el-button>
                  </template>
                </el-table-column>
                  <el-table-column align="center" prop="orderTime" label="下单时间"/>
                <el-table-column align="center" fixed="right" label="操作" width="120">
                  <template #default="scope">
                    <el-button link type="primary" size="small" @click="info(scope.row)">详细</el-button>
                    <el-button link type="primary" size="small" @click="edit(scope.row)">修改</el-button>
                  </template>
                </el-table-column>
              </el-table>
            </el-col>
          </el-row>
          <el-col style="margin-top: 20px">
            <el-pagination
                background
                layout="prev, pager, next"
                v-model:current-page.sync="formRef.currentPage"
                :total="formRef.totalCount"
                :page-size="formRef.pageSize"
                :page-count="formRef.pageCount"
            />
          </el-col>
        </el-main>
      </el-container>

      <!--      ----------------------------------------------------------弹窗----------------------------------------------------------      -->
      <el-dialog v-model="dialogFormVisible" title="添加商品">
        <el-form
            :model="form"
            :rules="rule"
            :disabled="form.disabled"
        >
          <el-form-item label="订单编号:" :label-width="formLabelWidth" prop="isNotNull">
            <el-input v-model="form.orderId" autocomplete="off"/>
          </el-form-item>
          <el-form-item label="商品编号:" :label-width="formLabelWidth" prop="isNotNull">
            <el-input v-model="form.bookId" autocomplete="off"/>
          </el-form-item>
          <el-form-item label="用户编号:" :label-width="formLabelWidth" prop="isNotNull">
            <el-input v-model="form.userId" autocomplete="off"/>
          </el-form-item>
          <el-form-item label="下单用户:" :label-width="formLabelWidth" prop="isNotNull">
            <el-input v-model="form.username" autocomplete="off"/>
          </el-form-item>
          <el-form-item label="商品名称:" :label-width="formLabelWidth" prop="isNotNull">
            <el-input v-model="form.bookName" autocomplete="off"/>
          </el-form-item>
          <el-form-item label="购买的数量:" :label-width="formLabelWidth" prop="isNotNull">
            <el-input v-model="form.num" autocomplete="off"/>
          </el-form-item>
          <el-form-item label="单价:" :label-width="formLabelWidth" prop="isNotNull">
            <el-input v-model="form.price" autocomplete="off"/>
          </el-form-item>
          <el-form-item label="总金额:" :label-width="formLabelWidth" prop="isNotNull">
            <el-input v-model="form.total" autocomplete="off"/>
          </el-form-item>
          <el-form-item label="订单时间:" :label-width="formLabelWidth" prop="isNotNull">
            <el-input v-model="form.orderTime" autocomplete="off"/>
          </el-form-item>
          <el-form-item label="收货人:" :label-width="formLabelWidth" prop="isNotNull">
            <el-input v-model="form.addressee" autocomplete="off"/>
          </el-form-item>
          <el-form-item label="收货人:" :label-width="formLabelWidth" prop="isNotNull">
            <el-input v-model="form.address" autocomplete="off"/>
          </el-form-item>
          <el-form-item label="联系方式:" :label-width="formLabelWidth" prop="isNotNull">
            <el-input v-model="form.phone" autocomplete="off"/>
          </el-form-item>
        </el-form>
        <template #footer>
      <span class="dialog-footer">
        <el-button @click="off()">取消</el-button>
        <el-button v-if="form.isEdit === false" type="primary" @click="onSubmit()">提交</el-button>
        <el-button v-else type="primary" @click="update()">修改</el-button>
      </span>
        </template>
      </el-dialog>
      <!--      ----------------------------------------------------------弹窗end----------------------------------------------------------      -->
    </div>
  </div>
</template>

<script setup>
import {onMounted, reactive, ref, watch} from "vue";
import {ElMessage, ElMessageBox} from "element-plus";
import {postUploadFileCover} from "@/net/person";
import {getPageList,getPagination,getOrderByUser,removeById,save,updateById} from "@/net/order";
import {getListById} from "@/net/user"

const baseURL = "http://192.168.0.124:8080"
const dialogFormVisible = ref(false)
const formLabelWidth = '100px'
const multipleSelection = ref([])
const selectValue = ref('')// 搜索字段
const tableList = ref([])//列表数据
const formRef = ref({
  // 显示第几页 （可修改）
  currentPage: 1,
  // 默认每页显示的条数（可修改）
  pageSize: 15,
  // 总数据 (数据库获取)
  tableData: null,
  // 总条数，根据接口获取数据长度(注意：这里不能为空)  (数据库获取)
  totalCount: null,
  // 总页数 (数据库返回)
  pageCount: null,
})

//表单验证
const rule = reactive({
  isNotNull: [{required: true, message: '必填项', trigger: 'blur'}]
})

onMounted(() => {
  const pageSize = formRef.value.pageSize
  getPagination({pageSize}).then(res => {//获取分页信息
    formRef.value.pageCount = res.data.data.pageCount
    formRef.value.tableData = res.data.data.tableData
    formRef.value.totalCount = res.data.data.totalCount
    const currentPage = formRef.value.currentPage
    const tagName = ''
    getPageList({pageSize, currentPage, tagName}).then(res => {//获取图书信息
      tableList.value = res.data.data
    })
  })
})

const options = ref([])//分类标签列表
const pressList = ref([])//出版社列表

const form = ref({
  orderId: '',
  bookId: '',
  userId: '',
  user: '',
  bookName: '',
  num: '',
  orderTime: '',
  username: '',
  phone: '',
  addressee: '',
  address: '',
  ispay: '',
  file: null,
  isEdit: false,
  disabled: false
})

watch(formRef.value,() =>{
  tableList.value = []
  const pageSize = formRef.value.pageSize;
  const currentPage = formRef.value.currentPage;
  const tagName = selectValue.value;
  getPageList({pageSize,currentPage,tagName}).then(res =>{
    tableList.value = res.data.data
  })
})

const handleSelectionChange = (val) => {
  multipleSelection.value = val
}

const select = () => {
  const pageSize = formRef.value.pageSize
  const currentPage = formRef.value.currentPage
  const tagName = selectValue.value
  getPageList({pageSize, currentPage, tagName}).then(res => {//获取图书信息
    tableList.value = res.data.data
  })
}
const add = () => {
  dialogFormVisible.value = true
}
const del = () => {
  if (multipleSelection.value.length > 0) {
    ElMessageBox.confirm(
        '确定删除数据吗?',
        '提醒：',
        {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        }
    ).then(() => {
      multipleSelection.value.forEach((item) => {
        const id = item.bannerId
        removeById({id}).then(res => {
          if (res.status === 200) {
            select()
            ElMessage({
              type: 'success',
              message: '删除成功',
            })
          }else {
            ElMessage({
              type: 'info',
              message: '删除失败，请联系管理员',
            })
          }
        })
      })
    }).catch(() => {
      ElMessage({
        type: 'info',
        message: '删除失败，请联系管理员',
      })
    })
  } else {
    ElMessage.warning('请选中数据行!')
  }
}
const edit = (object) => {
  form.value.isEdit = true
  dialogFormVisible.value = true
  form.value.orderId = object.orderId
  form.value.bookId = object.bookId
  form.value.userId = object.userId
  form.value.user = object.user
  form.value.bookName = object.bookName
  form.value.num = object.num
  form.value.price = object.price
  form.value.total = object.total
  form.value.orderTime = object.orderTime
  form.value.username = object.username
  form.value.phone = object.phone
  form.value.addressee = object.addressee
  form.value.address = object.address
  form.value.ispay = object.ispay
}

const info = (object) => {
  form.value.disabled = true
  dialogFormVisible.value = true
  edit(object)
}

const off = () => {
  dialogFormVisible.value = false
  history.go(0)
}
const onSubmit = () => {
  if (form.value.file === '' || form.value.bookName === '') {
    ElMessage.warning('请填写完整信息!!!')
    return null;
  } else {
    let formData = new FormData();
    formData.append('file', form.value.file)
    postUploadFileCover(formData).then(res => {
      if (res.status == 200) {
        form.value.bannerSrc = res.data
        save(form.value).then(res => {
          console.log(res)
          dialogFormVisible.value = false
          history.go(0)
        })
      } else {
        ElMessage.error('封面上传失败，请联系管理员')
        dialogFormVisible.value = false
        history.go(0)
      }
    })
  }
}

const update = () =>{
  if (form.value.file === '' || form.value.bookName === '') {
    ElMessage.warning('请填写完整信息!!!')
    return null;
  } else {
    let formData = new FormData();
    formData.append('file', form.value.file)
    postUploadFileCover(formData).then(res => {
      if (res.data != "图片上传失败！"){
        form.value.bannerSrc = res.data
      }
      updateById(form.value).then(res => {
        console.log(res)
        dialogFormVisible.value = false
        history.go(0)
      })
    })
  }
}

const fileList = ref([])
const uploadFile = (file) => {
  form.value.file = file.file
}

const handleExceed = () => {
  ElMessage.warning('文件数量超出最大限制')
}

const handleSuccess = () => {
  dialogFormVisible.value = false
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


</script>

<style scoped>
.myNote {
  display: -webkit-box;
  text-overflow: ellipsis;
  overflow: hidden;
  -webkit-line-clamp: 1;
  -webkit-box-orient: vertical;
}
</style>