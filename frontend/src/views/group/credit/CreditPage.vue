<template>
  <div>
    <GroupCreditPoint/>
    <GroupCreditChart/>
  </div>
</template>

<script>
import GroupCreditChart from '@/components/group/credit/GroupCreditChart.vue'
import GroupCreditPoint from '@/components/group/credit/GroupCreditPoint.vue';


export default {
  components: { GroupCreditChart, GroupCreditPoint }

}
</script>

<style>

</style>