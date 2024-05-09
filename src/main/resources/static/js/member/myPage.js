// 1. 모달
var modal = document.querySelector(".modifyModal");
var openBtn = document.querySelector(".modal_open");
var closeBtn = document.querySelector(".cancel_btn");
var saveBtn = document.querySelector(".save_btn");
var overlay = document.querySelector(".overlay");

openBtn.onclick = function () {
    modal.style.display = "block";
    overlay.style.display = "block";
};
closeBtn.onclick = function () {
    modal.style.display = "none";
    overlay.style.display = "none";
};
saveBtn.onclick = function () {
    modal.style.display = "none";
    overlay.style.display = "none";
};


// 2. 프로필 수정
const modifyBtn = document.querySelector(".modify_btn");
const fileInput = document.querySelector(".fileInput");
const profileImg = document.querySelector(".profile_img");

modifyBtn.addEventListener("click", function () {
    fileInput.click();
});

fileInput.addEventListener("change", function () {
    if (fileInput.files.length > 0) {
        const file = fileInput.files[0];
        const reader = new FileReader();

        reader.onload = function (e) {
            profileImg.style.backgroundImage = `url('${e.target.result}')`;
        };

        reader.readAsDataURL(file);
    }
});

const updateButton = document.getElementById('confirmStart');
updateButton.addEventListener('click', function () {
    const nickname = document.getElementById('nickname').value;
    const password = document.getElementById('password').value;

    Swal.fire({
        title: "정말로 변경 하시겠습니까?",
        text: "프로필 수정시 500 포인트가 차감됩니다.",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#FFBB00",
        cancelButtonColor: "#2A9AD9",
        confirmButtonText: "적용",
        cancelButtonText: "취소",
        reverseButtons: false
    }).then((result) => {
        if (result.isConfirmed) {
            $.ajax({
                url: '/member/updateMyInfo',
                type: 'PUT',
                contentType: 'application/json',
                data: JSON.stringify({nickname: nickname, password: password}),
                success: function () {
                    Swal.fire({
                        confirmButtonColor: "#FFBB00",
                        title: "정보 변경이 완료되었습니다.",
                        text: "현재 보유 포인트에서 500 포인트 차감되었습니다.",
                        icon: "success"
                    }).then(() => {
                            location.reload();
                        });
                    $('.modifyModal, .overlay').hide();
                },
                error: function () {
                    Swal.fire({
                        icon: "error",
                        title: "보유 포인트가 부족합니다.",
                        text: "미니 게임으로 포인트를 획득하세요!"
                    });
                }
            });
        }
    });
});


// 3. 내정보 수정 입력 제한 - 없어도 되긴 한디,, 2차 방지
// document
//     .getElementById("confirmStart")
    updateButton.addEventListener("click", function (event) {
        var nickname = document.getElementById("nickname").value;
        var password = document.getElementById("password").value;

        if (nickname.length > 6) {
            alert("닉네임은 최대 6글자입니다.");
            event.preventDefault();
        } else if (password.length > 10) {
            alert("비밀번호는 최대 10글자입니다.");
            event.preventDefault();
        }
    });


// 4. 회원 탈퇴 - 아직 미완성
$().ready(function () {
    $(document).ready(function () {
        $("#promptStart").click(async function () {
            const { value: password } = await Swal.fire({
                title: "본인 확인",
                html: "고객님의 소중한 개인정보 보호를 위해서<br> 본인확인을 진행합니다.",
                input: "password",
                inputPlaceholder: "비밀번호를 입력하세요",
                confirmButtonColor: "#FFBB00",
            });

            if (password) {
                // TODO: 입력 비밀번호와 DB 정보 일치하는지 확인 후
                showConfirmStart();
            }

            // TODO: 비밀번호가 일치하지 않는 경우 예외 처리
            else {
                notMatchAlert();
            }
        });

        function showConfirmStart() {
            Swal.fire({
                title: "정말 탈퇴 하시겠어요?",
                text: "탈퇴 버튼 선택시, 계정은 삭제되며 복구되지 않습니다.",
                icon: "warning",
                showCancelButton: true,
                confirmButtonColor: "#FFBB00",
                cancelButtonColor: "#2A9AD9",
                confirmButtonText: "탈퇴",
                cancelButtonText: "취소",
                reverseButtons: false,
            }).then((result) => {
                if (result.isConfirmed) {
                    Swal.fire({
                        confirmButtonColor: "#FF5065",
                        title: "탈퇴 처리가 완료되었습니다.",
                        text: "다시 돌아오실거죠?",
                        icon: "success",
                    });
                    // TODO: DB에서 member 정보 삭제 처리
                }
            });
        }

        function notMatchAlert() {
            Swal.fire({
                icon: "error",
                title: "비밀번호가 일치하지 않습니다.",
                text: "5초 후 창이 자동으로 닫힙니다.",
                timer: 5000,
                timerProgressBar: true, // 타이머 진행 상태를 보여주는 프로그레스 바 활성화
                didOpen: () => {
                    Swal.showLoading(); // 로딩 애니메이션 표시
                },
            });
        }
    });
});


// 5. 내 랭크 100위 이상일 경우 99+ 처리
const rankingElement = document.querySelector('.my_rank > .rank_icon > p');
const ranking = parseInt(rankingElement.textContent);

if (ranking >= 100) {
    rankingElement.textContent = '99+';
}
