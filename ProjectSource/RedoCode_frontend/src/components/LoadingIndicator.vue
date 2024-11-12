<template>
  <div class="loaderContainer" role="status" style="">
    <div class="loader"></div>
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
    align-self: center;
    padding: 10%;
  }
  .loader {
    width: 40%;
    aspect-ratio: 1;
    position: relative;
  }
  .loader:before,
  .loader:after {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    margin: -30% 0 0 -30%;
    width: 60%;
    aspect-ratio: 1;
    background: rgb(223, 223, 223);
    animation:
      l1-1 2s infinite,
      l1-2 0.5s infinite;
  }
  .loader:after {
    background: rgb(223, 223, 223);
    animation-delay: -1s, 0s;
  }
  @keyframes l1-1 {
    0% {
      top: 0;
      left: 0;
    }
    25% {
      top: 100%;
      left: 0;
    }
    50% {
      top: 100%;
      left: 100%;
    }
    75% {
      top: 0;
      left: 100%;
    }
    100% {
      top: 0;
      left: 0;
    }
  }
  @keyframes l1-2 {
    80%,
    100% {
      transform: rotate(0.5turn);
    }
  }
</style>
