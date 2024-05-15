document.addEventListener('DOMContentLoaded', function() {

    // ------------로그인시에 프로필이랑 messaggecom보이게하기----------------------
    function checkLoginStatus() {
        var loginBtn = document.querySelector('.loginbtn'); // 로그인 버튼
        var message = document.querySelector('.messagecom_Disabled'); // 메시지

        var MemberID = sessionStorage.getItem("member_id"); // 세션 스토리지에 있는 member_id 값 불러오기
        var MemberProfile = sessionStorage.getItem("member_profile"); // 세션 스토리지에 있는 member_profile 값 불러오기

        // MemberID가 존재하지 않고 model로 받아온 memberId가 존재할 때 -> sessionStorage에 memberId 값 저장
        if (!MemberID && typeof memberId !== 'undefined' && memberId !== 'default') {
            MemberID = memberId;  // 전역 변수로 접근
            MemberProfile = memberProfile;
            if (MemberID) {
                sessionStorage.setItem("member_id", MemberID);
                console.log("id session저장");
                sessionStorage.setItem("member_profile", MemberProfile);
                console.log("profile session저장");
            }
        }
        console.log("sessionStorage_memberId : ", MemberID);
        console.log("sessionStorage_memberProfile : ", MemberProfile);

        // 로그인 버튼 <-> 프로필 전환, messagecom 나오게
        if (sessionStorage.getItem("member_id") !== null) {
            // 로그인 버튼 없애고 프로필 나오게
            loginBtn.classList.remove('loginbtn');
            loginBtn.classList.add('profile');
            // 메시지 창
            message.classList.remove('messagecom_Disabled');
            message.classList.add('messagecom');
        } else {
            loginBtn.classList.remove('profile');
            loginBtn.classList.add('loginbtn');
            message.classList.remove('messagecom');
            message.classList.add('messagecom_Disabled');
        }

        // 'profile' 클래스가 있는지 확인
        if (loginBtn.classList.contains('profile')) {
            // 프로필 사진 sessionStorage에 저장된 member_profile 값으로 사진 변경
            var storedProfile = sessionStorage.getItem("member_profile");
            loginBtn.style.backgroundImage = 'url(' + storedProfile + ')';
            loginBtn.style.backgroundSize = 'cover';
            loginBtn.style.backgroundPosition = 'center';

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
    document.getElementById('upbtn').addEventListener('click', function () {
        window.scrollTo({
            top     : 0,
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


    // --------------------------------------- 프로필 --------------------------------------
    const loginBtnContainer = document.querySelector('.loginbtn');
    const dropdown = document.querySelector('.dropdown');

    // loginBtnContainer 클릭 이벤트 리스너
    loginBtnContainer.addEventListener('click', function (event) {
        // 이벤트 버블링 방지
        event.stopPropagation();

        // 'profile' 클래스가 있을 때만 드롭다운 토글
        if (loginBtnContainer.classList.contains('profile')) {
            dropdown.style.display = dropdown.style.display === 'block' ? 'none' : 'block';
        }
    });


    // 전체 문서에 클릭 이벤트 리스너 추가하여 드롭다운, messagebox 비활성화----------------------
    document.addEventListener('click', function () {
        if (dropdown.style.display === 'block') {
            dropdown.style.display = 'none';
        }
    });


    // --------------------------------------- 비로그인시에 미션등록, 미니게임 못들어가게 --------------------------------------

    const goalAddBtn = document.getElementById("addtab");
    const gameBtn = document.getElementById("gametab");

    function checkLoginAndRedirect(e) {
        const memberId = sessionStorage.getItem('member_id');

        if (!memberId) {
            e.preventDefault(); // 기본 동작 방지
            Swal.fire({
                title: '로그인이 필요합니다',
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#FFC933',
                cancelButtonColor: '#2A9AD9',
                confirmButtonText: '로그인 페이지로 이동',
                cancelButtonText: '취소'
            }).then((result) => {
                if (result.isConfirmed) {
                    window.location.href = '/member/login'; // 로그인 페이지로
                } else {
                    console.log("이동 취소됨");
                }
            });
        }
        else{ //로그인상태일때
            const path = e.currentTarget.getAttribute('data-path');
            window.location.href = path;
        }
    }

    goalAddBtn.addEventListener('click', checkLoginAndRedirect);
    gameBtn.addEventListener('click', checkLoginAndRedirect);


    // --------------------------------------- 로그아웃시 sessionStorage삭제 --------------------------------------
    var logoutbtn = document.getElementById("logout")
    // 로그아웃
    logoutbtn.addEventListener('click', function () {
        // sessionStorage에서 member_id 삭제
        sessionStorage.removeItem("member_id");
        // 로그아웃 후 리디렉션할 페이지로 이동
        window.location.href = "/main";
    });

});
