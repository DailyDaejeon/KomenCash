<template>
<div class="card-body">
  <div class="row">
    <div v-if="mActive" class="col-12">
      <h3 class="text-main ">직업 상세 내용을 수정한 뒤 수정완료 버튼을 눌러주세요.</h3>
    </div>
    <div class="col-6">
      <h3>직업명</h3>
    </div>
    <div class="col-6">
      <span class="h3" v-if="!mActive">{{jobData.name}}</span>
      <span class="h3" v-else>
        <input class="border border-main" type="text" v-model="jobData.name">
      </span>
    </div>
    <div class="col-6">
      <h3>급여</h3>
    </div>
    <div class="col-6">
      <span class="h3" v-if="!mActive">{{jobData.salary}}</span>
      <span class="h3" v-else>
        <input class="border border-main" type="text" v-model="jobData.salary">
      </span>
    </div>
    
    <div class="col-6">
      <h3>역할</h3>
    </div>
    <div class="col-6">
      <span class="h3" v-if="!mActive">{{jobData.role}}</span>
      <span class="h3" v-else>
        <input class="border border-main" type="text" v-model="jobData.role">
      </span>
    </div>
    <div class="col-6">
      <h3>인원</h3>
    </div>
    <div class="col-6">
      <span class="h3" v-if="!mActive">
        {{jobData.personnel}}
      </span>
      <span class="h3" v-else>
        <input class="border border-main" type="text" v-model="jobData.personnel">
      </span>
    </div>
    <div class="col-6">
      <h3>자격조건</h3>
    </div>
    <div class="col-6">
      <span class="h3" v-if="!mActive && jobData.qualification">
        {{jobData.qualification}}
      </span>
      <span class="h3" v-else-if="!mActive && !jobData.qualification">
        자격조건이 없습니다.
      </span>
      <span class="h3" v-else-if="mActive">
        <input class="border border-main" type="text" v-model="jobData.qualification">
      </span>
    </div>
    <div class="col-6">
      <h3>채용방식</h3>
    </div>
    <div class="col-6">
      <span class="h3" v-if="!mActive && jobData.name === '무직'">
        없음
      </span>
      <span class="h3" v-else-if="!mActive && jobData.recruitType === 'vote'">
        투표
      </span>
      <span class="h3" v-else-if="!mActive && jobData.recruitType === 'resume'">
        이력서
      </span>
      <div class="h3" v-else-if="mActive">
        <input type="radio"
          name="recruitType"
          :id="isRecruitType(true)"
         :value="isRecruitType(true)" checked
         @click="changeRecruit(isRecruitType(true))"
         >
        <label :for="isRecruitType(true)">
          <span 
          class="mr-3" v-if="isRecruitType(true) === 'resume' ">이력서</span>
          <span class="mr-3" v-else>투표</span>
        </label>
        <input type="radio"
        name="recruitType" 
        @click="changeRecruit(isRecruitType(false))"
        :id="isRecruitType(false)"
         :value="isRecruitType(false)">
         <label :for="isRecruitType(false)">
          <span v-if="isRecruitType(false) === 'resume' ">이력서</span>
          <span v-else>투표</span>
        </label>
      </div>
    </div>
    <div class="col-6">
      <h3>해당 직업 그룹원 목록</h3>
    </div>
    <div class="col-12">
      <table v-if="jobData.jobStudentResponses.length" class="text-center table table-hover my-0">
        <thead>
          <tr>
            <th>No.</th>
            <th>이름</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(member,index) in paginatedData"
          :key="index">
            <td>{{index+1+10*(pageNum)}}</td>
            <td>{{member.studentNickName}}</td>
          </tr>
        </tbody>
      </table>
      <div  v-if="paginatedData.length" class="btn-cover text-center">
        <button :disabled="pageNum === 0" @click="prevPage" class="page-btn mr-3">이전</button>
        <span class="page-count mr-3">{{pageNum+1}}/{{pageCount}} 페이지 </span>
        <button :disabled="pageNum >= pageCount-1"  @click="nextPage" class="page-btn">다음</button>
    </div>
      <p class="h5" v-else>
        해당 직업인 그룹원이 없습니다.
      </p>
    </div>

    <button class="btn btn-main mx-auto" @click="modifyJobInfo">
      <span v-if="!mActive">
        직업수정
      </span>
      <span v-else>
        수정완료
      </span>
      </button>
  </div>
</div>
</template>

<script>
import { fetchJobDetail, modifyJob } from '@/api/job';
import { mapState } from 'vuex';
export default {
  props:['id','propsData','dataName'],
  data() {
    return {
      pageSize:10,
      pageNum:0,
      jobData:{
        id: 0,
        jobStudentResponses: [],
        name: '', 
        personnel: 0,
        qualification: '',
        recruitType: '',
        role: '',
        salary: 0,
      },
      recruitType:'',
      mActive:false,
    }
  },
  created() {
    this.fetchDetail()
  },
  computed: {
    ...mapState({
      groupInfo:state => state.group.groupInfo
    }),
    pageCount() {
      let listLeng = this.jobData.jobStudentResponses.length,
          listSize = this.pageSize,
          page = Math.floor(listLeng / listSize);

      if(listLeng % listSize > 0) page += 1;

      return page;
    },
    paginatedData() {
      const start = this.pageNum * this.pageSize,
            end = start + this.pageSize;

      return this.jobData.jobStudentResponses.slice(start, end);
    }
  },
  methods: {
    nextPage() {
      this.pageNum += 1;
    },
    prevPage() {
      this.pageNum -= 1;
    },
    changeRecruit(recruit) {
      this.recruitType = recruit
    },
    isRecruitType(boolean) {
      let res = ''
      if (boolean) {
        res = this.jobData.recruitType === 'resume' ? 'resume' : 'vote'
      } else {
        res = this.jobData.recruitType === 'resume' ? 'vote' : 'resume'
      }
      return res
    },
    async fetchDetail() {
      const res = await fetchJobDetail(this.id);
      this.jobData = res.data
      this.recruitType = this.jobData.recruitType
    },
    async modifyJobInfo(){
      this.mActive = !this.mActive
      const jobData = {
        groupId: this.groupInfo.id,
        id: this.jobData.id,
        name: this.jobData.name,
        personnel: this.jobData.personnel,
        qualification: this.jobData.qualification,
        recruitType: this.recruitType,
        role: this.jobData.role,
        salary: this.jobData.salary
      };
        console.log('수정데이터',jobData)
        // await this.$store.dispatch('MODIFY',jobData)
        if (!this.mActive) {
        modifyJob(jobData).then(()=> {
          this.$swal({
          text: '직업정보 수정을 완료했습니다.',
          icon: 'success',
          timer: 1300,
          showConfirmButton: false,
          })
        })
        }
    },
  }
}
</script>

<style>

</style>