<template>
  <div style="padding: 15px">

    <div style="background-color: white;border-radius: 10px;padding: 5px;">
      <slot name="one"></slot>
    </div>


    <div style="display: flex;">
      <div style="margin-top: 13px;background-color: white;border-radius: 10px;margin-right: 10px;">
        <slot name="two"></slot>
      </div>

      <div style="margin-top: 13px;background-color: white;border-radius: 10px;flex: 1;width: 0;">

        <div style="padding: 10px;">
          <slot name="three"></slot>
        </div>

      </div>
    </div>

  </div>
</template>

<script>
export default {
  name: "x_table_template"
}
</script>

<style scoped>

</style>