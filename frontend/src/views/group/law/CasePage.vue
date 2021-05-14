<template>
  <div class="m-3">
    <!-- <button class="btn btn-main" @click="addCase">경위서추가</button> -->
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
    addCase() {
      // 경찰이 신청
    }
  }
};
</script>

<style lang="scss" scoped>

</style>