<template>
  <div class="law-type">
    <!-- <ul class="law-type-title">
      <li v-for="(article, index) in lawData" :key="index">
        <a href="#article{{article.article}}">{{article.article}} 조</a>
      </li>
    </ul> -->
    <div class="law-content">
      <div v-for="article in lawData" :key="article.id">
        <!-- 수정 폼을 여기에 만들어야 하나...? -->
        <p class="law-item-title">{{article.lawType}}법 제 {{article.article}}조 {{article.paragraph}}항</p>
        <p class="law-item-content">{{article.content}}</p>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
    }
  },
  props: ['lawType','lawData'],
  created() {
    console.log(this.lawType,this.lawData)
  },
  methods: {
    
  },
}
</script>

<style>

</style>