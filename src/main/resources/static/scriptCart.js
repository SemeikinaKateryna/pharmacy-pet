// Обработчик события при нажатии на кнопку "+" или "-"
function updateQuantity(element, increment) {
    var countElement = element.parentNode.querySelector('.count');
    var currentCount = parseInt(countElement.textContent);
    var newCount = currentCount + increment;

    // Не позволяет устанавливать отрицательное количество товара
    if (newCount < 0) {
        return;
    }

    countElement.textContent = newCount;
    calculateSubTotal();

    if (newCount === 0) {
        removeItem(element);
    }
}

// Обработчик события при нажатии на кнопку "Remove"
function removeItem(element) {
    var item = element.closest('.Cart-Items');
    item.parentNode.removeChild(item);
    calculateSubTotal();
    // Дополнительный код для удаления товара из общего подсчета
}

// Функция для расчета Sub-Total
function calculateSubTotal() {
    var cartItems = document.querySelectorAll('.Cart-Items');
    var subTotal = 0;
    var totalItems = 0;

    for (var i = 0; i < cartItems.length; i++) {
        var count = parseInt(cartItems[i].querySelector('.count').textContent);
        var amount = parseFloat(cartItems[i].querySelector('.amount').textContent);

        subTotal += count * amount;
        totalItems += count;
    }

    document.querySelector('.Subtotal').textContent = 'Sub-Total';
    document.querySelector('.items').textContent = totalItems + ' item' + (totalItems !== 1 ? 's' : '');
    document.querySelector('.total-amount').textContent = subTotal.toFixed(2) + ' ₴';


}

// Добавляем обработчики событий при загрузке страницы
window.onload = function () {
    var incrementButtons = document.querySelectorAll('.counter .mybtn:first-child');
    var decrementButtons = document.querySelectorAll('.counter .mybtn:last-child');
    var saveButtons = document.querySelectorAll('.prices .save');
    var removeButtons = document.querySelectorAll('.prices .remove');

    for (var i = 0; i < incrementButtons.length; i++) {
        incrementButtons[i].addEventListener('click', function () {
            updateQuantity(this, 1);
        });
    }

    for (var i = 0; i < decrementButtons.length; i++) {
        decrementButtons[i].addEventListener('click', function () {
            updateQuantity(this, -1);
        });
    }


    for (var i = 0; i < removeButtons.length; i++) {
        removeButtons[i].addEventListener('click', function () {
            removeItem(this);
        });
    }

    calculateSubTotal();
};

// Обработчик события при нажатии на кнопку "Remove all"
function removeAllItems() {
    var cartItems = document.querySelectorAll('.Cart-Items');
    for (var i = 0; i < cartItems.length; i++) {
        cartItems[i].parentNode.removeChild(cartItems[i]);
    }

    calculateSubTotal();
}

// Добавляем обработчик события для кнопки "Remove all"
var removeAllButton = document.getElementById('removeAll');
removeAllButton.addEventListener('click', removeAllItems);
