<template>
  <main class="content">
    <div class="container-fluid p-0">
      <h3><strong>월급날</strong> 버튼을 눌러주세요</h3>
      <button @click="giveSalary" class="btn btn-main">월급지급</button>
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
      </div>
      <JobList/>
    </div>
  </main>
</template>

<script>
import JobList from '@/components/group/job/JobList.vue'
import JobRequestList from '@/components/group/job/JobRequestList.vue'
import { mapState } from 'vuex'
import { salaryPaymentRequest } from '@/api/student'
export default {
  components: { JobList, JobRequestList },
  computed : {
    ...mapState({
      groupInfo: state => state.group.groupInfo
    })
  },
  methods: {
    giveSalary() {
      this.$swal({
        title:'직업 급여를 지급하겠습니까?',
        icon:'question',
        confirmButtonText:'지급',
        showCancelButton:true,
        cancelButtonText:'취소'
      }).then((res)=>{
        if (res.isConfirmed) {
          salaryPaymentRequest(this.groupInfo.id).then(()=> {
            this.$swal({
              text:'성공적으로 월급 요청을 했습니다. 은행원에게 승인받으세요.',
              icon:'success'              
            })
          })
        }
      })
      
    }
  },
}
</script>

<style lang="scss">
</style>