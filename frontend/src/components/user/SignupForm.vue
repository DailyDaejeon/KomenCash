<template>
  <main class="d-flex w-100">
		<div class="container d-flex flex-column">
			<div class="row ">
				<div class="col-sm-10 col-md-8 col-lg-6 mx-auto d-table h-100">
					<div class="d-table-cell align-middle">
						<div class="text-center mt-4">
							<h1 class="h2">WELCOME TO MONEY JAM!</h1>
						</div>

						<div class="card">
							<div class="card-body">
								<div class="m-sm-4">
									<form @submit.prevent="submitSignup">
										<div class="mb-3">
											<h4 class="form-label">Name
                        <span class="text-main icon-inline-block" v-show="!isUserNickNameEmpty && nickCheck">
                      <!-- 체크표시 아이콘 -->
                        <font-awesome-icon class="fw-icon fwCheck" :icon="['fas', 'check' ]" />
                      </span>
                      <!-- 중복 닉네임일 때 표출 -->
                      <span class="text-danger icon-inline-block" v-show="!isUserNickNameEmpty && !nickCheck">
                      <!-- 엑스표시 아이콘 -->
                        <font-awesome-icon class="fw-icon fwTimes" :icon="['fas', 'times' ]" />
                      </span>
                      </h4>
                      <!-- 중복 닉네임이 아닐 때 표출 -->
                      
											<input 
                      v-model="username"
                      class="form-control form-control-lg" type="text" name="name" placeholder="이름을 입력하세요." />
										</div>
										<div class="mb-3">
											<h4 class="form-label">Email
                        <!-- 중복 아이디가 아닐 때 표출 -->
                      <span class="text-main icon-inline-block" v-show="!isUserIdEmpty && isUserIdValid && !idCheck">
                      <!-- 체크표시 아이콘 -->
                        <font-awesome-icon class="fw-icon fwCheck" :icon="['fas', 'check' ]" />
                      </span>
                      <!-- 중복 아이디일 때 표출 -->
                      <span class="text-danger icon-inline-block" v-show="!isUserIdEmpty && isUserIdValid && idCheck">
                      <!-- 엑스표시 아이콘 -->
                        <font-awesome-icon class="fw-icon fwTimes" :icon="['fas', 'times' ]" />
                      </span>
                      </h4>
											<input 
                      v-model="userId"
                      autocapitalize="off"
                      class="form-control form-control-lg" type="email" name="email" placeholder="moneyJam@moneyJam.com" />
                      
										</div>
                    <p class="warning-form warning-signup">
                      <span class="text-danger" v-if="!isUserIdValid">
                        id를 이메일형식으로 입력하세요.
                      </span>
                    </p>
										<div class="mb-3">
											<h4 class="form-label">Password
                        <!-- 눈 모양 클릭하면 아이콘 바뀌면서 비밀번호 표출 -->
                        <span class="btn btn-main" @click="viewPassword">
                          <font-awesome-icon :icon="['far', fwName ]" :style="{color:'#495057'}" />
                        </span>
                      </h4>
											<input 
                      class="form-control form-control-lg"
                      name="password" 
                      v-model="password"
                      :type="passwordType"
                      placeholder="8자 이상 입력해주세요." />
										</div>
                    <div>
                      <input
                        v-model="passwordConfirm"
                        class="form-control form-control-lg"
                        type="password"
                        placeholder="비밀번호를 다시 한번 입력하세요."
                      />
                    </div>
                    <p class="warning-form warning-signup">
                      <span class="text-danger" v-if="!isPasswordConfirmValid">
                        password가 일치하지 않습니다.
                      </span>
                    </p>
                    <div class="mb-3">
                    <PhoneCertification class="join-authentic" 
                    :getIdChk="true" :getUserId="true"
                    @checkCertification="checkCertification"/>
                    </div>
                    <div class="fw-checkbox">
                        <input type="checkbox" id="term1" value="term1" v-model="isTerm.term1"
                        class="check-input-none"
                        />
                        <label for="term1"><font-awesome-icon class="chk-label" :icon="[isTerm.icon1, 'check-circle']" /></label>
                        <label for="term1">회원약관(필수)</label>
                        <span class="termView">약관보기</span>
                    </div>
                    <div class="fw-checkbox">
                      <input type="checkbox" id="term2" value="term2" v-model="isTerm.term2"
                      class="check-input-none"
                      />
                      <label for="term2"><font-awesome-icon class="chk-label" :icon="[isTerm.icon2, 'check-circle']" /></label>
                      <label for="term2">개인정보 수집 및 이용 동의(필수)</label>
                      <span class="termView">약관보기</span>
                    </div>
                    <div class="fw-checkbox">
                      <input type="checkbox" id="term3" value="term3" v-model="isTerm.term3"
                      class="check-input-none"/>
                      <label for="term3"><font-awesome-icon class="chk-label" :icon="[isTerm.icon3, 'check-circle']" /></label>
                      <label for="term3">마케팅/홍보의 수집 및 이용 동의(선택)</label>
                      <span class="termView">약관보기</span>
                    </div>
                    <div class="fw-checkbox">
                      <input type="checkbox" id="allTerm" value="allTerm" v-model="allTerm" @click="allTermcheck"
                      class="check-input-none"
                      />
                      <label for="allTerm"><font-awesome-icon class="chk-label" :icon="[isTerm.icon4, 'check-circle']" /></label>
                      <label for="allTerm">전체 동의</label>
                    </div>
										<div 
                    @click="signupComplete"
                    class="text-center mt-3">
											<button 
                      :disabled="isSignupDisabled"
                      type="submit" class="btn btn-lg btn-main">가입하기</button>
										</div>
									</form>
								</div>
							</div>
						</div>

					</div>
				</div>
			</div>
		</div>
	</main>
