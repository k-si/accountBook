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
      <el-form-item label="nickname:">
        <el-input v-model="user.name" placeholder="usename"/>
      </el-form-item>
      <el-form-item label="user introduction:">
        <el-input v-model="user.intro" :rows="5" type="textarea" placeholder="user's introduction"/>
      </el-form-item>
      <el-form-item label="balance:">
        <el-input v-model="user.balance" placeholder="balance of the user" style="width: 205px"/>
      </el-form-item>
      <el-form-item label="user sort:">
        <el-input v-model="user.sort" placeholder="user's sort" style="width: 205px"/>
      </el-form-item>      

      <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate()">save</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import user from '@/api/users'
export default {
  data() {
    return {
      labelPosition: "left",
      msg: "",
      type: "",
      user: {
          name: "",
          sort: 0,
          intro: "",
          avatar: "",
      },
      saveBtnDisabled: false // 保存按钮是否禁用,
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
          this.msg = "you can update user on this page"
          this.type = "warning"
          const id = this.$route.params.id
          this.getUserById(id)
        } else {
          this.msg = "you can add user on this page"
          this.type = "success"
          this.user = {}
          this.user.sort = 0
        }
    },
    saveOrUpdate() {
      this.saveBtnDisabled = true
      if (this.$route.params && this.$route.params.id) {
          this.updateUser()
      } else {
          this.saveUser()
      }
    },
    saveUser() { // 保存
        user.add(this.user).then(response => {
            this.$message({
                type: "success",
                message: "successfully save!",
            });
            // 路由跳转，注意是router，全局路由用于跳转
            this.$router.push({ path: "/users/list" })
        })
    },
    updateUser() { // 修改
        user.updateById(this.user.id, this.user).then(response => {
            this.$message({
                type: "success",
                message: "successfully update!",
            });
            this.$router.push({ path: "/users/list" })
        })
    },
    getUserById(id) {
      user.findById(id).then(response => {
        this.user = response.data.item
      })
    }
  }
}
</script>