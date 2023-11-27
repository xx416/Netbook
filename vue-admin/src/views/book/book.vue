<template>
  <div>
    <div class="common-layout">
      <el-container>
        <el-header>商品管理</el-header>
        <el-main>
          <el-row>
            <el-col>
              <el-form-item label="关键字:">
                <el-input v-model="selectValue" style="width: 200px" placeholder="商品名称"></el-input>
                <el-button type="primary" style="margin-left: 15px" @click="select()">搜索</el-button>
                <el-button type="primary" style="margin-left: 15px" @click="add()">+ 添加商品</el-button>
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
                <el-table-column align="center" prop="bookId" label="编号" width="60"/>
                <el-table-column align="center" prop="bookName" label="名称">
                  <template #default="scope">
                    <span class="myNote">{{ scope.row.bookName }}</span>
                  </template>
                </el-table-column>
                <el-table-column align="center" prop="image" label="封面" width="80">
                  <template #default="scope">
                    <el-image
                        :preview-src-list="[`${baseURL}${scope.row.image}`]"
                        :hide-on-click-modal="true"
                        :preview-teleported="true"
                        :src="`${baseURL}${scope.row.image}`"
                        style="width:40px;height: 40px"
                    ></el-image>
                  </template>
                </el-table-column>
                <el-table-column align="center" prop="pressName" label="出版社"/>
                <el-table-column align="center" prop="author" label="作者"/>
                <el-table-column align="center" prop="price" label="单价"/>
                <el-table-column align="center" prop="sales" label="销量"/>
                <el-table-column align="center" prop="stock" label="库存"/>
                <el-table-column align="center" prop="tagName" label="类别">
                  <template #default="scope">
                    <span class="myNote">{{ scope.row.tagName }}</span>
                  </template>
                </el-table-column>
                <el-table-column align="center" prop="addTime" label="入库时间" width="125">
                  <template #default="scope">
                    <span class="myNote">{{ scope.row.addTime }}</span>
                  </template>
                </el-table-column>
                <el-table-column align="center" prop="bookInfo" label="介绍" width="120"/>
                <el-table-column align="center" prop="state" label="上架状态" width="120">
                  <template #default="scope">
                    <el-button v-if="scope.row.state" type="danger" @click="change(scope.row.bookId,scope.row.state)">下架</el-button>
                    <el-button v-else type="success" @click="change(scope.row.bookId,scope.row.state)">上架</el-button>
                  </template>
                </el-table-column>
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

      <el-dialog v-model="dialogFormVisible" title="添加商品">
        <el-form
            :model="form"
            :rules="rule"
            :disabled="form.disabled"
        >
          <el-form-item label="图书名称:" :label-width="formLabelWidth" prop="isNotNull">
            <el-input v-model="form.bookName" autocomplete="off"/>
          </el-form-item>
          <el-form-item label="作者:" :label-width="formLabelWidth" prop="isNotNull">
            <el-input v-model="form.author" autocomplete="off"/>
          </el-form-item>
          <el-form-item label="出版社:" :label-width="formLabelWidth" prop="isNotNull">
            <el-select v-model="form.pressName" placeholder="选择出版社" style="width: 240px">
              <el-option
                  v-for="item in pressList"
                  :value="item.pressName"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="图书标签:" :label-width="formLabelWidth" prop="isNotNull">
            <el-select
                v-model="form.tagName"
                multiple
                placeholder="选择图书标签"
                style="width: 240px"
            >
              <el-option
                  v-for="item in options"
                  :label="item.tagName"
                  :value="item.tagName"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="单价:" :label-width="formLabelWidth" prop="isNotNull">
            <el-input v-model="form.price" autocomplete="off"/>
          </el-form-item>
          <el-form-item label="库存量:" :label-width="formLabelWidth" prop="isNotNull">
            <el-input v-model="form.stock" autocomplete="off"/>
          </el-form-item>
          <el-form-item label="运费:" :label-width="formLabelWidth" prop="isNotNull">
            <el-input v-model="form.transit" autocomplete="off"/>
          </el-form-item>
          <el-form-item label="出版时间:" :label-width="formLabelWidth" prop="isNotNull">
            <el-date-picker
                v-model="form.bookPubTime"
                type="date"
                placeholder=""
                format="YYYY/MM/DD"
                value-format="YYYY-MM-DD"
            />
          </el-form-item>
          <el-form-item label="介绍:" :label-width="formLabelWidth" prop="isNotNull">
            <el-input v-model="form.bookInfo" autocomplete="off"/>
          </el-form-item>
          <el-form-item label="封面:" :label-width="formLabelWidth" prop="isNotNull">
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
    </div>
  </div>
