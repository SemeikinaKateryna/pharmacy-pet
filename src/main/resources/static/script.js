function showStar() {
    const value = this.value.trim();
    const star = this.parentNode.querySelector(".star");
    if (value !== "") {
        star.style.display = "none";
    } else {
        star.style.display = "inline";
    }
}

function validateInput(input, maxLength, minValue) {
    input.addEventListener("input", function () {
        const value = this.value.trim();
        const errorMessage = this.parentNode.querySelector(".error-message");
        const fieldName = this.getAttribute("name");

        if (maxLength && value.length > maxLength) {
            errorMessage.textContent = "Max length exceeded";
            errorMessage.style.display = "block";
            errorMessage.style.marginLeft = "20px";
            errorMessage.style.marginTop = "5px";
            this.style.backgroundColor = "initial"; // Reset background color
        } else if (value === "") {
            errorMessage.style.display = "none";
            this.style.backgroundColor = "lightgrey"; // Light gray color
        } else if (fieldName === "Username") {
            if (value.length < 3) {
                errorMessage.textContent = "Username must be at least 3 characters";
                errorMessage.style.display = "block";
                errorMessage.style.marginLeft = "20px";
                errorMessage.style.marginTop = "5px";
                this.style.backgroundColor = "initial"; // Reset background color
            } else {
                errorMessage.style.display = "none";
                this.style.backgroundColor = "lightgrey"; // Light gray color
            }
        } else if (fieldName === "Age") {
            if (isNaN(value) || Number(value) < minValue || Number(value) > 123) {
                errorMessage.textContent = "Invalid age";
                errorMessage.style.display = "block";
                errorMessage.style.marginLeft = "20px";
                errorMessage.style.marginTop = "5px";
                this.style.backgroundColor = "initial"; // Reset background color
            } else {
                errorMessage.style.display = "none";
                this.style.backgroundColor = "lightgrey"; // Light gray color
            }
        } else if (fieldName === "Confirm Password") {
            const passwordInput = document.querySelector('input[name="Password"]');
            if (value !== passwordInput.value) {
                errorMessage.textContent = "Passwords do not match";
                errorMessage.style.display = "block";
                errorMessage.style.marginLeft = "20px";
                errorMessage.style.marginTop = "5px";
                this.style.backgroundColor = "initial"; // Reset background color
            } else {
                errorMessage.style.display = "none";
                this.style.backgroundColor = "lightgrey"; // Light gray color
            }
        } else if (fieldName === "Password") {
            const confirmPasswordInput = document.querySelector('input[name="Confirm Password"]');
            if (confirmPasswordInput.value !== "" && value !== confirmPasswordInput.value) {
                errorMessage.textContent = "Passwords do not match";
                errorMessage.style.display = "block";
                errorMessage.style.marginLeft = "20px";
                errorMessage.style.marginTop = "5px";
                this.style.backgroundColor = "initial"; // Reset background color
            } else if (value.length < 8) {
                errorMessage.textContent = "Password must be at least 8 characters";
                errorMessage.style.display = "block";
                errorMessage.style.marginLeft = "20px";
                errorMessage.style.marginTop = "5px";
                this.style.backgroundColor = "initial"; // Reset background color
            } else {
                errorMessage.style.display = "none";
                this.style.backgroundColor = "lightgrey"; // Light gray color
            }
        } else {
            errorMessage.style.display = "none";
            this.style.backgroundColor = "lightgrey"; // Light gray color
        }

        showStar.call(this);
    });
}

function togglePasswordVisibility() {
    var passwordInput = document.getElementById("passwordInput");
    var confirmPasswordInput = document.getElementById("confirmPasswordInput");
    var showPasswordButton = document.querySelector(".show-password");

    if (passwordInput.type === "password") {
        passwordInput.type = "text";
        confirmPasswordInput.type = "text";
        showPasswordButton.innerHTML = "&#128064;"; // Изменить значок на скрытый пароль
    } else {
        passwordInput.type = "password";
        confirmPasswordInput.type = "password";
        showPasswordButton.innerHTML = "&#128065;"; // Изменить значок на показ пароля
    }
    showPasswordButton.style.fontSize = "20px"; // Установить размер кнопки
}

const inputs = document.querySelectorAll("input");

inputs.forEach(function (input) {
    const parent = input.parentNode;
    const details = parent.querySelector(".details");
    const fieldName = details.textContent.trim();
    if (fieldName !== "Address") {
        const star = document.createElement("span");
        star.style.color = "red";
        star.classList.add("star");
        star.textContent = " *";
        details.appendChild(star);

        const errorMessage = document.createElement("span");
        errorMessage.classList.add("error-message");
        errorMessage.style.marginLeft = "50px";
        parent.appendChild(errorMessage);
    }

    const maxLength = {
        "Full Name": 80,
        "Username": 50,
        "Email": 80,
        "Phone Number": 20,
        "Age": 123,
        "Address": 80,
        "Password": 16,
        "Confirm Password": 16
    };

    const minValue = {
        "Age": 0
    };

    validateInput(input, maxLength[fieldName], minValue[fieldName]);
});

const form = document.querySelector("form");

form.addEventListener("submit", async function (event) {
    event.preventDefault();

    const errorMessages = document.querySelectorAll(".error-message");
    const errorFound = Array.from(errorMessages).some(function (errorMessage) {
        return errorMessage.style.display === "block";
    });

    if (errorFound) {
        // Display an error message to the user
        const errorContainer = document.querySelector(".error-container");
        errorContainer.textContent = "Please fix the errors before submitting the form.";
        errorContainer.style.display = "block";
    } else {
        // All inputs are valid, proceed with form submission
        try {
            const response = await fetch("/checkFormData", {
                method: "POST",
                body: new FormData(form)
            });

            if (response.ok) {
                // Form data is valid, submit the form
                form.submit();
            } else {
                // Display an error message to the user
                const errorContainer = document.querySelector(".error-container");
                errorContainer.textContent = "An error occurred. Please try again.";
                errorContainer.style.display = "block";
            }
        } catch (error) {
            // Display an error message to the user
            const errorContainer = document.querySelector(".error-container");
            errorContainer.textContent = "An error occurred. Please try again.";
            errorContainer.style.display = "block";
        }
    }
});

const showPasswordButton = document.querySelector(".show-password");
showPasswordButton.addEventListener("click", togglePasswordVisibility);