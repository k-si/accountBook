<template>
  <div class="app-container">
    <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item label="start time:">
        <el-date-picker
          v-model="searchObj.begin"
          type="datetime"
          placeholder="date from"
          value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00"
        />
      </el-form-item>
      <el-form-item label="end time:">
        <el-date-picker
          v-model="searchObj.end"
          type="datetime"
          placeholder="date to"
          value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00"
        />
      </el-form-item>
      <el-form-item label="username:">
        <el-input v-model="searchObj.name" placeholder="the record of who"/>
      </el-form-item>
      <el-button type="primary" icon="el-icon-search" @click="fetchData()">select</el-button>
        <el-button type="default" @click="resetData()">clear form</el-button>
    </el-form>
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item label="lowest price:">
        <el-input v-model="searchObj.low" placeholder="price from" style="width: 200px"/>
      </el-form-item>
      <el-form-item label="highest price:">
        <el-input v-model="searchObj.high" placeholder="price to" style="width: 190px"/>
      </el-form-item>
      <el-form-item label="label:">
      <el-select v-model="searchObj.labelId" filterable placeholder="select label" style="width: 225px">
        <el-option
        v-for="item in labels"
        :key="item.id"
        :label="item.name"
        :value="item.id">
        </el-option>
      </el-select>
      </el-form-item>
      <el-form-item label="">
        <span style="color:#409EFF;font-weight:bold;font-size:20px">total assets:{{" " + this.totalAssets + "￥"}}</span>
      </el-form-item>
    </el-form>

    <!-- 表格 -->
    <el-table
      v-loading="listLoading"
      :data="list"
      element-loading-text="data loading..."
      border
      fit
      highlight-current-row>

      <el-table-column
        label="number"
        width="80"
        align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column
        prop="label"
        label="tag"
        width="150"
        align="center">
        <template slot-scope="scope">
            <el-tag
            disable-transitions>{{scope.row.label}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="event" label="event" align="center"/>    
      <el-table-column label="change" width="120" align="center" >
        <template slot-scope="scope">
          <span v-if="scope.row.status === '1'" style="color:#67C23A;font-weight:bold">{{"- " + scope.row.change}}</span>
          <span v-else style="color:#F56C6C;font-weight:bold">{{"+ " + scope.row.change}}</span>
        </template>
      </el-table-column>
      <el-table-column prop="gmtCreate" label="date" width="200" align="center"/>
      <el-table-column label="operation" width="250" align="center">
        <template slot-scope="scope">
          <router-link :to="'/records/edit/'+scope.row.id">
            <el-button type="primary" size="mini" icon="el-icon-edit">update</el-button>
          </router-link>
          <el-button type="danger" size="mini" icon="el-icon-delete" @click="deleteById(scope.row.id)">delete</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <el-pagination
      :current-page="page"
      :page-size="limit"
      :total="total"
      style="padding: 30px 0; text-align: center;"
      layout="total, prev, pager, next, jumper"
      @current-change="fetchData"
    />
  </div>
</template>

<script>
import records from '@/api/records'
import labels from '@/api/labels'

export default {

  data() {// 定义数据
      return {
        listLoading: true, // 加载动画
        list: null,
        total: 0,
        page: 1,
        limit: 10,
        searchObj: {},
        labels: [],
        totalAssets: ''
      }
  },
    
  created() { // 当页面加载时获取数据
    this.fetchData()
    labels.all().then(response => {
        this.labels = response.data.items
    })
    records.getTotalAssets().then(response => {
      this.totalAssets = response.data.totalAssets
    })
  },

  methods: {
    fetchData(page = 1) { // 获取所有流水
      this.page = page
      this.listLoading = true
      records.findByCondition(this.page, this.limit, this.searchObj).then(response => {
        if (response.success === true) {
            this.list = response.data.items
            this.total = response.data.total
        }
        this.listLoading = false
      })
    },
    resetData() {
        this.searchObj = {}
        this.fetchData()
    },
    deleteById(id) {
        this.$confirm("Whether to delete the user information?", "", {
            confirmButtonText: "ok",
            cancelButtonText: "cancel",
            type: "warning",
        }).then(response => {
            records.deleteById(id).then(response => {
                this.fetchData()
                this.$message({
                    type: "success",
                    message: "successfully delete!",
                });
            })
        }).catch(response => {})
    }
  }
}
</script>