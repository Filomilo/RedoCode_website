<template>
  <div
    class=""
    role="status"
    style="height: 100%; width: 100%; aspect-ratio: 1/1"
  >
    <span class="loader"></span>
  </div>
</template>

<style lang="css">
  .loader {
    width: 100%;
    height: 100%;
    border: 0px solid #fff;
    border-radius: 90%;
    display: inline-block;
    position: relative;
    box-sizing: border-box;
    animation: rotation 1s linear infinite;
  }
  .loader::after {
    content: '';
    box-sizing: border-box;
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
    width: 100%;
    height: 100%;
    border-radius: 70%;
    border: 9px solid;
    border-color: var(--primary-color) transparent;
  }

  @keyframes rotation {
    0% {
      transform: rotate(0deg);
    }
    100% {
      transform: rotate(360deg);
    }
  }
</style>
