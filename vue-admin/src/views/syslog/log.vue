<template>
  <div>
    <div class="common-layout">
      <el-container>
        <el-header>标签管理</el-header>
        <el-main>
          <el-row>
            <el-col>
              <el-form-item label="关键字:">
                <el-input v-model="selectValue" style="width: 200px" placeholder="操作人名称"></el-input>
                <el-button type="primary" style="margin-left: 15px" @click="select()">搜索</el-button>
                <el-button type="primary" style="margin-left: 15px" @click="add()" disabled>+ 添加商品</el-button>
                <el-button type="danger" style="margin-left: 15px" @click="del()" disabled>删除</el-button>
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
                <el-table-column align="center" prop="id" label="编号"/>
                <el-table-column align="center" prop="username" label="操作人" width="100"/>
                <el-table-column align="center" prop="ip" label="ip地址"/>
                <el-table-column align="center" prop="actionName" label="模块名"/>
                <el-table-column align="center" prop="moduleName" label="模块动作"/>
                <el-table-column align="center" prop="request" label="请求信息">
                  <template #default="scope">
                    <span class="myNote">{{ scope.row.request }}</span>
                  </template>
                </el-table-column>
                <el-table-column align="center" prop="message" label="返回信息">
                  <template #default="scope">
                    <span class="myNote">{{ scope.row.message }}</span>
                  </template>
                </el-table-column>
                <el-table-column align="center" prop="addTime" label="请求时间"/>
                <el-table-column align="center" fixed="right" label="操作" width="60">
                  <template #default="scope">
                    <el-button link type="primary" size="small" @click="info(scope.row)">详细</el-button>
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
          <el-form-item v-if="form.isEdit" label="编号:" :label-width="formLabelWidth" prop="isNotNull">
            <el-input v-model="form.id" autocomplete="off" disabled/>
          </el-form-item>
          <el-form-item label="操作人:" :label-width="formLabelWidth" prop="isNotNull">
            <el-input v-model="form.username" autocomplete="off"/>
          </el-form-item>
          <el-form-item label="ip地址:" :label-width="formLabelWidth" prop="isNotNull">
            <el-input v-model="form.ip" autocomplete="off"/>
          </el-form-item>
          <el-form-item label="模块名:" :label-width="formLabelWidth" prop="isNotNull">
            <el-input v-model="form.moduleName" autocomplete="off"/>
          </el-form-item>
          <el-form-item label="模块动作:" :label-width="formLabelWidth" prop="isNotNull">
            <el-input v-model="form.actionName" autocomplete="off"/>
          </el-form-item>
          <el-form-item label="请求信息:" :label-width="formLabelWidth" prop="isNotNull">
            <el-input v-model="form.request" type="textarea" autocomplete="off"/>
          </el-form-item>
          <el-form-item label="返回信息:" :label-width="formLabelWidth" prop="isNotNull">
            <el-input v-model="form.message" type="textarea" autocomplete="off"/>
          </el-form-item>
          <el-form-item label="请求时间:" :label-width="formLabelWidth" prop="isNotNull">
            <el-input v-model="form.addTime" autocomplete="off"/>
          </el-form-item>
        </el-form>
        <template #footer>
      <span class="dialog-footer">
        <el-button @click="off()" type="primary">返回</el-button>
<!--        <el-button v-if="form.isEdit === false" type="primary" @click="onSubmit()">提交</el-button>-->
<!--        <el-button v-else type="primary" @click="update()">修改</el-button>-->
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
import {getPageList,getPagination} from "@/net/log";

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
  isNotNull: [{required: false, message: '必填项', trigger: 'blur'}]
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

const form = ref({
  id: '',
  ip: '',
  actionName: '',
  moduleName: '',
  request: '',
  message: '',
  username: '',
  addTime: '',
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

//搜索框动态
// watch(selectValue, () => {
//   select()
// })
const add = () => {
  dialogFormVisible.value = true
}
const del = () => {

}
const edit = (object) => {

}

const info = (object) => {
  form.value.disabled = false
  dialogFormVisible.value = true
  form.value.id = object.id
  form.value.ip = object.ip
  form.value.actionName = object.actionName
  form.value.moduleName = object.moduleName
  form.value.request = object.request
  form.value.message = object.message
  form.value.username = object.username
  form.value.addTime = object.addTime
}

const off = () => {
  dialogFormVisible.value = false
}
const onSubmit = () => {

}

const update = () =>{

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