<template>
  <div class="row w-100">
    <div class="card vl-width">
      <div class="card-header">
        <div v-if="voteList.length != 0">
          <table class="text-center table table-hover my-0">
            <thead>
              <tr>
                <th>No.</th>
                <th>투표명</th>
                <th>작성자</th>
                <th>상세보기</th>
                <th>삭제</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(vote,index) in paginatedData" :key="vote.id">
                <td>{{index+1+10*(pageNum)}}</td>
                <td>{{vote.title}}</td>
                <td>{{vote.studentNickname}}</td>
                <td>
                  <button 
                  class="btn btn-main"
                  @click="goVoteDetail(vote.id)">자세히</button>
                </td>
                <td @click="removeVote(index,vote)">
                  <i class="text-danger fas fa-trash-alt"></i>
                </td>
              </tr>
            </tbody>
          </table>
          <div  v-if="paginatedData.length" class="btn-cover text-center">
            <button :disabled="pageNum === 0" @click="prevPage" class="page-btn mr-3">이전</button>
            <span class="page-count mr-3">{{pageNum+1}}/{{pageCount}} 페이지 </span>
            <button :disabled="pageNum >= pageCount-1"  @click="nextPage" class="page-btn">다음</button>
        </div>
        </div>
        <div class="ta-center" v-else>
          <span>생성된 투표가 없습니다. 투표를 생성해보세요.</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex';
import { deleteVote, fetchVoteList } from '@/api/vote';
export default {
  data() {
    return {
      pageSize:10,
      pageNum:0,
      voteList:[],
      voteInfo: {}
    }
  },
  created() {
    this.fetchVote();
  },
  computed: {
    ...mapState({
      groupInfo: state=>state.group.groupInfo
    }),
    pageCount() {
      let listLeng = this.voteList.length,
          listSize = this.pageSize,
          page = Math.floor(listLeng / listSize);

      if(listLeng % listSize > 0) page += 1;

      return page;
    },
    paginatedData() {
      const start = this.pageNum * this.pageSize,
            end = start + this.pageSize;

      return this.voteList.slice(start, end);
    }
  },
  methods: {
    nextPage() {
      this.pageNum += 1;
    },
    prevPage() {
      this.pageNum -= 1;
    },
    async fetchVote() {
      const res = await fetchVoteList(this.groupInfo.id)
      console.log(res)
      this.voteList = res.data
    },
    goVoteDetail(vid){ //vid
      this.$router.push({name: 'VoteDetail', params: {id:vid}});
    },
    removeVote(index,voteData){
      this.$swal({
        title: '삭제하시겠습니까?',
        text:'해당 투표를 삭제하면 복구를 할 수 없습니다.',
        icon:"warning",
        confirmButtonText: '삭제',
        showCancelButton:true,
        cancelButtonText:'취소',
      }).then((result)=>{
        if(result.isConfirmed) {
        const vote = this.voteList[index];
        this.voteList.slice(index, 1);
        console.log(vote);
        deleteVote(voteData.id).then(() => {
          this.$swal({
            title:'성공적으로 삭제됐습니다.',
            icon:'success',
            timer:1500
          })
        })
        }
      })
    }
  },
}
</script>

<style>
</style>