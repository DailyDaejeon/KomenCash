<template>
    <!-- <b-col class="col-setting col-center"> -->
      <div class="phoneNum-form  mb-3">
        <form @submit.prevent="sendCertificationNumber">
          <label class="form-label">휴대폰 번호  </label><input type="text" @focus="isAuthen" class="form-control form-control-lg" v-model="userPhoneNum" placeholder="휴대폰 번호를 입력하세요(-제외한 숫자만 입력)"/>
          <button class="btn btn-normal btn-authentic" :disabled="!putPhoneNum">
            휴대폰 인증</button>
          <br />
        </form>
        <div class="authentic-form" :style="{ display: authenDisplay }">
          {{ resTimeData }} <input type="text" class="form-control form-control-lg authentic" v-model="authenNum" @keydown.enter="checkCertification"/>
          <button class="btn btn-normal btn-authentic" @click="checkCertification" :disabled="!putAuthenNum">인증하기</button>
          <p class="authentic-text" :style="{display:resetBtnDisplay}">인증 문자가 도착하지 않았다면? <b class="" @click="smsReset">다시보내기</b></p>
        </div>
      </div>
    <!-- </b-col> -->
</template>

<script>
import { validatePhoneNum } from '@/utils/validations';
import { phoneAuth } from '@/api/user';
import store from '@/stores/index';
import { mapState } from 'vuex';

export default {
    name: 'PhoneCertification',
    data() {
        return {
            userId:'',
            userPhoneNum:'',
            phonebtn:false,
            timeCount: 300,
            resTimeData : '',
            polling: null,
            authenDisplay: 'none',
            idDisplay: 'none',
            resetBtnDisplay: 'none',
            authenNum: '0000',
            confirmNum: '0000',
        };
    },
    props: ['getIdChk', 'getUserId'],
    computed: {
    ...mapState({
      userInfo: state => state.user.userInfo
    }),
    putAuthenBtn() {
      return validatePhoneNum(this.userPhoneNum);
    },
    putPhoneNum() {
      return this.putAuthenBtn;
    },
    putAuthenNum() {
      if (this.authenNum.length >= 4) {
        return true;
      }
      return false;
    },
  },
  methods: {
    isAuthen(){
      if(this.getIdChk == "") {
        this.$emit('idChkFocus');
        if(!this.getIdChk && !this.getUserId){
          this.$swal({
            customClass: {
              container: 'swal2-container'
            },
            text: "아이디 체크를 먼저 진행해주세요",
            icon: 'info',
            timer: 1300,
            showConfirmButton: false,
          }).then(()=>{
            this.$emit('idChkFocus');
          })
        }
      }
    },
    sendCertificationNumber() {
      console.log(this.userPhoneNum)
      const response = phoneAuth({
        phone_number : this.userPhoneNum
        })
      console.log('휴대폰인증',response)
      // this.userId = response.data.u_email;
      // this.confirmNum = response.data.auth_number;
      this.$swal({
        customClass: {
          container: 'swal2-container'
        },
        text: '인증 번호를 발송했습니다.',
        icon: 'info',
        timer: 1300,
        showConfirmButton: false,
      })
      this.start();
      this.authenDisplay = 'block';
    },
    checkCertification() {
      if (this.confirmNum === this.authenNum) {
        this.$swal({
          customClass: {
          container: 'swal2-container'
        },
        text: '인증에 성공했습니다.',
        icon: 'success',
        timer: 1300,
        showConfirmButton: false,
      }).then(()=>{
        this.timeStop();
        this.resetBtnDisplay = 'none';
      })
        store.commit('setEmail', this.userId);
        store.commit('setPhonenum',this.userPhoneNum);
        this.$emit('checkCertification')
      
      } else {
        this.$swal({
          customClass: {
          container: 'swal2-container'
        },
        text: '인증 실패했습니다. 다시 시도해주세요.',
        icon: 'error',
        timer: 1300,
        showConfirmButton: false,
      })
      }
    },
    start(){
      this.resTimeData = this.prettyTime();
      this.polling = setInterval(()=>{
        this.timeCount--;
        this.resTimeData = this.prettyTime();
        if(this.timeCount == 0) this.timeStop();
        if(this.timeCount == 270) {
          this.resetBtnDisplay = 'block'
        }
      }, 1000);
    },
    // 시간 형식으로 변환 리턴
    prettyTime() { 
      let time = this.timeCount / 60;
      let minutes = parseInt(time);
      let secondes = Math.round((time - minutes) * 60);
      return this.pad(minutes, 2) + ":" + this.pad(secondes, 2);
    },
    // 2자리수로 만들어줌 09,08... 
    pad(n, width) {
      n = n + '';
      return n.length >= width ? n : new Array(width - n.length + 1).join('0') + n;
    },
    timeStop() {
      clearInterval(this.polling);
      this.polling = null;
    },
    // 재발행 
    smsReset() {
      clearInterval(this.polling);
      this.timeCount = 300;
      this.start();
    },
  },
  beforeDestroy() { 
    clearInterval(this.polling) 
  }
};
</script>

<style lang="scss" scoped>

</style>