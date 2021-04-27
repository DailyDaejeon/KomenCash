<template>
  <main class="content">
    <!-- <div>law</div> -->
    <div class="container-fluid p-0">
      <div class="row mb-2 mb-xl-3">
        <div class="col-auto d-none d-sm-block">
          <h3><strong>OO초 5학년 2반</strong>의 헌법</h3> <!-- {{this.$store.groupName}} -->
        </div>
        <div class="col-auto near-title-btn">
          <button class="btn btn-main" @click="modifyLaw">헌법 수정하기</button>
        </div>
      </div>
      <!-- 1. 헌법 관리 -->
      <LawList />
    </div>

    <!-- 2. 헌법 준수 서약서 관리 -->
    <div class="row mb-2 mb-xl-3">
      <div class="col-auto d-none d-sm-block">
        <h3><strong>헌법 준수 서약서</strong> 관리</h3>
      </div>
    </div>

    <!-- 2. 법률 제안 요청 관리 -->
    <div class="row mb-2 mb-xl-3">
      <div class="col-auto d-none d-sm-block">
        <h3><strong>법률 제안 요청</strong> 관리</h3>
      </div>
    </div>
    <RequestItem RequestType="법률제안"/>

    <!-- 3. 투표 생성 -->
    <!-- 4. 투표 관리 -->
    <div class="row mb-2 mb-xl-3">
      <div class="col-auto d-none d-sm-block">
        <h3><strong>투표</strong> 관리</h3>
      </div>
      <div class="col-auto near-title-btn">
        <button class="btn btn-main" @click="addVote">투표 생성하기</button>
      </div>
    </div>
  </main>
</template>

<script>
import LawList from '@/components/group/law/LawList.vue'
import RequestItem from '@/components/group/main/RequestItem.vue'
export default {
  data() {
    return {
      
    }
  },
  components: { LawList, RequestItem },
  methods: {
    modifyLaw(){
      //법률 수정 폼(모달,,,,?)
      window.alert("수정 폼 들어갈꺼임");
    }
  },
}
</script>

<style>

</style>