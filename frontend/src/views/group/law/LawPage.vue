<template>
  <main class="content">
    <!-- <div>law</div> -->
    <div class="container-fluid p-0">
      <div class="row mb-2 mb-xl-3">
        <div class="col-auto d-none d-sm-block">
          <h3><strong>OO초 5학년 2반</strong>의 헌법</h3> <!-- {{this.$store.groupName}} -->
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
    createVote(){
      this.$swal.queue([
      {
        title: '투표 생성 1단계',
        input: 'text',
        text: '투표 주제를 정해주세요.',
        inputPlaceholder: '2021 반장 선거',
        confirmButtonText: 'Next &rarr;',
        showCancelButton: true,
        progressSteps: ['1', '2', '3', '4'],
        inputValidator: (result) => {
          return !result && '투표 주제를 작성해주세요!'
        }
      },
      {
        title: '투표 생성 2단계',
        input: 'text',
        text: '화폐 단위를 정해주세요.',
        inputPlaceholder: '원, 꿈, 미소',
        confirmButtonText: 'Next &rarr;',
        showCancelButton: true,
        progressSteps: ['1', '2'],
        inputValidator: (result) => {
          return !result && '화폐 단위를 작성해주세요!'
        }
      }
      ]).then((result) => {
      if (result.value) {
        const answers = JSON.stringify(result.value)
        //서버로 넘기는 api 추가
        this.$swal({
            title: '그룹 생성 전, 정보를 확인해주세요!',
            html: `
              Your answers:
              <pre><code>${answers}</code></pre>
            `,
            confirmButtonText: 'Lovely!'
          })
        }
      })
    }
  },
}
</script>

<style>

</style>