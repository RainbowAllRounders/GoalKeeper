const signUpButton = document.querySelector("#signup_btn");

signUpButton.onclick = function (event){


    let email = document.querySelector("#signup_addr").value.trim();
    let nickname = document.querySelector("#signup_nickname").value.trim();
    let password = document.querySelector("#signup_password").value.trim();

    if (email === "" || nickname === "" || password === "") {
        swal({
            title: "내용을 입력해주세요.",
            text: "입력하지 않은 내용이 존재합니다.",
            type: "error",
            confirmButtonText: "확인",
            confirmButtonColor: "#FF5065",
        }).then(function () {
            location.reload(true);
        });
        event.preventDefault(); // 이벤트 전파 방지
        return true;
    }
};