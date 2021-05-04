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
    </div>
    <hr>
    <!-- 3. 그룹 리스트 -->
    <div class="group-list" v-if="groupList.length != 0">
      <div class="row">
        <div class="group-list-card" v-for="(group,index) in groupList" :key="index">
          <div class="card" @click="goDetail(group)">
            <div class="card-body" style="padding-top:60px;padding-bottom:60px;text-align:center">
              <h3 class="mt-1 mb-3">{{group.group.name}}</h3>
              <span class="text-danger">{{group.studentCnt}}명</span><br>
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
        const answers = JSON.stringify(result.value)
        const groupInfo = {
          name:result.value[0],
          monetaryUnitName:result.value[1],
          code:null,
          id:null,
          taxRate:0,
          inflationRate: 0,
          teacherId:this.userInfo.id,
        }
        this.$swal({
            title: '그룹 생성 전, 정보를 확인해주세요!',
            html: `
              Your answers:
              <pre><code>${answers}</code></pre>
            `,
            confirmButtonText: 'Lovely!'
          }).then(()=>{
            saveGroup(groupInfo)
            this.$emit('createGroup')
          })
        }
      })
    }
  },
}
</script>

<style>

</style>