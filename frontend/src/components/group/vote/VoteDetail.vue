<template>
  <main class="content">
    <div class="container-fluid p-0">
      <div class="row mb-2 mb-xl-3">
        <div class="col-auto d-sm-block w-100">
          <h3 class="d-inline-block">[{{voteInfo.title}}] 투표 결과</h3>
          <p class="d-inline-block float-right">투표 개최자 : {{voteInfo.studentNickname}}</p>
        </div>
      </div>
      <div class="row">
        <div class="vote-result">
          <div class="card-header border" v-for="(item,index) in voteInfo.voteItemResultResponses" :key="item.id">
            <p>{{item.content}}</p>
            <k-progress 
              status="success" 
              type="line"
              :percent="Number(getPercent(index))"
            ></k-progress>
          </div>
        </div>
      </div>
      <!-- <div class="row">
        <div class="w-100">
          <table>
            <tr>
              <th>No.</th>
              <th>항목</th>
              <th>득표 수</th>
            </tr>
            <tr v-for="voteItem in voteInfo.voteItemResultResponses" :key="voteItem.id">
              <td>{{voteItem.itemNum}}</td>
              <td>{{voteItem.content}}</td>
              <td>{{voteItem.resultCnt}}</td>
            </tr>
          </table>
        </div>
      </div> -->
    </div>
  </main>
</template>

<script>
export default {
  data() {
    return {
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
      }
    }
  },
  props: ['id'],
  created() {
    this.fetchVoteInfo();
  },
  methods: {
    fetchVoteInfo() {
      window.alert("voteInfo 불러오는 api 여기에 만들어조");
    },
    getPercent(idx){

      let totalNum = 0;
      for (let index = 0; index < this.voteInfo.voteItemResultResponses.length; index++) {
        totalNum += this.voteInfo.voteItemResultResponses[index].resultCnt;
      }
      console.log("받은 표 : ",this.voteInfo.voteItemResultResponses[idx])
      const pers = (this.voteInfo.voteItemResultResponses[idx].resultCnt / totalNum) * 100;
      console.log(typeof(pers)," ",pers);
      return pers;
    }
  },
}
</script>

<style>

</style>