</template>

<script>
import PhoneCertification from './PhoneCertification.vue';
import { registerUser, userIdChk, dupNickNameChk } from '@/api/user'; 
import { validateEmail, validatePassword, validatePhoneNum } from '@/utils/validations';
import store from '@/stores/modules/user'

export default {
  components: { PhoneCertification },
  data() {
    return {
      userId: '',
      password: '',
      passwordConfirm: '',
      fwName:"eye",
      username: '',
      userPhoneNumber: '',
      phonebtn:false,
      clickSignupBtn:false,
      passwordType:"password",
      isTerm: {
        term1:false,
        term2:false,
        term3:false,
        icon1:'far',
        icon2:'far',
        icon3:'far',
        icon4:'far'
      },
      allTerm:false,
      termPopup: false,
      showCertiForm: true,
      isAuthentic:false,
      authenResult:'휴대폰 인증',
      idCheck:false,
      nickCheck:false,
      signUpTerm:{}
    };
  },
  computed: {
    isUserIdEmpty() {
      if(!this.userId) {
        return true;
      }
      return false;
    },
    isUserNickNameEmpty() {
      if(!this.username) {
        return true;
      }
      return false;
    },
    isUsernameValid() {
      if (!this.username) {
        return false;
      }
      return true;
    },
    isUserIdValid() {
      if (!this.userId) {
        return true;
      }
      return validateEmail(this.userId);
    },
    isPasswordValid() {
      if (!this.password) {
        return true;
      }
      return validatePassword(this.password);
    },
    isPasswordConfirmValid() {
      if (!this.passwordConfirm) {
        return true;
      } else if (this.password != this.passwordConfirm) {
        return false;
      }
      return true;
    },
    isSignupDisabled() {
      if (
        !this.isUsernameValid ||
        !this.isUserIdValid ||
        !this.isPasswordValid ||
        !this.isPasswordConfirmValid ||
        !this.isTerm
      ) {
        return true;
      }
      return false;
    },
    putAuthenBtn() {
      return validatePhoneNum(this.userPhoneNumber);
    },
    putPhoneNum() {
      if(this.putAuthenBtn && !this.isAuthentic) {
        return true;
      }
      return false;
    },
    putAuthenNum() {
      if (this.authenNum.length >= 4) {
        return true;
      }
      return false;
    },
  },
  watch: {
    'isTerm.term1': function() {
      if(this.isTerm.term1) {
        this.isTerm.icon1 = 'fas'
      }else {
        this.isTerm.icon1 = 'far'
      }
    },
    'isTerm.term2': function() {
      if(this.isTerm.term2) {
        this.isTerm.icon2 = 'fas'
      }else {
        this.isTerm.icon2 = 'far'
      }
    },
    'isTerm.term3': function() {
      if(this.isTerm.term3) {
        this.isTerm.icon3 = 'fas'
      }else {
        this.isTerm.icon3 = 'far'
      }
    },
    userId : function () {
      this.isDupIdCheck()
    },
    username : function () {
      this.isDupNickNameCheck()
    }
  },
  methods: {
    async isDupIdCheck() {
      try{
        const response = await userIdChk(this.userId);
        this.idCheck = response.data;
        if (this.idCheck === -1) {
          this.idCheck = false
        }
        // console.log('아이디체크?',response,this.idCheck)
      }catch(err) {
        console.log(err);
      }
      return false;
    },
    async isDupNickNameCheck() {
      try{
        const result = await dupNickNameChk(this.username);
        this.nickCheck = result.data;
        // console.log('닉네임체크?',result,this.nickCheck)
      }catch(err) {
        console.log(err);
      }
      return false;
      
    },
    checkCertification(e) {
      this.userPhoneNumber = store.state.userInfo.phoneNumber;
      this.userPhoneNumber = e.phoneNumber;
      this.showCertiForm = false;
    },
    clickphonebtn() {
      this.phonebtn = !this.phonebtn
    },
    viewPassword() {
      // type이 password가 true면 text, false라면 type이 password
      this.passwordType = this.passwordType==="password" ? "text" : "password";
      if(this.passwordType === "text") {
        this.fwName = "eye-slash"
      }else {
        this.fwName = "eye"
      }
    },
    allTermcheck(){
      this.allTerm=!this.allTerm
      if (this.allTerm) {
        this.isTerm.term1=true
        this.isTerm.term2=true
        this.isTerm.term3=true
        this.isTerm.icon1 = 'fas'
        this.isTerm.icon2 = 'fas'
        this.isTerm.icon3 = 'fas'
        this.isTerm.icon4 = 'fas'
      } else {
        this.isTerm.term1=false
        this.isTerm.term2=false
        this.isTerm.term3=false
        this.isTerm.icon1 = 'far'
        this.isTerm.icon2 = 'far'
        this.isTerm.icon3 = 'far'
        this.isTerm.icon4 = 'far'
      }
    },
    async submitSignup() {
      console.log(this.idCheck)
      if(this.idCheck){
        this.$swal({
        text: "이미 사용중인 아이디입니다.",
        icon: 'info',
        timer: 1300,
        showConfirmButton: false,
      })
        return;
      }
      if(!this.nickCheck) {
        this.$swal({
        text: "이미 사용중인 닉네임입니다.",
        icon: 'info',
        timer: 1300,
        showConfirmButton: false,
      })
        return;
      }
      if (!this.clickSignupBtn) {
        return;
      }
      if (!this.userPhoneNumber) {
        this.$swal({
        text: "휴대폰 번호 인증을 완료해주세요.",
        icon: 'info',
        timer: 1300,
        showConfirmButton: false,
      })
        return;
      }
      try {
        const userData = {
            email:this.userId,
            password: this.password,
            phoneNumber : this.userPhoneNumber,
            nickname : this.username,
        };
        await registerUser(userData).then(() => {
          this.$swal({
            title:'회원가입이 완료됐습니다.',
            icon:'success',
            timer:1500
          })
          this.$router.push({name:'Login'});
        });
      } catch(error) {
        console.log(error)
      }
    },
    signupComplete() {
      this.clickSignupBtn =true;
    },
    initForm() {
      this.username = '';
      this.password = '';
      this.nickname = '';
    },
 
  },
};
</script>


<style scoped>
  .title-text {
    background: linear-gradient(to right, #feac5e, #c779d0, #4bc0c8);
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
    font-size: 2.3rem;
  }
</style>
