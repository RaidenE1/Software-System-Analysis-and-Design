<template>
  <div class="site-wrapper site-page--not-found">
    <div class="site-content__wrapper">
      <div class="site-content">
        <img style="width: 200px; height: 200px;" src='../assets/404.jpeg'>
        <p class="not-found-desc">抱歉！<em>找不到</em>该网页 ...</p>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: '404'
}
</script>

<style>
.site-wrapper.site-page--not-found {
  position: absolute;
  top: 60px;
  right: 0;
  bottom: 0;
  left: 0;
  overflow: hidden;
}
.site-content__wrapper {
   padding: 0;
   margin: 0;
   background-color: #fff;
 }
.site-content {
  position: fixed;
  top: 15%;
  left: 50%;
  z-index: 2;
  padding: 30px;
  text-align: center;
  transform: translate(-50%, 0);
}
.not-found-title {
  margin: 20px 0 15px;
  font-size: 8em;
  font-weight: 500;
  color: rgb(55, 71, 79);
}
.not-found-desc {
  margin: 0 0 30px;
  font-size: 26px;
  text-transform: uppercase;
  color: rgb(118, 131, 143);
}
em {
  font-style: normal;
  color: #F90716;
  font-size: 150%;
  font-weight: bold;
}
.not-found-btn-gohome {
  margin-left: 30px;
}
</style>
