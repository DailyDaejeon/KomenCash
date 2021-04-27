<template>
  <div class="law-type">
    <ul class="law-type-title">
      <li v-for="(article, index) in articles" :key="index">
        <a href="#article{{article.article}}">{{article.article}} 조</a>
      </li>
    </ul>
    <div class="law-content">
      <div v-for="article in articles" :key="article.id">
        <!-- 수정 폼을 여기에 만들어야 하나...? -->
        <p class="law-item-title">{{article.law_type}}법 제 {{article.article}}조 {{article.paragraph}}항</p>
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
  props: {
    articles : {
      type:Array
    }
  },
  methods: {
    
  },
}
</script>

<style>

</style>