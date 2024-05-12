document.addEventListener('DOMContentLoaded', function() {

    // ------------로그인-------------
    function checkLoginStatus() {
        // 여기서 로그인 상태를 검사하고, 로그인 상태에 따라 클래스를 변경
        var loginBtn = document.querySelector('.loginbtn');

        // 로그인 상태라고 가정하고 'profile' 클래스를 바로 추가
        // -> 후에 로그인 상태일때만 바꾸기
        // loginBtn.classList.remove('loginbtn');
        // loginBtn.classList.add('profile');

        // 'profile' 클래스가 있는지 확인
        if (loginBtn.classList.contains('profile')) {
            // 'p' 태그를 찾아 삭제
            var pTag = loginBtn.querySelector('p');
            if (pTag) {
                pTag.remove();
            }
            // onclick도 삭제
            loginBtn.removeAttribute('onclick');
        }
    }

    // 페이지 로드 시 실행
    window.onload = checkLoginStatus;

    //------------------------- upbtn --------------------------------------------
    document.getElementById('upbtn').addEventListener('click', function() {
        window.scrollTo({
            top: 0,
            behavior: 'smooth' // 스크롤 효과
        });
    });

    // --------------------------------------- 탭 --------------------------------------
    // 모든 탭 요소를 가져옴
    // 현재 URL을 기반으로 활성 탭 설정
    const currentPath = window.location.pathname;
    const tabs = document.querySelectorAll('.tab'); // 모든 탭 선택

    tabs.forEach(tab => {
        // 링크가 현재 경로와 일치하는지 확인
        const path = tab.getAttribute('data-path');
        if (path === currentPath) {
            tab.classList.add('active'); // active 클래스 추가
        } else {
            tab.classList.remove('active'); // 다른 탭에서 active 클래스 제거
        }
    });

    // --------------------------------------- 메세지 --------------------------------------
    // const messageCom = document.querySelector('.messagecom');
    // const message = messageCom.querySelector('.message');
    // const redDot = messageCom.querySelector('.reddot');
    // const messageBox = document.querySelector('.messagebox');
    //
    // messageCom.addEventListener('click', function() {
    //     // message 요소에 'message_open' 클래스를 토글
    //     const isOpen = message.classList.toggle('message_open');
    //
    //     // reddot 요소의 표시 상태를 토글
    //     redDot.style.display = isOpen ? 'none' : 'block';
    //
    //     // messageBox 요소의 표시 상태를 토글
    //     messageBox.style.display = isOpen ? 'block' : 'none';
    // });


    // --------------------------------------- 프로필 --------------------------------------
    const loginBtnContainer = document.querySelector('.loginbtn');
    const dropdown = document.querySelector('.dropdown');

    // loginBtnContainer 클릭 이벤트 리스너
    loginBtnContainer.addEventListener('click', function(event) {
        // 이벤트 버블링 방지
        event.stopPropagation();

        // 'profile' 클래스가 있을 때만 드롭다운 토글
        if (loginBtnContainer.classList.contains('profile')) {
            dropdown.style.display = dropdown.style.display === 'block' ? 'none' : 'block';
        }
    });

    // 전체 문서에 클릭 이벤트 리스너 추가하여 드롭다운 비활성화
    document.addEventListener('click', function() {
        if (dropdown.style.display === 'block') {
            dropdown.style.display = 'none';
        }
    });
});
