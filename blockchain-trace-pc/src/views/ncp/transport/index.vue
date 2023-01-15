<template>
  <div class="app-container">
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5"
        ><el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          >添加运输</el-button
        ></el-col
      >
    </el-row>

    <el-table v-loading="loading" :data="transportList">
      <el-table-column label="商品ID" prop="ProductID" />
      <el-table-column label="出发地" prop="Src" />
      <el-table-column label="目的地" prop="Dst" />
      <el-table-column label="上传人" prop="Name" />
      <el-table-column label="上传时间" prop="Timestamp" />
      <el-table-column label="状态" prop="State" />
    </el-table>

    <!-- 添加或修改农作物对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="商品ID"
              ><el-input v-model="form.ProductID" placeholder="请输入商品ID"
            /></el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="出发地"
              ><el-input v-model="form.Src" placeholder="请输入出发地"
            /></el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="目的地"
              ><el-input v-model="form.Dst" placeholder="请输入目的地"
            /></el-form-item>
          </el-col>
        </el-row>   
        <el-row>
          <el-col :span="12">
            <el-form-item label="状态">
              <el-select
                v-model="form.State"
                placeholder="状态"
                clearable
                size="small"
                style="width: 240px"
              >
                <el-option
                  v-for="(dict, index) in stateOptions"
                  :key="index"
                  :label="dict.value"
                  :value="dict.value"
                /> </el-select
            ></el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="createProduct">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listTransport, saveTransport } from "../../../api/ncp";
export default {
  name: "Transport",

  data() {
    return {
      // 遮罩层
      loading: false,
      transportList: [],
      stateOptions:[{'value':'未开始'},{'value':'运输中'},{'value':'已到达'}],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 表单参数
      form: {},
      imageUrl: "",
      imgBase64: [],
      cropsPhotoUrl: "",
      cropsInfo: ""
    };
  },
  created() {
    this.getTransportList();
  },
  filters: {
    filtersImg(item) {
      if (typeof item === "string") {
        return item.split("|");
      } else {
        return item;
      }
    }
  },
  methods: {
    getFile(file) {
      this.imageUrl = URL.createObjectURL(file.raw);
      this.getBase64(file.raw).then(res => {
        this.imgBase64 = res;
        this.cropsPhotoUrl = this.imgBase64;
      });
    },

    getBase64(file) {
      return new Promise(function(resolve, reject) {
        const reader = new FileReader();
        let imgResult = "";
        reader.readAsDataURL(file);
        reader.onload = function() {
          imgResult = reader.result;
        };
        reader.onerror = function(error) {
          reject(error);
        };
        reader.onloadend = function() {
          resolve(imgResult);
        };
      });
    },

    createProduct() {
      const productInfo = {
        ProductID: this.form.ProductID,
        Src: this.form.Src,
        Dst: this.form.Dst,
        State: this.form.State,
      };
      saveTransport(productInfo)
        .then(res => {
          if (res.code === 200) {
            this.msgSuccess("后台执行成功");
            this.getTransportList();
          }
        })
        .catch(err => {
          this.msgError("后台执行失败 " + err);
          this.getTransportList();
        });
      this.open = false;
    },

    getTransportList() {
      this.loading = true;
      listTransport().then(response => {
        this.transportList = response.data;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.resetForm("form");
      this.imageUrl = '';
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.form = {};
      this.open = true;
      this.title = "运输信息";
    }
  }
};
</script>
<style>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}

.image {
  width: 80%;
  display: block;
}
/**一定要给宽高，否则不显示*/
#position {
  height: 100%;
}
#panel {
  position: fixed;
  background-color: white;
  max-height: 90%;
  overflow-y: auto;
  top: 10px;
  right: 10px;
  width: 280px;
}
#container {
  width: 100%;
  height: 100%;
}

.map {
  width: 100%;
  height: 500px;
}
</style>
