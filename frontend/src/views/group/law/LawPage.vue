<template>
  <main class="content">
    <div class='d-flex justify-content-end'>
      <button @click="alertTuto" class="btn btn-main">튜토리얼</button>
    </div>
    <div class="container-fluid p-0">
      <div class="row mb-2 mb-xl-3">
        <div class="col-auto d-none d-sm-block">
          <h3><strong>{{groupInfo.name}}</strong>의 헌법</h3>
        </div>
      </div>
      
      <!-- 1. 헌법 관리 -->
      <LawList/>
    </div>

    <!-- 2. 법률 제안 요청 관리 -->
    <div class="row mb-2 mb-3">
      <div class="col-auto  d-block">
        <h3><strong>법률 제안 요청</strong> 관리</h3>
      </div>
    </div>
    <RequestItem RequestType="법률제안"/>

    <!-- 3. 투표 생성 -->
    <!-- 4. 투표 관리 -->
    <div class="row mb-2 mb-xl-3">
      <div class="col-auto d-none d-sm-block">
        <h3><strong>투표</strong> 관리</h3>
      </div>
      <div class="col-auto near-title-btn">
        <button class="btn btn-main" @click="addVote">투표 생성하기</button>
      </div>
      <!-- <Modal v-if="showModal" @close="showModal = false" class="addVoteModal">
        <h3 slot="header">
          <div class="modal-head">
            투표 생성하기
            <div class="modal-cancel-btn" @click="closeModal">
              <i class="closeModalBtn fa fa-times"
                aria-hidden="true"
              ></i>
            </div>
          </div>
        </h3>
        <div slot="body">
          <div class="vote-body-item">
            <p>투표 주제</p> 
            <div>
              <input type="text" v-model="voteTitle" class="voteTitle" placeholder="ex) 2021년 1학기 국무총리 선거"/>
            </div>
          </div>
          <div class="vote-body-item">
            <p>투표 내용</p> 
            <textarea v-model="voteContent" placeholder="2021년도 1학기 국무총리를 뽑는 투표입니다. 우리 모두 투표권을 행사하여...."></textarea>
          </div>
          <div class="vote-body-item">
            <p>투표 항목 추가</p>
            <VoteItemInput @addItem="addOneItem" />
            <VoteItemList :voteList="voteItemList" @removeItem="removeOneItem" @toggleItem="toggleOneItem" />
          </div>
          <div class="vote-body-item createVote">
            <button class="btn btn-main" @click="createVote">투표 등록</button>
          </div>
        </div>
      </Modal> -->
      <VoteList />
    </div>
  </main>
</template>

<script>
import RequestItem from '@/components/group/main/RequestItem.vue'
// import Modal from '@/components/common/Modal.vue'
// import VoteItemInput from '@/components/group/vote/VoteItemInput.vue'
// import VoteItemList from '@/components/group/vote/VoteItemList.vue'
import VoteList from '@/components/group/vote/VoteList.vue'
import { mapState } from 'vuex'
import LawList from '@/components/group/law/LawList.vue'
// import VoteList from '../../../components/group/vote/VoteList.vue'

export default {
  data() {
    return {
      showModal:false,
      voteItemList:[],
      voteTitle:'',
      voteContent:'',
      activetab: 1,
      lawData:{}
    }
  },
  components: { RequestItem, VoteList, LawList }, //Modal, VoteItemInput, VoteItemList, 
 
  computed: {
    ...mapState({
      groupInfo:state => state.group.groupInfo
    })
  },
  methods: {
    alertTuto() {
      this.$swal.queue([
        {
        title: '법률 및 투표 튜토리얼',
        html: 
            '<div id="swal2-content" class="swal2-html-container" style="display: block;"><p>1. 해당 페이지에서는 그룹의 헌법 및 투표를 확인할 수 있습니다.</p><p>2. "법률 제안 요청 관리"에서는 학생들이 제출한 법률 제안서를 확인할 수 있습니다.<p>3. "자세히" 버튼을 클릭하면 투표 결과와 함께 내용을 확인할 수 있습니다.</p><p>4. 법률을 추가하고 싶다면, "헌법추가"버튼을 통해 추가할 수 있습니다.</p><p>5. "수락(O) 또는 거절(X)"을 클릭하여 요청을 없애주세요.</p><p>6. 법률을 수정하고 싶다면 옆의 "노란 연필"버튼을 클릭 후 수정하고 싶은 부분만 작성한 뒤 "수정"버튼을 눌러주세요.</p><p>7. "투표 관리"에서는 학생들이 제시한 투표 목록과 결과를 볼 수 있습니다.</p><p>8. "자세히" 버튼을 클릭하면, 투표 결과를 차트로 확인할 수 있습니다.</p></div>',
        confirmButtonText: '확인',
        // showCancelButton: true,
      },
      ])
    },
    addVote() {
      // this.showModal = true;
      this.$swal({
        text: '투표 생성은 게임에서 진행할 수 있습니다!'
      })
    },
    // addOneItem(item) {
    //   console.log("이거 실행 됨?",item)
    //   const obj = {completed: false, item: item};
    //   this.voteItemList.push(obj);
    //   console.log("this is new item : ",obj);
    // },
    // removeOneItem(item, index) {
    //   this.voteItemList.splice(index, 1);
    // },
    // toggleOneItem(item, index) {
    //   this.voteItemList[index].completed = !this.voteItemList[index].completed;
    // },
    // createVote(){
    //   const iList = [];

    //   for(let i = 1; i<=this.voteItemList.length; i++){
    //     iList.push({item_num: i, content: this.voteItemList[i-1].item});
    //   }

    //   const list = [];
    //   list.push({title: this.voteTitle, content: this.voteContent, voteItemList: iList});

    //   console.log(list)
    //   //list 서버에 넘겨주는 api 추가
    // },
    // closeModal() {
    //   this.showModal = !this.showModal;
    //   this.voteItemList = [];
    //   this.voteTitle = '';
    //   this.voteContent = '';

    // }
  },
}
</script>

<style>
.modal-head{
  width: 100%;
}

.modal-header h3{
  width: 100%;
  text-align: initial;
}

.modal-cancel-btn {
  display: inline-block;
  float: right;
  cursor: pointer;
}

.vote-body-item textarea {
  display: block;
  resize: none;
  width: 22.1rem;
  height: 5rem;
}

.vote-body-item input[type=text] {
  width: 19.1rem;
}

.voteTitle{
  width: 22.1rem !important;
}

.vote-body-item textarea,
.vote-body-item input[type=text] {
  border: 1px solid #d9d9d9;
  border-radius: .1875em;
  background: inherit;
  box-shadow: inset 0 1px 1px rgb(0 0 0 / 6%);
  color: inherit;
  font-size: 1.125em;
  padding: 2px;
  margin-bottom: 1rem;
  box-sizing: border-box;
  transition: border-color .3s,box-shadow .3s;
}

.vote-body-item p {
  color: #545454;
  font-size: 1.125em;
  font-weight: 400;
  line-height: normal;
  margin : 0;
}

.createVote {
  text-align: center;
}

.addVoteModal .modal-container{
  width: 500px;
  height: 500px;
}

</style>