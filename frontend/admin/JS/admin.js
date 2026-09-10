const API_URL = "https://udayhostelmanagement-production.up.railway.app/admins";


// ===============================
// Check Login
// ===============================

const token = localStorage.getItem("token");

if (!token) {

    window.location.href = "login.html";

}


// ===============================
// Load All Admins
// ===============================

function loadAdmins() {

    fetch(API_URL, {

        method: "GET",

        headers: {

            "Authorization":
                "Bearer " + token

        }

    })

    .then(response => {

        if (response.status === 401) {

            logout();

            return;

        }

        return response.json();

    })

    .then(result => {

        if (!result) return;

        displayAdmins(result.data);

    })

    .catch(error => {

        console.error("Error:", error);

        document.getElementById("adminTableBody").innerHTML =

            `<tr>
                <td colspan="5">
                    Failed to load admin details
                </td>
            </tr>`;

    });

}


// ===============================
// Display Admins
// ===============================

function displayAdmins(admins) {

    const tableBody =
        document.getElementById("adminTableBody");


    tableBody.innerHTML = "";


    if (!admins || admins.length === 0) {

        tableBody.innerHTML =

            `<tr>
                <td colspan="5">
                    No admin found
                </td>
            </tr>`;

        return;

    }


    admins.forEach(admin => {

        const row =
            document.createElement("tr");


        row.innerHTML = `

            <td>
                ${admin.adminId}
            </td>

            <td>
                ${admin.name}
            </td>

            <td>
                ${admin.email}
            </td>

            <td>
                ${admin.role}
            </td>

            <td>

                <button
                    onclick="editAdmin(${admin.adminId})">

                    Edit

                </button>


                <button
                    onclick="deleteAdmin(${admin.adminId})">

                    Delete

                </button>

            </td>

        `;


        tableBody.appendChild(row);

    });

}


// ===============================
// Edit Admin
// ===============================

function editAdmin(id) {

    fetch(API_URL + "/" + id, {

        method: "GET",

        headers: {

            "Authorization":
                "Bearer " + token

        }

    })

    .then(response => response.json())

    .then(result => {

        const admin = result.data;


        document.getElementById("adminId")
            .value = admin.adminId;


        document.getElementById("adminName")
            .value = admin.name;


        document.getElementById("adminEmail")
            .value = admin.email;


        document.getElementById("adminFormTitle")
            .innerText = "Update Admin";


        document.getElementById("adminFormContainer")
            .style.display = "block";

    })

    .catch(error => {

        console.error("Error:", error);

        alert("Unable to load admin");

    });

}


// ===============================
// Hide Admin Form
// ===============================

function hideAdminForm() {

    document.getElementById("adminFormContainer")
        .style.display = "none";


    document.getElementById("adminForm")
        .reset();

}


// ===============================
// Update Admin
// ===============================

document.getElementById("adminForm")
    .addEventListener("submit", function(event) {

        event.preventDefault();


        const adminId =
            document.getElementById("adminId").value;


        const adminData = {

            name:
                document.getElementById("adminName").value,

            email:
                document.getElementById("adminEmail").value

        };


        fetch(API_URL + "/" + adminId, {

            method: "PUT",

            headers: {

                "Content-Type":
                    "application/json",

                "Authorization":
                    "Bearer " + token

            },

            body:
                JSON.stringify(adminData)

        })

        .then(response => response.json())

        .then(result => {

            if (result.status === 200) {

                alert(result.message);


                hideAdminForm();


                loadAdmins();

            }

            else {

                alert(
                    result.message ||
                    "Update failed"
                );

            }

        })

        .catch(error => {

            console.error("Error:", error);

            alert("Something went wrong");

        });

    });


// ===============================
// Change Password
// ===============================

document.getElementById("passwordForm")
    .addEventListener("submit", function(event) {

        event.preventDefault();


        const adminId =
            document.getElementById("adminId").value;


        if (!adminId) {

            alert(
                "Please edit an admin first"
            );

            return;

        }


        const passwordData = {

            currentPassword:
                document.getElementById("currentPassword").value,

            newPassword:
                document.getElementById("newPassword").value

        };


        fetch(
            API_URL + "/" + adminId + "/password",
            {

                method: "PUT",

                headers: {

                    "Content-Type":
                        "application/json",

                    "Authorization":
                        "Bearer " + token

                },

                body:
                    JSON.stringify(passwordData)

            }

        )

        .then(response => response.json())

        .then(result => {

            if (result.status === 200) {

                alert(result.message);


                document.getElementById("passwordForm")
                    .reset();

            }

            else {

                alert(
                    result.message ||
                    "Password change failed"
                );

            }

        })

        .catch(error => {

            console.error("Error:", error);

            alert("Something went wrong");

        });

    });


// ===============================
// Delete Admin
// ===============================

function deleteAdmin(id) {

    const confirmation = confirm(
        "Are you sure you want to delete this admin?"
    );


    if (!confirmation) {

        return;

    }


    fetch(API_URL + "/" + id, {

        method: "DELETE",

        headers: {

            "Authorization":
                "Bearer " + token

        }

    })

    .then(response => response.json())

    .then(result => {

        if (result.status === 200) {

            alert(result.message);

            loadAdmins();

        }

        else {

            alert(
                result.message ||
                "Delete failed"
            );

        }

    })

    .catch(error => {

        console.error("Error:", error);

        alert("Something went wrong");

    });

}


// ===============================
// Logout
// ===============================

function logout() {

    localStorage.removeItem("token");

    window.location.href = "login.html";

}


// ===============================
// Initial Load
// ===============================

loadAdmins();