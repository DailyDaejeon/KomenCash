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
              <h1 class="mt-1 mb-3">{{group.group.name}}</h1>
              <span class="text-success h4">{{priceToString(group.studentCnt)}}명</span><br>
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
        title: 'Money Jam이란?',
        html: 
            '<div id="swal2-content" class="swal2-html-container" style="display: block;"><p>MONEY JAM은 경제 교육 플랫폼입니다.</p><p>초등학교 학생들을 대상으로 학급에서 경제활동을 체험하며 이론이 아닌 실제적이고 지속적인 "경제교육"을 할 수 있습니다.</p><p>법을 만들어 지키고, 어기면 벌금도 내며 합리적이고 공정한 보상과 벌을 줄 수 있습니다.</p><p>자신의 적성, 직업의 월급, 갖고있는 자격증, 내 신용등급을 고려해서 직업을 선택합니다. </p><p>직업별로 정해진 일도 하고 월급을 받습니다. </p><p>세금도 내야 합니다. 은행에 예금도 합니다.</p><p> 중도해지의 아픔을 맛보기도 합니다.</p><p> 돈을 더 벌기 위해 주식도 할 수 있습니다.</p><p> 열심히 모은 돈으로 자리를 살 수도 있습니다.</p><p>이제 그룹을 생성해 경제교육을 진행해 보세요!</p></div>',
        confirmButtonText: 'Next &rarr;',
        showCancelButton: true,
        progressSteps: ['1', '2'],
      },
      {
        title: '그룹 생성 단계 튜토리얼',
        html: 
            '<div id="swal2-content" class="swal2-html-container" style="display: block;"><p>1. 먼저 학생들과 함께 할 그룹을 생성해주세요.</p><p>2. 그룹 이름과 화폐단위를 설정해 그룹을 생성한 뒤 그룹을 클릭해주세요.</p><p>3. 그룹 설정에 들어가셔서 "그룹코드"를 복사한 뒤, 학생들이 "그룹코드"로 회원가입을 진행할 수 있습니다.</p><p>4. 그룹 가입을 요청한 학생들을 "그룹원 가입 요청"에서 "수락(O)"을 눌러주세요.</p></div>',
        confirmButtonText: '확인',
        // showCancelButton: true,
        progressSteps: ['1', '2'],
      }
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