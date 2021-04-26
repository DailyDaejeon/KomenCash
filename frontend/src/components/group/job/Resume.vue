<template>
  <main class="d-flex w-100">
    <div class="container d-flex flex-column">
      <div class="row vh-100">
        <div class="col-sm-10 col-md-8 col-lg-6 mx-auto d-table h-100">
          <div class="d-table-cell">
            <div class="text-center mt-4">
              <h1 class="h2">[학생이름] 이력서</h1>
            </div>

            <div class="card">
              <div class="card-body">
                <div class="m-sm-4">
                  <form @submit.prevent="resumeComplete">
                    <div class="mb-3">
                      <label class="form-label">{{JobType}}명</label>
                      <h3>직업이름</h3>                    
                    </div>
                    <div class="mb-3">
                      <label class="form-label">이름</label>
                      <h3>이름</h3>                    
                    </div>
                    <div class="mb-3">
                      <label class="form-label">지원동기</label>
                      <div>
                        {{지원동기}}  
                      </div>                    
                    </div>
                    <div class="text-center mt-3">
                      <button 
                      type="submit" class="btn btn-lg btn-main">승인</button>
                    </div>
                  </form>
                  <div class="mt-4">
                    <h6>다른 이력서를 더 보고싶다면? <router-link :to="{name:'JobResumeRequest'}">이력서페이지로</router-link></h6>
                  </div>
                </div>
              </div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</main>
</template>

<script>
export default {
  props:{
    JobType:{
      type:String,
    }
  }
}
</script>

<style>

</style>