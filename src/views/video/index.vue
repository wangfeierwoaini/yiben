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
        <el-table ref="multipleTable" :data="fetchData()" border style="width: 100%" @selection-change="handleSelectionChange">
          <el-table-column type="selection" style="width: 2%" />
          <el-table-column prop="id" label="序号" style="width: 5%" />
          <el-table-column prop="videoName" label="视频名称" style="width: 10%" />
          <el-table-column prop="videoUrl" label="视频链接" style="width: 20%" />
          <el-table-column prop="videoEwm" label="视频二维码" style="width: 10%" />
          <el-table-column prop="uploadTime" label="上传时间" style="width: 10%" />
          <el-table-column ref="multipleTable" :data="videoData" label="操作" width="180">
            <el-button v-model="videoData.PackingId" size="small" round @click="updateVideo1=true">编辑</el-button>
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
          :current-page="currentPage4"
          :page-sizes="[10, 20, 30, 40]"
          :page-size="10"
          layout="total, sizes, prev, pager, next, jumper"
          :total="400"
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
      currentPage1: 5,
      currentPage2: 5,
      currentPage3: 5,
      currentPage4: 4,
      input: '',
      videoData: [
        { PackingId: 1, videoName: '认识车辆1', videoUrl: 'http:25.34.122.23/video/1.mp4', videoEwm: '二维码', uploadTime: '2021-05-23', updateTime: '2021-05-23' },
        { PackingId: 2, videoName: '认识车辆2', videoUrl: 'http:25.34.122.23/video/2.mp4', videoEwm: '二维码', uploadTime: '2021-05-23', updateTime: '2021-05-23' },
        { PackingId: 3, videoName: '认识车辆3', videoUrl: 'http:25.34.122.23/video/3.mp4', videoEwm: '二维码', uploadTime: '2021-05-23', updateTime: '2021-05-23' },
      ],
      addVideoFrom: {
        videoName: '',
        videoUrl: ''
      },
      // 控制添加用户对话框的显示与隐藏，默认为隐藏
      addVideo: false,
      updateVideo1: false
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    fetchData() {
      this.$axios.get('http://192.168.31.117:7879/video/allVideo').then(function(res) {
        this.fetchData(
      })
    },
    saveVideo: function() {
      const _this = this
      const data = new FormData()
      data.append('videoName', this.addVideoFrom.videoName)
      data.append('videoUrl', this.addVideoFrom.videoUrl)
      this.$axios.post('http://192.168.31.117:7879/video//saveVideo', data).then(function(res) {
        // eslint-disable-next-line eqeqeq
        if (res.data.code == '404') {
          alert('用户不存在')
          // eslint-disable-next-line eqeqeq
        } else if (res.data.code == '300') {
          alert('登录失败，账号或密码错误')
          // eslint-disable-next-line eqeqeq
        } else if (res.data.code == '200') {
          _this.$router.push(
            '/find')
        }
      })
    },
    /* addVido(){
      this.$alert('这是一段内容', '新增视频', {
        cancelButtonText: '取消',
        confirmButtonText: '确定',
        callback: action => {
          this.$message({
            type: 'info',
            message: `action: ${ action }`
          });
        }
      });

    },*/
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
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`)
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`)
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
  /*&-text {
    font-size: 30px;
    line-height: 46px;
  }*/
}
</style>
