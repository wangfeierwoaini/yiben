// 弹窗组件
<template>
  <el-dialog title="示例弹窗" :visible.sync="dialogVisible" :before-close="cancel">
    <div class="dialog-content">
    </div>
    <span slot="footer" class="dialog-footer">
            <el-button @click="cancel">取 消</el-button>
            <el-button type="primary">确 定</el-button>
        </span>
  </el-dialog>
</template>
<script>
export default {
  name: 'saveVideo',
  data() {
    return {

    }
  },
  props: {
    dialogVisible: {
      type: Boolean,
      default: false,
    },
  },
  methods: {
    cancel() {
      this.$emit('dialog-cancel');
    }
  },
}
</script>

// 父组件
<dialog-example :dialog-visible="showDialog" @dialog-cancel="showDialog=false"></dialog-example>
<el-button @click="showDialog=true">打开弹窗</el-button>
