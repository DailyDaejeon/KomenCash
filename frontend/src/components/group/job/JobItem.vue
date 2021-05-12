<template>
  <div class="card flex-fill">
    <table class="table table-hover my-0">
      <thead>
        <tr>
          <th>{{JobType}}</th>
          <th>역할</th>
          <th>급여</th>
          <th>자격조건</th>
          <th>인원수</th>
          <th>채용방식</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(job,index) in jobData" :key="index" @click="goDetail(job)">
          <td>{{job.name}}</td>
          <td>{{job.role}}</td>
          <td>{{job.salary}}</td>
          <td v-if="job.qualification">{{job.qualification}}</td>
          <td v-else-if="!job.qualification">없음</td>
          <td>{{job.personnel}}</td>
          <td>{{job.recruitType}}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
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
    async goDetail(job) {
      this.$router.push({name:"JobDetail",params: {
        dataName : job.name,
        id:job.id,
        propsData:job
      }})
      // const res = await fetchJobDetail(jobId);
      // this.jobDetail = res.data
      // console.log(this.jobDetail,'직업상세')
      // const inputOptions = [];
      // this.jobDetail.jobStudentResponses.forEach((el) => {
      //   inputOptions.push(el.studentNickName)
      // })
        
      // this.$swal({
      //   title: `직업이 ${this.jobDetail.name}인 그룹원 보기`,
      //   input: 'radio',
      //   inputOptions:inputOptions,
      //   confirmButtonText:'직업수정',
      // }).then((result)=>{
      //   console.log(result)
      //   if (result.isConfirmed) {
      //     this.$swal({
      //       title: this.jobDetail.name+'상세',
      //       confirmButtonText:'수정',
      //       showCancelButton:true,
      //       cancelButtonText:'취소',
      //       html: 
      //       '<div id="swal2-content" class="swal2-html-container" style="display: block;">'
      //       +'<input id="swal-input-name" class="swal2-input" type="text" placeholder=' +`"${this.jobDetail.name}"`+'>'
      //       +'<input id="swal-input-role" class="swal2-input" type="text" placeholder=' +`"역할 ${this.jobDetail.role}"`+'>'
      //       +'<input id="swal-input-salary" class="swal2-input" type="text" placeholder=' +`"급여 ${this.jobDetail.salary}"`+'>'
      //       +'<input id="swal-input-qualification" class="swal2-input" type="text" placeholder='+`"자격조건 ${this.jobDetail.qualification}"`+'>'+'<input id="swal-input-personnel" class="swal2-input" type="text" placeholder='+`"인원 ${this.jobDetail.personnel}"`+'>'
      //       +'<input id="swal-input-recruitType" class="swal2-input" type="text" placeholder='+`"채용방식 ${this.jobDetail.recruitType}"`+'>'
      //       +'</div>',
      //       focusConfirm: false,
      //       preConfirm: () => {
      //         return [
      //           document.getElementById('swal-input-name').value,
      //           document.getElementById('swal-input-condition').value,
      //           document.getElementById('swal-input-role').value,
      //           document.getElementById('swal-input-salary').value,
      //           document.getElementById('swal-input-qualification').value,
      //           document.getElementById('swal-input-personnel').value,
      //           document.getElementById('swal-input-recruitType').value
      //         ]
      //       },
      //     }).then((result) => {
      //       console.log(result)
      //       if (result.value) {
      //         const jobData = {
      //           groupId: this.groupInfo.id,
      //           id: jobId,
      //           name: result.value[0],
      //           role: result.value[1],
      //           salary: Number(result.value[2]),
      //           qualification: result.value[3],
      //           personnel: Number(result.value[4]),
      //           recruitType: result.value[5],
      //         }
      //         modifyJob(jobData).then(()=>{
      //           this.$swal({
      //             title: '직업을 수정했습니다.',
      //             icon:'success',
      //             showConfirmButton: false,
      //             timer: 1500
      //           })
      //         })
      //       }
      //     })
      //   }
      // })
      
    }
  }
}
</script>

<style scoped>
/* input[type="radio"] */
.swal2-radio  {
  display: none !important;
  visibility:hidden  !important;
}
.swal2-checkbox input, .swal2-radio input {
  margin: 0 .4em;
  display: none !important;
}
</style>