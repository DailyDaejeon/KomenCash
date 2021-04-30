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
					>
						<i class="fas fa-home align-middle"></i><span class="align-middle">메인</span>
					</router-link>
				</li>

				<li class="sidebar-item" id="JobPage"
				@click="toggleMenu"
				>
					<router-link class="sidebar-link" :to="{name:'JobPage'}" >
						<i class="fas fa-user-tag align-middle"></i> <span class="align-middle" >직업</span>
					</router-link>
				</li>

				<li class="sidebar-item" id="LawPage"
				@click="toggleMenu"
				>
					<router-link class="sidebar-link" :to="{name:'LawPage'}" >
						<i class="fas fa-balance-scale align-middle"></i><span class="align-middle" >국회</span>
					</router-link>
				</li>

				<li class="sidebar-item" id="TaxPage"
				@click="toggleMenu"
				>
					<router-link class="sidebar-link" :to="{name:'TaxPage'}" >
						<i class="fas fa-tenge align-middle"></i> <span class="align-middle" >국세청</span>
					</router-link>
				</li>

				<li class="sidebar-item" id="BankPage"> 
					<span data-bs-toggle="collapse" class="sidebar-link collapsed">
						<i class="fas fa-piggy-bank align-middle"></i> <span class="align-middle">은행</span>
					</span>
					<ul id="bank" class="sidebar-dropdown list-unstyled" data-bs-parent="#sidebar">
						<!-- collapse -->
						<li class="sidebar-item" @click="toggleMenu"><router-link :to="{name:'BankAccountPage'}" class="sidebar-link">
							그룹원 계좌조회 </router-link></li>
						<li class="sidebar-item" @click="toggleMenu"><router-link class="sidebar-link" :to="{name:'BankFinancialPage'}" >예적금 상품관리</router-link></li>	
						</ul>
				</li>

				<li class="sidebar-item" id="StockPage" @click="toggleMenu">
					<router-link class="sidebar-link" :to="{name:'StockPage'}" >
						<i class="fas fa-chart-line align-middle"></i><span class="align-middle" >증권</span>
					</router-link>
				</li>

				<li class="sidebar-item" id="CreditPage" @click="toggleMenu">
					<router-link class="sidebar-link" :to="{name:'CreditPage'}">
						<i class="fas fa-chart-bar align-middle"></i> <span class="align-middle" >신용</span>
					</router-link>
				</li>

				<li class="sidebar-item" id="StorePage"> 
					<span data-bs-toggle="collapse" class="sidebar-link collapsed">
						<i class="fas fa-store align-middle"></i><span class="align-middle">상점</span>
					</span>
					<ul id="bank" class="sidebar-dropdown list-unstyled" data-bs-parent="#sidebar">
						<!-- collapse -->
						<li class="sidebar-item" @click="toggleMenu"><router-link :to="{name:'StoreListPage'}" class="sidebar-link">
							상품리스트 조회 </router-link></li>
						<li class="sidebar-item" @click="toggleMenu"><router-link class="sidebar-link" :to="{name:'StoreHistoryPage'}" >상품 거래내역 관리</router-link></li>	
						</ul>
				</li>

				<li class="sidebar-item" id="GroupSettingPage" @click="toggleMenu">
					<router-link class="sidebar-link" :to="{name:'GroupSettingPage'}"  >
						<i class="align-middle" data-feather="setting"></i> <span class="align-middle" >그룹관리</span>
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
			// const remove = document.querySelector(".active");
			// remove.classList.remove('active');
			// const add = document.getElementById(this.$route.name);
			// add.classList.add('active');
		},
	},
}
</script>

<style>

</style>