document.addEventListener('DOMContentLoaded', function() {

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
    loginBtnContainer.addEventListener('click', function() {
        // 'profile' 클래스가 있으면 드롭다운 토글, 없으면 'profile'로 변환
        if (loginBtnContainer.classList.contains('profile')) {
            // 드롭다운 보이기/숨기기 토글
            dropdown.style.display = dropdown.style.display === 'block' ? 'none' : 'block';
        } else {
            // 클래스 'loginbtn'을 'profile'로 교체하고, 내용을 제거
            loginBtnContainer.classList.replace('loginbtn', 'profile');
            loginBtnContainer.innerHTML = ''; // 내부 HTML 제거

            // 이벤트 리스너를 추가하는 부분을 여기서 제거
        }
    });
});
