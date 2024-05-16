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





// 2. 파일 서버 업로드 및 제거
async function uploadToServer(formObj) {

    console.log("upload to server......")
    console.log(formObj)

    const response = await axios({
        method: 'post',
        url: '/upload',
        data: formObj,
        headers: {
            'Content-Type': 'multipart/form-data',
        },
    });

    return response.data
}

async function removeFileToServer(uuid, fileName) {

    const response = await axios.delete(`/remove/${uuid}_${fileName}`)

    return response.data
}


// 3. 프로필 수정
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
            profileImg.src = e.target.result;
        };

        reader.readAsDataURL(file);
    }
});

const updateButton = document.getElementById('confirmStart');
updateButton.addEventListener('click', async () => {
    const nickname = document.getElementById('nickname').value;
    const password = document.getElementById('password').value;
    let imgPath = document.querySelector('.profile_value').value;
    const fileInput = document.querySelector("input[name='files']");

    console.log(fileInput.files);

    const formObj = new FormData();
    const files = fileInput.files;

    if (files.length > 0) { formObj.append("files", files[0]); }
    formObj.append("nickname", nickname);
    formObj.append("password", password);

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
            const uploadPromise = files.length > 0 ? uploadToServer(formObj) : Promise.resolve([]);

            // 여기서는 첨부파일만 처리하는게 아니라서 변경된 이미지가 없어도 서버로 formData를 보낼 수 있도록 처리
            // uploadToServer(formObj).then(uploadResult => {
            //     imgPath = `${uploadResult[0].uuid}_${uploadResult[0].fileName}`;
            uploadPromise.then(uploadResult => {
                if (uploadResult.length > 0) {
                    const newImgPath = `${uploadResult[0].uuid}_${uploadResult[0].fileName}`;

                    if (imgPath) {
                        const [uuid, fileName] = imgPath.split('_');
                        removeFileToServer(uuid, fileName);
                    }
                    imgPath = newImgPath;
                }

                axios.put('/myPage/updateMyInfo', {
                    nickname: nickname,
                    password: password,
                    imgPath: imgPath
                }, {
                    headers: {
                        'Content-Type': 'application/json'
                    }
                })
                    .then(function () {
                        sessionStorage.setItem("member_profile", imgPath);

                        Swal.fire({
                            confirmButtonColor: "#FFBB00",
                            title: "정보 변경이 완료되었습니다.",
                            text: "현재 보유 포인트에서 500 포인트 차감되었습니다.",
                            icon: "success"
                        }).then(() => {
                            location.reload();
                        });
                        document.querySelector('.modifyModal').style.display = 'none';
                        document.querySelector('.overlay').style.display = 'none';
                    })
                    .catch(function () {
                        Swal.fire({
                            icon: "error",
                            title: "보유 포인트가 부족합니다.",
                            text: "미니 게임으로 포인트를 획득하세요!"
                        });
                    });
            }).catch(function (error) {
                    Swal.fire({
                        icon: "error",
                        title: "프로필 수정 실패",
                        text: "다시 시도해 주세요."
                    });
                });
        }
    });
});


// 4. 내정보 수정 입력 제한 - 없어도 되긴 한디,, 2차 방지
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


// 5. 회원 탈퇴
const unregisterBtn = document.querySelector(".unregister_btn ");
unregisterBtn.addEventListener('click', async function () {
    const {value: password} = await Swal.fire({
        title: "본인 확인",
        html: "고객님의 소중한 개인정보 보호를 위해서<br> 본인확인을 진행합니다.",
        input: "password",
        inputPlaceholder: "비밀번호를 입력하세요",
        confirmButtonColor: "#FFBB00"
    });

    if (password) {
        fetch('/myPage/verifyPassword', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({password: password})
        })
            .then(response => response.json())
            .then(data => {
                if (data.isValid) {
                    showConfirmStart();
                } else {
                    notMatchAlert();
                }
            })
            .catch(() => {
                Swal.fire("오류", "서버 오류가 발생했습니다.", "error");
            });
    } else {
        Swal.fire("경고", "비밀번호를 입력해주세요.", "warning");
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
            fetch('/myPage/unregister', {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json'
                }
            })
                .then(response => {
                    if (response.ok) {
                        window.location.href = '/';
                    } else {
                        throw new Error();
                    }
                })
                .catch(error => {
                    Swal.fire("오류", "탈퇴 처리 중 오류가 발생했습니다: " + error.message, "error")
                });
        }
    });
}

function notMatchAlert() {
    Swal.fire({
        icon: "error",
        title: "비밀번호가 일치하지 않습니다.",
        text: "5초 후 창이 자동으로 닫힙니다.",
        timer: 5000,
        timerProgressBar: true,
        didOpen: () => {
            Swal.showLoading();
        },
    });
}


// 6. 내 랭크 100위 이상일 경우 99+ 처리
const rankingElement = document.querySelector('.my_rank > .rank_icon > p');
const ranking = parseInt(rankingElement.textContent);

if (ranking >= 100) {
    rankingElement.textContent = '99+';
}
