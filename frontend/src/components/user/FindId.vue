<template>
  <b-container class="container-setting">
    <template v-if="showCertiForm">
      <phone-certification @checkCertification="checkCertification"></phone-certification>
    </template>
    <b-col class="showID" :style="{ display: idDisplay }">
      고객님의 아이디는
      <p class="idView" :style="{ display: 'inline-block' }">{{ userId }}</p>
      입니다.
       <div class="sign-setting" >
          <button class="btn btn-normal btn-small" @click="closeModal">로그인</button>
        </div>
    </b-col>
  </b-container>
</template>

<script>
import '@/css/index.css'
import PhoneCertification from './PhoneCertification.vue';
import store from '@/stores/modules/user';

export default {
  components: { PhoneCertification },
  data() {
    return {
      userId: '',
      idDisplay: 'none',
      showCertiForm: true,
    };
  },
  methods: {
    checkCertification() {
      this.idDisplay = 'block';
      this.showCertiForm = false;
      if (store.state.userInfo.u_email) {
        this.userId = store.state.userInfo.u_email;
      } else {
        this.$swal({
          customClass: {
          container: 'swal2-container'
        },
        text: '회원가입이 필요합니다.',
        icon: 'info',
        timer: 1300,
        showConfirmButton: false,
      })
        this.$router.push('/member/join')
      }
    },
    closeModal(e) {
      console.log(e)
      this.$emit('changePw')
      
    },
  },
};
</script>
