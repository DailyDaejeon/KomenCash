<template>
  <div>
    <GroupCreditChart :propsdata="chartData"/>
    <GroupCreditTable :studentList="memberData"/>
  </div>
</template>

<script>
import GroupCreditChart from '@/components/credit/GroupCreditChart.vue'
import GroupCreditTable from '@/components/credit/GroupCreditTable.vue'
import { fetchCreditList } from '@/api/credit'
import { mapState } from 'vuex';


export default {
  components: { GroupCreditChart, GroupCreditTable },
  data() {
    return {
      creditList : {
        "1":[],
        "2":[],
        "3":[],
        "4":[],
        "5":[],
        "6":[],
        "7":[],
        "8":[],
        "9":[],
        "10":[],
      },
      chartData:[],
      memberData:[]
    }
  },
  created() {
    this.fetchCredit()
  },
  computed: {
    ...mapState({
      groupInfo: state=>state.group.groupInfo
    })
  },
  methods:{
    async fetchCredit() {
      const res = await fetchCreditList(this.groupInfo.id)
      this.memberData = res.data
      this.memberData.forEach(element => {
        this.creditList[String(element.grade)].push(element)
      });
      for (let i = 1; i < 11; i++) {
        const element = this.creditList[String(i)].length;
        this.chartData.push(element)
        
      }
    }
  }
}
</script>

<style>

</style>