<template>
  <router-view v-slot="{ Component }">
    <transition name="scale-slide">
        <component :is="Component" />
    </transition>
  </router-view>
</template>

<script>

export default {
  setup(){

  }
}

</script>

<style scoped >

</style>