<template>
  <main class="content">
    <div class='d-flex justify-content-end'>
      <button @click="alertTuto" class="btn btn-main">튜토리얼</button>
    </div>
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
    alertTuto() {
      this.$swal.queue([
        {
        title: '직업 튜토리얼',
        html: 
            '<div id="swal2-content" class="swal2-html-container" style="display: block;"><p>1. 해당 페이지에서는 그룹의 직업과 아르바이트를 확인할 수 있습니다.</p><p>2. "월급지급" 버튼은 직업을 가진 그룹원들에게 월급을 주는 버튼으로, 클릭 시 "은행원"인 학생이 월급 수락 요청을 할 수 있도록 요청을 보냅니다.</p><p>3. 아래에는 학생들이 보낸 이력서와 직업 추가 요청을 볼 수 있습니다.</p><p>4. 직업리스트에서는 그룹의 직업을 추가, 수정, 삭제를 할 수 있습니다.</p><p>5. 아르바이트는 단시간에 할 수 있는 일입니다. 선생님이 추가를 하면, 학생페이지에서 할 사람이 요청을 보내고, 선생님이 일당을 지급합니다.</p></div>',
        confirmButtonText: '확인',
        // showCancelButton: true,
        
      },
      ])
    },
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