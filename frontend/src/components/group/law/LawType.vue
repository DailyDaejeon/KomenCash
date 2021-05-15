<template>
  <div class="law-type">
    <!-- {{lawData}} -->
    <div class="law-content">
      <div v-for="(law,index) in paginatedData" :key="index">
        <!-- 수정 폼을 여기에 만들어야 하나...? -->
        <p class="law-item-title">{{lawType}} 제 {{law.article}}조 {{law.paragraph}}항</p>
        <p class="law-item-content">{{law.content}}</p>
      </div>
      <div  v-if="paginatedData.length" class="btn-cover text-center">
        <button :disabled="pageNum === 0" @click="prevPage" class="page-btn mr-3">이전</button>
        <span class="page-count mr-3">{{pageNum+1}}/{{pageCount}} 페이지 </span>
        <button :disabled="pageNum >= pageCount-1"  @click="nextPage" class="page-btn">다음</button>
    </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      lawList: [],
      pageSize:10,
      pageNum:0,
    }
  },
  props: ['lawType','lawData'],
  created() {
    this.fetchData()
  },
  computed : {
    pageCount() {
      if (this.lawList.length) {
      let listLeng = this.lawList.length,
          listSize = this.pageSize,
          page = Math.floor(listLeng / listSize);

      if(listLeng % listSize > 0) page += 1;

      return page;
      }
      return 0
    },
    paginatedData() {
      if (this.lawList.length) {
      const start = this.pageNum * this.pageSize,
            end = start + this.pageSize;

      return this.lawList.slice(start, end);
      }
      return []
    }
  },
  methods: {
    nextPage() {
      this.pageNum += 1;
    },
    prevPage() {
      this.pageNum -= 1;
    },
    fetchData() {
      this.lawList = this.$route.params.lawData
      console.log('이건들어왔니?',this.lawList)
    }
  },
}
</script>

<style>

</style>