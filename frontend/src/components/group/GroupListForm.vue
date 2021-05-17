<template>
  <div class="container-fluid">
    <!-- 1. OOO선생님의 그룹 리스트 입니다 -->
    <div style="margin-top:13px;">
      <span><h2 style="display:inline-block">{{userInfo.nickname}}</h2> 선생님의 그룹 리스트 입니다.</span>
      <p>
        관리를 원하는 그룹을 선택해서 해당 그룹을 관리하세요.
      </p>
    </div>

    <!-- 2. 튜토리얼 -->
    <div>
      <button @click="alertTuto" class="btn btn-main">튜토리얼</button>
    </div>
    <hr>
    <!-- 3. 그룹 리스트 -->
    <div class="group-list" v-if="groupList.length != 0">
      <div class="row">
        <div class="group-list-card" v-for="(group,index) in groupList" :key="index">
          <div class="card" @click="goDetail(group)">
            <div class="card-body" style="padding-top:60px;padding-bottom:60px;text-align:center">
              <h3 class="mt-1 mb-3">{{group.group.name}}</h3>
              <span class="text-danger">{{priceToString(group.studentCnt)}}명</span><br>
            </div>
          </div>
        </div>
        <div class="add-group-card" @click="addGroup">
          <div class="card">
            <div class="card-body" style="padding-top:73px;padding-bottom:73px;text-align:center">
              <h2 class="mt-1 mb-3" style="display:inline-block;margin-right:10px">그룹 추가하기</h2>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="group-list" v-else>
      <div class="row not-group">
        <h2>아직 생성한 그룹이 없습니다.</h2>
        <button @click="addGroup">그룹 생성하기</button>
      </div>
    </div>
  </div>
</template>

<script>
import { saveGroup } from '@/api/group'
export default {
  props:['userInfo', 'groupList'],
  methods: {
    alertTuto() {
      this.$swal.queue([
      {
        title: '튜토리얼 1단계',
        html: 
            '<div id="swal2-content" class="swal2-html-container" style="display: block;"><h3 class="fw-bold swal2-title">[MONEY JAM]</h3><p>MONEY JAM은 경제 교육 플랫폼입니다. 초등학교 학생들을 대상으로 학급에서 경제활동을 간접적으로 체험하며 경제 개념을 익힐 수 있습니다.</p></div>',
        confirmButtonText: 'Next &rarr;',
        showCancelButton: true,
        progressSteps: ['1', '2'],
      },
      {
        title: '튜토리얼 2단계',
        html: 
            '<div id="swal2-content" class="swal2-html-container" style="display: block;"><h3 class="fw-bold swal2-title">[그룹 생성 단계]</h3><p>1. 먼저 학생들과 함께 할 그룹을 생성해주세요.</p><p>2. 그룹 이름과 화폐단위를 설정해 그룹을 생성한 뒤 그룹을 클릭해주세요.</p><p>3. 그룹 설정에 들어가셔서 "그룹코드"를 복사한 뒤, 학생들이 "그룹코드"로 회원가입을 진행할 수 있습니다.</p><p>4. 그룹 가입을 요청한 학생들을 "그룹원 가입 요청"에서 "수락(O)"을 눌러주세요.</p></div>',
        confirmButtonText: 'Next &rarr;',
        showCancelButton: true,
        progressSteps: ['1', '2'],
      },
      {
        title: '튜토리얼 3단계',
        html: 
            '<div id="swal2-content" class="swal2-html-container" style="display: block;"><h3 class="fw-bold swal2-title">[법률 페이지]</h3><p>1. 국회 메뉴의 "법률 및 투표"를 클릭해주세요.</p><p>2. 해당 페이지에서는 그룹의 헌법 및 투표를 확인할 수 있습니다.</p><p>3. "법률 제안 요청 관리"에서는 학생들이 제출한 법률 제안서를 확인할 수 있습니다.<p>4. "자세히" 버튼을 클릭하면 투표 결과와 함께 내용을 확인할 수 있습니다.</p><p>5. 법률을 추가하고 싶다면, "헌법추가"버튼을 통해 추가할 수 있습니다.</p><p>6. "수락(O) 또는 거절(X)"을 클릭하여 요청을 없애주세요.</p><p>7. 법률을 수정하고 싶다면 옆의 "노란 연필"버튼을 클릭 후 수정하고 싶은 부분만 작성한 뒤 "수정"버튼을 눌러주세요.</p><p>8. "투표 관리"에서는 학생들이 제시한 투표 목록과 결과를 볼 수 있습니다.</p><p>9. "자세히" 버튼을 클릭하면, 투표 결과를 차트로 확인할 수 있습니다.</p></div>',
        confirmButtonText: 'Next &rarr;',
        showCancelButton: true,
        progressSteps: ['1', '2'],
      },
      {
        title: '튜토리얼 4단계',
        html: 
            '<div id="swal2-content" class="swal2-html-container" style="display: block;"><h3 class="fw-bold swal2-title">[경위서]</h3><p>1. 국회 메뉴의 "경위서"를 클릭해주세요.</p><p>2. 해당 페이지에서는 그룹의 헌법을 어겼을 시에 접수된 "경찰" 학생이 보낸 경위서를 볼 수 있습니다.</p><p>3. 어떤 잘못을 했고, 벌금이 얼마인지 확인 할 수 있습니다. 요청을 확인한 뒤 "수락"을 누르면 세금으로 돈이 들어갑니다.</p><</div>',
        confirmButtonText: 'Next &rarr;',
        showCancelButton: true,
        progressSteps: ['1', '2'],
      },
      ])
    },
    priceToString(price) {
      return price.toLocaleString('ko-KR')
    },
    goDetail(group) {
      this.$store.commit('setGroupInfo',group.group)
      this.$store.commit('setGroupMemberCnt',group)
      this.$router.push({name:'MainPage',params:{id:group.group.id,groupData:group.group}})
    },
    addGroup(){
      this.$swal.queue([
      {
        title: '그룹 생성 1단계',
        input: 'text',
        text: '그룹명을 정해주세요.',
        inputPlaceholder: '햇반, OO초등학교 O학년 O반',
        confirmButtonText: 'Next &rarr;',
        showCancelButton: true,
        progressSteps: ['1', '2'],
        inputValidator: (result) => {
          return !result && '그룹명을 작성해주세요!'
        }
      },
      {
        title: '그룹 생성 2단계',
        input: 'text',
        text: '화폐 단위를 정해주세요.',
        inputPlaceholder: '원, 꿈, 미소',
        confirmButtonText: 'Next &rarr;',
        showCancelButton: true,
        progressSteps: ['1', '2'],
        inputValidator: (result) => {
          return !result && '화폐 단위를 작성해주세요!'
        }
      }
      ]).then((result) => {
      if (result.value) {
        const groupInfo = {
          name:result.value[0],
          monetaryUnitName:result.value[1],
          code:null,
          id:null,
          taxRate:0,
          inflationRate: 0,
          teacherId:this.userInfo.id,
        }
        saveGroup(groupInfo).then(()=>{
          this.$emit('createGroup')
          this.$swal({
            title: '그룹 생성이 완료됐습니다.',
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