<template>
  <div class="app-container">
      <el-tag
        :key="tag.id"
        v-for="tag in tags"
        closable
        :disable-transitions="false"
        @close="handleClose(tag)"
        style="margin-bottom:10px">
        {{tag.name}}
      </el-tag>
      <el-input
        class="input-new-tag"
        v-if="inputVisible"
        v-model="inputValue"
        ref="saveTagInput"
        size="small"
        @keyup.enter.native="handleInputConfirm"
        @blur="handleInputConfirm"
        style="margin-bottom:10px">
      </el-input>
      <el-button v-else class="button-new-tag" size="small" @click="showInput">+ New Tag</el-button>
  </div>
</template>

<script>
  import labels from '@/api/labels'
  export default {
    data() {
      return {
        tags: null,
        tag: {
            id: '',
            name: ''
        },
        inputVisible: false,
        inputValue: ''
      };
    },
    created() {
        this.fetchData()
    },
    methods: {
      fetchData() {
        labels.all().then(response => {
            this.tags = response.data.items
        })
      },
      handleClose(tag) {
        labels.deleteById(tag.id).then(response => {
            this.tags.splice(this.tags.indexOf(tag), 1);
        }) 
      },

      showInput() {
        this.inputVisible = true;
        this.$nextTick(_ => {
          this.$refs.saveTagInput.$refs.input.focus();
        });
      },

      handleInputConfirm() { 
        let inputValue = this.inputValue;
        if (inputValue) { 
            this.tag = {}; 
            this.tag.name = inputValue
            labels.add(this.tag).then(response => {
                this.tag = response.data.item;
                this.tags.push(this.tag); 
            })     
        }
        this.inputVisible = false;
        this.inputValue = '';
        this.tag = {};    
      }
    }
  }
</script>

<style scoped>
  .el-tag + .el-tag {
    margin-left: 10px;
  }
  .button-new-tag {
    margin-left: 10px;
    height: 32px;
    line-height: 30px;
    padding-top: 0;
    padding-bottom: 0;
  }
  .input-new-tag {
    width: 90px;
    margin-left: 10px;
    vertical-align: bottom;
  }
</style>