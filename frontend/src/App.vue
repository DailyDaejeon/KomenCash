<template>
  <div id="app">
    <div class="wrapper">
      <Sidebar 
      v-show="groupInfo && isLogin"
      :istoggleNav="istoggleNav"/>
      <div class="main">
        <Navbar 
      v-show="isLogin"
        @toggleNav="toggleNav"/>
        <div class="main">
          <router-view></router-view>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import Navbar from './components/common/Navbar.vue'
import Sidebar from './components/common/Sidebar.vue'
export default {
  components: { Sidebar, Navbar },
  name: 'App',
  data() {
    return {
      istoggleNav:false,
    }
  }, 
  computed:{
    ...mapState({
      isLogin:state => state.user.isLogin,
      groupInfo:state => state.group.groupInfo
    })
  },
  methods: {
    toggleNav() {
      this.istoggleNav = !this.istoggleNav;
    },
  }
}
</script>

<style>
</style>
