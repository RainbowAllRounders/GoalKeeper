document.addEventListener("DOMContentLoaded", function () {
  const snails = document.querySelectorAll(".snail");
  const startBtn = document.querySelector("#bat_start_btn");
  const resultText = document.querySelector("#result");

  startBtn.addEventListener("click", startRace);

  function startRace() {
    console.log("시작");
    startBtn.disabled = true;
    resultText.textContent = "Start the race!";

    const trackWidth = 1000;
    const snailsWidth = 50;

    startBtn.style.display = "none";
    const raceInterval = setInterval(function (index) {
      console.log("asd");
      snails.forEach((snail) => {
        snail.position = snail.position || 0;
        snail.position += Math.random() * 10;
        // console.log(snail.position);
        snail.style.left = snail.position + "px";

        if (snail.position + snailsWidth >= trackWidth) {
          clearInterval(raceInterval);
          resultText.innerText = `${snail.id}번 달팽이가 1등으로 도착했습니다! :)`;
          startBtn.disabled = false;
        }
      });
    }, 100);
  }
});
