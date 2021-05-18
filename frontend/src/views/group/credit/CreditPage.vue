<template>
  <div>
    <div class='d-flex justify-content-end mb-3'>
      <button @click="alertTuto" class="btn btn-main">튜토리얼</button>
    </div>
    <GroupCreditPoint/>
    <GroupCreditChart/>
  </div>
</template>

<script>
import GroupCreditChart from '@/components/group/credit/GroupCreditChart.vue'
import GroupCreditPoint from '@/components/group/credit/GroupCreditPoint.vue';


export default {
  components: { GroupCreditChart, GroupCreditPoint },
  methods: {
    alertTuto() {
      this.$swal.queue([
        {
        title: '신용등급 튜토리얼',
        html: 
            '<div id="swal2-content" class="swal2-html-container" style="display: block;"><p>1. 해당 페이지에서는 그룹의 신용등급을 볼 수 있는 페이지입니다.</p><p>2. 그룹원의 신용등급 현황을 차트로 볼 수 있습니다.</p><p>3. 표에는 각 그룹원의 신용등급과 신용점수를 볼 수 있습니다.</p></div>',
        confirmButtonText: '확인',

      },
      ])
    },
  }
}
</script>

<style>

</style>