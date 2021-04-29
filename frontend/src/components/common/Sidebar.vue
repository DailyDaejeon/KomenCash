<template>
  <nav id="sidebar" class="sidebar">
		<div class="sidebar-content js-simplebar">
			<router-link class="sidebar-brand" :to="{name:'GroupList'}">
				<span class="align-middle">모범택시</span>
			</router-link>

			<ul class="sidebar-nav">
				<li class="sidebar-header">
					GroupName
				</li>

				<li class="sidebar-item active" id="MainPage"  @click="toggleMenu">
					<router-link class="sidebar-link" :to="{name:'MainPage'}"
					v-slot="{ href, navigate, isActive}" custom
					>
						<i class="align-middle" data-feather="main"></i> <span class="align-middle" :active="isActive" :href="href" @click="navigate"
          :class="[isActive && 'active']">메인</span>
					</router-link>
				</li>

				<li class="sidebar-item" id="JobPage"
				@click="toggleMenu"
				>
					<router-link class="sidebar-link" :to="{name:'JobPage'}" v-slot="{ href, navigate, isActive}" custom>
						<i class="align-middle" data-feather="job"></i> <span class="align-middle" :active="isActive" :href="href" @click="navigate"
          :class="[isActive && 'active']">직업</span>
					</router-link>
				</li>

				<li class="sidebar-item" id="LawPage"
				@click="toggleMenu"
				>
					<router-link class="sidebar-link" :to="{name:'LawPage'}" v-slot="{ href, navigate, isActive}" custom>
						<i class="align-middle" data-feather="law"></i> <span class="align-middle" :active="isActive" :href="href" @click="navigate"
          :class="[isActive && 'active']">국회</span>
					</router-link>
				</li>

				<li class="sidebar-item" id="TaxPage"
				@click="toggleMenu"
				>
					<router-link class="sidebar-link" :to="{name:'TaxPage'}" v-slot="{ href, navigate, isActive}" custom>
						<i class="align-middle" data-feather="tax"></i> <span class="align-middle" :active="isActive" :href="href" @click="navigate"
          :class="[isActive && 'active']">국세청</span>
					</router-link>
				</li>

				<li class="sidebar-item" id="BankPage"> 
					<span data-bs-toggle="collapse" class="sidebar-link collapsed">
						<i class="align-middle" data-feather="bank"></i> <span class="align-middle">은행</span>
					</span>
					<ul id="bank" class="sidebar-dropdown list-unstyled" data-bs-parent="#sidebar">
						<!-- collapse -->
						<li class="sidebar-item" @click="toggleMenu"><router-link :to="{name:'BankAccountPage'}" class="sidebar-link" v-slot="{ href, navigate, isActive}" custom>
							<span :active="isActive" :href="href" @click="navigate"
          :class="[isActive && 'active']">그룹원 계좌조회</span> </router-link></li>
						<li class="sidebar-item" @click="toggleMenu"><router-link class="sidebar-link" :to="{name:'BankFinancialPage'}" v-slot="{ href, navigate, isActive}" custom><span :active="isActive" :href="href" @click="navigate"
          :class="[isActive && 'active']">예적금 상품관리</span></router-link></li>	
						</ul>
				</li>

				<li class="sidebar-item" id="StockPage" @click="toggleMenu">
					<router-link class="sidebar-link" :to="{name:'StockPage'}" v-slot="{ href, navigate, isActive}">
						<!-- <i class="align-middle" data-feather="stock"></i> -->
						<span class="align-middle" :active="isActive" :href="href" @click="navigate"
          :class="[isActive && 'active']">증권</span>
					</router-link>
				</li>

				<li class="sidebar-item" id="CreditPage" @click="toggleMenu">
					<router-link class="sidebar-link" :to="{name:'CreditPage'}" v-slot="{ href, navigate, isActive}" custom>
						<!-- <i class="align-middle" data-feather="credit"></i> -->
						<span class="align-middle" :active="isActive" :href="href" @click="navigate"
          :class="[isActive && 'active']">신용</span>
					</router-link>
				</li>

				<li class="sidebar-item" id="StorePage"> 
					<span data-bs-toggle="collapse" class="sidebar-link collapsed">
						<i class="align-middle" data-feather="store"></i> <span class="align-middle">상점</span>
					</span>
					<ul id="store" class="sidebar-dropdown list-unstyled" data-bs-parent="#sidebar">
						<!-- collapse -->
						<li class="sidebar-item" @click="toggleMenu"><router-link :to="{name:'StoreListPage'}" class="sidebar-link" v-slot="{ href, navigate, isActive}" custom>
							<span :active="isActive" :href="href" @click="navigate"
          :class="[isActive && 'active']">상품리스트</span> </router-link></li>
						<li class="sidebar-item" @click="toggleMenu"><router-link class="sidebar-link" :to="{name:'StoreHistoryPage'}" v-slot="{ href, navigate, isActive}" custom><span :active="isActive" :href="href" @click="navigate"
          :class="[isActive && 'active']">상품 거래내역</span></router-link></li>	
						</ul>
				</li>

				<li class="sidebar-item" id="GroupSettingPage" @click="toggleMenu">
					<router-link class="sidebar-link" :to="{name:'GroupSettingPage'}"  v-slot="{ href, navigate, isActive}" custom>
						<!-- <i class="align-middle" data-feather="setting"></i> -->
						<span class="align-middle" :active="isActive" :href="href" @click="navigate"
          :class="[isActive && 'active']">그룹관리</span>
					</router-link>
				</li>

			</ul>

			<div class="sidebar-cta">
				<div class="sidebar-cta-content">
					<strong class="d-inline-block mb-2">로그아웃</strong>
					<div class="mb-3 text-sm">
						로그아웃하시겠습니까?
					</div>
					<div class="d-grid">
						<a target="_blank" class="btn btn-primary">Logout</a>
					</div>
				</div>
			</div>
		</div>
	</nav>
</template>

<script>
export default {
	props: {
		istoggleNav: {
			type:Boolean
		}
	},
	watch: {
		istoggleNav: function() {
			this.toggleSidebar()
		}
	},
	methods : {
		toggleSidebar() {
			const e = document.getElementsByClassName("sidebar")[0];
			e.classList.toggle("collapsed");
		},
		toggleMenu() {
			const remove = document.querySelector(".active");
			remove.classList.remove('active');
			const add = document.getElementById(this.$route.name);
			add.classList.add('active');
		},
	},
}
</script>

<style>

</style>