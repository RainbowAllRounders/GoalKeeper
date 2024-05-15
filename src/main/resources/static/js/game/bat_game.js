document.addEventListener("DOMContentLoaded", function () {
  const snails = document.querySelectorAll(".snail");
  const startBtn = document.querySelector("#bat_start_btn");
  const resultText = document.querySelector("#result");
  // 수정된 부분: num_radio와 bat_radio 선택자에 공백이 포함된 클래스 이름을 사용했을 가능성이 있습니다.
  let num_radio = document.querySelectorAll(".num1, .num2, .num3, .num4, .num5");
  let bat_radio = document.querySelectorAll(".P100, .P500, .P1000, .P3000");


  var modal = document.querySelector(".battingModal");
  var run_Btn = document.querySelector(".run_btn");

  startBtn.onclick = function () {
    getSessionValue(); // 세션 값을 요청
  };

  run_Btn.onclick = function () {
    modal.style.display = "none";
  };

  // run_Btn 클릭 이벤트에 getSessionValue 함수 등록
  run_Btn.addEventListener("click", startRace);

  window.addEventListener("beforeunload", function (event) {
    // 서버로 로그아웃 요청을 보냄
    navigator.sendBeacon('/logout');
  });


  // AJAX를 사용하여 서버로 세션 값을 요청하는 함수
  function getSessionValue() {
    // 서버로 요청을 보냄
    $.ajax({
      url: '/member/check-login-status', // 세션 값을 요청할 서버의 엔드포인트
      type: 'GET', // GET 요청
      success: function(response) {
        // 세션 값이 정상적으로 받아졌을 때 실행되는 콜백 함수
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
        // 요청이 실패했을 때 실행되는 콜백 함수
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

    if (batChecked && numChecked) {
      const resultPointWin = checkedRadio.className.slice(1) * 3;
      const resultPointLose = parseInt(checkedRadio.className.slice(1));

      // AJAX POST 요청으로 resultPoint1 값을 전달
      $.ajax({
        url: "/game/update-point", // 포인트 업데이트를 처리할 서버의 엔드포인트
        type: 'POST', // POST 요청
        data: { resultPointWin: resultPointWin,resultPointLose: resultPointLose},
        // 전달할 데이터
        success: function(response) {
          // 성공적으로 업데이트가 되었을 때 실행되는 콜백 함수
          console.log('포인트 업데이트 성공', response);
        },
        error: function(xhr, status, error) {
          // 요청이 실패했을 때 실행되는 콜백 함수
          console.error('포인트 업데이트 실패', error);
        }
      });

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
              swal({
                title: `${winSnail}번 달팽이가 1등으로 도착했습니다! :)`,
                text: "배팅금액의 포인트 3배를 획득했습니다. :)",
                type: "success",
                confirmButtonText: "확인",
                confirmButtonColor: "#FF5065",
              }).then(function () {
                location.reload(true);
              });
              return resultPointWin;
            } else {
              swal({
                title: `${winSnail}번 달팽이가 1등으로 도착했습니다! :)`,
                text: "아쉽게도 포인트를 획득하지 못했습니다...ㅜㅜ :)",
                type: "error",
                confirmButtonText: "확인",
                confirmButtonColor: "#FF5065",
              }).then(function () {
                location.reload(true);
              });
              return resultPointLose;
            }
          }

        });
      }, 50);
    } else {
      !batChecked && !numChecked;
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
