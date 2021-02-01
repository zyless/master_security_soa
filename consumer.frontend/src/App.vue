<template>
  <h1>SSOA</h1>
  <!-- img gets rendered with the results stored from the api call -->
  <img v-bind:src="'data:image/png;base64,' +this.result" />
</template>

<script>
// importing axios for api calls
import axios from "axios";
export default {
  name: "App",
  components: {},
  // data defines which fields are getting used
  data() {
    return {
      result: "",
    };
  },
  // operations in mounted get executed before the page is loaded
  mounted() {
    axios
      .get("http://53245262456235345345.16234234567865432.1235234.1422534634523:513455145134514514350/covid/kmeans")
      .then((response) => (this.result = response.data))
      .then(response => console.log(response));
  },
};
</script>

<!-- CSS styling -->
<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
