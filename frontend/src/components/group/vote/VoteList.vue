<template>
  <div class="row w-100">
    <div class="card vl-width">
      <div class="card-header">
        <div v-if="voteList.length != 0">
          <table class="table table-hover my-0">
            <thead>
              <tr>
                <th>투표 명</th>
                <th class="d-none d-table-cell">작성자</th>
                <th class="d-none d-table-cell">참여율</th>
                <th class="d-none d-table-cell ta-center">삭제</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="vote in voteList" :key="vote.id">
                <td @click="goVoteDetail(vote.id)">{{vote.title}}</td> <!-- @click="goVoteDetail(vote.id)" -->
                <td class="d-none d-table-cell">{{vote.studentNickname}}</td>
                <td class="d-none d-table-cell">{{voteAttandRate}}%</td>
                <td class="d-none d-table-cell ta-center" @click="removeVote(index,vote)"> <!-- remove(index) -->
                  <i class="fas fa-trash-alt"></i>
                </td>
              </tr>
            </tbody>
          </table>
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
      voteList:[
        {
          id:1,
          title:"반장선거",
          content:"2021년 햇반의 반장선거를 실시합니다. 지지하는 입후보자에게 투표해주세요.",
          studentId:1,
          studentNickname:"박싸피",
          voteItemResultResponses: [
            {
              id: 1,
              content: "고재석",
              itemNum: 1,
              resultCnt: 6
            },
            {
              id:2,
              content: "박수아",
              itemNum: 2,
              resultCnt: 5
            },
            {
              id:3,
              content: "배상웅",
              itemNum: 3,
              resultCnt: 8
            },
            {
              id:4,
              content: "정혜림",
              itemNum: 4,
              resultCnt: 3
            }
          ]
        },
      ],
      voteInfo: {
        id: 1,
        title: "반장선거",
        content:"2021년 햇반의 반장선거를 실시합니다. 지지하는 입후보자에게 투표해주세요.",
        studentId:1,
        studentNickname:"박싸피",
        voteAttendResponses: [
          {
            choiceItemNum: 1,
            studentId: 1,
            studentNickname: "고재석",
          },
          {
            choiceItemNum: 2,
            studentId: 2,
            studentNickname: "박수아",
          },
          {
            choiceItemNum: 3,
            studentId: 3,
            studentNickname: "배상웅",
          },
          {
            choiceItemNum: 4,
            studentId: 4,
            studentNickname: "정혜림",
          },
        ],
        voteItemResultResponses: [
          {
            id: 1,
            content: "고재석",
            itemNum: 1,
            resultCnt: 1
          },
          {
            id:2,
            content: "박수아",
            itemNum: 2,
            resultCnt: 1
          },
          {
            id:3,
            content: "배상웅",
            itemNum: 3,
            resultCnt: 1
          },
          {
            id:4,
            content: "정혜림",
            itemNum: 4,
            resultCnt: 1
          }
        ]
      },
      voteAttandRate: 0,
    }
  },
  created() {
    this.fetchVote();
  },
  computed: {
    ...mapState({
      groupInfo: state=>state.group.groupInfo
    })
  },
  methods: {
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
        if(result.value) {
        const vote = this.voteList[index];
        this.voteList.slice(index, 1);
        console.log(vote);
        deleteVote(voteData.id)
        }
      })
    }
  },
}
</script>

<style>
</style>