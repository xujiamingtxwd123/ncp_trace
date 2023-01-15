<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
       ref="queryForm"
      :inline="true"
    >
      <el-form-item label="商品ID" prop="cropsName">
        <el-input
          v-model="queryParams.productID"
          placeholder="请输入商品ID"
          clearable
          size="small"
          style="width: 240px"
        />
      </el-form-item>
      <el-form-item>
        <el-button
          type="cyan"
          icon="el-icon-search"
          size="mini"
          @click="handleQuery"
          >搜索</el-button
        >
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="cropsList">
      <!-- <el-table-column type="selection" width="55" align="center" /> -->
      <el-table-column label="作物ID" prop="CropID" />
      <el-table-column label="上传人" prop="Name" />
      <el-table-column label="上传时间" prop="Timestamp" />
      <el-table-column label="生长状况" prop="Healthy" />
      <el-table-column label="农户操作" prop="Action" />
      <el-table-column label="图片" class="demo-image__preview">
        <template slot-scope="scope">
          <el-image
            :src="scope.row.Pic"
            style="width: 100px;height: 80px"
          ></el-image>
        </template>
      </el-table-column>
    </el-table>

    <el-table v-loading="loading" :data="processesList">
      <!-- <el-table-column type="selection" width="55" align="center" /> -->
      <el-table-column label="商品ID" prop="ProductID" />
      <el-table-column label="作物ID" prop="CropID" />
      <el-table-column label="上传人" prop="Name" />
      <el-table-column label="上传时间" prop="Timestamp" />
      <el-table-column label="处理工序" prop="Process" />
      <el-table-column label="图片" class="demo-image__preview">
        <template slot-scope="scope">
          <el-image
            :src="scope.row.Pic"
            style="width: 100px;height: 80px"
          ></el-image>
        </template>
      </el-table-column>
    </el-table>

    <el-table v-loading="loading" :data="salesList">
      <el-table-column label="商品ID" prop="ProductID" />
      <el-table-column label="购买者" prop="ConsumerName" />
      <el-table-column label="上传时间" prop="Timestamp" />
    </el-table>
    
  </div>
</template>

<script>
import { trace } from "../../../api/ncp";
export default {
  name: "Trace",

  data() {
    return {
      // 遮罩层
      loading: false,
      // 角色表格数据
      cropsList: [],
      processesList: [],
      transportsList: [],
      salesList:[],
      sale: {},
      // 表单参数
      form: {},
      queryParams: {},
    };
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
    handleQuery() {
      trace(this.queryParams.productID).then(response => {
        this.cropsList = response.data.TraceCrop;
        this.processesList = response.data.TraceProcess;
        this.transportsList = response.data.TraceTransport;
        this.salesList = response.data.TraceSale;
      });
    },
    // 表单重置
    reset() {
      this.resetForm("form");
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
