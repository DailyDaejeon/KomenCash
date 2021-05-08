<template>
  <div class="card flex-fill">
    <div class="card-header">
      <h5 class="card-title mb-0">{{ RequestType }} Requests</h5>
    </div>
    <table class="table table-hover my-0">
      <thead>
        <tr>
          <th>Name</th>
          <!-- <th class="d-none d-xl-table-cell">Date</th> -->
          <th>Date</th>
          <th>Assignee</th>
          <th>수락</th>
          <th>거절</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(request,index) in requestList"
        :key="index" @click="goDetail()">
          <td>요청내용 title</td>
          <!-- <td class="d-none d-xl-table-cell">날짜</td> -->
          <td><span class="badge bg-success">날짜</span></td>
          <td>누가요청했는지</td>
          <td><span @click="acceptRequest(request.id)">accept</span></td>
          <td><span @click="rejectRequest(request.id)">reject</span></td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import { acceptGroupMember, fetchGroupRequest, rejectGroupMember } from '@/api/student';
import { mapState } from 'vuex';
import { acceptLawRequest, fetchLawRequest } from '@/api/law';
import { acceptStoreRequest, fetchStoreRequestList } from '@/api/store';
import { acceptJobRequest, fetchJobRequestList, fetchJobResumeList } from '@/api/job';
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
    fetchRequest() {
      this.RequestName = this.RequestType;
      if (this.RequestName === "그룹원추가") {
        const res = fetchGroupRequest(this.groupInfo.id)
        this.requestList = res.data
      } else if (this.RequestName === "이력서") {
        const res = fetchJobResumeList(this.groupInfo.id)
        this.requestList = res.data
      } else if (this.RequestName === "경위서") {
        const res = fetchCaseList(this.groupInfo.id)
        this.requestList = res.data
      } else if (this.RequestName === "직업추가") {
        const res = fetchJobRequestList(this.groupInfo.id)
        this.requestList = res.data
      } else if (this.RequestName === "법률추가") {
        const res = fetchLawRequest(this.groupInfo.id)
        this.requestList = res.data
      } else if (this.RequestName === "상품추가") {
        const res = fetchStoreRequestList(this.groupInfo.id)
        this.requestList = res.data
      } 
    },
    goDetail() {
      // 각요청별로 detail 보내는것 다르게 표시
      this.$router.push(`/`)
    },
    rejectRequest(id) {
      const requestData = {
          caseId:id,
          accept:"reject"
        }
      if (this.RequestName === "그룹원추가") {
        rejectGroupMember(id)
      } else if (this.RequestName === "이력서") {
        console.log(this.RequestName,'거절api')
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
          caseId:id,
          accept:"accept"
        }
      if (this.RequestName === "그룹원추가") {
        acceptGroupMember(id)
      } else if (this.RequestName === "이력서") {
        console.log(this.RequestName,'수락api')

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