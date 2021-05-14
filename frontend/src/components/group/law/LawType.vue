<template>
  <div class="law-type">
    <!-- {{lawData}} -->
    <div class="law-content">
      <div v-for="(law,index) in lawData" :key="index">
        <!-- 수정 폼을 여기에 만들어야 하나...? -->
        <p class="law-item-title">{{lawType}} 제 {{law.article}}조 {{law.paragraph}}항</p>
        <p class="law-item-content">{{law.content}}</p>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      lawList: []
    }
  },
  props: ['lawType','lawData','id'],
  created() {
    this.fetchData()
  },
  methods: {
    fetchData() {
      this.lawList = this.lawData
      console.log('이건들어왔니?',this.lawList)
    }
  },
}
</script>

<style>

</style>