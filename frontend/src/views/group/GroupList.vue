<template>
  <div>
    <GroupListForm :userInfo="userName" :groupList="groupList" />
  </div>
</template>

<script>
import GroupListForm from "@/components/group/GroupListForm"
export default {
  data() {
    return {
      userName:"박싸피",
      groupList:[],
    }
  },
  components: { GroupListForm },
  created() {
    this.featchGroupInfo();
  },
  methods: {
    featchGroupInfo(){
      this.groupList = [
        {groupName:"그룹1", totalCnt:25},
        {groupName:"그룹2", totalCnt:36},
        {groupName:"그룹3", totalCnt:17}
      ];
    },
  },
}
</script>

<style>

</style>