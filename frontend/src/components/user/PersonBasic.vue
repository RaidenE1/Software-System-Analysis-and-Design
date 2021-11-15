<template>
  <div id='person'>
      <div id="head">

      </div>
      <div id='name'>
          <span>{{basic_info.username}}</span>
      </div>
  </div>
</template>

<script>
export default {
    props: {
        basic_info:{
            head_img:'',
            username:"",
        }
    }
}
</script>

<style>

</style>