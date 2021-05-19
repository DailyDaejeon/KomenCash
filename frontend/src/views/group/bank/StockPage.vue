<template>
  <main class="content">
    <div class='d-flex justify-content-end mb-3'>
      <button @click="alertTuto" class="btn btn-main">튜토리얼</button>
    </div>
    <StockList/>
  </main>
</template>

<script>
import StockList from '@/components/group/stock/StockList.vue'

export default {
  components: { StockList },
  methods : {
    alertTuto() {
      this.$swal.queue([
        {
        title: '주식 튜토리얼',
        html: 
            '<div id="swal2-content" class="swal2-html-container" style="display: block;"><p>1. 해당 페이지에서는 그룹의 주식을 관리 할 수 있는 페이지입니다.</p><p>2. 학생들이 다음 날 유추를 할 수 있는 힌트와 함께 주식을 새로 추가할 수 있습니다.</p><p>3. 주식을 클릭하면 주가 변동 현황 차트와 주식 내용을 수정 및 삭제를 할 수 있습니다.</p></div>',
        confirmButtonText: '확인',
      },
      ])
    },
  }

}
</script>