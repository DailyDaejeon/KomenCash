<template>
  <div>
    <ListItem listType="statistic" :propsData="statisticList"/>
  </div>
</template>

<script>
import ListItem from '@/components/group/ListItem.vue';
import { mapState } from 'vuex';
import { fetchStatisticList } from '@/api/statistic';

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
    fetchStatistic() {
      const res = fetchStatisticList(this.groupInfo.id)
      this.statisticList = res.data
    }
  }
};
</script>

<style lang="scss" scoped>

</style>