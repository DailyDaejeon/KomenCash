<template>
  <nav class="navbar navbar-expand navbar-light navbar-bg">
		<a class="sidebar-toggle d-flex" @click="toggleSidebar">
			<i class="hamburger align-self-center"></i>
		</a>
		<div class="navbar-collapse collapse">
			<ul class="navbar-nav navbar-align">
				<li class="nav-item dropdown">
					<span class="nav-icon dropdown-toggle d-inline-block d-sm-none"  data-bs-toggle="dropdown">
						<i class="align-middle" data-feather="settings"></i>
					</span>
					<!-- dropdown-toggle -->
					<span class="nav-link d-inline-block"  data-bs-toggle="dropdown">
						<span class="text-dark">{{userInfo.nickname}} 선생님</span>
					</span>
				</li>
			</ul>
		</div>
	</nav>
</template>

<script>
import { mapState } from 'vuex';
export default {
	data() {
		return {
			dropDown:true
		}
	},
	computed: {
		...mapState({
			groupInfo : state => state.group.groupInfo,
			userInfo:state=>state.user.userInfo
		})
	},
	methods : {
		toggleSidebar() {
			this.$emit("toggleNav");	
		},
	}
}
</script>

<style>

</style>