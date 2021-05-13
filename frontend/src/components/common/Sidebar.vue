<template>
  <nav id="sidebar" class="sidebar">
		<div class="sidebar-content js-simplebar">
			<router-link class="sidebar-brand" :to="{name:'GroupList'}">
				<span class="align-middle">MONEY JAM</span>
			</router-link>

			<ul class="sidebar-nav">
				<!-- <li class="sidebar-header">
					GroupName
				</li> -->

				<li class="sidebar-item" id="MainPage"
				:class="[toActive === 'MainPage'? 'active' : '']">
					<router-link 
					class="sidebar-link" :to="{name:'MainPage'}"
					>
						<i class="fas fa-home align-middle"></i><span class="align-middle">메인</span>
					</router-link>
				</li>
				
				<li class="sidebar-item" id="JobPage"
				:class="[toActive === 'JobPage'? 'active' : '']"
				>
					<router-link class="sidebar-link" :to="{name:'JobPage'}" >
						<i class="fas fa-tags align-middle"></i>
						<span class="align-middle" >직업</span>
					</router-link>
				</li>

				<li class="sidebar-item" id="LawPage"
				:class="[toActive === 'LawPage'? 'active' : '']"
				>
					<router-link class="sidebar-link" :to="{name:'LawPage'}" >
						<i class="fas fa-balance-scale align-middle"></i><span class="align-middle" >국회</span>
					</router-link>
				</li>

				<li class="sidebar-item" id="TaxPage"
				:class="[toActive === 'TaxPage'? 'active' : '']"
				>
					<router-link class="sidebar-link" :to="{name:'TaxPage'}" >
						<i class="fas fa-university  align-middle"></i> <span class="align-middle" >국세청</span>
					</router-link>
				</li>

				<li class="sidebar-item" id="BankPage"> 
					<span data-bs-toggle="collapse" class="sidebar-link collapsed">
						<i class="fas fa-won-sign align-middle"></i> <span class="align-middle">은행</span>
					</span>
					<ul id="bank" class="sidebar-dropdown list-unstyled" data-bs-parent="#sidebar">
						<!-- collapse -->
						<li class="sidebar-item" 
						id="BankAccountPage"
				:class="[toActive === 'BankAccountPage'? 'active' : '']"><router-link :to="{name:'BankAccountPage'}" class="sidebar-link">
							그룹원 계좌조회 </router-link></li>
						<li class="sidebar-item" 
						id="BankFinancialPage"
					:class="[toActive === 'BankFinancialPage'? 'active' : '']"><router-link class="sidebar-link" :to="{name:'BankFinancialPage'}" >예금 상품관리</router-link></li>	
						</ul>
				</li>

				<li class="sidebar-item" id="StockPage" :class="[toActive === 'StockPage'? 'active' : '']">
					<router-link class="sidebar-link" :to="{name:'StockPage'}" >
						<i class="fas fa-chart-line align-middle"></i><span class="align-middle" >증권</span>
					</router-link>
				</li>

				<li class="sidebar-item" id="CreditStatisticPage"> 
					<span data-bs-toggle="collapse" class="sidebar-link collapsed">
						<i class="fas fa-chart-bar align-middle"></i></i><span class="align-middle">신용</span>
					</span>
					<ul id="CreditPage" class="sidebar-dropdown list-unstyled" data-bs-parent="#sidebar">
						<!-- collapse -->
						<li class="sidebar-item"
						id="StoreListPage"
						:class="[toActive === 'CreditPage'? 'active' : '']"><router-link :to="{name:'CreditPage'}" class="sidebar-link">
							신용등급 조회 </router-link></li>
						<li class="sidebar-item" 
						id="StatisticPage"
						:class="[toActive === 'StatisticPage'? 'active' : '']"><router-link class="sidebar-link" :to="{name:'StatisticPage'}" >제출물 관리</router-link></li>	
						</ul>
				</li>

				<li class="sidebar-item" id="StorePage"> 
					<span data-bs-toggle="collapse" class="sidebar-link collapsed">
						<i class="fas fa-building align-middle"></i><span class="align-middle">상점</span>
					</span>
					<ul id="store" class="sidebar-dropdown list-unstyled" data-bs-parent="#sidebar">
						<!-- collapse -->
						<li class="sidebar-item"
						id="StoreListPage"
						:class="[toActive === 'StoreListPage'? 'active' : '']"><router-link :to="{name:'StoreListPage'}" class="sidebar-link">
							상품리스트 조회 </router-link></li>
						<li class="sidebar-item" 
						id="StoreHistoryPage"
						:class="[toActive === 'StoreHistoryPage'? 'active' : '']"><router-link class="sidebar-link" :to="{name:'StoreHistoryPage'}" >상품 거래내역 관리</router-link></li>	
						</ul>
				</li>

				<li class="sidebar-item" id="GroupSettingPage"> 
					<span data-bs-toggle="collapse" class="sidebar-link collapsed">
						<i class="fas fa-cogs align-middle"></i><span class="align-middle">그룹관리</span>
					</span>
					<ul class="sidebar-dropdown list-unstyled" data-bs-parent="#sidebar">
						<!-- collapse -->
						<li class="sidebar-item"
						id="GroupInfo"
						:class="[toActive === 'GroupInfo'? 'active' : '']"><router-link :to="{name:'GroupInfo'}" class="sidebar-link">
							그룹 정보 </router-link></li>
						<li class="sidebar-item" 
						id="GroupMemberDetail"
						:class="[toActive === 'GroupMemberDetail'? 'active' : '']"><router-link class="sidebar-link" :to="{name:'GroupMemberDetail'}" >그룹원 관리</router-link></li>	
						</ul>
				</li>

			</ul>

			<div class="sidebar-cta">
				<div class="sidebar-cta-content">
					<strong class="d-inline-block mb-2">로그아웃</strong>
					<div class="mb-3 text-sm">
						로그아웃하시겠습니까?
					</div>
					<div class="d-grid" @click="logout">
						<a target="_blank" class="btn btn-main">Logout</a>
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
	data() {
		return {
			fromActive:"",
			toActive:""
		}
	},
	watch: {
		istoggleNav: function() {
			this.toggleSidebar()
		},
		$route(to,from) {
			this.fromActive = from.name;
			this.toActive = to.name;
		}
	},
	methods : {
		toggleSidebar() {
			const e = document.getElementsByClassName("sidebar")[0];
			e.classList.toggle("collapsed");
		},
    logout() {
        this.$swal({
        icon:'question',
        text: '로그아웃 하시겠습니까?',
        showCancelButton: true,
        customClass: {
          container: 'swal2-container'
        },
        confirmButtonText: '로그아웃',
        confirmButtonColor: "#fc3c44",
      }).then((result) => {
        if(result.value) {
          this.$swal({
            text: '로그아웃했습니다.',
            icon: 'success',
            timer: 1300,
            customClass: {
          container: 'swal2-container'
        },
            showConfirmButton: false,
          }).then(()=>{
            this.$store.commit('user/logout')
            // window.location.href = '/'

          })
        } 
      })
     
    },
	},
}
</script>

<style>

</style>