</template>

<script setup>
import {nextTick, onMounted, reactive, ref, watch} from "vue";
import {getBookByName, getPageList, getPagination, removeById, save, updateById, updateByIdInt} from "@/net/book";
import {getCurrentInstance} from "vue";
import {ElMessage, ElMessageBox} from "element-plus";
import {postUploadFile, postUploadFileCover} from "@/net/person";
import {getList} from "@/net/tag";
import {getPressList} from "@/net/press";

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
  getList().then(res => {//获取分类标签列表
    options.value = res.data.data
  })
  getPressList().then(res => {//获取出版社列表
    pressList.value = res.data.data
  })
})
const options = ref([])//分类标签列表
const pressList = ref([])//出版社列表

const form = ref({
  bookId: null,
  bookName: '',
  bookInfo: '',
  image: '',
  price: '',
  author: '',
  stock: '',
  pressName: '',
  tagName: '',
  transit: '',
  state: true,
  bookPubTime: '',
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
  const bookName = selectValue.value
  getBookByName({bookName}).then(res => {
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
            const id = item.bookId
            removeById({id}).then(res => {
              console.log('res', res)
              if (res.status === 200) {
                select()
                ElMessage({
                  type: 'success',
                  message: '删除成功',
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
  form.value.bookId = object.bookId
  form.value.bookName = object.bookName
  form.value.bookInfo = object.bookInfo
  form.value.image = object.image
  form.value.price = object.price
  form.value.author = object.author
  form.value.stock = object.stock
  form.value.pressName = object.pressName
  form.value.pressName = object.pressName
  form.value.bookPubTime = object.bookPubTime
  form.value.transit = object.transit
  form.value.tagName = object.tagName.split(",")
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
  if (form.value.file === '' || form.value.bookName === '' || form.value.tagName === '' || form.value.price === '' || form.value.stock === '' || form.value.pressName === '' || form.value.bookPubTime === '' || form.value.bookPubTime === '' || form.value.file === '' || form.value.author === '') {
    ElMessage.warning('请填写完整信息!!!')
    return null;
  } else {
    const tagName = form.value.tagName.join(',');
    form.value.tagName = tagName
    let formData = new FormData();
    formData.append('file', form.value.file)
    postUploadFileCover(formData).then(res => {
      if (res.status == 200) {
        form.value.image = res.data
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
  if (form.value.file === '' || form.value.bookName === '' || form.value.tagName === '' || form.value.price === '' || form.value.stock === '' || form.value.pressName === '' || form.value.bookPubTime === '' || form.value.bookPubTime === '' || form.value.file === '' || form.value.author === '') {
    ElMessage.warning('请填写完整信息!!!')
    return null;
  } else {
    const tagName = form.value.tagName.join(',');
    form.value.tagName = tagName
    let formData = new FormData();
    formData.append('file', form.value.file)
    postUploadFileCover(formData).then(res => {
      if (res.data != "图片上传失败！"){
        form.value.image = res.data
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

const change = (id,state) => {
  const column = "state"
  let value = null
  if (state === true){
    value = 0
  }else{
    value = 1
  }
  updateByIdInt({id,column,value}).then(res =>{
    if(res.data.code === 200){
      var index = tableList.value.findIndex((index) => index.bookId === id);
      tableList.value[index].state = value
    }
  })
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