<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="活动标题" prop="activityTitle">
        <el-input
          v-model="queryParams.activityTitle"
          placeholder="请输入活动标题"
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
          v-hasPermi="['lab_web_sys:activities:add']"
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
          v-hasPermi="['lab_web_sys:activities:edit']"
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
          v-hasPermi="['lab_web_sys:activities:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['lab_web_sys:activities:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="activitiesList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="" align="center" prop="id" />
      <el-table-column label="活动开始时间" align="center" prop="activityTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.activityTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="活动标题" align="center" prop="activityTitle" />
      <el-table-column label="活动地点" align="center" prop="activityLocation" />
      <el-table-column label="主持人" align="center" prop="activityHost" />
      <el-table-column label="记录人" align="center" prop="activityRecorder" />
      <el-table-column label="参会人员名单 (例如：张三,李四,王五)" align="center" prop="activityAttendees" />
      <el-table-column label="活动详细内容" align="center" prop="activityContent" />
      <el-table-column label="活动图片链接1" align="center" prop="activityImageUrl1" />
      <el-table-column label="活动图片链接2" align="center" prop="activityImageUrl2" />
      <el-table-column label="活动图片链接3" align="center" prop="activityImageUrl3" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['lab_web_sys:activities:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['lab_web_sys:activities:remove']"
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

    <!-- 添加或修改活动管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="活动开始时间" prop="activityTime">
          <el-date-picker clearable
            v-model="form.activityTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择活动开始时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="活动标题" prop="activityTitle">
          <el-input v-model="form.activityTitle" placeholder="请输入活动标题" />
        </el-form-item>
        <el-form-item label="活动地点" prop="activityLocation">
          <el-input v-model="form.activityLocation" placeholder="请输入活动地点" />
        </el-form-item>
        <el-form-item label="主持人" prop="activityHost">
          <el-input v-model="form.activityHost" placeholder="请输入主持人" />
        </el-form-item>
        <el-form-item label="记录人" prop="activityRecorder">
          <el-input v-model="form.activityRecorder" placeholder="请输入记录人" />
        </el-form-item>
        <el-form-item label="参会人员名单 (例如：张三,李四,王五)" prop="activityAttendees">
          <el-input v-model="form.activityAttendees" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="活动详细内容">
          <editor v-model="form.activityContent" :min-height="192"/>
        </el-form-item>
        <el-form-item label="活动图片链接1" prop="activityImageUrl1">
          <el-input v-model="form.activityImageUrl1" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="活动图片链接2" prop="activityImageUrl2">
          <el-input v-model="form.activityImageUrl2" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="活动图片链接3" prop="activityImageUrl3">
          <el-input v-model="form.activityImageUrl3" type="textarea" placeholder="请输入内容" />
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
import { listActivities, getActivities, delActivities, addActivities, updateActivities } from "@/api/lab_web_sys/activities"

export default {
  name: "Activities",
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
      // 活动管理表格数据
      activitiesList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        activityTitle: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        activityTime: [
          { required: true, message: "活动开始时间不能为空", trigger: "blur" }
        ],
        activityTitle: [
          { required: true, message: "活动标题不能为空", trigger: "blur" }
        ],
        activityLocation: [
          { required: true, message: "活动地点不能为空", trigger: "blur" }
        ],
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询活动管理列表 */
    getList() {
      this.loading = true
      listActivities(this.queryParams).then(response => {
        this.activitiesList = response.rows
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
        activityTime: null,
        activityTitle: null,
        activityLocation: null,
        activityHost: null,
        activityRecorder: null,
        activityAttendees: null,
        activityContent: null,
        activityImageUrl1: null,
        activityImageUrl2: null,
        activityImageUrl3: null
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
      this.title = "添加活动管理"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getActivities(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改活动管理"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateActivities(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addActivities(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除活动管理编号为"' + ids + '"的数据项？').then(function() {
        return delActivities(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('lab_web_sys/activities/export', {
        ...this.queryParams
      }, `activities_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
