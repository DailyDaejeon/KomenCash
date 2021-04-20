<template>
     <Modal v-if="showModal" @close="showModal = false">
      <h3 slot="header">
        <router-link :to="{name:'FindId'}">아이디 찾기</router-link> |
        <router-link :to="{name:'FindPw'}">비밀번호 찾기</router-link> |
        <i class="closeModalBtn fa fa-times"
          aria-hidden="true"
          @click="showModal = false">
        </i>
      </h3>
      <p slot="body"><router-view></router-view></p>
    </Modal>
</template>

<script>
import Modal from '@/components/common/Modal'

export default {
  data() {
    return {
      newTodoItem:'',
      showModal:false
    }
  },
  methods: {
    addTodo() {
      if (this.newTodoItem!=='') {
        const item = this.newTodoItem.trim()
        this.$store.commit('addOneItem',item)
        this.clearInput()
      } else {
        this.showModal = !this.showModal
      }
    },
    clearInput() {
      this.newTodoItem = ''
    }
  },
  components: {
    Modal
  }
}
</script>

<style scoped>
input:focus {
  outline:none;
}
.inputBox {
  background: white;
  height: 50px;
  line-height: 50px;
  border-radius: 5px;
}
.inputBox input {
  border-style: none;
  font-size: 0.9rem;
}
.addContainer {
  float: right;
  background: linear-gradient(to right, #6478FB, #8763FB);
  display: block;
  width: 3rem;
  border-radius: 0 5px 5px 0;
}
.addBtn {
  color: white;
  vertical-align: middle;
}
.closeModalBtn {
  color: #42b983;
}

</style>