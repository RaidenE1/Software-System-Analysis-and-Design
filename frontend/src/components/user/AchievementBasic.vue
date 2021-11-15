<template>
  <div id="achievement">
      <span>{{achievement.title}}</span>
      <span>{{achievement.date}}</span>
  </div>
</template>

<script>
export default {
    props: {
        achievement:{
            title:'',
            date:''
        }
    }
}
</script>

<style>

</style>