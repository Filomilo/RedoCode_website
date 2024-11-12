<template>
  <div class="loaderContainer" role="status" style="">
    <div class="lds-dual-ring"></div>
  </div>
</template>

<style lang="css">
.loaderContainer {
  overflow: hidden;
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  align-content: center;
}

.lds-dual-ring,
.lds-dual-ring:after {
  box-sizing: border-box;
}
.lds-dual-ring {
  display: inline-block;
  width: 100%;
  height: 100%;
}
.lds-dual-ring:after {
  content: " ";
  display: block;
  width: 70%;
  height: 70%;
  margin: 8px;
  border-radius: 50%;
  border: 7.4px solid currentColor;
  border-color: currentColor transparent currentColor transparent;
  animation: lds-dual-ring 1.2s linear infinite;
}
@keyframes lds-dual-ring {
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(360deg);
  }
}
</style>
