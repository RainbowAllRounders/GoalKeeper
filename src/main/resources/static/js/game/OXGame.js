document.addEventListener('DOMContentLoaded', function() {

    var MemberID = sessionStorage.getItem("member_id");

    // --------------------------- 시작화면에서 시작버튼클릭하면 문제화면으로 넘어감 --------------------------
    var start = document.querySelector('.start'); // 시작화면
    var startbtn = document.querySelector('.startbtn'); // 시작버튼

    var Q = document.querySelector('.Q'); // 문제화면

    startbtn.addEventListener('click', function () {
        start.style.display = "none";
        Q.style.display = "flex";
        startQuiz();
    });

    // -------------------------------------- 문제화면 ----------------------------------------------
    var Q_num = document.querySelector('.Q_num');                   // 문제번호
    var Q_quest = document.querySelector('.Q_quest');               // 문제
    var balls = document.querySelectorAll('.ball');     // 맞춘 갯수 활성화
    var O = document.querySelector('.O');                           // O 버튼
    var X = document.querySelector('.X');                           // X 버튼
    var progressBar = document.querySelector('.progress');          // 프로그레스바


    var QNum = 0;       // 현재 문제 번호
    var correctNum = 0; // 맞은 문제 수
    var interval;                // 타이머 인터벌


    // 새로운 문제를 설정
    function setQuestion(index) {
        var question = Questions[index];
        Q_num.textContent = 'Q' + (index + 1);
        Q_quest.textContent = question.question;
        resetProgressBar(); // 새로운 문제 설정 시 프로그레스바 초기화
    }

    // 퀴즈 시작
    function startQuiz() {
        setQuestion(QNum);

        // O 버튼 클릭
        O.addEventListener('click', function() {
            answerQuestion(true);
        });

        // X 버튼 클릭
        X.addEventListener('click', function() {
            answerQuestion(false);
        });

        // 타이머 시작
        startTimer();
    }

    // 질문에 대한 답변을 정답여부 처리
    function answerQuestion(isCorrectAnswer) {
        var question = Questions[QNum];
        var isCorrect = question.isCorrect === isCorrectAnswer;

        // 타이머를 정지시킴
        clearInterval(interval);

        //정답여부 스왈창
        swal({
            title: isCorrect ? '<span style="color: blue;">정답입니다</span>' : '<span style="color: red;">오답입니다</span>',
            html: '<div style="margin-top: 10px;">' + question.commentary + '</div>',
            imageUrl: isCorrectAnswer ? '../../images/OXGame/O.png' : '../../images/OXGame/X.png',
            imageWidth: 100,
            imageHeight: 100,
            timer: 2000,
            showConfirmButton: false
        }).then(function() {
            if (isCorrect) { //정답일때 공 활성화
                balls[QNum].style.opacity = 1;
                correctNum++;
            }
            QNum++;
            if (QNum < Questions.length) { //문제 안끝났으면
                setQuestion(QNum);
                startTimer();
            } else {                       //문제 끝났을때
                endQuiz();
            }
        });
    }

    // 타이머를 시작하는 함수
    function startTimer() {
        var timer = 5; // 타이머 설정 (초 단위)
        var elapsed = 0; // 경과시간

        progressBar.style.width = '0%'; // 초기 너비 설정

        interval = setInterval(function() {
            elapsed += 0.1;
            progressBar.style.width = (elapsed / timer) * 100 + '%';


            if (elapsed >= timer) {
                clearInterval(interval);
                //시간초과 스왈창
                swal({
                    title: '<span style="color: #ffc933;">시간초과</span>',
                    text: Questions[QNum].commentary,
                    imageUrl: '../../images/OXGame/Alarm.png',
                    imageWidth: 100,
                    imageHeight: 100,
                    timer: 2000,
                    showConfirmButton: false
                }).then(function() {
                    QNum++;
                    if (QNum < Questions.length) {
                        setQuestion(QNum);
                        startTimer();
                    } else {
                        endQuiz();
                    }
                });
            }
        }, 100);
    }

    // 프로그레스바를 초기화하는 함수
    function resetProgressBar() {
        progressBar.style.transition = 'none'; // 애니메이션 없이 초기화
        progressBar.style.width = '0%';
        setTimeout(function() {
            progressBar.style.transition = 'width 0.1s linear'; // 부드러운 애니메이션 설정
        }, 10); // 약간의 지연 후 애니메이션 다시 설정
    }

    // 퀴즈를 종료하는 함수
    function endQuiz() {
        var point = correctNum * 100
        addPoints(point);
        swal({
            title: correctNum + ' 문제 정답!',
            html: '<span style="font-size: 32px; color: #ffc933; margin-top: 8px">' + point + ' Point 획득!</span>',
            imageUrl: '../../images/OXGame/oxlogo.png',
            imageWidth: 200,
            imageHeight: 100,
            showConfirmButton: true,
            confirmButtonText: '게임메인으로 돌아가기'
        }).then(function() {
            window.location.href = 'game_main';// 게임 메인 화면으로 리디렉션
        });
    }

    //포인트 적립
    function addPoints(points){
        $.ajax({
            url: '/game/OXUpdatePoint',
            type: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify({point: points}),
            success: function (){
                console.log(MemberID + " : " + points + "적립 성공");
            },
            error: function (){
                console.log(MemberID + " : " + points + "적립 실패");
            }
        })
    }

});
