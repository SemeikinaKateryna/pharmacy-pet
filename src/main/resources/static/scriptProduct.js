// Получаем все кнопки "Додати до кошика"
let addToCartButtons = document.getElementsByClassName('add-to-cart-btn');

// Получаем элемент иконки корзины и цифры для количества товаров
let cartIcon = document.querySelector('.bi-cart4');
let cartItemCountElement = document.querySelector('.cart-item-count');

// Получаем список товаров в корзине из сессии
let cartItems = JSON.parse(sessionStorage.getItem('cartItems')) || [];
let cartItemCount = cartItems.length;

// Обновляем текст в иконке корзины и цифре
cartIcon.setAttribute('data-count', cartItemCount);
cartItemCountElement.textContent = cartItemCount;

// Обрабатываем клик по кнопке
for (let i = 0; i < addToCartButtons.length; i++) {
    (function(index) {
        addToCartButtons[index].addEventListener('click', function(event) {
            let productName = event.target.dataset.productName; // Получаем имя товара
            let productPrice = event.target.dataset.productPrice; // Получаем цену товара

            let cartItems = JSON.parse(sessionStorage.getItem('cartItems')) || []; // Получаем текущий список товаров в корзине

            // Создаем объект с именем и ценой товара и добавляем его в корзину
            let cartItem = {
                name: productName,
                price: productPrice
            };
            cartItems.push(cartItem);

            // Сохраняем обновленный список товаров в корзине в сессии
            sessionStorage.setItem('cartItems', JSON.stringify(cartItems));

            // Обновляем количество товаров в иконке корзины и цифре
            let cartItemCount = cartItems.length;
            cartIcon.setAttribute('data-count', cartItemCount);
            cartItemCountElement.textContent = cartItemCount;

            let cartMessage = document.getElementById("cart-message");
            cartMessage.style.display = "block"; // Показать сообщение

            setTimeout(function() {
                cartMessage.style.display = "none"; // Скрыть сообщение через 10 секунд
            }, 10000);
        });
    })(i);
}

