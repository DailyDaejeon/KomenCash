<template>
  <div>
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