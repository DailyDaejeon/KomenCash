<template>
  <div class="m-3">
    <div class='d-flex justify-content-end'>
      <button @click="alertTuto" class="btn btn-main">튜토리얼</button>
    </div>
    <RequestItem RequestType="경위서"/>
    <h3>경위서 목록</h3>
    <CaseList/>
  </div>
</template>

<script>
import CaseList from '@/components/group/law/CaseList.vue';
import RequestItem from '@/components/group/main/RequestItem.vue';
export default {
  components: { CaseList, RequestItem },
  methods:{
    alertTuto() {
      this.$swal.queue([
        {
        title: '경위서 튜토리얼',
        html: 
            '<div id="swal2-content" class="swal2-html-container" style="display: block;"><p>1. 해당 페이지에서는 그룹의 헌법을 어겼을 시에 접수된 "경찰" 학생이 보낸 경위서를 볼 수 있습니다.</p><p>2. 어떤 잘못을 했고, 벌금이 얼마인지 확인 할 수 있습니다. 요청을 확인한 뒤 "수락"을 누르면 세금으로 돈이 들어갑니다.</p></div>',
        confirmButtonText: '확인',
      },
      ])
    },
  }
};
</script>

<style lang="scss" scoped>

</style>