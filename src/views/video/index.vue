<template>
  <div class="video-container">
    <section class="el-container is-vertical">
      <header class="el-header" style="height: 60px; width: 100%">
        <el-row>
          <el-button type="success" round @click="addVideo=true">新增</el-button>
          <el-button type="danger" round @click="deleteVideo">删除</el-button>
          <el-input v-model="input" placeholder="请输入内容" clearable class="input-with-select" style="float: right ;width: 250px ;margin-right: 10px">
            <el-button slot="append" icon="el-icon-search" />
          </el-input>
        </el-row>
      </header>
      <main class="el-main" style="height: 80%">
        <el-table ref="multipleTable" :data="videoList" border style="width: 100%">
          <el-table-column type="selection" style="width: 2%" />
          <el-table-column prop="id" label="序号" style="width: 5%" />
          <el-table-column prop="videoName" label="视频名称" style="width: 10%" />
          <el-table-column prop="videoUrl" label="视频链接" style="width: 20%" />
          <el-table-column prop="videoEwm" label="视频二维码" style="width: 10%" />
          <el-table-column prop="uploadTime" label="上传时间" style="width: 10%" />
          <el-table-column ref="multipleTable" :data="videoData" label="操作" width="180">
            <el-button v-model="videoData.id" size="small" round @click="updateVideo1=true">编辑</el-button>
            <el-button size="small" prop="PackingId" type="danger" @click="deleteVideo(videoData.id)">删除</el-button>
          </el-table-column>
        </el-table>
        <!--编辑提示框-->
        <el-dialog
          title="修改视频"
          :visible.sync="updateVideo1"
          width="50%"
          center
          @close="addVideoClosed"
        >
          <!-- 内容的主体区域 -->
          <el-form ref="addVideoRef" :model="addVideoFrom" label-width="70px">
            <el-form-item label="序号" prop="videoName">
              <el-input v-model="videoData.PackingId" />
            </el-form-item>
            <el-form-item label="视频" prop="Video">
              <el-upload
                class="upload-demo"
                drag
                action="https://jsonplaceholder.typicode.com/posts/"
                multiple
              >
                <i class="el-icon-upload" />
                <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
                <!--            <div class="el-upload__tip" slot="tip">只能上传jpg/png文件，且不超过500kb</div>-->
              </el-upload>
            </el-form-item>
          </el-form>

          <!-- 删除提示框 -->
          <el-dialog title="提示" :visible.sync="delVisible" width="300px" center>
            <div class="del-dialog-cnt">删除不可恢复，是否确定删除？</div>
            <span slot="footer" class="dialog-footer">
              <el-button @click="delVisible = false">取 消</el-button>
              <el-button type="primary" @click="deleteRow">确 定</el-button>
            </span>
          </el-dialog>
        </el-dialog></main>
      <footer class="el-footer" style="float: right ; height: 10%">
        <el-pagination
          :page-sizes="[7 , 14 , 28 ,56]"
          :page-size="size"
          :current-page="pn"
          layout="total, sizes, prev, pager, next, jumper"
          background
          :total="totalElements"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </footer>
    </section>
    <!-- 新增视频的对话框 -->
    <el-dialog
      title="新增视频"
      :visible.sync="addVideo"
      width="50%"
      center
      @close="addVideoClosed"
    >
      <!-- 内容的主体区域 -->
      <el-form ref="addVideoRef" :model="addVideoFrom" label-width="70px">
        <el-form-item label="视频名称" prop="videoName">
          <el-input v-model="addVideoFrom.videoName" />
        </el-form-item>
        <el-form-item label="视频" prop="Video">
          <el-upload
            class="upload-demo"
            drag
            action="https://jsonplaceholder.typicode.com/posts/"
            multiple
          >
            <i class="el-icon-upload" />
            <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
            <!--            <div class="el-upload__tip" slot="tip">只能上传jpg/png文件，且不超过500kb</div>-->
          </el-upload>
        </el-form-item>
      </el-form>
      <!-- 底部区域 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="addVideo = false">取 消</el-button>
        <el-button type="primary" @click="saveVideo">确 定</el-button>
      </span>
    </el-dialog>

  </div>
</template>
<script>
export default {
  data() {
    return {
      totalElements: '',
      page: 1,
      size: 7,
      total: 0,
      input: '',
      videoData: [],
      addVideoFrom: {
        videoName: '',
        videoUrl: ''
      },
      videoList: [],
      // 控制添加用户对话框的显示与隐藏，默认为隐藏
      addVideo: false,
      updateVideo1: false
    }
  },
  mounted() {
    this.fetchData()
  },
  methods: {
    fetchData: function() {
      const that = this
      this.$axios.get('http://192.168.101.8:7879/video/allVideo', {
        params: {
          pn: this.page,
          size: this.size
        }}).then(function(res) {
        // eslint-disable-next-line eqeqeq
        if (res.data.code == '200') {
          that.videoList = res.data.data.content
          that.totalElements = res.data.data.totalElements
          that.page = res.data.data.number
          that.size = res.data.data.size
        }
      })
    },
    saveVideo: function() {
      const _this = this
      const data = new FormData()
      data.append('videoName', this.addVideoFrom.videoName)
      data.append('videoUrl', this.addVideoFrom.videoUrl)
      this.$axios.post('http://192.168.101.8:7879/video/saveVideo', data).then(function(res) {
          _this.$router.push(
            '/find')
      })
    },
    deleteVideo() {
      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$message({
          type: 'success',
          message: '删除成功!'
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    handleCurrentChange (page) {
      this.$axios
        .get('http://192.168.101.8:7879/video/allVideo', {
          params: {
            pn: page,
            size: this.size
          }
        }).then(res => {
        this.videoList = res.data.data.content
        this.total = res.data.data.totalElements
        this.page = res.data.data.number + 1
        this.size = res.data.data.size
      })
    },
    handleSizeChange (size) {
      this.$axios
        .get('http://192.168.101.8:7879/video/allVideo', {
          params: {
            pn: 1,
            size: size
          }
        }).then(res => {
        this.videoList = res.data.data.content
        this.total = res.data.data.totalElements
        this.page = res.data.data.number
        this.size = res.data.data.size
      })
    },
    // 监听添加用户对话框的关闭事件
    addVideoClosed() {
      this.$refs.addVideoRef.resetFields()
    }
  }
}
</script>
<style lang="scss" scoped>
.video{
  &-container {
    margin: 30px;
  }

}
</style>
