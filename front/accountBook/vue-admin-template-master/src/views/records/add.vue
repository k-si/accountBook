<template>
  <div class="app-container">
    <el-alert
        :title="msg"
        :type="type"
        close-text="got it"
        show-icon>
    </el-alert>
    <br>
    <el-form label-width="100px" :label-position="labelPosition">
      <el-form-item label="username:">
      <el-select v-model="userId" filterable placeholder="select the user">
        <el-option
          v-for="item in users"
          :key="item.id"
          :label="item.name"
          :value="item.id">
        </el-option>
      </el-select>
      </el-form-item>

      <el-form-item label="label:">
        <el-select v-model="labelId" filterable placeholder="select a tag">
          <el-option
            v-for="item in labels"
            :key="item.id"
            :label="item.name"
            :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
      
      <el-form-item label="change:">
        <el-select
          v-model="record.status"
          clearable
          placeholder="more or less">
          <el-option :value="1" label="expend" />
          <el-option :value="2" label="income" />
        </el-select>
        <el-input v-model="record.change" placeholder="price change" style="width: 205px"/>
      </el-form-item>

      <el-form-item label="event time:">
        <el-date-picker
          v-model="record.gmtCreate"
          type="datetime"
          placeholder="when did you do that"
          value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00"
        />
      </el-form-item>

      <el-form-item label="event:">
        <el-input :rows="5" type="textarea" v-model="record.event" placeholder="what did you do"/>
      </el-form-item>
      <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate()">save</el-button>
      </el-form-item> 
    </el-form>
  </div>
</template>

<script>
import records from '@/api/records'
import users from '@/api/users'
import labels from '@/api/labels'

export default {
  data() {
    return {
      labelPosition: "left",
      msg: "",
      type: "",
      record: {
        //change: 0
      },
      saveBtnDisabled: false, // 保存按钮是否禁用
      users: [],
      userId: '',
      labels: [],
      labelId: ''
    }
  },
  created() {
    this.init()
  },
  watch: {
    $route(to, from) { // 检测路由状态
      this.init()
    }
  },
  methods: {
    init() {
        if (this.$route.params && this.$route.params.id) {
          this.msg = "you can update record on this page"
          this.type = "warning"
          const id = this.$route.params.id
          this.getRecordInfo(id)
        } else {
          this.msg = "you can add record on this page"
          this.type = "success"
          this.user = {}
          this.record = {}
          this.record.change = 0
          this.users = []
          this.labels = []
          this.userId = ''
          this.labelId = ''
        }
        this.getAllUser()
        this.getAllLabel()
    },
    saveOrUpdate() {
      this.saveBtnDisabled = true
      if (this.$route.params && this.$route.params.id) {
          this.updateRecorder()
      } else {
          this.saveRecorder()
      }
    },
    saveRecorder() { // 保存
        this.record.userId = this.userId
        this.record.labelId = this.labelId
        records.add(this.record).then(response => {
            this.$message({
                type: "success",
                message: "save successfully!",
            });
            // 路由跳转，注意是router，全局路由用于跳转
            this.$router.push({ path: "/records/list" })
        })
    },
    updateRecorder() { // 修改
        this.record.userId = this.userId
        this.record.labelId = this.labelId
        records.updateById(this.record.id, this.record).then(response => {
            this.$message({
                type: "success",
                message: "update successfully!",
            });
            this.$router.push({ path: "/records/list" })
        })
    },
    getRecordInfo(id) {
      records.findById(id).then(response => {
        this.userId = response.data.item.userId
        this.labelId = response.data.item.labelId
        this.record = response.data.item
      })
    },
    getAllUser() {
      users.find().then(response => {
        this.users = response.data.items
      })
    },
    getAllLabel() {
      labels.all().then(response => {
        this.labels = response.data.items
      })
    }
  }
}
</script>