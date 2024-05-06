document.addEventListener("DOMContentLoaded", function() {
    var swiper = new Swiper(".swiper", {
        slidesPerView: 3,
        centeredSlides: true,
        loop: true,
        loopedSlides: 9,  // This should match the total number of slides
        spaceBetween: -40,
        autoplay: {
            delay: 5000,
            disableOnInteraction: false,
        },
        pagination: {
            el: ".swiper-pagination",
            clickable: true,
        }
    });
});
