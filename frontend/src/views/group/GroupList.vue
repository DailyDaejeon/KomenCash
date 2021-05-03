<template>
  <div>
    <GroupListForm :userInfo="userInfo" :groupList="groupList" @createGroup="createGroup"/>
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
    this.featchGroupInfo();
  },
  computed: {
    ...mapState({
      userInfo: state => state.user.userInfo,
    }),
  },
  methods: {
    async featchGroupInfo(){
      const res = await fetchGroupList();
      this.groupList = res.data.group
    },
    createGroup(groupData) {
      this.groupList.push(groupData)
    }
  },
}
</script>

<style>

</style>