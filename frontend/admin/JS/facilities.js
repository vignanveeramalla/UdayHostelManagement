const API_URL = "http://localhost:8080/facilities";


// ===============================
// Check Login
// ===============================

const token = localStorage.getItem("token");

if (!token) {
    window.location.href = "login.html";
}


// ===============================
// Load All Facilities
// ===============================

function loadFacilities() {

    fetch(API_URL, {
        method: "GET",
        headers: {
            "Authorization": "Bearer " + token
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

        displayFacilities(result.data);
    })

    .catch(error => {

        console.error("Error:", error);

        document.getElementById("facilityTableBody").innerHTML =
            `<tr>
                <td colspan="5">
                    Failed to load facilities
                </td>
            </tr>`;
    });
}


// ===============================
// Display Facilities
// ===============================

function displayFacilities(facilities) {

    const tableBody =
        document.getElementById("facilityTableBody");

    tableBody.innerHTML = "";


    if (!facilities || facilities.length === 0) {

        tableBody.innerHTML =
            `<tr>
                <td colspan="5">
                    No facilities found
                </td>
            </tr>`;

        return;
    }


    facilities.forEach(facility => {

        const row = document.createElement("tr");


        row.innerHTML = `

            <td>${facility.facilityId}</td>

            <td>${facility.name}</td>

            <td>${facility.description}</td>

            <td>
                <span class="status ${facility.status.toLowerCase()}">
                    ${facility.status}
                </span>
            </td>

            <td>

                <button
                    onclick="editFacility(${facility.facilityId})">
                    Edit
                </button>

                <button
                    onclick="deleteFacility(${facility.facilityId})">
                    Delete
                </button>

            </td>

        `;


        tableBody.appendChild(row);

    });
}


// ===============================
// Show Add Form
// ===============================

function showFacilityForm() {

    document.getElementById("facilityFormContainer")
        .style.display = "block";

    document.getElementById("formTitle")
        .innerText = "Add Facility";

    document.getElementById("facilityForm")
        .reset();

    document.getElementById("facilityId")
        .value = "";
}


// ===============================
// Hide Form
// ===============================

function hideFacilityForm() {

    document.getElementById("facilityFormContainer")
        .style.display = "none";

    document.getElementById("facilityForm")
        .reset();
}


// ===============================
// Add / Update Facility
// ===============================

document.getElementById("facilityForm")
    .addEventListener("submit", function(event) {

        event.preventDefault();


        const facilityId =
            document.getElementById("facilityId").value;


        const facilityData = {

            name:
                document.getElementById("facilityName").value,

            description:
                document.getElementById("facilityDescription").value,

            status:
                document.getElementById("facilityStatus").value

        };


        let url = API_URL;
        let method = "POST";


        // Update
        if (facilityId) {

            url = API_URL + "/" + facilityId;
            method = "PUT";
        }


        fetch(url, {

            method: method,

            headers: {

                "Content-Type": "application/json",

                "Authorization":
                    "Bearer " + token
            },

            body: JSON.stringify(facilityData)

        })

        .then(response => response.json())

        .then(result => {

            if (result.status === 200 ||
                result.status === 201) {

                alert(result.message);

                hideFacilityForm();

                loadFacilities();

            } else {

                alert(result.message ||
                      "Operation failed");
            }

        })

        .catch(error => {

            console.error("Error:", error);

            alert("Something went wrong");
        });

    });


// ===============================
// Edit Facility
// ===============================

function editFacility(id) {

    fetch(API_URL + "/id/" + id, {

        method: "GET",

        headers: {

            "Authorization":
                "Bearer " + token
        }

    })

    .then(response => response.json())

    .then(result => {

        const facility = result.data;


        document.getElementById("facilityId")
            .value = facility.facilityId;

        document.getElementById("facilityName")
            .value = facility.name;

        document.getElementById("facilityDescription")
            .value = facility.description;

        document.getElementById("facilityStatus")
            .value = facility.status;


        document.getElementById("formTitle")
            .innerText = "Edit Facility";


        document.getElementById("facilityFormContainer")
            .style.display = "block";

    })

    .catch(error => {

        console.error("Error:", error);

        alert("Unable to load facility");
    });
}


// ===============================
// Delete Facility
// ===============================

function deleteFacility(id) {

    const confirmation =
        confirm("Are you sure you want to delete this facility?");


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

            loadFacilities();

        } else {

            alert(result.message ||
                  "Delete failed");
        }

    })

    .catch(error => {

        console.error("Error:", error);

        alert("Something went wrong");
    });
}


// ===============================
// Filter By Status
// ===============================

function filterFacilities() {

    const status =
        document.getElementById("statusFilter").value;


    if (status === "") {

        loadFacilities();

        return;
    }


    fetch(API_URL + "/status/" + status, {

        method: "GET",

        headers: {

            "Authorization":
                "Bearer " + token
        }

    })

    .then(response => response.json())

    .then(result => {

        displayFacilities(result.data);

    })

    .catch(error => {

        console.error("Error:", error);

    });
}


// ===============================
// Search By Name
// ===============================

function searchFacilities() {

    const name =
        document.getElementById("searchName").value.trim();


    if (name === "") {

        loadFacilities();

        return;
    }


    fetch(
        API_URL + "/search?name=" +
        encodeURIComponent(name),

        {
            method: "GET",

            headers: {

                "Authorization":
                    "Bearer " + token
            }
        }
    )

    .then(response => response.json())

    .then(result => {

        displayFacilities(result.data);

    })

    .catch(error => {

        console.error("Error:", error);

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

loadFacilities();