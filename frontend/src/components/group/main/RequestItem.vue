<template>
  <div class="card flex-fill">
    <div class="card-header">
      <h5 class="card-title mb-0">{{ RequestType }} Requests</h5>
    </div>
    <table class="table table-hover my-0">
      <thead>
        <tr>
          <th>내용</th>
          <!-- <th class="d-none d-xl-table-cell">Date</th> -->
          <!-- <th>Date</th> -->
          <th>요청인</th>
          <th>상세</th>
          <th>수락/거절</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(request,index) in requestList"
        :key="index">
          <template v-if='RequestName === "이력서"'>
            <td>{{request.jobName}}</td>
            <!-- <td><span class="badge bg-success">날짜</span></td> -->
            <td>{{request.studentNickname}}</td>
          </template>
          <template v-else-if='RequestName === "직업추가"'>
            <td>{{request.name}}</td>
            <!-- <td><span class="badge bg-success">날짜</span></td> -->
            <td>{{request.studentNickname}}</td>
          </template>
          <template v-else-if='RequestName === "경위서"'>
            <td>{{request.title}}</td>
            <!-- <td><span class="badge bg-success">날짜</span></td> -->
            <td>{{request.student_nickname}}</td>
          </template>
          <template v-else-if='RequestName === "법률추가"'>
            <td>{{request.title}}</td>
            <!-- <td><span class="badge bg-success">날짜</span></td> -->
            <td>{{request.student_nickname}}</td>
          </template>
          <template v-else-if='RequestName === "그룹원추가"'>
            <td>그룹원추가</td>
            <!-- <td><span class="badge bg-success">날짜</span></td> -->
            <td>{{request.nickname}}</td>
          </template>
          <template v-else-if='RequestName === "상품추가"'>
            <td>{{request.name}}</td>
            <!-- <td><span class="badge bg-success">날짜</span></td> -->
            <td>{{request.studentNickname}}</td>
          </template>
          <td><button class="btn btn-main"  @click="goDetail(request)">자세히</button></td>
          <td>
            <button class="btn btn-main" @click="acceptRequest(request.id)">O</button>
            <button class="btn btn-danger" @click="rejectRequest(request.id)">X</button></td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import { acceptGroupMember, fetchGroupRequest, rejectGroupMember } from '@/api/student';
import { mapState } from 'vuex';
import { acceptLawRequest, fetchLawRequest, fetchLawRequestDetail } from '@/api/law';
import { acceptStoreRequest, fetchStoreRequestDetail, fetchStoreRequestList } from '@/api/store';
import { acceptJobRequest, acceptJobResumeRequest, fetchJobRequestList, fetchJobResumeList } from '@/api/job';
import { acceptCase, fetchCaseList } from '@/api/case';

