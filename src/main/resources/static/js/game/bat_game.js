document.addEventListener("DOMContentLoaded", function () {
  const snails = document.querySelectorAll(".snail");
  const startBtn = document.querySelector("#bat_start_btn");
  const resultText = document.querySelector("#result");
  let num_radio = document.querySelectorAll(".num1, .num2, .num3, .num4, .num5");
  let bat_radio = document.querySelectorAll(".P100, .P500, .P1000, .P3000");

  var modal = document.querySelector(".battingModal");
  var run_Btn = document.querySelector(".run_btn");
  var memberId = sessionStorage.getItem('member_id');

  startBtn.onclick = function () {
    getSessionValue(); // 세션 값을 요청
    console.error(memberId);
  };

  run_Btn.onclick = function () {
    modal.style.display = "none";
  };

  run_Btn.addEventListener("click", startRace);

  window.addEventListener("beforeunload", function (event) {
    navigator.sendBeacon('/logout');
  });

  function getSessionValue() {
    $.ajax({
      url: '/member/check-login-status',
      type: 'GET',
      success: function(response) {
        console.log('Received session value:', response);
        if (response) {
          modal.style.display = "block";
        } else {
          swal({
            title: "로그인을 먼저 해주세요.",
            text: "로그인이 되어있지 않습니다.",
            type: "error",
            confirmButtonText: "확인",
            confirmButtonColor: "#FF5065"
          });
        }
      },
      error: function(xhr, status, error) {
        console.error('Failed to fetch session value:', error);
        swal({
          title: "로그인을 먼저 해주세요.",
          text: "로그인이 되어있지 않습니다.",
          type: "error",
          confirmButtonText: "확인",
          confirmButtonColor: "#FF5065"
        });
      }
    });
  }

  function startRace() {
    console.log("시작");
    console.error(memberId);
    let win;


    let batChecked = false;
    let numChecked = false;

    let checkedRadio;
    let checkedRadio1;

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

    if (batChecked && numChecked) {
      const resultPointWin = checkedRadio.className.slice(1) * 3;
      const resultPointLose = parseInt(checkedRadio.className.slice(1));

      console.error(resultPointWin)
      console.error(resultPointLose)
      console.error(win);

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

            if (snailNum == winSnail) {
              const winSnail = snail.id.slice(5);
              win = true;
              swal({
                title: `${winSnail}번 달팽이가 \n 1등으로 도착했습니다! :)`,
                text: "배팅금액의 포인트 3배를 획득했습니다. :)",
                type: "success",
                confirmButtonText: "확인",
                confirmButtonColor: "#FF5065",
              }).then(function () {
                location.reload(true);
              });
              console.error("==============")
              console.error(win)
            } else {
              win = false;
              swal({
                title: `${winSnail}번 달팽이가 \n 1등으로 도착했습니다! :)`,
                text: "아쉽게도 포인트를 획득하지 못했습니다...ㅜㅜ :)",
                type: "error",
                confirmButtonText: "확인",
                confirmButtonColor: "#FF5065",
              }).then(function () {
                location.reload(true);
              });
              console.error("==============")
              console.error(win)
            }
            $.ajax({
              url: "/game/update-point",
              type: 'POST',
              data: {win: win, resultPointWin: resultPointWin, resultPointLose: resultPointLose, memberId: memberId },
              success: function(response) {
                console.log('포인트 업데이트 성공', response);
              },
              error: function(xhr, status, error) {
                console.log(memberId);
                console.error('포인트 업데이트 실패', error);
              }
            });
            console.error(win)
          }

        });
      }, 50);
    } else {
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
