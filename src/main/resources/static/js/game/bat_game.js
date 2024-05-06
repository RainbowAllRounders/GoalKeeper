document.addEventListener("DOMContentLoaded", function () {
  const snails = document.querySelectorAll(".snail");
  const startBtn = document.querySelector("#bat_start_btn");
  const resultText = document.querySelector("#result");
  let num_radio = document.querySelectorAll(".num1,.num2,.num3,.num4,.num5");
  let bat_radio = document.querySelectorAll(".P100,.P500,.P1000,.P3000");

  var modal = document.querySelector(".battingModal");
  var run_Btn = document.querySelector(".run_btn");

  startBtn.onclick = function () {
    modal.style.display = "block";
  };
  run_Btn.onclick = function () {
    modal.style.display = "none";
  };

  run_Btn.addEventListener("click", startRace);

  function startRace() {
    console.log("시작");
    let batChecked = false;
    let numChecked = false;

    let checkedRadio;
    let checkedRadio1;

    // bat_radio 내의 라디오 버튼 중 하나라도 체크되었는지 확인
    bat_radio.forEach(function (radio) {
      if (radio.checked) {
        batChecked = true;
        checkedRadio = radio;
      }
    });
    num_radio.forEach(function (radio) {
      if (radio.checked) {
        numChecked = true;
        checkedRadio1 = radio;
      }
    });
    // 달팽이 이름 및 포인트 계산식

    console.log(checkedRadio);
    num_radio.forEach(function (radio) {
      if (radio.checked) {
        numChecked = true;
      }
    });
    // console.log(bat_radio);
    if (batChecked && numChecked) {
      // const resultPoint = checkedRadio.className.slice(1) * 3;
      const resultPoint1 = checkedRadio.className.slice(1) * 3;

      startBtn.style.display = "none";
      const raceInterval = setInterval(function (index) {
        snails.forEach((snail) => {
          snail.position = snail.position || 0;
          snail.position += Math.random() * 10;
          snail.style.left = snail.position + "px";

          if (snail.position + snailsWidth * 2.45 >= trackWidth) {
            clearInterval(raceInterval);
            const snailNum = checkedRadio1.className.slice(3);
            const winSnail = snail.id.slice(5);
            // resultText.innerText = `${snail.id}번 달팽이가 1등으로 도착했습니다! :)`;
            if (snailNum == winSnail) {
              const winSnail = snail.id.slice(5);
              swal({
                title: `${winSnail}번 달팽이가 1등으로 도착했습니다! :)`, 
                text: "배팅금액의 포인트 3배를 획득했습니다. :)",
                type: "success",
                confirmButtonText: "확인",
                confirmButtonColor: "#FF5065",
              }).then(function () {
                location.reload(true);
              });
            } else {
              swal({
                title: `${winSanil}번 달팽이가 1등으로 도착했습니다! :)`,
                text: "아쉽게도 포인트를 획득하지 못했습니다...ㅜㅜ :)",
                type: "error",
                confirmButtonText: "확인",
                confirmButtonColor: "#FF5065",
              }).then(function () {
                location.reload(true);
              });
            }
          }
        });
      }, 50);
    } else {
      !batChecked && !numChecked;
      // alert("번호와 포인트를 선택해주세요");
      swal({
        title: "번호와 포인트를 선택해주세요",
        text: "배팅 할 달팽이 번호와 포인트를 선택하지 않았습니다.",
        type: "error",
        confirmButtonText: "확인",
        confirmButtonColor: "#FF5065",
      });
    }
    resultText.textContent = "꾸물꾸물 기어가는 달팽이~~";

    const trackWidth = 1000;
    const snailsWidth = 50;
  }
});
