<template>
  <nav id="sidebar" class="sidebar">
			<div class="sidebar-content js-simplebar">
				<router-link class="sidebar-brand" :to="{name:'GroupList'}">
          <span class="align-middle">KOMENCASH</span>
        </router-link>

				<ul class="sidebar-nav">
					<li class="sidebar-header">
						GroupName
					</li>

					<li class="sidebar-item active">
						<router-link class="sidebar-link" :to="{name:'MainPage'}">
              <i class="align-middle" data-feather="main"></i> <span class="align-middle">메인</span>
            </router-link>
					</li>

					<li class="sidebar-item">
						<router-link class="sidebar-link" :to="{name:'JobPage'}">
              <i class="align-middle" data-feather="job"></i> <span class="align-middle">직업</span>
            </router-link>
					</li>

					<li class="sidebar-item">
						<router-link class="sidebar-link" :to="{name:'LawPage'}">
              <i class="align-middle" data-feather="law"></i> <span class="align-middle">국회</span>
            </router-link>
					</li>

					<li class="sidebar-item">
						<router-link class="sidebar-link" :to="{name:'TaxPage'}">
              <i class="align-middle" data-feather="tax"></i> <span class="align-middle">국세청</span>
            </router-link>
					</li>

					<li class="sidebar-item">
						<router-link class="sidebar-link" :to="{name:'BankPage'}">
              <i class="align-middle" data-feather="bank"></i> <span class="align-middle">은행</span>
            </router-link>
					</li>

          <li class="sidebar-item">
						<router-link class="sidebar-link" :to="{name:'StockPage'}">
              <i class="align-middle" data-feather="stock"></i> <span class="align-middle">증권</span>
            </router-link>
					</li>

          <li class="sidebar-item">
						<router-link class="sidebar-link" :to="{name:'CreditPage'}">
              <i class="align-middle" data-feather="credit"></i> <span class="align-middle">신용</span>
            </router-link>
					</li>

          <li class="sidebar-item">
						<router-link class="sidebar-link" :to="{name:'StorePage'}">
              <i class="align-middle" data-feather="store"></i> <span class="align-middle">상점</span>
            </router-link>
					</li>

          <li class="sidebar-item">
						<router-link class="sidebar-link" :to="{name:'GroupSettingPage'}">
              <i class="align-middle" data-feather="setting"></i> <span class="align-middle">그룹관리</span>
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

}
</script>

<style>

</style>