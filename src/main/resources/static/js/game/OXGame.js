document.addEventListener('DOMContentLoaded', function() {

    var start = document.querySelector('.start'); // 시작화면
    var startbtn = document.querySelector('.startbtn'); // 시작버튼

    var Q = document.querySelector('.Q'); // 문제화면

    startbtn.addEventListener('click', function () {
        start.style.display = "none";
        Q.style.display = "flex";
        startQuiz();
    });


    var Q_num = document.querySelector('.Q_num'); // 문제번호
    var Q_quest = document.querySelector('.Q_quest'); // 문제
    var balls = document.querySelectorAll('.ball'); // 맞춘 갯수 활성화
    var O = document.querySelector('.O'); // O 버튼
    var X = document.querySelector('.X'); // X 버튼

    var currentQuestionIndex = 0; // 현재 문제 번호
    var correctAnswers = 0; // 맞은 문제 수
    var interval; // 타이머 인터벌을 저장할 변수

    // 문제를 설정하는 함수
    function setQuestion(index) {
        var question = Questions[index];
        Q_num.textContent = 'Q' + (index + 1);
        Q_quest.textContent = question.question;
    }

    // 퀴즈를 시작하는 함수
    function startQuiz() {
        setQuestion(currentQuestionIndex);

        // O 버튼 클릭 이벤트
        O.addEventListener('click', function() {
            answerQuestion(true);
        });

        // X 버튼 클릭 이벤트
        X.addEventListener('click', function() {
            answerQuestion(false);
        });

        // 타이머 시작
        startTimer();
    }

    // 질문에 대한 답변을 처리
    function answerQuestion(isCorrectAnswer) {
        var question = Questions[currentQuestionIndex];
        var isCorrect = question.isCorrect === isCorrectAnswer;

        // 타이머를 정지시킴
        clearInterval(interval);

        swal({
            title: isCorrect ? '<span style="color: blue;">정답입니다</span>' : '<span style="color: red;">오답입니다</span>',
            html: '<div style="margin-top: 10px;">' + question.commentary + '</div>',
            imageUrl: isCorrectAnswer ? '../../images/OXGame/O.png' : '../../images/OXGame/X.png',
            imageWidth: 100,
            imageHeight: 100,
            timer: 2000,
            showConfirmButton: false
        }).then(function() {
            if (isCorrect) {
                balls[currentQuestionIndex].style.opacity = 1;
                correctAnswers++;
            }
            currentQuestionIndex++;
            if (currentQuestionIndex < Questions.length) {
                setQuestion(currentQuestionIndex);
                startTimer();
            } else {
                endQuiz();
            }
        });
    }

    // 타이머를 시작하는 함수
    function startTimer() {
        var timer = 3;
        interval = setInterval(function() {
            timer--;
            if (timer <= 0) {
                clearInterval(interval);
                swal({
                    title: '시간초과',
                    text: Questions[currentQuestionIndex].commentary,
                    imageUrl: '../../images/OXGame/Alarm.png',
                    imageWidth: 100,
                    imageHeight: 100,
                    timer: 2000,
                    showConfirmButton: false
                }).then(function() {
                    currentQuestionIndex++;
                    if (currentQuestionIndex < Questions.length) {
                        setQuestion(currentQuestionIndex);
                        startTimer();
                    } else {
                        endQuiz();
                    }
                });
            }
        }, 1000);
    }

    // 퀴즈를 종료하는 함수
    function endQuiz() {
        swal({
            title: correctAnswers + ' 문제 정답!',
            html: '<span style="font-size: 32px; color: #ffc933; margin-top: 8px">' + (correctAnswers * 100) + ' Point 획득!</span>',
            imageUrl: '../../images/OXGame/oxlogo.png',
            imageWidth: 200,
            imageHeight: 100,
            showConfirmButton: true,
            confirmButtonText: '게임메인으로 돌아가기'
        }).then(function() {
            // 게임 메인 화면으로 리디렉션
            window.location.href = 'game/game_main';
        });
    }

});
