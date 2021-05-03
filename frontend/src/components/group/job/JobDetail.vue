<template>
  <main class="d-flex w-100">
    <div class="container d-flex flex-column">
      <div class="row vh-100">
        <div class="col-sm-10 col-md-8 col-lg-6 mx-auto d-table h-100">
          <div class="d-table-cell">
            <div class="card">
              <div class="card-body">
                <div class="m-sm-4">
                  <form @submit.prevent="modifyJobInfo">
                    <div class="mb-3">
                      <label class="form-label">직업이름</label>
                      <input type="text" class="h3"
                      v-model="jobName"
                      /> 
                    </div>
                    <div class="mb-3" v-for="member in jobMembers" :key="member.memberId">
                      <label class="form-label">맡은학생이름</label>
                      <input type="text" class="h3"
                      v-model="member.memberName"
                      />                 
                    </div>
                    <div class="mb-3">
                      <label class="form-label">급여</label>
                      <input type="text" class="h3"
                      v-model="jobSalary"
                      />                    
                    </div>
                    <div class="mb-3">
                      <label class="form-label">역할</label>
                      <input type="text" class="h3"
                      v-model="jobRole"
                      />                   
                    </div>
                    <div class="mb-3">
                      <label class="form-label">자격조건</label>
                      <input type="text" class="h3"
                      v-model="jobCerti"
                      />                   
                    </div>
                    <div class="text-center mt-3">
                      <button 
                      type="submit" class="btn btn-lg btn-main">수정</button>
                    </div>
                  </form>
                </div>
              </div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</main>
</template>

<script>
import { fetchJobDetail } from '@/api/job';
export default {
  props:{
    JobType:{
      type:String,
    },
    jobId:{
      type:Number
    }
  },
  data() {
    return {
      jobData:[],
      jobName: "은행원",
      jobSalary: "100미소",
      jobRole : "잡다한일..ㅎㅅㅎ",
      jobPersonnel:2,
      recruitType:"정직원",
      jobCerti:"국영수1등급",
      jobMembers: [{
        memberId:1,
        memberName:"박싸피"
      },
      {
        memberId:2,
        memberName:"김싸피"
      }]
    }
  },
  methods: {
    async fetchDetail() {
      const res = await fetchJobDetail(this.jobId);
      this.jobData = res.data
    },
    async modifyJobInfo(){
      if(!this.jobName) {
        this.$swal({
        text: "직업 이름을 입력하세요.",
        customClass: {
          container: 'swal2-container'
        },
        icon: 'info',
        timer: 1300,
        showConfirmButton: false,
      })
        return;
      }
      if(!this.jobSalary) {
        this.$swal({
          customClass: {
          container: 'swal2-container'
        },
        text: "급여를 확인하세요.",
        icon: 'info',
        timer: 1300,
        showConfirmButton: false,
      })
        return;
      }
      if(!this.jobRole) {
        this.$swal({
        text: "역할을 설정해주세요.",
        customClass: {
          container: 'swal2-container'
        },
        icon: 'info',
        timer: 1300,
        showConfirmButton: false,
      })
        return;
      }
      
      try {
        const jobData = {
          jobId : this.jobId,
          jobName: this.jobName,
          jobSalary:this.jobSalary,
          jobRole : this.jobRole,
          jobPersonnel:this.jobPersonnel,
          recruitType:this.recruitType,
          jobCerti:this.jobCerti,
          jobMembers: this.jobMembers
        };
        console.log(jobData)
        // await this.$store.dispatch('MODIFY',jobData)
        this.closeUserInfoModal();
          this.$swal({
          customClass: {
        container: 'swal2-container'
        },
          text: '직업정보 수정을 완료했습니다.',
          icon: 'success',
          timer: 1300,
          showConfirmButton: false,

      }).then(()=>{
        // 정보갱신
              
        // window.location.reload();
        
      })
      }catch(err) {
        console.log(err);
      }
    },
  }
}
</script>

<style>

</style>