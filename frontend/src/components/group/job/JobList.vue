<template>
	<div class="row">
    <div class="w-100">
      <div class="card-header">
        <h4 class="card-title mb-0 d-inline-block">Job list</h4>
        <button class="btn btn-main m-3" @click="createJob">직업추가</button>
        <JobItem JobType="Job" :jobData="jobList"/>
      </div>
      <div class="card-header">
        <h4 class="card-title mb-0 d-inline-block">Part-time list</h4>
        <button class="btn btn-main m-3" @click="createPart">아르바이트추가</button>
        <JobItem JobType="PartTime" :jobData="partTimeList"/>
      </div>
    </div>
  </div>
</template>

<script>
import { fetchJobList, fetchPartTimeList, addJob, addPartTime } from '@/api/job';
import JobItem from './JobItem.vue'
import { mapState } from 'vuex';

export default {
  components: { JobItem },
  data() {
    return {
      jobList :[],
      partTimeList :[]

    }
  },
  created() {
    this.fetchJob();
    this.fetchPart();
  },
  computed : {
    ...mapState({
      groupInfo: state => state.group.groupInfo,
    }),
  },
  methods: {
    async fetchJob() {
      const job = await fetchJobList(this.groupInfo.id);
      this.jobList = job.data
    },
    async fetchPart() {
      const partTime = await fetchPartTimeList(this.groupInfo.id);
      this.partTimeList = partTime.data
    },
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
          groupId: this.groupInfo.id,
          name: result.value[0],
          salary: Number(result.value[1]),
          role: result.value[2],
          personnel: Number(result.value[3]),
          // 자격도 추가
          qualification: result.value[4],
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
            this.jobList.push(jobData)
            this.fetchJob();
          })
      }
      })
    },
    createPart() {
      this.$swal.queue([
      {
        title: '아르바이트추가 1단계',
        input: 'text',
        text: '아르바이트명을 지정해주세요.',
        inputPlaceholder: '청소',
        confirmButtonText: 'Next &rarr;',
        showCancelButton: true,
        progressSteps: ['1', '2', '3'],
      },
      {
        title: '아르바이트추가 2단계',
        input: 'text',
        text: '일급을 책정해주세요',
        inputPlaceholder: '10미소',
        confirmButtonText: 'Next &rarr;',
      showCancelButton: true,
      progressSteps: ['1', '2', '3']
      },
      {
        title: '아르바이트추가 3단계',
        input: 'text',
        text: '역할을 적어주세요.',
        inputPlaceholder: '선생님의 오늘 청소를 도와줍니다.',
        confirmButtonText: 'Next &rarr;',
      showCancelButton: true,
      progressSteps: ['1', '2', '3']
      }
      ]).then((result) => {
      if (result.value) {
        const answers = JSON.stringify(result.value)
        const jobData = {
          groupId: this.groupInfo.id,
          name: result.value[0],
          salary: Number(result.value[1]),
          role: result.value[2],
        }
      this.$swal({
          title: '아르바이트 생성 전, 정보를 확인해주세요!',
          html: `
            Your answers:
            <pre><code>${answers}</code></pre>
          `,
          confirmButtonText: 'Lovely!'
        }).then(()=>{
            addPartTime(jobData)
            this.partTimeList.push(jobData)
            this.fetchPart();
          })
      }
      })
    }
  },
}
</script>

<style>

</style>