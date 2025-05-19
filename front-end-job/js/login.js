
document.getElementById("loginForm").addEventListener("submit", function (e) {
  e.preventDefault();
  verify();
});

function verify() {
  const email = document.getElementById("email").value;
  const password = document.getElementById("password").value;

  fetch(`${baseUrl}/auth/signin`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ userEmail: email, password: password })
  })
    .then(response => {
      // Handle HTTP status codes with console logs
      if (response.status === 200) {
        return response.json();
      } else if (response.status === 404) {
        document.getElementById("error").textContent = "404 Not Found - Users data not available.";
      } else if (response.status === 401) {
        document.getElementById("error").textContent = "401 Unauthorized - Invalid token or access denied.";
      } else if (response.status === 500) {
        document.getElementById("error").textContent = "500 Internal Server Error.";
      } else {
        document.getElementById("error").textContent = `Unexpected status code: ${response.status}`;
      }
    })
    .then(response => {
      if (!response) return;

      if (response.token)
        localStorage.setItem("token", response.token);
      else
        throw new Error("Token Not Found");

      const userType = getUserType();
      if (userType === "USER") {
        // window.location.href = "../pages/companyDashboard.html";
        window.location.href = "../userPages/User.html";
      } else {
        window.location.href = "../pages/companyDashboard.html";
      }
    }
    )
    .catch(error => {
      console.log(error);
      document.getElementById("error").textContent = "Something went wrong. Please try again.";
    });
}

