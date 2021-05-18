<template>
  <div>
    <div class='d-flex justify-content-end m-3'>
      <button @click="alertTuto" class="btn btn-main">튜토리얼</button>
    </div>
    <div class="d-flex flex-row justify-content-end m-3">
      <button @click="addStatis" class="btn btn-main">제출물생성</button>
    </div>
    <ListItem listType="statistic" :propsData="statisticList"/>
  </div>
</template>

<script>
import ListItem from '@/components/group/ListItem.vue';
import { mapState } from 'vuex';
import { addStatisticItem, fetchStatisticList } from '@/api/statistic';

export default {
  components: { ListItem },
  data() {
    return {
      statisticList:[]
    }
  },
  created() {
    this.fetchStatistic()
  },
  computed: {
    ...mapState({
      groupInfo : state=> state.group.groupInfo
    })
  },
  methods: {
    alertTuto() {
      this.$swal.queue([
        {
        title: '제출물관리 튜토리얼',
        html: 
            '<div id="swal2-content" class="swal2-html-container" style="display: block;"><p>1. 해당 페이지에서는 그룹의 제출물을 관리하는 페이지입니다.</p><p>2. "제출물 생성"버튼은 그룹원이 내야 될 제출물을 등록할 수 있습니다.</p><p>3. 해당 제출물 목록을 클릭하면, 상세 내용과 그룹원 목록과 함께 제출여부를 확인할 수 있습니다.</p><p>4. 그룹원이 제출물을 제출했다면 "O"버튼을 눌려 제출완료를 확인해주세요. 신용등급 점수에 영향을 줍니다.</p></div>',
        confirmButtonText: '확인',
      },
      ])
    },
    async fetchStatistic() {
      const res = await fetchStatisticList(this.groupInfo.id)
      this.statisticList = res.data
      console.log(this.statisticList)
    },
    addStatis() {
      this.$swal.queue([
      {
        title: '제출물 추가 1단계',
        input: 'text',
        text: '숙제 또는 준비물을 작성해주세요.',
        inputPlaceholder: '오늘 일기 쓰기',
        confirmButtonText: 'Next &rarr;',
        showCancelButton: true,
        progressSteps: ['1', '2'],
        inputValidator: (result) => {
          return !result && '숙제 또는 준비물을 작성해주세요!'
        }
      },
      {
        title: '제출물 추가 2단계',
        input: 'text',
        text: '종료일을 작성해주세요.',
        inputPlaceholder: '2021-01-01',
        confirmButtonText: 'Next &rarr;',
      showCancelButton: true,
      progressSteps:['1', '2'],
      inputValidator: (result) => {
          return !result && '종료일을 지정해주세요!'
        }
      },
      ]).then((result) => {
      if (result.value) {
        const statis = {
          groupId: this.groupInfo.id,
          sumbmitContent: result.value[0],
          startDate: new Date(),
          endDate:result.value[1]
        }
        addStatisticItem(statis).then(()=> {
          this.fetchStatistic()
          this.$swal({
          title: '제출물이 생성됐습니다',
          icon:'success',
          timer:1500
          })
        })
      
      }
      })
    }
  }
};
</script>

<style lang="scss" scoped>

</style>