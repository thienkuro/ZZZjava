let menu = document.querySelector('#menu-bar');
let navbar = document.querySelector('.navbar');

menu.addEventListener('click', ()=>{
    menu.classList.toggle('fa-time');
    navbar.classList.toggle('active');
});

window.onscroll = () =>{
    menu.classList.remove('fa-time');
    navbar.classList.remove('active');
}
function showSlides() {
    var i;
    var slides = document.getElementsByClassName("mySlides");
    for (i = 0; i < slides.length; i++) {
        slides[i].style.display = "none";
    }
    slideIndex++;
    if (slideIndex > slides.length) {slideIndex = 1}
    slides[slideIndex-1].style.display = "block";
    setTimeout(showSlides, 2000);
}

$(document).ready(function(){
    $(".add-to-cart").click(function(){
      var name = $(this).data("name");
      var price = parseFloat($(this).data("price"));
      alert(name + " added to cart. Price: $" + price.toFixed(2));
    });
  });

