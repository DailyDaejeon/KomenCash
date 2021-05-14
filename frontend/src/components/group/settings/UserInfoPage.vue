<template>
  <div>
    <div class="card">
      <div class="card-body">
        <div class="flex-fill">
          <h5 class="card-title mb-0">내 정보</h5>
          <template v-if="isPw">
          <div class="card-body">
            <div class="row">
              <div class="col">
                <h3 class="mb-3">이름
                <span class="m-3" v-if="!mActive">{{userName}}</span>
                <span class="m-3" v-else><input type="text" class="form-control d-inline-block ml-2 w-50" id="inputUsername" v-model="userName"></span>
                </h3>
                <h3 class="mb-3">아이디
                <span class="m-3">{{userEmail}}</span>
                </h3>
                <h3>비밀번호
                <span class="m-3" v-if="!mActive">{{userPw}}</span>
                <span class="p-3" v-else><input type="text" class="form-control d-inline-block ml-2 w-50" v-model="userPw"></span>
                </h3>
                <h3>
                  <span v-if="!mActive">
                  휴대폰번호
                  </span>
                <span class="m-3" v-if="!mActive">{{userPhoneNum}}</span>
                <span class="m-3 w-80" v-else>
                  <PhoneCertification/>
                </span>
                </h3>
              </div>
            </div>
            <button class="btn btn-primary" @click="submitModiInfo" v-if="mActive"> 변경내역저장</button>
            <button class="btn btn-main" @click="modiUserInfo" v-if="!mActive">수정</button>
          </div>
          </template>
          <template v-else>
            <h3>내 정보를 조회하시려면 비밀번호를 인증해주세요!</h3>
            <button class="btn btn-main" @click="checkPw">
              내정보 보기</button>
          </template>
        </div>
      </div>
    </div>
    <div class="card">
      <div class="card-body">
        <div class="flex-fill">
          <h5 class="card-title mb-0">계정 삭제</h5>
          <div class="card-body">
            <div class="row">
              <div class="col">
                <h4 class="text-danger font-weight-bold">[Warning!]</h4>
                <p>계정을 삭제하면 <strong>그룹 내 학생들의 계정을 포함</strong>하여 그룹과 관련된 모든 정보(기본 정보, 활동 데이터 등)는 복구할 수 없습니다. 정말로 삭제하시겠습니까?</p>
                <p>삭제되는 데이터:</p>
                <ul>
                  <li class="delete-item">그룹에 가입한 학생들의 계정</li>
                  <li class="delete-item">그룹 기본 정보</li>
                  <li class="delete-item">그룹 내 활동 데이터(은행, 국회, 상점 등의 내역 및 모든 활동 기록)</li>
                </ul>
                <div>
                  <input type="checkbox" id="acceptDelete" v-model="acceptChk">
                  <label for="acceptDelete">위 주의사항을 모두 확인하였으며, 이에 동의합니다.</label>
                </div>
                <button class="btn btn-secondary float-right" @click="deleteUserInfo">계정 삭제</button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex';
import { deleteMyInfo, fetchMyInfo, updateMyInfo } from '@/api/user';
import PhoneCertification from '@/components/user/PhoneCertification.vue';

export default {
  components: { PhoneCertification },
  data() {
    return {
      isPw:false,
      mActive: false,
      acceptChk: false,
      userName:'',
      userEmail:'',
      userPw:'',
      userPhoneNum:'',
    }
  },
  created() {
    this.fetchInfo();
  },
  computed: {
    ...mapState({
      userInfo: state=> state.user.userInfo
    })
  },
  methods: {
    checkPw() {
      this.$swal({
        title:'비밀번호를 인증해주세요',
        input: 'password',
        inputLabel: 'Password',
        inputPlaceholder: '비밀번호를 입력해주세요.',
      }).then((res) => {
        if (res.value === this.userPw) {
          this.isPw = true
        }
      })
    },
    async fetchInfo(){
      const res = await fetchMyInfo(this.userInfo.id)
      console.log(this.userInfo)
      this.userName = res.data.nickname
      this.userEmail= res.data.email 
      this.userPw= res.data.password
      this.userPhoneNum=res.data.phoneNumber
    },
    modiUserInfo() {
      this.mActive = !this.mActive;
    },
    submitModiInfo() {
      //modify api
      const userInfo = {
        email: this.userEmail,
        id: this.userInfo.id,
        nickname: this.userName,
        password: this.userPw,
        phoneNumber: this.userPhoneNum
      }
      updateMyInfo(userInfo)
      this.$store.commit('fetchInfo',userInfo)
      this.fetchInfo()
      this.mActive = !this.mActive;
    },
    deleteUserInfo(){
      if(!this.acceptChk) {
        this.$swal({
          icon: 'warning',
          text: '주의사항을 읽고, 동의여부에 체크해주세요!',
        })
        return;
      }else {
        this.$swal({
          text: '삭제한 데이터는 복구가 불가능합니다. 계정 삭제를 계속 진행하시겠습니까?',
          showCancelButton: true,
          onfirmButtonText: `예, 계정을 삭제합니다`,
        }).then((result) => {
          if (result.isConfirmed) {
            //그룹 삭제 api
            deleteMyInfo(this.userInfo.id)
            this.$swal('계정이 삭제되었습니다.', '', 'success');
            this.$router.push({name:'Login'});
          }
        })
      }
    }
  },
}
</script>

<style>
ul {
  margin-top: 0;
  margin-bottom: 1rem;
  padding-left: 1.5rem;
}

.delete-item {
  margin-top:0.3rem;
}
</style>