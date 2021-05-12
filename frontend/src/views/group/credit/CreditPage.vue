<template>
  <div>
    <GroupCreditPoint/>
    <GroupCreditChart :propsdata="chartData"/>
    <GroupCreditTable :studentList="memberData"/>
  </div>
</template>

<script>
import GroupCreditChart from '@/components/group/credit/GroupCreditChart.vue'
import GroupCreditTable from '@/components/group/credit/GroupCreditTable.vue'
import { fetchMemeberCreditList } from '@/api/credit'
import { mapState } from 'vuex';
import GroupCreditPoint from '@/components/group/credit/GroupCreditPoint.vue';


export default {
  components: { GroupCreditChart, GroupCreditTable, GroupCreditPoint },
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
      const res = await fetchMemeberCreditList(this.groupInfo.id)
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