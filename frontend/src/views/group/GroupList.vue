<template>
  <div>
    <GroupListForm :userInfo="userInfo" :groupList="groupList" @createGroup="fetchGroupInfo"/>
  </div>
</template>

<script>
import GroupListForm from "@/components/group/GroupListForm"
import { fetchGroupList } from '@/api/group';
import { mapState } from 'vuex';

export default {
  data() {
    return {
      groupList:[],
    }
  },
  components: { GroupListForm },
  created() {
    this.fetchGroupInfo();
  },
  computed: {
    ...mapState({
      userInfo: state => state.user.userInfo,
    }),
  },
  methods: {
    async fetchGroupInfo(){
      const res = await fetchGroupList();
      this.groupList = res.data.result
    },
  },
}
</script>

<style>

</style>