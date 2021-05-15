<template> 
    <b-container class="container-setting find-idpw block">
        <template v-if="showCertiForm">
          <div class="form-sort id-chk">
              <h4 class="mr-3 fw-bold">아이디 
                <button class="btn btn-main btn-authentic" @click="checkId" :disabled="userId && !isUserIdValid">아이디 확인</button>
                </h4>  <input type="text" v-model="userId" ref="idChk" class="form-control form-control-lg find" placeholder="example@example.com" @keydown.enter="checkId"/>
              
          </div>
          <div class="mt-3"> 
            <PhoneCertification
            @idChkFocus="idChkFocus" @checkCertification="checkCertification" :getIdChk="authenId" :getUserId="userId"/>
          </div>
        </template>

          <b-col :style="{display:pwDisplay}">
          <div class="newPW-form">
            <form @submit.prevent="submitChangePw">
              <div class="newPW-form-move">
                <div class="right-sort form-sort mb-3">
                  <h4  class="mr-3 fw-bold">새 비밀번호</h4>
                  
                  <input v-model="newPw" class="form-control form-control-lg find" type="password"/>
                </div>
                <div class="mb-3 form-sort">
                  <h4  class="mr-3 fw-bold">비밀번호 확인</h4>
                  <input v-model="newPwConfirm" class="form-control form-control-lg find" type="password"/>
                </div>
              </div>
              <button class="btn btn-main btn-middle newPWBtn-move" :disabled="newPw && newPwConfirm && isPasswordChangeValid">비밀번호 변경</button>
            </form>
          </div>
        </b-col>
    </b-container>
</template>

<script>
import PhoneCertification from './PhoneCertification.vue';
import { validateEmail, validatePassword } from '@/utils/validations';
import { changePw } from '@/api/user';
import { userIdChk } from '@/api/user'

export default {
    components: { PhoneCertification },
    data() {
        return {
            userId: "",
            authenId:false,
            newPw: "",
            newPwConfirm: "",
            pwDisplay:'none',
            showCertiForm: true,
        }
    },
    computed : {
      isUserIdValid() {
        if (!this.userId) {
          return true;
        }
        return validateEmail(this.userId);
      },
      isPasswordValid() {
        if (!this.newPw) {
          return true;
        }
        return validatePassword(this.newPw);
      },
      isPasswordConfirmValid() {
        if (!this.newPwConfirm) {
          return true;
        } else if (this.newPw != this.newPwConfirm) {
          return false;
        }
        return true;
      },
      isPasswordChangeValid() {
        if (
          !this.isPasswordValid ||
          !this.isPasswordConfirmValid
        ) {
          return true;
        }
        return false;
      },
    },
    methods: {
        async checkId(){
          const response = await userIdChk(this.userId)
          // id존재하면 false
          if (response.data) {
            this.$swal({
              customClass: {
                container: 'swal2-container'
              },
              text: '존재하지 않는 아이디입니다.',
              icon: 'info',
              timer: 1300,
              showConfirmButton: false,
            })
            this.userId = ""
          }else{
            this.authenId=true
            this.$swal({
              customClass: {
                container: 'swal2-container'
              },
              text: '아이디가 확인됐습니다.',
              icon: 'success',
              timer: 1300,
              showConfirmButton: false,
            })
          }
          return;
        },
        idChkConfirm(){
          if(!this.userId || !this.authenId) {
            this.$swal({
              customClass: {
                container: 'swal2-container'
              },
              text: '아이디 체크 먼저 진행해주세요.',
              icon: 'info',
              timer: 1300,
              showConfirmButton: false,
            })
          }
        },
        checkCertification() {
          this.showCertiForm =  false;
          this.pwDisplay = 'block';
          },
        submitChangePw() {
          const userData = {
            email:this.userId,
            password: this.newPw,
          };
          changePw(userData).then(()=>{
            this.$swal({
              customClass: {
                container: 'swal2-container'
              },
              text: '비밀번호 변경 완료.',
              icon: 'success',
              timer: 1300,
              showConfirmButton: false,
            }).then(()=>{
              this.$emit('changePw')
            })
            
            }).catch(()=>{
              this.$swal({
                customClass: {
                  container: 'swal2-container'
                },
                text: '비밀번호 변경 실패.',
                icon: 'error',
                timer: 1300,
                showConfirmButton: false,
              })
            })
        },
        idChkFocus(){
          this.$refs.idChk.focus();
          this.$swal({
            customClass: {
              container: 'swal2-container'
            },
            text: "아이디 체크를 먼저 진행해주세요",
            icon: 'info',
            timer: 1300,
            showConfirmButton: false,
          })
        }
    },
}
</script>

<style>

</style>

