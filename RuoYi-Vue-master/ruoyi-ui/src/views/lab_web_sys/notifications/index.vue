<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="通知标题" prop="notificationTitle">
        <el-input
          v-model="queryParams.notificationTitle"
          placeholder="请输入通知标题"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['lab_web_sys:notifications:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['lab_web_sys:notifications:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['lab_web_sys:notifications:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['lab_web_sys:notifications:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="notificationsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="" align="center" prop="id" />
      <el-table-column label="通知发布时间" align="center" prop="notificationTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.notificationTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="通知标题" align="center" prop="notificationTitle" />
      <el-table-column label="通知详细内容" align="center" prop="notificationContent" />
      <el-table-column label="通知相关文件的URL或路径，可为空" align="center" prop="notificationFileUrl" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['lab_web_sys:notifications:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['lab_web_sys:notifications:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改通知管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="通知发布时间" prop="notificationTime">
          <el-date-picker clearable
            v-model="form.notificationTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择通知发布时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="通知标题" prop="notificationTitle">
          <el-input v-model="form.notificationTitle" placeholder="请输入通知标题" />
        </el-form-item>
        <el-form-item label="通知详细内容">
          <editor v-model="form.notificationContent" :min-height="192"/>
        </el-form-item>
        <el-form-item label="通知相关文件的URL或路径，可为空" prop="notificationFileUrl">
          <el-input v-model="form.notificationFileUrl" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listNotifications, getNotifications, delNotifications, addNotifications, updateNotifications } from "@/api/lab_web_sys/notifications"

export default {
  name: "Notifications",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 通知管理表格数据
      notificationsList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        notificationTitle: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        notificationTime: [
          { required: true, message: "通知发布时间不能为空", trigger: "blur" }
        ],
        notificationTitle: [
          { required: true, message: "通知标题不能为空", trigger: "blur" }
        ],
        notificationContent: [
          { required: true, message: "通知详细内容不能为空", trigger: "blur" }
        ],
        notificationFileUrl: [
          { required: true, message: "通知相关文件的URL或路径，可为空不能为空", trigger: "blur" }
        ]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询通知管理列表 */
    getList() {
      this.loading = true
      listNotifications(this.queryParams).then(response => {
        this.notificationsList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        notificationTime: null,
        notificationTitle: null,
        notificationContent: null,
        notificationFileUrl: null
      }
      this.resetForm("form")
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm")
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加通知管理"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getNotifications(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改通知管理"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateNotifications(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addNotifications(this.form).then(response => {
              this.$modal.msgSuccess("新增成功")
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal.confirm('是否确认删除通知管理编号为"' + ids + '"的数据项？').then(function() {
        return delNotifications(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('lab_web_sys/notifications/export', {
        ...this.queryParams
      }, `notifications_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
