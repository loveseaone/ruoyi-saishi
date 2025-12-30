<template>
  <div class="app-container home">
    <el-row :gutter="20">
      <el-col :sm="24" :lg="24" style="padding-left: 20px">
        <!-- 角色统计图表容器 -->
        <div ref="roleChartContainer" class="chart-container">
          <div ref="roleChart" class="chart"></div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import * as echarts from 'echarts'
import { listAthletes } from '@/api/activity/athletes'

export default {
  name: "Index",
  data() {
    return {
      roleChart: null
    }
  },
  mounted() {
    this.fetchRoleData()
  },
  beforeDestroy() {
    if (this.roleChart) {
      this.roleChart.dispose()
    }
  },
  methods: {
    // 获取角色统计数据
    fetchRoleData() {
      // 使用pageNum=1&pageSize=1000参数请求数据
      listAthletes({ pageNum: 1, pageSize: 1000 }).then(response => {
        console.log('API Response:', response)
        
        if (response.code === 200) {
          // 读取数据list集合
          const athletes = response.rows || []
          console.log('Athletes Data:', athletes)
          
          // 按角色分组统计
          const roleStats = {}
          athletes.forEach(athlete => {
            const role = athlete.role || '未指定'
            if (!roleStats[role]) {
              roleStats[role] = 0
            }
            roleStats[role]++
          })
          
          console.log('Role Stats:', roleStats)
          
          // 转换为图表所需格式
          const roles = Object.keys(roleStats)
          const counts = roles.map(role => roleStats[role])
          
          console.log('Roles:', roles)
          console.log('Counts:', counts)
          
          this.$nextTick(() => {
            this.initRoleChart(roles, counts)
          })
        } else {
          console.error('API Error:', response.msg || 'Unknown error')
          // 出错时使用模拟数据
          this.useMockData()
        }
      }).catch(error => {
        console.error('Request failed:', error)
        // 请求失败时使用模拟数据
        this.useMockData()
      })
    },
    
    // 使用模拟数据（错误处理）
    useMockData() {
      const roles = ['领队', '教练', '工会人员', '男队员', '女队员']
      const counts = [5, 8, 12, 25, 20]
      console.log('Using mock data - Roles:', roles)
      console.log('Using mock data - Counts:', counts)
      this.$nextTick(() => {
        this.initRoleChart(roles, counts)
      })
    },
    
    // 初始化角色统计图表（折线图）
    initRoleChart(roles, counts) {
      console.log('Initializing chart with:', { roles, counts })
      
      // 销毁之前的实例
      if (this.roleChart) {
        this.roleChart.dispose()
      }
      
      // 检查DOM元素是否存在
      if (!this.$refs.roleChart) {
        console.error('Chart container not found')
        return
      }
      
      // 初始化图表
      this.roleChart = echarts.init(this.$refs.roleChart)
      
      // 配置图表选项
      const option = {
        title: {
          text: '队员角色统计',
          left: 'center'
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross'
          }
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: {
          type: 'category',
          data: roles,
          axisTick: {
            alignWithLabel: true
          }
        },
        yAxis: {
          type: 'value',
          name: '人数'
        },
        series: [{
          name: '角色人数',
          type: 'line',
          data: counts,
          smooth: true,
          lineStyle: {
            width: 3
          },
          itemStyle: {
            color: '#409EFF',
            borderWidth: 2
          },
          emphasis: {
            focus: 'series'
          },
          areaStyle: {
            opacity: 0.3,
            color: '#409EFF'
          },
          markPoint: {
            data: [
              { type: 'max', name: '最大值' },
              { type: 'min', name: '最小值' }
            ]
          },
          markLine: {
            data: [
              { type: 'average', name: '平均值' }
            ]
          }
        }]
      }
      
      // 设置配置项
      this.roleChart.setOption(option, true)
      
      // 监听窗口大小变化
      window.addEventListener('resize', this.handleRoleChartResize)
    },
    
    // 处理窗口大小变化（角色图表）
    handleRoleChartResize() {
      if (this.roleChart) {
        this.roleChart.resize()
      }
    }
  }
}
</script>

<style scoped lang="scss">
.home {
  font-family: "open sans", "Helvetica Neue", Helvetica, Arial, sans-serif;
  font-size: 13px;
  color: #676a6c;
  overflow-x: hidden;
  
  .chart-container {
    width: 100%;
    height: 500px;
    margin-top: 20px;
  }
  
  .chart {
    width: 100%;
    height: 100%;
  }
}
</style>