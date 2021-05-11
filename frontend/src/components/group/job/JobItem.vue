<template>
  <div class="card flex-fill">
    <table class="table table-hover my-0">
      <thead>
        <tr>
          <th>{{JobType}}</th>
          <th>급여</th>
          <th>인원수</th>
          <th>역할</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(job,index) in jobData" :key="index" @click="goDetail(job.id)">
          <td>{{job.name}}</td>
          <td>{{job.salary}}</td>
          <td>{{job.personnel}}</td>
          <td>{{job.role}}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import { fetchJobDetail, modifyJob } from '@/api/job';
export default {
  data() {
    return {
      jobDetail:[]
    }
  },
  props: {
    JobType: {
      type: String
    },
    jobData: {
      type: Array
    }
  },
  methods: {
    async goDetail(jobId) {
      // this.$router.push({name:"JobDetail",params: {jobId:jobId}})
      const res = await fetchJobDetail(jobId);
      this.jobDetail = res.data
      console.log(this.jobDetail,'직업상세')
      // this.$swal({

      // })
      this.$swal({
        title: this.jobDetail.name+'상세',
        confirmButtonText:'수정',
        showCancelButton:true,
        cancelButtonText:'취소',
        html: 
        '<div id="swal2-content" class="swal2-html-container" style="display: block;">'
        +'<input id="swal-input-name" class="swal2-input" type="text" placeholder=' +`"${this.jobDetail.name}"`+'>'
        +'<input id="swal-input-role" class="swal2-input" type="text" placeholder=' +`"역할 ${this.jobDetail.role}"`+'>'
        +'<input id="swal-input-salary" class="swal2-input" type="text" placeholder=' +`"급여 ${this.jobDetail.salary}"`+'>'
        +'<input id="swal-input-qualification" class="swal2-input" type="text" placeholder='+`"자격조건 ${this.jobDetail.qualification}"`+'>'+'<input id="swal-input-personnel" class="swal2-input" type="text" placeholder='+`"인원 ${this.jobDetail.personnel}"`+'>'
        +'<input id="swal-input-recruitType" class="swal2-input" type="text" placeholder='+`"채용방식 ${this.jobDetail.recruitType}"`+'>'
        +'</div>',
        focusConfirm: false,
        preConfirm: () => {
          return [
            document.getElementById('swal-input-name').value,
            document.getElementById('swal-input-condition').value,
            document.getElementById('swal-input-role').value,
            document.getElementById('swal-input-salary').value,
            document.getElementById('swal-input-qualification').value,
            document.getElementById('swal-input-personnel').value,
            document.getElementById('swal-input-recruitType').value
          ]
        },
      }).then((result) => {
        if (result.value) {
          const jobData = {
            groupId: this.groupInfo.id,
            id: jobId,
            name: result.value[0],
            role: result.value[1],
            salary: Number(result.value[2]),
            qualification: result.value[3],
            personnel: Number(result.value[4]),
            recruitType: result.value[5],
          }
          modifyJob(jobData).then(()=>{
            this.$swal({
              title: '직업을 수정했습니다.',
              icon:'success',
              showConfirmButton: false,
              timer: 1500
            })
          })
        }
      })
    }
  }
}
</script>

<style>

</style>