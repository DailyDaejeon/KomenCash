<template>
  <main class="content">
    <div class="container-fluid p-0">
      <div class="row mb-2 mb-xl-3">
        <div class="col-auto d-none d-sm-block">
          <h3><strong>Request</strong> Dashboard</h3>
        </div>  
      </div>
      <JobRequestList/>
      <div class="row mb-2 mb-xl-3">
        <div class="col-auto d-none d-sm-block">
          <h3><strong>Job</strong> Dashboard</h3>
        </div>
        <div class="col-auto near-title-btn" @click="createJob">
          <button class="btn btn-main">직업추가</button>
        </div>
      </div>
      <JobList/>
    </div>
  </main>
</template>

<script>
import JobList from '@/components/group/job/JobList.vue'
import JobRequestList from '@/components/group/job/JobRequestList.vue'
import { addJob } from '@/api/job'
export default {
  components: { JobList, JobRequestList },
  methods: {
    createJob() {
      this.$swal.queue([
      {
        title: '직업추가 1단계',
        input: 'text',
        text: '직업명을 지정해주세요.',
        inputPlaceholder: '은행원',
        confirmButtonText: 'Next &rarr;',
        showCancelButton: true,
        progressSteps: ['1', '2', '3','4','5','6'],
      },
      {
        title: '직업추가 2단계',
        input: 'text',
        text: '월급을 책정해주세요',
        inputPlaceholder: '100미소',
        confirmButtonText: 'Next &rarr;',
      showCancelButton: true,
      progressSteps: ['1', '2', '3','4','5','6']
      },
      {
        title: '직업추가 3단계',
        input: 'text',
        text: '역할을 적어주세요.',
        inputPlaceholder: '그룹원의 월급을 담당합니다.',
        confirmButtonText: 'Next &rarr;',
      showCancelButton: true,
      progressSteps: ['1', '2', '3','4','5','6']
      },
      {
        title: '직업추가 4단계',
        input: 'number',
        text: '인원수를 적어주세요.',
        inputPlaceholder: '0',
        confirmButtonText: 'Next &rarr;',
      showCancelButton: true,
      progressSteps: ['1', '2', '3','4','5','6']
      },
      {
        title: '직업추가 5단계',
        input: 'text',
        text: '자격조건을 적어주세요.',
        inputPlaceholder: '수학 자격증 4급 이상',
        confirmButtonText: 'Next &rarr;',
      showCancelButton: true,
      progressSteps: ['1', '2', '3','4','5','6']
      },
      {
        title: '직업추가 6단계',
        input: 'select',
        inputOptions:{vote:'투표',resume:'이력서'},
        text: '선출방식을 선택해주세요.',
        inputPlaceholder: '선출방식을 정해주세요',
        confirmButtonText: 'Next &rarr;',
      showCancelButton: true,
      progressSteps: ['1', '2', '3','4','5','6']
      }
      ]).then((result) => {
      if (result.value) {
        const answers = JSON.stringify(result.value)
        const jobData = {
          groupId: this.groupId,
          id: null,
          name: result.value[0],
          salary: Number(result.value[1]),
          role: result.value[2],
          personnel: Number(result.value[3]),
          // 자격도 추가
          recruitType: result.value[5],
        }
      this.$swal({
          title: '직업 생성 전, 정보를 확인해주세요!',
          html: `
            Your answers:
            <pre><code>${answers}</code></pre>
          `,
          confirmButtonText: 'Lovely!'
        }).then(()=>{
            addJob(jobData)
          })
      }
      })
    }
  },
}
</script>

<style lang="scss">
</style>