export default {
  props:{
    RequestType : {
      type:String
    }
  },
  data() {
    return {
      RequestName: "",
      requestList: []
    }
  },
  created() {
    this.fetchRequest();
  },
  computed: {
    ...mapState({
      groupInfo : state => state.group.groupInfo
    })
  },
  methods : {
    async fetchRequest() {
      this.RequestName = this.RequestType;
      if (this.RequestName === "그룹원추가") {
        const res = await fetchGroupRequest(this.groupInfo.id)
        this.requestList = res.data
      } else if (this.RequestName === "이력서") {
        const res = await fetchJobResumeList(this.groupInfo.id)
        this.requestList = res.data
      } else if (this.RequestName === "경위서") {
        const res = await fetchCaseList(this.groupInfo.id)
        this.requestList = res.data
      } else if (this.RequestName === "직업추가") {
        const res = await fetchJobRequestList(this.groupInfo.id)
        this.requestList = res.data
      } else if (this.RequestName === "법률추가") {
        const res = await fetchLawRequest(this.groupInfo.id)
        this.requestList = res.data
      } else if (this.RequestName === "상품추가") {
        const res = await fetchStoreRequestList(this.groupInfo.id)
        this.requestList = res.data
      } 
    },
    goDetail(request) {
      if (this.RequestName === "그룹원추가") {
        console.log(request,'-->',this.RequestName,'요청내용없니')
        this.$swal({
          title: '그룹원 추가 요청',
          text: `${request.nickname}님의 요청입니다.`
        })
      } else if (this.RequestName === "이력서") {
        console.log(request,'-->',this.RequestName,'요청내용없니')

        this.$swal({
          title: `${request.studentNickname}님의 "${request.jobName}" 이력서`,
          text: `${request.title}`
        })
       
      } else if (this.RequestName === "경위서") {
        console.log(request,'-->',this.RequestName,'요청내용없니')

        this.$swal({
          title: `경위서`,
          // text: `${request.content}`,
          html: 
            '<div id="swal2-content" class="swal2-html-container" style="display: block;">'
            +'<p class="swal2-input">'+'제출인 : '+`${request.policeNickname}`+'</p>' +
            '<p class="swal2-input">'+'벌금내는 학생 : '+`${request.studentNickname}`+'</p>' +
            '<p class="swal2-input">'+'벌금 : '+`${request.fine}`+'</p>' + 
            '<p class="swal2-input">'+'내용 : '+`${request.content}`+'</p>'
            +'</div>',

        })
      } else if (this.RequestName === "직업추가") {
        console.log(request,'-->',this.RequestName,'요청내용없니')

        this.$swal({
          title: `${request.studentNickname}님의 ${request.name} 직업추가 요청`,
          text: `${request.content}`
        })
      } else if (this.RequestName === "법률추가") {
        console.log(request,'-->',this.RequestName,'요청내용없니')

        fetchLawRequestDetail(request.id).then((res) => {
          let vote = 0
          let voteResult = ''
          for (let i = 0; i < res.voteFindResponseDto.length; i++) {
            const element = res.voteFindResponseDto[i];
            if (element.itemNum > vote) {
              vote = element.itemNum
              voteResult = element.content
            }
            
          }
          this.$swal({
          title: `${res.student_nickname}님의 법률추가 요청`,
          text: `'${res.title}' 법률이 필요합니다. 추가해주세요. `,
          html:'<div id="swal2-content" class="swal2-html-container" style="display: block;">'
            +'<span class="swal2-input">'+'제출인 : '+`${res.student_nickname}`+'</span>' +
            '<span class="swal2-input">'+'추가 법률 내용 : '+`${res.content}`+'</span>' +
            '<span class="swal2-input">'+'투표결과 : '+`${res.voteFindResponseDto[0].resultCnt} 중 ${voteResult}가 ${vote}표를 얻었습니다.}`+'</span>'  
            +'</div>',
        })
        })
        
      } else if (this.RequestName === "상품추가") {
        console.log(request,'-->',this.RequestName,'요청내용없니')

        fetchStoreRequestDetail(request.id).then((res) => {
          this.$swal({
            title: `${res.studentNickname}님의 상품추가 요청`,
            text: `'${res.name}' 상품이 '${res.content}' 이유로 필요합니다. 추가해주세요. `
          })
        })
        
      } 
    },
    rejectRequest(id) {
      const requestData = {
          id:id,
          accept:"reject"
        }
      if (this.RequestName === "그룹원추가") {
        rejectGroupMember(id)
      } else if (this.RequestName === "이력서") {
        console.log(this.RequestName,'거절api')
        acceptJobResumeRequest(requestData)
      } else if (this.RequestName === "경위서") {
        acceptCase(requestData)
        console.log(this.RequestName,'거절api')
       
      } else if (this.RequestName === "직업추가") {
        console.log(this.RequestName,'거절api')
        acceptJobRequest(requestData)
        
      } else if (this.RequestName === "법률추가") {
        console.log(this.RequestName,'거절api')
        acceptLawRequest(requestData)
       
      } else if (this.RequestName === "상품추가") {
        console.log(this.RequestName,'거절api')
        acceptStoreRequest(requestData)
       
      }
    },
    acceptRequest(id) {
      const requestData = {
          id:id,
          accept:"accept"
        }
      if (this.RequestName === "그룹원추가") {
        acceptGroupMember(id)
      } else if (this.RequestName === "이력서") {
        console.log(this.RequestName,'수락api')
        acceptJobResumeRequest(requestData)

      } else if (this.RequestName === "경위서") {
        console.log(this.RequestName,'수락api')
        acceptCase(requestData)
       
      } else if (this.RequestName === "직업추가") {
        console.log(this.RequestName,'수락api')
        acceptJobRequest(requestData)
        
      } else if (this.RequestName === "법률추가") {
        acceptLawRequest(requestData)
        console.log(this.RequestName,'수락api')
       
      } else if (this.RequestName === "상품추가") {
        console.log(this.RequestName,'수락api')
        acceptStoreRequest(requestData)
       
      }
    }
  }
}
</script>

<style>

</style>