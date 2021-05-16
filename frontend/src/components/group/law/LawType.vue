<template>
  <div class="law-type">
    <div class="law-content">
      <div v-for="(law,index) in paginatedData" :key="index">
        <p class="h5 fw-bold law-item-title">제 {{law.article}}조 {{law.paragraph}}항
          <button @click="alertModi(law)" class="text-main"><i class="fas fa-edit"></i></button>
        </p>
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
import { modifyLawItem } from '@/api/law';
import { mapState } from 'vuex';

export default {
  data() {
    return {
      pageSize:10,
      pageNum:0,
      mActive:false
    }
  },
  props: ['lawType','lawData'],
  created() {
    this.fetchData()
  },
  computed : {
    ...mapState({
      groupInfo:state => state.group.groupInfo
    }),
    pageCount() {
      if (this.lawData.length) {
      let listLeng = this.lawData.length,
          listSize = this.pageSize,
          page = Math.floor(listLeng / listSize);

      if(listLeng % listSize > 0) page += 1;

      return page;
      }
      return 0
    },
    paginatedData() {
      if (this.lawData.length) {
      const start = this.pageNum * this.pageSize,
            end = start + this.pageSize;

      return this.lawData.slice(start, end);
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
    alertModi(law) {
      this.$swal({
        title: `${this.lawType} 법률 수정`,
        html:
        '<div id="swal2-content" class="swal2-html-container" style="display: block;">수정할 내용만 입력 해주세요.</div>'+'<input id="swal-input-lawtype" class="swal2-input" type="text" placeholder="'+law.lawType+'">'
        +'<input id="swal-input-content" class="swal2-input" type="text" placeholder="'+law.content+'">'+
        '<input id="swal-input-article" class="swal2-input-custom" type="number" min=0 placeholder="'+law.article+'조">'+'<input id="swal-input-paragraph" class="swal2-input-custom" type="number" min=0 placeholder="'+law.paragraph+'항">',
        focusConfirm: false,
        preConfirm: () => {
          return [
            document.getElementById('swal-input-lawtype').value,
            document.getElementById('swal-input-content').value,
            document.getElementById('swal-input-article').value,
            document.getElementById('swal-input-paragraph').value,
          ]
        },
        confirmButtonText: '수정',
        showCancelButton: true,
      }).then((res)=>{
        if (res.isConfirmed) {
          const modiData = {
            article: res.value[2] === "" ? law.article:res.value[2],
            content: res.value[1] === "" ? law.content:res.value[1],
            groupId: this.groupInfo.id,
            id: law.id,
            lawType: res.value[0] === "" ? law.lawType:res.value[0],
            paragraph: res.value[3] === "" ? law.paragraph:res.value[3]
          }
          console.log(modiData,'수정할 법률')
          modifyLawItem(modiData).then(() => {
            this.$swal({
              title:'법률 수정이 완료되었습니다.',
              icon:'success',
              timer:1500
            })
          })
        } 
      })
    }
  },
}
</script>

<style>

</style>