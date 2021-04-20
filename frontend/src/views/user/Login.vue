<template>
  <div>
    <LoginForm v-on:showModalForm="showModalForm"></LoginForm>

    <Modal v-if="showModal" @click="$emit('closeModal')" >
      <h3 slot="header">
        <div class="findIdPw__title">아이디 및 비밀번호 찾기</div>
        <span id="closeModalBtn" @click="showModal = false">
          <i
            class="fa fa-times"
            aria-hidden="true"
            
          ></i>
        </span>
        <hr />
        <div class="findIdPw__contents">
          <div>
            <router-link :to="{ name: 'FindId' }"
              ><span class="findIdPw__content">아이디 찾기</span></router-link
            >
          </div>
          <div>
            <router-link :to="{ name: 'FindPw' }"
            
              ><span class="findIdPw__content">비밀번호 찾기</span></router-link
            >
          </div>
        </div>
      </h3>
      <p slot="body">
        <router-view @changePw="changePw"></router-view>
      </p>
    </Modal>
  </div>
</template>

<script>
import LoginForm from '@/components/user/LoginForm';
import Modal from '@/components/common/Modal';

export default {
  data() {
    return {
      showModal: false,
      isSignin: false,
    };
  },
  components: {
    LoginForm,
    Modal,
  },
  methods: {
    changePw(){
      this.closeModal()
    },
    closeModal() {
      this.showModal = false;
    },
    showModalForm() {
      this.showModal = true;
    },
  },
};
</script>

<style scoped>

img {
  margin-left: 10px;
  margin-top: 10px;
  height: 75px;
  margin-bottom: 10px;
}
</style>
