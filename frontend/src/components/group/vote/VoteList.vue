<template>
  <div class="row w-100">
    <div class="card vl-width">
      <div class="card-header">
        <div v-if="this.voteList.length != 0">
          <table class="table table-hover my-0" v-for="vote in voteList" :key="vote.id">
            <thead>
              <tr>
                <th>투표 명</th>
                <th class="d-none d-table-cell">작성자</th>
                <th class="d-none d-table-cell">참여율</th>
                <th class="d-none d-table-cell ta-center">삭제</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td @click="goVoteDetail(vote.id)">{{vote.title}}</td>
                <td class="d-none d-table-cell">{{vote.studentNickname}}</td>
                <td class="d-none d-table-cell">{{voteAttandRate}}%</td>
                <td class="d-none d-table-cell ta-center" @click="removeVote"> <!-- remove(index) -->
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
      voteAttandRate: 15,
    }
  },
  created() {
    // fetchVoteList();
  },
  methods: {
    // fetchVoteList() {

    // },
    goVoteDetail(vid){
      this.$router.push({name: 'VoteDetail', params: {id:vid}});
    },
    removeVote(index){
      const vote = this.voteList[index];
      this.voteList.slice(index, 1);
      console.log(vote);
      //해당 투표 삭제하는 api
    }
  },
}
</script>

<style>
</style>