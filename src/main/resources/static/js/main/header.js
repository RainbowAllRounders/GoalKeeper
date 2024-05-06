document.getElementById('upbtn').addEventListener('click', function() {
    window.scrollTo({
        top: 0,
        behavior: 'smooth' // 부드러운 스크롤 효과
    });
});

document.addEventListener('DOMContentLoaded', function() {
    // 탭
    // 모든 탭 요소를 가져옴
    const tabs = document.querySelectorAll('.navtabs .tab');

    // 각 탭에 클릭 이벤트 리스너를 추가
    tabs.forEach(tab => {
        tab.addEventListener('click', function() {
            // 모든 탭의 특정 클래스를 초기화
            tabs.forEach(t => {
                const icon = t.children[0];
                const text = t.children[1];
                icon.className = icon.className.replace('_pink', ''); // '_pink' 접미사 제거
                text.className = text.className.replace('pink', ''); // 'pink' 클래스 제거
            });

            // 클릭된 탭에만 특정 클래스를 추가
            const icon = this.children[0];
            const text = this.children[1];
            icon.className += '_pink'; // 아이콘에 '_pink' 추가
            text.className += 'pink'; // 텍스트에 'pink' 추가
        });
    });

    // 메세지
    const messageCom = document.querySelector('.messagecom');
    const message = messageCom.querySelector('.message');
    const redDot = messageCom.querySelector('.reddot');
    const messageBox = document.querySelector('.messagebox');

    messageCom.addEventListener('click', function() {
        // message 요소에 'message_open' 클래스를 토글
        const isOpen = message.classList.toggle('message_open');

        // reddot 요소의 표시 상태를 토글
        redDot.style.display = isOpen ? 'none' : 'block';

        // messageBox 요소의 표시 상태를 토글
        messageBox.style.display = isOpen ? 'block' : 'none';
    });


    //프로필
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
