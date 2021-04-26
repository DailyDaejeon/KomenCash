<template>
  <div id="app">
    <div class="wrapper">
      <Sidebar :istoggleNav="istoggleNav"/>
      <div class="main">
        <Navbar @toggleNav="toggleNav"/>
        <div class="main">
          <router-view></router-view>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
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
  methods: {
    toggleNav() {
      this.istoggleNav = !this.istoggleNav;
    }
  }
}
</script>

<style>
</style>
