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
          <th class="d-none d-md-table-cell">Assignee</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(request,index) in requestList"
        :key="index" @click="goDetail()">
          <td>요청내용 title</td>
          <!-- <td class="d-none d-xl-table-cell">날짜</td> -->
          <td><span class="badge bg-success">날짜</span></td>
          <td class="d-none d-md-table-cell">누가요청했는지</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import { fetchGroupRequest } from '@/api/student';
import { mapState } from 'vuex';
import { fetchLawRequest } from '@/api/law';
import { fetchStoreRequestList } from '@/api/store';
import { fetchJobRequestList, fetchJobResumeList } from '@/api/job';

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
        // const res = fetchGroupRequest(this.groupInfo.id)
        // this.requestList = res.data
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
    }
  }
}
</script>

<style>

</style>