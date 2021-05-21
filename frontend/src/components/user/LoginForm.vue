<template>
  <main class="d-flex w-100">
    <div class="container d-flex flex-column">
      <div class="row vh-100">
        <div class="col-sm-10 col-md-8 col-lg-6 mx-auto d-table h-100">
          <div class="d-table-cell align-middle">
            <div class="text-center mt-4">
              <h1 class="h2">Welcome back, MONEY JAM!</h1>
            </div>

            <div class="card">
              <div class="card-body">
                <div class="m-sm-4">
                  <form @submit.prevent="loginComplete">
                    <div class="mb-3">
                      <label class="form-label">Email</label>
                      <input 
                      v-model="userId"
                      autocapitalize="off"
                      class="form-control form-control-lg" type="email" name="email" placeholder="moneyjam@moneyjam.com" />
                      <p class="warning-form">
                        <span class="text-danger" v-if="!isUserIdValid && userId">
                        id를 이메일형식으로 입력하세요.
                        </span>
                      </p>
                    </div>
                    <div class="mb-3">
                      <label class="form-label">Password</label>
                      <input 
                      v-model="password"
                      :type="passwordType"
                      placeholder="●●●●●●●●(8자 이상)"
                      class="form-control form-control-lg" name="password"/>
                      <!-- <div class="input-group-text input-group-button" @click="viewPassword">
                        <font-awesome-icon :icon="['far', fwName]" :style="{ color: '#495057' }"/>
                      </div> -->
                    </div>
                    <div class="text-center mt-3">
                      <button 
                      :disabled="isLoginValid"
                      type="submit" class="btn btn-lg btn-main">로그인</button>
                    </div>
                  </form>
                  <div class="sign-setting">
                    <h6><a @click="showModalForm">아이디 또는 비밀번호</a>를 잊으셨습니까?</h6>
                    <h6>아직 계정이 없으시다면? <router-link :to="{name:'Signup'}">회원 가입</router-link></h6>
                  </div>
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
import { validateEmail } from '@/utils/validations';
import { mapState } from 'vuex';

export default {
  data() {
    return {
      userId: '',
      password: '',
      passwordType:"password",
      fwName:"eye",
      //log
      logMessage: '',
      display: 'none'
    };
  },
  computed: {
    isUserIdValid() {
      return validateEmail(this.userId);
    },

    isLoginValid() {
      if (!this.isUserIdValid || !this.password) {
        return true;
      }
      return false;
    },
    ...mapState({
      isLoginError : state => state.user.isLoginError
    })
  },
  methods: {
    showModalForm() {
      this.$emit('showModalForm')
    },
    viewPassword() {
      // tupe이 password가 tureaus text, false라면 type이 password
      this.passwordType = this.passwordType==="password" ? "text" : "password";
      if(this.passwordType === "text") {
        this.fwName = "eye-slash"
      }else {
        this.fwName = "eye"
      }
    },
    async loginComplete() {
      try {
          await this.$store.dispatch('LOGIN', {
          email:this.userId,
          password:this.password
        })
        if (this.isLoginError) {
          this.$swal({
          text: "로그인을 다시 해주세요!",
          icon: 'info',
          timer: 1300,
          showConfirmButton: false,
          })
        } else {
          this.initForm()
          this.$router.push({name:"GroupList"})
        }
        
      } catch (error) {
        if(error.status === 500) {
          this.loginError();
        }
      }
    },
    initForm() {
      this.userId = '';
      this.password = '';
    },
    loginError() {
      this.display = 'block';
    },
    closeError(){
      this.display = 'none';
    }
  }
};
</script>


<style scoped>

  .temp-bc {
    background-color: #FFFFFF;
    margin-top: 100px;
    
  }

  .temp-color {
    color:white
  }

</style>