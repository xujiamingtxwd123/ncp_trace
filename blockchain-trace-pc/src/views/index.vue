<template>
  <div class="dashboard-editor-container">
    <!-- <h1>基于区块链技术的农产品溯源系统</h1>
    <h3>描述：项目基于开源区块链底层平台Hyperledger Fabric,原材料生产、加工、运输、销售等全流程记录到区块链上</h3> -->
    <h1>{{ hello }}</h1>
  </div>
</template>

<script>
import PanelGroup from "./dashboard/PanelGroup";
import LineChart from "./dashboard/LineChart";
import RaddarChart from "./dashboard/RaddarChart";
import PieChart from "./dashboard/PieChart";
import BarChart from "./dashboard/BarChart";

const lineChartData = {
  newVisitis: {
    expectedData: [100, 120, 161, 134, 105, 160, 165],
    actualData: [120, 82, 91, 154, 162, 140, 145]
  },
  messages: {
    expectedData: [200, 192, 120, 144, 160, 130, 140],
    actualData: [180, 160, 151, 106, 145, 150, 130]
  },
  purchases: {
    expectedData: [80, 100, 121, 104, 105, 90, 100],
    actualData: [120, 90, 100, 138, 142, 130, 130]
  },
  shoppings: {
    expectedData: [130, 140, 141, 142, 145, 150, 160],
    actualData: [120, 82, 91, 154, 162, 140, 130]
  }
};

export default {
  name: "Index",
  components: {
    PanelGroup,
    LineChart,
    RaddarChart,
    PieChart,
    BarChart
  },
  data() {
    return {
      hello: "",
      lineChartData: lineChartData.newVisitis
    };
  },
  created() {
    var role1 = this.$store.getters.roles[0];
    switch (role1) {
      case "zhongzhihu":
        this.hello = '欢迎您！农场操作员'
        break;
      case "driver":
         this.hello = '欢迎您！运输员'
        break;
      case "regulator":
        this.hello = '监管'
        break;
      case "consumer":
         this.hello = '消费者'
        break;
      case "material":
         this.hello = '欢迎您！质检员'
        break;
      case "factoryLeader":
         this.hello = '欢迎您！加工操作员'
        break;
      case "retailer":
         this.hello = '欢迎您！销售员'
        break;
      case "admin":
          this.hello = '欢迎您！管理员'
          break;
    }
    
  },
  methods: {
    handleSetLineChartData(type) {
      this.lineChartData = lineChartData[type];
    }
  }
};
</script>

<style lang="scss" scoped>
.dashboard-editor-container {
  padding: 32px;
  background-color: rgb(240, 242, 245);
  position: relative;

  .chart-wrapper {
    background: #fff;
    padding: 16px 16px 0;
    margin-bottom: 32px;
  }
}

@media (max-width: 1024px) {
  .chart-wrapper {
    padding: 8px;
  }
}
</style>
