<template>
  <div class="video-container">
    <section class ="el-container is-vertical">
      <header  class="el-header" style="height: 60px; width: 100%" >
        <el-row>
          <el-button type="success" round @click="addVideo=true">新增</el-button>
          <el-button type="danger" round @click="deleteVideo">删除</el-button>
          <el-input  placeholder="请输入内容" v-model="input" clearable class="input-with-select" style="float: right ;width: 250px ;margin-right: 10px">
            <el-button slot="append" icon="el-icon-search"></el-button>
          </el-input>
        </el-row>
      </header>
      <main class="el-main" style="height: 80%">
        <el-table :data="videoData" border style="width: 100%" ref="multipleTable" @selection-change="handleSelectionChange">
          <el-table-column type="selection" style="width: 2%"></el-table-column>
          <el-table-column prop="PackingId" label="序号"  style="width: 5%">
          </el-table-column>
          <el-table-column prop="videoName" label="视频名称" style="width: 10%" >
          </el-table-column>
          <el-table-column prop="videoUrl" label="视频链接" style="width: 20%" >
          </el-table-column>
          <el-table-column prop="videoEwm" label="视频二维码" style="width: 10%" >
          </el-table-column>
          <el-table-column prop="uploadTime" label="上传时间" style="width: 10%" >
          </el-table-column>
          <el-table-column prop="updateTime" label="最后修改时间" style="width: 10%" >
          </el-table-column>

          <el-table-column label="操作" width="180">
            <template slot-scope="scope">
              <el-button size="small" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
              <el-button size="small" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>

        <!-- 删除提示框 -->
        <el-dialog title="提示" :visible.sync="delVisible" width="300px" center>
          <div class="del-dialog-cnt">删除不可恢复，是否确定删除？</div>
          <span slot="footer" class="dialog-footer">
                <el-button @click="delVisible = false">取 消</el-button>
                <el-button type="primary" @click="deleteRow" >确 定</el-button>
            </span>
        </el-dialog>
      </main>
      <footer class="el-footer" style="float: right ; height: 10%">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage4"
          :page-sizes="[10, 20, 30, 40]"
          :page-size="10"
          layout="total, sizes, prev, pager, next, jumper"
          :total="400">
        </el-pagination>
      </footer>
    </section>
    <!-- 新增视频的对话框 -->
    <el-dialog
      title="新增视频"
      :visible.sync="addVideo"
      width="50%"
      @close="addVideoClosed"
      center>
      <!-- 内容的主体区域 -->
      <el-form ref="addVideoRef" :model="addVideoFrom"  label-width="70px">
        <el-form-item label="视频名称" prop="videoName">
          <el-input v-model="addVideoFrom.videoName"></el-input>
        </el-form-item>
        <el-form-item label="视频" prop="Video">
          <el-upload
            class="upload-demo"
            drag
            action="https://jsonplaceholder.typicode.com/posts/"
            multiple>
            <i class="el-icon-upload"></i>
            <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
<!--            <div class="el-upload__tip" slot="tip">只能上传jpg/png文件，且不超过500kb</div>-->
          </el-upload>
        </el-form-item>
      </el-form>
      <!-- 底部区域 -->
      <span slot="footer" class="dialog-footer">
            <el-button @click="addVideo = false">取 消</el-button>
            <el-button type="primary" @click="addVideo= false">确 定</el-button>
        </span>
    </el-dialog>

  </div>
</template>
<script>
export default {
  methods:{
    /*addVideo(){
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
    deleteVideo(){
      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$message({
          type: 'success',
          message: '删除成功!'
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
    },
    // 监听添加用户对话框的关闭事件
    addVideoClosed () {
      this.$refs.addVideoRef.resetFields()
    },
  },
  data() {
    return {
      currentPage1: 5,
      currentPage2: 5,
      currentPage3: 5,
      currentPage4: 4,
      input: '',
      videoData:[
        {PackingId :1,videoName:"认识车辆1",videoUrl:"http:25.34.122.23/video/1.mp4",videoEwm:'二维码',uploadTime:"2021-05-23",updateTime:"2021-05-23"},
        {PackingId :2,videoName:"认识车辆2",videoUrl:"http:25.34.122.23/video/2.mp4",videoEwm:'二维码',uploadTime:"2021-05-23",updateTime:"2021-05-23"},
        {PackingId :3,videoName:"认识车辆3",videoUrl:"http:25.34.122.23/video/3.mp4",videoEwm:'二维码',uploadTime:"2021-05-23",updateTime:"2021-05-23"},
        {PackingId :4,videoName:"认识车辆4",videoUrl:"http:25.34.122.23/video/4.mp4",videoEwm:'二维码',uploadTime:"2021-05-23",updateTime:"2021-05-23"},
        {PackingId :5,videoName:"认识车辆5",videoUrl:"http:25.34.122.23/video/5.mp4",videoEwm:'二维码',uploadTime:"2021-05-23",updateTime:"2021-05-23"},
        {PackingId :6,videoName:"认识车辆6",videoUrl:"http:25.34.122.23/video/6.mp4",videoEwm:'二维码',uploadTime:"2021-05-23",updateTime:"2021-05-23"},
        {PackingId :7,videoName:"认识车辆7",videoUrl:"http:25.34.122.23/video/7.mp4",videoEwm:'二维码',uploadTime:"2021-05-23",updateTime:"2021-05-23"},
        {PackingId :8,videoName:"认识车辆8",videoUrl:"http:25.34.122.23/video/8.mp4",videoEwm:'二维码',uploadTime:"2021-05-23",updateTime:"2021-05-23"},
        {PackingId :9,videoName:"认识车辆9",videoUrl:"http:25.34.122.23/video/9.mp4",videoEwm:'二维码',uploadTime:"2021-05-23",updateTime:"2021-05-23"},
        {PackingId :10,videoName:"认识车辆10",videoUrl:"http:25.34.122.23/video/10.mp4",videoEwm:'二维码',uploadTime:"2021-05-23",updateTime:"2021-05-23"},
        {PackingId :11,videoName:"认识车辆11",videoUrl:"http:25.34.122.23/video/11.mp4",videoEwm:'二维码',uploadTime:"2021-05-23",updateTime:"2021-05-23"},
      ],
      addVideoFrom:{
        videoName:'',
        videoUrl:''
      },
      // 控制添加用户对话框的显示与隐藏，默认为隐藏
      addVideo: false,
      // 点击按钮，添加新用户
      /*addUser () {
        this.$refs.addFormRef.validate(async valid => {
          // 校验失败
          if (!valid) return
          // 校验成功，可以发起添加用户的网络请求
          const {data: res} = await this.$http.post('users', this.addForm)
          if (res.meta.status !== 201) {
            this.$message.error('添加用户失败！')
          }
          this.$message.success('添加用户成功！')
          // 隐藏添加用户的对话框
          this.dialogVisible = false
          // 重新获取用户列表数据
          this.getUserList()
        })
      }*/
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
